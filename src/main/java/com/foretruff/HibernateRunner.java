package com.foretruff;

import com.foretruff.convertor.BirthDayConvertor;
import com.foretruff.entity.BirthDay;
import com.foretruff.entity.PersonalInfo;
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
                    .personalInfo(PersonalInfo.builder()
                            .lastname("Petrov")
                            .firstname("Ivanov")
                            .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
                            .build())
                    .role(Role.ADMIN)
                    .info("""
                            {
                                "name": "Ivan",
                                "id": 30
                            }
                            """)
                    .build();
//            session.persist(user);
//            session.merge(user);
//            session.remove(user);
            var user1 = session.get(User.class, "foretruffUA");
//            user1.setLastname("Maksimov222");
            System.out.println(session.isDirty());
            session.flush();


//            var user2 = session.get(User.class, "foretruffUA");

//            session.evict(user1);
//            session.clear(); // как удалить хеш из персистер
//            session.close();

            System.out.println(user1);
            session.getTransaction().commit();
        }
    }
}
