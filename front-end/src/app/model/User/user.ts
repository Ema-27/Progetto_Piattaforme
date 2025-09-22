import { Purchase } from "../Purchase/purchase";

export interface User {
    id?: number;                       // opzionale se usato in POST
    name: string;
    surname: string;
    telephoneNumber: string;
    email: string;
    address: string;
    purchases?: Purchase[];            // opzionale, solo quando vuoi vedere la "storia acquisti"
}
