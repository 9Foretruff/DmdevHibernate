package comm.foretruff.dto;

import comm.foretruff.entity.PersonalInfo;
import comm.foretruff.entity.Role;

public record UserReadDto(Long id,
                          PersonalInfo personalInfo,
                          String username,
                          String info,
                          Role role,
                          CompanyReadDto company) {

}
