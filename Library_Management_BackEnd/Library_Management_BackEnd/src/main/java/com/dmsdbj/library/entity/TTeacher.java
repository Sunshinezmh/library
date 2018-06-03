package com.dmsdbj.library.entity;

import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_teacher")
@NamedQueries({
        @NamedQuery(name="TTeacher.findTeacherByCondition",query="select o from TTeacher o where (o.code like:condition or o.name like:condition or o.sex like:condition) and o.isDelete=0"),
        @NamedQuery(name="TTeacher.findAllTeacher" ,query="select o from TTeacher o where o.isDelete =0"),
        @NamedQuery(name="TTeacher.findTeacherByCode",query="select o from TTeacher o where o.code =:teachCode and o.isDelete=0")
})
@ExcelModelConfig
public class TTeacher extends AbstractAuditingEntity implements Serializable {

//    @Column(name = "ID", table = "t_teacher", nullable = false, length = 22)
//    @Id
//    @Lang(value = "序号")
//    @InputIntConfig(nullAble=false)


    @Column(name = "account_address", table = "t_teacher", nullable = false, length = 50)
    @Basic(optional = false)
    private String accountAddress;

    @Column(name = "operator", table = "t_teacher", nullable = false, length = 22)
    @Basic(optional = false)
    private String operator;

    @Column(name = "code", table = "t_teacher", nullable = false, length = 22)
    @Basic(optional = false)
    
    
    @Lang(value = "教工号")
    @InputTextConfig(nullAble=false)
    private String code;

    @Column(name = "remark", table = "t_teacher")
    @Basic
    @Lang(value = "备注")
    @InputTextConfig(nullAble=false)
    private String remark;

    @Column(name = "entrance_date", table = "t_teacher", nullable = false, length = 22)
    @Basic(optional = false)
    private String entranceDate;

    @Column(name = "graduate_school", table = "t_teacher")
    @Basic
    private String graduateSchool;

    @Column(name = "identity_card_id", table = "t_teacher")
    @Basic
    private String identityCardId;

    @Column(name = "name", table = "t_teacher" )
    @Basic
    
    @InputTextConfig(nullAble=false)
    @Lang(value = "姓名")
    private String name;

    @Column(name = "native_placenative_place", table = "t_teacher")
    @Basic
    private String nativePlacenativePlace;

    @Column(name = "pictrue", table = "t_teacher")
    @Basic
    private String pictrue;

    @Column(name = "political_status", table = "t_teacher", unique = true)
    @Basic
    private String politicalStatus;

    @Column(name = "sex", table = "t_teacher")
    @Basic
    @InputTextConfig(nullAble=false)
    @Lang(value = "性别")
    private String sex;

    @Column(name = "status", table = "t_teacher")
    @Basic
    private String status;

    @Column(name = "tel_num", table = "t_teacher")
    @Basic
    private String telNum;

    @Column(name = "degree", table = "t_teacher")
    @Basic
    private String degree;

    @Column(name = "education", table = "t_teacher")
    @Basic
    private String education;

    @Column(name = "is_external", table = "t_teacher")
    @Basic
    private String isExternal;

    @Column(name = "is_teacher", table = "t_teacher")
    @Basic
    private String isTeacher;

    @Column(name = "now_address", table = "t_teacher")
    @Basic
    private String nowAddress;

    @Column(name = "jobTitle_name", table = "t_teacher")
    @Basic
    @InputTextConfig(nullAble=false)
	@Lang(value = "职称")
    private String jobTitlename;

    @Column(name = "tutuor_type", table = "t_teacher")
    @Basic
    private String tutuorType;

    @Column(name = "is_tutor", table = "t_teacher")
    @Basic
    private String isTutor;

    @Column(name = "brief", table = "t_teacher")
    @Basic
    private String brief;

    @Column(name = "achivement", table = "t_teacher")
    @Basic
    private String achivement;

    @Column(name = "check_or_not", table = "t_teacher")
    @Basic
    private String checkOrNot;

    @Column(name = "duty_name", table = "t_teacher")
    @Basic
    @InputTextConfig(nullAble=false)
    @Lang(value = "职务")
    private String dutyname;

    @Column(name = "email", table = "t_teacher")
    @Basic
    private String email;

    @Column(name = "nation", table = "t_teacher")
    @Basic
    private String nation;

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEmail() {
        return email;
    }


    public String getJobTitlename() {
        return jobTitlename;
    }

    public void setJobTitlename(String jobTitlename) {
        this.jobTitlename = jobTitlename;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountAddress(){
	return this.accountAddress;
    }

    public void setAccountAddress(String accountAddress){
	this.accountAddress = accountAddress;
    }






    public String getOperator(){
	return this.operator;
    }

    public void setOperator(String operator){
	this.operator = operator;
    }

    public String getCode(){
	return this.code;
    }

    public void setCode(String code){
	this.code = code;
    }

    public String getRemark(){
	return this.remark;
    }

    public void setRemark(String remark){
	this.remark = remark;
    }

    public String getEntranceDate(){
	return this.entranceDate;
    }

    public void setEntranceDate(String entranceDate){
	this.entranceDate = entranceDate;
    }

    public String getGraduateSchool(){
	return this.graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool){
	this.graduateSchool = graduateSchool;
    }

    public String getIdentityCardId(){
	return this.identityCardId;
    }

    public void setIdentityCardId(String identityCardId){
	this.identityCardId = identityCardId;
    }

    public String getName(){
	return this.name;
    }

    public void setName(String name){
	this.name = name;
    }

    public String getNativePlacenativePlace(){
	return this.nativePlacenativePlace;
    }

    public void setNativePlacenativePlace(String nativePlacenativePlace){
	this.nativePlacenativePlace = nativePlacenativePlace;
    }

    public String getPictrue(){
	return this.pictrue;
    }

    public void setPictrue(String pictrue){
	this.pictrue = pictrue;
    }

    public String getPoliticalStatus(){
	return this.politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus){
	this.politicalStatus = politicalStatus;
    }

    public String getSex(){
	return this.sex;
    }

    public void setSex(String sex){
	this.sex = sex;
    }

    public String getStatus(){
	return this.status;
    }

    public void setStatus(String status){
	this.status = status;
    }

    public String getTelNum(){
	return this.telNum;
    }

    public void setTelNum(String telNum){
	this.telNum = telNum;
    }

    public String getDegree(){
	return this.degree;
    }

    public void setDegree(String degree){
	this.degree = degree;
    }

    public String getEducation(){
	return this.education;
    }

    public void setEducation(String education){
	this.education = education;
    }

    public String getIsExternal(){
	return this.isExternal;
    }

    public void setIsExternal(String isExternal){
	this.isExternal = isExternal;
    }

    public String getIsTeacher(){
	return this.isTeacher;
    }

    public void setIsTeacher(String isTeacher){
	this.isTeacher = isTeacher;
    }

    public String getNowAddress(){
	return this.nowAddress;
    }

    public void setNowAddress(String nowAddress){
	this.nowAddress = nowAddress;
    }


    public String getTutuorType(){
	return this.tutuorType;
    }

    public void setTutuorType(String tutuorType){
	this.tutuorType = tutuorType;
    }

    public String getIsTutor(){
	return this.isTutor;
    }

    public void setIsTutor(String isTutor){
	this.isTutor = isTutor;
    }

    public String getBrief(){
	return this.brief;
    }

    public void setBrief(String brief){
	this.brief = brief;
    }

    public String getAchivement(){
	return this.achivement;
    }

    public void setAchivement(String achivement){
	this.achivement = achivement;
    }

    public String getCheckOrNot(){
	return this.checkOrNot;
    }

    public void setCheckOrNot(String checkOrNot){
	this.checkOrNot = checkOrNot;
    }


    public String getdutyname(){
	return this.dutyname;
    }

    public void setdutyname(String dutyname){
	this.dutyname = dutyname;
    }

}