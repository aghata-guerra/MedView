package com.guerra.medview.controller;

import com.guerra.medview.dto.DoctorResponse;
import com.guerra.medview.dto.LoginRequest;
import com.guerra.medview.dto.LoginResponse;
import com.guerra.medview.dto.RegisterDoctorRequest;
import com.guerra.medview.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Doctor registration and login (public endpoints)")
@SecurityRequirements
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new doctor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doctor created"),
            @ApiResponse(responseCode = "400", description = "Invalid body (e.g. invalid CPF, short password)", content = @Content),
            @ApiResponse(responseCode = "409", description = "E-mail or CPF already registered", content = @Content)
    })
    public ResponseEntity<DoctorResponse> register(@Valid @RequestBody RegisterDoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(DoctorResponse.from(authService.register(request)));
    }

    @PostMapping("/login")
    @Operation(summary = "Log in and get a JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Wrong e-mail or password", content = @Content)
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
