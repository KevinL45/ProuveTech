// tool.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Tool } from '../models/Tool';

@Injectable({ providedIn: 'root' })
export class ToolService {
  private apiUrl = `${environment.apiUrl}/tool`;

  constructor(private http: HttpClient) {}

  getAllTools(): Observable<Tool[]> {
    return this.http.get<Tool[]>(`${this.apiUrl}/list`);
  }

  getToolsByIds(ids: number[]): Observable<Tool[]> {
    return this.http.post<Tool[]>(`${this.apiUrl}/by-ids`, ids);
  }
}