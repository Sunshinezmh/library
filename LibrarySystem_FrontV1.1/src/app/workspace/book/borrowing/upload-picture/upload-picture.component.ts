import { Component, OnInit,ElementRef } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { HttpInterceptorService } from '../../../../shared/interceptorservice';
import { Location } from '@angular/common';
import { FileUploader } from 'ng2-file-upload';
import { BorrowingTwoModel } from '../../../../models/borrowing-two.models';

@Component({
  selector: 'app-upload-picture',
  templateUrl: './upload-picture.component.html',
  styleUrls: ['./upload-picture.component.css'],
})
export class UploadPictureComponent implements OnInit {
  uploadPictureUrl = this.http.getExportExcelUrl("book/uploadPicture");//上传图片的URL
  editPicture: boolean = true;  //修改图片
  picture:string;
  borrowDetailsStay = new Array<BorrowingTwoModel>();
  borrowModel=new BorrowingTwoModel();
   borrowMessage:string;
   public msgs: any;
  constructor(
    private route: Router,
    private el:ElementRef,
    //拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    private location: Location,
    public router: Router,
    private activeRoute: ActivatedRoute
  ) { 
    this.picture='';
  }

  ngOnInit() {
  }

  public borrowing(): void {
    this.location.back();
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
        //修改页面图片
        let el: Element = document.getElementById("wizardPicturePreview");
        el.setAttribute("src", data);
        //为防止fastdfs服务器发生改变，截取ip后边的图片路径保存到数据库中-张婷-2017-11-13 12:43:15
        length = data.length;
        data = data.substring(21, length);
        this.picture = data;        
        localStorage.setItem('picUrl',data);
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
  /**
   * 还书
   * @param borrow 
   */
  returnBook() {
    
    let bookInfoAddModel = this.setBookInfoAddModel();
    if(bookInfoAddModel.location=="1"){
      bookInfoAddModel.location="万达"
    }
    if(bookInfoAddModel.location=="2"){
      bookInfoAddModel.location="学校"
    }   
    let url = "borrowing/returnBorrowingRecordAPP";

    console.log(bookInfoAddModel)
    let body=JSON.stringify(bookInfoAddModel);
    console.log(body)
    this.http.post(url, body).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        // this.borrowMessage= "还书成功！"
        // setTimeout(() => {
        //   this.borrowMessage = "";
        // }, 1000)
     
        // if(this.borrowDetailsStay==null){
        //   this.borrowDetailsStay=null;
        // }      
        this.msgs = [{
          severity: 'success',
          summary: '提示',
          detail: "还书成功！"
        }]
        this.location.back();
      } else {  
        // this.borrowMessage = "还书失败，请联系管理员"; setTimeout(() => {
        //   this.borrowMessage = "";
        // }, 1000)
        this.msgs = [{
          severity: 'error',
          summary: '提示',
          detail: "还书失败！"
        }]
      }
    }).catch(res => {
      this.borrowMessage = "还书失败，请联系管理员"; setTimeout(() => {
        this.borrowMessage = "";
      }, 1000)
    })

  }
  private setBookInfoAddModel(): BorrowingTwoModel 
  {
    let bookInfoAddModel = new BorrowingTwoModel();
    bookInfoAddModel.userId=localStorage.getItem("appuserId"); 
    bookInfoAddModel.isbn = this.activeRoute.snapshot.paramMap.get("isbn");
    bookInfoAddModel.location=this.activeRoute.snapshot.paramMap.get("location");
    bookInfoAddModel.remark = localStorage.getItem('picUrl');
    return bookInfoAddModel;
  }
}
