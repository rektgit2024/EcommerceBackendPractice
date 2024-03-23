package com.springboot.practiceimitateshopeebackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private String name;
    private String address;
    private String email;
    private String contactNumber;

    private Double price;
    private Double totalAmount;
    private String shopName;
    private String productName;
    private Long quantity;

    private Long productId;

    private String paymentMethod;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Transaction> transaction;


}
