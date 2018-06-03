package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;





/**
 * Collection
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Collection   {
  private String id = null;

  private String name = null;

  private String autor = null;

  private Picture picture = null;

  private String bookType = null;

  private String publish = null;

  private Integer totalbook = null;

  private String roomName = null;

  private String areaName = null;

  private String shelfName = null;


  public Collection id(String id) {
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

  public Collection name(String name) {
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

  public Collection autor(String autor) {
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

  public Collection picture(Picture picture) {
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

  public Collection bookType(String bookType) {
    this.bookType = bookType;
    return this;
  }

   /**
   * Get bookType
   * @return bookType
  **/
  @ApiModelProperty(example = "计算机", required = true, value = "")
  public String getBookType() {
    return bookType;
  }

  public void setBookType(String bookType) {
    this.bookType = bookType;
  }

  public Collection publish(String publish) {
    this.publish = publish;
    return this;
  }

   /**
   * Get publish
   * @return publish
  **/
  @ApiModelProperty(example = "图灵出版社", required = true, value = "")
  public String getPublish() {
    return publish;
  }

  public void setPublish(String publish) {
    this.publish = publish;
  }

  public Collection totalbook(Integer totalbook) {
    this.totalbook = totalbook;
    return this;
  }

   /**
   * Get totalbook
   * @return totalbook
  **/
  @ApiModelProperty(example = "100", required = true, value = "")
  public Integer getTotalbook() {
    return totalbook;
  }

  public void setTotalbook(Integer totalbook) {
    this.totalbook = totalbook;
  }

  public Collection roomName(String roomName) {
    this.roomName = roomName;
    return this;
  }

   /**
   * Get roomName
   * @return roomName
  **/
  @ApiModelProperty(example = "第三阅览室", required = true, value = "")
  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public Collection areaName(String areaName) {
    this.areaName = areaName;
    return this;
  }

   /**
   * Get areaName
   * @return areaName
  **/
  @ApiModelProperty(example = "文学馆", required = true, value = "")
  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public Collection shelfName(String shelfName) {
    this.shelfName = shelfName;
    return this;
  }

   /**
   * Get shelfName
   * @return shelfName
  **/
  @ApiModelProperty(example = "心理区", required = true, value = "")
  public String getShelfName() {
    return shelfName;
  }

  public void setShelfName(String shelfName) {
    this.shelfName = shelfName;
  }

  

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Collection collection = (Collection) o;
    return Objects.equals(this.id, collection.id) &&
        Objects.equals(this.name, collection.name) &&
        Objects.equals(this.autor, collection.autor) &&
        Objects.equals(this.picture, collection.picture) &&
        Objects.equals(this.bookType, collection.bookType) &&
        Objects.equals(this.publish, collection.publish) &&
        Objects.equals(this.totalbook, collection.totalbook) &&
        Objects.equals(this.roomName, collection.roomName) &&
        Objects.equals(this.areaName, collection.areaName) &&
        Objects.equals(this.shelfName, collection.shelfName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, autor, picture, bookType, publish, totalbook, roomName, areaName, shelfName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Collection {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    autor: ").append(toIndentedString(autor)).append("\n");
    sb.append("    picture: ").append(toIndentedString(picture)).append("\n");
    sb.append("    bookType: ").append(toIndentedString(bookType)).append("\n");
    sb.append("    publish: ").append(toIndentedString(publish)).append("\n");
    sb.append("    totalbook: ").append(toIndentedString(totalbook)).append("\n");
    sb.append("    roomName: ").append(toIndentedString(roomName)).append("\n");
    sb.append("    areaName: ").append(toIndentedString(areaName)).append("\n");
    sb.append("    shelfName: ").append(toIndentedString(shelfName)).append("\n");
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

