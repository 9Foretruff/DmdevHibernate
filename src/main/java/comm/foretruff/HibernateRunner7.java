package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.entity.User;
import comm.foretruff.util.HibernateUtil;
import org.hibernate.query.Query;

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

                var paymentList = session.createQuery("select p from Payment p where receiver.id = :userId", Payment.class).
                        setParameter("userId", 1L)
                        .setCacheable(true)
//                        .setCacheRegion("queries")
                        .list();

//                System.out.println(sessionFactory.getStatistics());
                System.out.println(sessionFactory.getStatistics().getCacheRegionStatistics("userCache"));
                session.getTransaction().commit();
            }
            try (var session = sessionFactory.openSession()) {

                session.beginTransaction();

                var user2 = session.find(User.class, 1L);
                user2.getCompany().getName();
                user2.getUserChats().size();

                var paymentList = session.createQuery("select p from Payment p where receiver.id = :userId", Payment.class).
                        setParameter("userId", 1L)
                        .setCacheable(true) //!!!
//                        .setCacheRegion("queries")
                        .list();
                System.out.println(sessionFactory.getStatistics().getCacheRegionStatistics("userCache"));
                session.getTransaction().commit();
            }
        }
    }
}
