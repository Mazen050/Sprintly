import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductCardComponent } from '../product-card/product-card.component';
import { ProductService } from '../../services/product-details.service';
import { Product } from '../../models/product';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, ProductCardComponent, RouterModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit, OnDestroy {

  products: Product[] = [];
  heroProducts: Product[] = [];
  currentIndex = 0;
  intervalId: any;

  constructor(private productService: ProductService) { }

  ngOnInit() {
    this.productService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.heroProducts = this.products.filter(p => p.isSale).slice(0, 4);

        if (this.heroProducts.length === 0) {
          this.heroProducts = this.products.slice(0, 4);
        }

        this.startSlideShow();
      },
      error: (err) => console.error('Error loading products:', err)
    });
  }
  startSlideShow() {
    if (this.heroProducts.length === 0) return;

    this.intervalId = setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.heroProducts.length;
    }, 3000);
  }

  get currentHero() {
    if (this.heroProducts.length === 0) return undefined;
    return this.heroProducts[this.currentIndex];
  }

  resetTimer() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
    this.startSlideShow();
  }

  goToSlide(index: number) {
    this.currentIndex = index;
    this.resetTimer();
  }

  nextSlide() {
    if (this.heroProducts.length === 0) return;
    this.currentIndex = (this.currentIndex + 1) % this.heroProducts.length;
    this.resetTimer();
  }

  prevSlide() {
    if (this.heroProducts.length === 0) return;
    this.currentIndex = (this.currentIndex - 1 + this.heroProducts.length) % this.heroProducts.length;
    this.resetTimer();
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}