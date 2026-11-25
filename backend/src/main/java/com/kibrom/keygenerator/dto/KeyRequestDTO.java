package com.kibrom.keygenerator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class KeyRequestDTO {

    @NotBlank(message = "Algorithm is required")
    private String algorithm;

    @NotNull(message = "Key size is required")
    private Integer keySize;

    // NEW: Alias field (optional)
    private String alias;

    // NEW: Purpose field (optional)
    private String purpose;

    public KeyRequestDTO() {}

    public KeyRequestDTO(String algorithm, Integer keySize, String alias, String purpose) {
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.alias = alias;
        this.purpose = purpose;
    }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public Integer getKeySize() { return keySize; }
    public void setKeySize(Integer keySize) { this.keySize = keySize; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
