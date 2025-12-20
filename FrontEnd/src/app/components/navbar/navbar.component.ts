import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service'; 

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  menuActive = false;

  cartCount:number = 0;

  constructor(private cartService: CartService) {
    this.cartService.currentCount.subscribe(count => {
      this.cartCount = count;
    });
  }

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }
}