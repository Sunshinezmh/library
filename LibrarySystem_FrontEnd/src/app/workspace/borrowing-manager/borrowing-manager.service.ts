import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {
  Http,
  RequestOptionsArgs,
  RequestOptions,
  Response,
  Headers
} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
class RequestMethod{
  public static readonly GET = "GET";
}

@Injectable()
export class BorrowingManagerService {

  constructor(private http: Http) { }
  
 //调用远程地址，获取数据 get方法
 public getGetData(url: string) {
  return this.http
    .get(url)
    .map(response => response.json() as any)
    .catch(this.handleError);
}
 //错误处理
 private handleError(error: any): Promise<any> {
  console.error('An error occurred', error); // for demo purposes only
  return Promise.reject(error.message || error);
}

}
