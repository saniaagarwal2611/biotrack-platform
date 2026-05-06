package com.biotrack.iamservice.controller;

import com.biotrack.iamservice.dto.request.LoginRequest;
import com.biotrack.iamservice.dto.request.UserRequestDTO;
import com.biotrack.iamservice.dto.response.TokenResponse;
import com.biotrack.iamservice.dto.response.UserResponseDTO;
import com.biotrack.iamservice.entity.User;
import com.biotrack.iamservice.repository.UserRepository;
import com.biotrack.iamservice.security.JwtUtil;
import com.biotrack.iamservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO requestDTO) {
        return userService.addUser(requestDTO);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtUtil.generateToken(authentication);
        return new TokenResponse(token);
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logged out";
    }

    @GetMapping("/me")
    public UserResponseDTO me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;
        if (email == null) return null;

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return null;

        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus()
        );
    }

    @GetMapping("/validate")
    public String validate() {
        return "VALID";
    }
}
