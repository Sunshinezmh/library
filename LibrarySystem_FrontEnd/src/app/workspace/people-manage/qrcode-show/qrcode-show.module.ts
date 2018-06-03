import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {QrcodeShowComponent } from './qrcode-show.component';
import {rcodeShowRoutes } from './qrcode-show.routes';
import {DataTableModule } from 'ng-itoo-datatable'; //data-table组件
import { FileUploadModule } from 'ng2-file-upload';

import {SearchModule} from 'ng2-itoo-search'; //search组件
@NgModule({
  imports: [
    CommonModule,
    DataTableModule,//data-table组件
    FileUploadModule,//导入导出
    SearchModule, //search组件
    RouterModule.forChild(rcodeShowRoutes)
  ],
  declarations: [
    QrcodeShowComponent
  ]
})
export class QrcodeShowModule { }
