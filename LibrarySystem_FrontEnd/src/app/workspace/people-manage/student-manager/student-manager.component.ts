import { Component, OnInit, ViewChild, Renderer2, ElementRef } from '@angular/core';
//引用自己的model
import { StudentModel } from '../../../models/student.model'
import { StudentAddModel } from '../../../models/studentAdd.model';
import { Http, RequestOptions, Headers } from '@angular/http';

//-----拦截器服务
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

import { FileUploader } from 'ng2-file-upload';
import { fadeIn } from '../../../animations/fade-in';
import { Router } from '@angular/router';


@Component({
  selector: 'app-student-manager',
  templateUrl: './student-manager.component.html',
  styleUrls: ['./student-manager.component.css'],
  animations: [fadeIn]

})

export class StudentManagerComponent implements OnInit {
  /**
   * 杨晓风
   * 2017年11月5日08:52:05
   *modal框可拖拽
   */
  draggable() {
    $('.modal-dialog').draggable();
  }

  private institutionId;
  filename = ""; //文件名
  display = false;

  info = "";
  fileUrl = "";

  importUrl = this.http.getExportExcelUrl("student/importStudent");
  constructor(
    // 拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    public router: Router
  ) { }

  //实例化model
  studentModel = new StudentModel();
  addStudentModel = new StudentAddModel();

  //modal框的名字
  property: string;

  ngOnInit() {
    // 页面已加载就调用此方法
    this.getData();
  }


  /*******************************************表格Start*********************************************** */
  // 初始化表格数据--张明慧-2017年10月15日12:33:12
  btnAdd: boolean = true;
  btnDelete: boolean = true;
  btnEdit = true;
  btnImport: boolean = true;
  btnExport: boolean = true;
  title = ['学号', '姓名', '专业', '班级', '手机号', 'Email地址'];
  arrbutes = ["code", "name", "professionName", "classesName", "telNum", "email"];
  isLink = [false, true, false, false, false];
  paging: boolean = false;
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
  private searchContent = "%20";//查询条件
  information = '班级/姓名/学号/专业/电话/邮箱';
  searchUrl = '';
  dataUrl: string;
  displayP: boolean;
  message: '';
  condition: string="";

  showDialog(string) {
    this.message = string;
    this.displayP = true;
  }



  query(el: string) {
    this.condition=el.trim();
    let searchUrl;
    if (el.toString() == "/") {
      searchUrl = "student/getAllStudent";
    }
    else {
      searchUrl = "student/getStudentByCondition" + el.trim() + "/" + this.page + "/" + this.pageSize;
    }
    //模糊查询的路径
    let sUrl = this.searchUrl;
    this.http.get(searchUrl).subscribe(
      //表格联动
      res => {
        if (res.json().code == "0000") {
          this.data = res.json().data;
          this.total = res.json().data.length;
          this.obj = null;
        } else {
          this.data = [];
        }
      }
    )
    //url清空
    this.searchUrl = '';
  }

  /****************************************end******************************************** */

  /**
 * 添加的保存事件--张明慧-2017年10月15日12:29:50
 */

  save(el: HTMLElement) {
    this.addStudentModel.id = "";
    let body = JSON.stringify(this.addStudentModel);
    let url = "student/addStudent";
    this.http.post(url, body).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          this.showDialog("添加学生信息成功！")
          el.style.visibility = "hidden";
          this.clearModelData();//清除模态框数据
          this.getData();//刷新表格
          this.refresh();
        }
        else {
          this.showDialog(res.json().message);
        }
      }

    );

  }
  /*****************************************添加end******************************************** */


  /*****************************************删除start******************************************** */


  /**
   * 删除多行数据--张明慧--2017年10月15日12:27:31 -修改删除方法--郭晶晶--2017年11月26日11:32:58
   * @param el 
   */
  deleteDatas(el: any) {
    let first: string = el[0];
    let studentIds: string = this.data[first].id;
    for (let i = 1; i < el.length; i++) {
      let j = el[i];
      studentIds = studentIds + "," + this.data[j].id;
    }
    let url = "student/deleteStudentBytudentCodes/" + studentIds;
    this.http.post(url, null).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          let i = 0;
          this.getData();
          this.refresh();
          this.showDialog("删除学生信息成功！")
        }
        else {
          this.showDialog("删除失败");
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
    let url = "student/getAllStudent";
    // 调用自己的服务
    this.http.get(url).subscribe(
      res => {
        if (res.json().code == "0000") {
          this.data = res.json().data;
          this.total = res.json().data.length;
        } else {
          this.showDialog(res.json().message);
        }
      })
  }
  /**
 * 表格按钮点击事件，点击姓名跳转首页，同时将点击的名字保存在 localStorage中
 * @param el 
 */
  linkClick(el: any) {
    const na = localStorage.setItem('studentName', this.data[el].name);
    this.data.forEach(item => {
      this.router.navigateByUrl('workspace/borrowing-manager');
    })
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
    this.addStudentModel = new StudentAddModel();
  }

  /**
    * 编辑信息模态框
    */
  edit(obj, editModal) {
    if (obj.length > 1) {
      this.showDialog("只能选择一条记录！");
      return;
    }
    if (obj.length < 1) {
      this.showDialog("请选择一条记录！");
      return;
    }
    this.addStudentModel = this.data[obj];

    editModal.style.visibility = 'visible';
  }

  /**
   * 关闭模态框
   * @param el 
   */
  close(el: HTMLElement) {
    el.style.visibility = "hidden";
  }
  operatData(obj: any, modalEdit: HTMLElement) {
    this.studentModel = this.data[obj.data];
    this.property = "编辑";
    modalEdit.style.visibility = 'visible';

  }

  /***********************模态框的打开、编辑、删除end******************************************** */

  /***************************编辑更新start******************************************** */

  updateStudent(e1: HTMLElement) {

    let updateStudentUrl = "student/updateStudent";
    let body = JSON.stringify(this.addStudentModel);
    this.http.post(updateStudentUrl, body).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          this.showDialog("更新学生信息成功！！")
          e1.style.visibility = "hidden";

          this.clearModelData();//清除模态框数据
          this.getData();//刷新表格
          this.refresh();
        }
        else {
          this.getData();
          this.showDialog(res.json().message);
        }
      }
    );
  }
  /*****************************************编辑更新end******************************************** */

  /*****************************************树结构start******************************************** */
  /**
    * tree的使用--张明慧--2017年10月15日12:38:08
    */
  treeURL = this.http.getExportExcelUrl("student/initTree");

  /**
   * 刷新树--刘雅雯--2017年11月17日17:46:12
   */
  refresh() {
    this.treeURL = this.http.getExportExcelUrl("student/initTree");
  }

  treeClick(el: any) {

    this.obj = el;
    //拼url
    let showTableUrl = "student/getStudentByCondition/" + el.name + "/" + this.page + "/" + this.pageSize;
    //表格获取数据
    this.http.get(showTableUrl).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          this.data = res.json().data;
        }
        else {
          this.showDialog("该机构下没有学生！");
          this.data = new Array();
          this.total = 0;
        }
        this.searchURL = null;
      }
    )
  }
  /*****************************************end******************************************** */

  /*****************************************导出-excel start******************************************** */
  studentId: string = "%20";
  like: string = "%20";
  /**
   * 导出-excel
   */
  export() {
    let exportCondition="";
    if(this.condition==null || this.condition.trim()==""){
      exportCondition ="/%20";
    }else{
      exportCondition=this.condition;
    }
    let url = "student/getAllStudent/";
    // 调用自己的服务
    this.http.get(url).subscribe(
      res => {
        if (res!= null && res.json().code == "0000" && res.json().data!= null && res.json().data.length>0) {
          let urlExcel = this.http.getExportExcelUrl('student/exportStudent'+ exportCondition);
          console.log(urlExcel);
          console.log("wanghuawei");
          window.open(urlExcel);
          URL.revokeObjectURL(urlExcel);
        } else {
          this.showDialog("没有可用的记录");
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
    let url = this.http.getExportExcelUrl("student/downExcel");
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
   * @param event 选中文件--刘雅雯--2017年10月15日16:47:22
   */
  selectedFileOnChanged(event: any) {
    //打印文件选择名称
    this.filename = event.target.value;
  }

  /**
   * 导入数据excel：初始化
   */
  public uploader: FileUploader = new FileUploader({
    url: this.importUrl,
    method: "POST",
    itemAlias: "multfile",
    allowedFileType: ["xls", "xlxs"]

  });
  upload() {
    if (this.filename == "") {
      this.showDialog("没有选择文件，请选择文件");
      return;
    }
    this.uploadFile();
    this.display = false;
  }

  uploadFile() {
    var obj = this;
    //上传
    this.uploader.queue[this.uploader.queue.length - 1].onSuccess = function (response, status, headers) {
      //上传文件成功
      if (status == 200) {
        //上传文件后后去服务器返回的数据
        let tempRes = JSON.parse(response);
        obj.getData();
        obj.refresh();
        obj.showDialog("上传成功");
      } else {
        //上传文件后湖区服务器返回的数据错误
        this.showDialog("失败");
      }
    };
    this.uploader.queue[this.uploader.queue.length - 1].upload();
  }
  /*****************************************导入excel end******************************************** */


  institutionOptions: string[]//定义学院门类下拉框
  classOptions: string[]//定义班级类别下拉框
  professionTitleOptions: string[]//定义专业名称下拉

  /**
    * 获取学院下拉框-张明慧-2017年9月25日11:49:19
    */
  getInstitutionOptions() {
    let url = "basicInfo-web/organization/queryAllInstitutions";
    this.http.get(url).toPromise().then(
      res => {
        this.institutionOptions = res.json().data;
      }
    );
  }

  onchangeInstitution(i: any) {
    this.getProfessionTitleOptions(i);
  }

  /**
   *  获取专业下拉框-张明慧-2017年10月31日14:00:49
   */
  getProfessionTitleOptions(ProfessionCategory: string) {
    let url = "basicInfo-web/organization/queryProfession/" + ProfessionCategory;
    this.http.get(url).toPromise().then(
      res => {
        this.professionTitleOptions = res.json().data;
      }
    );
  }

  onchangeProfession(i: any) {
    this.getClassOptions(i);
  }

  /**
   * 获取班级下拉框-张明慧-2017年9月25日11:49:27
   */
  getClassOptions(SubjectCategory: string) {
    let url = "basicInfo-web/divideStudent/queryClassByProfessionIdForLab/" + SubjectCategory;
    this.http.get(url).toPromise().then(
      res => {
        this.classOptions = res.json().data;
      }
    );
  }

  /**
   * 清空模态框数据 郑晓东 2017年11月13日17点42分
   */
  clearModelData() {
    this.addStudentModel = new StudentAddModel();
  }
}


