import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { UserSocialNetworks } from '../models/UserSocialNetworks';
import { UserService } from './user.service';

@Injectable({ providedIn: 'root' })
export class UserSocialNetworkService {
  private apiUrl = `${environment.apiUrl}/user-social-network`;

  constructor(private http: HttpClient, private userService: UserService) {}

  getByUserId(userId: number): Observable<UserSocialNetworks[]> {
    return this.http.get<UserSocialNetworks[]>(`${this.apiUrl}/user/${userId}`, {
      headers: this.userService.getAuthHeaders()
    });
  }

  addUserSocialNetwork(data: UserSocialNetworks): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, data, {
      headers: this.userService.getAuthHeaders()
    });
  }
}