import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { environment } from '../../../environments/environment';
import { UserService } from '../../services/user.service';
import { Project } from '../../models/Project';
import { ActivatedRoute, Router} from '@angular/router';

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

  url:string = environment.apiUrl;
  showFullDescription = false;

  id: number | null = null


  mode: 'details-profile' | 'profil' = 'profil';

   constructor(
    private userService:UserService,
    private route:ActivatedRoute,
    private router:Router
  ){

  }

  ngOnInit():void{
      this.route.paramMap.subscribe(params =>{
        const id = params.get("id");
        console.log(id)
        if(id){
          this.id = Number(id);
        }
      })
        if(this.id){

            this.loadSelectedProfile(this.id);
            this.loadSelectedProject(this.id);

          }else{

            this.loadProfile();
            this.loadMyProject();

          }
  }

  loadProfile():void{
    this.userService.getProfil().subscribe((data)=>{
      this.user = data
    })
  }

  editProfile(){
        return this.router.navigate(["/modifier-profil"])
  }

  loadMyProject():void{
    this.userService.getMyProjects().subscribe({
        next: (data) => {
              this.projects = data; 
            },
        error: (err) => {
              console.error('Erreur chargement projets :', err);
              this.projects = []; 
            }    
      })
  }

  loadSelectedProfile(id:number):void{
    this.userService.getUser(id).subscribe((data)=>{
      this.user = data
    })
  }

  loadSelectedProject(id:number):void{
    this.userService.getProjectsUser(id).subscribe({
      next: (data) => {
              this.projects = data; 
            },
      error: (err) => {
              console.error('Erreur chargement projets :', err);
              this.projects = []; 
            } 
    })
  }

  toggleDescription() {
    this.showFullDescription = !this.showFullDescription;
  }

  isLongDescription(): boolean {
    return !!this.user?.description && this.user.description.length > 200;
  }

  

}
