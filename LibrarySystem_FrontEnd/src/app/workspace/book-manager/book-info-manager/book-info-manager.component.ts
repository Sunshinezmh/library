import { Component, OnInit } from '@angular/core';
// 引用自己的service
import { bookInfoManagerModel } from '../../../models/book-info-manager.model'
import { BookTypeManagerModel } from '../../../models/book-type-manager.model'

import { Http, RequestOptions, Headers } from '@angular/http';
import { Router } from '@angular/router';
//-----拦截器服务
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { FileUploader } from 'ng2-file-upload';
import { fadeIn } from '../../../animations/fade-in';


//引入日期控件
import { CalendarModule } from 'primeng/primeng';

// import { DatePipe } from '@angular/common';//日期转换  

@Component({
  selector: 'app-book-info-manager',//组件的名字
  // templateUrl: './book-info-manager.component.html',
  templateUrl: './book-info-manager.component.html',

  styleUrls: ['./book-info-manager.component.css'],
  animations: [fadeIn]


})
export class BookInfoManagerComponent implements OnInit {


  /**
 * 杨晓风
 * 2017年11月5日08:52:05
 *modal框可拖拽
 */
  draggable() {
    $('.modal-dialog').draggable();
  }
  zh: any;
  filename = ""; //文件名
  display = false;

  info = "";
  fileUrl = "";
  pictureUrl = "";
  uploadPictureUrl = this.http.getExportExcelUrl("book/uploadPicture");//上传图片的URL
  oldPosition:string="";//原数据的书籍位置
  newPosition:string="";//修改之后的书籍的位置

  /**
   * 导入数据excel：初始化
   */
  public uploader: FileUploader = new FileUploader({
    url: this.http.getExportExcelUrl("book/importBook"),
    method: "POST",
    itemAlias: "multfile",
    allowedFileType: ["xls", "xlxs"]

  });





  constructor(
    // 拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    private router: Router
  ) { }


  //实例化model
  bookInfoManagerModel = new bookInfoManagerModel();
  BookTypeManagerModel = new BookTypeManagerModel();
  bookInfoList = new Array<bookInfoManagerModel>();
  property: string;
  ngOnInit() {
    this.dataInit();

  }

  /**
    * 十二期杨晓风-2017-11-10 20:07:06
    * 从人员管理中的学生管理进行路由跳转，并且将点击的学生姓名传递过来 
    *然后自动填充到搜索框中，同时调用模糊查询的方法，直接显示相应学生的信息 
    */
  public dataInit() {
    let teacherName = localStorage.getItem('teacherName');
    if (teacherName != null) {
      this.condition = teacherName;
      this.query();
      localStorage.removeItem('teacherName');
    } else {
      let url = "book/findAllBookByPagination/" + this.page + "/" + this.pageSize;
      this.getData(url);
    }
  }

  /*************表格所需要初始化的变量******************************** */
  btnAdd: boolean = true;
  btnEdit = true;
  btnDelete: boolean = true;
  btnImport: boolean = true;
  btnExport: boolean = true;

  btnList: string[] = ["启用"];
  title = ['标识号(ISBN)','书名', '目录', '摘要', '书籍位置','作者', '出版社','库存量','可用量', '备注',];
  arrbutes = ["isbn","bookName", "content", "summary","location", "author", "publishPlace","wareCount","useNum", "remark"];
  isLink = [true, false, false, false, false];
  paging: boolean = true;
  url = '';
  data = new Array();
  total: number;
  pageSize = 10;
  page = 1;
  isbns = [];

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


  /**************************************************表格end********************************* */
  expectBook = new Array();

  /**************************************搜索框start********************************************* */
  //搜索框查询数据--张明慧--2017年10月15日12:27:15

  obj: any;
  searchURL: string;
  searchUrl = '';
  dataUrl: string;
  condition: string = "";
  displayP: boolean;
  message: "";

  /**
 * 提示框信息--刘雅雯--2017年11月9日16:23:10
 * @param string 提示框信息
 */
  showDialog(string) {
    this.message = string;
    this.displayP = true;
  }
  query() {
    let url = "";
    if (this.condition == null || this.condition.trim() == "") {
      url = "book/findAllBookByPagination/" + this.page + "/" + this.pageSize;
      this.getData(url);
    } else {
      url = "book/findByConditions/" + this.condition + "/" + this.page + "/" + this.pageSize;
      //表格联动
      this.http.get(url).toPromise().then(

        res => {
          if (res.json().code == "0000") {
            this.data = res.json().data;

            let temData = res.json().data.listRows
            for (let i = 0; i < temData.length; i++) {
              temData[i].updatetime = new Date(temData[i].updatetime);
              if (temData[i].summary != null) {
                temData[i].summary = temData[i].summary.substring(0, 8) + "...";
              }
              if (temData[i].content != null) {
                temData[i].content = temData[i].content.substring(0, 8) + "...";
              }
            }
            this.data = temData;
            this.total = res.json().data.totalNum;
          }
          else {
            this.showDialog("目前没有符合查询条件的数据!");
          }
        }
      );
    }
    //url清空
    this.searchUrl = '';
  }



  /****************************************end******************************************** */
  showChange(){
    
  }


  getBookTypeOptions: string[]
  getBookType() {
    let urlTypt = "bookType/getAllType/1/50";
    this.http.get(urlTypt).toPromise().then(
      res => {
        this.getBookTypeOptions = res.json().data;
            
      }
    );
  }

  getBookLocationOptions:string[]
  getBookLocation(){
    let urlLocation="book/getAllLocation/1/10";
    this.http.get(urlLocation).toPromise().then(
      res =>{
        this.getBookLocationOptions=res.json().data;
        }
    )
  }

  /**
 * 添加的保存事件--张明慧-2017年10月15日12:29:50
 */
  save(typeName: string,location:string, el, modalEdit) {
 
    this.bookInfoManagerModel.typeName = typeName;
    this.bookInfoManagerModel.location = location;
    console.log(this.bookInfoManagerModel.location)
    this.bookInfoManagerModel.operator = localStorage.getItem('userId');
    let body = JSON.stringify(this.bookInfoManagerModel);
    let url = "book/addBookInfo";
    let addurl = "book/findAllBookByPagination/" + this.page + "/" + this.pageSize;

    if (this.bookInfoManagerModel.typeName == null || this.bookInfoManagerModel.typeName == "") {
      this.showDialog("请选择要添加的图书子类别！")
      return;
    }
    if (this.bookInfoManagerModel.typeName.trim() == "") {
      this.showDialog("请选择要添加的图书子类别！")
      return;
    }
   console.log(body)
    this.http.post(url, body).toPromise().then(
          res => {
           if (res.json() != null && res.json().code == "0000") {
          this.showDialog(res.json().message);

          this.getData(addurl);
        }else{
          this.showDialog(res.json().message);
        }

      });
    this.close(el);
    localStorage.removeItem('picUrl');
    localStorage.removeItem('userId');

  }

  getSearchNmu() {
    let url = "book/getSearchSumByISBN/" + this.bookInfoManagerModel.isbn;
    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.bookInfoManagerModel.searchNum = res.json().data;
      } else {
        this.showDialog(res.json().message);
      }
    }).catch(e => this.showDialog("获取索书号失败，请检查网络情况或服务器状态"))
  }


  changepage(data: any) {

    this.page = data.page;
    this.pageSize = data.pageSize;
    this.query()
  }

  /*****************************************添加end******************************************** */


  /*****************************************删除start******************************************** */


  /**
    * 删除多行数据--张明慧--2017年10月15日12:27:31
    * @param el 
    */
  deleteDatas(el: any) {
    let deleteData = new Array();
    let userId = localStorage.getItem("userId");
    let bookTypeIds: string = this.data[el[0]].id;
     for (let i = 1; i < el.length; i++) {
      bookTypeIds = bookTypeIds + "," + this.data[el[i]].id
    }

    let r = confirm("您确认要删除这条信息吗？");
    let options = new RequestOptions();
    let url = "book/findAllBookByPagination/" + this.page + "/" + this.pageSize;
    if (r == true) {
      let urldelete = "book/deleteBasicBooks/" + bookTypeIds;
      this.http.post(urldelete, null).subscribe(
        res => {
          if (res != null && res.json().code == "0000") {
            this.getData(url);
            this.showDialog(res.json().message);
            for (let i = 0; i < el.length; i++) {
              this.data.splice(el[i] - i, 1);
              this.total--;
            }
          }else{
            this.showDialog("删除失败");
          }

        }
      );

    }
  }
  /*****************************************删除end******************************************** */


  /*****************************************显示数据start******************************************** */
  /**
 * 加载页面显示数据--张明慧--2017年10月15日12:27:36
 * 查询后台的数据，赋给data，改变data的值，后台的值也就改变了。
 */



  getData(url) {
    this.data = new Array();
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().code == "0000" && res.json().data != null) {
          if (res.json().data.listRows != null) {
            this.data = res.json().data.listRows;
            let i = 0;
            this.data.forEach(el => {
              if (el.summary != null) {
                el.summary = el.summary.substring(0, 8) ;
              }
              if (el.content != null) {
                el.content = el.content.substring(0, 8) ;
              }
            });
            this.total = res.json().data.totalNum;
          } else {
            this.showDialog("当前没有可用的记录！");
          }

        }
        else {
          this.showDialog(res.json().message);
        }
      })
  }

  public convertDate(str: number): string {
    let newDate: Date = new Date(str);
    newDate.setTime(str);
    let fmt = "yyyy-MM-dd";
    var o = {
      "M+": newDate.getMonth() + 1, //月份 
      "d+": newDate.getDate(), //日 
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (newDate.getUTCFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

  }


//单击isbn号链接事件
linkClick(index:any){
//   if(this.data[index].location=="万达"){
//     this.data[index].location="1";
// }
// if(this.data[index].location=="学校"){
//   this.data[index].location="2";
// }
  this.router.navigateByUrl("workspace/book-manager/book-details-manager/" + this.data[index].isbn +'/'+ this.data[index].location );
console.log(this.data[index].location)
}




  /*****************************************显示数据end******************************************** */


  /*****************************************模态框的打开、编辑、删除start********************************* */
  /**
   * 打开模态框
   * @param el 
   * @param modal 
   */

  open(el: HTMLElement, modal: HTMLElement) {
    modal.style.visibility = 'visible';
    this.bookInfoManagerModel = new bookInfoManagerModel();
    this.bookInfoManagerModel.useNum = 1;

    this.getBookType();
    this.getBookLocation();
  }

  /**
    * 编辑信息模态框
    */
  edit(obj, modal: HTMLElement) {
    if (obj.length > 1) {
      this.showDialog("只能选择一条记录！");
      return;
    }
    if (obj.length < 1) {
      this.showDialog("请至少选中一条记录！");
      return;
    }
    this.bookInfoManagerModel = this.data[obj];
    console.log(this.bookInfoManagerModel)
    console.log(this.bookInfoManagerModel.location)

     this.oldPosition=this.bookInfoManagerModel.location;
    
     console.log(this.oldPosition)
    this.property = "编辑";
    modal.style.visibility = 'visible';
    this.getBookType();
    this.getBookLocation();
  }

  operatData(obj: any, modal: HTMLElement) {
    this.bookInfoManagerModel.operator = localStorage.getItem("userId");
    this.bookInfoManagerModel = this.data[obj.data];
    this.property = "编辑";
    modal.style.visibility = 'visible';
  

  }
  /**
  * 关闭模态框
  * @param el 
  */
  close(el: HTMLElement) {
    el.style.visibility = "hidden";
  }
  /***********当isbn未输入时，提醒是必填项 -张婷***************/

  inputMsg(obj: any) {
    if (obj.value == "") {
      this.showDialog("亲，请先填写ISBN项！");
    }
    return;
  }


  /******************根据isbn获得索书号，图书入库使用***************/

  public bookInfo: any;



  /*****************************************模态框的打开、编辑、删除end******************************************** */

  /*****************************************编辑更新start******************************************** */

  updateBook(el, modalEdit) {
    this.bookInfoManagerModel.operator = localStorage.getItem("userId");
    
    // let url = "book/updateBook";
    let url;
    let body = JSON.stringify(this.bookInfoManagerModel);
    this.newPosition=this.bookInfoManagerModel.location;
    if(this.oldPosition==this.newPosition){
      url="book/updateBook";
    }
    else{
      url="book/updateBookBasicBylocation";
    }
    console.log(this.newPosition);
    this.http.post(url, body).toPromise().then(
      res => {
        if (res.json() != null && res.json().code == "0000") {
          this.showDialog("更新成功！");
          this.dataInit();
          this.close(el);
        } else {
          this.showDialog("更新失败！");
          this.close(el);
          localStorage.removeItem("userId");
        }
      }
    );
  }
  /*****************************************编辑更新end******************************************** */


  /*****************************************树结构start******************************************** */
  /**
   * tree的使用--杜雨--2017年10月26日09:11:55
   */
  /****显示树结构*****/
  treeURL = this.http.getExportExcelUrl("bookType/getAllType/1/10");

  treeClick(el: any) {
    this.obj = el;
    //在页面弹出
    let id = el.id;
    let pid = el.pId;
    let showTableUrl: string;
    /**表格联动，根据图书类型id查询对应的图书信息 */
    if (pid == null || pid == 0) {
      showTableUrl = "book/getBookByParentTypeId/" + id;
    }
    else {
      showTableUrl = "book/getBookByBookType/" + id;
    }
    //设计访问后的id为“”
    id = "";
    //表格获取数据
    this.http.get(showTableUrl).toPromise().then(
      res => {
        /**如果查询成功返回的code码为0000，则根据查询结果在页面上显示相应的图书信息 */
        if (res.json().code == "0000") {
          this.data = res.json().data;
          this.total = res.json().data.length;
          this.searchURL = null;
        }
        /***如果返回code码是1111，代表该类别下没有图书，弹框提示用户该类别下没有图书*/
        else {
          this.data = new Array();
          this.total = 0;
          this.showDialog(res.json().message);
        }
      }
    )
  }

  


  /*****************************************树结构end******************************************** */
  //上传配置
  public uploaderAddBack: FileUploader = new FileUploader(
    {
      url: this.uploadPictureUrl,
      method: "POST",
      removeAfterUpload: true
    }
  )

  public selectedFileOnChange(el: any) {
    let obj = this;
    this.uploaderAddBack.queue[this.uploaderAddBack.queue.length - 1].onSuccess = function (response, status, headers) {
      if (status == 200) {
        // 上传文件后获取服务器返回的数据
        let temRes = JSON.parse(response);
        let data = temRes.data;
        //为防止fastdfs服务器发生改变，截取ip后边的图片路径保存到数据库中-张婷-2017-11-13 12:43:15
        length = data.length;
        data = data.substring(22, length);
        obj.bookInfoManagerModel.picture = data;
        this.pictureUrl = obj.bookInfoManagerModel.picture;
        localStorage.setItem('picUrl', this.pictureUrl);
      }
    }
    // 开始上传
    this.uploaderAddBack.queue[this.uploaderAddBack.queue.length - 1].upload();
  }


  /*****************************************导出-excel start******************************************** */

  /**
   * 导出-excel
   */
  export(obj:any) {
    //http.get().toPromise().then是angualr4封装好的方法。res是后台返回的response
    //res.json其实就是后台返回的ItooResult类型
    //注意访问后台服务是一个异步的过程
    //导出的时候，先调用后台判断是否有数据，然后在进行导出
    let exportCondition = "";
    
    if (this.condition == null || this.condition.trim() == "") {
      exportCondition = "%20";
    } else {
      exportCondition = this.condition;
    }
    let url = "book/findAllBookByPagination/" + 1 + "/" + 1;
    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000" && res.json().data != null && res.json().data.listRows.length > 0) {
          if(obj==null)
          {
            let urlExcel = this.http.getExportExcelUrl("book/bookExportBookGet/"+exportCondition+"/"+ "%20");
            if (this.info == "") {
              this.info = "%20";
            }
            let urlReal = `${urlExcel}`;
            window.open(urlReal);
            URL.revokeObjectURL(urlReal);
            if (this.info = "%20") {
              this.info = "";
            }
          }
          else //选中导出。
          {
            
            exportCondition="";//模糊查询的数据为空。
            // let isbns: string = this.data[obj[0]].isbn;
            for(let i = 0;i<obj.length;i++)
            {
              let isbn: string = this.data[obj[i]].isbn;
              this.isbns.push(isbn);
            }                                                         
            let urlExcel = this.http.getExportExcelUrl("book/bookExportBookGet/"+"%20"+"/"+ this.isbns);
            if (this.info == "") {
              this.info = "%20";  
            }
            let urlReal = `${urlExcel}`;
            window.open(urlReal);
            URL.revokeObjectURL(urlReal);
            if (this.info = "%20") {
              this.info = "";
            }
          }
          this.isbns=[];
        } else {
          this.showDialog("当前没有可用记录！");
        }
      })
  }
  /*****************************************导出-excel end******************************************** */

  /*****************************************导入excel end******************************************** */
  /**
   *  导入excel值
   * @param info 索引值
   */
  importFile(info: any) {
    this.display = true;
  }

  /**
   * 下载模板
   */
  down() {
    let url = this.http.getExportExcelUrl("book/downExcel");
    window.open(url);
    URL.revokeObjectURL(url);
  }

  /**
   * 显示选择文件的框
   * @param el 
   */
  show(el: HTMLElement) {
    el.click();
  }

  /**
   * 
   * @param event 选中文件--刘雅雯--2017年10月15日16:47:22
   */
  selectedFileOnChanged(event: any) {
    //打印文件选择名称
    this.filename = event.target.value;
  }

  /**
   * 导入文件
   */
  upload() {
    if (this.filename == "") {
      this.showDialog("没有选择文件，请选择文件");
      return;
    }
    this.uploadFile();
    this.display = false;
  }

  uploadFile() {
    let obj = this;
    console.log(this.uploader.queue)
    this.uploader.queue[this.uploader.queue.length - 1].upload();
    //上传
    this.uploader.queue[this.uploader.queue.length - 1].onSuccess = function (response, status, headers) {
      //上传文件成功
      if (status == 200) {
        if (response != null) {
          let tempRes = JSON.parse(response);
          if (tempRes.code == "0000") {
            obj.showDialog("上传成功");
            obj.getData("book/findAllBookByPagination/"+ obj.page + "/" + obj.pageSize);
          }else{
            obj.showDialog(tempRes.message);
          }
        }
      } else {
        //上传文件后湖区服务器返回的数据错误
        obj.showDialog("上传失败，请检查类别是否存在！");
      }
    };
  }








}


