package com.guerra.medview.service;

import com.guerra.medview.dto.LoginRequest;
import com.guerra.medview.dto.LoginResponse;
import com.guerra.medview.dto.RegisterDoctorRequest;
import com.guerra.medview.exception.DoctorAlreadyExistsException;
import com.guerra.medview.exception.InvalidCredentialsException;
import com.guerra.medview.model.Doctor;
import com.guerra.medview.repository.DoctorRepository;
import com.guerra.medview.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private static Doctor storedDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setEmail("ana@med.com");
        doctor.setPasswordHash("hashed");
        return doctor;
    }

    @Test
    void registerNormalizesEmailAndCpfAndHashesThePassword() {
        when(doctorRepository.existsByEmail("ana@med.com")).thenReturn(false);
        when(doctorRepository.existsByCpf("52998224725")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("hashed");
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Doctor saved = authService.register(
                new RegisterDoctorRequest("Ana", " Ana@Med.com ", "529.982.247-25", "secret123"));

        assertEquals("ana@med.com", saved.getEmail());
        assertEquals("52998224725", saved.getCpf());
        assertEquals("hashed", saved.getPasswordHash());
    }

    @Test
    void registerRejectsDuplicateEmail() {
        when(doctorRepository.existsByEmail("ana@med.com")).thenReturn(true);

        assertThrows(DoctorAlreadyExistsException.class, () -> authService.register(
                new RegisterDoctorRequest("Ana", "ana@med.com", "529.982.247-25", "secret123")));
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void registerRejectsDuplicateCpf() {
        when(doctorRepository.existsByEmail("ana@med.com")).thenReturn(false);
        when(doctorRepository.existsByCpf("52998224725")).thenReturn(true);

        assertThrows(DoctorAlreadyExistsException.class, () -> authService.register(
                new RegisterDoctorRequest("Ana", "ana@med.com", "529.982.247-25", "secret123")));
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void loginReturnsTokenWhenCredentialsMatch() {
        Doctor doctor = storedDoctor();
        when(doctorRepository.findByEmail("ana@med.com")).thenReturn(Optional.of(doctor));
        when(passwordEncoder.matches("secret123", "hashed")).thenReturn(true);
        when(jwtService.generateToken(doctor)).thenReturn("jwt");
        when(jwtService.getExpirationSeconds()).thenReturn(3600L);

        LoginResponse response = authService.login(new LoginRequest("Ana@Med.com", "secret123"));

        assertEquals("jwt", response.token());
        assertEquals("Bearer", response.tokenType());
        assertEquals(3600L, response.expiresIn());
    }

    @Test
    void loginFailsWithWrongPassword() {
        when(doctorRepository.findByEmail("ana@med.com")).thenReturn(Optional.of(storedDoctor()));
        when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(new LoginRequest("ana@med.com", "wrong")));
        verify(jwtService, never()).generateToken(any(Doctor.class));
    }

    @Test
    void loginFailsWithUnknownEmail() {
        when(doctorRepository.findByEmail("ghost@med.com")).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class,
                () -> authService.login(new LoginRequest("ghost@med.com", "secret123")));
        verifyNoInteractions(passwordEncoder, jwtService);
    }
}
