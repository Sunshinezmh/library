package com.dmsdbj.library.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_reservation")
@NamedQueries({
        @NamedQuery(name="TReservation.findReserveByStatus",query="SELECT r FROM TReservation r WHERE r.status=:status and r.isDelete=:isDelete"),

        //查询可预约次数
        @NamedQuery(name="TSetting.findReservationCount",query="SELECT ts.reservationCount FROM TSetting ts where ts.isDelete=0"),

        //查询用户已预约次数
        @NamedQuery(name="TReservation.findReservationCountByUserId",query = "select count(tr) from TReservation tr where tr.isDelete=0 and tr.status=0 and tr.userId=:userId"),

        //查询用户是否已预约对应图书
        @NamedQuery(name="TReservation.findReservationByUserIdIsbn",query = "select tr from TReservation tr where tr.isDelete=0 and tr.status=0 and tr.userId =:userId and tr.isbn =:isbn and tr.location =:location"),

        //批量查询用户已预约次数
        @NamedQuery(name="TReservation.SelectReservationCountByUserId",query = "select count(tr) from TReservation tr where tr.isDelete=0 and tr.status=0 and tr.userId in:list"),

        //批量查询用户已预约次数及用户ID
        @NamedQuery(name="TReservation.SelectCountByUserIdList",query = "SELECT tr.userId,count(tr) AS count FROM TReservation tr  WHERE tr.userId IN :list  AND tr.isDelete = 0 and tr.status=0  GROUP BY tr.userId ")

})
public class TReservation  extends AbstractAuditingEntity implements Serializable {

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Column(name = "isbn", table = "t_reservation", nullable = false, length = 22)
    @Basic(optional = false)
    private String isbn;

    @Column(name = "Is_announce", table = "t_reservation", nullable = false)
    @Basic(optional = false)
    private short isannounce;

    @Column(name = "remark", table = "t_reservation")
    @Basic
    private String remark;

    @Column(name = "status", table = "t_reservation", nullable = false, length = 10)
    @Basic(optional = false)
    //完成 0 取消 1
    private int status;

    @Column(name = "user_id", table = "t_reservation", nullable = false, length = 22)
    @Basic(optional = false)
    private String userId;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "location", table = "t_reservation", nullable = false, length =18)
    @Basic(optional = false)
    private String location;











    public short getIsannounce() {
        return this.isannounce;
    }

    public void setIsannounce(short isannounce) {
        this.isannounce = isannounce;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}