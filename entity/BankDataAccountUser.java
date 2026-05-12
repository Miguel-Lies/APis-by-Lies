package com.enterprise.bank_lies.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "AccountUser")
public class BankDataAccountUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",length = 80,nullable = false)
    private String name;

    @Column(name = "email",length = 90,nullable = false,unique = true)
    private String email;

    @Column(name = "numberOfUser",nullable = false,unique = true)
    private String number;

    @Column(name = "password",length = 20,nullable = false)
    private String password;

    @Column(name = "date_of_birth",nullable = false)
    private LocalDate dateOfBirth;
}
