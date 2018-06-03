package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

;

@Entity
@Table(name = "t_area_distribution")
public class TAreaDistribution extends AbstractAuditingEntity implements Serializable{


    @Column(name = "name", table = "t_area_distribution", nullable = false, length = 40)
    @Basic(optional = false)
    private String name;

    @Column(name = "place", table = "t_area_distribution", nullable = false)
    @Basic(optional = false)
    private double place;

    @Column(name = "remark", table = "t_area_distribution")
    @Basic
    private String remark;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPlace() {
        return this.place;
    }

    public void setPlace(double place) {
        this.place = place;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}