import {BookInfoComponent} from './workspace/home/book-info/book-info.component';
import {BookListComponent} from './workspace/book-type/book-list/book-list.component';
import {ReservebookComponent} from './workspace/book/reservebook/reservebook.component';
import{UploadPictureComponent} from './workspace/book/borrowing/upload-picture/upload-picture.component';
import { PersonalInfoComponent } from './workspace/book/PersonalInfo/PersonalInfo.component';

export const appRoutes = [
    {
        path: '',
        redirectTo: 'workspace',//项目启动第一个路由
        pathMatch: 'full'
    },
    {
        path: 'workspace',
        loadChildren: './workspace/workspace.module#WorkspaceModule'//主页面

    },
    {
        //没有登录时的状态，直接跳到了http://localhost:4200/下面
        path: 'login',
        loadChildren: './workspace/login/login.module#LoginModule'//直接跳转到登录

    },
    {
        //登录个人信息，直接跳到了http://localhost:4200/下面
        path: 'PersonalInfo',
        // loadChildren: './workspace/book/PersonalInfo/PersonalInfo.module#PersonalInfoModule'//直接跳转到个人信息
        component: PersonalInfoComponent
        
    },
    {
        //预约，直接跳到了http://localhost:4200/下面
        path:'reservebook',
        loadChildren: './workspace/book/reservebook/reservebook.module#ReservebookModule'//直接跳转到预约
    },
    {
        //借阅，直接跳到了http://localhost:4200/下面
        path: 'borrowing',
        loadChildren: './workspace/book/borrowing/borrowing.module#BorrowingModule'//直接跳转到借阅

    },
    {
        //书籍借阅详情，直接跳到了http://localhost:4200/下面
        path: 'reservation',
        loadChildren: './workspace/book/reservation/reservation.module#ReservationModule'//直接跳转到书籍借阅详情

    },    
    {
        //搜索，直接跳到了http://localhost:4200/下面
        path: 'search',
        loadChildren: './workspace/home/search/search.module#SearchModule'//直接跳转到搜索

     },    
    {
        //修改密码，直接跳到了http://localhost:4200/下面
        path: 'changepwd',
        loadChildren: './workspace/book/PersonalInfo/changepwd/changepwd.module#ChangepwdModule'//直接跳转到书籍详情

    },
    {
        //书架，直接跳到了、、、、、田成荣
        path: 'collection',
        loadChildren: './workspace/collection/collection.module#CollectionModule'//直接跳转到搜索
        
        },
        {
        //首页，直接跳到了、、、田成荣
        path: 'home',
        loadChildren: './workspace/home/home.module#HomeModule'//直接跳转到搜索
        
        },
        {
        //书城，直接跳到了、、、田成荣
        path: 'book-type',
        loadChildren: './workspace/book-type/book-type.module#BookTypeModule'//直接跳转到搜索
        
        },
        {
            path:'find-result', //查询
            loadChildren:'./workspace/home/find-result/find-result.module#FindResultModule'//采用懒加载
            
        },
        {
            path:'book-info/:isbn',
            component:BookInfoComponent
        },
        {
           path:'book-info/:isbn/:location',
           component:BookInfoComponent
       },
       {
          path:'bookList/:location/:typeName/:typeId/:bookNum',
          component:BookListComponent
      },
      {
        path:'upload-picture/:isbn/:location',
        component:UploadPictureComponent
    }
  
];
