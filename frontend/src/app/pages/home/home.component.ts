import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ActorItComponent } from "../actor-it/actor-it.component";
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [RouterModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private userService: UserService){

  }

  isAuthenticated():boolean{
    return this.userService.isAuthenticated()
  }

}
