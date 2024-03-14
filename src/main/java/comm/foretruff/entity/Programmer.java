package comm.foretruff.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "programmer")
//@DiscriminatorValue("programmer")
public class Programmer extends User {
    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Long id, PersonalInfo personalInfo, String username, Role role, String info, Company company, Profile profile, List<UserChat> userChats, Language language) {
        super(id, personalInfo, username, role, info, company, profile, userChats);
        this.language = language;
    }
}