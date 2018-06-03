import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpInterceptorService } from '../../shared/interceptorservice';

import { LoginModel } from '../../models/Loginmodls';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, Message } from 'primeng/primeng';
import { User } from '../../models/Loginmodls';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [ConfirmationService]
})
export class LoginComponent implements OnInit {
  // public msgs: any;
  message = "";
  msgs: Message[] = [];
  display = false;
  //跳转到首页
  style = {
    "margin-top": "20px"
  }

  constructor(
    private http: HttpInterceptorService,
    private router: Router,
    public confirmationService: ConfirmationService
  ) { }


  public user: User = new User();
  public willnavigateUrl: string;
  ngOnInit() {
  }
 

  // ----------------itoo登录start------------
  /**-----------------------登录--------------------------------------------------
   * @param userCode 用户名
   * @param password 密码
   */

  login() {
    const loginUrl = 'auth-web/access/login';
    let userCode = this.user.userCode;
    let password = this.user.password;
    if (userCode == undefined || userCode == undefined) {
      this.msgs = [];
      this.msgs = [{
        severity: 'error',
        summary: '警告',
        detail: "亲，用户名和密码不能为空哦"
      }]
      return;
    } else {
      const body = JSON.stringify({
        userCode: userCode.trim(),
        password: password.trim()
      });
      this.http.post(loginUrl, body).toPromise().then(
        res => {
          if (res.json().code === '0000') {
            console.log(res.json().data);
            localStorage.setItem('Authorization', res.json().data.token);
            localStorage.setItem('appuserId', res.json().data.id);
            localStorage.setItem('userName', res.json().data.userName);
            localStorage.setItem('userCode', res.json().data.userCode);
            localStorage.setItem('companyId', res.json().data.companyId);
            
            
            this.router.navigateByUrl("workspace/book");
          } else if (res.json().code === '1001') {
            this.confirm();
          } else if (res.json().code === '1111') 
          {
            this.msgs = [{
              severity: 'error',
              summary: '提示',
              detail: "亲，您的用户名或密码不对！"
            }]
            return;
          }
        }
      ).catch(this.handleError)
    }
  }
  DiaMessage: "";
  showDialog(string) {
    this.DiaMessage = string;
    this.display = true;
  }


  public handleError(error: any): Promise<any> {
    this.showDialog('亲，抱歉登录失败奥');
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  confirm() {
    this.confirmationService.confirm({
      message: '用户已在线，如果您继续登陆，将强制其他用户下线',
      header: '温馨提示',
      icon: 'fa fa-question-circle',
      accept: () => {

        //退出跳转到登录页
        const quitURL = 'auth-web/access/logout/' + this.user.userCode;
        this.http.get(quitURL).subscribe(
          res => {
            if (res.json().code == '0000') {
              let locat=localStorage.getItem('comName');
              localStorage.clear();
              localStorage.setItem('comName',locat);
              
              this.login();
            }
          }
        );
      },
      reject: () => {
        this.router.navigate(['/login']);
      }
    });
  }
  exit(){
    this.router.navigateByUrl("workspace"); 
  }
}

