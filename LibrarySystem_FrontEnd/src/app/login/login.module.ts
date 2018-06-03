import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LoginRoutes } from './login.routes';
import { FormsModule } from '@angular/forms';
import {DialogModule} from 'primeng/primeng'; //对话框
import {GrowlModule,ConfirmDialogModule} from 'primeng/primeng';

@NgModule({
    imports: [
    CommonModule,
    RouterModule.forChild(<any>LoginRoutes),
    FormsModule,
    DialogModule,
    ConfirmDialogModule
    
],
declarations: [
    ]
})
export class LoginModule { }