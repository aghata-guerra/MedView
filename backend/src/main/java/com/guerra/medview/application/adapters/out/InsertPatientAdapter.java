package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.CustomerEntityMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.InsertPatientOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InsertPatientAdapter implements InsertPatientOutputPort {


    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public void insert(Patient customer){
        var customerEntity = customerEntityMapper.toCustomerEntity(customer);
        patientRepository.save(customerEntity);
    }
}
