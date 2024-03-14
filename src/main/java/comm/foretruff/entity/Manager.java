package comm.foretruff.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@SuperBuilder
@Table(name = "manager")
@DiscriminatorValue("manager")
public class Manager extends User {
    private String projectName;

    @Builder
    public Manager(Long id, PersonalInfo personalInfo, String username, Role role, String info, Company company, Profile profile, List<UserChat> userChats, String projectName) {
        super(id, personalInfo, username, role, info, company, profile, userChats);
        this.projectName = projectName;
    }
}
