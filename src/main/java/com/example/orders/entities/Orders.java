package com.example.orders.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Orders implements Serializable {

    @Id
    @SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
    @Column(name = "ORDER_ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "CUSTOMER_NAME", length = 100)
    private String customeName;

    @Column(name = "CUSTOMER_CONTACT", length = 100)
    private String customeContact;

    @Column(name = "SHIPPING_ADDRESS", length = 200)
    private String shippingAddress;

    @Column(name = "GRAND_TOTAL")
    private Double grandTotal;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @OrderBy("id ASC")
    @Fetch(FetchMode.SELECT)
    private List<Item> items;
}