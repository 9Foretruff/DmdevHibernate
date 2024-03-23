package comm.foretruff.mapper;

import comm.foretruff.dto.UserReadDto;
import comm.foretruff.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getPersonalInfo(),
                object.getUsername(),
                object.getInfo(),
                object.getRole(),
                Optional.of(object.getCompany())
                        .map(companyReadMapper::mapFrom)
                        .orElse(null)
        );
    }
}
