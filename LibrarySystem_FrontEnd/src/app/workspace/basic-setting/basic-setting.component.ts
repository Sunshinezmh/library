import { Component, OnInit } from '@angular/core';
import {BasicSettingModule} from '../../models/basic-setting.model';
//引入服务
import{HttpInterceptorService}from  '../../shared/interceptorservice';
import{Observable}from 'rxjs/Observable';
import'rxjs/add/operator/toPromise';

@Component({
  selector: 'app-basic-setting',
  templateUrl: './basic-setting.component.html',
  styleUrls: ['./basic-setting.component.css']
})
export class BasicSettingComponent implements OnInit {

  constructor(
    //实例化拦截服务器
    public http:HttpInterceptorService
  ) { }

  //提示框
  displayP=false;
  message:"";
  ngOnInit( ) {
    this.getData();
  }

 public basicsetting  = new BasicSettingModule();
  close(){  
   this.getData();
 }

 /**
  * 提示框信息--刘雅雯--2017年11月9日16:23:10
  * @param string 提示框信息
  */
 showDialog(string){
   this.message=string;
   this.displayP=true;
 }
 /**
  * 页面初始化加载数据--刘雅雯--2017年11月9日11:59:29
  */
 getData(){
   let url="basicSetting/getAlltSettings";
   this.http.get(url).subscribe(
     res=>{
        if(res.json().code=="0000"){
          this.basicsetting= res.json().data[0];          
        
        }else{
          this.showDialog("没有记录");
        }
     }
   ) 
 }

 /**
  * 编辑信息--刘雅雯--2017年11月9日12:00:52
  */
 submit(){
   if(this.basicsetting.id==null){
    /**
    * 添加信息--王雪芬--2018年4月5日11:42:19
    */
       this.basicsetting.id="1";   
       console.log(this.basicsetting);
       let url="basicSetting/addSetting";      
       let body =JSON.stringify(this.basicsetting);
       console.log(this.basicsetting)
       this.http.post(url,body).toPromise().then(
          res=>{
            if(res.json().code="0000"){           
                this.showDialog("添加成功");
                this.getData;
            }else{
              this.showDialog("添加失败");
            }
          }
       ) 
   }else{
    let url="basicSetting/updateSetting";
    let body =JSON.stringify(this.basicsetting);
    console.log(this.basicsetting)
    this.http.post(url,body).toPromise().then(
       res=>{
         if(res.json().code="0000"){           
             this.showDialog("更新成功");
             this.getData;
         }else{
           this.showDialog("更新失败");
         }
       }
    ) 
   } 
 }
}