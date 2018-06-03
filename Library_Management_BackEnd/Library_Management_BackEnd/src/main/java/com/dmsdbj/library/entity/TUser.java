package com.dmsdbj.library.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Entity
@Table(name = "tc_allusers")
@NamedQueries({
        @NamedQuery(name = "TUser.findUserByname", query = "select u from TUser u where u.name = :name")
})
public class TUser extends AbstractAuditingEntity implements Serializable {

    @Column(name = "user_code", table = "tc_allusers", length = 20)
    @Basic(optional = true)
    private String userCode;

    @Column(name = "password", table = "tc_allusers", nullable = false, length = 8)
    @Basic(optional = true)
    private String pwd;

    @Column(name = "user_name", table = "tc_allusers", length = 20)
    @Basic(optional = true)
    private String name;

    @Column(name = "school_no", table = "tc_allusers", length = 20)
    @Basic(optional = true)
    private String schoolNo;

    @Column(name = "email", table = "tc_allusers", length = 50)
    @Basic(optional = false)
    @Pattern(regexp = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", message = "{invalid.email}")
    private String email;

    @Column(name = "tel_num", table = "tc_allusers", length = 11)
    @Basic(optional = false)
    @Pattern(regexp = "0?(13|14|15|18)[0-9]{9}", message = "{invalid.phonenumber}")
    private String tel;

    @Column(name = "remark", table = "tc_allusers")
    @Basic(optional = false)
    private String remark;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}

