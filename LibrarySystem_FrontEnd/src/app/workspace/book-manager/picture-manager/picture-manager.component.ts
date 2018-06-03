import { Component, OnInit,ElementRef } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { fadeIn } from '../../../animations/fade-in';
import { FileUploader } from 'ng2-file-upload';
import { Observable } from 'rxjs/Observable';
import { bookInfoManagerModel, BookInfoAddModel } from '../../../models/book-info-manager.model'
import { BookTypeManagerModel } from '../../../models/book-type-manager.model';

@Component({
  selector: 'app-picture-manager',
  templateUrl: './picture-manager.component.html',
  styleUrls: ['./picture-manager.component.css'],
  animations: [fadeIn] 
})
export class PictureManagerComponent implements OnInit {
  picture: string;
  editPicture: boolean = true;  //修改图片
  booktypeModel = new BookTypeManagerModel();
  bookInfoModel = new bookInfoManagerModel();
  bookpircture = new BookInfoAddModel();
  bookInfoList = new Array<bookInfoManagerModel>();//书籍名称
  bookInfoisbnModel=new bookInfoManagerModel(); //用户获取isbn；
  bookInfoName //用于判断书名是否为空
  autobookpicture//用于判断自动获取图片是否成功。
  bookinfourl="book/getBookinfoName";
  bookpicture="book/updateTBookpicture";
  bookisbn;
  isbn;
  uploadPictureUrl = this.http.getExportExcelUrl("book/uploadPicture");//上传图片的URL
  constructor(
    private el:ElementRef,
    private http: HttpInterceptorService,
    public activeRoute: ActivatedRoute,
    public router: Router
  ) { 
    this.picture = '';
  }

  ngOnInit() {
  //页面初始化加载显示没有图片的书名
  this.bookinfo();
  this.bookInfoName=22;
  }
    //提示框
    displayP=false;
    message:"";
   /**
  * 提示框信息--刘雅雯--2017年11月9日16:23:10
  * @param string 提示框信息
  */
 showDialog(string){
  this.message=string;
  this.displayP=true;
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
 * 页面初始化加载显示没有图片的书名
 */
 bookinfo(){
   this.http.get(this.bookinfourl).toPromise().then(
     res => {      
       if(res != null && res.json().code == "0000"){  
         for(let i=0 ; i<30;i++){           
               this.bookInfoList[i]=res.json().data[i];      
               this.bookInfoisbnModel[i]=res.json().data[i].isbn;  
               console.log( this.bookInfoisbnModel[i])    ;    
            }
       }
       else{
        //为空所以提示警告。
        this.bookInfoName=null;
       }
     }
   )
}
upbookoption(el: HTMLElement){
  this.isbn=el;
  var url="book/getSearchBookByISBN/"+el;
  var urltwo="book/getBookinfopicture/"+el;///查看图片是否已经获取。
    this.http.get(url).toPromise().then(
     res => {      
       if(res != null && res.json().code == "0000"){  
        this.http.get(urltwo).toPromise().then(
          ress => {      
            if(ress != null && ress.json().code == "0000"){
               this.autobookpicture=1;
            }
          }) 
       }}) 
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
        data = data.substring(21, length);
        obj.bookInfoModel.picture = data;

        localStorage.setItem('pictureUrl', obj.bookInfoModel.picture);
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
      this.uploader.queue[0].upload();// 开始上传
    };
    this.uploader.queue[0].upload();// 开始上传
  }

  loadPicture(){
    var picture=localStorage.getItem('pictureUrl');
    this.bookpircture.picture=picture;
    this.bookpircture.isbn=this.isbn;
   
    let body = JSON.stringify(this.bookpircture);
        this.http.post(this.bookpicture,body).toPromise().then(
        res => {
        if(res.json().code=="0000"){         
          $("#my-modal-alert").modal("toggle");  
          $(".modal-backdrop").remove();//删除class值为modal-backdrop的标签，可去除阴影  
          //设置提示信息  
          $("#message").text("图片上传成功");   
        } 
        else  {
          $("#my-modal-alert").modal("toggle");  
          $(".modal-backdrop").remove();//删除class值为modal-backdrop的标签，可去除阴影  
          //设置提示信息  
          $("#message").text("图片上传失败，请重新尝试");   
        }
      }
    )
  }
}
