import { BorrowingComponent } from './borrowing.component';
import{AlreadyComponent} from './already/already.component';
import{ExceedComponent} from './exceed/exceed.component';
import{ReserveInfoComponent} from './reserve-info/reserve-info.component';
export const BorrowingRoutes = [
    {
        path: '',
        component: BorrowingComponent,
        children:[
            {path: '', redirectTo: 'borrow', pathMatch: 'full'},
            {path: 'borrow', component: BorrowingComponent},
            {path: 'already', component: AlreadyComponent},
            {path: 'exceed', component: ExceedComponent},
            // {path: 'reserve-info', component: ReserveInfoComponent}
            // {
            //     path:'reserve-info/:isbn/:location',
            //     component:ReserveInfoComponent
            // },
            // {
            //     //书籍预约详情，直接跳到了http://localhost:4200/下面
            //     path:'reserve-info/:isbn/:location',
            //     loadChildren: 'reserve-info/reserve-info.module#ReserveInfoModule'//直接跳转到书籍预约详情
        
            // },  
        ]

    }
];
