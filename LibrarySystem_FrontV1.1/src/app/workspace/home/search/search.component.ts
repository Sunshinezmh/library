import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SearchModel } from '../../../models/search-details-model';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { ViewChild } from '@angular/core';
import { book } from 'app/workspace/book-type/bookList.model';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  public condition = "";
  public judy = "";
  @ViewChild('myInput') input;
  history = [];
  bookname = [];
  hotBooks = new Array(SearchModel);
  twobookname;
  hotBook = new Array(SearchModel);
  flag: string;
  test: string;
  constructor(
    public router: Router,
    private http: HttpInterceptorService
  ) { }

  ngOnInit() {
    this.loadData();
    this.getData();
    document.getElementById('find').focus();
  }
  /**
   * 查询出热门搜索的内容
   */
  book = new Array();
  result = new Array();
  loadData(): void {
    let url = "book/getHotBook";
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().data != null && res.json().data.length > 0) {
          this.result.length = 0;
          let jsonData = res.json().data;
          jsonData.forEach(el => {
            if (el != null) {
              this.result.push(el[0]);
            }
          });
        }
        
        // this.history.push(localStorage.getItem('search-history'));
      }
    )
  }
getData(){
  this.flag = localStorage.getItem('flag');
  console.log("出来了吗")
  console.log(this.flag)
  for (let i = 0; i <= Number(this.flag); i++) {
    this.history.push(localStorage.getItem(i.toString()));
  }
  console.log(this.history)
}
  selectCondition(condition: any) {
    this.router.navigateByUrl("workspace/find-result/" + condition);
  }

  public routeToHome(): void {
    this.router.navigateByUrl("workspace/home")
  }
  public findResult(): void {
    let contentSearch = this.condition;
    this.router.navigateByUrl("workspace/find-result/" + contentSearch);
    let flag1 = localStorage.getItem('0');
    console.log(flag1);
    if (flag1 == null||flag1=="") {
      localStorage.setItem('0', this.condition);
      localStorage.setItem('flag', "0");
    }
    else {
      flag1 = localStorage.getItem('flag');
      for (let i = 0; i <= Number(flag1); i++) {
        this.bookname.push(localStorage.getItem(i.toString()));
      }
      if(this.bookname.indexOf(String(this.condition))==-1){
      
        console.log(this.bookname.indexOf(String(this.condition)))
        localStorage.setItem((Number(flag1) + 1).toString(), this.condition);
        localStorage.setItem('flag', (Number(flag1) + 1).toString());
        console.log(localStorage.getItem('flag'))
      }
      else{
        console.log(this.bookname.indexOf(String(this.condition)))
      }
      // localStorage.clear();
    }
  }
  // searchhistory:string
  deletehistory(){
    let flag2= localStorage.getItem('flag');
    console.log(flag2)
    for (let i = 0; i <= Number(flag2); i++) {
      localStorage.removeItem(i.toString());
      
    }
  //  this.searchhistory=="";
  //  console.log(this.searchhistory)
    this.history=[];
     console.log("this.searchhistory") 
  }
  select(id: any) {
    if (id == 1) {
      this.judy = "全部";
      console.log(this.judy)
    }
    if (id == 2) {
      this.judy = "可借阅";
    }
    if (id == 3) {
      this.judy = "不可借阅";
    }
    localStorage.setItem('all', this.judy);
  }

}
// Java并发编程实战