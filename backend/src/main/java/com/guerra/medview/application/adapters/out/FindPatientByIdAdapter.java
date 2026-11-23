package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.CustomerEntityMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.FindPatientByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindPatientByIdAdapter implements FindPatientByIdOutputPort {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Patient> find(String id){
        var customerEntity = patientRepository.findById(id);
        return customerEntity.map(entity -> customerEntityMapper.toCustomer(entity) );
    }
}
