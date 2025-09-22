import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Purchase } from "src/app/model/Purchase/purchase";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class PurchaseService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getPurchases(): Observable<Purchase[]> {
        return this.http.get<Purchase[]>(`${this.apiServerUrl}/purchases`);
    }

    public getPurchasesByUserId(userId: number): Observable<Purchase[]> {
        return this.http.get<Purchase[]>(`${this.apiServerUrl}/purchases/user/${userId}`);
    }

    public addPurchase(purchase: Purchase): Observable<any> {
        // Il backend ora restituisce l'oggetto o la lista aggiornata, NON più una stringa!
        return this.http.post(`${this.apiServerUrl}/purchases`, purchase, { responseType: 'text' });
    }
}