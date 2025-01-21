package com.fastfood.api.adapters.inbound;

import com.fastfood.api.application.usecases.CustomerUseCase;
import com.fastfood.api.domain.customer.CustomerDTO;
import com.fastfood.api.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerUseCase customerUseCase;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void createCustomer_shouldReturnCreatedCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        Mockito.when(customerUseCase.createCustomer(customerDTO)).thenReturn(customerDTO);

        CustomerDTO result = customerController.createCustomer(customerDTO);

        assertEquals(customerDTO, result);
    }

    @Test
    void findCustomerById_shouldReturnCustomer_whenCustomerExists() {
        CustomerDTO customerDTO = new CustomerDTO();
        Mockito.when(customerUseCase.findCustomerById(1L)).thenReturn(customerDTO);

        CustomerDTO result = customerController.findCustomerById(1L);

        assertEquals(customerDTO, result);
    }

    @Test
    void findCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        Mockito.when(customerUseCase.findCustomerById(1L)).thenThrow(new CustomerNotFoundException("Customer not found"));

        assertThrows(CustomerNotFoundException.class, () -> customerController.findCustomerById(1L));
    }

    @Test
    void findCustomerByCpf_shouldReturnCustomer_whenCustomerExists() {
        CustomerDTO customerDTO = new CustomerDTO();
        Mockito.when(customerUseCase.findCustomerByCpf("12345678901")).thenReturn(customerDTO);

        CustomerDTO result = customerController.findCustomerByCpf("12345678901");

        assertEquals(customerDTO, result);
    }

    @Test
    void findCustomerByCpf_shouldThrowException_whenCustomerDoesNotExist() {
        Mockito.when(customerUseCase.findCustomerByCpf("12345678901")).thenThrow(new CustomerNotFoundException("Customer not found"));

        assertThrows(CustomerNotFoundException.class, () -> customerController.findCustomerByCpf("12345678901"));
    }

    @Test
    void disableCustomerById_shouldCallUseCaseDisableCustomerById() {
        customerController.disableCustomerById(1L);

        Mockito.verify(customerUseCase).disableCustomerById(1L);
    }
}