import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import{BookTypeComponent} from'./book-type.component';
import { RouterModule } from '@angular/router';
import{BookTypeRoutes} from'./book_type.routes';
import {HttpModule} from '@angular/http';
import { FormsModule } from '@angular/forms';
// import { BookListComponent } from './book-list/book-list.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>BookTypeRoutes),
    HttpModule,
    FormsModule
   
  ],


  
  providers:[],

  declarations: [
    BookTypeComponent,
    // BookListComponent
  ]
})
export class BookTypeModule { }
