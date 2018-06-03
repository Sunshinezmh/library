import { Component, OnInit, ViewChild, Renderer2, ElementRef, DoCheck } from '@angular/core';
import { BookTypeManagerModel } from '../../../models/book-type-manager.model';
import { Http, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
//引用拦截器服务
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { FileUploader } from 'ng2-file-upload';
import { ConfirmationService } from 'primeng/primeng';
import { fadeIn } from '../../../animations/fade-in';
@Component({
  selector: 'app-book-type-manager',
  templateUrl: './book-type-manager.component.html',
  styleUrls: ['./book-type-manager.component.css'],
  animations: [fadeIn]
})

export class BookTypeManagerComponent implements OnInit {

  filename = ""; //文件名 
  searchUrl = "";
  display = false;
  info = "";
  fileUrl = "";
  information = '图书类别名称';
  dataUrl: string = "";
  obj: any;
  importUrl = this.http.getExportExcelUrl("bookType/importBookType");//导入的地址

  //弹框提示信息
  message = "";
  displayP = false; //默认的值为 

  constructor(
    // 实例化拦截器提供的服务，实例化后才能用
    public http: HttpInterceptorService, private renderer2: Renderer2,
    private confirmService: ConfirmationService
  ) { }

  showDialog(string) {
    this.message = string;
    this.displayP = true;
  }
  /**
   * 杨晓风
   * 2017年11月5日08:52:05
   *modal框可拖拽
   */
  draggable() {
    $('.modal-dialog').draggable();
  }

  /*************表格所需要初始化的变量******************************** */
  btnAdd: boolean = true;
  btnDelete: boolean = true;
  btnImport: boolean = true;
  btnExport: boolean = true;
  arrbutes = ['name', 'remark'];
  title = ["类别名称", "备注"];
  isLink = [false, false, false, false];
  paging: boolean = false;
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

  ngOnInit() {
    // 页面一加载就调用此方法
    this.getData();

  }

  BookTypyModel = new BookTypeManagerModel();
  booktypeManagerModel = new BookTypeManagerModel();
  property: string;

  //添加-下拉框-加载所有类别--刘雅雯-2017年11月3日14:01:46
  typeOptions: string[];


  /************表格导入******************************** */
  /**
 * 导入数据excel：初始化
 */

  public uploader: FileUploader = new FileUploader({

    url: this.importUrl,
    method: "POST",
    itemAlias: "multfile",
    allowedFileType: ["xls", "xlxs"]

  });

  /**
     *  导入excel值表格按钮--刘雅雯--2017年11月6日08:36:42
     * @param info 索引值
     */
  import(info: any) {
    this.display = true;
  }

  /**
    * 下载模板
    */

  down() {
    let url = this.http.getExportExcelUrl("bookType/downBookType");
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

    //打印文件路径

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
   * 刘雅雯维护-2017年11月10日21:35:42
   * 调用onSuccess方法
   */
  uploadFile() {
    var obj = this;
    this.uploader.queue[this.uploader.queue.length - 1].onSuccess = function (response, status, headers) {
      // 上传文件成功
      if (status === 200) {
        // 上传文件后后获取服务器返回的数据
        let tempRes = JSON.parse(response);
        obj.getData();
        obj.refresh();
        obj.showDialog('上传成功');
      } else {
        // 上传文件后获取服务器返回的数据错误
        obj.showDialog('上传失败了');
      }
    };
    this.uploader.queue[this.uploader.queue.length - 1].upload();
  }

  /*******************导出****************************/
  /**
 * 导出-excel
 */
  export() {
    //王华伟修---根据条件导出--2017年12月2日
    let exportCondition="";
    if(this.condition==null || this.condition.trim()=="" ||this.condition=="/"){
      exportCondition ="/%20";
    }else{
      exportCondition=this.condition;
    }
     // json数据文件地址
    let url = "bookType/getAllType/" + this.page + "/" + this.pageSize;
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().code == "0000" && res.json().data != null && res.json().data.length>0) {
         let urlExcel = this.http.getExportExcelUrl('bookType/exportBookType'+exportCondition);
          window.open(urlExcel);
          URL.revokeObjectURL(urlExcel);
        }else{
           this.showDialog("当前没有可用记录！");
        }
      }
    )
  }
  /****************************************************** */
  
  /************根据条件查询******************************** */
  condition: string="";
  query(el: string) {
    this.condition=el.trim();
    // let url = "bookType/getBookTypeByConditon"+ el.toString() +"/"+this.page+"/"+this.pageSize;
    let url;
    if (el.toString() == "/") { //没有查询条件
      url = "bookType/getAllType/" + this.page + "/" + this.pageSize;
    }
    else {
      url = "bookType/getBookTypeByConditon" + el.trim() + "/" + this.page + "/" + this.pageSize;
    }
    console.log(el.trim());
      this.http.get(url).toPromise().then(
      res => {
        if (res.json().code == "0000") {

          this.data = res.json().data;
          this.total = res.json().data.length;
          this.obj = null;
        }
        else {
          this.showDialog(res.json().message);
        }
      }
    )
  }
  /*******************************************************************/



  //显示树
  treeURL = this.http.getExportExcelUrl("bookType/getAllType/1/10");

  /**
   * 更新树
   */
  refresh() {
    this.treeURL = this.http.getExportExcelUrl("bookType/getAllType/1/10");
  }

  /**
 * 页面加载显示后台的数据，赋给data，改变data的值，后台的值也就改变了。
 */
  getData() {
    // json数据文件地址
    let url = "bookType/getAllType/" + this.page + "/" + this.pageSize;

    this.http.get(url).subscribe(
      res => {

        this.data = res.json().data;
        this.total = res.json().data.length;

        this.obj = null;

      }
    )
  }

  /**
  * 分页设置
  * @param 
  */
  changepage(data: any) {
    this.page = data.page;
    this.pageSize = data.pageSize;
    let url = "book/getAllbook/" + this.page + "/" + this.pageSize;

    this.getData();
  }


  /************编辑信息模态框******************************** */

  edit(index: string, modal: HTMLElement) {
    this.getAllPtree();

    if (index.length > 1) {
      this.showDialog("只能选择一条记录！");
      return;
    }
    if (index.length < 1) {
      this.showDialog("请选择一条记录！");
      return;
    }
    modal.style.visibility = 'visible';
    this.booktypeManagerModel = this.data[index];

  }

  operatData(obj: any, modal: HTMLElement) {
    modal.style.visibility = 'visible';
    this.booktypeManagerModel = this.data[obj.data];
    this.property = "编辑";
  }

  //编辑时更新图书类别信息-刘雅雯额-2017年11月6日17:32:40
  updateBookType(e1: HTMLElement) {

    if (this.booktypeManagerModel.name == "") {
      this.showDialog("请将内容补充完整");
      return;
    }
    let url = "bookType/updateBookType";
    let body = JSON.stringify(this.booktypeManagerModel);

    this.http.post(url, body).toPromise().then(
      res => {

        if (res.json().code == "0000") {
          this.getData();
          this.refresh();
          this.showDialog("更新成功");
          this.close(e1);
        } else {
          this.getData();
          this.showDialog(res.json().message);
        }
      }
    );
  }

  /*************************************************************************/


  /**
  *  关闭模态框
  * @param el 
  */
  close(el: HTMLElement) {
    el.style.visibility = "hidden";
  }

  studentId: string = "%20";

  /*
  * 打开添加信息模态框
  */
  // @ViewChild("Modal") Modal:ElementRef

  open(el: HTMLElement, Modal: HTMLElement) {
    Modal.style.visibility = 'visible';
    this.getAllPtree();
    this.booktypeManagerModel = new BookTypeManagerModel();
    this.booktypeManagerModel.shelfId = "";
    this.booktypeManagerModel.operator = "";
    this.booktypeManagerModel.id = ""; //主键，必须要设置，如果为空会自动生成字段

  }

  //添加时保存图书类型信息-刘雅雯-2017年11月10日11:37:13
  save(el: HTMLElement) {

    if (this.booktypeManagerModel.name == null) {
      this.showDialog("请将内容补充完整");
      return;
    }

    let url = "bookType/addBookType";
    //读取父类的类型框是否隐藏的值
    var parentType = document.getElementById("ParentTypeShow");
    if (parentType.style.display == "none" || this.booktypeManagerModel.pId == null) {
      this.booktypeManagerModel.pId = "0";
    }
    let body = JSON.stringify(this.booktypeManagerModel);
    this.http.post(url, body).toPromise().then(
      res => {
        if (res.json().code == "0000") {

          this.refresh();
          this.getData();
          this.showDialog("添加成功");
          this.close(el);
        }
        else {
          this.showDialog(res.json().message);
        }
      }
    );
  }


  /**
   * 添加父类的类别--刘雅雯--2017年11月5日11:14:13
   * @param Modal 
   */
  saveParentType(el: HTMLElement, parentSelect: HTMLElement, btnAdd: HTMLElement) {

    if (el.innerText.toString() == "父类") {
      el.innerText = "切换子类";
      parentSelect.style.display = "none";

    } else {
      el.innerText = "父类";
      parentSelect.style.display = "block";
    }
  }



  //删除多条记录
  /**
     * 删除多行数据--李娜--2017年10月15日12:27:31
     * @param el 传入删除的索引值
     */
  deleteDatas(el: any) {
    let Option = new RequestOptions();
    let bookTypeIds: string = this.data[el[0]].id;
    for (let i = 1; i < el.length; i++) {
      bookTypeIds = bookTypeIds + "," + this.data[el[i]].id;
    }

    let url = "bookType/deleteBookTypes/" + bookTypeIds;
    this.http.post(url, null).toPromise().then(
      res => {
        this.getData();
        this.refresh();
        this.showDialog("删除成功");
      }
    );
    for (let i = 0; i < el.length; i++) {
      this.data.splice(el[i] - i, 1);
      this.total--;
    }

  }

  /**
   * 获取所有的父类类别--刘雅雯--2017年11月3日14:00:41
   */
  getAllPtree() {
    let url = "bookType/initTree";
    this.http.get(url).toPromise().then(
      res => {
        this.typeOptions = res.json().data;

      }
    )

  }
}








