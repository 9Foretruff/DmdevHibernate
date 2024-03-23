package comm.foretruff;

import comm.foretruff.dao.PaymentRepository;
import comm.foretruff.util.HibernateUtil;
import org.hibernate.Session;

import java.lang.reflect.Proxy;

public class HibernateRunner8 {
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {


            var session = (Session) Proxy.newProxyInstance(HibernateRunner8.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)
            );

            session.beginTransaction();

            var paymentRepository = new PaymentRepository(session);
            paymentRepository.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();

            session.beginTransaction();

            var paymentRepository1 = new PaymentRepository(session);
            paymentRepository1.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();

        }
    }
}
