import { Component, OnInit, ViewChild, ElementRef, ViewChildren, QueryList, AfterViewInit, AfterContentInit, OnChanges, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';

import { borrowingbystatusModel } from '../../../models/borrowing-model';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
// import { ReservationViewModel } from '../../../models/reservation-details-model';
const getBookDetailUrl = "borrowing/getAllBorrowedRecoed/"; //获取借阅记录
const getBookDetailByMoHuUrl = "borrowing/findBorrowedRecordByConditions/"; //获取借阅记录
@Component({
  selector: 'app-reservation',
  // templateUrl: './test.html',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
  // styleUrls: ['./test.css']

})
//当使用这个组件的时候，我们需要导入我们的服务ReservationService
export class ReservationComponent implements OnInit {
  Url = "assets/image/search.png";
  backPicture = "assets/image/bookback.jpg";
  // borrowDetailW = new borrowingbystatusModel();
  borrowDetailW = new Array<borrowingbystatusModel>();
  imgSearchURL = 'assets/image/search.png';

  public msgs: any;
  page = 1;
  pageSize = 4;
  totalPages = 0;
  total = 0;
  condition: string = "";

  constructor(
    private route: Router,
    //拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService
  ) { }


  //进行初始化
  ngOnInit() {
    this.getData();
  }
  getData() {
    let url = getBookDetailUrl + this.page + "/" + this.pageSize;

    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.borrowDetailW = res.json().data;
        console.log(res.json().data)
        for (let i = 0; i < this.borrowDetailW.length; i++) {
          if (this.borrowDetailW[i].picture != null) {
            this.borrowDetailW[i].picture = this.http.fastDsfUrl + this.borrowDetailW[i].picture;
          }
        }
      }

    });
  }
  query() {
    if (this.condition == null || this.condition.trim() == "") {
      this.getData();
    }
    else {
      let url = getBookDetailByMoHuUrl + this.condition + "/" + this.page + "/" + this.pageSize;
      console.log(url)
      this.http.get(url).toPromise().then(res => {
        if (res != null && res.json().code == "0000") {
            this.borrowDetailW = res.json().data.listRows;
            console.log(res.json().data.listRows)
            for (let i = 0; i < this.borrowDetailW.length; i++) {
              if (this.borrowDetailW[i].picture != null) {
                this.borrowDetailW[i].picture = this.http.fastDsfUrl + this.borrowDetailW[i].picture;
              }
            }
          }
        // }
      });
    }

  }

  datas: any;

  previousPage() {
    this.route.navigateByUrl("/workspace/book");
  }

  search() {
    this.route.navigateByUrl("/search");
  }
  public convertDate(str: number): string {
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
  //i代表索引值
  private keyup(i: any) {
    let isbn = this.borrowDetailW[i].isbn;
    let location = this.borrowDetailW[i].location;
    this.route.navigateByUrl("/book-info/" + isbn + "/" + location);

  }
}
