import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule, XHRBackend, RequestOptions } from '@angular/http';
import { RouterModule } from '@angular/router'; 
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import { FileUploadModule } from 'ng2-file-upload';
import {GrowlModule,ConfirmDialogModule} from 'primeng/primeng';
import {ConfirmationService} from 'primeng/primeng';

import { AppComponent } from './app.component';
import {appRoutes} from './app.routes';
import { HttpInterceptorService }from'./shared/interceptorservice';
export function interceptorFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions){
  let service = new HttpInterceptorService(xhrBackend, requestOptions);
  return service;
}
import {BookInfoComponent} from './workspace/home/book-info/book-info.component';
import {BookListComponent} from './workspace/book-type/book-list/book-list.component';
import{UploadPictureComponent} from './workspace/book/borrowing/upload-picture/upload-picture.component';
import { PersonalInfoComponent } from './workspace/book/PersonalInfo/PersonalInfo.component';

@NgModule({
  declarations: [
    AppComponent,
    BookInfoComponent,
    BookListComponent,
    UploadPictureComponent,
    PersonalInfoComponent
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    FileUploadModule,
    GrowlModule,
    BrowserAnimationsModule,
    ConfirmDialogModule,
    RouterModule.forRoot(appRoutes)
    
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    HttpInterceptorService,ConfirmationService,
    {
      provide: HttpInterceptorService,
      useFactory: interceptorFactory, 
      deps: [XHRBackend, RequestOptions]
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
