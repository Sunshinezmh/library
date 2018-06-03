
import { WorkspaceComponent } from './workspace.component';
export const WorkspaceRoutes = [
    {
        path: '',
        component: WorkspaceComponent,
        children: [
            {
                path: '',
                redirectTo: 'home',//项目启动第一个路由
                pathMatch: 'full'
            },

            {
                path: 'home', //主页
                loadChildren: './home/home.module#HomeModule'//采用懒加载
            },
            {
                path: 'collection',//书架
                loadChildren: './collection/collection.module#CollectionModule'//采用懒加载CollectionModule
            },
            {
                path: 'book-type', //分类
                loadChildren: './book-type/book-type.module#BookTypeModule'//采用懒加载CollectionModule
            },           
            {
                path:'login', //登录
                loadChildren:'./login/login.module#LoginModule'//采用懒加载
                
            },
            {
                path:'book', //我的图书
                loadChildren:'./book/book.module#BookModule'//采用懒加载
                
            } ,
            {
                path:'find-result/:condition',   //查询
                loadChildren:'./home/find-result/find-result.module#FindResultModule'//采用懒加载
                
            }
        ]
    }

];