package com.example.personbackend.dto;

import com.example.personbackend.model.Gender;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String email;
    private String phone;
    private String taxId;
    private String address;
}
