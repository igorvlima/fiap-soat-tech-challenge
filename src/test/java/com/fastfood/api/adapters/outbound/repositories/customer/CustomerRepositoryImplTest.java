package com.fastfood.api.adapters.outbound.repositories.customer;

import com.fastfood.api.adapters.outbound.entities.customer.JpaCustomerEntity;
import com.fastfood.api.domain.customer.Customer;
import com.fastfood.api.exceptions.CustomerNotFoundException;
import com.fastfood.api.utils.mappers.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryImplTest {

    @Mock
    private JpaCustomerRepository jpaCustomerRepository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Test
    void save_shouldReturnSavedCustomer() {
        Customer customer = new Customer();
        JpaCustomerEntity entity = new JpaCustomerEntity(customer);
        Mockito.when(jpaCustomerRepository.save(Mockito.any(JpaCustomerEntity.class))).thenReturn(entity);
        Mockito.when(mapper.jpaToDomain(entity)).thenReturn(customer);

        Customer result = customerRepository.save(customer);

        assertEquals(customer, result);
    }

    @Test
    void findById_shouldReturnCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        JpaCustomerEntity entity = new JpaCustomerEntity(customer);
        Mockito.when(jpaCustomerRepository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(mapper.jpaToDomain(entity)).thenReturn(customer);

        Optional<Customer> result = customerRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void findById_shouldThrowException_whenCustomerDoesNotExist() {
        Mockito.when(jpaCustomerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerRepository.findById(1L));
    }

    @Test
    void findByCpf_shouldReturnCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        JpaCustomerEntity entity = new JpaCustomerEntity(customer);
        Mockito.when(jpaCustomerRepository.findByCpf("12345678901")).thenReturn(Optional.of(entity));
        Mockito.when(mapper.jpaToDomain(entity)).thenReturn(customer);

        Optional<Customer> result = customerRepository.findByCpf("12345678901");

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void findByCpf_shouldThrowException_whenCustomerDoesNotExist() {
        Mockito.when(jpaCustomerRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerRepository.findByCpf("12345678901"));
    }

    @Test
    void deleteById_shouldCallRepositoryDisableCustomerById() {
        customerRepository.deleteById(1L);

        Mockito.verify(jpaCustomerRepository).disableCustomerById(1L);
    }
}