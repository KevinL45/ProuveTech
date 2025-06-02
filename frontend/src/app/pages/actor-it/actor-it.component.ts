import { Component } from '@angular/core';
import { StatusService } from '../../services/status.service';
import { User } from '../../models/User';
import { Status } from '../../models/Status';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-actor-it',
  imports: [CommonModule],
  templateUrl: './actor-it.component.html',
  styleUrl: './actor-it.component.scss'
})
export class ActorItComponent {

    listStatus: Status[] = [];
    listUsers: User[] = [];
  

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
          this.listUsers = data;
          },
        (error) => {
          console.error('Erreur sur les utilisateurs:', error);
        }
      )
    

    }

}
