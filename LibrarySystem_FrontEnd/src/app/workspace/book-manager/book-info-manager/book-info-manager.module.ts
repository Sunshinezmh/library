import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{RouterModule} from '@angular/router';
import { BookInfoManagerComponent } from './book-info-manager.component';
import { BookInfoManagerRoutes } from './book-info-manager.routes';
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
// 使用组件查询框及按钮，需要引用模块
import{SearchModule} from 'ng2-itoo-search';
import {DialogModule} from 'primeng/primeng'; //对话框
import {ButtonModule} from 'primeng/primeng'; //
import {TreeModule} from 'ng-itoo-tree';
import {CalendarModule} from 'primeng/primeng';//日期控件

import { FormsModule, ReactiveFormsModule } from '@angular/forms'; //这两个是表单验证
import {CustomFormsModule} from "ng2-validation";

import {GrowlModule} from 'primeng/primeng';  //提示框

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BookInfoManagerRoutes),
    CalendarModule, //日期控件
    
      DataTableModule, //表格
      FileUploadModule,
      SearchModule,
      FormsModule,
      SearchModule, //查询按钮
      DialogModule,
      ButtonModule,
      TreeModule,//数组件
      
      FormsModule,//下面三个都是表单验证
      CustomFormsModule,
      ReactiveFormsModule,

      GrowlModule,//提示框
  

  ],
  declarations: [
    BookInfoManagerComponent
  ]

})
export class BookInfoManagerModule { }
