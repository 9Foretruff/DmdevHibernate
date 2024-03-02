package com.foretruff;

import com.foretruff.convertor.BirthDayConvertor;
import com.foretruff.entity.BirthDay;
import com.foretruff.entity.Role;
import com.foretruff.entity.User;
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
//        configuration.addAttributeConverter(new BirthDayConvertor());
//        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = User.builder()
                    .username("foretruffUAa")
                    .firstname("Maksim")
                    .lastname("Rokitko")
                    .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
                    .role(Role.ADMIN)
                    .info("""
                            {
                                "name": "Ivan",
                                "id": 30
                            }
                            """)
                    .build();
//            session.merge(user);
//            session.remove(user);
            var foretruffUA = session.get(User.class, "foretruffUA");
            System.out.println(foretruffUA);

            session.getTransaction().commit();
        }
    }
}
