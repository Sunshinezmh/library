import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ReservationManagerComponent} from './reservation-management.component';
import {ReservationManagementRoutes } from './reservation-management.routes';
// 使用组件表格，需要引用这两个模块
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
// 使用组件查询框及按钮，需要引用模块
import{SearchModule} from 'ng2-itoo-search';
import {DialogModule} from 'primeng/primeng'; //对话框
import {ButtonModule} from 'primeng/primeng'; //
import {ConfirmDialogModule,ConfirmationService} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    // 引用组件的模块
    DataTableModule,  //表格
    FileUploadModule,
    FormsModule,
    ConfirmDialogModule,
    DialogModule,
    ButtonModule,
    SearchModule, //查询按钮
  
    // ***********************
    RouterModule.forChild(ReservationManagementRoutes)
  ],
  providers:[ConfirmationService],
  declarations: [
    ReservationManagerComponent
  ]
})
export class ReservationManagementModule { }
