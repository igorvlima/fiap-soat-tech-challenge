package com.fastfood.api.application.service;

import com.fastfood.api.domain.customer.Customer;
import com.fastfood.api.domain.customer.CustomerDTO;
import com.fastfood.api.domain.customer.CustomerRepository;
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
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void createCustomer_shouldReturnCreatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        Mockito.when(mapper.DTOtoDomain(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(mapper.domainToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        assertEquals(customerDTO, result);
    }

    @Test
    void findCustomerById_shouldReturnCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(mapper.domainToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.findCustomerById(1L);

        assertEquals(customerDTO, result);
    }

    @Test
    void findCustomerById_shouldReturnNull_whenCustomerDoesNotExist() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerDTO result = customerService.findCustomerById(1L);

        assertNull(result);
    }

    @Test
    void findCustomerByCpf_shouldReturnCustomer_whenCustomerExists() {
        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();
        Mockito.when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.of(customer));
        Mockito.when(mapper.domainToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.findCustomerByCpf("12345678901");

        assertEquals(customerDTO, result);
    }

    @Test
    void findCustomerByCpf_shouldReturnNull_whenCustomerDoesNotExist() {
        Mockito.when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        CustomerDTO result = customerService.findCustomerByCpf("12345678901");

        assertNull(result);
    }

    @Test
    void disableCustomerById_shouldCallRepositoryDeleteById() {
        customerService.disableCustomerById(1L);

        Mockito.verify(customerRepository).deleteById(1L);
    }
}
