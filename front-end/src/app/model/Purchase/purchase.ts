// src/app/model/Purchase/purchase.ts

import { User } from "../User/user";

export interface Purchase {
    id?: number;
    price: number;
    purchaseTime: string | Date;
    buyerId: number;
    buyer?: User;
    productIds?: number[];
    // AGGIUNGI:
    products?: { productId: number; name: string; price: number; imageUrl?: string }[];
}
