package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_donate")

@NamedQueries({

        @NamedQuery(name ="TDonate.getDonate",query = "SELECT a FROM  TDonate a WHERE a.userId=:userId ")
        ,
        @NamedQuery(name ="TDonate.findByStatus",query = "SELECT o FROM  TDonate o WHERE  o.auditStatus=:status ")
})

public class TDonate extends AbstractAuditingEntity implements Serializable{


    @Column(name = "user_Id", table = "t_donate", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    @Column(name = "audit_reason", table = "t_donate", nullable = false)
    @Basic(optional = false)
    private String auditReason;

    @Column(name = "audit_status", table = "t_donate", nullable = false, length = 40)
    @Basic(optional = false)
    private String auditStatus;

    @Column(name = "autor", table = "t_donate", nullable = false, length = 50)
    @Basic(optional = false)
    private String autor;

    @Column(name = "don_count", table = "t_donate", nullable = false, length = 2147483647)
    @Lob
    @Basic(optional = false)
    private String donCount;

    @Column(name = "don_money", table = "t_donate", precision = 22)
    @Basic
    private Double donMoney;

    @Column(name = "don_type", table = "t_donate", nullable = false, length = 10)
    @Basic(optional = false)
    private String donType;

    @Column(name = "donate_name", table = "t_donate", nullable = false, length = 40)
    @Basic(optional = false)
    private String donateName;

    @Column(name = "donater_type", table = "t_donate", nullable = false, length = 10)
    @Basic(optional = false)
    private String donaterType;

    @Column(name = "isbn", table = "t_donate", nullable = false, length = 13)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "name", table = "t_donate", nullable = false, length = 40)
    @Basic(optional = false)
    private String name;


    @Column(name = "publish_time", table = "t_donate", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;

    @Column(name = "remark", table = "t_donate")
    @Basic
    private String remark;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuditReason() {
        return this.auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDonCount() {
        return this.donCount;
    }

    public void setDonCount(String donCount) {
        this.donCount = donCount;
    }

    public Double getDonMoney() {
        return this.donMoney;
    }

    public void setDonMoney(Double donMoney) {
        this.donMoney = donMoney;
    }

    public String getDonType() {
        return this.donType;
    }

    public void setDonType(String donType) {
        this.donType = donType;
    }

    public String getDonateName() {
        return this.donateName;
    }

    public void setDonateName(String donateName) {
        this.donateName = donateName;
    }

    public String getDonaterType() {
        return this.donaterType;
    }

    public void setDonaterType(String donaterType) {
        this.donaterType = donaterType;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

  
}