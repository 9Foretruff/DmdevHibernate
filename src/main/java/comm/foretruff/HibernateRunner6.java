package comm.foretruff;

import comm.foretruff.entity.Payment;
import comm.foretruff.util.HibernateUtil;
import org.hibernate.envers.AuditReaderFactory;

import java.util.Date;

public class HibernateRunner6 {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 10);

            session.getTransaction().commit();
        }

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();

            var auditReader = AuditReaderFactory.get(session);
//            auditReader.find(Payment.class, 1L, 1);
            var oldPayment = auditReader.find(Payment.class, 1L, new Date(1711108760018L));

            session.getTransaction().commit();
        }
    }
}
