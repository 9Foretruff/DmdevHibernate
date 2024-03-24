package comm.foretruff;

import comm.foretruff.dao.CompanyRepository;
import comm.foretruff.dao.PaymentRepository;
import comm.foretruff.dao.UserRepository;
import comm.foretruff.dto.UserCreateDto;
import comm.foretruff.entity.PersonalInfo;
import comm.foretruff.entity.Role;
import comm.foretruff.interceptor.TransactionInterceptor;
import comm.foretruff.mapper.CompanyReadMapper;
import comm.foretruff.mapper.UserCreateMapper;
import comm.foretruff.mapper.UserReadMapper;
import comm.foretruff.service.UserService;
import comm.foretruff.util.HibernateUtil;
import jakarta.transaction.Transactional;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

public class HibernateRunner9 {
    @Transactional
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session = (Session) Proxy.newProxyInstance(HibernateRunner9.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)
            );
//            session.beginTransaction();

            var paymentRepository = new PaymentRepository(session);
            var userRepository = new UserRepository(session);
            var companyRepository = new CompanyRepository(session);

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);
            var userCreateMapper = new UserCreateMapper(companyRepository);

            var transactionInterceptor = new TransactionInterceptor(sessionFactory);

//            var userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            var userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            userService.findById(2L).ifPresent(System.out::println);

            var userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Maksim")
                            .lastname("Roki")
                            .birthDate(LocalDate.now())
                            .build(),
                    "maksroki20061",
                    null,
                    Role.USER,
                    2
            );

            userService.create(userCreateDto);

        }
    }
}
