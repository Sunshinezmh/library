package com.dmsdbj.library.viewmodel;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * Announcement
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Announcement   {
  private String id = null;

  private String title = null;

  private Picture picture = null;

  private String content = null;

  private String startTime = null;

  private String endTime = null;

  public Announcement id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Announcement title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(  required = true, value="新书到了")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Announcement picture(Picture picture) {
    this.picture = picture;
    return this;
  }

   /**
   * Get picture
   * @return picture
  **/
  @ApiModelProperty(value = "")
  public Picture getPicture() {
    return picture;
  }

  public void setPicture(Picture picture) {
    this.picture = picture;
  }

  public Announcement content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(value = "《 疯狂Java讲义》 最新上架")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Announcement startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(value = "2017年8月22日")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public Announcement endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @ApiModelProperty( value = "2017年8月22日")
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Announcement announcement = (Announcement) o;
    return Objects.equals(this.id, announcement.id) &&
        Objects.equals(this.title, announcement.title) &&
        Objects.equals(this.picture, announcement.picture) &&
        Objects.equals(this.content, announcement.content) &&
        Objects.equals(this.startTime, announcement.startTime) &&
        Objects.equals(this.endTime, announcement.endTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, picture, content, startTime, endTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Announcement {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    picture: ").append(toIndentedString(picture)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
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

