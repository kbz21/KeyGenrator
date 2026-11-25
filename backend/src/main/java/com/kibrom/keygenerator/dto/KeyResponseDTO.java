package com.kibrom.keygenerator.dto;

import java.time.LocalDateTime;

public class KeyResponseDTO {

    private Long id;
    private String algorithm;
    private Integer keySize;
    private String generatedKey;
    private String alias;      // NEW
    private String purpose;    // NEW
    private LocalDateTime createdAt;

    public KeyResponseDTO() {}

    public KeyResponseDTO(Long id, String algorithm, Integer keySize,
                          String generatedKey, String alias, String purpose,
                          LocalDateTime createdAt) {
        this.id = id;
        this.algorithm = algorithm;
        this.keySize = keySize;
        this.generatedKey = generatedKey;
        this.alias = alias;
        this.purpose = purpose;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getAlgorithm() { return algorithm; }
    public Integer getKeySize() { return keySize; }
    public String getGeneratedKey() { return generatedKey; }
    public String getAlias() { return alias; }
    public String getPurpose() { return purpose; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
