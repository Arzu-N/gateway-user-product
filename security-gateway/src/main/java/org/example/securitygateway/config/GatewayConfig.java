package org.example.securitygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator router(RouteLocatorBuilder builder){
       return  builder.routes().route("user-service",r->
                r.path("/auth/**")
                        .uri("http://localhost:8081"))
                .route("product-service",r->
                        r.path("/product/**")
                                .uri("http://localhost:8082"))
                .build();

    }

}
