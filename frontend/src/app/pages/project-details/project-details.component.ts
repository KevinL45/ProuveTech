import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { DemoService } from '../../services/demo.service';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Project } from '../../models/Project';
import { Demo } from '../../models/Demo';
import { User } from '../../models/User';

@Component({
  selector: 'app-project-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-details.component.html',
  styleUrl: './project-details.component.scss'
})
export class ProjectDetailsComponent implements OnInit{

  id: number | null = null
  project: Project | null = null;
  demos: Demo[] = [];
  url:string = environment.apiUrl
  isProjectLoading: boolean = true;
  isDemosLoading: boolean = true;

    constructor(
    private route:ActivatedRoute, 
    private projectService:ProjectService,
    private demoService:DemoService,
    private router:Router,
  ){

  }


  ngOnInit():void{

    this.route.paramMap.subscribe(params =>{
      const id = params.get("id");
      if(id){
        this.id = Number(id);
        this.loadProject(this.id)
      }
    })

  }

  loadProject(id : number){
    this.isProjectLoading = true;
    this.projectService.getProject(id).subscribe((data)=>{
      this.project = data;
      this.isProjectLoading = false;
      this.loadDemos(id);
    })
  }

  loadDemos(projectId: number): void {
    this.isDemosLoading = true;
    this.demoService.getAllDemos().subscribe(
      (demos) => {
        this.demos = demos.filter(demo => demo.project?.id === projectId);
        this.isDemosLoading = false;
      },
      (error) => {
        console.error('Erreur lors du chargement des démos:', error);
        this.isDemosLoading = false;
      }
    );
  }

   detailsUser(user: User  | undefined ) {
    return this.router.navigate(["/details-profil",user?.id])
  }

  detailsDemo(demo: Demo  | undefined ) {
    return this.router.navigate(["/details-demo",demo?.id])
  }

  truncate(title: string | undefined, maxLength: number): string {
    if (!title) return 'Aucun titre';
    return title.length > maxLength ? title.substring(0, maxLength) + '...' : title;
  }

  copyToClipboard(text: string) {
    navigator.clipboard.writeText(text);
  }

}
