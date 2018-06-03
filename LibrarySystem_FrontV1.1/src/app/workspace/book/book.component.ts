import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { HttpInterceptorService } from '../../shared/interceptorservice';
import { borrowingbystatusModel } from '../../../app/models/borrowing.model';
import { BorrowStateModel } from '../../../app/models/borrowstate.model';
import { PickList } from 'primeng/primeng';
import { ReservationViewModel } from '../../models/reservation-details-model';
// import { parse } from 'path';

const FEMALE = "assets/image/female.svg";
const MALE = "assets/image/male.svg";
const PERSON = "assets/image/person.svg";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  //还书提醒分页处理所需字段
  page = 1;
  pageSize = 3;
  total = 0;
  totalPages = 0;
  borrowDetailW: borrowingbystatusModel[] = [];
  reservebooks:ReservationViewModel[]=[];
  // borrowDetailW = new Array();
  date = new Date();
  //借阅状态（超期，已借，已还）
  borrowState: string;
  backPicture = "assets/image/bookback.jpg";
  //个人信息所需字段
  picture = PERSON;
  userid: string;
  userCode:string;
  userPicture: string;
  userName: string;
  test = new Array();
  BookName: any;
  booknames = new Array();
  //c可以借阅书本个数
  reserveCount: number;
  mod = new Array();
  constructor(
    //拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    public router: Router
  ) { }

  ngOnInit() {
    this.userid = localStorage.getItem("appuserId") || sessionStorage.getItem("appuserId");
    this.userCode = localStorage.getItem("userCode")|| sessionStorage.getItem("userCode");
    //用户信息 头像 还有姓名
    this.getUserInfo(this.userCode);
    // 显示待还书的图书信息
    this.getData(this.userid);
    //根据用户ID看有哪几本预定的书能借阅了  
    this.getAppointmentCount();
    //根据用户ID查看借阅记录
    this.getReservationData();
  }


  /**
   * 根据学号查询该学生或用户的信息
   * @param userCode 
   */

    getUserInfo(userCode: string) {
    let url = "student/getStudentBystudentCode/" + this.userCode;
    this.http.get(url).subscribe(
      res => {
        console.log(res.json().data);
        this.userPicture = res.json().data[0].pictrue;
        console.log(this.userPicture)
        this.userName = res.json().data[0].name;
        //性别不同，显示默认照片不同
        if (res.json().data[0].picture == null || res.json().data[0].picture == "") {
          console.log(this.userPicture)
          switch (res.json().data[0].sex) {
            case "男":
              this.picture = MALE;
              break;
            case "女":
              this.picture = FEMALE;
              break;
            default:
              this.picture = PERSON;
              break;
          }
        } else {
          this.picture =this.http.fastDsfUrl+ res.json().data[0].picture;
          console.log(this.picture)
        }
      })
  }


  /**
   * 还书提醒，做分页处理，防止页面加载过慢
   * @param studentNo   ==userID
   */
  book = new Array();
  
  getData(studentNo: String) {
    console.log('-----------');
    console.log(studentNo);
    if (studentNo == null) {
      this.router.navigateByUrl("workspace/login");
    }
    let borrowState = BorrowStateModel.已借;   
     let userid = localStorage.getItem("appuserId");//获取用户ID--王华伟添加
    
    //根据借阅状态：待还 查找借阅记录
    let url = "borrowing/getBorrowingByStatus/" + borrowState + '/' + userid + "/" + this.pageSize + "/" + this.page;
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().code == "0000") {
      //     ///打印出来的models表示的是后台返回数据中的数组在listRows里面 
            let result = res.json().data.listRows;
          var models = new Array();
          /*王雪芬-修改显示日期-2018年4月17日20:01:51 */
          result.forEach(element => {
            element.forEach(book => {
              if(book.picture ==null){
                book.picture =this.backPicture;           
              }else{
                book.picture = this.http.fastDsfUrl + book.picture;       
              }
              book.name = book.name.substring(0, 10)+ "...";
              book.endDate = this.convertToDate(book.endDate)
              this.borrowDetailW.push(book);
            });
          });        
            this.totalPages = Math.floor((this.total + this.pageSize - 1) / this.pageSize);
        }
      })
  }

  //还书提醒点击更多时，查询下一页
  nextPage() {
    if (this.page < this.totalPages) {
      this.page++;
      this.getData(this.userid);
    }

  }
  /**
   * 获取预约的数量
   */
  getAppointmentCount() {
    let urserId = localStorage.getItem("appuserId");
    console.log("------------")
    console.log(urserId);
    if (urserId == null) {
      this.router.navigateByUrl("workspace/login");
    }
    let url = "reserve/getReservationCount/" + urserId;
    this.http.get(url).subscribe(
      res => {
        this.reserveCount = res.json().data;
        console.log(res.json().data);
        console.log("数量-------------"+this.reserveCount);
      })
  }

  //为日期转为日常的格式+马金兴+2017年10月28日21:30:52
  convertToDate(nows) {
    var now = new Date(nows);
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    return year + "年" + month + "月" + date + "日";
  }

  // 个人信息
  public routeToPersonInfo(): void {
    this.router.navigateByUrl("/PersonalInfo");
  }
  //预约
  public reservebook():void{
    this.router.navigateByUrl("/reservebook");
  }

  // 我的借阅
  public borrowing(): void {
    this.router.navigateByUrl("/borrowing");
  }
  // 书籍借阅详情
  public reservation(): void {
    this.router.navigateByUrl("/reservation");
  }

  //获取预约记录，存入缓存中----未使用
  getReservationData() {
    let userId = localStorage.getItem("appuserId") || sessionStorage.getItem("appuserId");
    let url = "reserve/getReservationByuserId/" + userId;
    this.http.get(url).toPromise().then(
      res => {
        if (res != null && res.json().code == "0000") {
              this.reservebooks=res.json().data;
        }
      })
  }


  //我的书架  
  public collection(): void {
    this.router.navigateByUrl("workspace/collection");
  }



  //获取界面的dom滑动对象
  @ViewChild("content") content: ElementRef
  //触摸开始的点
  start: any;
  //触摸后的点
  delta: any;
  ngAfterViewInit() {
    this.touchStart();
    this.touchMove();
    this.touchEnd();
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
      //根据滑动距离来判断是否进行页面跳转
      if (obj.delta != null && obj.delta.x > 80) {
        obj.toBookCollection();
      }
    });
  }
  public toBookCollection() {
    this.router.navigateByUrl("workspace/collection");
  }
}




