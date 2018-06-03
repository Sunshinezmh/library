import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { RequestOptions } from '@angular/http';

import { BookUpdateModel } from '../../../models/book.model';
import { ReservationModel } from '../../../models/reservation.model';
import { Router, ActivatedRoute, ParamMap } from '@angular/router'
import { CollectionAddModel } from '../../../models/collection.model';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
// import {ViewEncapsulation } from '@angular/core';

const getBookDetailUrl = "book/getSearchBookByISBN/"; //获取图书详情
const addreservetionByApp = "reserve/addreservetionByApp";  //预约
const addBookCollection = "collection/addBook"; //加入书架
const addBorrowingRecord = "borrowing/addAppBorrowingRecord";//借阅
const BookcountUrl = "book/bookcount/"; //查询isbn剩余数量
const isReserveUrl = "reserve/isReservedBook";//根据isbn和location查询是否已经预约该书

@Component({
  selector: 'app-book-info',
  templateUrl: './book-info.component.html',
  styleUrls: ['./book-info.component.css'],
  // encapsulation: ViewEncapsulation.None  //解决消息框被导航栏覆盖，相当于全局的样式
})
export class BookInfoComponent implements OnInit {

  backPicture = "assets/image/bookback.jpg";

  imgReservationUrl = "assets/image/reservation.png";
  imgCollectionUrl = "assets/image/collection.png";
  borrowDetailW = new BookUpdateModel();
  borrowDetailWCount = new BookUpdateModel();
  number: number;
  borrowORreserve: string;
  position = this.activeRoute.snapshot.paramMap.get("location");
  isbn=this.activeRoute.snapshot.paramMap.get("isbn");
  isReservedBookResult: boolean;


  public msgs: any;

  constructor(private http: HttpInterceptorService,
    private activeRoute: ActivatedRoute,
    private location: Location,
    private router: Router) { }
  ngOnInit() {
    this.loadData();
    this.loadcont();

  }
  loadData(): void {

    let isbn = this.activeRoute.snapshot.paramMap.get("isbn");//接收路由跳转过来的ISBN
    let url = getBookDetailUrl + isbn;
    this.http.get(url).subscribe(res => {
      if (res != null && res.json().code == "0000") {
        this.borrowDetailW = res.json().data;
        console.log("this.borrowDetailW")
        console.log(this.borrowDetailW)
        if (this.borrowDetailW.picture != null) {
          this.borrowDetailW.picture = this.http.fastDsfUrl + this.borrowDetailW.picture;
        }
      }
    })
  }


  /*-------------查询是否重复预约同一本书-杜雨-2018年5月8日10:00:14-----------*/
  public IsReserverBook() {
    let id = localStorage.getItem("appuserId");
    let isbn = this.activeRoute.snapshot.paramMap.get("isbn");
    let location = this.position;
    let url = isReserveUrl + "/" + id + "/" + isbn + "/" + location;
    this.http.get(url, null).toPromise().then(res => {
      this.isReservedBookResult = res.json().data;
      console.log("duyu")
      console.log(this.isReservedBookResult );

    })
  }


  /*-------------查询本书的数量-王雪芬-2018年1月11日15:30:28-----------*/
  loadcont(): void {
    switch(this.position) { 
      case "1": { 
       this.position = "万达";
         break; 
      } 
      case "2": { 
       this.position = "学校";
         break; 
      } 
      case "3": {
       this.position = "建工"; 
         break;    
      }
   }
    console.log(this.position)
    let isbn = this.activeRoute.snapshot.paramMap.get("isbn");
    let url = BookcountUrl + isbn + "/" + this.position;
    console.log(url)
    this.http.get(url).subscribe(res => {
      if (res != null && res.json().code == "0000") {
        this.borrowDetailWCount = res.json().data[0];
        this.borrowORreserve = "借阅";
        if (this.borrowDetailWCount.useNum == "0") {
          this.borrowORreserve = "预约";
          this.borrowDetailWCount.useNum = "0";
        }
      }

    })
  }

  /*-------------弹出框-----------*/
  //借阅或预约----杜雨-2018年5月1日15:15:54
  Borrow() {
    this.imgReservationUrl = "assets/image/reservation1.png";
    let options = new RequestOptions();
    options.method = "POST";
    let reservation = new ReservationModel();
    reservation.isbn = this.activeRoute.snapshot.paramMap.get("isbn");
    reservation.userId = localStorage.getItem("appuserId");
    reservation.location = this.borrowDetailW.location;
    console.log(reservation);
    if (reservation.userId == null) {
      this.msgs = [{
        severity: 'info',
        summary: '提示',
        detail: this.borrowORreserve + "请先登录！"
      }]
      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 1000)

      return;
    }

    if (this.borrowORreserve == "预约") {
      this.IsReserverBook();
      console.log(reservation);
      //后台接收数据 需要 @RequestBody 标签  
      this.http.post(addreservetionByApp, JSON.stringify(reservation)).subscribe(
        res => {
          console.log('post请求成功', res);
          // this.http.post(addreservetionByApp, JSON.stringify(reservation)).toPromise().then(
          // this.http.post(addreservetionByApp, "sdaf").toPromise().then(        
          if (res != null && res.json().code == "0000") {
            this.msgs = [];
            this.msgs = [{
              severity: 'success',
              summary: '提示',
              detail: "预约成功！"
            }]
            return;
          } else {
            console.log("this.isReservedBookResult----" + this.isReservedBookResult)
            
            if (this.isReservedBookResult == true) {
              this.msgs = [{
                severity: 'info',
                summary: '提示',
                detail: "请勿重复预约"
              }]
              return;
            } else {
            console.log("this.isReservedBookResult----" + this.isReservedBookResult)
            
              this.msgs = [{
                severity: 'info',
                summary: '提示',
                detail: "预约次数已达上限"
              }]
              return;
            }
          }
        }
      )
    } else {
      reservation.id = localStorage.getItem("appuserId");
      let url = addBorrowingRecord + "/" + this.position + "/" + reservation.userId + "/" + reservation.isbn + "/" + reservation.id;
      this.http.get(url, JSON.stringify(reservation)).toPromise().then(
        res => {
          console.log("res.json().code");
          if (res != null && res.json().code == "0000") {
            this.msgs = [];
            this.msgs = [{
              severity: 'success',
              summary: '提示',
              detail: "借阅成功！"
            }]
            if (Number(this.borrowDetailWCount.useNum) > 0) {//借阅成功，库存数量-1
              this.borrowDetailWCount.useNum = String(Number(this.borrowDetailWCount.useNum) - 1)
              if (Number(this.borrowDetailWCount.useNum) == 0) { this.borrowORreserve = "预约" }
            }
          } else {
            if (Number(this.borrowDetailWCount.useNum) == 0) {
              this.msgs = [{
                severity: 'info',
                summary: '提示',
                detail: "没有库存"
              }]
              return;

            }
            this.msgs = [{
              severity: 'info',
              summary: '提示',
              detail: "借阅次数已达上限或不可重复借阅"
            }]
            return;

          }
        }
      )
    }
  }



  AddBookrack() {
    this.imgCollectionUrl = 'assets/image/collection1.png';
    let options = new RequestOptions();
    options.method = "POST";
    let collectionModel = new CollectionAddModel();
    collectionModel.userId = localStorage.getItem("appuserId");
    if (collectionModel.userId == null) {
      this.msgs = [{
        severity: 'info',
        summary: '提示',
        detail: "加入书架请先登录！"
      }]
      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 1000)
      return;
    }
    collectionModel.isbn = this.activeRoute.snapshot.paramMap.get("isbn");
    collectionModel.location = this.position;
    console.log(this.position);
    collectionModel.isDelete = 0;
    collectionModel.remark = "手机端添加书到书架";
    collectionModel.operator = "手机端";
    this.http.post(addBookCollection, JSON.stringify(collectionModel), options).toPromise().then(
      res => {
        //王雪芬更改-2018年3月20日20:44
        if (res != null && res.json().code == "0000") {
          this.msgs = [];
          this.msgs = [{
            severity: 'success',
            summary: '提示',
            detail: "加入书架成功！"
          }]
        } else {

          this.msgs = [{
            severity: 'error',
            summary: '提示',
            detail: "请勿重复添加"
          }]
        }

      }
    )

  }

  /*-------------弹出框-----------*/
  /**
   * 
   * @param str number转换为时间类型   yyyy-MM-dd格式
   */
  public convertDate(date: String) {
    if (date == null || date == "") {
      return "";
    }
  }

  routeTosearch() {
    this.router.navigateByUrl("search");
  }

  routeToHome() {
    this.location.back();
  }

}
