import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  imports: [RouterModule, CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {

  constructor(private userService:UserService){

  }

  isAuthenticated():boolean{
    return this.userService.isAuthenticated()
  }

  isAdmin():boolean{
    return this.userService.isAdmin()
  }
}
