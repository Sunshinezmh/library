import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { HttpInterceptorService } from '../../../../shared/interceptorservice';
import { BorrowingTwoModel } from '../../../../models/borrowing-two.models';
import { BorrowStateModel } from '../../../../../app/models/borrowstate.model';

//描述本组件的元数据

@Component({
  selector: 'app-exceed',
  templateUrl: './exceed.component.html',
  styleUrls: ['../borrowing.component.css']

})

export class ExceedComponent {
  borrowDetailsExceed = new Array<BorrowingTwoModel>();
  borrowDetailsStay = new Array<BorrowingTwoModel>();
  backPicture = "assets/image/bookback.jpg";

  //借书记录分页查询所需字段
  page = 1;
  pageSize = 5;
  totalPages = 0;
  total = 0;
  maxBook = false;



  //续借成功提示信息
  borrowMessage = [];

  constructor(
    //拦截器实例化服务，实例化后才能用
    private http: HttpInterceptorService,
    public router: Router
  ) {

  }

  ngOnInit() {
    this.getData();
  }


  getData() {
    //借阅状态（1-已借，2-已还，3-超期）
    let userid = localStorage.getItem("appuserId");//获取用户ID--王华伟添加
    let status = BorrowStateModel.超期;
    let url = "borrowing/getBorrowingByStatus/" + status + "/" + userid + "/" + this.pageSize + "/" + this.page;
    console.log("超期加载...")
    this.http.get(url).subscribe(
      res => {
        console.log("超期")
        if (res != null && res.json().code == "0000") {
          ///打印出来的models表示的是后台返回数据中的数组在listRows里面     

          let totalNum = res.json().data.totalNum;
          let mod = null;

          for (let i = 0; i < res.json().data.listRows.length; i++) {

            mod = res.json().data.listRows[i];

            for (let j = 0; j < 1; j++) {

              if (mod[j].picture == null) {
                mod[j].picture = this.backPicture;
              } else {
                mod[j].picture = this.http.fastDsfUrl + mod[j].picture;
              }
              this.total = totalNum;
              if (this.total > 0) {                                            //concat方法用于连接两个或多个数组
                this.borrowDetailsExceed = this.borrowDetailsExceed.concat(mod[j]); //把mod[i]这个数组的值连接在borrowDetailsStay后面
                //原来的borrowDetailsStay是空的
                //所以此时borrowDetailsStay里面的就是mod[i]的内容
                this.totalPages = Math.floor((this.total + this.pageSize - 1) / this.pageSize);
              }
            }//endfor j
          }//endfor i
        }//endif 
      })
  }

     //i代表索引值
     private keyup(i: any) {
      let isbn = this.borrowDetailsStay[i].isbn;
      let location=this.borrowDetailsStay[i].location;
      this.router.navigateByUrl("/book-info/" + isbn+"/"+location);
    }

  manyBook() {
    this.page++;
    this.getData();

  }

  /**
     * 续借
     * @param borrow 
     */
  continueBorrow(index: number) {
    let borrowId = this.borrowDetailsExceed[index].id;
    console.log(borrowId);
    let url = "borrowing/updateBorrowing/" + borrowId;
    let renew = this.borrowDetailsExceed[index].renew;
    this.http.post(url, null).toPromise().then(res => {
      console.log(res.json().code);
      console.log("一定要保存代码！！！！");
      if (res != null && res.json().code == "0000") {
        console.log(res.json().data.endDate);
        //续借成功，修改预计还书日期
        this.borrowDetailsExceed[index].endDate = res.json().data.endDate;
        console.log(index);
        this.borrowMessage[index] = "续借成功！"
        console.log("123132");
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




  public convertDate(str: number): string {
    let newDate: Date = new Date(str);
    newDate.setTime(str);
    let fmt = "yyyy-MM-dd";
    var o = {
      //月份  
      "M+": newDate.getMonth() + 1,
      //日           
      "d+": newDate.getDate(),
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(RegExp.$1, (newDate.getUTCFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt))
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

  }
}

