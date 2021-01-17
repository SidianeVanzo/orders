package com.example.orders.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ITEM")
@ToString(exclude = "orders")
public class Item implements Serializable {

    @Id
    @SequenceGenerator(name = "ITEM_SEQ", sequenceName = "ITEM_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ")
    @Column(name = "ITEM_ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "COST")
    private Double cost;

    @Column(name = "SHIPPING_FEE")
    private Double shippingFee;

    @Column(name = "TAX_AMOUNT")
    private Double taxAmount;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}