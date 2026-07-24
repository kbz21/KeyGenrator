package com.kibrom.keyGenerator.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class KeyRequestDTO {


    @NotBlank(message = "Algorithm is required")
    private String algorithm;


    @NotNull(message = "Key size is required")
    private Integer keySize;


    public KeyRequestDTO() {
    }


    public KeyRequestDTO(String algorithm, Integer keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }


    public String getAlgorithm() {
        return algorithm;
    }


    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }


    public Integer getKeySize() {
        return keySize;
    }


    public void setKeySize(Integer keySize) {
        this.keySize = keySize;
    }
}