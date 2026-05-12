package com.enterprise.bank_lies.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "informations_of_user")
@Entity
public class AddressOfUser {

    @Id
    private Integer userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private BankDataAccountUser userIdWithAddress;

    @Column(name = "country_of_origin",nullable = false)
    private String countryOfOrigin;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "neighborhood",nullable = false)
    private String neighbordhood;

    @Column(name = "number",nullable = false)
    private String HouseNumber;
}
