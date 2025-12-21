import { Injectable } from '@angular/core';
import { Product } from '../components/product-card/product-card.component';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private products: Product[] = [
    {
      id: 1,
      image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Modern Laptop Backpack',
      category: 'Accessories',
      price: 109.95,
      rating: 4.8,
      reviews: 120,
      isNew: true,
      starBreakdown: { 5: 75, 4: 15, 3: 6, 2: 2, 1: 2 },
      reviewList: [
        {
          id: 101,
          username: "Sarah Jenkins",
          rating: 5,
          date: "Reviewed on Oct 15, 2023",
          title: "Perfect for commute and travel!",
          comment: "I absolutely love this backpack. The laptop compartment is well padded and there are so many pockets for organization. It looks professional enough for work but casual enough for weekends.",
          verified: true
        },
        {
          id: 102,
          username: "Mike D.",
          rating: 4,
          date: "Reviewed on Sept 2, 2023",
          title: "Great quality, slightly smaller than expected",
          comment: "The materials feel premium and zippers are smooth. It fits my 15-inch laptop snugly. I just wish the main compartment had a little more depth for bulky items like a lunch box.",
          verified: true
        },
        {
          id: 103,
          username: "Alex T.",
          rating: 5,
          date: "Reviewed on Aug 20, 2023",
          title: "Best investment this year",
          comment: "Comfortable straps, sleek design, and very durable water-resistant material. Highly recommend.",
          verified: true
        }
      ]
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
      isSale: true,
      starBreakdown: { 5: 50, 4: 30, 3: 10, 2: 5, 1: 5 },
      reviewList: [
        {
          id: 201,
          username: "John Doe",
          rating: 4,
          date: "Reviewed on July 10, 2023",
          title: "Nice fit",
          comment: "Fits well and feels soft. Shrunk a tiny bit after the first wash but still good and the model is black also.",
          verified: true
        }
      ]
    },
    {
      id: 3,
      image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Nike Air Sneakers',
      category: 'Footwear',
      price: 120.00,
      rating: 5,
      reviews: 410,
      starBreakdown: { 5: 90, 4: 8, 3: 1, 2: 0, 1: 1 },
      reviewList: []
    },
    {
      id: 4,
      image: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
      title: 'Wireless Noise Cancelling Headphones',
      category: 'Electronics',
      price: 250.00,
      originalPrice: 300.00,
      rating: 5,
      reviews: 850,
      isSale: true,
      starBreakdown: { 5: 85, 4: 10, 3: 3, 2: 1, 1: 1 },
      reviewList: []
    }
  ];

  constructor() { }

  getProducts() {
    return this.products;
  }

  getProductById(id: number) {
    return this.products.find(p => p.id === id);
  }
}