// tool.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Tool } from '../models/Tool';
import { UserService } from './user.service';
import { ResponseMessage } from '../utils/ResponseMessage';

@Injectable({ providedIn: 'root' })
export class ToolService {
  private apiUrl = `${environment.apiUrl}/tool`;

  constructor(private http: HttpClient, private userService:UserService) {}

  getAllTools(): Observable<Tool[]> {
    return this.http.get<Tool[]>(`${this.apiUrl}/list`, {
      headers: this.userService.getAuthHeaders()
    });
  }

  getToolsByIds(ids: number[]): Observable<Tool[]> {
    return this.http.post<Tool[]>(`${this.apiUrl}/by-ids`, ids, {
      headers: this.userService.getAuthHeaders()
    });
  }

  addTool(tool: FormData): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(`${this.apiUrl}/create`, tool,{
        headers: this.userService.getAuthHeaders() 
      });
  }
}