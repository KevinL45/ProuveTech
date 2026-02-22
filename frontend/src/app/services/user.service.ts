import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';
import { environment } from '../../environments/environment';
import { Project } from '../models/Project';
import { ResponseMessage } from '../utils/ResponseMessage';
import { Demo } from '../models/Demo';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient) {}

  login(user: User): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(`${this.apiUrl}/login`, user);
  }

  register(user: User): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(`${this.apiUrl}/register`, user, {
      headers: this.getAuthHeaders()
    });
  }

  updateUser(formData: FormData): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(`${this.apiUrl}/update`, formData,{
      headers : this.getAuthHeaders()
    });
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined';
  }

  saveToken(token: string): void {
    if (this.isBrowser()) localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return this.isBrowser() ? localStorage.getItem('authToken') : null;
  }

  saveRoles(roles: string[]): void {
    if (this.isBrowser()) localStorage.setItem('userRoles', JSON.stringify(roles));
  }

  getRoles(): string[] {
    if (!this.isBrowser()) return [];
    const roles = localStorage.getItem('userRoles');
    return roles ? JSON.parse(roles) : [];
  }

  isAdmin(): boolean {
    return this.getRoles().includes('ROLE_ADMIN');
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem('authToken');
      localStorage.removeItem('userRoles');
    }
  }

isAuthenticated(): boolean {
  const token = this.getToken();
  if (!token) return false;

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const expiry = payload.exp;
    const now = Math.floor(Date.now() / 1000);

    if (expiry > now) {
      return true;
    } else {
      this.logout();
      return false;
    }
  } catch (e) {
    this.logout();
    return false;
  }
}

  getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/list`, {
      headers: this.getAuthHeaders()
    });
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/details/${id}`, {
      headers: this.getAuthHeaders()
    });
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

  getMyDemos(): Observable<Demo[]> {
    return this.http.get<Demo[]>(`${this.apiUrl}/mydemos`, {
      headers: this.getAuthHeaders()
    });
  }

  getProjectsUser(id:number): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/projects/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  searchUsers(params: HttpParams): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/search`, { params, headers: this.getAuthHeaders() });
  }
}
