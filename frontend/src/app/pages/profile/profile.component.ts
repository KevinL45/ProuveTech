import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { environment } from '../../../environments/environment';
import { UserService } from '../../services/user.service';
import { Project } from '../../models/Project';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{

  user: User | null = null;
  projects: Project[]=[];

  url:string = environment.apiUrl

   constructor(
    private userService:UserService,
  ){

  }

  ngOnInit():void{

    this.loadProfile();
    this.loadMyProject();

  }

  loadProfile():void{
    this.userService.getProfil().subscribe((data)=>{
      this.user = data
    })
  }

  loadMyProject():void{
    this.userService.getMyProjects().subscribe((data)=>{
      this.projects = data
    })
  }

  

}
