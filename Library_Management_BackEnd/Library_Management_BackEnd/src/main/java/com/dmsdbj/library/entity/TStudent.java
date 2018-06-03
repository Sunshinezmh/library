package com.dmsdbj.library.entity;


import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_student")
@NamedQueries({
        @NamedQuery(name = "TStudent.findStudentByCondition",query = "select o from TStudent o where (o.classesName " +
                "like:conditions or o.name like:conditions or o.code like:conditions or o.professionName " +
                "like:conditions or o.telNum like:conditions or o.email like:conditions )and o.isDelete = 0"),
        @NamedQuery(name = "TStudent.findParentCollege",query = "select DISTINCT o.professionName,o.classesName from TStudent o where o.isDelete=0"),
        @NamedQuery(name="TStudent.getStudent",query = "select o from TStudent o where o.id =:userId and o.isDelete = 0"),
        @NamedQuery(name = "TStudent.getAllStudent",query = "select o from TStudent o where o.isDelete = 0 "),
        @NamedQuery(name = "TStudent.getStudentBystudentCode",query = "SELECT o FROM TStudent o WHERE o.isDelete =0 AND o.code =:studentCode"),
        //根据学号集合查询学生信息
        @NamedQuery(name = "TStudent.getStudentByCodeList",query = "SELECT o FROM TStudent o WHERE o.isDelete =0 AND o.code in :codeList")
})
@ExcelModelConfig
public class TStudent extends AbstractAuditingEntity implements Serializable {

    @Column(name = "account_address", table = "t_student", nullable = false, length = 50)
    @Basic(optional = false)
    private String accountAddress;


    @Column(name = "code", table = "t_student", nullable = false, length = 22)
    @Basic(optional = false)
    @Lang(value = "学号")
    @InputTextConfig(nullAble=false)
    private String code;

    @Column(name = "remark", table = "t_student")
    @Basic
    private String remark;

    @Column(name = "entrance_date", table = "t_student", nullable = false, length = 22)
    @Basic(optional = false)
    private Date entranceDate;

    @Column(name = "graduated_school", table = "t_student")
    @Basic
    @Lang(value = "学院")
    @InputTextConfig(nullAble=false)
    private String graduateSchool;

    @Column(name = "identity_card_id", table = "t_student")
    @Basic
    private String identityCardId;

    @Column(name = "native_placenative_place", table = "t_student")
    @Basic
    private String nativePlacenativePlace;

    @Column(name = "pictrue", table = "t_student")
    @Basic
    private String pictrue;

    @Column(name = "political_status", table = "t_student", unique = true)
    @Basic
    private String politicalStatus;

    @Column(name = "sex", table = "t_student")
    @Basic
    private String sex;

    @Column(name = "status", table = "t_student")
    @Basic
    private String status;

    @Column(name = "tel_num", table = "t_student")
    @Basic
    @Lang(value = "手机号")
    @InputTextConfig(nullAble=false)
    private String telNum;

    @Column(name = "is_graduate", table = "t_student" ,nullable = true)
    @Basic
    private Integer isGraduate;

    @Column(name = "name", table = "t_student")
    @Basic
    @Lang(value = "姓名")
    @InputTextConfig(nullAble=false)
    private String name;

    @Column(name = "nation", table = "t_student")
    @Basic
    private String nation;

    @Column(name = "classes_name", table = "t_student")
    @Basic
    @Lang(value = "班级")
    @InputTextConfig(nullAble=false)
    private String classesName;



    @Column(name = "profession_name", table = "t_student")
    @Basic
    @Lang(value = "专业")
    @InputTextConfig(nullAble=false)
    private String professionName;


    @Column(name = "email", table = "t_student")
    @Basic
    @Lang(value = "Email地址")
    @InputTextConfig(nullAble=false)
    private String email;

    @Column(name = "no_graduate_reason", table = "t_student")
    @Basic
    private String noGraduateReason;

    @Column(name = "original_place", table = "t_student")
    @Basic
    private String originalPlace;


    @Column(name = "used_name", table = "t_student")
    @Basic
    private String usedName;

    @Column(name = "is_registry", table = "t_student", nullable = false, length = 50)
    @Basic(optional = false)
    private Integer isRegistry;

    @Column(name = "pay_number", table = "t_student", nullable = false, length = 50)
    @Basic(optional = false)
    private String payNumber;

    @Column(name = "room_id", table = "t_student", nullable = false, length = 50)
    @Basic(optional = false)
    private String roomId;

    public String getAlluserId() {
        return alluserId;
    }

    public void setAlluserId(String alluserId) {
        this.alluserId = alluserId;
    }

    @Column(name = "alluser_id", table = "t_student", nullable = false, length = 50)
    @Basic(optional = false)
    private String alluserId;



    public String getAccountAddress() {
        return this.accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }



    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEntranceDate() {
        return this.entranceDate;
    }

    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }

    public String getGraduateSchool() {
        return this.graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getIdentityCardId() {
        return this.identityCardId;
    }

    public void setIdentityCardId(String identityCardId) {
        this.identityCardId = identityCardId;
    }

    public String getNativePlacenativePlace() {
        return this.nativePlacenativePlace;
    }

    public void setNativePlacenativePlace(String nativePlacenativePlace) {
        this.nativePlacenativePlace = nativePlacenativePlace;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getPictrue() {
        return this.pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public String getPoliticalStatus() {
        return this.politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelNum() {
        return this.telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public Integer getIsGraduate() {
        return this.isGraduate;
    }

    public void setIsGraduate(Integer isGraduate) {
        this.isGraduate = isGraduate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return this.nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoGraduateReason() {
        return this.noGraduateReason;
    }

    public void setNoGraduateReason(String noGraduateReason) {
        this.noGraduateReason = noGraduateReason;
    }

    public String getOriginalPlace() {
        return this.originalPlace;
    }

    public void setOriginalPlace(String originalPlace) {
        this.originalPlace = originalPlace;
    }


    public String getUsedName() {
        return this.usedName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    public Integer getIsRegistry() {
        return this.isRegistry;
    }

    public void setIsRegistry(Integer isRegistry) {
        this.isRegistry = isRegistry;
    }

    public String getPayNumber() {
        return this.payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

}