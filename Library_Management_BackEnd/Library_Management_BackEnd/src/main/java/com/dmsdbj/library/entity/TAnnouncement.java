package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_announcement")
@NamedQueries({@NamedQuery(name="TAnnouncement.findAnnounceByTime",query="SELECT o FROM TAnnouncement o WHERE o.startDate < :date AND o.endDate > :date and o.isDelete=0"),
@NamedQuery(name="TAnnouncement.findByType",query="SELECT a FROM TAnnouncement a WHERE a.type=:type AND a.isDelete=0")
})
	           
public class TAnnouncement extends AbstractAuditingEntity {



    @Column(name = "title", table = "t_announcement", nullable = false, length = 80)
    @Basic(optional = false)
    private String title;

    @Column(name = "content", table = "t_announcement", nullable = false)
    @Basic(optional = false)
    private String content;

    @Column(name = "picture", table = "t_announcement", nullable = false, length = 100)
    @Basic(optional = false)
    private String picture;

    @Column(name = "start_date", table = "t_announcement", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", table = "t_announcement", nullable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "type", table = "t_announcement", nullable = false, length = 10)
    @Basic(optional = false)
    private String type;

    @Column(name = "remark", table = "t_announcement")
    @Basic
    private String remark;
    
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}