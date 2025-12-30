import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Demo } from '../models/Demo';
import { environment } from '../../environments/environment';
import { ResponseMessage } from '../utils/ResponseMessage';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class DemoService {

    private apiUrl = `${environment.apiUrl}/demo`;

  constructor(private http: HttpClient, private userService: UserService) {}

  addDemo(demo: FormData): Observable<Object> {
    return this.http.post<Demo>(`${this.apiUrl}/create`, demo,{
      headers: this.userService.getAuthHeaders() 
    });
  }

  getAllDemos(): Observable<Demo[]> {
    return this.http.get<Demo[]>(`${this.apiUrl}/list`,{
        headers: this.userService.getAuthHeaders()
      });
  }

  getDemo(id: number): Observable<Demo> {
    return this.http.get<Demo>(`${this.apiUrl}/details/${id}`, {
        headers: this.userService.getAuthHeaders()
      });
  }

  getMyDemo(id: number): Observable<Demo> {
      return this.http.get<Demo>(`${this.apiUrl}/mydemo/${id}`, {
        headers: this.userService.getAuthHeaders()
      });
    }

  updateDemo(demo:FormData, id: number): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(`${this.apiUrl}/update/${id}`, demo,{
      headers: this.userService.getAuthHeaders()
    });
  }

  deleteDemo(demo : Demo): Observable<ResponseMessage> {
    return this.http.delete<ResponseMessage>(`${this.apiUrl}/delete/${demo.id}`,{
      headers: this.userService.getAuthHeaders()
    });
  }
  
  searchDemos(params: HttpParams): Observable<Demo[]> {
    return this.http.get<Demo[]>(`${this.apiUrl}/search`, { params, headers: this.userService.getAuthHeaders() });
  }
}
