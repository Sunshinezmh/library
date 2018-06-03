package com.dmsdbj.library.entity;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "t_opinion")
@NamedQueries({@NamedQuery(name="TOpinion.findOpinionByUserId",query="SELECT o FROM TOpinion o WHERE o.userId=:userId and o.isDelete=0"),
               @NamedQuery(name="TOpinion.findOpinionByStatus",query="SELECT o FROM TOpinion o WHERE o.status=:status and o.isDelete=0")})
public class TOpinion extends AbstractAuditingEntity implements Serializable {

  
    @Column(name = "user_id", table = "t_opinion", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    @Column(name = "content", table = "t_opinion", nullable = false)
    @Basic(optional = false)
    private String content;

    @Column(name = "status", table = "t_opinion", nullable = false, length = 30)
    @Basic(optional = false)
    private String status;

   

    

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
}