package com.foretruff;

import com.foretruff.entity.User;
import com.foretruff.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRunner2.class);

    public static void main(String[] args) throws SQLException {
        User user1 = User.builder()
                .username("Ivan818")
                .firstname("Ivan")
                .lastname("Ivanov")
                .build();

        LOGGER.info("User entity is in transient state , object: {}", user1);

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                LOGGER.trace("User is in persistent state: {} , session: {}", user1, transaction);

                session1.persist(user1);

                transaction.commit();
            }
            LOGGER.warn("User is in detached state: {} , session is closed {}", user1, session1);
//            try (var session2 = sessionFactory.openSession()) {
//                session2.beginTransaction();
//
//                user1.setFirstname("Lol");
////                session2.remove(user1);
////                var freshUser = session2.get(User.class, user1.getUsername());
////                user1.setLastname(freshUser.getFirstname());
////                session2.merge(user1);
//                session2.refresh(user1);
//
//                session2.getTransaction().commit();
//            }
        } catch (Exception exception) {
            LOGGER.error("Exception occurred", exception);
            throw exception;
        }
    }
}
