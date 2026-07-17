package com.kibrom.keyGenerator.dto;


import java.time.LocalDateTime;


public class KeyResponseDTO {


    private Long id;

    private String algorithm;

    private Integer keySize;

    private String generatedKey;

    private LocalDateTime createdAt;



    public KeyResponseDTO() {
    }


    public KeyResponseDTO(Long id,
                          String algorithm,
                          Integer keySize,
                          String generatedKey,
                          LocalDateTime createdAt) {

        this.id = id;
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.generatedKey = generatedKey;
        this.createdAt = createdAt;
    }



    public Long getId() {
        return id;
    }


    public String getAlgorithm() {
        return algorithm;
    }


    public Integer getKeySize() {
        return keySize;
    }


    public String getGeneratedKey() {
        return generatedKey;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}