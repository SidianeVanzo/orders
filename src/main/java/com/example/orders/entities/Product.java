package com.example.orders.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

@Data
@NoArgsConstructor
@Entity
    @Table(name = "PRODUCT")
public class Product implements Serializable {

    @Id
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ")
    @Column(name = "PRODUCT_ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "CATEGORY", length = 100)
    private String category;

    @Column(name = "WEIGHT")
    private Double weight;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;
}