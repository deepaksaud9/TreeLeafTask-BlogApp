package com.blogapp.auth.authService;

import com.blogapp.auth.authRequest.AuthenticationRequest;
import com.blogapp.auth.authResponse.AuthenticationResponse;
import com.blogapp.auth.RegisterUser.RegisterUserRequest;
import com.blogapp.exception.ValidationException;
import com.blogapp.security.jwt.JwtService;
import com.blogapp.user.model.Role;
import com.blogapp.user.model.User;
import com.blogapp.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(@Valid RegisterUserRequest request) {

        // Validate first name
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new ValidationException("First name cannot be null or empty");
        }
        //Validate first name
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new ValidationException("Last name cannot be null or empty");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request){

        if ((request.getEmail() == null || request.getEmail().isEmpty()) || (request.getPassword() == null || request.getPassword().isEmpty())) {
            throw new ValidationException("Email and password filed cannot be null");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }catch (Exception e){
            throw new BadCredentialsException("Invalid username or password");
        }
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new ValidationException("user not found"));

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
    }
}
