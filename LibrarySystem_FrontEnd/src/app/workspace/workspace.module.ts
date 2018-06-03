import { NgModule } from '@angular/core';
 import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { WorkspaceComponent } from './workspace.component';
import { workspaceRoutes } from './workspace.routes';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {MenubarModule,MenuItem} from 'primeng/primeng'; //priemng
import { HttpModule } from '@angular/http';
import { PrintPageComponent } from './print-page/print-page.component';

@NgModule({
  imports: [
     CommonModule,
     MenubarModule,
     FormsModule,
    RouterModule.forChild(<any>workspaceRoutes)

  ],
  declarations: [
    WorkspaceComponent,
    PrintPageComponent
    ],
  providers:[]
})
export class WorkspaceModule { }
