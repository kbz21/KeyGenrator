package com.kibrom.keyGenerator.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "algorithms")
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(
            mappedBy = "algorithm",
            cascade = CascadeType.ALL
    )
    private List<GeneratedKey> keys;


    public Algorithm() {
    }


    public Algorithm(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<GeneratedKey> getKeys() {
        return keys;
    }


    public void setKeys(List<GeneratedKey> keys) {
        this.keys = keys;
    }
}