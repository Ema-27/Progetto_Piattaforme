import { Category } from "../Category/category";
// import { ProductInPurchase } from "../ProductInPurchase/productInPurchase"; // Solo se serve

export interface Product {
    id: number;
    name: string;
    imageUrl: string;          // <- camelCase per coerenza col backend!
    price: number;
    description: string;
    categoryId: number;
    // productInPurchases?: ProductInPurchase[]; // Solo se necessario
}
