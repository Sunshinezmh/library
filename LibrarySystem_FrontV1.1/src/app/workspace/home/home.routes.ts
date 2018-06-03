
import { HomeComponent } from './home.component';
export const HomeRoutes = [
    {
        path: '',
        component: HomeComponent,     
        children: [ 
            {path: '', redirectTo: 'home', pathMatch: 'full'}
        ]
    }    
];