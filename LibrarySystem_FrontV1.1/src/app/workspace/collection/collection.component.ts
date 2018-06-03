import { Component, OnInit, Input, ElementRef, ViewChild, Output, EventEmitter, QueryList } from '@angular/core';
import { CollectionModel } from '../../models/collection.model';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpInterceptorService } from '../../shared/interceptorservice';
@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.css']
})
export class CollectionComponent implements OnInit {
  checkboxlist = new Array();
  public msgs: any;
  backPicture = "assets/image/bookback.jpg";
  borrowDetailJ = new Array<CollectionModel>();
  defaultPicture: string = "assets/image/miss.png"
  constructor(
    private router: Router,
    public http: HttpInterceptorService
  ) {
  }

  ngOnInit() {
    this.getData();
    document.getElementById('find').focus();
  }
  getData() {
    let userId = localStorage.getItem("appuserId");
    let url = "collection/getCollectionByuserId/" + userId;
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().code == "0000") {

          this.borrowDetailJ = res.json().data;
          for (let i = 0; i < this.borrowDetailJ.length; i++) {
            if (this.borrowDetailJ[i].picture != null) {

              this.borrowDetailJ[i].picture = this.http.fastDsfUrl + this.borrowDetailJ[i].picture;

            }
            if (this.borrowDetailJ[i].bookName.length > 5) {
              if (this.borrowDetailJ[i].bookName.charCodeAt) {
                this.borrowDetailJ[i].bookName = this.borrowDetailJ[i].bookName.substring(0, 8);
              }
              else {
                this.borrowDetailJ[i].bookName = this.borrowDetailJ[i].bookName.substring(0, 8);
              }
            }
          }

        }
      }
    )
  }
  public routeToHome(): void {
    this.router.navigateByUrl("workspace/book");
  }


  public condition = "";
  //查询书架内容
  public findColResult(): void {
    let url = "";
    let userId = localStorage.getItem("appuserId");
    if (this.condition == null || this.condition.trim() == "") {
      url = "collection/getCollectionByuserId/" + userId;
      this.getData();
    } else {
      url = "collection/findColByCondition/" + this.condition + '/' + userId;
      this.http.get(url).subscribe(
        res => {
          if (res != null && res.json().code == "0000") {

            this.borrowDetailJ = res.json().data;
            for (let i = 0; i < this.borrowDetailJ.length; i++) {

              if (this.borrowDetailJ[i].picture != null) {

                this.borrowDetailJ[i].picture = this.http.fastDsfUrl + this.borrowDetailJ[i].picture;

              }
              if (this.borrowDetailJ[i].bookName.length > 5) {
                if (this.borrowDetailJ[i].bookName.charCodeAt) {
                  this.borrowDetailJ[i].bookName = this.borrowDetailJ[i].bookName.substring(0, 8);
                }
                else {
                  this.borrowDetailJ[i].bookName = this.borrowDetailJ[i].bookName.substring(0, 8);
                }
              }
              console.log(this.borrowDetailJ);
            }

          }
        }
      )
    }
  }
  //为日期转为日常的格式+马金兴+2017年10月28日21:30:52
  convertToDate(nows) {
    var now = new Date(nows);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    return year + "年" + month + "月" + date + "日";
  }

  public deleteCollections(): void {
    //用于删除的ID数组
    let collectionIds = new Array();
    let indexs = new Array();
    // 定义用户是否选中一条记录
    let selectFlag: boolean = false;
    for (let i = 0; i < this.checkboxlist.length; i++) {
      if (this.checkboxlist[i]) {
        collectionIds.push(this.borrowDetailJ[i].collectionId);
        indexs.push(i);
        selectFlag = true;
      }
    }
    //如果没有选中则进行提示，结束运行
    if (selectFlag == false) {
      this.msgs = [{
        severity: 'success',
        summary: '提示',
        detail: "至少选中一本书！"
      }]
      return;
    }

    // 访问后台，进行批量删除
    let url = "collection/deleteCollections/" + collectionIds;
    this.http.post(url, null).toPromise().then(res => {
      var d = document.getElementById("delete");
      var e = document.getElementById("edit");
      var i: number;
      var x = document.getElementsByClassName("chbs");

      if (res != null && res.json().code == "0000") {
        this.msgs = [{
          severity: 'success',
          summary: '提示',
          detail: "删除成功！"
        }]
        e['style'].display = "block";
        d['style'].display = "none";
        for (i = 0; i < x.length; i++) {
          if (x[i]['style'].display == "none") {
            x[i]['style'].display = "block";
            d['style'].display = "block";
            e['style'].display = "none";
          }
          else {
            if (x[i]['style'].display == "block") {
              x[i]['style'].display = "none";
              d['style'].display = "none";
              console.log(x[i]['style'].display)
            }
          }
        }
        for (let j = 0; j < indexs.length; j++) {
          this.borrowDetailJ.splice(indexs[j] - j, 1);
        }
        this.checkboxlist = new Array();
      }
    })
  }
  checkBook(checkBox: HTMLInputElement, i: number) {
    this.checkboxlist[i] = !checkBox.checked
  }
  chbClick() {
    event.stopPropagation();
  }

  public routeToSearch(): void {
    this.router.navigateByUrl("search");
  }
  //i代表索引值
  private keyup(i: any) {
    let isbn = this.borrowDetailJ[i].isbn;
    let location = this.borrowDetailJ[i].location;
    if (location == "万达") {
      location = "1";
    }
    if (location == "学校") {
      location = "2";
    }
    this.router.navigateByUrl("/book-info/" + isbn + "/" + location);
  }
  @ViewChild("content") content: ElementRef
  start: any;
  delta: any;

  //界面初始化后，进行页面的滑动事件绑定
  ngAfterViewInit() {

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
        obj.toMe();
      }
      if (obj.delta != null && obj.delta.x > 80) {
        obj.toBookstType();
      }
    });
  }

  public toBookstType() {
    this.router.navigateByUrl("workspace/book-type");
  }

  //跳转到我的界面，如果没有登录进入登陆界面
  public toMe(): void {
    let urserId = localStorage.getItem("appuserId");
    if (urserId == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.router.navigateByUrl("/workspace/book");
    }
  }



  public edit() {
    var i: number;
    var x = document.getElementsByClassName("chbs");
    var d = document.getElementById("delete");
    var e = document.getElementById("edit");

    for (i = 0; i < x.length; i++) {

      if (x[i]['style'].display == "none") {
        x[i]['style'].display = "block";
        d['style'].display = "block";
        e['style'].display = "none";
      }
      else {
        if (x[i]['style'].display == "block") {
          x[i]['style'].display = "none";
          d['style'].display = "none";
          console.log(x[i]['style'].display)
        }
      }
    }
  }
}
