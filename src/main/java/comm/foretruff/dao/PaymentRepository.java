package comm.foretruff.dao;

import comm.foretruff.entity.Payment;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;


public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(entityManager, Payment.class);
    }

}
