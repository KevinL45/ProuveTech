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
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-projects',
  imports: [CommonModule, FormsModule, NgSelectModule],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent {

  mode: 'mes-demos' | 'projets' = 'projets';


  listProjects: Project[]=[];
  url:string = environment.apiUrl;

  listTools: Tool[] = [];
  searchTitle: string ="";
  selectedToolIds: number[] = [];
  filteredProjects: Project[] = [];

  currentPage: number = 1;
  pageSize: number = 8; 
  paginatedProject: Project[] = [];
  

  constructor(
    private projectService:ProjectService, 
    private router:Router, 
    private toolService:ToolService,
    private userService:UserService)
  {

  }

  ngOnInit():void{


    this.allProjet();

  }

      loadMyProject():void{
        this.userService.getMyProjects().subscribe({
            next: (data) => {
                  this.filteredProjects = data; 
                },
            error: (err) => {
                  console.error('Erreur chargement projets :', err);
                  this.filteredProjects = []; 
                }    
          })
      }

      detailsProject(project :Project){
        return this.router.navigate(["/details-projet",project.id])

      }

      allProjet() : void{
          this.projectService.getAllProjects().subscribe(
            (data)=>{
              this.filteredProjects = data;
              this.updatePaginatedProject();
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
          (data) => {
            this.filteredProjects = data;
            this.currentPage = 1; // Reset à la première page
            this.updatePaginatedProject();
          },
          (err) => console.error("Erreur de recherche :", err)
        );

      }

      updatePaginatedProject(): void {
          const start = (this.currentPage - 1) * this.pageSize;
          const end = start + this.pageSize;
          this.paginatedProject = this.filteredProjects.slice(start, end);
      }
    
      totalPages(): number {
          return Math.ceil(this.filteredProjects.length / this.pageSize);
      }
    
      nextPage() {
        if (this.currentPage < this.totalPages()) {
            this.currentPage++;
            this.updatePaginatedProject();
            }
      }
    
      prevPage() {
        if (this.currentPage > 1) {
            this.currentPage--;
            this.updatePaginatedProject();
            }
      }
    
      truncate(title: string | undefined, maxLength: number): string {
          if (!title) return 'Aucun titre';
          return title.length > maxLength ? title.substring(0, maxLength) + '...' : title;
      }

      editProject(project : Project){
        return this.router.navigate(["/modifier-projet",project.id])
       }

      deleteProject(project : Project){
        if (confirm('Êtes-vous sûr de vouloir supprimer la vidéo du projet ?')) {
          this.projectService.deleteProject(project).subscribe(()=>{
          this.filteredProjects = this.filteredProjects.filter(p => p.id !== project.id)
       });
      }
  }
  

}
