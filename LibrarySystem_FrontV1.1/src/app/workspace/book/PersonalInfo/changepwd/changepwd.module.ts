import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule  } from '@angular/forms';

import{ChangepwdComponent} from'./changepwd.component';
import { RouterModule } from '@angular/router';
import{ChangepwdRoutes} from'./changepwd.routes';

import {GrowlModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    GrowlModule,
    RouterModule.forChild(<any>ChangepwdRoutes)
  ],
  declarations: [
    ChangepwdComponent
  ]
})
export class ChangepwdModule { }
