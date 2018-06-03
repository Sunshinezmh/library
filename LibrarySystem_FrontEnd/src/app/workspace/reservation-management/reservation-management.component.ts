import { Component, OnInit, DoCheck, Output } from '@angular/core';
//引用自己的model
import { ReservationModel } from '../../models/reservation.model'
import { Http, RequestOptions, Headers } from '@angular/http';
//-----拦截器服务
import { HttpInterceptorService } from '../../shared/interceptorservice';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

import { FileUploader } from 'ng2-file-upload';//导入
import { fadeIn } from '../../animations/fade-in';
import { Router } from '@angular/router';

import { ConfirmationService } from 'primeng/primeng';

@Component({
  selector: 'app-reservation-manager',
  templateUrl: './reservation-management.component.html',
  styleUrls: ['./reservation-management.component.css'],
  animations: [fadeIn]
})
export class ReservationManagerComponent implements OnInit, DoCheck {
  /**
 * 杨晓风
 * 2017年11月5日08:52:05
 *modal框可拖拽
 */
  draggable() {
    $('.modal-dialog').draggable();
  }

  filename = "";//文件名
  display = false;
  info = "";
  fileUrl = this.http.getExportExcelUrl("reserve/importReservation");//importReservation testServiceFunction
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
    // 拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService, private router: Router,
    private confirmService: ConfirmationService
  ) { }
  //实例化model
  reservationModel = new ReservationModel();
  property: string;


  ngOnInit() {
    // 页面已加载就调用此方法
    this.getData();
  }

  displayP: boolean;
  messages: "";
  showDialog(string) {
    this.messages = string;
    this.displayP = true;
  }

  /*******************************************表格Start*********************************************** */
  // 初始化表格数据--张明慧-2017年10月15日12:33:12
  btnAdd: boolean = true;
  btnDelete: boolean = true;
  btnEdit = true;
  btnImport: boolean = true;
  btnExport: boolean = true;

  title = ['学号', '姓名', '专业', '班级', '书籍名称','书籍位置', 'isbn', "创建时间"];
  arrbutes = ["studentNo", "studentName", "professionName", "classesName", "bookName", "location" ,"isbn", "createTime"];
  isLink = [true, true, false, false, false];
  paging: boolean = true;
  url = '';
  data = new Array();
  total: number;
  pageSize = 10;
  page = 1;
  sizeList = [5, 10, 20, 50];
  isCheck = true;
  btnstyle = [
    { "color": "green" },
    { "color": "red" },
    { "color": "" }]
  /**************************************************表格end********************************* */

  /**************************************搜索框start********************************************* */
  //搜索框查询数据--张明慧--2017年10月15日12:27:15
  obj: any;
  searchURL: string;
  //模糊查询条件
  condition = "";
  information = '输入查询信息';
  searchUrl = '';
  dataUrl: string;


  /* 模糊查询 */
  query(el: string) {
    this.page = 1;
    this.pageSize = 10;
    if (this.condition.trim() == "") {
      this.searchUrl = "reserve/getReservationNoConditions/" + this.page + "/" + this.pageSize
    } else {
      this.searchUrl = "reserve/getReservationByConditions/" + this.condition.trim() + "/" + this.page + "/" + this.pageSize
    }
    console.log(this.condition.trim());
    //表格联动
    this.http.get(this.searchUrl).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          if (res != null && res.json().code == "0000") {
            let temData = res.json().data.listRows
            for (let i = 0; i < temData.length; i++) {
              temData[i].createTime = this.convertToDate(temData[i].createTime);
            }
            this.data = temData;
            this.total = res.json().data.totalNum;
          } else {
            this.showDialog("查询为空");
          }
        }
      }
    )
    this.searchUrl = '';//清空查询url
  }
  /****************************************end******************************************** */

  /*****************************************添加start******************************************** */
  /**
  * 添加一条数据--李小龙--2017年10月15日12:27:21 (未见使用)
  */

  /**
  * 添加的保存事件--张明慧-2017年10月15日12:29:50
  */
  save(form) {
    let body = JSON.stringify(this.reservationModel);
    let url = "reserve/addreservetionByPC";
    this.http.post(url, body).toPromise().then(
      res => {
        this.getData();
        this.showDialog("添加预约信息成功!");
        this.reservationModel = new ReservationModel();
      }
    );
  }
  /*****************************************添加end******************************************** */

  /*****************************************删除start******************************************** */
  /**
  * 删除多行数据--张明慧--2017年10月15日12:27:31
  * @param el 
  */
  deleteDatas(el: any) {
    this.confirmService.confirm({
      message: "您确认要删除信息吗?",
      accept: () => {
        let ids: String[] = new Array();

        for (let i = 0; i < el.length; i++) {

          ids.push(this.data[el[i]].id);
        }
        let url = "reserve/deleteReservationes/" + ids;
        this.http.post(url, JSON.stringify(ids)).toPromise().then(
          res => {
            if (res.json() != null && res.json().code == "0000") {
              this.showDialog("删除成功！");
              let isFlashData = document.getElementById("isFlashData");
              isFlashData.setAttribute('value', 'true');
            } else {
              this.showDialog("没有这本书！");
            }
            for (let i = 0; i < el.length; i++) {
              this.data.splice(el[i] - i, 1);
              this.total--;
            }
          }
        );

      }
    });
  }
  /*****************************************删除end******************************************** */

  /*****************************************显示数据start******************************************** */
  /**
  * 加载页面显示数据--李小龙--2017年10月15日12:27:36
  */
  getData() {
    let url = "reserve/getReservationNoConditions/" + this.page + "/" + this.pageSize;//接口需要修改，应该没有条件，查询所有
    this.http.get(url).subscribe(
      res => {
        console.log(res.json().data)
        console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (res.json() != null && res.json().code == "0000") {
          let temData = res.json().data.listRows
          console.log(res.json().data)
          for (let i = 0; i < temData.length; i++) {
            temData[i].createTime = this.convertToDate(temData[i].createTime);
          }
          this.data = temData;
          this.total = res.json().data.totalNum;
        }
      });
  }

  /*****************************************显示数据end******************************************** */

  /*****************************************模态框的打开、编辑、删除start********************************* */
  /**
  * 打开模态框
  * @param el 
  * @param modal 
  */
  open(el: HTMLElement, modal: HTMLElement) {
    modal.style.visibility = 'visible';//模态框显示
    this.reservationModel = new ReservationModel();
  }
  /**
  * 编辑信息模态框
  */
  edit(index: string, modal: HTMLElement) {
    if (index.length > 1) {
      this.showDialog("只能选择一条记录！");
      return;
    }
    if (index.length < 1) {
      this.showDialog("请选择一条记录！");
      return;
    }
    modal.style.visibility = 'visible';
    this.reservationModel = this.data[index];
  }

  operatData(obj: any, modal: HTMLElement) {
    modal.style.visibility = 'visible';
    this.reservationModel = this.data[obj.data];
    this.property = "编辑";
  }
  /**
  * 关闭模态框
  * @param el 
  */
  close(el: HTMLElement) {
    el.style.visibility = "hidden";
  }
  /*****************************************模态框的打开、编辑、删除end******************************************** */

  // private updateReservationUrl = "reservation/updateReservation";

  updateStudent(model: HTMLElement) {
    //判断是否修改，修改则走向后台传递，不修改则不操作
    if (this.reservationModel.isbn == this.reservationModel.viewIsbn) {
      this.showDialog("编辑成功！");
      return;
    } else {
      //向后台传递数据（预约ID和预约的ISBN）  
      let reservationModel = new ReservationModel();
      reservationModel.id = this.reservationModel.id;
      reservationModel.isbn = this.reservationModel.isbn;

      let url = "reserve/updateReservation"
      let body = JSON.stringify(reservationModel);

      this.http.post(url, body).toPromise().then(
        res => {
          if (res.json() != null && res.json().code == "0000") {
            this.showDialog("编辑成功！");
            model.style.visibility = "hidden";
          } else {
            this.showDialog("编辑失败！");
          }
        }
      );
    }
  }

  /*****************************************导出-excel start******************************************** */

  /**
  * 导出-excel
  */
  export() {
    let exportCondition="";
    if(this.condition==null||this.condition.trim()==""){
      exportCondition="%20";
    }else{
      exportCondition=this.condition;
    }
      let url = "reserve/getReservationNoConditions/" + 1 + "/" + 10;
    this.http.get(url).subscribe(
      res => {
        if (res.json() != null && res.json().code == "0000") {
          if (res.json().data.listRows != null && res.json().data.listRows.length>0) {
            // let url = "reserve/ExportReservationGet"+exportCondition;
            // let urlExcel = this.http.getExportExcelUrl(url);
            let urlExcel = this.http.getExportExcelUrl("reserve/ExportReservationGet"+"/"+exportCondition);
            window.open(urlExcel);
            URL.revokeObjectURL(urlExcel);
          } else {
            this.showDialog("当前没有可用记录");
            return;
          }


        }
      });





  }
  /*****************************************导出-excel end******************************************** */
  /*****************************************导入excel end******************************************** */
  /**
  * 导入excel值
  * @param info 索引值
  */
  import(info: any) {
    this.display = true;
  }
  /**
  * 下载模板
  */
  down() {
    let url = this.http.getExportExcelUrl("reserve/downExcel");
    window.open(url);
    URL.revokeObjectURL(url);
  }
  /**
  * 显示选择文件的
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
  upload(el: HTMLElement, filepath: any) {
    if (this.filename == "") {
      this.showDialog("没有选择文件，请选择文件");
      return;
    }
    let extName = this.filename.substring(this.filename.lastIndexOf('.') + 1);
    if (extName == "xls" || extName == "xlsx") {
      this.uploadFile();
      this.display = false;
    } else {
      this.showDialog("文件类型不正确，请选择正确的Excel文件（.xls|.xlsx）");
    }
    filepath.value = "";//清空文件选择框
  }

  uploadFile() {
    //上传
    this.uploader.queue[this.uploader.queue.length - 1].upload();
    let obj = this;
    this.uploader.queue[this.uploader.queue.length - 1].onSuccess = function (response, status, headers) {
      //上传文件成功
      console.log(response)
      if (status == 200) {
        // 上传文件后后获取服务器返回的数据
        let tempRes = JSON.parse(response)
        if (tempRes.code == '0000') {
          obj.showDialog("导入成功！");
          // 上传文件后后获取服务器返回的数据
          const el = document.getElementById('isFlashData');
          el.setAttribute('value', 'true');
        } else {
          //导入失败
          obj.showDialog("导入失败,请检查学号和ISBN号是否正确 ！");
        }
      }
    }
  }
  /*****************************************导入excel end******************************************** */
  /**
  * 改变页码：真分页情况下，页号、每页大小改变就会查询后台数据
  * @param data 
  */
  changepage(data: any) {
    this.page = data.page;
    this.pageSize = data.pageSize;
    let url = "";

    if (this.condition.trim() == "") {
      url = "reserve/getReservationNoConditions/" + this.page + "/" + this.pageSize
    } else {
      url = "reserve/getReservationByConditions/" + this.condition + "/" + this.page + "/" + this.pageSize

    }

    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000") {
          let temData = res.json().data.listRows
          for (let i = 0; i < temData.length; i++) {
            temData[i].createTime = this.convertToDate(temData[i].createTime);
          }
          this.data = temData;
          this.total = res.json().data.totalNum;
        }
      }
    )
  }
  //转换时间
  convertToDate(nows) {
    if (nows == null) {
      return "";
    }
    var now = new Date(nows);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "年" + month + "月" + date + "日 "
  }



  /**
   * 根据学号查询学生的信息
   */
  getStudentInfo() {
    let url = "student/getStudentBystudentCode/" + this.reservationModel.studentNo;
    this.http.get(url).toPromise().then(res => {
      if (res.json() != null && res.json().code == "0000") {
        this.reservationModel.studentName = res.json().data[0].name;
        this.reservationModel.professionName = res.json().data[0].professionName
        this.reservationModel.classesName = res.json().data[0].classesName;
      } else {
        this.reservationModel.studentNo = "";
        this.showDialog("抱歉，没有该学生！");
      }
    });
  }


  /**
   * 根据书籍名称查询isbn号
   */
  getBookInfo() {
    let url = "book/getBookNameByISBN/" + this.reservationModel.isbn;
    this.http.get(url).toPromise().then(res => {
      if (res.json() != null && res.json().code == "0000") {
        this.reservationModel.bookName = res.json().data[0].name;
        this.reservationModel.location=res.json().data[0].location;
      } else {
        if (this.reservationModel.viewIsbn != null) {
          this.reservationModel.isbn = this.reservationModel.viewIsbn;   
        } else {
          this.reservationModel.isbn = "";
        }
        this.showDialog("抱歉，没有这本书！");
      }
    });
  }

  /**
   * 郑晓东 2017年11月10日08点52分
   * 设置隐含域，动态监测上传文件是否成功
   * ngDoCheck()实现接口DoCheck，自动执行代码
   */
  ngDoCheck() {
    //监测是否刷新表格
    let el = document.getElementById('isFlashData');

    if (el.getAttribute('value') === 'true') {
      this.query(this.condition);
      el.setAttribute('value', 'false');

    }
  }

}
