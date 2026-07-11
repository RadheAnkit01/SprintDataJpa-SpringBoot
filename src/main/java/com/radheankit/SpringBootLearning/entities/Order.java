package com.radheankit.SpringBootLearning.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
//    @ManyToOne(fetch = FetchType.EAGER) // fetch at starting
    @ManyToOne(fetch = FetchType.LAZY) //fetch when required
    @JoinColumn(name = "userid")
    private User user;
}
