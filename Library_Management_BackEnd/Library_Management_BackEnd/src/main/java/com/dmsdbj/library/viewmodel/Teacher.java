package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;
import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;

import javax.persistence.Id;
import java.util.Objects;


/**
 * Teacher
 */
@ExcelModelConfig
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public class Teacher   {
  @Id
  private String id = null;

  @Lang(value="教工号")
  private String teacherCode = null;

  @Lang(value="姓名")
  private String teachername = null;

  @Lang(value="性别")
  private String sex = null;


  @Lang(value="职务")
  private String duties = null;

  @Lang(value="职称")
  private String job = null;

  private Integer total = null;

  public Teacher id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "d290f1ee6c544b0190e6", required = true, value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }



   /**
   * Get teacherCode
   * @return teacherCode
  **/
  @ApiModelProperty(example = "12435", required = true, value = "")
  public String getTeacherCode() {
    return teacherCode;
  }

  public void setTeacherCode(String teacherCode) {
    this.teacherCode = teacherCode;
  }



   /**
   * Get teachername
   * @return teachername
  **/
  @ApiModelProperty(example = "李四", required = true, value = "")
  public String getTeachername() {
    return teachername;
  }

  public void setTeachername(String teachername) {
    this.teachername = teachername;
  }



   /**
   * Get sex
   * @return sex
  **/
  @ApiModelProperty(example = "女", required = true, value = "")
  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }




   /**
   * Get authority
   * @return authority
  **/
  @ApiModelProperty(example = "1", required = true, value = "")
//  public Integer getAuthority() {
//    return authority;
//  }

//  public void setAuthority(Integer authority) {
//    this.authority = authority;
//  }

  public Teacher duties(String duties) {
    this.duties = duties;
    return this;
  }

   /**
   * Get duties
   * @return duties
  **/
  @ApiModelProperty(example = "职称", required = true, value = "")
  public String getDuties() {
    return duties;
  }

  public void setDuties(String duties) {
    this.duties = duties;
  }

  public Teacher job(String job) {
    this.job = job;
    return this;
  }

   /**
   * Get job
   * @return job
  **/
  @ApiModelProperty(example = "职称", required = true, value = "")
  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public Teacher total(Integer total) {
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @ApiModelProperty(required = true, value = "")
  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Teacher teacher = (Teacher) o;
    return Objects.equals(this.id, teacher.id) &&
        Objects.equals(this.teacherCode, teacher.teacherCode) &&
        Objects.equals(this.teachername, teacher.teachername) &&
        Objects.equals(this.sex, teacher.sex) &&
//        Objects.equals(this.authority, teacher.authority) &&
        Objects.equals(this.duties, teacher.duties) &&
        Objects.equals(this.job, teacher.job) &&
        Objects.equals(this.total, teacher.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, teacherCode, teachername, sex, duties, job, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Teacher {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    teacherCode: ").append(toIndentedString(teacherCode)).append("\n");
    sb.append("    teachername: ").append(toIndentedString(teachername)).append("\n");
    sb.append("    sex: ").append(toIndentedString(sex)).append("\n");
//    sb.append("    authority: ").append(toIndentedString(authority)).append("\n");
    sb.append("    duties: ").append(toIndentedString(duties)).append("\n");
    sb.append("    job: ").append(toIndentedString(job)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

