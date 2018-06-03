package com.dmsdbj.library.viewmodel;

import com.dmsdbj.library.entity.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;

import java.io.Serializable;
import java.util.Objects;




/**
 * Book
 */
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-09-26T16:04:17.815+08:00")

@ExcelModelConfig
public class Book extends AbstractAuditingEntity implements Serializable{

  @Lang(value="索书号")
  private String searchNum = null;
  @Lang(value="ISBN")
  private String isbn = null;
  @Lang(value="书籍名称")
  private Integer bookName = null;
  @Lang(value="作者")
  private String author = null;
  @Lang(value="目录")
  private String content = null;
  @Lang(value="摘要")
  private String summary = null;
  @Lang(value="页数")
  private Integer totalpage = null;
  @Lang(value="语种")
  private String languagea = null;
  @Lang(value="出版时间")
  private String publishtime = null;
  @Lang(value="入库时间")
  private String updatetime = null;
  @Lang(value="图书分类")
  private String typeName = null;
  @Lang(value="图书所有者")
  private String owner = null;
  @Lang(value="可借阅量")
  private String usenum =null;
  @Lang(value="备注")
  private String remark =null;

  public Book searchNum(String searchNum) {
    this.searchNum = searchNum;
    return this;
  }
  public Book remark(String remark) {
    this.remark = remark;
    return this;
  }
   public Book owner(String owner) {
    this.owner = owner;
    return this;
  }
    public Book usenum(String usenum) {
    this.usenum = usenum;
    return this;
  }
@JsonIgnoreProperties(ignoreUnknown = true)
   /**
   * Get searchNum
   * @return searchNum
  **/
  @ApiModelProperty(example = "d290f1ee6c544b0190e6", required = true, value = "")
public String getremark() {
    return remark;
  }

  public void setremark(String remark) {
    this.remark = remark;
  }
  public String getSearchNum() {
    return searchNum;
  }

  public void setSearchNum(String searchNum) {
    this.searchNum = searchNum;
  }
  public String getowner() {
    return owner;
  }

  public void setowner(String owner) {
    this.owner = owner;
  }
  
   public String getuseName() {
    return owner;
  }

  public void setusename(String usenum) {
    this.usenum = usenum;
  }

  public Book isbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

   /**
   * Get ISBN
   * @return ISBN
  **/
  @ApiModelProperty(example = "d290f1ee6c544b0190e6", required = true, value = "")
  public String getISBN() {
    return isbn;
  }

  public void setISBN(String isbn) {
    this.isbn = isbn;
  }

  public Book bookName(Integer bookName) {
    this.bookName = bookName;
    return this;
  }

   /**
   * Get bookName
   * @return bookName
  **/
  @ApiModelProperty(example = "《疯狂Java讲义》", required = true, value = "")
  public Integer getBookName() {
    return bookName;
  }

  public void setBookName(Integer bookName) {
    this.bookName = bookName;
  }

  public Book author(String author) {
    this.author = author;
    return this;
  }

   /**
   * Get author
   * @return author
  **/
  @ApiModelProperty(example = "李刚", required = true, value = "")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Book content(String content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(example = "目录，目录，目录", required = true, value = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Book summary(String summary) {
    this.summary = summary;
    return this;
  }

   /**
   * Get summary
   * @return summary
  **/
  @ApiModelProperty(example = "这是摘要，这是摘要", required = true, value = "")
  public String getsummary() {
    return summary;
  }

  public void setsummary(String summary) {
    this.summary = summary;
  }

  public Book totalpage(Integer totalpage) {
    this.totalpage = totalpage;
    return this;
  }

   /**
   * Get totalpage
   * @return totalpage
  **/
  @ApiModelProperty(example = "100", required = true, value = "")
  public Integer getTotalpage() {
    return totalpage;
  }

  public void setTotalpage(Integer totalpage) {
    this.totalpage = totalpage;
  }

  public Book languagea(String languagea) {
    this.languagea = languagea;
    return this;
  }

   /**
   * Get language
   * @return language
  **/
  @ApiModelProperty(example = "汉语", required = true, value = "")
  public String getLanguage() {
    return languagea;
  }

  public void setLanguage(String languagea) {
    this.languagea = languagea;
  }

  public Book publishtime(String publishtime) {
    this.publishtime = publishtime;
    return this;
  }

   /**
   * Get publishtime
   * @return publishtime
  **/
  @ApiModelProperty(example = "2017年9月19日", required = true, value = "")
  public String getPublishtime() {
    return publishtime;
  }

  public void setPublishtime(String publishtime) {
    this.publishtime = publishtime;
  }

  public Book updatetime(String updatetime) {
    this.updatetime = updatetime;
    return this;
  }

   /**
   * Get updatetime
   * @return updatetime
  **/
  @ApiModelProperty(example = "2017年9月19日", required = true, value = "")
  public String getUpdatetime() {
    return updatetime;
  }

  public void setUpdatetime(String updatetime) {
    this.updatetime = updatetime;
  }

  public Book typeName(String typeName) {
    this.typeName = typeName;
    return this;
  }

   /**
   * Get typeName
   * @return typeName
  **/
  @ApiModelProperty(example = "d290f1ee6c544b0190e6", required = true, value = "")
  public String gettypeName() {
    return typeName;
  }

  public void settypeName(String typeName) {
    this.typeName = typeName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(this.searchNum, book.searchNum) &&
        Objects.equals(this.isbn, book.isbn) &&
        Objects.equals(this.bookName, book.bookName) &&
        Objects.equals(this.author, book.author) &&
        Objects.equals(this.content, book.content) &&
        Objects.equals(this.summary, book.summary) &&
        Objects.equals(this.totalpage, book.totalpage) &&
        Objects.equals(this.languagea, book.languagea) &&
        Objects.equals(this.publishtime, book.publishtime) &&
        Objects.equals(this.typeName, book.typeName) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(searchNum, isbn, bookName, author, content, summary, totalpage, languagea, publishtime, updatetime, typeName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Book {\n");
    
    sb.append("    searchNum: ").append(toIndentedString(searchNum)).append("\n");
    sb.append("    isbn: ").append(toIndentedString(isbn)).append("\n");
    sb.append("    bookName: ").append(toIndentedString(bookName)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    summary: ").append(toIndentedString(summary)).append("\n");
    sb.append("    totalpage: ").append(toIndentedString(totalpage)).append("\n");
    sb.append("    language: ").append(toIndentedString(languagea)).append("\n");
    sb.append("    publishtime: ").append(toIndentedString(publishtime)).append("\n");
    sb.append("    updatetime: ").append(toIndentedString(updatetime)).append("\n");
    sb.append("    typeId: ").append(toIndentedString(typeName)).append("\n");
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

