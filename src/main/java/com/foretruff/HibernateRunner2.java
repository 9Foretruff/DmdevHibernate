package com.foretruff;

import com.foretruff.entity.User;
import com.foretruff.util.HibernateUtil;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class HibernateRunner2 {
    public static void main(String[] args) throws SQLException {
        User user1 = User.builder()
                .username("Ivan@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .build();
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.persist(user1);

                session1.getTransaction().commit();
            }

            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user1.setFirstname("Lol");
//                session2.remove(user1);
//                var freshUser = session2.get(User.class, user1.getUsername());
//                user1.setLastname(freshUser.getFirstname());
//                session2.merge(user1);
                session2.refresh(user1);

                session2.getTransaction().commit();
            }
        }
    }
}
