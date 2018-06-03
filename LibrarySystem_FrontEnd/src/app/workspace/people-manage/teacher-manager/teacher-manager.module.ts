import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TeacherManagerComponent } from './teacher-manager.component';
import { TeacherManagerRoutes } from './teacher-manager.routes';
// 使用组件表格，需要引用这两个模块
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
import {DialogModule} from 'primeng/primeng'; //对话框
import {ButtonModule} from 'primeng/primeng'; //
// 使用组件查询框及按钮，需要引用模块
import{SearchModule} from 'ng2-itoo-search';

@NgModule({
  imports: [
    CommonModule,
    // 引用组件的模块
    DataTableModule,  //表格
    FileUploadModule,
    DialogModule,
    ButtonModule,
    FormsModule,
    SearchModule, //查询按钮
    RouterModule.forChild(TeacherManagerRoutes)
  ],
  declarations: [
    TeacherManagerComponent 
  ]
})
export class TeacherManagerModule { }
