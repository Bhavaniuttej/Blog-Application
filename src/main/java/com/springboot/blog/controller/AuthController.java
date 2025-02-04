package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name="CRUD REST APIs for Authentication Resource"
)
public class AuthController
{
    private AuthService authService;

    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
    {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
    //Build Register Rest Api

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register( @RequestBody RegisterDto registerDto)
    {
        String response=authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
