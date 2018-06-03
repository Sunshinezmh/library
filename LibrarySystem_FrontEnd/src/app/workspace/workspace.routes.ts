import { WorkspaceComponent } from './workspace.component';
import { PrintPageComponent } from './print-page/print-page.component';
export const workspaceRoutes = [
  {
    path: '',
    component: WorkspaceComponent,
    children: [


      { path: '', redirectTo: 'borrowing-manager', pathMatch: 'full' },
      { path: 'borrow', loadChildren: './borrow/borrow.module#BorrowModule' },  //首页
      { path: 'borrowing-manager', loadChildren: './borrowing-manager/borrowing-manager.module#BorrowingManagerModule' },//借阅记录查询
      { path: 'reservation-management', loadChildren: './reservation-management/reservation-management.module#ReservationManagementModule' },//预约管理
      { path: 'book-manager', loadChildren: './book-manager/book-manager.module#BookManagerModule' },  //图书管理
      { path: 'people-manage', loadChildren: './people-manage/people-manage.module#PeopleManageModule' },  //人员管理
      { path: 'basic-setting', loadChildren: './basic-setting/basic-setting.module#BasicSettingModule' },
      { path: 'check',loadChildren: './check/check.module#CheckModule'},
      {
        path: 'print-page',
        component: PrintPageComponent
      }   

    ]
  },

];

