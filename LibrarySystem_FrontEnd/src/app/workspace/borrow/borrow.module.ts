import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BorrowComponent } from './borrow.component';
import { BorrowRoutes } from './borrow.routes';
import {DialogModule} from 'primeng/primeng'; //对话框

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BorrowRoutes),
    DialogModule
  ],
  declarations: [
    BorrowComponent
  ]
})
export class BorrowModule { }
