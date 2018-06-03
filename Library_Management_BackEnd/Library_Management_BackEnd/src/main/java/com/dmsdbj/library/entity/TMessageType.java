package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_message_type")
public class TMessageType extends AbstractAuditingEntity implements Serializable {


    @Column(name = "name", table = "t_message_type", nullable = false, length = 40)
    @Basic(optional = false)
    private String name;

    @Column(name = "PID", table = "t_message_type", nullable = false, length = 22)
    @Basic(optional = false)
    private String pid;

    @Column(name = "remark", table = "t_message_type")
    @Basic
    private String remark;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}