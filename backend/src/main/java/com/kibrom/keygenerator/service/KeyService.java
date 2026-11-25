package com.kibrom.keygenerator.service;

import com.kibrom.keygenerator.dto.KeyRequestDTO;
import com.kibrom.keygenerator.dto.KeyResponseDTO;
import com.kibrom.keygenerator.entity.Algorithm;
import com.kibrom.keygenerator.entity.GeneratedKey;
import com.kibrom.keygenerator.exception.KeyGenerationException;
import com.kibrom.keygenerator.mapper.KeyMapper;
import com.kibrom.keygenerator.repository.AlgorithmRepository;
import com.kibrom.keygenerator.repository.GeneratedKeyRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KeyService {

    private static final Logger logger = LoggerFactory.getLogger(KeyService.class);

    private final GeneratedKeyRepository keyRepository;
    private final KeyMapper keyMapper;
    private final AlgorithmRepository algorithmRepository;

    public KeyService(GeneratedKeyRepository keyRepository,
                      KeyMapper keyMapper,
                      AlgorithmRepository algorithmRepository) {
        this.keyRepository = keyRepository;
        this.keyMapper = keyMapper;
        this.algorithmRepository = algorithmRepository;
    }

    @PostConstruct
    public void initializeAlgorithms() {
        logger.info("Initializing algorithms...");
        if (algorithmRepository.count() == 0) {
            logger.info("No algorithms found. Seeding database...");
            algorithmRepository.save(new Algorithm("AES"));
            algorithmRepository.save(new Algorithm("RSA"));
            logger.info("Algorithms seeded successfully!");
        } else {
            logger.info("Algorithms already exist. Count: {}", algorithmRepository.count());
        }
    }

    public KeyResponseDTO generateKey(KeyRequestDTO request) {
        try {
            String algorithmName = request.getAlgorithm().toUpperCase();
            Integer keySize = request.getKeySize();

            validateKeySize(algorithmName, keySize);

            logger.info("Generating {} key with size {} bits", algorithmName, keySize);

            String generatedKey = generateKeyString(algorithmName, keySize);

            Algorithm algorithm = algorithmRepository
                    .findByNameIgnoreCase(algorithmName)
                    .orElseThrow(() -> new KeyGenerationException("Algorithm not found: " + algorithmName));

            GeneratedKey keyEntity = new GeneratedKey();
            keyEntity.setKeyAlgorithm(algorithmName);
            keyEntity.setKeySize(keySize);
            keyEntity.setGeneratedKey(generatedKey);
            keyEntity.setAlgorithm(algorithm);
            
            // Save alias and purpose
            keyEntity.setAlias(request.getAlias());
            keyEntity.setPurpose(request.getPurpose());

            GeneratedKey savedKey = keyRepository.save(keyEntity);
            logger.info("Key generated successfully with ID: {}", savedKey.getId());

            return keyMapper.toDTO(savedKey);

        } catch (Exception e) {
            logger.error("Key generation failed: {}", e.getMessage(), e);
            throw new KeyGenerationException("Failed to generate key: " + e.getMessage(), e);
        }
    }

    private void validateKeySize(String algorithm, Integer keySize) {
        boolean isValid = switch (algorithm) {
            case "AES" -> List.of(128, 192, 256).contains(keySize);
            case "RSA" -> List.of(1024, 2048, 4096).contains(keySize);
            default -> false;
        };

        if (!isValid) {
            throw new KeyGenerationException(
                    String.format("Invalid key size %d for algorithm %s", keySize, algorithm)
            );
        }
    }

    private String generateKeyString(String algorithm, Integer keySize) throws Exception {
        return switch (algorithm) {
            case "AES" -> generateAESKey(keySize);
            case "RSA" -> generateRSAKey(keySize);
            default -> throw new KeyGenerationException("Unsupported algorithm: " + algorithm);
        };
    }

    private String generateAESKey(Integer size) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(size);
        SecretKey secretKey = generator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    private String generateRSAKey(Integer size) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(size);
        KeyPair pair = generator.generateKeyPair();
        return Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
    }

    public List<KeyResponseDTO> getKeysByAlgorithm(String algorithm) {
        return keyRepository.findByKeyAlgorithmIgnoreCase(algorithm)
                .stream()
                .map(keyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<KeyResponseDTO> getAllKeys() {
        return keyRepository.findAll()
                .stream()
                .map(keyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteKey(Long id) {
        if (!keyRepository.existsById(id)) {
            throw new KeyGenerationException("Key not found with ID: " + id);
        }
        keyRepository.deleteById(id);
        logger.info("Key with ID {} deleted successfully", id);
    }
}
