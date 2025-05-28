import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Comment } from '../models/Comment';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiUrl = `${environment.apiUrl}/comment`;


  constructor(private http: HttpClient, private userService:UserService) {}

  
  // Ajouter un commentaire
  addComment(comment: Comment): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, comment, {
      headers: this.userService.getAuthHeaders()
    });
  }

  // Récupérer les commentaires d’un projet
  getCommentsByProject(projectId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/project/${projectId}`, {
      headers:this.userService.getAuthHeaders()
    });
  }

  // Supprimer un commentaire (optionnel)
  deleteComment(commentId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${commentId}`, {
      headers: this.userService.getAuthHeaders()
    });
  }
}
