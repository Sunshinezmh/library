import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
// 使用组件表格，需要引用这两个模块
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
import {CheckComponent} from './check.component';
import {DialogModule} from 'primeng/primeng'; //对话框
import {CheckRoutes} from './check.routes'
import {GrowlModule} from 'primeng/primeng';
import {ConfirmDialogModule,ConfirmationService} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    DataTableModule,  //表格
    FileUploadModule,
    FormsModule,
    DialogModule,
    RouterModule.forChild(CheckRoutes),
    GrowlModule
  ],
  declarations: [
    CheckComponent
  ]
})
export class CheckModule { }
