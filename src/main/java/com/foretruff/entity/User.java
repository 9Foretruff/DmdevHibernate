package com.foretruff.entity;

import jakarta.persistence.Entity;
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
@Table(name = "users" , schema = "public")
public class User { // ентити нельзя делать final поля
    @Id
    private String username; // должен быть Serializable
    private String firstname;
    private String lastname;
//    @Column(name = "birth_date")
    private LocalDate birthDate;
    private Integer age;
}
