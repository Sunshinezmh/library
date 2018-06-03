import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { LoginRoutes } from './login.routes';
import { FormsModule } from '@angular/forms';
import {GrowlModule,ConfirmDialogModule} from 'primeng/primeng';

@NgModule({
  imports: [
    RouterModule.forChild(<any>LoginRoutes),
    FormsModule,
    GrowlModule,
    ConfirmDialogModule

  ],
  declarations: [
    LoginComponent
  ]
})
export class LoginModule { }
