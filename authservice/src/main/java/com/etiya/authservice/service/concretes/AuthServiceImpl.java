package com.etiya.authservice.service.concretes;

import com.etiya.authservice.service.abstracts.AuthService;
import com.etiya.authservice.service.abstracts.UserService;
import com.etiya.authservice.service.dtos.LoginRequest;
import com.etiya.authservice.service.dtos.LoginResponse;
import com.etiya.authservice.service.dtos.RegisterUserRequest;
import com.etiya.authservice.service.rules.AuthBusinessRules;
import com.etiya.common.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthBusinessRules authBusinessRules;

    public AuthServiceImpl(JwtService jwtService, UserService userService, AuthenticationManager authenticationManager, AuthBusinessRules authBusinessRules) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.authBusinessRules = authBusinessRules;
    }

    @Override
    public void register(RegisterUserRequest request) {
        userService.add(request);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        
        authBusinessRules.checkIfAuthenticated(authentication);

        UserDetails user = userService.loadUserByUsername(request.getEmail());
        String tokenString = jwtService.generateToken(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(tokenString);
        return loginResponse;
    }
}
