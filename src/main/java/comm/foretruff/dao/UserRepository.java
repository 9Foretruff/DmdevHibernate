package comm.foretruff.dao;

import comm.foretruff.entity.User;
import jakarta.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User> {
    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }
    //todo
}
