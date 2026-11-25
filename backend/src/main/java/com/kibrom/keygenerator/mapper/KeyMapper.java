package com.kibrom.keygenerator.mapper;

import com.kibrom.keygenerator.dto.KeyResponseDTO;
import com.kibrom.keygenerator.entity.GeneratedKey;
import org.springframework.stereotype.Component;

@Component
public class KeyMapper {

    public KeyResponseDTO toDTO(GeneratedKey key) {
        if (key == null) {
            return null;
        }

        return new KeyResponseDTO(
                key.getId(),
                key.getKeyAlgorithm(),
                key.getKeySize(),
                key.getGeneratedKey(),
                key.getAlias(),
                key.getPurpose(),
                key.getCreatedAt()
        );
    }
}
