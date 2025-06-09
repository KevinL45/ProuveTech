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

  addProject(project: Project): Observable<any> {
    const formData = new FormData();

    formData.append('name', project.name);
    formData.append('description', project.description);
    formData.append('sourcecode', project.sourceCode);
    formData.append('video', project.video);
    formData.append('userId', project.user.id!.toString());

    project.tools.forEach(tool => {
      formData.append('tools', tool.id!.toString());
    });

    project.documents.forEach((doc: any) => {
      formData.append('documents', doc);
    });

    return this.http.post(`${this.apiUrl}/create`, formData, {
      headers: this.userService.getAuthHeaders() 
    });
  }

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/list`);
  }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/${id}`, {
      headers: this.userService.getAuthHeaders()
    });
  }

  deleteProject(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      headers: this.userService.getAuthHeaders()
    });
  }
}
