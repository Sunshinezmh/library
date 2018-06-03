package com.dmsdbj.library.viewmodel;

import io.swagger.annotations.ApiModelProperty;
import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;
import org.jplus.hyberbin.excel.annotation.input.InputTextConfig;

import javax.persistence.Id;
import java.util.List;
import java.util.Objects;


/**
 * BookType
 */
@ExcelModelConfig
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-28T15:20:45.131+08:00")
public class BookType   {
  @Id
  @InputTextConfig(nullAble = false)
  private String id = null;

  @Lang(value="父类名称")
  @InputDicConfig(dicCode = "PID")
  private String PID = null;

  @Lang(value="类别名称")
  @InputTextConfig(nullAble = false)
  private String name = null;

  @Lang(value="备注")
  private String remark =null;

  @Lang(value="确认父类名称")
  /**父类名称的name**/
  private String Pname=null;

  public List<BookViewModel> getListBookModel() {
    return listBookModel;
  }

  public void setListBookModel(List<BookViewModel> listBookModel) {
    this.listBookModel = listBookModel;
  }

  private List<BookViewModel> listBookModel;

  public String getPname() {
    return Pname;
  }

  public void setPname(String pname) {
    Pname = pname;
  }

  public BookType remark(String remark){
      this.remark=remark;
      return this;
  }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

  public BookType id(String id) {
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

  public BookType PID(String PID) {
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

  public BookType name(String name) {
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



  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookType bookType = (BookType) o;
    return Objects.equals(this.PID, bookType.PID) &&
            Objects.equals(this.name, bookType.name) &&
			Objects.equals(this.remark, bookType.remark) &&
            Objects.equals(this.id, bookType.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, PID, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookType {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    PID: ").append(toIndentedString(PID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
	sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
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

