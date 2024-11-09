package com.hotels.hotel.web.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hotels.hotel.web.api.data.entity.CustomerEntity;
import com.hotels.hotel.web.api.repository.CustomerRepository;
import com.hotels.hotel.web.api.web.controller.ControllerUtils;
import com.hotels.hotel.web.api.web.error.ConflictException;
import com.hotels.hotel.web.api.web.error.NotFoundException;
import com.hotels.hotel.web.api.web.model.Customer;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        Iterable<CustomerEntity> entities = this.customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        entities.forEach(entity -> {
            customers.add(translateDbToWeb(entity));
        });

        System.out.println("Translated customers: " + customers);
        return customers;
    }

    public Customer findByEmailAddress(String emailAddress) {
        CustomerEntity entity = this.customerRepository.findByEmailAddress(emailAddress);
        return this.translateDbToWeb(entity);
    }

    public Customer addCustomer(Customer customer) {
        Customer existing = this.findByEmailAddress(customer.getEmailAddress());
        if (existing != null) {
            throw new ConflictException("customer with email already exists");
        }
        CustomerEntity entity = this.translateWebToDb(customer, true);
        entity = this.customerRepository.save(entity);
        return this.translateDbToWeb(entity);
    }

    public Customer getCustomer(String id) {
        UUID customerId = ControllerUtils.translateStringToUUID(id);
        Optional<CustomerEntity> optionalEntity = this.customerRepository.findById(customerId);
        if (optionalEntity.isEmpty()) {
            throw new NotFoundException("customer not found with id");
        }
        return this.translateDbToWeb(optionalEntity.get());
    }

    public Customer updateCustomer(Customer customer) {
        CustomerEntity entity = this.translateWebToDb(customer, false);
        entity = this.customerRepository.save(entity);
        return this.translateDbToWeb(entity);
    }

    public void deleteCustomer(String id) {
        UUID customerId = ControllerUtils.translateStringToUUID(id);
        this.customerRepository.deleteById(customerId);
    }

    private Customer translateDbToWeb(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        Customer customer =  new Customer(entity.getCustomerId().toString(), entity.getFirstName(),
                entity.getLastName(), entity.getEmailAddress(), entity.getPhoneNumber(), entity.getAddress());

                System.out.println("Translated Customer: " + customer); 

        return customer;
    }

    private CustomerEntity translateWebToDb(Customer customer, boolean createId) {
        UUID id;
        if (createId) {
            id = UUID.randomUUID();
        } else {
            id = ControllerUtils.translateStringToUUID(customer.getCustomerId());
        }
        return new CustomerEntity(id, customer.getFirstName(), customer.getLastName(),
                customer.getEmailAddress(), customer.getPhoneNumber(), customer.getAddress());
    }

}
