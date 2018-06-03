import { HttpModule } from '@angular/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchComponent } from './search.component';
import { RouterModule } from '@angular/router';
import { SearchRoutes } from './search.routes';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(<any>SearchRoutes),
    HttpModule

  ],
  providers: [],
  declarations: [
    SearchComponent
  ],

})
export class SearchModule { }
