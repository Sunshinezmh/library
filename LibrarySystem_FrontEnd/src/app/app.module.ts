import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule,XHRBackend,RequestOptions } from '@angular/http';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {appRoutes} from './app.routes';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {DialogModule} from 'primeng/primeng'; //对话框
import {ConfirmationService} from 'primeng/primeng';

import {GrowlModule,ConfirmDialogModule} from 'primeng/primeng';
//拦截器服务
import{HttpInterceptorService } from './shared/interceptorservice';
export function interceptorFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions){
  let service = new HttpInterceptorService(xhrBackend, requestOptions);
  return service;
}
 //第二步：引用1

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
   
  ],
  imports: [
    BrowserModule,
    FormsModule,
    GrowlModule,
    ConfirmDialogModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),   //创建根路由模块3
    DialogModule
  ],
  providers: [
    HttpInterceptorService,ConfirmationService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    {
      provide: HttpInterceptorService,
      useFactory: interceptorFactory,
      deps: [XHRBackend, RequestOptions]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
