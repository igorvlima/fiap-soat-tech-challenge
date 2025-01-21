package com.fastfood.api.application.usecases;


import com.fastfood.api.domain.customer.CustomerDTO;

public interface CustomerUseCase {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO findCustomerById(Long id);

    CustomerDTO findCustomerByCpf(String cpf);

    void disableCustomerById(Long id);
}
