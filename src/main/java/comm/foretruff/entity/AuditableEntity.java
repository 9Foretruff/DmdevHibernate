package comm.foretruff.entity;

import comm.foretruff.listener.AuditDatesListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@MappedSuperclass
//@Audited
@EntityListeners(AuditDatesListener.class)
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    private Instant createdAt;
    private String createdBy;

    private Instant updatedAt;
    private String updatedBy;
}
