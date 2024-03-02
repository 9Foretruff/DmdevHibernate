package com.foretruff;

import com.foretruff.convertor.BirthDayConvertor;
import com.foretruff.entity.BirthDay;
import com.foretruff.entity.Role;
import com.foretruff.entity.User;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;

//        SessionFactory

//        var connection = DriverManager
//                .getConnection("db_url", "db_username", "db_password");
//        Session

        var configuration = new Configuration();
//        configuration.configure().addAnnotatedClass(User.class);
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthDayConvertor());
        configuration.configure();

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = User.builder()
                    .username("foretruff1")
                    .firstname("Maksim")
                    .lastname("Rokitko")
                    .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
                    .role(Role.ADMIN)
                    .build();
            session.persist(user);
            session.getTransaction().commit();
        }
    }
}
