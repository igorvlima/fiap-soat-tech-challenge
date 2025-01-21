package com.fastfood.api.adapters.inbound;

import com.fastfood.api.application.usecases.CustomerUseCase;
import com.fastfood.api.domain.customer.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerUseCase customerUseCase;

    @PostMapping("/customer")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerUseCase.createCustomer(customerDTO);
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO findCustomerById(@PathVariable Long id) {
        return customerUseCase.findCustomerById(id);
    }

    @GetMapping("/customer")
    public CustomerDTO findCustomerByCpf(@RequestParam String cpf) {
        return customerUseCase.findCustomerByCpf(cpf);
    }

    @DeleteMapping("/customer/{id}")
    public void disableCustomerById(@PathVariable Long id) {
        customerUseCase.disableCustomerById(id);
    }
}

