package com.kibrom.keygenerator.repository;

import com.kibrom.keygenerator.entity.GeneratedKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GeneratedKeyRepository extends JpaRepository<GeneratedKey, Long> {

    List<GeneratedKey> findByKeyAlgorithmIgnoreCase(String algorithm);

    List<GeneratedKey> findByKeySize(Integer keySize);

    List<GeneratedKey> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT k FROM GeneratedKey k WHERE k.keyAlgorithm = :algorithm AND k.keySize = :size")
    List<GeneratedKey> findByAlgorithmAndSize(@Param("algorithm") String algorithm,
                                              @Param("size") Integer size);

    long countByKeyAlgorithmIgnoreCase(String algorithm);
}