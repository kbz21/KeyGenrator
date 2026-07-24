package com.kibrom.keyGenerator.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "generated_keys")
public class GeneratedKey {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String keyAlgorithm;


    @Column(nullable = false)
    private Integer keySize;


    @Column(columnDefinition = "LONGTEXT")
    private String generatedKey;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;



    public GeneratedKey() {
    }


    public GeneratedKey(String keyAlgorithm,
                        Integer keySize,
                        String generatedKey) {

        this.keyAlgorithm = keyAlgorithm;
        this.keySize = keySize;
        this.generatedKey = generatedKey;
    }



    public Long getId() {
        return id;
    }


    public String getKeyAlgorithm() {
        return keyAlgorithm;
    }


    public void setKeyAlgorithm(String keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
    }


    public Integer getKeySize() {
        return keySize;
    }


    public void setKeySize(Integer keySize) {
        this.keySize = keySize;
    }


    public String getGeneratedKey() {
        return generatedKey;
    }


    public void setGeneratedKey(String generatedKey) {
        this.generatedKey = generatedKey;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Algorithm getAlgorithm() {
        return algorithm;
    }


    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}