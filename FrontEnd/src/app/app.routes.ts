import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'login', component: AuthComponent },
  { path: 'signup', component: AuthComponent },
  { path: 'product/:id', component: ProductDetailsComponent },
{ path: 'about', component: AboutComponent },
  {path:'Contact',component:ContactComponent},
];