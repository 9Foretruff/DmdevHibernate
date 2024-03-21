package comm.foretruff.entity;

import comm.foretruff.listener.AuditDatesListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor // или PUBLIC
@MappedSuperclass
@SuperBuilder
//@EntityListeners(AuditDatesListener.class)
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "created_by")
    private String createdBy;

    private Instant updatedAt;
    private String updatedBy;

//    @PrePersist
//    public void prePersist() {
//        setCreatedAt(Instant.now());
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        setUpdatedAt(Instant.now());
//    }

}
