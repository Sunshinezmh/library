import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {FileUploader} from 'ng2-file-upload';
import { PersonInfoModel } from '../../../models/personinfomodel';
import { HttpInterceptorService } from '../../../shared/interceptorservice'

const FEMALE = "assets/image/female.svg";
const MALE = "assets/image/male.svg";
const PERSON = "assets/image/person.svg";

@Component({
  selector: 'app-PersonalInfo',
  templateUrl: './PersonalInfo.component.html',
  styleUrls: ['./PersonalInfo.component.css']
})

export class PersonalInfoComponent implements OnInit {
  public student: PersonInfoModel = new PersonInfoModel;
  public editPicture = true; // 修改图片
  public display = false; // 提示窗口是否显示
  msgs: any; // 右上角提示框内容
  public defaultPicture = 'assets/image/default-avatar.png'; // 默认图片地址
 history=[];
 flag: string;
  // 上传配置
  uploadPictureUrl = this.http.getExportExcelUrl("book/uploadPicture");//上传图片的URL
  
  constructor(public http: HttpInterceptorService,
    public activeRoute: Router) {
  }

  ngOnInit() {
    this.getStudentInformation();
  }
  public uploader: FileUploader = new FileUploader(
    {
      url: this.uploadPictureUrl,
      method: "POST",
      removeAfterUpload: true,
      itemAlias: "multfile",
      allowedFileType: ["image"]
    }
  )
  /* 得到学生个人信息 */
  public getStudentInformation() {
    let studentNo = localStorage.getItem("userCode");
    let url = "student/getStudentBystudentCode/" + studentNo;
    this.http.get(url).subscribe(
      res => {
        if (res != null && res.json().code == "0000") {
          this.student = res.json().data[0];
          console.log(res.json().data[0])
          // 将默认图片改为学生图片
          if (res.json().data[0].pictrue == null || res.json().data[0].pictrue == "") {
            switch (res.json().data[0].sex) {
              case "男":
                this.student.pictrue = MALE;
                break;
              case "女":
                this.student.pictrue = FEMALE;
                break;
              default:
                this.student.pictrue = PERSON;
                break;
            }
          }
          else {
            this.student.pictrue =this.http.fastDsfUrl+ res.json().data[0].pictrue;
            const el: Element = document.getElementById('wizardPicturePreview');
            el.setAttribute('src', this.student.pictrue);
          }
        }
      }
    );
  }



  /* 更新照片 */
  public updatePicture() {
    this.editPicture = !this.editPicture;
    if (this.editPicture == true) {
      this.student.pictrue = localStorage.getItem('studentPictureURL');
      console.log(this.student.pictrue)
      this.updateStudentInformation();
      const el: Element = document.getElementById('wizard-picture');
      el.setAttribute('class', 'showPicture');

      console.log('After--' + this.student);
    }
  }

  // 上传图片
  public selectedFileOnChanged() {
    this.editPicture = !this.editPicture;
    // 上传
    this.uploader.queue[0].onSuccess = function (response, status, headers) {
      // 上传文件成功
      if (status == 200) {
        // 上传文件后获取服务器返回的数据
        const tempRes = JSON.parse(response);
        // 修改页面图片
        const pictureURL: string = tempRes.data;
        console.log(pictureURL)
        const el: Element = document.getElementById('wizardPicturePreview');
        el.setAttribute('src', pictureURL);
        console.log(el)
        const el2: Element = document.getElementById('wizard-picture');
        console.log(el2)
        el2.setAttribute('class', 'hidePicture');
        // 存储图片路径
        localStorage.setItem('studentPictureURL', pictureURL);
      } else {
        // 上传文件后获取服务器返回的数据错误
        this.msgs = [{
          severity: 'error',
          summary: '提示',
          detail: '上传图片失败'
        }];
      }
    };
    this.uploader.queue[0].upload(); // 开始上传
  }


  /* 更新学生信息 */
  updateStudentInformation() {
    const url = 'student/updateStudent';
    const body = JSON.stringify(this.student);
    console.log('this.student==-=-=' + this.student);
    this.http.post(url, body).subscribe(
      res => {
        if (res.json().code == '0000') {
          this.msgs = [{
            severity: 'success',
            summary: '提示',
            detail: '修改成功！'
          }];
        } else {
          this.msgs = [{
            severity: 'error',
            summary: '提示',
            detail: '修改失败'
          }];
        }
      }
    );
  }
    //退出回到首页
    public unlogin(): void {
      const quitURL='auth-web/access/logout/' + localStorage.getItem("userCode");
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
            this.activeRoute.navigateByUrl("/workspace")
          }
        }
      )
   
    }
  //修改密码
  public changepwd(): void {
    this.activeRoute.navigateByUrl("/changepwd")
  }

  public routeToBook() {
    this.activeRoute.navigateByUrl("workspace/book");
  }

}
