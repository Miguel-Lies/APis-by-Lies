package com.enterprise.bank_lies.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "information_of_transfer")
public class TransferOfUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "name_of_sender",nullable = false)
    private BankDataAccountUser sender;

    @ManyToOne
    @JoinColumn(name = "name_of_receiver",nullable = false)
    private BankDataAccountUser receiver;

    @Column(name = "value_of_transfer",nullable = false,precision = 19,scale = 2)
    private BigDecimal value;

    @Column(name = "transfer_time",nullable = false)
    private LocalDateTime time;


}
