package comm.foretruff.dao;

import comm.foretruff.entity.Company;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

public class CompanyRepository extends RepositoryBase<Integer, Company> {
    public CompanyRepository(EntityManager entityManager) {
        super(entityManager, Company.class);
    }
}