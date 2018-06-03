import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{RouterModule} from '@angular/router';
import { BorrowingManagerComponent } from './borrowing-manager.component';
import { BorrowingManagerRoutes } from './borrowing-manager.routes';
import { DataTableModule } from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
import {SearchModule} from 'ng2-itoo-search';
import {CustomFormsModule} from "ng2-validation";
import {DialogModule} from 'primeng/primeng'; //对话框
import { FormsModule,ReactiveFormsModule  } from '@angular/forms'; //双向绑定
import {ButtonModule} from 'primeng/primeng'; //
import {CalendarModule} from 'primeng/primeng';//日期控件
import {ConfirmDialogModule,ConfirmationService} from 'primeng/primeng';
@NgModule({
  imports: [
    CalendarModule, //日期控件
    CommonModule,
    DataTableModule,
    FileUploadModule,
    DialogModule,
    FormsModule,
    ButtonModule,
    SearchModule,
    CustomFormsModule,
    ReactiveFormsModule,
    ConfirmDialogModule,
    RouterModule.forChild(BorrowingManagerRoutes)
  ],
  providers:[ConfirmationService],
  declarations: [
    BorrowingManagerComponent
  ]
  
})
export class BorrowingManagerModule { }
