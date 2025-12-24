import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router'; 
import { CartService } from '../../services/cart.service'; 
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  menuActive = false;
  cartCount: number = 0;
  isDropdownOpen = false; 
  productsOpen = false;



  constructor(
    private cartService: CartService,
    public authService: AuthService,
    private router: Router
  ) {
    this.cartService.currentCount.subscribe(count => {
      this.cartCount = count;
    });
  }

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
  onLogout() {
    this.authService.logout();
    this.isDropdownOpen = false;
    this.menuActive = false;
    this.router.navigate(['/login']);
  }
}