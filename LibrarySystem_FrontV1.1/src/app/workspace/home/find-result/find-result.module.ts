import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{FindResultComponent} from'./find-result.component';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';
import{FindResultRoutes} from'./find-result.routes';

import { FormsModule } from '@angular/forms';
import {GrowlModule} from 'primeng/primeng';
@NgModule({  
  imports: [
    GrowlModule,
    CommonModule,
    HttpModule,
    RouterModule.forChild(<any>FindResultRoutes)
  ],
  providers:[],
  declarations: [
    FindResultComponent
  ]
})
export class FindResultModule { }
