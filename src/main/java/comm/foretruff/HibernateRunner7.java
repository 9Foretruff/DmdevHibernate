package comm.foretruff;

import comm.foretruff.entity.User;
import comm.foretruff.util.HibernateUtil;

public class HibernateRunner7 {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            User user = null;
            try (var session = sessionFactory.openSession()) {

                session.beginTransaction();

                user = session.find(User.class, 1L);
                user.getCompany().getName();
                user.getUserChats().size();
                var user1 = session.find(User.class, 1L);

                session.getTransaction().commit();
            }
            try (var session = sessionFactory.openSession()) {

                session.beginTransaction();

                var user2 = session.find(User.class, 1L);
                user2.getCompany().getName();
                user2.getUserChats().size();

                session.getTransaction().commit();
            }
        }
    }
}
