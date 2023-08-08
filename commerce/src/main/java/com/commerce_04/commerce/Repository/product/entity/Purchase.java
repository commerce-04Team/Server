package com.commerce_04.commerce.Repository.product.entity;

import com.commerce_04.commerce.Repository.user.Entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Purchase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "purchase_at", nullable = false)
    private LocalDateTime purchaseAt;

}
