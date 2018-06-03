import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkspaceComponent } from './workspace.component';
import { RouterModule } from '@angular/router';   //必须有

import{WorkspaceRoutes} from './workspace.routes';
import { HttpModule, XHRBackend, RequestOptions } from '@angular/http';
@NgModule({
  declarations: [
    WorkspaceComponent
  ],
  imports: [
    HttpModule,
    CommonModule,
    RouterModule.forChild(WorkspaceRoutes)//表示根目录
  ]

})
export class WorkspaceModule { }








