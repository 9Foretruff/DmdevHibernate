package comm.foretruff.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "public", name = "profile")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    private String street;
    private String language;

    public void setUser(User user){
        this.id = user.getId();
        this.user = user;
        user.setProfile(this);
    }

}
