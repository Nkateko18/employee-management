import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Employees } from "../models/employee.model";

@Injectable({providedIn: 'root'})

export class EmployeeService {
    private apiUrl = '/api/employees';

    constructor(private http: HttpClient) {}

    getAllEmployees(): Observable<Employees[]> {
        return this.http.get<Employees[]>(this.apiUrl);
    }

    getEmployeeById(id: number): Observable<Employees> {
        return this.http.get<Employees>(`${this.apiUrl}/${id}`);
    }

    create(employee: Employees): Observable<Employees> {
        return this.http.post<Employees>(this.apiUrl, employee);
    }

    update(id: number, employee: Employees): Observable<Employees> {
        return this.http.put<Employees>(`${this.apiUrl}/${id}`, employee);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}