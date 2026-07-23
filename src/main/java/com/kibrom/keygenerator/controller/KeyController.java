package com.kibrom.keygenerator.controller;

import com.kibrom.keygenerator.dto.KeyRequestDTO;
import com.kibrom.keygenerator.dto.KeyResponseDTO;
import com.kibrom.keygenerator.service.KeyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
@CrossOrigin(origins = "http://localhost:3000")
public class KeyController {

    private final KeyService keyService;

    public KeyController(KeyService keyService) {
        this.keyService = keyService;
    }

    @PostMapping("/generate")
    public ResponseEntity<KeyResponseDTO> generateKey(@Valid @RequestBody KeyRequestDTO request) {
        KeyResponseDTO response = keyService.generateKey(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<KeyResponseDTO>> getAllKeys() {
        return ResponseEntity.ok(keyService.getAllKeys());
    }

    @GetMapping("/algorithm/{algorithm}")
    public ResponseEntity<List<KeyResponseDTO>> getKeysByAlgorithm(@PathVariable String algorithm) {
        return ResponseEntity.ok(keyService.getKeysByAlgorithm(algorithm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKey(@PathVariable Long id) {
        keyService.deleteKey(id);
        return ResponseEntity.noContent().build();
    }
}