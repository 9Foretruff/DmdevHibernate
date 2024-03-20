package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.util.HibernateUtil;
import jakarta.transaction.Transactional;

public class HibernateRunner4 {
    @Transactional
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                var payment1 = session.find(Payment.class, 1);
                var payment2 = session.find(Payment.class, 2);

                session.getTransaction().commit();
            } catch (Exception exception) {
                session.getTransaction().rollback();
                throw exception;
            }
        }
    }
}
