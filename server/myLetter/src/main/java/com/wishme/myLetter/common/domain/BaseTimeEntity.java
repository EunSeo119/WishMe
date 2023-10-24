<<<<<<< HEAD:server/myLetter/src/main/java/com/wishme/common/domain/BaseTimeEntity.java
package com.wishme.common.domain;
=======
package com.wishme.myLetter.common.domain;
>>>>>>> dafc7ecb8b4e08adfa23083df27d85de71e08de6:server/myLetter/src/main/java/com/wishme/myLetter/common/domain/BaseTimeEntity.java

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {

    @CreationTimestamp
    @Column(name ="create_at",updatable = false)
    private LocalDateTime createAt;
}

