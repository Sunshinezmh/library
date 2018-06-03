import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PeopleManageComponent } from './people-manage.component';
import { PeopleManageRoutes } from './people-manage.routes';
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(<any>PeopleManageRoutes)
  ],
  declarations: [
    PeopleManageComponent
  ]
})
export class PeopleManageModule { }
