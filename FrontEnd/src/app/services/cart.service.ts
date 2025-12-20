import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartCountSource = new BehaviorSubject<number>(0);
  currentCount = this.cartCountSource.asObservable();

  constructor() { }

  addToCart(amount: number = 1) {
    this.cartCountSource.next(this.cartCountSource.value + amount);
  }

  removeFromCart(amount: number = 1) {
    const current = this.cartCountSource.value;
    if (current > 0) {
      this.cartCountSource.next(current - amount);
    }
  }
}