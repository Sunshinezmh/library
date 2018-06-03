package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;





/**
 * Message
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Message   {
  private String id = null;

  private String PID = null;

  private String name = null;

  private String content = null;

  private String isbn = null;

  private Picture picture = null;

  private String autor = null;

  private String grade = null;

  /**
   * 状态
   */
  public enum StatusEnum {
    NOTREAD("未读"),
    
    ALREADYREAD("已读 ");

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

  public Message id(String id) {
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

  public Message PID(String PID) {
    this.PID = PID;
    return this;
  }

   /**
   * Get PID
   * @return PID
  **/
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  public String getPID() {
    return PID;
  }

  public void setPID(String PID) {
    this.PID = PID;
  }

  public Message name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "预约提醒", required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Message content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(example = "您预约的《疯狂Java讲义》已在书架是哪个，就等小主前去认领~~", required = true, value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Message isbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

   /**
   * Get isbn
   * @return isbn
  **/
  @ApiModelProperty(example = "d290f1ee6c544", required = true, value = "")
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Message picture(Picture picture) {
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

  public Message autor(String autor) {
    this.autor = autor;
    return this;
  }

   /**
   * Get autor
   * @return autor
  **/
  @ApiModelProperty(example = "李刚", required = true, value = "")
  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public Message grade(String grade) {
    this.grade = grade;
    return this;
  }

   /**
   * Get grade
   * @return grade
  **/
  @ApiModelProperty(example = "5.5", required = true, value = "")
  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public Message status(StatusEnum status) {
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

  public Message date(String date) {
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
    Message message = (Message) o;
    return Objects.equals(this.id, message.id) &&
        Objects.equals(this.PID, message.PID) &&
        Objects.equals(this.name, message.name) &&
        Objects.equals(this.content, message.content) &&
        Objects.equals(this.isbn, message.isbn) &&
        Objects.equals(this.picture, message.picture) &&
        Objects.equals(this.autor, message.autor) &&
        Objects.equals(this.grade, message.grade) &&
        Objects.equals(this.status, message.status) &&
        Objects.equals(this.date, message.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, PID, name, content, isbn, picture, autor, grade, status, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Message {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    PID: ").append(toIndentedString(PID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    isbn: ").append(toIndentedString(isbn)).append("\n");
    sb.append("    picture: ").append(toIndentedString(picture)).append("\n");
    sb.append("    autor: ").append(toIndentedString(autor)).append("\n");
    sb.append("    grade: ").append(toIndentedString(grade)).append("\n");
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

