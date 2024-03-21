package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.util.HibernateUtil;
import comm.foretruff.util.TestDataImporter;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

public class HibernateRunner4 {
    @Transactional
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession();
             var session2 = sessionFactory.openSession()) {

//            TestDataImporter.importData(sessionFactory);

//            session.beginTransaction();
            session2.beginTransaction();

//            session.createQuery("select p from Payment p", Payment.class)
//                    .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
//                    .setTimeout(2)
//                    .list();

//            var payment1 = session.find(Payment.class, 1L, LockModeType.PESSIMISTIC_FORCE_INCREMENT);//, LockModeType.OPTIMISTIC
            // OPTIMISTIC_FORCE_INCREMENT при любых операциях делает инкримент версии
//            payment1.setAmount(payment1.getAmount() + 10);

            var payment2 = session2.find(Payment.class, 1L);//, LockModeType.OPTIMISTIC
            payment2.setAmount(payment2.getAmount() + 20);

            session2.getTransaction().commit();
//            session.getTransaction().commit();
        }
    }
}

