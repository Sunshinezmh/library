import { Component, OnInit } from '@angular/core';
import { HttpInterceptorService } from '../../shared/interceptorservice';
import { Http, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { BookTypeManagerModel } from '../../models/book-type-manager.model';
const PUSHTYPE: number = 4;
@Component({
  selector: 'app-check',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.css']
})
export class CheckComponent implements OnInit {

  constructor(
    private http:HttpInterceptorService
  ) { }
  parentTypeModels=new Array();
  // parentTypeModels[] = new BookTypeManagerModel();

  ngOnInit() {
    this.loadData();
    this.checkResult(this.location);
  }

    public msgs: any;
    locamodel:string="";
    value:string ="";
    location:string="万达";

   /*************表格所需要初始化的变量******************************** */
   btnAdd: boolean = false;
   btnEdit = false;
   btnDelete: boolean = false;
   btnImport: boolean = false;
   btnExport: boolean = true;
   
   btnList: string[] = ["启用"];
   title = ['标识号(ISBN)','书名', '书籍位置','可用量', '盘点量','是否盘点','盘点结果', '更新日期'];
   arrbutes = ["isbn","bookName", "location", "useNum","addcount", "ischeck", "result","updatetime"];
   isLink = [true, false, false, false, false];
   paging: boolean = false;
   url = '';
   data = new Array();
   total: number;
   pageSize = 10;
   page = 1;
   isbns = [];
   sizeList = [3, 5, 10, 20];
   isCheck = true;
   btnstyle = [
     { "color": "green" },
     { "color": "red" },
     { "color": "" }]
 
 
   /**************************************************表格end********************************* */
   loadData(): void {
    let url = "book/getAllLocation/1/10";
    this.http.get(url).subscribe(
      res => {
        console.log(url);
        if (res != null && res.json().code == "0000") {
          let models = new Array();
          let model1 = new Array();
          models = res.json().data;
          console.log(res.json().data);
          console.log(models);
          for (let i = 0; i < models.length; i = i + 1) {
            if (models[i] != null) {
              this.parentTypeModels[i]=models[i];
            }
          }
         
        }
      }
    )
  }

  start(model :any):void
  {
    let url = "book/getCheckInfo/"+model;
    console.log(model)
    console.log(url);
    this.http.get(url).subscribe(
      res=>{
        if(res!=null&&res.json().code =="0000")
        {
          this.data=res.json().data;
          console.log(this.data);
          for (let i = 0; i < res.json().data.length; i++) {

            this.data[i].updatetime = this.convertToDate(this.data[i].updatetime);
          }
          this.total=res.json().data.length;
          document.getElementById("isbn").focus();
        }
      }
    )
  }


  checking(locat :any)
  {
    let url ="book/Checking/"+this.value+"/"+locat;
    if(this.value==null||this.value=="")
    {

    }
    else
    {
      this.http.get(url).subscribe(
        res=>{
          if(res!=null&&res.json().code=="0000")
          {
            this.data=res.json().data;
            for (let i = 0; i < res.json().data.length; i++) {
  
              this.data[i].updatetime = this.convertToDate(this.data[i].updatetime);
            }
            this.msgs = [{
              severity: 'success',
              summary: '提示',
              detail: "盘点成功！"
            }]
            this.value="";
            document.getElementById("isbn").focus();
            this.total=res.json().data.length;
          }
          else
          {
            this.msgs = [{
              severity: 'error',
              summary: '提示',
              detail: "盘点失败！"
            }]
            this.value="";
            document.getElementById("isbn").focus();
          }
        }
      )
    }
    
  }

  checkend(locat :any)
  {
    let url ="book/Checkend/"+locat;
      this.http.get(url).subscribe(
        res=>{
          if(res!=null&&res.json().code=="0000")
          {
            this.data=res.json().data;
            for (let i = 0; i < res.json().data.length; i++) {
  
              this.data[i].updatetime = this.convertToDate(this.data[i].updatetime);
            }
            this.msgs = [{
              severity: 'success',
              summary: '提示',
              detail: "成功！"
            }]
            this.total=res.json().data.length;
          }
          else
          {
            this.msgs = [{
              severity: 'error',
              summary: '提示',
              detail: "失败！"
            }]
          }
        }
      )
  }

  checkResult(locat :any)
  {
    let url ="book/checkResult/"+locat;
      this.http.get(url).subscribe(
        res=>{
          if(res!=null&&res.json().code=="0000")
          {
            this.data=res.json().data;
            for (let i = 0; i < res.json().data.length; i++) {
  
              this.data[i].updatetime = this.convertToDate(this.data[i].updatetime);
            }
            this.msgs = [{
              severity: 'success',
              summary: '提示',
              detail: "成功！"
            }]
            this.total=res.json().data.length;
          }
          else
          {
            this.msgs = [{
              severity: 'error',
              summary: '提示',
              detail: "失败！"
            }]
          }
        }
      )
  }

   /**
 * 导出-excel
 */
export() {
  // json数据文件地址
      let url = "book/checkResult/"+this.location;
      // let url = "borrowing/getAllBorrowing/1/10"
  // 调用自己的服务
  this.http.get(url).toPromise().then(res => {
    if (res != null && res.json().code == "0000" && res.json().data != null && res.json().data.length>0) {
      let url = "book/exportCheck/"+this.location;
      // let url = "borrowing/exportBorrowing";
      let urlExcel = this.http.getExportExcelUrl(url);
      window.open(urlExcel);
      URL.revokeObjectURL(urlExcel);
    }else{
      this.showDialog("当前没有盘点信息！");        
    }
  })
}

message = "";
displayP = false; //刚开始赋值为false，让弹框不显示

showDialog(string) {
  this.message = string;
  this.displayP = true;
}

convertToDate(nows) {
  if (nows == null) {
    return "";
  }
  var now = new Date(nows);
  var year = now.getFullYear();
  var month = now.getMonth() + 1;
  var date = now.getDate();
  var hour = now.getHours();
  var minute =  now.getMinutes();
  var seconds = now.getSeconds();
  return year + "-" + month + "-" + date+""+hour+":"+minute+":"+seconds;
}

}



