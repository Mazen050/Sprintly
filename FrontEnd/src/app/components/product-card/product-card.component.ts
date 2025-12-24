import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { RouterModule } from '@angular/router';
import { Product } from '../../models/product'; 

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent {
  @Input({ required: true }) product!: Product;
  quantity: number = 0;

  constructor(private cartService: CartService) { }
  addToCart(event: Event) {
    event.stopPropagation();
    this.quantity = 1;
    this.cartService.addToCart(1);
  }

  increment(event: Event) {
    event.stopPropagation();
    this.quantity++;
    this.cartService.addToCart(1);
  }

  decrement(event: Event) {
    event.stopPropagation();
    if (this.quantity > 0) {
      this.quantity--;
      this.cartService.removeFromCart(1);
    }
  }

  toggleWishlist(event: Event) {
    if (this.product.isWishlisted === undefined) {
       this.product.isWishlisted = false;
    }
    this.product.isWishlisted = !this.product.isWishlisted;
    event.stopPropagation();
  }
}