import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookTypeManagerComponent } from './book-type-manager.component';
import { BookTypeManagerRoutes } from './book-type-manager.routes';
import { RouterModule } from '@angular/router';
import{DataTableModule} from 'ng-itoo-datatable';
import { FileUploadModule } from 'ng2-file-upload';
import{SearchModule} from 'ng2-itoo-search';
import {FormsModule,ReactiveFormsModule} from '@angular/forms'; //验证
import{CustomFormsModule} from "ng2-validation"; //验证
import {DialogModule} from 'primeng/primeng'; //对话框
import {ButtonModule,ConfirmDialogModule,ConfirmationService} from 'primeng/primeng'; //

import {TreeModule} from 'ng-itoo-tree';

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    SearchModule,
    TreeModule,
    DialogModule,
    CustomFormsModule,
    ReactiveFormsModule,
    ButtonModule,
    DataTableModule,
    FileUploadModule,
    ConfirmDialogModule,
    RouterModule.forChild(BookTypeManagerRoutes)
  ],
  declarations: [
    BookTypeManagerComponent
  ],
  providers:[ConfirmationService]
})
export class BookTypeManagerModule { }
