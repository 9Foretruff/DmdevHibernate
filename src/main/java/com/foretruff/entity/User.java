package com.foretruff.entity;

import com.foretruff.convertor.BirthDayConvertor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
//POJO

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User { // в ентити нельзя делать final поля
    @Id
    private String username; // должен быть Serializable
    private String firstname;
    private String lastname;

//    @Convert(converter = BirthDayConvertor.class)
    @Column(name = "birth_date")
    private BirthDay birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;
}
