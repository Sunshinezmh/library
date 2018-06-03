import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{RouterModule} from '@angular/router';
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
// 使用组件查询框及按钮，需要引用模块
import{SearchModule} from 'ng2-itoo-search';
//对话框
import {DialogModule} from 'primeng/primeng'; 
import {ButtonModule} from 'primeng/primeng'; 
import {TreeModule} from 'ng-itoo-tree';
//日期控件
import {CalendarModule} from 'primeng/primeng';

//这两个是表单验证
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; 
import {CustomFormsModule} from "ng2-validation";
import {ConfirmDialogModule,ConfirmationService} from 'primeng/primeng';

import {  BookDetailsManagerComponent } from './book-details-manager.component';
import { BookDetailsRoutes  } from './book-details-manager.routes';
 //提示框
import {GrowlModule} from 'primeng/primeng'; 

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BookDetailsRoutes),
    //日期控件
    CalendarModule, 
    //表格
      DataTableModule, 
      FileUploadModule,
      SearchModule,
      FormsModule,
      //查询按钮
      SearchModule, 
      DialogModule,
      ButtonModule,
      //数组件
      TreeModule,
      //下面三个都是表单验证
      FormsModule,
      CustomFormsModule,
      ReactiveFormsModule,
      ConfirmDialogModule,
      //提示框
      GrowlModule,
  

  ],
  declarations: [
    BookDetailsManagerComponent
  ],
  providers:[ConfirmationService]

})
export class BookDetailsManagerModule { }
