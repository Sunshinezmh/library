import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Http, Headers, Response,RequestOptions ,Jsonp } from '@angular/http';
import {Router} from '@angular/router';
// import { AuthGuardService } from '../shared/auth-guard.service';
//引用model
import {LoginModel}from '../models/login.model' ;
//引用服务
import { HttpInterceptorService } from '../shared/interceptorservice'


@Injectable()
export class LoginService {
  
  // public userLoginURL = this.authGuardService.getAuthorityUrl() + 'access/login';

  
  constructor(
    public http:Http,
    public router: Router,
    // public authGuardService:AuthGuardService
  ) { }

  //提示框
displayP=false;
message:string="";

public showDialog(string){
  this.message=string;
  this.displayP=true;
}

  public login(user:LoginModel){
    let code=user.userCode;
    let pwd = user.password;
    let userLoginURL = 'http://192.168.22.153:8080/library/resources/user/login/'+code+"/"+pwd;  
    let token : string;
    var headers = new Headers();
    let id: string;
    let schoolNo:string;
  
    var content = JSON.stringify({
      userCode: user.userCode,
      password: user.password
    });
    var password=user.password;
    var username=user.userCode;
 
    headers.append('Content-Type', 'application/json');
    return this.http
            .post(userLoginURL, content,{headers:headers})
            .map((response: Response) => {
              token = response.json().data.token;
              id = response.json().data.id;
              schoolNo = response.json().data.schoolNo;
              if(token!=null && token!=""){                          
                localStorage.setItem("Authorization",token);
                localStorage.setItem("userId",id);
                localStorage.setItem("currentSchoolNo",schoolNo);
              }
              return response;

            })
            .subscribe(
              response => {
                if(response.json().code == "0000"){
                  // 用户ID
                  let Usercode = username;
                  // 密码
                  let Password = password;
                  localStorage.setItem("UserCode", code);
                  localStorage.setItem("PassWord", pwd);
                //跳转到借书记录
                   this.router.navigateByUrl("workspace/borrowing-manager");
                }
                return;
                },              
                error => {                                     
                    this.showDialog("用户名或密码错误！");
                }
            );
  }

}
