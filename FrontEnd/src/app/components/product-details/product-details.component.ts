import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product-details.service';
import { CartService } from '../../services/cart.service';
import { Product, ProductCardComponent } from '../product-card/product-card.component';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [CommonModule, ProductCardComponent], 
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css'
})
export class ProductDetailsComponent implements OnInit {
  product: Product | undefined;
  relatedProducts: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService
  ) { }

  ngOnInit() {

    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.product = this.productService.getProductById(id);
      this.relatedProducts = this.productService.getProducts().filter(p => p.id !== id);
      window.scrollTo(0, 0);
    });
  }

  addToCart() {
    this.cartService.addToCart(1);
  }

  toggleWishlist() {
    if (this.product) {
      this.product.isWishlisted = !this.product.isWishlisted;
    }
  }
}