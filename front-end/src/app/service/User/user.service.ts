import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "src/app/model/User/user";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${this.apiServerUrl}/users`);
    }

    public addUser(user: User): Observable<User> {
        return this.http.post<User>(`${this.apiServerUrl}/users`, user);
    }

    public deleteUser(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/users/${id}`);
    }
}
