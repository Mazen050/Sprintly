import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductCardComponent, Product } from '../product-card/product-card.component';
import { ProductService } from '../../services/product-details.service';

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
    this.products = this.productService.getProducts();
    this.heroProducts = this.products.filter(p => p.isSale).slice(0, 4);

    if (this.heroProducts.length === 0) {
      this.heroProducts = this.products.slice(0, 4);
    }
    this.startSlideShow();
  }

  startSlideShow() {
    this.intervalId = setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.heroProducts.length;
    }, 3000);
  }

  get currentHero() {
    return this.heroProducts[this.currentIndex];
  }

  resetTimer() {
    clearInterval(this.intervalId);
    this.startSlideShow();
  }

  goToSlide(index: number) {
    this.currentIndex = index;
    this.resetTimer();
  }

  nextSlide() {
    this.currentIndex = (this.currentIndex + 1) % this.heroProducts.length;
    this.resetTimer();
  }

  prevSlide() {
    this.currentIndex = (this.currentIndex - 1 + this.heroProducts.length) % this.heroProducts.length;
    this.resetTimer();
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}