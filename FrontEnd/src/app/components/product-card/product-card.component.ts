import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { RouterModule } from '@angular/router';


export interface Review {
  id: number;
  username: string;
  rating: number;
  date: string;
  title: string;
  comment: string;
  verified: boolean;
}
export interface Product {
  id: number;
  image: string;
  title: string;
  category: string;
  price: number;
  originalPrice?: number;
  rating: number;
  reviews: number;
  isNew?: boolean;
  isSale?: boolean;
  isWishlisted?: boolean;
  reviewList?: Review[];
  starBreakdown?: { 5: number, 4: number, 3: number, 2: number, 1: number };
}

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent {
  // bta5od data mn el parent component
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
    this.product.isWishlisted = !this.product.isWishlisted;
    if (this.product.isWishlisted) {
      console.log('Added to Wishlist:', this.product.title);
    } else {
      console.log('Removed from Wishlist:', this.product.title);
    }
    event.stopPropagation();
    console.log('Toggled wishlist:', this.product.title);
  }
}