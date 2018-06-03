package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * Comment
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Comment   {
  private String id = null;

  private String bookId = null;

  private String pId = null;

  private String content = null;

  private String userId = null;

  private String userName = null;

  private String date = null;

  public Comment id(String id) {
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

  public Comment bookId(String bookId) {
    this.bookId = bookId;
    return this;
  }

   /**
   * Get bookId
   * @return bookId
  **/
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public Comment pId(String pId) {
    this.pId = pId;
    return this;
  }

   /**
   * Get pId
   * @return pId
  **/
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  public String getPId() {
    return pId;
  }

  public void setPId(String pId) {
    this.pId = pId;
  }

  public Comment content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(example = "《人类灭绝》这本书很好看。", required = true, value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Comment userId(String userId) {
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Comment userName(String userName) {
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  @ApiModelProperty(example = "郭某某", required = true, value = "")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Comment date(String date) {
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
    Comment comment = (Comment) o;
    return Objects.equals(this.id, comment.id) &&
        Objects.equals(this.bookId, comment.bookId) &&
        Objects.equals(this.pId, comment.pId) &&
        Objects.equals(this.content, comment.content) &&
        Objects.equals(this.userId, comment.userId) &&
        Objects.equals(this.userName, comment.userName) &&
        Objects.equals(this.date, comment.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookId, pId, content, userId, userName, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bookId: ").append(toIndentedString(bookId)).append("\n");
    sb.append("    pId: ").append(toIndentedString(pId)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
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

