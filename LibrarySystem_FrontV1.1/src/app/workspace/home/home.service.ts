import { Injectable } from '@angular/core';
import {  Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { BorrowDetailModel } from '../../models/borrow-detail-model';

@Injectable()
export class homeService {
  private url ='api/homeDetailW';
  private urlwh ='api/homeDetailsJ';
  private urlyh ='api/homeDetailsK';
  
  constructor(private http: Http) {
  }

  getBorrowList(): Promise<BorrowDetailModel[]> {
   
    return this.http.get(this.url)
      
      .toPromise()
      .then(response => { console.log(response);return response.json() as BorrowDetailModel[]})
     
  }
  getBorrowListyh(): Promise<BorrowDetailModel[]> {
   
 
  console.log(this.urlwh);
  return this.http.get(this.urlwh)
    .toPromise()
    .then(response => { console.log(response);return response.json() as BorrowDetailModel[]})
    //.catch(this.handleError);
}
getBorrowListyq(): Promise<BorrowDetailModel[]> {


console.log(this.urlyh);
return this.http.get(this.urlyh)
  .toPromise()
  .then(response => { console.log(response);return response.json() as BorrowDetailModel[]})
  //.catch(this.handleError);
}


  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
