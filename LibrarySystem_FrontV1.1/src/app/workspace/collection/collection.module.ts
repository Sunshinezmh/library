import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{CollectionComponent} from'./collection.component';
import { RouterModule } from '@angular/router';
import{CollectionRoutes} from'./collection.routes';
import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';
import {GrowlModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>CollectionRoutes),
    HttpModule,
    FormsModule,
    GrowlModule
  ],
  providers:[],

  declarations: [
    CollectionComponent
  ]
})
export class CollectionModule { }
