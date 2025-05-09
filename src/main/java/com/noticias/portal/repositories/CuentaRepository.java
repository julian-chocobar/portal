package com.noticias.portal.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.noticias.portal.models.Cuenta;

public interface CuentaRepository extends MongoRepository<Cuenta, String> {

    Optional<Cuenta> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
