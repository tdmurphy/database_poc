package com.tdm.example.poc.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "test")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseEntity {

    @Id
    private int id;

    @Column(name = "city")
    private String city;

    @Column(name = "code")
    private String code;

    @Column(name = "population")
    private int population;
}
