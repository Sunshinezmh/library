package com.dmsdbj.library.entity;

import com.dmsdbj.library.app.util.UUID.UuidUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * base abstract class for entities which will hold definitions for created,
 * last modified by and created, last modified by date.
 */
@MappedSuperclass
@EntityListeners(AuditListner.class)
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID", nullable = false, length = 22)
    @Id

    private String id;
    @Column(name = "is_delete",  updatable = true)

    private int isDelete;

    @Column(name = "create_time", nullable = true, updatable = true)

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Column(name = "operator",nullable = true, length = 50)

    private String operator;

    @Column(name = "update_time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {

       this.isDelete=isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        if (createTime==null){
            createTime=new Timestamp(System.currentTimeMillis());
        }
        this.createTime = createTime;
    }

    public String getoperator() {
        return operator;
    }
    public void setoperator(String operator) {
        this.operator = operator;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }

    public Date getUpdateTime() {
        return updateTime;
    }
    public String getId() {

        return this.id;
    }

    public void setId(String id) {

        if(StringUtils.isBlank(id)){
            id=UuidUtils.base58Uuid();
        }
        this.id=id;
    }
}
