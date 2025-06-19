import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';
import { environment } from '../../environments/environment';
import { Project } from '../models/Project';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient) {}

  login(user: User): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, user);
  }

  register(user: User): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  saveToken(token: string): void {
    localStorage.setItem('authToken', token);
    console.log(this.getToken())
  }

  getToken(): string | null {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('authToken');
  }
  return null;
  }

  logout(): void {
    localStorage.removeItem('authToken');
  }

  isAuthenticated(): boolean {
  return this.getToken() !== null;
  }

  getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/list`);
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/details/${id}`);
  }

  getProfil(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/profile`, {
      headers: this.getAuthHeaders()
    });
  }

  getMyProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/myprojects`, {
      headers: this.getAuthHeaders()
    });
  }
}
