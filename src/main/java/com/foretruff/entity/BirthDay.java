package com.foretruff.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record BirthDay(LocalDate birthDate) {

    public long getAge() {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

}
