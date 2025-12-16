import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { HomePageComponent } from './components/home-page/home-page.component';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'login', component: AuthComponent },
  { path: 'signup', component: AuthComponent },
];