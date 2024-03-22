package comm.foretruff.dao;

import comm.foretruff.entity.Company;
import org.hibernate.SessionFactory;

public class CompanyRepository extends RepositoryBase<Integer, Company> {
    public CompanyRepository(SessionFactory sessionFactory, Class<Company> clazz) {
        super(sessionFactory, clazz);
    }
}