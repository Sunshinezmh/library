package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_off_shelf")
public class TOffShelf extends AbstractAuditingEntity implements Serializable{


    @Column(name = "book_id", table = "t_off_shelf", nullable = false, length = 22)
    @Basic(optional = false)
    private String bookId;

    @Column(name = "trace", table = "t_off_shelf", nullable = false, length = 50)
    @Basic(optional = false)
    private String trace;

    @Column(name = "remark", table = "t_off_shelf")
    @Basic
    private String remark;

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTrace() {
        return this.trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}