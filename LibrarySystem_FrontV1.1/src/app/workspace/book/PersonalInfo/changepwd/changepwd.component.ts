import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { HttpInterceptorService } from '../../../../shared/interceptorservice'


@Component({
  selector: 'app-changepwd',
  templateUrl: './changepwd.component.html',
  styleUrls: ['./changepwd.component.css']
})
export class ChangepwdComponent implements OnInit {
  history = [];
  flag: string;
  oldPwd: string = "";
  newPwd: string = "";
  userId: string = "";
  code: string = "";
  secondPwd = "";
  msgs: any;
  style = {
    "margin-top": "20px"
  }

  constructor(private router: Router, private http: HttpInterceptorService) { }

  ngOnInit() {

  }

  updatePwd() {
    this.userId = localStorage.getItem("appuserId");
    this.code = localStorage.getItem("userCode");

    if (this.newPwd != this.secondPwd) {
      this.msgs = [{
        severity: 'info',
        summary: '提示',
        detail: "两次不一致！"
      }]
      return;
    }
    if (this.userId == null) {
      this.router.navigateByUrl("workspace/login");
    }
    // let url = "user/updateTuser/" + this.oldPwd + "/" + this.newPwd + "/" + this.code;
    let url = "auth-web/user/updateUserPassword/" + this.userId + "/" + this.code + "/" + this.newPwd;

    this.http.post(url, null).toPromise().then(res => {
      if (res != null && res.json().code == "0000") {
        this.msgs = [{
          severity: 'success',
          summary: '提示',
          detail: "修改成功！"

        }]
        const quitURL = 'auth-web/access/logout/' + localStorage.getItem("userCode");
        this.http.get(quitURL).subscribe(
          res => {
            if (res.json().code == '0000') {
              this.flag = localStorage.getItem('flag');
              console.log(this.flag)
              for (let i = 0; i <= Number(this.flag); i++) {
                this.history.push(localStorage.getItem(i.toString()));
              }
              localStorage.clear();
              localStorage.setItem('flag',this.flag);
              for (let i =0 ; i <= Number(this.flag); i++) {
                localStorage.setItem(i.toString(), this.history[i]);
              }
              this.router.navigateByUrl("workspace/login");
            }
          }
        );

      } else {
        this.msgs = [{
          severity: 'error',
          summary: '提示',
          detail: "修改失败，请检查原密码是否正确！"
        }]
      }
    })
  }
  public toPersoninfo() {
    this.router.navigateByUrl("/PersonalInfo");
  }


}
