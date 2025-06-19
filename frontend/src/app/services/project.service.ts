import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Project } from '../models/Project';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private apiUrl = `${environment.apiUrl}/project`;

  constructor(private http: HttpClient, private userService: UserService) {}

  addProject(project: FormData): Observable<any> {
    console.log(this.userService.getAuthHeaders())
    return this.http.post<Project>(`${this.apiUrl}/create`, project,{
      headers: this.userService.getAuthHeaders() 
    });
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/list`);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/details/${id}`, {
      headers: this.userService.getAuthHeaders()
    });
  }

  deleteProject(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      headers: this.userService.getAuthHeaders()
    });
  }
}
