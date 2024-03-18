package comm.foretruff;

import comm.foretruff.entity.Company;
import comm.foretruff.util.HibernateUtil;

import java.sql.SQLException;

public class HibernateRunner3 {

    public static void main(String[] args) throws SQLException {
        var company = Company.builder()
                .name("Softservee11")
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


    /* example of initialization in enums
    public static void main(String[] args) {
            System.out.println(Values.A);
        }

        enum Values {
            A(1), B(2), C(3);

            static {
                System.out.println("static");
            }

            Values(int i) {
                System.out.println(i);
            }
        }
     */
}
