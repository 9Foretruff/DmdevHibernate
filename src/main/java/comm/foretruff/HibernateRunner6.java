package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.entity.User;
import comm.foretruff.util.HibernateUtil;
import comm.foretruff.util.TestDataImporter;

public class HibernateRunner6 {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            var payment = Payment.builder()
                    .amount(1500)
                    .receiver(session.get(User.class, 3L))
                    .build();
            session.persist(payment);
            payment.setAmount(payment.getAmount() + 1000);

            session.getTransaction().commit();
        }
    }
}
