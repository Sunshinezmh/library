import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{ReserveInfoComponent} from'./reserve-info.component';
import { RouterModule } from '@angular/router';
import {ReserveInfoRoutes} from'./reserve-info.routes';

import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';




@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>ReserveInfoRoutes),
    HttpModule,
    FormsModule
   
  ],
  providers:[  ],
  declarations: [
    ReserveInfoComponent
  ]
})
export class ReserveInfoModule { }
