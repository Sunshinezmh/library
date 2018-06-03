import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{ReservebookComponent} from'./reservebook.component';
import { RouterModule } from '@angular/router';
import {ReservebookRoutes} from'./reservebook.routes';

import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';




@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>ReservebookRoutes),
    HttpModule,
    FormsModule
   
  ],
  providers:[  ],
  declarations: [
    ReservebookComponent
  ]
})
export class ReservebookModule { }
