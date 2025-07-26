import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Project } from '../models/Project';
import { UserService } from './user.service';
import { ResponseMessage } from '../utils/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private apiUrl = `${environment.apiUrl}/project`;

  constructor(private http: HttpClient, private userService: UserService) {}

  addProject(project: FormData): Observable<Object> {
    return this.http.post<Project>(`${this.apiUrl}/create`, project,{
      headers: this.userService.getAuthHeaders() 
    });
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/list`);
  }

  getProject(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/details/${id}`);
  }

  getMyProject(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/myproject/${id}`, {
      headers: this.userService.getAuthHeaders()
    });
  }

  updateProject(project:FormData, id: number): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(`${this.apiUrl}/update/${id}`, project,{
      headers: this.userService.getAuthHeaders()
    });
  }

  deleteProject(project : Project): Observable<ResponseMessage> {
    return this.http.delete<ResponseMessage>(`${this.apiUrl}/delete/${project.id}`,{
      headers: this.userService.getAuthHeaders()
    });
  }
  
  searchProjects(params: HttpParams): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/search`, { params });
  }
}
