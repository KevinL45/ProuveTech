import { Component } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/Project';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-projects',
  imports: [CommonModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent {

  listProjects: Project[]=[];
    url:string = environment.apiUrl;

  constructor(private projectService:ProjectService, private router:Router){

  }

  ngOnInit():void{

    this.allProjet();

  }

  detailsProject(project :Project){
    return this.router.navigate(["/details-projet",project.id])

  }

  allProjet() : void{
      this.projectService.getAllProjects().subscribe(
        (data)=>{
          this.listProjects = data;
          },
        (error) => {
          console.error('Erreur sur les projets:', error);
        }
      )
    }

}
