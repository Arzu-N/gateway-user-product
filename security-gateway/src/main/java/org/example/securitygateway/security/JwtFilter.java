package org.example.securitygateway.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter implements GlobalFilter {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorization == null || !(authorization.startsWith("Bearer "))) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String token = authorization.substring(7);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            Long userId = ((Number) claims.get("userId")).longValue();
            String role = (claims.get("role")!=null)?claims.get("role").toString():null;
            ServerHttpRequest request = exchange.getRequest().mutate().
                    header("X-User-Id", String.valueOf(userId))
                    .header("X-Role",role).build();
            ServerWebExchange webExchange = exchange.mutate().request(request).build();
            return chain.filter(webExchange);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            e.printStackTrace();
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
