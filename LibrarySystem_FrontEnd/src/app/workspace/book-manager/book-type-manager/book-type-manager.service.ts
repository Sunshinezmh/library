import { Injectable } from '@angular/core';
// import{Observable} from 'rxjs/observable';
import {
  Http,
  RequestOptionsArgs,
  RequestOptions,
  Response,
  Headers
} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
/** */
class RequestMethod{
  public static readonly GET = "GET";
}

@Injectable()
export class BookTypeManagerService {

  constructor(private http: Http) { }
    //调用远程地址，获取数据 get方法
    public getGetData(url: string) {
      return this.http
        .get(url)
        //.toPromise()
        //.then(response => response.json() as any)
        .map(response => response.json() as any)
        .catch(this.handleError);
  
    }
    //错误处理
  private handleError(error: any): Promise<any> {
    
    return Promise.reject(error.message || error);
  }
}
