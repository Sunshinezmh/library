import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';   //必须有
import { HttpModule } from '@angular/http';
import{  HomeRoutes} from './home.routes';
import { CommonModule } from '@angular/common'; //只在子中出现
import { CarouselModule } from 'ngx-bootstrap';

import { FormsModule } from '@angular/forms';
import { HomeModel } from '../../models/homemodel';
import {GalleriaModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CarouselModule,
    CommonModule,
    RouterModule.forChild(<any>HomeRoutes),
    HttpModule,
    FormsModule
    // InMemoryWebApiModule.forRoot(InMemoryDataService),
   
  ],
  providers:[],
  declarations: [
    HomeComponent  
  ]
})
export class HomeModule { }





