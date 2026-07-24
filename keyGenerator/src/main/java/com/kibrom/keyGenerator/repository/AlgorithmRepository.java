package com.kibrom.keyGenerator.repository;


import com.kibrom.keyGenerator.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {


    Optional<Algorithm> findByName(String name);


}