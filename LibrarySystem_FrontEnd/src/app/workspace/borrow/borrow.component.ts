import { Component, OnInit } from '@angular/core';
// import { BorrowingModel } from '../../models/borrowing.model';
//-----拦截器服务
import { HttpInterceptorService } from '../../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { RequestOptions } from '@angular/http';
import  {fadeIn} from '../../animations/fade-in';

@Component({
  selector: 'app-borrow',
  templateUrl: './borrow.component.html',
  styleUrls: ['./borrow.component.css'],
  animations:[fadeIn]
})
export class BorrowComponent implements OnInit {

  //学生信息: name姓名 sex性别 count已借阅数量 studentCode学生学号 class班级
  name?: string;
  sex?: string;
  count?: string;
  studentCode?:string;
  class?: string;

  //图书信息：bookName书名 author作者 version版次 searchNum索书号 bookcount剩余数量 bookId图书ID
  bookName?: string;
  author?: string;
  version?: string;
  searchNum?: string;
  bookcount?: string;
  bookId: string; 

  picture = 'assets/images/girl.jpg';//默认封面
  
  bookURL = this.picture;//默认图书封面
  studentURL = this.picture;//默认学生封面

  displayP:boolean;
  message:"";
  showDialog(string){
    this.message=string;
    this.displayP=true;
  }
  
  constructor(
    // 实例化拦截服务，实例化后才能用
    public http: HttpInterceptorService
  ) { }

  ngOnInit() {
      this.setSearchNumFocuns();//聚焦索书号输入
  }

  // public studentInfo: any;

  /**
  * 获取学生信息 + obj.value;
  */
  getStudentInfo(obj: any) {
    if(obj.value!=null && obj.value.trim()!=""){
      let url = "borrowing/getStudentBystudentCode/" + obj.value;
      // 调用自己的服务
      this.http.get(url).subscribe(
        res => {
          if (res.json().code == "0000") {
              this.name = res.json().data.studentName;
              this.count = res.json().data.borrowCount==null?0:res.json().data.borrowCount;
              this.sex = res.json().data.sex;
              this.class = res.json().data.className;
              this.studentURL=res.json().data.picture==null?this.picture:res.json().data.picture;//==null?this.picture:res.json().data.pictrue;

              this.studentCode = obj.value;//保存学号
              //检查图书信息是否存在，不存在则进行图书查询
              let el=document.getElementById("searchNum");
              if (el.getAttribute('value')==null || el.getAttribute('value').trim()=="") {
                el.focus();
              }
          } else {
            this.showDialog("未找到该学生信息，请重新扫描或输入信息 ！")
            this.setStudentCodeFocuns();//聚焦学号
          }
        }
      )  
    }else{
      this.clearUserData();
    }                              
  }

  // public bookInfo: any;
  /* 
  获取图书信息 + obj.value;
  */
  getBookInfo(obj: any) {
    //有图书索书号才进行后端查询，否则不走后端
    
    if(obj.value!=null && obj.value.trim()!=""){
      let url = "book/getBookBySearchNum/" + obj.value;
      this.http.get(url).subscribe(
        res => {
          if (res.json().code == "0000") {
            this.bookName = res.json().data[0].bookName;
            this.author = res.json().data[0].author;
            this.bookcount = res.json().data[0].useNum==null?0:res.json().data[0].useNum;
            this.version = res.json().data[0].publishtime;
            this.bookId = res.json().data[0].isbn;
            this.bookURL=res.json().data[0].picture==null?this.picture:res.json().data[0].picture;
            this.searchNum = obj.value;//保存图书索引号
            this.setStudentCodeFocuns();//聚焦学生学号
          } else {
            this.showDialog("未找到该图书信息，请重新扫描或输入信息 ！")
            this.setSearchNumFocuns();//聚焦索书号
          }
        }
      )
    }else{
      this.clearBookData();
    }
  }

  //保存借书记录
  borrowBook(searchNum:any,studentCode:any) {
    let url = "borrowing/addBorrowingRecord/"+this.studentCode+"/"+this.searchNum;
    this.http.get(url).toPromise().then(
      res => {
        if (res.json().code == '0000') {
          this.showDialog("借书成功");
          searchNum.value="";//清空索书号
          studentCode.value="";//清空学号
          this.clearData();//清空数据
          this.setSearchNumFocuns();//聚焦索书号
        } else {
          this.showDialog("借阅失败或已借阅该书，请检查 ");
          this.setSearchNumFocuns();//聚焦索书号
        }
      }
    );
  }

  //保存还书记录
  returnBook(searchNum:any,studentCode:any){
    let el = document.getElementById("searchNum");
    if (this.studentCode==null||this.studentCode.trim() == "" ||this.searchNum==null|| this.searchNum.trim() =="" ) {
      this.showDialog("请确认信息，再次尝试！");
      this.setSearchNumFocuns();//聚焦索书号
    } else {
      let url = "borrowing/returnBorrowingRecord/"+this.studentCode+"/"+this.searchNum;
      this.http.get(url).toPromise().then(
        res => {
          
          if (res.json().code == '0000') {
            // this.showDialog("成功还书！");
            this.showDialog("成功还书！");
            searchNum.value="";//清空索书号
            studentCode.value="";//清空学号
            this.clearData();//清空数据
            this.setSearchNumFocuns();//聚焦索书号
          } else {
            // this.showDialog("还书失败！请检查信息，再次尝试！");
            this.showDialog("未借阅过该资料，请检查或联系管理员");
            this.setSearchNumFocuns();//聚焦索书号
          }
        }
      );
    }
    
  }

  //清空数据
  clearData(){
    this.clearBookData();
    this.clearUserData();
  }
  //清空图书信息
  clearBookData(){
    this.searchNum="";
    this.bookName="";
    this.author="";
    this.bookcount="";
    this.version="";
    this.bookURL=this.picture;
  }
  //清空用户信息
  clearUserData(){
    this.studentCode="";
    this.name="";
    this.sex="";
    this.class="";
    this.count="";
    this.studentURL=this.picture;
  }

  //聚焦索书号
  setSearchNumFocuns(){
    document.getElementById('searchNum').focus();
  }

  //聚焦学号
  setStudentCodeFocuns(){
    document.getElementById('studentCode').focus();
  }
} 
