import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.css']
})
export class WorkspaceComponent implements OnInit {

  imgMeUrl = 'assets/image/me.png';
  imgBooksUrl = 'assets/image/books.png';
  imgCollectionUrl = 'assets/image/collection.png';
  imgHomeUrl = 'assets/image/home1.png';
  UserId = localStorage.getItem("appuserId");
  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }
  ngOnInit() {
  }


  public Login(): void {
    this.imgMeUrl = 'assets/image/me1.png';
    this.imgBooksUrl = 'assets/image/books.png';
    this.imgCollectionUrl = 'assets/image/collection.png';
    this.imgHomeUrl = 'assets/image/home.png';
    
    if (this.UserId == null) {
      this.router.navigate(['/login']);
    }
    else {
      this.router.navigateByUrl("/workspace/book");
    }
  }

  public home(): void {
    this.imgMeUrl = 'assets/image/me.png';
    this.imgBooksUrl = 'assets/image/books.png';
    this.imgCollectionUrl = 'assets/image/collection.png';
    this.imgHomeUrl = 'assets/image/home1.png';
  }

  public collection(): void {
    this.imgMeUrl = 'assets/image/me.png';
    this.imgBooksUrl = 'assets/image/books.png';
    this.imgCollectionUrl = 'assets/image/collection1.png';
    this.imgHomeUrl = 'assets/image/home.png';
  }

  public booktype(): void {
    this.imgMeUrl = 'assets/image/me.png';
    this.imgBooksUrl = 'assets/image/books1.png';
    this.imgCollectionUrl = 'assets/image/collection.png';
    this.imgHomeUrl = 'assets/image/home.png';
  }
}
