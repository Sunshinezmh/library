import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { CustomFormsModule } from 'ng2-validation';
import { StudentManagerComponent } from './student-manager.component';
import { StudentManagerRoutes } from './student-manager.routes';
// 使用组件表格，需要引用这两个模块
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
// 使用组件查询框及按钮，需要引用模块
import{SearchModule} from 'ng2-itoo-search';
//使用树组件
import{TreeModule} from 'ng-itoo-tree';
import {DialogModule} from 'primeng/primeng'; //对话框
import {ButtonModule} from 'primeng/primeng'; //

@NgModule({
  imports: [
    CommonModule,
    // 引用组件的模块
    DataTableModule,  //表格
    FileUploadModule,
    FormsModule,
    ReactiveFormsModule,
    CustomFormsModule,
    DialogModule,
    ButtonModule,
    SearchModule, //查询按钮
    TreeModule, //树组件
    // ***********************
    RouterModule.forChild(StudentManagerRoutes)
  ],
  declarations: [
    StudentManagerComponent
  ]
})
export class StudentManagerModule { }
