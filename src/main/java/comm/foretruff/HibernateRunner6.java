package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.util.HibernateUtil;
import comm.foretruff.util.TestDataImporter;

public class HibernateRunner6 {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 10);

            session.getTransaction().commit();
        }
    }
}
