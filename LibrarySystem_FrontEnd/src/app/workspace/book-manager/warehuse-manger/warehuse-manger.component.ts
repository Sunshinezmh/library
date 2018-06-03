import { Component, OnInit , ElementRef} from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { BookTypeManagerModel } from '../../../models/book-type-manager.model';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { FileUploader } from 'ng2-file-upload';
import { bookInfoManagerModel, BookInfoAddModel } from '../../../models/book-info-manager.model'
import { fadeIn } from '../../../animations/fade-in';
import { BarcodeModel } from '../../../models/barcode.model';

@Component({
  selector: 'app-warehuse-manger',
  templateUrl: './warehuse-manger.component.html',
  styleUrls: ['./warehuse-manger.component.css'],
  animations: [fadeIn]
})
export class WarehuseMangerComponent implements OnInit {
  picture: string;
  editPicture: boolean = true;  //修改图片
  booktypeModel = new BookTypeManagerModel();
  bookInfoModel = new bookInfoManagerModel();
  uploadPictureUrl = this.http.getExportExcelUrl("book/uploadPicture");//上传图片的URL
  public msgs: any;
  constructor(
    private el:ElementRef,
    private http: HttpInterceptorService,
    public activeRoute: ActivatedRoute,
    public router: Router
  ) {
    //页面加载默认图片
    this.picture = '';
  }


  ngOnInit() {
    this.getBookTypeOpetion();
    this.getbookdress();
    this.bookInfoModel.useNum=1;
    this.bookInfoModel.publishtime= "2017-01-02";
  }

  /***********当isbn未输入时，提醒是必填项 -张婷***************/
  displayP: boolean;
  message: "";

  showDialog(string) {
    this.message = string;
    this.displayP = true;
  }

  inputMsg(ISBN: any) {
    if (ISBN.value == "") {
      // this.showDialog("亲，请先填写ISBN项！");
      this.msgs = [{
        severity: 'warn',
        summary: '提示',
        detail: "亲，请先填写ISBN项！"
      }]
    }

    return;
  }

  // 打印条码页面跳转--郭晶晶--2017年11月24日15:49:48
  print() {
    let obj = new BarcodeModel();
    if (this.bookInfoModel.name == null || this.bookInfoModel.useNum == null || this.bookInfoModel.searchNum == null) {
      // this.showDialog("数量，书名，索书号需要填写完整");
      // alert("数量，书名，索书号需要填写完整");
      this.msgs = [{
        severity: 'warn',
        summary: '提示',
        detail: "数量，书名，索书号需要填写完整！"
      }]
      return;
    }
    if (this.bookInfoModel.name == "" || this.bookInfoModel.useNum.toString() == "" || this.bookInfoModel.searchNum == "") {
      // this.showDialog("数量，书名，索书号需要填写完整");
      // alert("数量，书名，索书号需要填写完整");
      this.msgs = [{
        severity: 'error',
        summary: '提示',
        detail: "数量，书名，索书号需要填写完整！"
      }]
      return;
    }
    if (this.bookInfoModel.useNum < 1) {
      // this.showDialog("数量应该大于0");
      // alert("数量应该大于0");
      this.msgs = [{
        severity: 'warn',
        summary: '提示',
        detail: "数量应该大于0"
      }]
      return;
    }
    obj.printBookName = this.bookInfoModel.name;
    obj.printNumber = this.bookInfoModel.useNum;
    obj.printSearchNum = this.bookInfoModel.searchNum;
    alert(this.bookInfoModel.typeName);
    obj.typeName = this.bookInfoModel.typeName;
    let objList = new Array();
    objList.push(obj)
    localStorage.setItem("printbarcode", JSON.stringify(objList))
    this.router.navigateByUrl("workspace/print-page");
  }

  typeOptions = new Array();
  sonTypeOptions = new Array();
  bookaddress= new Array();


  /**
    * 获取父分类-张明慧-2017年10月26日18:05:27
   */
  getBookTypeOpetion() {
    let url = "bookType/initTree";
    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000") {
          this.typeOptions = res.json().data;
          console.log(this.typeOptions);
          this.getSubByID(this.typeOptions[0].id);
        }
      }
    );
    this.auto();
  }


  /**
    * 获取子分类-张明慧-2017年10月26日18:05:27
   */
  getSubByID(parentId: string) {   
    let url = "bookType/getSubByID/" + parentId;
    this.http.get(url).toPromise().then(
      res => {
        this.sonTypeOptions = res.json().data;
      }
    )

    this.auto();

  }

  /** 
   * 
   * 获取书籍地址-王雪芬-2018年5月5日15:54:24
   * 
   */
  getbookdress(){
    let url="book/getAllLocation/1/10";
    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000") {
          this.bookaddress = res.json().data;
          console.log(this.bookaddress)
        }
      }
    );
  }



  //上传配置
  public uploader: FileUploader = new FileUploader(
    {
      url: this.uploadPictureUrl,
      method: "POST",
      removeAfterUpload: true,
      itemAlias: "multfile",
      allowedFileType: ["image"]
    }
  )


auto(){
  
  if(this.bookInfoModel.isbn!=null && this.bookInfoModel.searchNum!=null && this.bookInfoModel.name!=null)
    {  
      if (this.bookInfoModel.typeName !=null && this.bookInfoModel.typeName != "") {
        if (this.bookInfoModel.typeName.trim() != "") {
          this.save(this.bookInfoModel.publishtime);    
          this.bookInfoModel.useNum=1;       
        }
      }    
    }   
}

  //选择上传图片
  public selectedFileOnChanged(file: HTMLInputElement) {
    let length: number;
    let obj = this;
    this.editPicture = !this.editPicture;
    let fileName = file.value;
    let fileDot: string = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
    let images = ["bmp", "png", "jpg"]
    this.uploader.queue[0].onSuccess = function (response, status, headers) {
      //上传文件成功
      if (status == 200) {       
        // 上传文件后获取服务器返回的数据
        let temRes = JSON.parse(response);
        // let data = "http://" + temRes.data;
        let data = temRes.data;
        console.log(data)
        console.log(temRes.data)
        //修改页面图片
        let el: Element = document.getElementById("wizardPicturePreview");
        el.setAttribute("src", data);
        //为防止fastdfs服务器发生改变，截取ip后边的图片路径保存到数据库中-张婷-2017-11-13 12:43:15
        length = data.length;
        data = data.substring(22, length);
        obj.bookInfoModel.picture = data;

        localStorage.setItem('picUrl', obj.bookInfoModel.picture);
        let el1: Element = document.getElementById("wizard-picture");
      }
      else {
        // 上传文件后获取服务器返回的数据错误
        // this.showDialog("上传图片失败");
        // alert("上传图片失败");
        this.msgs = [{
          severity: 'error',
          summary: '提示',
          detail: "上传图片失败！"
        }]
      }

    };
  
    this.uploader.queue[0].upload();// 开始上传
  }
  
  updateInformation(el: HTMLElement) {
   
    let body = JSON.stringify(this.bookInfoModel);
    let url = "book/uploadPicture";
    
    this.http.post(url, body).toPromise().then(
      res => {
        if (res.json().code == "0000") {
          // alert("修改成功")
          this.msgs = [{
            severity: 'success',
            summary: '提示',
            detail: "修改成功!"
          }]
        }
        else {
          // alert("修改失败")
          this.msgs = [{
            severity: 'error',
            summary: '提示',
            detail: "修改失败！"
          }]
        }
      }
    )
  }

  getBookInfo() {
    let url = "book/getBookBasicByISBN/" + this.bookInfoModel.isbn;
    console.log(this.bookInfoModel.isbn)
    this.http.get(url).toPromise().then(res => {
       
      if (res != null && res.json().code == "0000") {
        this.bookInfoModel = res.json().data;
        this.picture = this.bookInfoModel.picture;
        this.getSearchNmu();
        this.bookInfoModel.useNum=1;
      }
      else {
        this.showDialog(res.json().message);
      }
    })

 this.getSearchNmu();
  }
  getSearchNmu() {
    let url = "book/getSearchSumByISBN/" + this.bookInfoModel.isbn;
    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        console.log(res.json().data)
        this.bookInfoModel.searchNum = res.json().data;
      } else {
        // this.showDialog(res.json().message);
        
      }
    // }).catch(e => this.showDialog("获取索书号失败，请检查网络情况或服务器状态"))
  })
  }

  public dateToString(str: number): string {
    let newDate: Date = new Date(str);
    newDate.setTime(str);
    let fmt = "yyyy-MM-dd";
    var o = {
      "M+": newDate.getMonth() + 1,         //月份  
      "d+": newDate.getDate(),          //日   
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (newDate.getUTCFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

  }

  


  // save() {
  save(publishTime: string) {
    
    if (this.bookInfoModel.typeName == null || this.bookInfoModel.typeName == "") {
      // this.showDialog("请选择要添加的图书子类别！")
      // alert("请选择要添加的图书子类别！")
      this.msgs = [{
        severity: 'warn',
        summary: '提示',
        detail: "请选择要添加的图书子类别！"
      }]
      return;
    }
    if (this.bookInfoModel.typeName.trim() == "") {
      // this.showDialog("请选择要添加的图书子类别！")
      // alert("请选择要添加的图书子类别！")
      this.msgs = [{
        severity: 'warn',
        summary: '提示',
        detail: "请选择要添加的图书子类别！"
      }]
      return;
    }
    // if (this.bookInfoModel.totalpage <= 1000) {     
      this.bookInfoModel.publishtime = publishTime;
      let bookInfoAddModel = this.setBookInfoAddModel();
      let url = "book/addBookInfo";
      this.http.post(url, JSON.stringify(bookInfoAddModel)).toPromise().then(res => {        
        if (res != null && res.json().code == "0000") {
          // this.showDialog("图书入库成功！");
          // alert("图书入库成功！")
          this.msgs = [{
            severity: 'success',
            summary: '提示',
            detail: "图书入库成功！"
          }]
          this.bookInfoModel = new bookInfoManagerModel();
          this.bookInfoModel.useNum=1;
          document.getElementById("isbn1").focus();
        } else {
          // alert("入库失败，请重试！")
          // this.showDialog("入库失败，请重试！")
          this.msgs = [{
            severity: 'error',
            summary: '提示',
            detail: "入库失败，请重试！"
          }]
        }
        localStorage.removeItem('picUrl');
      }).catch(res => this.showDialog("保存失败，请检查网络状态。"));
    }
    // else {
    //   // this.showDialog('buxing');
    //   alert("失败")  
    //   }
  // }
  private setBookInfoAddModel(): BookInfoAddModel {
    let bookInfoAddModel = new BookInfoAddModel();
    bookInfoAddModel.bookName = this.bookInfoModel.name;
    bookInfoAddModel.author = this.bookInfoModel.author;
    bookInfoAddModel.content = this.bookInfoModel.content;
    bookInfoAddModel.isbn = this.bookInfoModel.isbn;
    bookInfoAddModel.picture = localStorage.getItem('picUrl');
    bookInfoAddModel.picture = this.bookInfoModel.picture;
    bookInfoAddModel.primCost = this.bookInfoModel.primCost;
    bookInfoAddModel.publishPlace = this.bookInfoModel.publishPlace;
    bookInfoAddModel.rank = this.bookInfoModel.rank;
    bookInfoAddModel.searchNum = this.bookInfoModel.searchNum;
    bookInfoAddModel.summary = this.bookInfoModel.summary;
    bookInfoAddModel.typeName = this.bookInfoModel.typeName;
    bookInfoAddModel.totalpage = this.bookInfoModel.totalpage;
    bookInfoAddModel.useNum = this.bookInfoModel.useNum;
    bookInfoAddModel.publishtime = this.bookInfoModel.publishtime;
    bookInfoAddModel.owner = this.bookInfoModel.owner;
    return bookInfoAddModel;
  }
}


