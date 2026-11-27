package com.guerra.medview.repository;

import com.guerra.medview.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
