import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductService } from './product-details.service';
import { Product } from '../models/product';

fdescribe('ProductService', () => {
  let service: ProductService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductService]
    });
    service = TestBed.inject(ProductService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should repair malformed "Double JSON" responses from the backend', () => {
    const brokenBackendResponse = `
      [{"id": 1, "title": "Broken Product", "rating": 5, "reviews": 0}]
      [{"id": 1, "title": "Broken Product", "rating": 5, "reviews": 0}]
    `;
    service.getProducts().subscribe((products: Product[]) => {
      expect(products.length).toBe(1);
      expect(products[0].title).toBe('Broken Product');
      console.log('TEST PASSED: Broken JSON was repaired successfully!');
    });
    const req = httpMock.expectOne('https://peculiar-ginni-mazen212-2562c12b.koyeb.app/products');
    expect(req.request.method).toBe('GET');
    req.flush(brokenBackendResponse);
  });
});