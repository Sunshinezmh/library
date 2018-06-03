package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;





/**
 * Rule
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Rule   {
  private String id = null;

  private String name = null;

  private String content = null;

  private String date = null;

  private Picture picture = null;


  public Rule id(String id) {
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

  public Rule name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "读者须知", required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Rule content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(example = "这是积分须知，这是预约须知，这是积分须知，这是预约须知", required = true, value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Rule date(String date) {
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

  public Rule picture(Picture picture) {
    this.picture = picture;
    return this;
  }

   /**
   * Get picture
   * @return picture
  **/
  @ApiModelProperty(required = true, value = "")
  public Picture getPicture() {
    return picture;
  }

  public void setPicture(Picture picture) {
    this.picture = picture;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rule rule = (Rule) o;
    return Objects.equals(this.id, rule.id) &&
        Objects.equals(this.name, rule.name) &&
        Objects.equals(this.content, rule.content) &&
        Objects.equals(this.date, rule.date) &&
        Objects.equals(this.picture, rule.picture);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, content, date, picture);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rule {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    picture: ").append(toIndentedString(picture)).append("\n");
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

