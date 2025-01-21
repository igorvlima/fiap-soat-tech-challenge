package com.fastfood.api.application.service;

import com.fastfood.api.application.usecases.CustomerUseCase;
import com.fastfood.api.domain.customer.CustomerDTO;
import com.fastfood.api.domain.customer.CustomerRepository;
import com.fastfood.api.utils.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        var customer = customerRepository.save(mapper.DTOtoDomain(customerDTO));
        return mapper.domainToDTO(customer);
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        var customer = customerRepository.findById(id).orElse(null);
        return mapper.domainToDTO(customer);
    }

    @Override
    public CustomerDTO findCustomerByCpf(String cpf) {
        var customer = customerRepository.findByCpf(cpf).orElse(null);
        return mapper.domainToDTO(customer);
    }

    @Override
    public void disableCustomerById(Long id) {customerRepository.deleteById(id);}
}
