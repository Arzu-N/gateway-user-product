package org.example.userservice.service;

import ch.qos.logback.core.util.ReentryGuard;
import org.example.userservice.dao.entity.User;
import org.example.userservice.dao.repository.UserRepository;
import org.example.userservice.dto.LoginDto;
import org.example.userservice.dto.RegisterDto;
import org.example.userservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encode;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder encode, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encode = encode;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterDto dto){
        if(userRepository.existsByUserName(dto.getUserName())){
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(encode.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        userRepository.save(user);
    }

    public String login(LoginDto dto){
        User user = userRepository.findByUserName(dto.getUserName()).orElseThrow(() ->
                new RuntimeException("User not found"));
        if(!encode.matches(dto.getPassword(),user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }
        return jwtUtil.generate(user);
    }
}
