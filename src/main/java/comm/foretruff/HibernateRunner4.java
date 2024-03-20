package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.util.HibernateUtil;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

public class HibernateRunner4 {
    @Transactional
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

//            TestDataImporter.importData(sessionFactory);

            session.beginTransaction();

            var payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC);
            // OPTIMISTIC_FORCE_INCREMENT при любых операциях делает инкримент версии
            payment.setAmount(payment.getAmount() + 10);

            session.getTransaction().commit();
        }
    }
}

