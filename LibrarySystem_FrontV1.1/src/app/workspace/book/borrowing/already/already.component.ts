import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BorrowStateModel } from '../../../../../app/models/borrowstate.model';

import { HttpInterceptorService } from '../../../../shared/interceptorservice';
import { BorrowingTwoModel } from '../../../../models/borrowing-two.models';
//描述本组件的元数据
@Component({
  selector: 'app-already',
  templateUrl: './already.component.html',
  styleUrls: ['../borrowing.component.css']

})
export class AlreadyComponent implements OnInit {
  borrowDetailsalready = new Array<BorrowingTwoModel>();
  backPicture = "assets/image/bookback.jpg";

  //借书记录分页查询所需字段
  page = 1;
  pageSize = 5;
  totalPages = 0;
  total = 0;

  maxBook = false;


  picture: string;
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
    let studentNo = localStorage.getItem("appstudentNo");
    let userid = localStorage.getItem("appuserId");//获取用户ID--王华伟添加

    let status = BorrowStateModel.已还;
    let url = "borrowing/getBorrowingByStatus/" + status + "/" + userid + "/" + this.pageSize + "/" + this.page;//获取后台查询超期的URL，从swagger上获取后台API的URL--王华伟修改

    console.log("已还加载...")
    this.http.get(url).subscribe(
      res => {
        console.log(url)

        console.log("我是已还里面")
        if (res != null && res.json().code == "0000") {
          ///打印出来的models表示的是后台返回数据中的数组在listRows里面     
          console.log(res.json().data)
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
              if (this.total > 0) {                                                 //concat方法用于连接两个或多个数组
                this.borrowDetailsalready = this.borrowDetailsalready.concat(mod[j]);//把mod[i]这个数组的值连接在borrowDetailsStay后面
                //原来的borrowDetailsStay是空的
                //所以此时borrowDetailsStay里面的就是mod[i]的内容
                this.totalPages = Math.floor((this.total + this.pageSize - 1) / this.pageSize);
              }
            }//endfor j
          }//endfor i
        }//endif 
      })
  }

  manyBook() {
    this.page++;
    this.getData();

  }

    //i代表索引值
    private keyup(i: any) {
      let isbn = this.borrowDetailsalready[i].isbn;
      let location=this.borrowDetailsalready[i].location;
      this.router.navigateByUrl("/book-info/" + isbn+"/"+location);
    }

  public convertDate(str: number): string {
    let newDate: Date = new Date();
    newDate.setTime(str);
    let fmt = "yyyy-MM-dd";
    var o = {
      "M+": newDate.getMonth() + 1,
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
