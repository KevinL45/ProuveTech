import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { SocialNetworks } from '../models/SocialNetworks';

@Injectable({ providedIn: 'root' })
export class SocialNetworkService {
  private apiUrl = `${environment.apiUrl}/social-network`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<SocialNetworks[]> {
    return this.http.get<SocialNetworks[]>(`${this.apiUrl}/all`);
  }
}