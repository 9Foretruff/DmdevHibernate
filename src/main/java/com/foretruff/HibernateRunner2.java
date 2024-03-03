package com.foretruff;

import com.foretruff.entity.BirthDay;
import com.foretruff.entity.PersonalInfo;
import com.foretruff.entity.User;
import com.foretruff.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner2 {
//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner2.class);

    public static void main(String[] args) throws SQLException {
        User user1 = User.builder()
                .username("Petr")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov1")
                        .firstname("Ivanov1")
                        .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
                        .build())
                .build();

        log.info("User entity is in transient state , object: {}", user1);

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                log.trace("User is in persistent state: {} , session: {}", user1, transaction);

                session1.merge(user1);

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
            try (var session = sessionFactory.openSession()) {
                var key = PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Ivanov")
                        .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
                        .build();
                var user = session.get(User.class, key);
                System.out.println(user);
            }
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
