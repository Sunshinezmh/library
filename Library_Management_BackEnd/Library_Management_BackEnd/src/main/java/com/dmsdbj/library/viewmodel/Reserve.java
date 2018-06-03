package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * Reserve
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Reserve   {
  private String id = null;

  private String name = null;

  private Integer count = null;

  private Integer totalbook = null;

  private String reserveDate = null;

  /**
   * reserve Status
   */
  public enum StatusEnum {
    CANCEL("取消"),
    
    FINISH("完成");

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

  public Reserve id(String id) {
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

  public Reserve name(String name) {
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

  public Reserve count(Integer count) {
    this.count = count;
    return this;
  }

   /**
   * Get count
   * @return count
  **/
  @ApiModelProperty(example = "6", required = true, value = "")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Reserve totalbook(Integer totalbook) {
    this.totalbook = totalbook;
    return this;
  }

   /**
   * Get totalbook
   * @return totalbook
  **/
  @ApiModelProperty(example = "10", required = true, value = "")
  public Integer getTotalbook() {
    return totalbook;
  }

  public void setTotalbook(Integer totalbook) {
    this.totalbook = totalbook;
  }

  public Reserve reserveDate(String reserveDate) {
    this.reserveDate = reserveDate;
    return this;
  }

   /**
   * Get reviewDate
   * @return reviewDate
  **/
  @ApiModelProperty(example = "2017年8月16日", required = true, value = "")
  public String getReserveDate() {
    return reserveDate;
  }

  public void setReserveDate(String reserveDate) {
    this.reserveDate = reserveDate;
  }

  public Reserve status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * reserve Status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "reserve Status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reserve reserve = (Reserve) o;
    return Objects.equals(this.id, reserve.id) &&
        Objects.equals(this.name, reserve.name) &&
        Objects.equals(this.count, reserve.count) &&
        Objects.equals(this.totalbook, reserve.totalbook) &&
        Objects.equals(this.reserveDate, reserve.reserveDate) &&
        Objects.equals(this.status, reserve.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, count, totalbook, reserveDate, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reserve {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    totalbook: ").append(toIndentedString(totalbook)).append("\n");
    sb.append("    reviewDate: ").append(toIndentedString(reserveDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

