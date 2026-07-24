package com.kibrom.keygenerator.repository;

import com.kibrom.keygenerator.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    Optional<Algorithm> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}