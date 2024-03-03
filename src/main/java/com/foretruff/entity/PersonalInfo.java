package com.foretruff.entity;

import com.foretruff.convertor.BirthDayConvertor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {
    private String firstname;
    private String lastname;

    @Convert(converter = BirthDayConvertor.class)
    @Column(name = "birth_date")
    private BirthDay birthDate;
}