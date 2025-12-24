import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product-details.service';
import { CartService } from '../../services/cart.service';
import { ProductCardComponent } from '../product-card/product-card.component';
import { Product } from '../../models/product';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [CommonModule, ProductCardComponent, RouterModule],
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
      this.productService.getProductById(id).subscribe({
        next: (data) => {
          this.product = data;
          window.scrollTo(0, 0);
        }
      });
      this.productService.getProducts().subscribe({
        next: (allProducts) => {
          this.relatedProducts = allProducts.filter(p => p.id !== id);
        }
      });
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