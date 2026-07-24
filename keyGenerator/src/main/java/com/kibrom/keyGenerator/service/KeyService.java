package com.kibrom.keyGenerator.service;


import com.kibrom.keyGenerator.dto.KeyRequestDTO;
import com.kibrom.keyGenerator.dto.KeyResponseDTO;
import com.kibrom.keyGenerator.entity.Algorithm;
import com.kibrom.keyGenerator.entity.GeneratedKey;
import com.kibrom.keyGenerator.mapper.KeyMapper;
import com.kibrom.keyGenerator.repository.AlgorithmRepository;
import com.kibrom.keyGenerator.repository.GeneratedKeyRepository;

import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;



@Service
public class KeyService {


    private final GeneratedKeyRepository keyRepository;

    private final KeyMapper keyMapper;

    private final AlgorithmRepository algorithmRepository;



    public KeyService(
            GeneratedKeyRepository keyRepository,
            KeyMapper keyMapper,
            AlgorithmRepository algorithmRepository) {

        this.keyRepository = keyRepository;
        this.keyMapper = keyMapper;
        this.algorithmRepository = algorithmRepository;
    }





    public KeyResponseDTO generateKey(KeyRequestDTO request) {


        try {


            String generatedKey;


            if(request.getAlgorithm()
                    .equalsIgnoreCase("AES")) {


                generatedKey =
                        generateAES(request.getKeySize());


            } else if(request.getAlgorithm()
                    .equalsIgnoreCase("RSA")) {


                generatedKey =
                        generateRSA(request.getKeySize());


            } else {


                throw new RuntimeException(
                        "Unsupported algorithm"
                );

            }



            Algorithm algorithm =
                    algorithmRepository
                            .findByName(
                                    request.getAlgorithm()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Algorithm not found"
                                    )
                            );




            GeneratedKey key = new GeneratedKey();


            key.setKeyAlgorithm(
                    request.getAlgorithm()
            );


            key.setKeySize(
                    request.getKeySize()
            );


            key.setGeneratedKey(
                    generatedKey
            );


            key.setAlgorithm(
                    algorithm
            );



            GeneratedKey savedKey =
                    keyRepository.save(key);



            return keyMapper.toDTO(savedKey);



        } catch (Exception e) {


            throw new RuntimeException(
                    "Key generation failed",
                    e
            );

        }


    }







    private String generateAES(Integer size)
            throws Exception {


        KeyGenerator generator =
                KeyGenerator.getInstance("AES");


        generator.init(size);


        SecretKey secretKey =
                generator.generateKey();



        return Base64.getEncoder()
                .encodeToString(
                        secretKey.getEncoded()
                );

    }








    private String generateRSA(Integer size)
            throws Exception {


        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");


        generator.initialize(size);



        KeyPair pair =
                generator.generateKeyPair();




        return Base64.getEncoder()
                .encodeToString(
                        pair.getPrivate()
                                .getEncoded()
                );

    }


}