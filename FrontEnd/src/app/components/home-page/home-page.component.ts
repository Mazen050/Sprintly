import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'
import { ProductCardComponent, Product } from '../product-card/product-card.component';

@Component({
  selector: 'app-home-page',
  standalone: true,
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
  imports: [CommonModule, ProductCardComponent]
})
export class HomePageComponent {
  // Data
  products: Product[] = [
    {
      id: 1,
      image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Modern Laptop Backpack',
      category: 'Accessories',
      price: 109.95,
      rating: 5,
      reviews: 120,
      isNew: true
    },
    {
      id: 2,
      image: 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Premium Slim Fit T-Shirt',
      category: 'Clothing',
      price: 22.30,
      originalPrice: 35.00,
      rating: 4,
      reviews: 259,
      isSale: true
    },
    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },

    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },


    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },

    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },

    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },


    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },


    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },


    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },


    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },




    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410
    },



  ];
}
