import { Component } from '@angular/core';
import { StatusService } from '../../services/status.service';
import { User } from '../../models/User';
import { Status } from '../../models/Status';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

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

    currentPage: number = 1;
    pageSize: number = 8; 
    paginatedUsers: User[] = [];
  

    constructor(
      private statusService: StatusService, 
      private userService:UserService,
      private router:Router
    ){
    }

    ngOnInit(): void {
      if (!this.userService.isAuthenticated()) {
        this.router.navigate(['/']);
        return;
      }
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

    detailsUser(user :User){
        return this.router.navigate(["/details-profil",user.id])
    
      }

    allUsers() : void{
           this.userService.getAllUsers().subscribe(
        (data)=>{
          this.filteredUsers = data
          this.updatePaginatedUsers();
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
        (data) => {
          this.filteredUsers = data;
          this.currentPage = 1; // Reset à la première page
          this.updatePaginatedUsers();
        },
        (err) => console.error("Erreur de recherche :", err)
      );
    }


      updatePaginatedUsers(): void {
              const start = (this.currentPage - 1) * this.pageSize;
              const end = start + this.pageSize;
              this.paginatedUsers = this.filteredUsers.slice(start, end);
      }

      totalPages(): number {
        return Math.ceil(this.filteredUsers.length / this.pageSize);
      }

      nextPage() {
        if (this.currentPage < this.totalPages()) {
          this.currentPage++;
          this.updatePaginatedUsers();
        }
      }

      prevPage() {
        if (this.currentPage > 1) {
          this.currentPage--;
          this.updatePaginatedUsers();
        }
      }

      truncate(user: User, maxLength: number): string {
        if (!user.title) return 'Aucun titre';
        return user.title.length > maxLength ? user.title.substring(0, maxLength) + '...' : user.title;
      }
}
