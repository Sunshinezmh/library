import { Component, OnInit, Input, ElementRef, ViewChild, AfterViewInit, Output, EventEmitter, DoCheck, OnChanges } from '@angular/core';


import { Router } from '@angular/router';
import { BooktypeModel } from '../../models/booktype.model';
import { BookUpdateModel } from '../../models/book.model';
import { HttpInterceptorService } from '../../shared/interceptorservice';
import { CollectionModel } from '../../models/collection.model';


const PUSHTYPE: number = 4;

@Component({
  selector: 'app-book-type',
  templateUrl: './book-type.component.html',
  styleUrls: ['./book-type.component.css']
})
export class BookTypeComponent implements OnInit, AfterViewInit {

  backPicture = "assets/image/bookback.jpg";
  //父类别
  parentTypeModels = new Array<Array<BooktypeModel>>();

  //子类别
  sonTypeModels = new Array<BooktypeModel>();
  bookTypes = new Array<BookUpdateModel>();
  bookTypeShow = new Array<BookUpdateModel>();
  model1 = new Array();
  pageNum = 1;
  pageSize = 500;
  totalPages = 1;
  total = 0;
  sonTypeIndex = 0;
  parentId: string;
  isMany = false;
  mySwiper: any;
  item: string;
  item2: string;
  location: string;
  constructor(
    public http: HttpInterceptorService,
    private router: Router
  ) {

  }

  ngOnInit() {


    //初始化Sweiper对象,实现父类滑动,并赋予分页器（即页面的蓝圈）
    // this.mySwiper = new Swiper('.swiper-container', {
    //   pagination: '.swiper-pagination',
    //   paginationClickable: true,
    //   //触发swiper所需要的最小拖动距离比例
    //   longSwipesRatio: 0.5,
    //   //滑动的拖放的，如果是1表示滑动之后换下一个界面是一整块，如果是0.5就表示才能滑一半。
    //   touchRatio: 1,
    //   // 修改swiper自己或子元素时，自动初始化swiper
    //   observer: true,
    //   // 修改swiper的父元素时，自动初始化swiper
    //   observeParents: true,
    //   //默认初始页
    //   initialSlide: 0
    // })

    this.loadData();
  }

  //类别下对应的书籍
  private books = new Array<BookUpdateModel>();

  loadData(): void {
    console.log("123dfa")
    let url = "book/getAllLocation/1/10";
    console.log(url);
    this.http.get(url).subscribe(
      res => {
        console.log(url);
        if (res != null && res.json().code == "0000") {
          let models = new Array();
          
          models = res.json().data;
          console.log(res.json().data);
          console.log(models.length);
          for (let i = 0; i < models.length; i = i + 1) {
            if (models[i] != null) {
              this.model1.push(models[i]);
            }
          }
          for (let i = 0; i < this.model1.length; i = i + PUSHTYPE) {
            this.parentTypeModels.push(this.model1.slice(i, i + PUSHTYPE));
          }
          

          this.location=localStorage.getItem('comName');
          console.log("!!!!!!!!!!!!!!!!!!!location!!!!!!!!")
          console.log(this.location);
          if(this.location==null||this.location=="")
          {

            this.findAllSontype("万达");
            localStorage.setItem('comName',"万达");
          }
          else
          {
            this.findAllSontype(this.location);
            
          }

            
        }
      }
    )
  }

  findAllSontype(location: string) {
    localStorage.setItem('comName',location);
    console.log("1234")
    this.bookTypeShow = [];
    this.bookTypes = [];
    this.location = location;
    let url = "bookType/shelfType/"+this.location;
    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().data != null && res.json().data.length > 0) {
        this.bookTypes = res.json().data;
        console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        console.log(res.json().data)
        for (let i = 0; i < this.bookTypes.length; i++) {
          if (this.bookTypes[i].bookNum != 0) {
            this.bookTypeShow.push(this.bookTypes[i]);
          }
        }
          console.log(this.bookTypeShow)
          for (let j = 0; j < this.bookTypeShow.length; j++) {
            // console.log(this.bookTypeShow.length)
            if (this.bookTypeShow[j].picture == null) {
              this.bookTypeShow[j].picture = this.backPicture;

            }
            else {
              this.bookTypeShow[j].picture = this.http.fastDsfUrl + this.bookTypeShow[j].picture;
              // console.log(this.bookTypeShow[j].picture)
            }
          }
          console.log(this.bookTypeShow)
      }
    })
  }
  // change(location:any):void{
  //   localStorage.setItem('comName',location);
  // }
  bookList(sonType: any): void {
    sonType.location = this.location;
    this.router.navigateByUrl("bookList" + "/" + sonType.location + "/" + sonType.typeName + "/" + sonType.typeId + "/" + sonType.bookNum);
  }

  public limitWord(summary: String) {
    if (summary.length > 50) {
      return summary.substring(0, 50) + "......";
    } else {
      return summary;
    }
  }


  routeToBookInfo(book: BookUpdateModel) {
    //把父的id存储在全局变量中
    this.item2 = this.item;
    //然后再存储起来
    localStorage.setItem("itemID", this.item2);
    this.router.navigateByUrl("book-info/" + book.isbn);
  }

  @ViewChild("content") content: ElementRef
  start: any;
  delta: any;

  //界面初始化后，进行页面的滑动事件绑定
  ngAfterViewInit() {
    // this.touchStart();
    // this.touchMove();
    // this.touchEnd();
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
        obj.toBookCollection();
      }
      if (obj.delta != null && obj.delta.x > 80) {
        obj.toHome();
      }
    });
  }

  public toHome() {
    this.router.navigateByUrl("workspace/home");
  }

  public toBookCollection() {
    this.router.navigateByUrl("workspace/collection");
  }


}
