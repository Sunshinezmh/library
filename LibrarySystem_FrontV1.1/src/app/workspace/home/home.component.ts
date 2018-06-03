import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { CarouselConfig } from 'ngx-bootstrap/carousel';
import { CarouselModule } from 'ngx-bootstrap';
import { HomeModel } from '../../models/homemodel';
import { BookUpdateModel } from '../../models/book.model';
import { HttpInterceptorService } from '../../shared/interceptorservice';
import { concat } from 'rxjs/observable/concat';



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [{ provide: CarouselConfig, useValue: { interval: 1500, noPause: true } }]
})
export class HomeComponent implements OnInit, AfterViewInit {
  logoURL = 'assets/image/dynamic.ico';
  imgSearchURL = 'assets/image/search.png';
  imageModel = new Array<BookUpdateModel>();
  userid = localStorage.getItem("appuserId");
  picture: string;
  constructor(public router: Router,
    private http: HttpInterceptorService
  ) {
    this.picture = "assets/image/bookback.jpg";
  }

  ngOnInit() {
    this.getData();
  }
  
  page: number = 1;
  pageSize: number = 3;
  totalNum: number = 0;
  totalPages: number = 0;
  returnPage: string;
  returnNumber: number;
  isbn: string;
  location: string;

  getData() {
    //学号
    let studentNo = localStorage.getItem("appuserId");
    let url = "";
    //如果没有登录

    if (studentNo == null) {
      url = "book/getFavorite/" + "%20";
    }
    //登录之后
    else {
      url = "book/getFavorite/" + studentNo;
    }
    //首先判断的是我们是否存储了以前的页数
    if (localStorage.getItem(this.returnPage) != null) {
      this.page = parseInt(localStorage.getItem(this.returnPage))
      //王雅静修改
      if (this.page == this.totalPages) {
        this.page = 1;
        url = url + "/" + this.page + "/" + this.pageSize;
      } else {
        //如果存储过肯定要加1
        this.page++;
        url = url + "/" + this.page + "/" + this.pageSize;
      }
    }
    else {
      url = url + "/" + this.page + "/" + this.pageSize;
    }

    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000") {
          let models = res.json().data.listRows
          console.log(res.json().data.listRows)
          for (let i = 0; i < models.length; i++) {
            if (models[i].picture != null) {
              models[i].picture = this.http.fastDsfUrl + models[i].picture;
              models[i].picture = models[i].picture;
            }
          }
          this.imageModel = models;
          this.totalNum = res.json().data.totalNum;
          this.totalPages = Math.ceil(this.totalNum / this.pageSize);
        }
      })
  }

  //i代表索引值
  private keyup(i: any) {
    this.isbn = this.imageModel[i].isbn;
    this.location = this.imageModel[i].location;
    switch(this.location) { 
       case "万达": { 
        this.location = "1";
          break; 
       } 
       case "学校": { 
        this.location = "2";
          break; 
       } 
       case "建工": {
        this.location = "3"; 
          break;    
       }
    }


    this.router.navigateByUrl("/book-info/" + this.isbn + "/" + this.location);

  }
  //点击下一批  
  nextFavoriteInfo() {
    localStorage.setItem(this.returnPage, this.page.toString())
    if (this.page < this.totalPages) {
      this.page = parseInt(localStorage.getItem(this.returnPage))
      console.log(this.page);
      this.page++;
      // console.log("第二次"+this.page);
      this.getData();
    } else {
      this.page = 1;
      this.getData();
    }
  }
  //点击上一批
  toptFavoriteInfo() {

    //学号
    let studentNo = localStorage.getItem("appstudentNo");
    let url = "";
    //如果没有登录

    if (studentNo == null) {
      url = "book/getFavorite/" + "%20";
    }
    //登录之后
    else {
      url = "book/getFavorite/" + studentNo;
    }
    if (this.page < this.totalPages) {
      //判断是否上一批还有数据
      if (this.page != 1) {
        this.page--;
        url = url + "/" + this.page + "/" + this.pageSize;
        this.http.get(url).toPromise().then(
          res => {
            if (res != null && res.json().code == "0000") {
              let models = res.json().data.listRows
              console.log(res.json().data.listRows)
              for (let i = 0; i < models.length; i++) {
                if (models[i].picture != null) {
                  models[i].picture = this.http.fastDsfUrl + models[i].picture;
                  models[i].picture = models[i].picture;
                }
              }
              this.imageModel = models;
              this.totalNum = res.json().data.totalNum;
              this.totalPages = Math.ceil(this.totalNum / this.pageSize);
            }
          })
      } else {
        this.page = 1;
      }

    }
  }

  @ViewChild("content") content: ElementRef
  start: any;
  delta: any;

  //界面初始化后，绑定滑动事件 ---平滑幅度太小了，所以注释掉了
  ngAfterViewInit() {
    // this.touchStart();
    // this.touchMove();
    // this.touchEnd();
  }
  // 滑动开始
  touchStart() {
    let obj = this;
    this.content.nativeElement.addEventListener('touchstart', function (e) {
      // 记录开始按下时的点
      let touches = e.touches[0];
      obj.start = {
        x: touches.pageX,
        y: touches.pageY
      };
    });
  }

  /**
   * 滑动过程中
   */
  touchMove() {
    let obj = this;
    this.content.nativeElement.addEventListener('touchmove', function (e) {
      var touches = e.touches[0];
      //记录下横纵的移动距离
      obj.delta = {
        x: touches.pageX - obj.start.x,
        y: touches.pageY - obj.start.y
      };
    });
  }

  /**
   * 滑动结束
   */
  touchEnd() {
    let obj = this;
    this.content.nativeElement.addEventListener('touchend', function (e) {
      if (obj.delta != null && obj.delta.x < -80) {
        obj.toBookstType();
      }
    });
  }

  public toBookstType() {
    this.router.navigateByUrl("workspace/book-type");
  }


  
  /**
   * 微信扫一扫测试--王华伟--2018年5月27日09:46:08
   */
  Scan() {
    let urlCurrentPage = encodeURIComponent(location.href.split('#')[0]);
    let url = 'borrowing/Scan'+'/'+urlCurrentPage;
    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000") {
          let data = res.json().data;
          let signature = data.signature;
          let timestamp = data.timestamp;
          let nonceStr = data.nonceStr;
          wx.config({
            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: 'wxf7be5ce2c5dc091d', // 必填，公众号的唯一标识
            timestamp: timestamp, // 必填，生成签名的时间戳
            nonceStr: nonceStr, // 必填，生成签名的随机串
            signature: signature,// 必填，签名
            jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表
          });
          wx.scanQRCode({
            needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
              var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
            }
          });
        }
      })
  }
}