package com.foretruff;

import com.foretruff.entity.BirthDay;
import com.foretruff.entity.Company;
import com.foretruff.entity.PersonalInfo;
import com.foretruff.entity.User;
import com.foretruff.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner3 {

    public static void main(String[] args) throws SQLException {
        var company = Company.builder()
                .name("Softserve1")
                .build();

        User user1 = User.builder()
                .username("Petr1")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov1")
                        .firstname("Ivanov1")
                        .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
                        .build())
                .company(company)
                .build();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();

//                session1.persist(company);
//                session1.persist(user1);
                var user = session1.get(User.class, 2L);
                Company company1 = user.getCompany();
                var name = company1.getName();

                var unproxy = Hibernate.unproxy(company1);
                
                transaction.commit();
            }
        }
    }
}
