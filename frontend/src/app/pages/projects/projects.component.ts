import { Component } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/Project';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-projects',
  imports: [CommonModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent {

  listProjects: Project[]=[];
    url:string = environment.apiUrl;

  constructor(private projectService:ProjectService){

  }

  ngOnInit():void{

    this.allProjet();

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
