
import { LoginComponent } from './login/login.component';
export const appRoutes=[  //第一步：路由配置：一个带routers类型的数组，：approutes   .
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path:'login',  //http://localhost:4002/login   //同步路由
    component:LoginComponent
  },
  {
    path: 'workspace',     //异步路由
    loadChildren: './workspace/workspace.module#WorkspaceModule'
   }
    
  ];