import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpInterceptorService } from '../../../shared/interceptorservice';
import { BooktypeModel } from '../../../models/booktype.model';
@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  sonTypeModels = new Array<BooktypeModel>();
  public name: string;
  public num:any;
  public location:string;
  backPicture = "assets/image/bookback.jpg";
  constructor(
    public http: HttpInterceptorService,
    private router: Router,
    private activeRoute: ActivatedRoute
  ) {

  }
  ngOnInit() {
    this.loadcont();
  }

  booktype(): void {
    this.router.navigateByUrl("/workspace/book-type");
    // this.router.navigateByUrl("/workspace/book-type/"+this.location);
  }
  loadcont(): void {
    this.location = this.activeRoute.snapshot.paramMap.get("location");
    console.log(this.location);
    this.name = this.activeRoute.snapshot.paramMap.get("typeName");
    let id = this.activeRoute.snapshot.paramMap.get("typeId");
    this.num = this.activeRoute.snapshot.paramMap.get("bookNum");
    console.log(location+id+this.name+this.num)
    let url = 'bookType/findBookbyShelf/' + this.location + '/' + id + '/' + 1 + '/' + 10;
    console.log(url);
    this.http.get(url).toPromise().then(res => {
      if (res != null && res.json().data != null && res.json().data.length > 0) {
        this.sonTypeModels = res.json().data;
        let models: Array<BooktypeModel>
        for (let i = 0; i < this.sonTypeModels.length; i++) {
          this.sonTypeModels[i].name =  this.sonTypeModels[i].name;
          this.sonTypeModels[i].location =  this.sonTypeModels[i].location;
          if ( this.sonTypeModels[i].picture == null) {
            this.sonTypeModels[i].picture = this.backPicture;

          } else {
            this.sonTypeModels[i].picture = this.http.fastDsfUrl +  this.sonTypeModels[i].picture;
          }
        }
        console.log(this.sonTypeModels)
          }
    })
  }

  public routeToBookInfo(book:BooktypeModel) {
    if (book.location == "万达") {
      book.location = "1";
    }
    if (book.location == "学校") {
      book.location = "2";
    }
    this.router.navigateByUrl("book-info/" + book.isbn +"/"+ book.location);
  }
}
