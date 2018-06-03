import { Component, OnInit, ViewChild, Renderer2, ElementRef } from '@angular/core';
import { Http, RequestOptions, Headers, Jsonp, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginModel } from '../models/login.model';
import { fadeIn } from '../animations/fade-in';
import { HttpInterceptorService } from '../shared/interceptorservice';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { ConfirmationService, Message } from 'primeng/primeng';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn],
  providers: [ConfirmationService]
})
export class LoginComponent implements OnInit {
  user = new LoginModel;
  constructor(
    public router: Router,
    public activatedRoute: ActivatedRoute,
    public http: HttpInterceptorService,
    public confirmationService: ConfirmationService
  ) { }

  ngOnInit() {
  }
  msgs: Message[] = [];
  display = false;

  public login(valid): void {
    if (valid == true) {
      this.loginService(this.user);
    }
  }

  //提示框
  displayP = false;
  message: string = "";

  public showDialog(string) {
    this.message = string;
    this.displayP = true;
  }

  public loginService(user: LoginModel) {
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
          console.log("123")
          console.log(res.json().code)
          if (res.json().code === '0000') {
            console.log(res.json().data);
            localStorage.setItem('Authorization', res.json().data.token);
            localStorage.setItem('appuserId', res.json().data.id);
            localStorage.setItem('userName', res.json().data.userName);
            localStorage.setItem('userCode', res.json().data.userCode);
            localStorage.setItem('companyId', res.json().data.companyId);


            this.router.navigateByUrl("workspace/borrowing-manager");
          } else if (res.json().code === '1001') {
            this.confirm();
          } else if (res.json().code === '1111') {
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
              let locat = localStorage.getItem('comName');
              localStorage.clear();
              localStorage.setItem('comName', locat);

            }
          }
        );
      },
      reject: () => {
        this.router.navigate(['/login']);
      }
    });
  }
  exit() {
    this.router.navigateByUrl("workspace");
  }

}

