package com.foretruff;

import com.foretruff.entity.PersonalInfo;
import com.foretruff.entity.User;
import com.foretruff.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class HibernateRunner2 {
//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner2.class);

    public static void main(String[] args) throws SQLException {
        User user1 = User.builder()
                .username("Petr1")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Ivanov")
                        .build())
                .build();

        log.info("User entity is in transient state , object: {}", user1);

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                log.trace("User is in persistent state: {} , session: {}", user1, transaction);

                session1.persist(user1);

                transaction.commit();
            }
            log.warn("User is in detached state: {} , session is closed {}", user1, session1);
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
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
