import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component'; // check this path

export const routes: Routes = [
  { path: 'login', component: AuthComponent },
  { path: 'signup', component: AuthComponent },
];