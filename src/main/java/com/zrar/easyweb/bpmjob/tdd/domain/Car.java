package com.zrar.easyweb.bpmjob.tdd.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    // when do find ,hibernate tries to reconstruct that object with default contructor
    public Car() {

    }

    public Car(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
