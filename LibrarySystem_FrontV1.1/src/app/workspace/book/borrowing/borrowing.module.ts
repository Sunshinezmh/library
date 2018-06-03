import { NgModule, } from '@angular/core';
import { CommonModule } from '@angular/common';
// 在每个浏览器中运行的跟模块都需要引入browsermodule
import{BorrowingComponent} from'./borrowing.component';
import { RouterModule } from '@angular/router';
import{BorrowingRoutes} from'./borrowing.routes';

import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';
import { TabsModule } from 'ngx-bootstrap/tabs';
import {borrowingbystatusModel } from '../../../models/borrowing-model';
import { AlreadyComponent } from './already/already.component';
import { ExceedComponent } from './exceed/exceed.component';


@NgModule ({
  imports: [  
    CommonModule,
    RouterModule.forChild(<any>BorrowingRoutes),
    HttpModule,
    FormsModule,
    TabsModule.forRoot()
  ],
  providers:[],

  declarations: [
    BorrowingComponent,
    ExceedComponent,
    AlreadyComponent,
  ]
})
export class BorrowingModule { }
