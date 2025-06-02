import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Status } from '../models/Status';

@Injectable({ providedIn: 'root' })
export class StatusService {
  private apiUrl = `${environment.apiUrl}/status`;

  constructor(private http: HttpClient) {}

  getAllStatus(): Observable<Status[]> {
    return this.http.get<Status[]>(`${this.apiUrl}/list`);
  }

  getStatus(id:number): Observable<Status> {
    return this.http.get<Status>(`${this.apiUrl}/details/${id}`);
  }
}
