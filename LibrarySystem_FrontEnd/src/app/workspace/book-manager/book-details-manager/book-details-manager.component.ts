import { Component, OnInit } from '@angular/core';

import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { Router, ActivatedRoute } from '@angular/router';
import { BarcodeModel } from '../../../models/barcode.model';

import { ConfirmationService } from 'primeng/primeng';

import { bookInfoManagerModel } from '../../../models/book-info-manager.model'
import { BookTypeManagerModel } from '../../../models/book-type-manager.model'


// 声明后台的接口路径

// 模糊查询接口
const CONDITION_URL = "book/findTBookByConditions/";

// 表格初始化接口
const TABLEINIT_URL = "book/findTBookByConditions/";

// 子类型接口
const SONTYPE_URL = "bookType/getAllType/";
// 批量删除接口
const DELETE_URL = "book/deleteBooks/"
// 导出接口
const EXPORT_URL = "";
// 更新接口
const UPDATE_URL = "book/updateTBookInfo";
// ***************************************


@Component({
  selector: 'app-book-details-manager',
  templateUrl: './book-details-manager.component.html',
  styleUrls: ['./book-details-manager.component.css']
})
export class BookDetailsManagerComponent implements OnInit {

  constructor(     // 拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    private confirmService: ConfirmationService,
    private router: Router,
    private activeRoute: ActivatedRoute) { }

  ngOnInit() {
    //从路由中获取isbn
    let isbn = this.activeRoute.snapshot.paramMap.get("isbn");
    let location=this.activeRoute.snapshot.paramMap.get("location");
    //初始化表格
    this.condition = isbn;
    this.location=location;
    this.findResult();
    this.getSontype();
  }
  // 模糊条件
  condition: string = "";
  location:string="";
  //实例化 编辑所需要的实体
  bookInfoManagerModel = new bookInfoManagerModel();
  //获取类别所需要的实体数组
  typeInfoModels: string[];

  /*************表格所需要初始化的变量******************************** */
  btnAdd: boolean = false;
  btnImport: boolean = false;
  btnEdit = true;
  btnDelete: boolean = true;
  btnExport: boolean = false;

  btnList: string[] = ["启用"];
  title = ['标识号(ISBN)', '索书号', '书名', '作者', '书籍位置','页数',  '出版时间', '分类', '来源', '备注'];
  arrbutes = ["isbn", "searchNum", "bookName", "author","location", "totalpage", "publishtime", "typeName", "owner", "remark"];
  isLink = [false, false, false, false, false];
  paging: boolean = true;
  data = new Array();
  total: number;
  pageSize = 10;
  page = 1;

  btnBracodeValue = "浏览条形码"
  ButtonHeaders = [{
    value: this.btnBracodeValue,
    style: { background: "#3F84F4", color: "#fff", fontsize: "14px" }
  }]
  sizeList = [5, 10, 20, 50];
  isCheck = true;
  btnstyle = [
    { "color": "green" },
    { "color": "red" },
    { "color": "" }]

  // ******************以上为表格所需数据*****************************************

  message = "";
  display = false;
  showDialog(msg: string) {
    this.message = msg;
    this.display = true;
  }


  /****************************************编辑模态框的有关操作**************************/

  //模态框可拖拽
  draggable() {
    $('.modal-dialog').draggable();
  }
  //点击编辑，打开模态框
  edit(index: any, modal: HTMLElement) {
    if(index == null || index.length==0){
        this.showDialog("请选择一条记录！")
        return;        
    }
    modal.style.visibility = 'visible';
    this.bookInfoManagerModel = new bookInfoManagerModel();
    this.bookInfoManagerModel = this.data[index];
    this.bookInfoManagerModel.useNum = 1;
  }

  //关闭模态框

  close(el: HTMLElement) {
    el.style.visibility = "hidden";
  }

  /******************************************编辑模态框的有关操作*************************** */
  
  findResult() {
    let url = "";
    if(this.location=="1"){
      this.location="万达";
     }
    if(this.location=="2"){
        this.location="学校";
    }
    url="book/findTBookByIsbnAndLocation/"+this.condition+"/"+this.location+"/" + this.page + "/" + this.pageSize;
    console.log(url)
    this.getData(url);
  }
  query() {
    let url = "";
    if (this.condition.trim() != "") {
      url = CONDITION_URL + this.condition + "/" + this.page + "/" + this.pageSize;
    } else {
      url = TABLEINIT_URL + "%20" + "/" + this.page + "/" + this.pageSize;
    }
    this.getData(url);
  }




  getData(url: string) {
    console.log(url)
    
    this.http.get(url).toPromise().then(res => {
      console.log(res)
      if (res != null && res.json().code == "0000" && res.json().data != null) {
        let temData = res.json().data;
        if (temData.listRows != null) {
          this.data = temData.listRows;
          this.total = temData.length;
        }
      }
    });

  }



  //获取子类方法
  getSontype() {
    let url = SONTYPE_URL + 1 + "/" + 10;
    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.typeInfoModels = res.json().data;
      }
    });
  }

  //更新书籍的信息
  updateBookInfo(el: HTMLElement) {
    let url = UPDATE_URL;
    let body = JSON.stringify(this.bookInfoManagerModel);
    this.http.post(url, body).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.showDialog("更新图书信息成功");
        el.style.visibility = "hidden";

      } else {
        this.showDialog("更新失败，请检查所填数据")
        el.style.visibility = "hidden";

      }
    })
  }

  //批量删除书籍信息
  deleteDatas(indexs: any) {
    this.confirmService.confirm({
      message: "您确认要删除信息吗?",
      accept: () => {
        let ids: String[] = new Array();
        for (let i = 0; i < indexs.length; i++) {
          ids.push(this.data[indexs[i]].id);
        }
        let url = DELETE_URL + ids;
        this.http.post(url, JSON.stringify(ids)).toPromise().then(
          res => {
            console.log(res);
            if (res.json() != null && res.json().code == "0000") {
              this.showDialog("删除成功！");
              let url="book/findTBookByIsbnAndLocation/"+this.condition+"/"+this.location+"/" + this.page + "/" + this.pageSize;
             this.getData(url);
            } else {
              this.showDialog("删除失败，可能没有这本书了");
            }
          });

      }
    });
  }

  //每次改变页大小时出发
  changepage(data:any){
      this.page = data.page;
      this.pageSize = data.pageSize;
      this.query();
  }


  checkboxList = new Array();
  barcodeList = new Array<BarcodeModel>();
  //单击选择框时事件，存入选中的所有索引
  checkboxClick(el: any) {
    this.checkboxList = el;
  }

  //点击浏览条形码时事件
  browseBarcode(el: any) {
    if (el.indexs == null) {
      this.showDialog("请选择相应的记录");
      return;
    }
    switch (el.value) {
      case this.btnBracodeValue:
        this.printBarcode(el.indexs);
        break;
    }
  }
  //生成要打印条形码的相关信息，存入缓存
  printBarcode(indexs: number[]) {
    for (let i = 0; i < indexs.length; i++) {
      let barcodeModel = new BarcodeModel();
      barcodeModel.printBookName = this.data[indexs[i]].bookName;
      barcodeModel.printNumber = 1;
      barcodeModel.typeName = this.data[indexs[i]].typeName;
      barcodeModel.printSearchNum = this.data[indexs[i]].searchNum;
      this.barcodeList.push(barcodeModel);
    }
    localStorage.setItem("printbarcode", JSON.stringify(this.barcodeList));
    this.barcodeList = new Array<BarcodeModel>();
    this.router.navigateByUrl("workspace/print-page")

  }

}
