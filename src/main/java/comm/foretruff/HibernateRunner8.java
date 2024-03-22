package comm.foretruff;

import comm.foretruff.dao.PaymentRepository;
import comm.foretruff.entity.Payment;
import comm.foretruff.entity.User;
import comm.foretruff.util.HibernateUtil;

public class HibernateRunner8 {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session = sessionFactory.openSession()) {

                session.beginTransaction();

                var paymentRepository = new PaymentRepository(sessionFactory);
                paymentRepository.findById(1L).ifPresent(System.out::println);

                session.getTransaction().commit();
            }
        }
    }
}
