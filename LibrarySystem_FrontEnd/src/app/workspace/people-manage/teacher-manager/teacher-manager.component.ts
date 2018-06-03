import { Component, OnInit } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { TeacherModel } from '../../../models/teacher.model';

//----------拦截器服务
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

import { FileUploader } from 'ng2-file-upload';
import { fadeIn } from '../../../animations/fade-in';

import { Router } from '@angular/router';


@Component({
  selector: 'app-teacher-manager',
  templateUrl: './teacher-manager.component.html',
  styleUrls: ['./teacher-manager.component.css'],
  animations: [fadeIn]
})


export class TeacherManagerComponent implements OnInit {

  /**
   * 杨晓风
   * 2017年11月5日08:52:05
   *modal框可拖拽
   */
  draggable() {
    $('.modal-dialog').draggable();
  }


  information = "教工号/姓名/性别"//搜索框中的提示信息

  filename = ""//文件名
  display = false;

  info = "";
  fileUrl = "";
  importUrl = this.http.getExportExcelUrl("teacher/importTeacher");

  //弹框的信息
  message = "";
  displayP = false; //刚开始赋值为false，让弹框不显示
  /**
    * 导入数据excel：初始化
    */
  public uploader: FileUploader = new FileUploader({
    url: this.importUrl,
    method: "POST",
    itemAlias: "multfile",
    allowedFileType: ["xls", "xlxs"]

  });

  constructor(
    // 拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    public router: Router
  ) { }

  // 实例化自己的model
  teacherModel = new TeacherModel();

  property: string;


  ngOnInit() {
    //页面已加载就调用次方法
    this.getData();

  }
  /*************表格所需要初始化的变量******************************** */
  btnAdd: boolean = true;
  btnDelete: boolean = true;
  btnEdit: boolean = true;
  btnImport: boolean = true;
  btnExport: boolean = true;
  // btnList = ["编辑"];
  title = ['教工号', '姓名', '性别'];
  arrbutes = ["code", "name", "sex"];
  //表格连接为true,触发linkClick方法
  isLink = [false, true, false];
  paging: boolean = false;
  url = '';
  data = [];
  total: number;
  totalNum: number;
  pageSize = 10;
  page = 1;
  sizeList = [5, 10, 20, 50];
  isCheck = true;
  btnstyle = [
    { "color": "green" },
    { "color": "red" },
    { "color": "" }]

  /****************************表格end************************************** */


  /**************************************搜索框start********************************************* */
  //搜索框查询数据--李小龙--2017年10月15日12:27:15
  obj: any;
  searchURL: string;
  private searchContent = "";//查询条件
  infomation = '输入查询信息';
  searchUrl = '';
  dataUrl: string;

  query(el: string) {

    this.searchContent = el.toString();
    if (this.searchContent == "/") {
      this.searchUrl = "teacher/getAllTeacher" + '/' + this.pageSize + '/' + this.page
    } else {
      this.searchUrl = "teacher/getTeacherByCondition" + this.searchContent + '/' + this.pageSize + '/' + this.page
    }
    //表格联动
    this.http.get(this.searchUrl).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          this.data = res.json().data;
          this.total = res.json().data.length ? res.json().data.length : res.json().rows.length;
        }
        else {
          this.data = [];
        }

      }
    )
    //url清空
    this.searchUrl = '';
  }

  /*****************************************搜索框end******************************************** */

  /*****************************************添加start******************************************** */
  /**
 * 添加的保存事件--张明慧-2017年10月15日12:29:50
 */
  save(el: Element) {
    if (this.teacherModel.name == null || this.teacherModel.sex == null || this.teacherModel.code == null) {
      this.message = "请将内容补充完整";
      this.displayP = true;
      return;
    }
    this.teacherModel.id = "";
    let body = JSON.stringify(this.teacherModel);
    let url = "teacher/addTeacher";
    this.http.post(url, body).toPromise().then(
      res => {

        if (res.json().code == "0000") {
          this.message = res.json().message;
          this.displayP = true;
          this.getData();
          this.teacherModel = new TeacherModel;
        }
        else {
          this.message = res.json().message;
          this.displayP = true;
        }
      }
    );
  }
  /*****************************************添加end******************************************** */


  /*****************************************删除start******************************************** */

  /**
    * 删除多行数据--张明慧--2017年10月15日12:27:31
    * @param el 
    */
  deleteDatas(obj: any) {

    //let deleteDatas=new Array();
    let first: string = obj[0]
    let teacherids: string = this.data[first].code;

    for (let i = 1; i < obj.length; i++) {
      let j = obj[i];
      teacherids = teacherids + "," + this.data[j].code
    }
    let url = 'teacher/deleteTeachers/' + teacherids;
    this.http.post(url, null).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          let i = 0;
          this.getData();
          this.message = "删除成功！";
          this.displayP = true;
        }
        else {
          this.message = "删除失败！";
          this.displayP = true;
        }
      }
    )
  }
  /*****************************************删除end******************************************** */


  /*****************************************显示数据start******************************************** */
  /**
 * 加载页面显示数据--张明慧--2017年10月15日12:27:36
 */
  getData() {
    let url = "teacher/getAllTeacher/" + this.pageSize + '/' + this.page;
    // 调用自己的服务
    this.http.get(url).subscribe(
      res => {
        this.data = res.json().data;
        this.total = res.json().data.length ? res.json().data.length : res.json().rows.length;
      })
  }

  /**
   * 十二期杨晓风-2017年11月10日20:28:41
   * 表格按钮点击事件，点击姓名跳转首页，同时将点击的名字保存在 localStorage中
   * @param el 
   */
  linkClick(el: any) {

    localStorage.setItem('teacherName', this.data[el].name);
    this.router.navigateByUrl('workspace/book-manager/book-info-manager');
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
    this.teacherModel = new TeacherModel();
  }

  /**
    * 编辑信息模态框
    */
  edit(obj: any, editmodal: HTMLElement) {
    if (obj.length > 1) {
      this.message = "只能选择一条记录！";
      this.displayP = true;
      return;
    }
    if (obj.length < 1) {
      this.message = "请选择一条记录！";
      this.displayP = true;

      return;
    }
    editmodal.style.visibility = 'visible';
    this.teacherModel = this.data[obj];

  }
  operatData(obj: any, editmodal: HTMLElement) {
    // this.teacherModel=this.data[obj.data];
    editmodal.style.visibility = 'visible';
    this.teacherModel = this.data[obj];
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

  /*****************************************编辑更新start******************************************** */
  private updateTeacherUrl = "teacher/updateTeacher";


  updateTeacher(obj: any) {
    if (this.teacherModel.name == "") {
      this.message = "请输入教师姓名";
      this.displayP = true;
      return;
    }

    let body = JSON.stringify(this.teacherModel);
    this.http.post(this.updateTeacherUrl, body).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          this.getData();
          this.close(obj);
          this.message = "修改成功";
          this.displayP = true;
        }
        else {
          this.getData();
          this.message = "更新失败，请联系管理员！";
          this.displayP = true;
        }
      }
    );
  }
  /*****************************************编辑更新end******************************************** */

  /*****************************************导出-excel start******************************************** */
  teacherId: string = "%20";
  /**
   * 导出-excel
   */
  export() {
   
    let exportCondition="";
    if(this.searchContent==null || this.searchContent.trim()=="" || this.searchContent=="/"){
      exportCondition ="/%20";
    }
    else{
      exportCondition=this.searchContent;
    }
    console.log(exportCondition);
    console.log("去");
    let url = "teacher/getAllTeacher/" + this.pageSize + '/' + this.page;
    // 调用自己的服务
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().code == "0000" && res.json().data != null && res.json().data.length > 0) {
          let urlExcel = this.http.getExportExcelUrl("teacher/exportTeacher"+exportCondition);
          window.open(urlExcel);
          URL.revokeObjectURL(urlExcel);
        } else {
          this.message = "当前没有可用记录";
          this.displayP = true;
        }
      })

  }
  /*****************************************导出-excel end******************************************** */

  /*****************************************导入excel end******************************************** */
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
    let url = this.http.getExportExcelUrl("teacher/downExcel");
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
      this.message = "没有选择文件，请选择文件";
      this.displayP = true;
      return;
    }
    this.uploadFile();
    this.display = false;
  }
  /**
   * 上传文件--刘雅雯--2017年11月5日14:38:39
   */
  uploadFile() {
    var obj = this;
    this.uploader.queue[this.uploader.queue.length - 1].upload();
    //上传文件
    this.uploader.queue[this.uploader.queue.length - 1].onSuccess = function (response, status, headers) {
      //上传文件成功
      if (status == 200) {
        //上传文件后后去服务器返回的数据
        if (response != null) {
          let tempRes = JSON.parse(response);
          console.log(tempRes)
          if (tempRes.code == "0000") {
            obj.message = "上传成功";
            obj.displayP = true;
            obj.getData();
          } else {
            obj.message = "上传失败";
            obj.displayP = true;
          }
        } else {
          obj.message = "上传成功";
          obj.displayP = true;
        }
      } else {
        //上传文件后湖区服务器返回的数据错误
        obj.message = "失败";
        obj.displayP = true;
      }
    };
  }
  /*****************************************导入excel end******************************************** */

}

