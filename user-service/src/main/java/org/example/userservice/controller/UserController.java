package org.example.userservice.controller;

import ch.qos.logback.core.util.ReentryGuard;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.LoginDto;
import org.example.userservice.dto.RegisterDto;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void>register(@RequestBody RegisterDto dto){
        userService.register(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody LoginDto dto, ServletResponse servletResponse){
        return ResponseEntity.ok(userService.login(dto));
    }
}
