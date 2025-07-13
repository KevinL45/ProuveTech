import { Component } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/Project';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';
import { Tool } from '../../models/Tool';
import { ProjectFormComponent } from '../../form/project-form/project-form.component';
import { ToolService } from '../../services/tool.service';
import { HttpParams } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-projects',
  imports: [CommonModule, FormsModule, NgSelectModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent {

  listProjects: Project[]=[];
  url:string = environment.apiUrl;

  listTools: Tool[] = [];
  searchTitle: string ="";
  selectedToolIds: number[] = [];
  filteredProjects: Project[] = [];
  

  constructor(private projectService:ProjectService, private router:Router, 
    private toolService:ToolService
  ){

  }

  ngOnInit():void{

    this.allProjet();
    this.loadTools();

  }

    loadTools(): void {
    this.toolService.getAllTools().subscribe({
      next: (tools) => this.listTools = tools,
      error: (err) => console.error('Erreur chargement outils :', err)
    });
  }

  detailsProject(project :Project){
    return this.router.navigate(["/details-projet",project.id])

  }

  allProjet() : void{
      this.projectService.getAllProjects().subscribe(
        (data)=>{
          this.filteredProjects = data;
          console.log(this.filteredProjects)
          },
        (error) => {
          console.error('Erreur sur les projets:', error);
        }
      )
    }

    filterProjects(): void {
      let params = new HttpParams();
      if (this.searchTitle) {
        params = params.set('name', this.searchTitle);
      }
      if (this.selectedToolIds) {
        params = params.set('statusId', this.selectedToolIds.toString());
      }

      this.projectService.searchProjects(params).subscribe(
        (data) => this.filteredProjects = data,
        (err) => console.error("Erreur de recherche :", err)
      );

      }
  

}
