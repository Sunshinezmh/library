package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;




/**
 * BookRank
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class BookRank   {
  private String id = null;

  private String name = null;

  private String bookTypeID = null;

  /**
   * 周期
   */
  public enum PeriodEnum {
    WEEK(" 周 "),
    
    MONTH(" 月 "),
    
    HALFYEAR(" 半年 "),
    
    ALLYEAR(" 一年");

    private String value;

    PeriodEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private PeriodEnum period = null;

  public BookRank id(String id) {
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

  public BookRank name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "《疯狂Java讲义 》", required = true, value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BookRank bookTypeID(String bookTypeID) {
    this.bookTypeID = bookTypeID;
    return this;
  }

   /**
   * Get bookTypeID
   * @return bookTypeID
  **/
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", value = "")
  public String getBookTypeID() {
    return bookTypeID;
  }

  public void setBookTypeID(String bookTypeID) {
    this.bookTypeID = bookTypeID;
  }

  public BookRank period(PeriodEnum period) {
    this.period = period;
    return this;
  }

   /**
   * 周期
   * @return period
  **/
  @ApiModelProperty(value = "周期")
  public PeriodEnum getPeriod() {
    return period;
  }

  public void setPeriod(PeriodEnum period) {
    this.period = period;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookRank bookRank = (BookRank) o;
    return Objects.equals(this.id, bookRank.id) &&
        Objects.equals(this.name, bookRank.name) &&
        Objects.equals(this.bookTypeID, bookRank.bookTypeID) &&
        Objects.equals(this.period, bookRank.period);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, bookTypeID, period);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookRank {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    bookTypeID: ").append(toIndentedString(bookTypeID)).append("\n");
    sb.append("    period: ").append(toIndentedString(period)).append("\n");
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

