import { BookManagerComponent } from './book-manager.component';
export const BookManagerRoutes = [
  {
       path: '',
       component: BookManagerComponent,
       children: [
        
              { path: '', redirectTo: 'book-details-manager', pathMatch: 'full' },
              { path: 'book-info-manager', loadChildren: './book-info-manager/book-info-manager.module#BookInfoManagerModule' },
              { path: 'book-type-manager', loadChildren: './book-type-manager/book-type-manager.module#BookTypeManagerModule' },
              { path: 'warehuse-manger', loadChildren: './warehuse-manger/warehuse-manger.module#WarehuseMangerModule' } ,
              { path: 'picture-manager', loadChildren: './picture-manager/picture-manager.module#PictureManagerModule' } ,
              {
                path: 'book-details-manager/:isbn/:location',
                loadChildren: './book-details-manager/book-details-manager.module#BookDetailsManagerModule'
              }
          ]
          
          
          
  }
];