package com.guerra.medview.application.adapters.out;

import com.guerra.medview.application.adapters.out.repository.PatientRepository;
import com.guerra.medview.application.adapters.out.repository.mapper.CustomerEntityMapper;
import com.guerra.medview.application.core.domain.Patient;
import com.guerra.medview.application.ports.out.UpdatePatientOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientAdapter implements UpdatePatientOutputPort {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public void update(Patient customer){
        var customerEntity = customerEntityMapper.toCustomerEntity(customer);
        patientRepository.save(customerEntity);

    }
}
