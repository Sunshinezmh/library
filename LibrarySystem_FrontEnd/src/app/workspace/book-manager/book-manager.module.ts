import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule   } from '@angular/forms';
import { BookManagerComponent } from './book-manager.component';
import { BookManagerRoutes } from './book-manager.routes';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(BookManagerRoutes),
  ],
  declarations: [
    BookManagerComponent,
  ]
})
export class BookManagerModule { }



