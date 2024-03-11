package com.foretruff;

import com.foretruff.entity.User;
import com.foretruff.util.HibernateUtil;
import comm.foretruff.entity.Chat;
import comm.foretruff.entity.Company;
import comm.foretruff.entity.LocaleInfo;
import comm.foretruff.entity.Profile;
import comm.foretruff.entity.UserChat;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;

import static comm.foretruff.entity.User.builder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {

    @Test
    void localeInfo() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            var company = session.get(Company.class, 5L);
//            company.getLocales().add(LocaleInfo.of("ua","привіт"));
//            company.getLocales().add(LocaleInfo.of("en","hello"));
            company.getUsers().forEach((k, v) -> System.out.println(v));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkManyToMany() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            var user = session.get(comm.foretruff.entity.User.class, 6L);
            var chat = session.get(Chat.class, 2L);

            var userChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();
            userChat.setUser(user);
            userChat.setChat(chat);

            session.persist(userChat);


            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            var user = builder()
                    .username("test4")
                    .build();

            var profile = Profile.builder()
                    .language("uk")
                    .street("kiev-12b")
                    .build();
            profile.setUser(user);
            session.persist(user);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrhanRemoval() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = session.get(Company.class, 5);
//            company.getUsers().removeIf(user -> user.getId().equals(3L));

            session.getTransaction().commit();
        }
    }


    @Test
    void checkLazyInitialization() {
        Company company = null;
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.get(Company.class, 10); //getReference что бы получить прокси

            session.getTransaction().commit();
        }
        var users = company.getUsers();
        System.out.println(users.size());
    }


    @Test
    void deleteCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 14);
        session.remove(company);

        session.getTransaction().commit();
    }

    @Test
//    @Disabled
    void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = Company.builder()
                .name("Facebook111")
                .build();

        var user = builder()
                .username("for777111")
                .build();

//        user.setCompany(company);
//        company.getUsers().add(user);

        company.addUser(user);

        session.persist(company);

        session.getTransaction().commit();
    }


    @Test
//    @Disabled
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(comm.foretruff.entity.Company.class, 5);
        Hibernate.initialize(company.getUsers());
        System.out.println(company);

        session.getTransaction().commit();
    }

    @Test
    @Disabled
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        var resultSet = preparedStatement.executeQuery();
        var username = resultSet.getString("username");
        resultSet.getString("firstname");
        resultSet.getString("lastname");

        var userClass = User.class;
        var userConstructorconstructor = userClass.getConstructor();
        var user = userConstructorconstructor.newInstance();
        var usernameField = userClass.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(user, username);
    }

    @Test
    @Disabled
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
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