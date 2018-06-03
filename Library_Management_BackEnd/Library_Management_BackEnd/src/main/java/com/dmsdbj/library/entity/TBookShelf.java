package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

;


@Entity
@Table(name = "t_book_shelf")
public class TBookShelf extends AbstractAuditingEntity implements Serializable{


    @Column(name = "place", table = "t_book_shelf", nullable = false, length = 50)
    @Basic(optional = false)
    private String place;

    @Column(name = "remark", table = "t_book_shelf")
    @Basic
    private String remark;

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}