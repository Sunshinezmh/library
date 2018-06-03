import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { BorrowDetailModel } from '../../../models/borrow-detail-model';

@Component({
  selector: 'app-find-result',
  templateUrl: './find-result.component.html',
  styleUrls: ['./find-result.component.css']
})
export class FindResultComponent implements OnInit {

  backPicture = "assets/image/bookback.jpg";

  public msgs: any;
  condition = "";
  judy="";
  contentSearch = "";
  page = 1;
  pageSize = 10;
  totalPages = 0;
  total = 0;

  borrowDetailW = new Array<BorrowDetailModel>();
  constructor(public http: HttpInterceptorService,
    private activeRoute: ActivatedRoute,
    private router: Router) {
  }
  ngOnInit() {
    this.contentSearch = this.activeRoute.snapshot.paramMap.get("contentSearch");
    this.condition = this.activeRoute.snapshot.paramMap.get("condition");
    // this.judy = this.activeRoute.snapshot.paramMap.get("judy");
    this.judy =  localStorage.getItem('all');
    this.loadData();
  }
  loadData(): void {
    //如果没有选择则是查询全部
    if( this.judy==null || this.judy==""){
      this.judy="全部";
    }
    let url = "book/findByConditionselect/" + this.condition + "/" +  this.judy + "/" + this.page + "/" + this.pageSize;

    console.log(this.condition)  
    console.log(url)
    this.http.get(url).toPromise().then(res => {
      
      if (res != null && res.json().code == "0000") {
        if(res.json().data.listRows==0){
          this.msgs = [{
            severity: 'error',
            summary: '提示',
            detail: "未查询到相应书籍！"
          }]
        }
        let models: Array<BorrowDetailModel> = res.json().data.listRows;
        let cont = res.json().data.totalNum;
        //对图片的路径加上ip前缀
        for (let i = 0; i < models.length; i++) {
          models[i].bookName = models[i].bookName;
          if (models[i].picture != null) {
            models[i].picture = this.http.fastDsfUrl + models[i].picture;
          }
        }
        this.borrowDetailW = this.borrowDetailW.concat(models);
        console.log(this.borrowDetailW)
        this.total = 5;
        //在js里没有 /整除符号。使用math的floor向下取整
        this.totalPages = Math.floor((this.total + this.pageSize - 1) / this.pageSize);
      } else {
        this.msgs = [{
          severity: 'error',
          summary: '提示',
          detail: "未查询到相应书籍！"
        }]
      }
    }).catch(() => {
      this.msgs = [{
        severity: 'error',
        summary: '提示',
        detail: "未查询到相应书籍！"
      }]
    })
  }

  public nextPage() {
    if (this.page < this.totalPages) {
      this.page++;
      this.loadData();
    }
  }


  public routeToSearch() {
    this.router.navigateByUrl("search");
  }

  public routeToBookInfo(book: BorrowDetailModel) {
    if (book.location == "万达") {
      book.location = "1";
    }
    if (book.location == "学校") {
      book.location = "2";
    }
    this.router.navigateByUrl("book-info/" + book.isbn +"/"+ book.location);
  }

}
