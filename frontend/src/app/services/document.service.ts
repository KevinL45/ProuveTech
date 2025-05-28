import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Document } from '../models/Document';
import { UserService } from './user.service';

@Injectable({ providedIn: 'root' })
export class DocumentService {
  private apiUrl = `${environment.apiUrl}/document`;

  constructor(private http: HttpClient, private userService: UserService) {}

  uploadDocument(document: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', document);

    return this.http.post(`${this.apiUrl}/upload`, formData, {
      headers: this.userService.getAuthHeaders()
    });
  }
}