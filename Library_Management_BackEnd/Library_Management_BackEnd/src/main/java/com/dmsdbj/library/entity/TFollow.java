package com.dmsdbj.library.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "t_follow")
public class TFollow extends AbstractAuditingEntity implements Serializable {


    @Column(name = "follow_id", table = "t_follow", nullable = false, length = 22)
    @Basic(optional = false)
    private String followId;

    @Column(name = "unfollow", table = "t_follow")
    @Basic
    private Short unfollow;

    

    @Column(name = "user_id", table = "t_follow", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    public String getFollowId() {
        return this.followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public Short getUnfollow() {
        return this.unfollow;
    }

    public void setUnfollow(Short unfollow) {
        this.unfollow = unfollow;
    }


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}