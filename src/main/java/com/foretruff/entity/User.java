package com.foretruff.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
//POJO

@Getter
@Setter
@ToString(exclude = "company")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
@Access(AccessType.FIELD)
public class User { // в ентити нельзя делать final поля

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @EmbeddedId
//    @Embedded
//    @AttributeOverride(name = "birthdate", column = @Column(name = "birth_date"))
    @AttributeOverride(name = "birthdate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username; // должен быть Serializable

    //    @Transient
    @Enumerated(EnumType.STRING)
    private Role role;

    //    JsonStringType
    //    @Type(JsonBinaryType.class) -- в hibernate 6 не работате
    @JdbcTypeCode(SqlTypes.JSON)
    private String info;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "company_id") //
    private Company company;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getUsername() != null && Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}


//private String firstname;
//private String lastname;
//
//@Convert(converter = BirthDayConvertor.class)
//@Column(name = "birth_date")
//private BirthDay birthDate;


//@Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
//@SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
//private Long id;
