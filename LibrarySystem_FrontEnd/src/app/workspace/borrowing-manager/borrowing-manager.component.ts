import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { BorrowingModel } from '../../models/borrowing.model';

// 引用自己的service
// import { http } from './borrowing-manager.service';

//-----拦截器服务
import { HttpInterceptorService } from '../../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { RequestOptions } from '@angular/http';
import { fadeIn } from '../../animations/fade-in';
import { ConfirmationService } from 'primeng/primeng';
@Component({
  selector: 'app-borrowing-manager',
  templateUrl: './borrowing-manager.component.html',
  styleUrls: ['./borrowing-manager.component.css'],
  animations: [fadeIn]
  // 表明自己的提供的服务
  // providers:[http]

})
export class BorrowingManagerComponent implements OnInit {


  /**
   * 杨晓风
   * 2017年11月5日08:52:05
   *modal框可拖拽
   */
  draggable() {
    $('.modal-dialog').draggable();
  }

  filename = ""; //文件名
  display = false;
  info = "";
  fileUrl = this.http.getExportExcelUrl("borrowing/importBorrowing");
  userID="";
  /**
   * 导入数据excel：初始化
   */
  public uploader: FileUploader = new FileUploader({
    url: this.fileUrl,
    method: "POST",
    itemAlias: "multfile",
    allowedFileType: ["xls", "xlxs"]

  });



  constructor(

    // 实例化拦截服务，实例化后才能用
    public http: HttpInterceptorService,
    private confirmService: ConfirmationService

  ) { }

  //modal框的名字
  property: string;
  /**
   * 十二期杨晓风-2017年11月10日20:02:11
   * 页面加载每次都会默认无条件查询，两个方法之间没有约束，会根据相应的事件触发不同的方法
   */
  ngOnInit() {
    // 页面已加载就调用此方法
   
    /**
     * 十二期杨晓风-2017-11-10 20:07:06
     * 从人员管理中的学生管理进行路由跳转，并且将点击的学生姓名传递过来 
     *然后自动填充到搜索框中，同时调用模糊查询的方法，直接显示相应学生的信息 
     */
    const studentName = localStorage.getItem('studentName');
    if (studentName != null) {
      if (this.condition == '') {
        this.condition = studentName;
        this.query(this.condition);
        localStorage.removeItem('studentName');
      }
    }else{
       this.getData();
    }

  }
  /**
   * 借阅实体
   */
  borrowing = new BorrowingModel();
  // 初始化表格数据

  btnAdd: boolean = true;
  btnDelete: boolean = true;
  btnImport: boolean = true;
  btnExport: boolean = true;
  title = [ '姓名', '书名', 'ISBN', '借书日期', '实际还书日期', '续借次数', '是否超期','书籍位置'];
  arrbutes = ["studentName",  "bookName", "isbn", "borrowTime", "realDate", "renew", "isOverdue","location"];
  isLink = [true, true, false, false, false];
  //假分页
  paging: boolean = false; 
  data = new Array();
  total: number;
  pageSize = 10;
  page = 1;
  sizeList = [3, 5, 10, 20];
  isCheck = true;
  buttonHeaders = [{
    value: '一键计算',
    style: { background: "red", visibility: "hidden" }

  }]
  condition: string = "";
  /**
   * 搜索框
   */

  //  url='Class/find';
  dataUrl: string;
  searchUrl = '';
  query(el: string) {

    if (this.condition == "") {
      this.searchUrl = "borrowing/getAllBorrowing/1/10";
    } else {
      //模糊查询路径
      this.searchUrl = "borrowing/getBorrowingByConditon/" + this.condition + "/1/10";
    }
    let sUrl = this.searchUrl;

    //表格联动
    this.http.get(sUrl).toPromise().then(

      res => {
        if (res != null && res.json().code == "0000") {
          this.data = res.json().data;
          for (let i = 0; i < res.json().data.length; i++) {

            this.data[i].borrowTime = this.convertToDate(this.data[i].borrowTime);
            this.data[i].realDate = this.convertToDate(this.data[i].realDate);
          }
        }

      }
    );
    //url清空
    this.searchUrl = '';
  }

  /**
   * 分页设置
   * @param  
   */
  changepage(data: any) {
    // let url = "student/getAllStudent/1/1" + "\n页号page: " + data.page + "\n页大小pageSize:" + data.pageSize + "\n总页数totalPages:" + data.totalPages;
    // this.showDialog(url);

    this.page = data.page;
    this.pageSize = data.pageSize;
    let url = "";

    if (this.condition.trim() == "") {

      url = "borrowing/getAllBorrowing/" + this.page + "/" + this.pageSize
    } else {

      url = "borrowing/getAllBorrowing/" + this.condition + "/" + this.page + "/" + this.pageSize

    }

    this.http.get(url).toPromise().then(
      res => {
        this.data = res.json().data.listRows;
        this.total = res.json().data.totalNum;
      }
    )
  }


  /**
   * 加载数据给表格--刘雅雯--2017年10月30日16:15:21
   */
  getData() {
    // json数据文件地址
    let url = "borrowing/getAllBorrowing/1/10"
    // +this.page+"/"+this.pageSize;
    // 调用自己的服务
    this.http.get(url).toPromise().then(
      res => {
        if (res!= null && res.json().data != null ) {
          this.data = res.json().data;
          console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          console.log(this.data);
          // this.data=res.json().listRows;
          //遍历修改状态为是否
          for (let i = 0; i < this.data.length; i++) {
            if (this.data[i].isOverdue == 0) {
              this.data[i].isOverdue = "否"
            }
            if (this.data[i].isOverdue == 1) {
              this.data[i].isOverdue = "是"
            }
            this.data[i].borrowTime = this.convertToDate(this.data[i].borrowTime);
            this.data[i].realDate = this.convertToDate(this.data[i].realDate);
          }
          
          this.data = this.data;
          this.total = res.json().data.length ? res.json().data.length : res.json().rows.length;
        }

      }

    )
  }


  convertToDate(nows) {
    if (nows == null) {
      return "";
    }
    var now = new Date(nows);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    return year + "-" + month + "-" + date;
  }

  /**
   * 添加模态框--根据学号找到对应的学生信息-郭晶晶-2017年11月3日13:45:00
   */
  getStudentInfo() {

    let url = "student/getStudentBystudentCode/" + this.borrowing.code;
    this.http.get(url).toPromise().then(res => {
      if (res.json().code == "0000") {
        this.borrowing.studentName = res.json().data[0].name;
        this.borrowing.professionName = res.json().data[0].professionName;
        this.borrowing.classesName = res.json().data[0].classesName;
        console.log(1);
      }
      else {
        this.showDialog("没有该学生");
        // this.borrowing= new BorrowingModel ();
      }
    });
     this.findUserInfoByCode(this.borrowing.code);
  }
  findUserInfoByCode(code:string):void{
    let url = "user/findUserInfoByCode/"+code;
    this.http.post(url,null).toPromise().then(r=>{
      if(r!=null && r.json().code=="0000")
      {
        let model = r.json().data;
        console.log(r.json().data);
        console.log(2);
        this.userID = model.id;
        console.log(this.userID);
      }
    })
  }
  
  /**
   * 添加模态框---根据索书号找到图书信息--郭晶晶--2017年11月3日14:20:23
   */

  getBookInfo(isbn) {

    let url = "book/getBookBasicByISBN/" + isbn.value;
    this.http.get(url).toPromise().then(res => {
      if (res.json() != null && res.json().code == "0000") {
        console.log("name!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        console.log(res.json().data);
          this.borrowing.bookName = res.json().data.name;
      } else {
        this.showDialog("没有这本书");
      }
    });
  }

  studentId: string = "%20";

  /**
 * 导出-excel
 */
  export() {
    // json数据文件地址
    let url = "borrowing/getAllBorrowing/1/10"
    // 调用自己的服务
    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().code == "0000" && res.json().data != null && res.json().data.length>0) {
        let url = "borrowing/exportBorrowing";
        let urlExcel = this.http.getExportExcelUrl(url);
        window.open(urlExcel);
        URL.revokeObjectURL(urlExcel);
      }else{
        this.showDialog("当前没有可用记录！");        
      }
    })




  }


  /**
   *  导入excel值
   * @param info 索引值
   */
  import(info: any) {
    this.display = true;
  }

  /**
  * 下载模板
  */
  down() {
    let url = this.http.getExportExcelUrl("borrowing/downExcel");
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
  //弹框的信息
  message = "";
  displayP = false; //刚开始赋值为false，让弹框不显示
  uploadFile() {
    var obj = this;
    this.uploader.queue[this.uploader.queue.length - 1].upload();
    //上传文件
    this.uploader.queue[this.uploader.queue.length - 1].onSuccess = function (response, status, headers) {
      //上传文件成功
      if (status == 200) {
        //上传文件后后去服务器返回的数据
        let tempRes = JSON.parse(response);
        obj.message = "上传成功";
        obj.displayP = true;
        obj.getData();

      } else {
        //上传文件后湖区服务器返回的数据错误
        obj.message = "失败";
        obj.displayP = true;
      }
    };
  }


  /**
   * 添加框弹出来，令borrowing为空
   * @param el 
   * @param modal 
   */
  open(el: any, modal: HTMLElement) {
    this.borrowing = new BorrowingModel;
    this.property = "添加";
    modal.style.visibility = 'visible';
  }
  /**
   * 
   * @param el 关闭弹框
   */
  close(el: HTMLElement) {
    el.style.visibility = 'hidden';
    this.getData();
  }
  dateChang() {
    if (this.borrowing.borrowTime > this.borrowing.realDate) {
      this.message = "还书日期应大于借书日期";
      this.displayP = true;
      return;
    }
  }

  showDialog(string) {
    this.message = string;
    this.displayP = true;
  }
  /*****************************************添加start******************************************** */
  /**
   * 添加更新的方法--刘雅雯--2017年10月15日21:32:26
   * @param modal 
   * @param formvalue 
   */
  add(el,form) {
    if (this.borrowing.borrowTime > this.borrowing.realDate) {
      this.showDialog("还书日期应大于借书日期！");
      return;
    }
    if(this.borrowing.location=="")
    {
      this.showDialog("请选择图书位置！");
      return;
    }
    // let body = JSON.stringify(this.borrowing);

    let url = "borrowing/addAppBorrowingRecord/"+this.borrowing.location+"/"+this.borrowing.code+"/"+this.borrowing.isbn+"/"+this.userID;

    this.http.get(url).toPromise().then(
      res => {
        if (res.json().code == "0000") {

          this.getData();
          this.showDialog("添加借阅信息成功！！")
          this.borrowing = new BorrowingModel();
          this.close(el);
        } else {
          this.showDialog("添加失败！");
        }


      }
    );
  }
  /*****************************************添加end******************************************** */

  /*****************************************编辑start******************************************** */
  /**
   * 编辑弹窗
   */
  edit(obj, modalEdit) {
    if (obj.length > 1) {
      this.showDialog("只能选择一条记录！");
      return;
    }
    if (obj.length < 1) {
      this.showDialog("请选择一条记录！");
      return;
    }
    this.borrowing = this.data[obj];

    this.property = "编辑";
    modalEdit.style.visibility = 'visible';
  }

  /**
   * 编辑
   */
  editSave(el,editSel, modalEdit) {

    let tempBorrowing: BorrowingModel = new BorrowingModel();
    tempBorrowing.bookId = this.borrowing.bookId;
    tempBorrowing.id = this.borrowing.id;
    tempBorrowing.borrowTime = this.borrowing.borrowTime;
    tempBorrowing.realDate = this.borrowing.realDate;
    tempBorrowing.renew = this.borrowing.renew;
    tempBorrowing.location = this.borrowing.location;
    tempBorrowing.userId = this.borrowing.userId;
    if (editSel.value == "否") {
      tempBorrowing.isOverdue = 0;
    } else {
      if (editSel.value == "是") {
        tempBorrowing.isOverdue = 1;
      }
    }

    let url = "borrowing/editorBorrowing";
    let body = JSON.stringify(tempBorrowing);

    this.http.post(url, body).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.getData();
        this.showDialog("修改成功");
        this.close(el);
      } else {
        this.showDialog("修改失败,请联系管理员");
      }
    }
    );
    this.borrowing = new BorrowingModel();
  }
  /**
     * 编辑模态框--根据学号找到对应的学生信息-郭晶晶-2017年11月3日13:45:00
     */
  getStudentInfomation() {

    let url = "student/getStudentBystudentCode/" + this.borrowing.code;
    this.http.get(url).toPromise().then(res => {

      if (res.json() != null && res.json().code == "0000") {

        this.borrowing.studentName = res.json().data[0].name;
        this.borrowing.professionName = res.json().data[0].professionName
        this.borrowing.className = res.json().data[0].classesName;

      }
      else {
        this.showDialog("没有该学生");
        this.borrowing = new BorrowingModel();
      }

    });
  }
  /**
   * 编辑模态框---根据索书号找到图书信息--郭晶晶--2017年11月3日14:20:23
   */

  getBookInfomation(isbn) {
    let url = "book/getBookBySearchNum/" + isbn.value;
    this.http.get(url).toPromise().then(res => {

      if (res.json() != null && res.json().code == "0000") {

        this.borrowing.bookName = res.json().data[0].bookName;
      } else {
        this.showDialog("没有这本书");
        this.borrowing = new BorrowingModel();
      }
    });
  }

  /*****************************************编辑end******************************************** */

  /*****************************************删除start******************************************** */
  deleteDatas(el: any) {
    this.confirmService.confirm({
      message: '您确认要删除这条信息吗？',
      accept: () => {

        let ids = new Array();
        for (let i = 0; i < el.length; i++) {

          ids.push(this.data[el[i]].id);
        }
        let url = "borrowing/deleteBorrowings/" + ids;
        console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        console.log(ids);
        this.http.post(url, null).toPromise().then(
          res => {
            this.showDialog("删除成功");
          }
        );
        for (let i = 0; i < el.length; i++) {
          this.data.splice(el[i] - i, 1);
          this.total--;
        }
      }
    });
    let options = new RequestOptions();

  }
  /*****************************************删除end******************************************** */

  /*****************************************操作start******************************************** */
  operatData(obj: any, modalEdit: HTMLElement) {
    this.borrowing = this.data[obj.data];
    this.property = "编辑";
    modalEdit.style.visibility = 'visible';

  }
  /*****************************************操作end******************************************** */

  //选中的htmlElement 
  /**
   * 判断续借超期情况--刘雅雯--2017年10月30日16:26:17---未完成
   */
  reLoad(obj: HTMLSelectElement) {

    // this.showDialog(obj.value);   adf  
    if (obj.value == "已续借" || obj.value == "未续借") {
      // json数据文件地址,查询是否续借
      let url = "borrowing/getBorrowingByCondition/10/1?reNewStatus=" + obj.value;
      // 调用自己的服务
      this.http.get(url).toPromise().then(
        res => {
          this.data = res.json();
        }
      )
      return;
    } else {
      // json数据文件地址,查询是否超期,（url中的4是默认的一个无效参数）--刘雅雯--2017年10月30日16:51:03
      let url = "borrowing/getBorrowingByConditon/4/" + obj.selectedOptions[0].id + "/" + this.page + "/" + this.pageSize;
      // 调用自己的服务
      this.http.get(url).toPromise().then(
        res => {
          this.data = null;
          this.data = res.json().listRows;
        }
      )
      return;
    }
  }

}
