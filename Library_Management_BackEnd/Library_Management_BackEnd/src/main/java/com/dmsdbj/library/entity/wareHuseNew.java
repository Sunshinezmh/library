package com.dmsdbj.library.entity;

import org.jplus.hyberbin.excel.annotation.ExcelModelConfig;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@ExcelModelConfig
@Entity
//public class BookViewModel  extends AbstractAuditingEntity implements Serializable {
    public class wareHuseNew implements Serializable {

    //使用构造器
    public wareHuseNew(){
}
     public wareHuseNew(String id,  Integer usenum ){

	 this.id=id;
	 this.usenum=usenum;

}




    @Id
    private String id = null;
    private Integer usenum =null;
    public String getId() {
        return id;
    }

  public void setId(String id) {
    this.id = id;
  }

   public Integer getuseNum() {
    return usenum;
  }

  public void setuseNum(Integer usenum) {
    this.usenum = usenum;
  }



    }


