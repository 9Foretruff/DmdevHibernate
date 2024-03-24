package comm.foretruff.service;

import comm.foretruff.dao.UserRepository;
import comm.foretruff.dto.UserCreateDto;
import comm.foretruff.dto.UserReadDto;
import comm.foretruff.entity.User;
import comm.foretruff.mapper.Mapper;
import comm.foretruff.mapper.UserCreateMapper;
import comm.foretruff.mapper.UserReadMapper;
import comm.foretruff.validation.UpdateCheck;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;


//    @Transactional
//    public Long create(UserCreateDto userDto) {
//        // validation
//        // map
//        var userEntity = userCreateMapper.mapFrom(userDto);
//        return userRepository.save(userEntity).getId();
//    }


    @Transactional
    public Long create(UserCreateDto userDto) {
        @Cleanup
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(userDto, UpdateCheck.class);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        User user = userCreateMapper.mapFrom(userDto);
        return userRepository.save(user).getId();
    }

    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        return findById(id, userReadMapper);
    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<User, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(),
                userRepository.getEntityManager().getEntityGraph("WithCompany")
        );
        return userRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }

}
