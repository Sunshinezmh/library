import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BasicSettingComponent} from './basic-setting.component';
import{BasicSettingRoutes} from './basic-setting.routes';
import { RouterModule } from '@angular/router';
import {ButtonModule} from 'primeng/primeng';
import { FormsModule } from '@angular/forms';
import{CustomFormsModule} from "ng2-validation"; //验证
import {DialogModule} from 'primeng/primeng'; //对话框

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(BasicSettingRoutes),
    ButtonModule,
    CustomFormsModule,
    DialogModule,
    FormsModule
      
  ],
  
  declarations: [
    BasicSettingComponent
  ]
})
export class BasicSettingModule { }
