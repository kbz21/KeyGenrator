package com.kibrom.keygenerator.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class KeyRequestDTO {

    @NotBlank(message = "Algorithm is required")
    @Pattern(regexp = "^(AES|RSA)$", message = "Algorithm must be AES or RSA")
    private String algorithm;

    @NotNull(message = "Key size is required")
    @Min(value = 128, message = "Key size must be at least 128 bits")
    private Integer keySize;

    // Constructors
    public KeyRequestDTO() {}

    public KeyRequestDTO(String algorithm, Integer keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }

    // Getters and Setters
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