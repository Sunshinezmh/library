import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpInterceptorService } from '../../../../shared/interceptorservice';
import { ReservationViewModel } from '../../../../models/reservation-details-model';

@Component({
  selector: 'app-reserve-info',
  templateUrl: './reserve-info.component.html',
  // styleUrls: ['./reserve-info.component.css']
  styleUrls: ['../borrowing.component.css']
})
export class ReserveInfoComponent implements OnInit {
  isbn:string;
  location :string;
  borrowReservations = new Array<ReservationViewModel>();
  

  constructor(
    private http: HttpInterceptorService,
    private router: Router,
    private activeRoute: ActivatedRoute,
  ) {
    activeRoute.queryParams.subscribe(queryParams=>{
      this.isbn=queryParams.isbn;
      this.location=queryParams.location;
    });
  }

  ngOnInit() {
    this.getdata(this.isbn,this.location)
  }

  getdata(isbn:string,location:string) {
    let url = "reserve/getBookReservationInfo" + "/" + isbn + "/" + location;
    console.log("url-----"+url)
    this.http.get(url, null).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
       this.borrowReservations = res.json().data;
       console.log(res.json());
      }
    })
  }


  routeToBorrowing():void{
    console.log("审美")
    this.router.navigateByUrl("borrowing");
  }
}
