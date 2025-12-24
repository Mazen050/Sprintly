import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'https://peculiar-ginni-mazen212-2562c12b.koyeb.app';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('auth_token');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }
  getProducts(): Observable<Product[]> {
    return this.http.get(`${this.baseUrl}/products`, {
      headers: this.getHeaders(),
      responseType: 'text'
    }).pipe(
      map(responseText => {
        let backendData: any;

        try {
          backendData = JSON.parse(responseText);
        } catch (e) {
          console.warn("Normal Parse Failed. Attempting to extract first JSON array...");
          const cleanText = this.extractFirstJson(responseText);

          if (cleanText) {
            try {
              backendData = JSON.parse(cleanText);
              console.log("JSON Extracted & Fixed Successfully!");
            } catch (e2) {
              console.error("Extraction Failed:", e2);
              return [];
            }
          } else {
            return [];
          }
        }
        let itemsArray = [];
        if (Array.isArray(backendData)) {
          itemsArray = backendData;
        } else if (backendData && Array.isArray(backendData.data)) {
          itemsArray = backendData.data;
        } else if (backendData && Array.isArray(backendData.products)) {
          itemsArray = backendData.products;
        }

        return itemsArray.map((item: any) => this.mapBackendToFrontend(item));
      })
    );
  }
  private extractFirstJson(text: string): string | null {
    let bracketCount = 0;
    let inString = false;
    let escape = false;
    let startFound = false;
    let startIndex = -1;

    for (let i = 0; i < text.length; i++) {
      const char = text[i];

      if (!startFound) {
        if (char === '[' || char === '{') {
          startFound = true;
          startIndex = i;
          bracketCount = 1;
        }
        continue;
      }
      if (char === '"' && !escape) {
        inString = !inString;
      }
      if (char === '\\' && !escape) {
        escape = true;
      } else {
        escape = false;
      }

      if (!inString) {
        if (char === '[' || char === '{') {
          bracketCount++;
        } else if (char === ']' || char === '}') {
          bracketCount--;
          if (bracketCount === 0) {
            return text.substring(startIndex, i + 1);
          }
        }
      }
    }
    return null;
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get(`${this.baseUrl}/products/${id}`, {
      headers: this.getHeaders(),
      responseType: 'text'
    }).pipe(
      map(responseText => {
        let item: any;
        try {
          item = JSON.parse(responseText);
        } catch (e) {
          const clean = this.extractFirstJson(responseText);
          if (clean) item = JSON.parse(clean);
        }
        return this.mapBackendToFrontend(item);
      })
    );
  }

  private mapBackendToFrontend(item: any): Product {
    if (!item) return {} as Product;

    const rating = item.rating || 0;
    const reviewCount = item.reviews || 0;

    const backendReviews = item.reviewList || [];
    let finalReviews: any[] = [];

    if (backendReviews.length > 0) {
      finalReviews = backendReviews.map((r: any, index: number) => ({
        id: index,
        username: "Verified Customer",
        rating: r.rating,
        date: new Date(r.createdAt).toLocaleDateString(),
        title: "Product Review",
        comment: r.comment,
        verified: true
      }));
    } else if (reviewCount > 0) {
      finalReviews = this.generateMockReviews(reviewCount, rating);
    }

    const starBreakdown = { 5: 0, 4: 0, 3: 0, 2: 0, 1: 0 };
    if (finalReviews.length > 0) {
      finalReviews.forEach((r: any) => {
        const rRating = Math.round(r.rating);
        if (rRating >= 1 && rRating <= 5) {
          // @ts-ignore
          starBreakdown[rRating]++;
        }
      });
      for (let star in starBreakdown) {
        // @ts-ignore
        starBreakdown[star] = (starBreakdown[star] / finalReviews.length) * 100;
      }
    }

    return {
      id: item.id,
      image: item.image || 'https://via.placeholder.com/300',
      title: item.title,
      category: item.category || 'General',
      price: item.price,
      originalPrice: item.originalPrice,
      rating: rating,
      reviews: reviewCount,
      isNew: item.isNew,
      isSale: item.isSale,
      isWishlisted: false,
      description: item.description,
      reviewList: finalReviews,
      starBreakdown: starBreakdown
    };
  }

  private generateMockReviews(count: number, avgRating: number): any[] {
    const mocks = [];
    for (let i = 0; i < count; i++) {
      mocks.push({
        id: i,
        username: `Customer ${i + 1}`,
        rating: avgRating,
        date: new Date().toLocaleDateString(),
        title: "Great Product!",
        comment: "This product met my expectations.",
        verified: true
      });
    }
    return mocks;
  }
}