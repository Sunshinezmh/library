import{BookTypeComponent} from'./book-type.component';

export const BookTypeRoutes=[
    {
        path:'',
        component:BookTypeComponent,
        children: [
            {path: '', redirectTo: 'borrow', pathMatch: 'full'},
            {path: 'borrow', component: BookTypeComponent}
          ]
        
         
    }

]; 