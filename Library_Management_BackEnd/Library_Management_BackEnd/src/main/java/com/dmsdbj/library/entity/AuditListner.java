package com.dmsdbj.library.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Entity listener class for audit info
 */
public class AuditListner {


    @PrePersist
    void onCreate(AbstractAuditingEntity entity) {
        entity.setCreateTime(new Date());

    }

    @PreUpdate
    void onUpdate(AbstractAuditingEntity entity) {
        entity.setUpdateTime(new Date());

    }
}
