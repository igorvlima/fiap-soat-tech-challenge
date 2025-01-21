package com.fastfood.api.domain.customer;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByCpf(String cpf);

    void deleteById(Long id);
}
