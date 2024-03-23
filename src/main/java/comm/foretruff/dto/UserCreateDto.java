package comm.foretruff.dto;

import comm.foretruff.entity.PersonalInfo;
import comm.foretruff.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo,
                            String username,
                            String info,
                            Role role,
                            Integer companyId) {
}
