package bf.plainte.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Ouagadougou")
    private Date createdDate = new Date();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Ouagadougou")
    private Date lastModifiedDate = new Date();

    private boolean deleted = false;

    @PrePersist
    void beforePersist() {
        if (this.createdBy == null) {
            this.createdBy = "anonymous";
        }
    }
}
