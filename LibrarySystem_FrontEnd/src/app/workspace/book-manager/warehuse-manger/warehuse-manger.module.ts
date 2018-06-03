import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WarehuseMangerComponent } from './warehuse-manger.component';
import { WarehuseMangerRoutes } from './warehuse-manger.routes';
import { RouterModule } from '@angular/router';
import { FileUploadModule } from 'ng2-file-upload';
import { FormsModule } from '@angular/forms'; //双向绑定
import {DialogModule} from 'primeng/primeng'; //对话框
import { ReactiveFormsModule } from '@angular/forms';//表单验证
import {CustomFormsModule} from "ng2-validation";
import {GrowlModule} from 'primeng/primeng';

@NgModule({
  imports: [
    CommonModule,
     FileUploadModule,
    RouterModule.forChild(WarehuseMangerRoutes),
    DialogModule,
    /**表单验证 */
    FormsModule,   
    CustomFormsModule,
    ReactiveFormsModule,
    GrowlModule

  ],
  declarations: [
    WarehuseMangerComponent
  ]
})
export class WarehuseMangerModule { 

}
