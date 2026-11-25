package com.kibrom.keygenerator.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "generated_keys")
@EntityListeners(AuditingEntityListener.class)
public class GeneratedKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String keyAlgorithm;

    @Column(nullable = false)
    private Integer keySize;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String generatedKey;

    // NEW: Alias field for searching
    @Column(length = 100)
    private String alias;

    // NEW: Purpose field
    @Column(length = 255)
    private String purpose;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;

    // Constructors
    public GeneratedKey() {}

    public GeneratedKey(String keyAlgorithm, Integer keySize, String generatedKey) {
        this.keyAlgorithm = keyAlgorithm;
        this.keySize = keySize;
        this.generatedKey = generatedKey;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getKeyAlgorithm() { return keyAlgorithm; }
    public void setKeyAlgorithm(String keyAlgorithm) { this.keyAlgorithm = keyAlgorithm; }

    public Integer getKeySize() { return keySize; }
    public void setKeySize(Integer keySize) { this.keySize = keySize; }

    public String getGeneratedKey() { return generatedKey; }
    public void setGeneratedKey(String generatedKey) { this.generatedKey = generatedKey; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Algorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(Algorithm algorithm) { this.algorithm = algorithm; }
}
