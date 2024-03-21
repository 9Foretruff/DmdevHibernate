package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.util.HibernateUtil;
import jakarta.transaction.Transactional;

public class HibernateRunner5 {

    @Transactional
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

//            session.setDefaultReadOnly(true);
//            session.setReadOnly(User.class, true);
//            session.beginTransaction();
//
//            session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate();

            var payment2 = session.find(Payment.class, 1111L);
            payment2.setAmount(payment2.getAmount() + 20);
            session.persist(payment2);
            session.flush();
//
//            session.getTransaction().commit();
        }
    }

}
