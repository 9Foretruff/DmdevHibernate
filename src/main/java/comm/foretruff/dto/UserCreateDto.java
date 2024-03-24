package comm.foretruff.dto;

import comm.foretruff.entity.PersonalInfo;
import comm.foretruff.entity.Role;
import comm.foretruff.validation.UpdateCheck;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@Valid PersonalInfo personalInfo,
                            @NotNull String username,
                            String info,
                            @NotNull(groups = UpdateCheck.class)
                            Role role,
                            Integer companyId) {
}
