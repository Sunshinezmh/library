import { Component, OnInit } from '@angular/core';
import{Router} from '@angular/router';
import {MenubarModule,MenuItem} from 'primeng/primeng'; //priemng

@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.css']
})
export class WorkspaceComponent implements OnInit {

  constructor
  (
    public router: Router
  
  ) { 

  }
  items:MenuItem[];
  
  ngOnInit() {
   this.items=[
     {
      label: '首页',
      routerLink:['/workspace/borrowing-manager']
     },
     {
        label: '借还书',
        routerLink:['/workspace/borrow']
      },
      {
        label: '预约管理',
        routerLink:['/workspace/reservation-management']
      },
      {
        label:'图书管理',
        items:
        [
           {label: '类别管理',  routerLink: ['/workspace/book-manager/book-type-manager']},
           {label: '入库管理',  routerLink: ['/workspace/book-manager/warehuse-manger']},          
           {label: '信息管理',  routerLink: ['/workspace/book-manager/book-info-manager']},
           {label: '图片管理',  routerLink: ['/workspace/book-manager/picture-manager']}
       ]
      },
      {
        label:'人员管理',
        items:
        [
           {label: '教师管理',  routerLink: ['/workspace/people-manage/teacher-manager']},
           {label: '学生管理',  routerLink: ['/workspace/people-manage/student-manager']}
       ]
      },
      {
        label:'基本设置',
        routerLink: ['/workspace/basic-setting']
      },
      {
          label:'图书盘点',
          routerLink:['/workspace/check']
      }
  ];
  }
  /**
   * 退出并没有走真正的后台，直接跳转到登录
   * TODO:走真正后台，清除后台redis缓存，同时前端移除localstorge的缓存
   */
  public quite(): void {
    this.router.navigateByUrl('login'); // 首页
    }
}
