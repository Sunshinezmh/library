package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_level")
public class TLevel extends AbstractAuditingEntity implements Serializable {


    @Column(name = "count", table = "t_level", nullable = false)
    @Basic(optional = false)
    private int count;

    @Column(name = "duration", table = "t_level", nullable = false)
    @Basic(optional = false)
    private int duration;

    @Column(name = "integral_id", table = "t_level", nullable = false, length = 22)
    @Basic(optional = false)
    private String integralId;

    @Column(name = "remark", table = "t_level")
    @Basic
    private String remark;

    @Column(name = "times", table = "t_level", nullable = false)
    @Basic(optional = false)
    private int times;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIntegralId() {
        return this.integralId;
    }

    public void setIntegralId(String integralId) {
        this.integralId = integralId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTimes() {
        return this.times;
    }

    public void setTimes(int times) {
        this.times = times;
    }


}