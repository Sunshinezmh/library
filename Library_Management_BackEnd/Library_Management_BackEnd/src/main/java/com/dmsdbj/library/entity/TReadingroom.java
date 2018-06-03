package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_readingroom")
public class TReadingroom extends AbstractAuditingEntity implements Serializable{

    @Column(name = "area_id", table = "t_readingroom", nullable = false, length = 22)
    @Basic(optional = false)
    private String areaId;

    @Column(name = "name", table = "t_readingroom", nullable = false, length = 40)
    @Basic(optional = false)
    private String name;

    @Column(name = "place", table = "t_readingroom", nullable = false)
    @Basic(optional = false)
    private float place;

    @Column(name = "remark", table = "t_readingroom")
    @Basic
    private String remark;

   

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPlace() {
        return this.place;
    }

    public void setPlace(float place) {
        this.place = place;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}