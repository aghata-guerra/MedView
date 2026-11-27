package com.guerra.medview.service;

import com.guerra.medview.dto.LoginRequest;
import com.guerra.medview.dto.LoginResponse;
import com.guerra.medview.dto.RegisterDoctorRequest;
import com.guerra.medview.exception.DoctorAlreadyExistsException;
import com.guerra.medview.exception.InvalidCredentialsException;
import com.guerra.medview.model.Doctor;
import com.guerra.medview.repository.DoctorRepository;
import com.guerra.medview.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(DoctorRepository doctorRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public Doctor register(RegisterDoctorRequest request) {
        // Business rules: e-mail is stored in lower case, CPF only with digits.
        String email = normalizeEmail(request.email());
        String cpf = request.cpf().replaceAll("\\D", "");

        if (doctorRepository.existsByEmail(email)) {
            throw new DoctorAlreadyExistsException("e-mail");
        }
        if (doctorRepository.existsByCpf(cpf)) {
            throw new DoctorAlreadyExistsException("CPF");
        }

        Doctor doctor = new Doctor();
        doctor.setName(request.name().trim());
        doctor.setEmail(email);
        doctor.setCpf(cpf);
        doctor.setPasswordHash(passwordEncoder.encode(request.password()));
        return doctorRepository.save(doctor);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // Same error for "unknown e-mail" and "wrong password": does not reveal which one failed.
        Doctor doctor = doctorRepository.findByEmail(normalizeEmail(request.email()))
                .filter(found -> passwordEncoder.matches(request.password(), found.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);

        return new LoginResponse(jwtService.generateToken(doctor), "Bearer", jwtService.getExpirationSeconds());
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}
