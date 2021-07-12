package com.example.demo.entites.po;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ElasticUser {

    private String name;
    private int age;

    private String address;

}