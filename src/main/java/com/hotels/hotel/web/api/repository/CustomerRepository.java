package com.hotels.hotel.web.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.hotels.hotel.web.api.data.entity.CustomerEntity;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<CustomerEntity, UUID> {
    CustomerEntity findByEmailAddress(String emailAddress);
  }
