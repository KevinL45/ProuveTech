import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(private userService:UserService, private router:Router){

  }

  logout(){
    this.userService.logout();
    this.router.navigate(["/accueil"])
  }

  isAuthenticated():boolean{
    return this.userService.isAuthenticated()
  }

}
