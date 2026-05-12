package com.enterprise.bank_lies.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String name;
    private String email;
    private String numberOfUser;
    private String password;
    private LocalDate dateOfBirth;
    private String country;
    private String state;
    private String neighborhood;
    private String city;
    private String number;
}
