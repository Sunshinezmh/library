package com.dmsdbj.library.entity;


import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/14.
 */
//Tcheck.getCheckinfo
@Entity
@Table(name = "t_check")

@NamedQueries(value = {

        @NamedQuery(name = "TCheck.getCheckinfo", query = "SELECT o FROM TCheck o WHERE  o.isDelete=0")
})
public class TCheck extends AbstractAuditingEntity implements Serializable{

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Column(name = "isbn",table = "t_check",nullable = false,length =18 )
   @Basic(optional = false)
    private String isbn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name",table = "t_check",nullable = false,length =50 )
    @Basic(optional = false)
    private String name;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "location",table = "t_check",nullable = false,length =18 )
    @Basic(optional = false)
    private String location;

    public int getUsecount() {
        return usecount;
    }

    public void setUsecount(int usecount) {
        this.usecount = usecount;
    }

    @Column(name = "use_count",table = "t_check",nullable = false,length =18 )
    @Basic(optional = false)
    private int usecount;

    public int getAddcount() {
        return addcount;
    }

    public void setAddcount(int addcount) {
        this.addcount = addcount;
    }

    @Column(name = "add_count",table = "t_check",nullable = false,length =18 )
    @Basic(optional = false)
    private int addcount;

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    @Column(name = "is_check",table = "t_check",nullable = false,length =18 )
    @Basic(optional = false)
    private String ischeck;

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    @Column(name = "checkResult",table = "t_check",nullable = false,length =18 )
    @Basic(optional = false)
    private int checkResult;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "end_time",table = "t_check",nullable = false,length =18 )
    @Basic(optional = false)
    private Date endTime;
}
