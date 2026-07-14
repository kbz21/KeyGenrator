package com.kibrom.keyGenerator.controller;


import com.kibrom.keyGenerator.dto.KeyRequestDTO;
import com.kibrom.keyGenerator.dto.KeyResponseDTO;
import com.kibrom.keyGenerator.service.KeyService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/keys")
public class KeyController {


    private final KeyService keyService;


    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }



    @PostMapping("/generate")
    public ResponseEntity<KeyResponseDTO> generateKey(
            @Valid @RequestBody KeyRequestDTO request
    ) {


        KeyResponseDTO response =
                keyService.generateKey(request);


        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );

    }

}