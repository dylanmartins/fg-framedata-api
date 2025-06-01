package com.fgc.framedata_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Character {

    @Id
    private Long id;
    private String name;
}
