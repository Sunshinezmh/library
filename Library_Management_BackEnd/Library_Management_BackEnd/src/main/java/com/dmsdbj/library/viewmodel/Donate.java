package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * Donate
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Donate   {
  private String id = null;

  private String name = null;

  /**
   * donate Status
   */
  public enum DonateTypeEnum {
    INDIVIDUAL("个人"),
    
    TEAM("集体");

    private String value;

    DonateTypeEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private DonateTypeEnum donateType = null;

  private String donateName = null;

  private Integer count = null;

  private String reason = null;

  /**
   * 捐赠状态
   */
  public enum StatusEnum {
    PASS("已通过"),
    
    NOTPASS("未通过"),
    
    AUDIT("待审核"),
    
    ALREADYCANCEL("已取消");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private StatusEnum status = null;

  private String date = null;


  public Donate id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Donate name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "《疯狂Java讲义》", required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Donate donateType(DonateTypeEnum donateType) {
    this.donateType = donateType;
    return this;
  }

   /**
   * donate Status
   * @return donateType
  **/
  @ApiModelProperty(required = true, value = "donate Status")
  public DonateTypeEnum getDonateType() {
    return donateType;
  }

  public void setDonateType(DonateTypeEnum donateType) {
    this.donateType = donateType;
  }

  public Donate donateName(String donateName) {
    this.donateName = donateName;
    return this;
  }

   /**
   * Get donateName
   * @return donateName
  **/
  @ApiModelProperty(example = "LYN", required = true, value = "")
  public String getDonateName() {
    return donateName;
  }

  public void setDonateName(String donateName) {
    this.donateName = donateName;
  }

  public Donate count(Integer count) {
    this.count = count;
    return this;
  }

   /**
   * Get count
   * @return count
  **/
  @ApiModelProperty(example = "100", required = true, value = "")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Donate reason(String reason) {
    this.reason = reason;
    return this;
  }

   /**
   * Get reason
   * @return reason
  **/
  @ApiModelProperty(example = "此书图书馆中数量过多，暂不接受", required = true, value = "")
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Donate status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * 捐赠状态
   * @return status
  **/
  @ApiModelProperty(required = true, value = "捐赠状态")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Donate date(String date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(example = "2017年8月19日", required = true, value = "")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Donate donate = (Donate) o;
    return Objects.equals(this.id, donate.id) &&
        Objects.equals(this.name, donate.name) &&
        Objects.equals(this.donateType, donate.donateType) &&
        Objects.equals(this.donateName, donate.donateName) &&
        Objects.equals(this.count, donate.count) &&
        Objects.equals(this.reason, donate.reason) &&
        Objects.equals(this.status, donate.status) &&
        Objects.equals(this.date, donate.date) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, donateType, donateName, count, reason, status, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Donate {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    donateType: ").append(toIndentedString(donateType)).append("\n");
    sb.append("    donateName: ").append(toIndentedString(donateName)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
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

