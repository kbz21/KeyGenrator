package com.kibrom.keyGenerator.mapper;


import com.kibrom.keyGenerator.dto.KeyResponseDTO;
import com.kibrom.keyGenerator.entity.GeneratedKey;
import org.springframework.stereotype.Component;


@Component
public class KeyMapper {


    public KeyResponseDTO toDTO(GeneratedKey key) {

        return new KeyResponseDTO(
                key.getId(),
                key.getKeyAlgorithm(),
                key.getKeySize(),
                key.getGeneratedKey(),
                key.getCreatedAt()
        );
    }


}