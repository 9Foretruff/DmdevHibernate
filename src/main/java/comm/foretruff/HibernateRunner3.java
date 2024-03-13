package comm.foretruff;

import comm.foretruff.entity.BirthDay;
import comm.foretruff.entity.Company;
import comm.foretruff.entity.PersonalInfo;
import comm.foretruff.entity.User;
import comm.foretruff.entity.UserChat;
import comm.foretruff.util.HibernateUtil;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner3 {

    public static void main(String[] args) throws SQLException {
        var company = Company.builder()
                .name("Softservee")
                .build();

//        User user1 = User.builder()
//                .username("Petrooo1")
//                .personalInfo(PersonalInfo.builder()
//                        .lastname("Petrov111")
//                        .firstname("Ivanov1")
//                        .birthDate(new BirthDay(LocalDate.of(2006, 1, 3)))
//                        .build())
//                .company(company)
//                .build();

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(company);
//            session.persist(user1);

            session.getTransaction().commit();
        }
    }
}
