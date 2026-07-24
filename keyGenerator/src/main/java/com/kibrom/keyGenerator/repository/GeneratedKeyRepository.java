package com.kibrom.keyGenerator.repository;


import com.kibrom.keyGenerator.entity.GeneratedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GeneratedKeyRepository
        extends JpaRepository<GeneratedKey, Long> {


    @Query("""
            SELECT k 
            FROM GeneratedKey k
            WHERE k.keyAlgorithm = :algorithm
            """)
    List<GeneratedKey> findByAlgorithmName(String algorithm);



    @Query("""
            SELECT k
            FROM GeneratedKey k
            WHERE k.keySize = :size
            """)
    List<GeneratedKey> findByKeySize(Integer size);

}