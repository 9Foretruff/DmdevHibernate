package comm.foretruff;

import comm.foretruff.entity.User;
import comm.foretruff.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.enableFetchProfile("withCompany");

            var user = session.get(User.class, 1L);
            System.out.println(user.getCompany().getName());
//            System.out.println(user.getPayments().size());
//            var users = session.createQuery(
//                    "from User", User.class)
////                    .setMaxResults(5)
//                    .list();
//            users.forEach(user -> System.out.println(user.getPayments().size()));
            session.getTransaction().commit();
        }
    }
}
