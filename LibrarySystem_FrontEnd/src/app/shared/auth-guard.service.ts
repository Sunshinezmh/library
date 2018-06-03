import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {
  Http,
  RequestOptionsArgs,
  RequestOptions,
  Response,
  Headers
} from '@angular/http';

const mergeToken = (options: RequestOptionsArgs = {}) => { 
  const newOptions = new RequestOptions({}).merge(options);
  const newHeaders = new Headers(newOptions.headers);
  const jwt = localStorage.getItem('Authorization');
  if (jwt) {
    newHeaders.set('Authorization', `Bearer ${jwt}`);
  }
  newOptions.headers = newHeaders;
  return newOptions;
};

/** */
class RequestMethod{
  // public static readonly  PUT = "PUT";
  public static readonly GET = "GET";
  // public static readonly POST = "POST";
  // public static readonly DELETE = "DELETE";
}

@Injectable()
export class AuthGuardService {

  constructor(private http: Http) { }
  get(url: string, options?: RequestOptionsArgs): Observable<Response> { 
    return this.http.get(url, mergeToken(options));
  }

  post(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.post(url, body, mergeToken(options));
  }

  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.post(url, body, mergeToken(options));
  }

  delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.post(url, mergeToken(options));
  }

  patch(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.patch(url, body, mergeToken(options));
  }

  head(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.head(url, mergeToken(options));
  }
  /**图书馆实例 */

  public getData(url: string,method:string): Promise<any> {
    if( method == RequestMethod.GET){
      return this.getGetData(url);

    }

  }

  
    //调用远程地址，获取数据 get方法
    public getGetData(url: string): Promise<any> {
      return this.http
        .get(url)
        .toPromise()
        .then(response => response.json() as any)
        .catch(this.handleError);
  
    }
    //错误处理
  private handleError(error: any): Promise<any> {
    
    return Promise.reject(error.message || error);
  }
  // *************************************************
  /*权限验证获取身份票据 */
  getTicket(): string{
    let id = localStorage.getItem("userId") || sessionStorage.getItem("userId");
    let token = localStorage.getItem("Authorization") || sessionStorage.getItem("Authorization");
    return "?id=" + id + "&token=" + token;
  }
  /*获取登录用户的userId */
  getUserId(): string{
    let userId = localStorage.getItem("userId") || sessionStorage.getItem("userId");
    return userId;
  }

}
