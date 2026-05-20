package com.bank.digital_banking.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double balance;
    private String email;
    @Builder.Default
    private Double limitPerTransaction = 1000.0;
    @Builder.Default
    private Double dailyLimit = 5000.0;
    @Builder.Default
    private Double monthlyLimit = 10000.0;
}
