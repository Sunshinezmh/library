import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule   } from '@angular/forms';
import { PictureManagerComponent } from './picture-manager.component';
import { PictureManagerRoutes } from './picture-manager.routes';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';//表单验证
import {CustomFormsModule} from "ng2-validation";
import {GrowlModule} from 'primeng/primeng';
import { FileUploadModule } from 'ng2-file-upload';
import {DialogModule} from 'primeng/primeng'; //对话框

@NgModule({
  imports: [
    CommonModule,
    FileUploadModule,
    FormsModule,
    RouterModule.forChild(PictureManagerRoutes),
    DialogModule,
    /**表单验证 */
    CustomFormsModule,
    ReactiveFormsModule,
    GrowlModule
  ],
  declarations: [
    PictureManagerComponent
  ]
})
export class PictureManagerModule { }



