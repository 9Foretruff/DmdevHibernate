package com.foretruff;

import com.foretruff.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .firstname("Maksim")
                .lastname("Rokitko")
                .username("foretruff11999")
                .birthDate(LocalDate.of(2006, 1, 3))
                .age(18)
                .build();
        String sql = """
                insert into
                %s
                (%s)
                values
                (%s)
                """;
        var tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(annotation -> annotation.schema() + "." + annotation.name())
                .orElse(user.getClass().getName());

        var declaredFields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(","));

        var columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        var preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(1, declaredField.get(user));
        }
    }

}