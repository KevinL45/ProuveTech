import { Component } from '@angular/core';
import { StatusService } from '../../services/status.service';
import { User } from '../../models/User';
import { Status } from '../../models/Status';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-actor-it',
  imports: [CommonModule, FormsModule],
  templateUrl: './actor-it.component.html',
  styleUrl: './actor-it.component.scss'
})
export class ActorItComponent {

    url:string = environment.apiUrl
  

    listStatus: Status[] = [];

    filteredUsers: User[] = [];
    searchTitle: string = '';
    selectedStatusId: number | '' = '';
  

  constructor(private statusService: StatusService, private userService:UserService){

  }

      ngOnInit(): void {
      this.allStatus();
      this.allUsers();
    }

    allStatus() : void{
      this.statusService.getAllStatus().subscribe(
        (data)=>{
          this.listStatus = data;
          },
        (error) => {
          console.error('Erreur sur les status:', error);
        }
      )
    }

    allUsers() : void{
           this.userService.getAllUsers().subscribe(
        (data)=>{
          this.filteredUsers = data
          },
        (error) => {
          console.error('Erreur sur les utilisateurs:', error);
        }
      )
    }

    filterUsers(): void {
      let params = new HttpParams();
      if (this.searchTitle) {
        params = params.set('title', this.searchTitle);
      }
      if (this.selectedStatusId) {
        params = params.set('statusId', this.selectedStatusId);
      }

      this.userService.searchUsers(params).subscribe(
        (data) => this.filteredUsers = data,
        (err) => console.error("Erreur de recherche :", err)
      );

      }

}
