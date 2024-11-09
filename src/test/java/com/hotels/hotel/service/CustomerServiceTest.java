package com.hotels.hotel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hotels.hotel.web.api.data.entity.CustomerEntity;
import com.hotels.hotel.web.api.repository.CustomerRepository;
import com.hotels.hotel.web.api.service.CustomerService;
import com.hotels.hotel.web.api.web.error.ConflictException;
import com.hotels.hotel.web.api.web.error.NotFoundException;
import com.hotels.hotel.web.api.web.model.Customer;

// @ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers_ReturnlistOfCustomers() {

        List<CustomerEntity> entities = new ArrayList<>();
        CustomerEntity entity = new CustomerEntity(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");
        entities.add(entity);

        System.out.println(entities);

        when(customerRepository.findAll()).thenReturn(entities);

        List<Customer> customers = customerService.getAllCustomers();

        assertEquals(1, customers.size());
        assertEquals(entity.getEmailAddress(), customers.get(0).getEmailAddress());
        assertNotNull(customers);
        assertEquals("John", customers.get(0).getFirstName());
    }

    @Test
    void getAllCustomers(){
        Mockito.doReturn(getMockCustomers(2)).when(customerRepository).findAll();
        List<Customer> customers = this.customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }

    private Iterable<CustomerEntity> getMockCustomers(int size) {

        List<CustomerEntity> customers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            customers.add(new CustomerEntity(UUID.randomUUID(), "firstName"+i, "lastName"+i, 
            "email"+1+"@test.com", "256700162509", "address"+1 )); 
        }

        return customers;
    }

    @Test
    public void testFindByEmailAddress() {
        CustomerEntity entity = new CustomerEntity(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");

        when(customerRepository.findByEmailAddress("john.doe@example.com")).thenReturn(entity);

        Customer customer = customerService.findByEmailAddress("john.doe@example.com");

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("john.doe@example.com", customer.getEmailAddress());
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer(null, "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");
        CustomerEntity entity = new CustomerEntity(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");

        when(customerRepository.findByEmailAddress("john.doe@example.com")).thenReturn(null);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(entity);

        Customer addedCustomer = customerService.addCustomer(customer);

        assertNotNull(addedCustomer);
        assertEquals("John", addedCustomer.getFirstName());
    }
   
    @Test
    public void testAddCustomerConflict() {
        Customer customer = new Customer(null, "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");
        CustomerEntity entity = new CustomerEntity(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");

        when(customerRepository.findByEmailAddress("john.doe@example.com")).thenReturn(entity);

        assertThrows(ConflictException.class, () -> {
            customerService.addCustomer(customer);
        });
    }

    @Test
    public void testGetCustomer() {
        UUID id = UUID.randomUUID();
        CustomerEntity entity = new CustomerEntity(id, "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");
        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));

        Customer customer = customerService.getCustomer(id.toString());

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
    }

    @Test
    public void testIfCustomerIsNotFound() {
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
           customerService.getCustomer(id.toString());
        });
    }

    @Test
    public void testUpdatingCustomer() {
        int id = new Random().nextInt(50);
        Customer customer = new Customer(UUID.randomUUID().toString(), "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");
        CustomerEntity entity = new CustomerEntity(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "1234567890", "123 Street");
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(entity);

        Customer updatedCustomer = customerService.updateCustomer(customer);

        assertEquals("john.doe@example.com", updatedCustomer.getEmailAddress());

    }

    @Test
    public void testDeletingCustomer() {
        UUID id = UUID.randomUUID();
        doNothing().when(customerRepository).deleteById(id);

        customerService.deleteCustomer(id.toString());

    }

    
}
