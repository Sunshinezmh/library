import { Component, OnInit } from '@angular/core';//所有组件必须引用
import { Router, ActivatedRoute, ParamMap } from '@angular/router'
import { BorrowingTwoModel } from '../../../models/borrowing-two.models';
import { BorrowingUpdateModel } from '../../../models/BorrowingUpdate.model';
import { BorrowStateModel } from '../../../../app/models/borrowstate.model';
import { HttpInterceptorService } from '../../../shared/interceptorservice';


//描述本组件的元数据
@Component({
  //创建组件的名字就像HTML标签名一样
  selector: 'app-borrowing',
  //HTML模板，使用相对路径 组件文件所在的目录
  // templateUrl: './borrowing.component.html',
  templateUrl: './borrowing.component.html',
  //HTML模板使用的样式，可以多个
  styleUrls: ['./borrowing.component.css']
})
//创建组件类并暴露出去
export class BorrowingComponent implements OnInit {
  Url = "assets/image/search.png";
  borrowDetailsStay = new Array<BorrowingTwoModel>();
  backPicture = "assets/image/bookback.jpg";

  //借书记录分页查询所需字段
  page = 1;
  pageSize = 10;
  totalPages = 0;
  total = 0;
  maxBook = false;
  allChecked = false;
  checked = new Array();
  picture: string;
  hero: string;
  reservationcount = new Array();//查看所有借阅的书可以预约的人数
  count = 0;//记录每本借阅的书正在预约的数量
  totalNum = 0;//记录总条数
  //续借成功提示信息
  borrowMessage = [];
  constructor(
    private route: Router,
    //拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    private activeRoute: ActivatedRoute,
    public router: Router

  ) {
  }

  ngOnInit() {
    this.getData();
  }

  getData() {
    //借阅状态（1-已借，2-已还，3-超期）

    //获取用户ID
    //定义状态变量status为超期
    //获取后台查询超期的URL，从swagger上获取后台API的URL
    //使用拦截器调取后台方法
    //判断返回的状态
    //若查询成功，后台返回 res.json().data.listRows[[[list],[list]],[list].....]
    let studentNo = localStorage.getItem("appstudentNo"); //获取用户ID（code）
    let userid = localStorage.getItem("appuserId");//获取用户ID--王华伟添加
    let status = BorrowStateModel.已借; //定义查询状态，枚举类型变量
    let url = "borrowing/getBorrowingByStatus/" + status + "/" + userid + "/" + this.pageSize + "/" + this.page;//获取后台查询超期的URL，从swagger上获取后台API的URL--王华伟修改
    this.http.get(url).subscribe(    //使用拦截器调取后台方法 this.http.get(url).subscribe(    //使用拦截器调取后台方法
      res => {
        if (res != null && res.json().code == "0000") {      //判断返回的状态
          ///打印出来的models表示的是后台返回数据中的数组在listRows里面的内容     
          console.log(res.json().data.listRows)


          let mod = null;                            //定义一个用于存放listRows里面的list集合

          console.log(res.json().data.listRows.length);
          this.borrowDetailsStay = [];
          let result = res.json().data.listRows;

          result.forEach(element => {
            if (element != null) {
              element.forEach(bookDetail => {

                if (bookDetail.picture == null) {
                  bookDetail.picture = this.backPicture;
                } else {
                  bookDetail.picture = this.http.fastDsfUrl + bookDetail.picture;
                }
                this.totalNum++;//统计一共借了多少本书
                this.reservationbook(bookDetail.isbn, bookDetail.location);//调方法，查询多少人预约了该书
                this.borrowDetailsStay.push(bookDetail);//拼接数组， 把两层forEach中的数组放到同一个list里
                this.totalPages = Math.floor(this.total + this.pageSize - 1)

              });
            }
          });

        }//endif 
        else {
          this.borrowDetailsStay = null;
        }
      })
  }

  //查询预约该书的人数--杜雨-2018年5月11日13:22:21
  reservationbook(isbn: String, location: String) {

    for (var index = 0; index < this.totalNum; index++) {
      let url = "reserve/getBookReservationInfo" + "/" + isbn + "/" + location;
      this.http.get(url, null).toPromise().then(res => {
        if (res != null && res.json().code == "0000") {
          this.reservationcount[index] = res.json().data.length;
        }
      })
    }
  }

  reserveInfo(i:any){
    let isbn = this.borrowDetailsStay[i].isbn;
    let location = this.borrowDetailsStay[i].location;
    // this.router.navigate(['reserve-info'],{
    //   queryParams:{
    //     isbn:this.borrowDetailsStay[i].isbn,
    //     location:this.borrowDetailsStay[i].location
    //   }
    // });
    // this.router.navigateByUrl("./workspace/book/borrowing/reserve-info/" + isbn + "/" + location);

    
  }

  manyBook() {
    this.page++;
    this.getData();
  }

  previousPage() {
    this.route.navigateByUrl("/workspace/book");
  }

  search() {
    this.route.navigateByUrl("/search");

  }

  //i代表索引值
  private keyup(i: any) {
    let isbn = this.borrowDetailsStay[i].isbn;
    let location = this.borrowDetailsStay[i].location;
    this.router.navigateByUrl("/book-info/" + isbn + "/" + location);
  }
  /**
   * 续借
   * @param borrow 
   */
  continueBorrow(index: number) {
    let borrowId = this.borrowDetailsStay[index].id;
    let url = "borrowing/updateBorrowing/" + borrowId;
    let renew = this.borrowDetailsStay[index].renew;
    this.http.post(url, null).toPromise().then(res => {
      console.log(res.json().code);

      if (res != null && res.json().code == "0000") {
        console.log(res.json().data.endDate);
        //续借成功，修改预计还书日期
        this.borrowDetailsStay[index].endDate = res.json().data.endDate;
        this.borrowMessage[index] = "续借成功！"
        setTimeout(() => {
          this.borrowMessage[index] = "";
        }, 1000)

      } else {
        this.borrowMessage[index] = "续借次数已达上线，请及时还书！"; setTimeout(() => {
          this.borrowMessage[index] = "";
        }, 1000)
      }
    }).catch(res => {
      this.borrowMessage[index] = "续借失败，请联系管理员"; setTimeout(() => {
        this.borrowMessage[index] = "";
      }, 1000)
    })
  }

  /**
     * 还书
     * @param borrow 
     */
  returnBook(index: number) {

    let userId = localStorage.getItem("appuserId");
    let isbn = this.borrowDetailsStay[index].isbn;
    let borrowId = this.borrowDetailsStay[index].id;
    let position = this.borrowDetailsStay[index].location;

    let url = "borrowing/returnBorrowingRecordAPP/" + userId + "/" + isbn + "/" + position;
    this.http.get(url, null).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.borrowMessage[index] = "还书成功！"
        setTimeout(() => {
          this.borrowMessage[index] = "";
        }, 1000)
        this.getData();

        if (this.borrowDetailsStay == null) {
          this.borrowDetailsStay = null;
        }
      } else {
        this.borrowMessage[index] = "还书失败，请联系管理员"; setTimeout(() => {
          this.borrowMessage[index] = "";
        }, 1000)
      }
    }).catch(res => {
      this.borrowMessage[index] = "还书失败，请联系管理员"; setTimeout(() => {
        this.borrowMessage[index] = "";
      }, 1000)
    })

  }

   // 还书页面
 public routeTouploadpicture(index:number): void {
  // let Id = localStorage.getItem("appuserId"); 
  let isbn = this.borrowDetailsStay[index].isbn;
  let location =this.borrowDetailsStay[index].location;
  if(location=="万达"){
    location="1"
  }
  if(location=="学校"){
    location="2"
  }
  this.router.navigateByUrl("/upload-picture/"+isbn+"/"+location);

  // this.router.navigateByUrl("/book-info/" + this.isbn+"/" + this.location );
}
  public routeToBook() {
    this.route.navigateByUrl("workspace/book");
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
}
