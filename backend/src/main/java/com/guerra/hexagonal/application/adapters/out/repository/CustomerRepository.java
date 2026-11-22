package com.guerra.hexagonal.application.adapters.out.repository;

import com.guerra.hexagonal.application.adapters.out.repository.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerEntity,String> {
}
