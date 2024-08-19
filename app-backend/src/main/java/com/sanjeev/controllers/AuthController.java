package com.sanjeev.controllers;


import com.sanjeev.dto.DtoUser;
import com.sanjeev.dto.JwtAuthResponse;
import com.sanjeev.dto.LoginDto;
import com.sanjeev.iservices.AuthService;
import com.sanjeev.utils.AppUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

//Build Register REST API
    @PostMapping("/signup")
    public ResponseEntity<Object> signup( @Valid @RequestBody DtoUser body){
        DtoUser newUser = authService.register(body);
        return AppUtils.createResponse(newUser
                , HttpStatus.CREATED);

    };

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
