package com.guerra.medview.application.adapters.out.repository;

import com.guerra.medview.application.adapters.out.repository.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
