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
  originalPrice?: number | null;
  rating: number;
  reviews: number;
  isNew?: boolean;
  isSale?: boolean;
  isWishlisted?: boolean;
  description?: string; 
  reviewList?: Review[];
  starBreakdown?: any; 
}