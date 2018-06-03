package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;




/**
 * Opinion
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Opinion   {
  private String id = null;

  private String content = null;

  private Integer comment = null;

  /**
   * 是否匿名
   */
  public enum IsAnonymityEnum {
    YES("是"),
    
    NO("否");

    private String value;

    IsAnonymityEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private IsAnonymityEnum isAnonymity = null;

  /**
   * 状态
   */
  public enum StatusEnum {
    NOTSOLVE("待解决"),
    
    ALREADYSOLVE("已解决");

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
  private Date date = null;

  public Opinion id(String id) {
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

  public Opinion content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(example = "“大大大大哒哒”这句话很好", required = true, value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Opinion comment(Integer comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @ApiModelProperty(example = "10", value = "")
  public Integer getComment() {
    return comment;
  }

  public void setComment(Integer comment) {
    this.comment = comment;
  }

  public Opinion isAnonymity(IsAnonymityEnum isAnonymity) {
    this.isAnonymity = isAnonymity;
    return this;
  }

   /**
   * 是否匿名
   * @return isAnonymity
  **/
  @ApiModelProperty(required = true, value = "是否匿名")
  public IsAnonymityEnum getIsAnonymity() {
    return isAnonymity;
  }

  public void setIsAnonymity(IsAnonymityEnum isAnonymity) {
    this.isAnonymity = isAnonymity;
  }

  public Opinion status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * 状态
   * @return status
  **/
  @ApiModelProperty(required = true, value = "状态")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Opinion date(Date date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(example = "2017年8月19日", required = true, value = "")
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
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
    Opinion opinion = (Opinion) o;
    return Objects.equals(this.id, opinion.id) &&
        Objects.equals(this.content, opinion.content) &&
        Objects.equals(this.comment, opinion.comment) &&
        Objects.equals(this.isAnonymity, opinion.isAnonymity) &&
        Objects.equals(this.status, opinion.status) &&
        Objects.equals(this.date, opinion.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, comment, isAnonymity, status, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Opinion {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    isAnonymity: ").append(toIndentedString(isAnonymity)).append("\n");
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

