import { PeopleManageComponent } from './people-manage.component';
export const PeopleManageRoutes = [
  {
       path: '',
       component: PeopleManageComponent,
       children: [
        
              { path: '', redirectTo: 'teacher-manager', pathMatch: 'full' },
              { path: 'teacher-manager', loadChildren: './teacher-manager/teacher-manager.module#TeacherManagerModule' },  //信息管理
              { path: 'student-manager', loadChildren: './student-manager/student-manager.module#StudentManagerModule' },//类别管理
              { path: 'qrcode-show', loadChildren: './qrcode-show/qrcode-show.module#QrcodeShowModule' },  //入库管理
          ]
    
  }
];


