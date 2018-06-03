package com.dmsdbj.library.entity;


import javax.persistence.*;

@Entity
@Table(name = "t_setting")
@NamedQueries({@NamedQuery(name = "getAlltSettings",query="select o from TSetting o"),
        @NamedQuery(name="findReservationCount",query="SELECT ts.reservationCount FROM TSetting ts where ts.isDelete=0"),

})
public class TSetting  extends AbstractAuditingEntity{

    @Column(name = "borrowing_number", table = "t_setting")
    @Basic
    private Integer borrowingNumber;

    @Column(name = "borrowing_time", table = "t_setting")
    @Basic
    private Integer borrowingTime;

    @Column(name = "reservation_count", table = "t_setting")
    @Basic
    private Integer reservationCount;


    @Column(name = "remark", table = "t_setting", length = 256)
    @Basic
    private String remark;

    @Column(name = "renew_number", table = "t_setting")
    @Basic
    private Integer renewNumber;

    public Integer getRenewNumber() {
        return renewNumber;
    }

    public void setRenewNumber(Integer renewNumber) {
        this.renewNumber = renewNumber;
    }
    

    public Integer getBorrowingNumber() {
        return this.borrowingNumber;
    }

    public void setBorrowingNumber(Integer borrowingNumber) {
        this.borrowingNumber = borrowingNumber;
    }


    public Integer getBorrowingTime() {
        return this.borrowingTime;
    }

    public void setBorrowingTime(Integer borrowingTime) {
        this.borrowingTime = borrowingTime;
    }

    public Integer getReservationCount() {
        return this.reservationCount;
    }

    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



}