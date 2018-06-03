import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{ReservationComponent} from'./reservation.component';
import { RouterModule } from '@angular/router';
import{ReservationRoutes} from'./reservation.routes';

import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';
import {GrowlModule} from 'primeng/primeng';




@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>ReservationRoutes),
    HttpModule,
    FormsModule,
    GrowlModule
   
  ],
  providers:[  ],
  declarations: [
    ReservationComponent
  ]
})
export class ReservationModule { }
