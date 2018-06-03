import { Component, OnInit, ViewChild, ElementRef, ViewChildren, QueryList, AfterViewInit, AfterContentInit, OnChanges, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { BorrowDetailModel } from '../../../models/borrow-detail-model';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { ReservationViewModel } from '../../../models/reservation-details-model';

@Component({
  selector: 'app-reservebook',
   templateUrl: './reservebook.component.html',
   styleUrls: ['./reservebook.component.css']

})
//当使用这个组件的时候，我们需要导入我们的服务ReservationService
export class ReservebookComponent implements OnInit, AfterViewInit {
  Url = "assets/image/search.png";
  //预约中可借阅书籍
  borrowReservations = new Array<ReservationViewModel>();
  //预约中不可借阅书籍
  noBorrowReservations = new Array<ReservationViewModel>();
   models=new Array<ReservationViewModel>();
  backPicture = "assets/image/bookback.jpg";

  @ViewChildren("linewrapper") lineWrapperList: QueryList<ElementRef>;
  @ViewChildren("nomalwrapper") nomalWrapperList: QueryList<ElementRef>;
  @ViewChildren("scorewrapper") scoreWrapperList: QueryList<ElementRef>;
  lastX: any;
  lastXForMobile: any;

  // 当前左滑的对象
  currentLeftObj: HTMLDivElement;

  // 上一个左滑的对象
  previousLeftObj: HTMLDivElement;
  //用于记录触摸时点击的点
  start: any;

  delta: any;


  constructor(
    private route: Router,
    //拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService
  ) {

  }

  //进行初始化
  ngOnInit() {
    this.getData();
    this.ngAfterViewInit();
  }

  ngAfterViewInit() {
    // this.divInitWidth();
    // this.touchStart();
    // this.touchMove();
    // this.touchEnd();
    // this.dymdicBind();

  }




  datas: any;
  /************************滑动删除有关********************************** */
  /**
   * 初始化每行的宽度
   */
  divInitWidth() {
    for (let i = 0; i < this.lineWrapperList.toArray().length; i++) {
      let line: HTMLDivElement = this.lineWrapperList.toArray()[i].nativeElement;
      let scorell: HTMLDivElement = this.scoreWrapperList.toArray()[i].nativeElement;
      let nomal: HTMLDivElement = this.nomalWrapperList.toArray()[i].nativeElement;
      scorell.style.width = line.clientWidth + 100 + "px";
      console.log(scorell.style.width);
      nomal.style.width = line.clientWidth + "px";
      console.log(nomal.style.width);
    }
  }

  

  // 滑动开始
  touchStart() {
    let obj = this;
    for (let i = 0; i < this.nomalWrapperList.toArray().length; i++) {
      this.nomalWrapperList.toArray()[i].nativeElement.addEventListener('touchstart', function (e) {
        obj.lastXForMobile = e.changedTouches[0].pageX;
        // 记录被按下的对象  注意此this并非指代组件 
        obj.currentLeftObj = this;

        // 记录开始按下时的点
        let touches = e.touches[0];
        obj.start = {
          x: touches.pageX,
          y: touches.pageY
        };
      });

    }
  }

  /**
   * 滑动过程中
   */
  touchMove() {
    for (let i = 0; i < this.nomalWrapperList.toArray().length; i++) {

      let obj = this;
      this.nomalWrapperList.toArray()[i].nativeElement.addEventListener('touchmove', function (e) {
        var touches = e.touches[0];
        //记录下横纵的移动距离
        obj.delta = {
          x: touches.pageX - obj.start.x,
          y: touches.pageY - obj.start.y
        };
        // 横向位移大于纵向位移，阻止纵向滚动
        if (Math.abs(obj.delta.x) > Math.abs(obj.delta.y)) {
          event.preventDefault();
        }
      });
    }
  }

  /**
   * 滑动结束
   */
  touchEnd() {
    let obj = this;
    for (let i = 0; i < this.nomalWrapperList.toArray().length; i++) {

      this.nomalWrapperList.toArray()[i].nativeElement.addEventListener('touchend', function (e) {
        if (obj.previousLeftObj && obj.currentLeftObj != obj.previousLeftObj) { // 点击除当前左滑对象之外的任意其他位置
          // 右滑
          $(obj.previousLeftObj).animate({ marginLeft: "0" }, 500);
          obj.previousLeftObj = null;
        }
        var diffX = e.changedTouches[0].pageX - obj.lastXForMobile;
        if (diffX < -150) {
          // 左滑
          $(obj.currentLeftObj).animate({ marginLeft: "-100px" }, 500);
          // 已经左滑状态的按钮右滑
          $(obj.previousLeftObj).animate({ marginLeft: "0" }, 500);
          // 记录上一个左滑的对象
          obj.previousLeftObj = obj.currentLeftObj;
        } else if (diffX > 150) {
          if (obj.currentLeftObj == obj.previousLeftObj) {
            // 右滑
            $(obj.currentLeftObj).animate({ marginLeft: "0" }, 500);
            // 清空上一个左滑的对象
            obj.previousLeftObj = null;
          }
        }
      });
    }
  }




  // 网页在PC浏览器中运行时的监听
  dymdicBind() {
    let obj = this;

    for (let i = 0; i < this.nomalWrapperList.toArray().length; i++) {

      this.nomalWrapperList.toArray()[i].nativeElement.addEventListener("mousedown", function (e) {
        obj.lastX = e.clientX;
        // 记录被按下的对象  注意此this并非指代组件
        obj.currentLeftObj = this;
      })
      this.nomalWrapperList.toArray()[i].nativeElement.addEventListener("mouseup", function (e) {
        if (obj.previousLeftObj && obj.currentLeftObj != obj.previousLeftObj) {
          $(obj.previousLeftObj).animate({ marginLeft: "0" }, 500);
          obj.previousLeftObj = null;
        }

        let diffx = e.clientX - obj.lastX;
        if (diffx < -150) {
          // 左滑
          $(obj.previousLeftObj).animate({ marginLeft: "-100px" }, 500);
          $(obj.previousLeftObj).animate({ marginLeft: "0" }, 500);
          // 记录上一个左滑的对象
          obj.previousLeftObj = obj.currentLeftObj;
        } else if (diffx > 150) {
          if (obj.currentLeftObj == obj.previousLeftObj) {
            // 右滑
            $(obj.currentLeftObj).animate({ marginLeft: "0" }, 500);
            // 清空上一个左滑的对象
            obj.previousLeftObj = null;
          }
        }
      })
    }
  }


  /************************滑动删除有关********************************** */


  /**
   * 查询预约记录 
   */
  getData() {
    this.borrowReservations=[];
    this.noBorrowReservations=[];

    //从login.component.ts中获取user.id，给变量userId
    let userId = localStorage.getItem("appuserId") || sessionStorage.getItem("appuserId");
    let url = "reserve/getReservationByuserId/" + userId;
    this.http.get(url).toPromise().then(
      res => {
        if (res.json().code=="0000"){
        this.models=res.json().data;
        for (let i = 0; i < this.models.length; i++) {
          if(this.models[i].picture != null){
            this.models[i].picture = this.http.fastDsfUrl + this.models[i].picture;//拼接图片地址
          }
      if (this.models[i].isBorrowed == 0) {
        this.borrowReservations.push(this.models[i]);//可借阅
      } else {
        this.noBorrowReservations.push(this.models[i]);//不可借阅
      }
    }
  }
  })

  }
//i代表索引值
private keyup(i: any) {
  let isbn = this.models[i].isbn;
  let location = this.models[i].location;
  this.route.navigateByUrl("/book-info/" + isbn + "/" + location);
  
}

  cancelBorrowReserve(index: number) {
    let ids = new Array();
    ids.push(this.borrowReservations[index].id);
    let url = "reserve/deleteReservationes/" + ids;
    this.http.post(url, null).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.borrowReservations.splice(index, 1);
        this.getData();
      }
    })
  }

  cancelNoBorrowReserve(index: number) {
    let ids = new Array();
    console.log(index);
    ids.push(this.noBorrowReservations[index].id);
    let url = "reserve/deleteReservationes/" + ids;
    this.http.post(url, null).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.noBorrowReservations.splice(index, 1);
        this.getData();
      }
    })
  }





  previousPage() {
    this.route.navigateByUrl("/workspace/book");
  }

  search() {
    this.route.navigateByUrl("/search");

  }

}
