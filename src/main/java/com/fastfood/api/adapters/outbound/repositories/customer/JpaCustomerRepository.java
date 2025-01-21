package com.fastfood.api.adapters.outbound.repositories.customer;

import com.fastfood.api.adapters.outbound.entities.customer.JpaCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface JpaCustomerRepository extends JpaRepository<JpaCustomerEntity, Long> {

    Optional<JpaCustomerEntity> findByCpf(String cpf);

    @Transactional
    @Modifying
    @Query("UPDATE JpaCustomerEntity c SET c.active = false, c.updatedAt = CURRENT_TIMESTAMP WHERE c.id = :id")
    void disableCustomerById(@Param("id") Long id);

}
