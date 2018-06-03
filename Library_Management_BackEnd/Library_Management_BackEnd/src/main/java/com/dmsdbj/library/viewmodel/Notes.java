package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * Notes
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class Notes   {
  private String id = null;

  private String name = null;

  private String content = null;

  private Integer readingCount = null;

  private Integer comment = null;

  /**
   * notes Status
   */
  public enum OperateEnum {
    EDIT("编辑"),
    
    DELETE("删除");

    private String value;

    OperateEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private OperateEnum operate = null;


  public Notes id(String id) {
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

  public Notes name(String name) {
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

  public Notes content(String content) {
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

  public Notes readingCount(Integer readingCount) {
    this.readingCount = readingCount;
    return this;
  }

   /**
   * Get readingCount
   * @return readingCount
  **/
  @ApiModelProperty(example = "6", required = true, value = "")
  public Integer getReadingCount() {
    return readingCount;
  }

  public void setReadingCount(Integer readingCount) {
    this.readingCount = readingCount;
  }

  public Notes comment(Integer comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @ApiModelProperty(example = "10", required = true, value = "")
  public Integer getComment() {
    return comment;
  }

  public void setComment(Integer comment) {
    this.comment = comment;
  }

  public Notes operate(OperateEnum operate) {
    this.operate = operate;
    return this;
  }

   /**
   * notes Status
   * @return operate
  **/
  @ApiModelProperty(required = true, value = "notes Status")
  public OperateEnum getOperate() {
    return operate;
  }

  public void setOperate(OperateEnum operate) {
    this.operate = operate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Notes notes = (Notes) o;
    return Objects.equals(this.id, notes.id) &&
        Objects.equals(this.name, notes.name) &&
        Objects.equals(this.content, notes.content) &&
        Objects.equals(this.readingCount, notes.readingCount) &&
        Objects.equals(this.comment, notes.comment) &&
        Objects.equals(this.operate, notes.operate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, content, readingCount, comment, operate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Notes {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    readingCount: ").append(toIndentedString(readingCount)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    operate: ").append(toIndentedString(operate)).append("\n");
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

