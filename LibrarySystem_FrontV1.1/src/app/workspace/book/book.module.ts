import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{BookComponent} from'./book.component';
import { RouterModule } from '@angular/router';
import{BookRoutes} from'./book.routes';
import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';
// import { ReservebookComponent } from './reservebook/reservebook.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>BookRoutes),
    
    HttpModule,
    FormsModule
  ],
  declarations: [
    BookComponent
    // ReservebookComponent
  ],
  providers:[]

})
export class BookModule { }
