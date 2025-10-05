import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../services/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Project } from '../../models/Project';
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
  url:string = environment.apiUrl
  isVideoLoading: boolean = true;
  isProjectLoading: boolean = true;

    constructor(
    private route:ActivatedRoute, 
    private projectService:ProjectService,
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
      this.isProjectLoading = false
    })
  }

   detailsUser(user: User  | undefined ) {
    return this.router.navigate(["/details-profil",user?.id])
  }

  truncate(title: string | undefined, maxLength: number): string {
    if (!title) return 'Aucun titre';
    return title.length > maxLength ? title.substring(0, maxLength) + '...' : title;
  }

  onVideoLoaded() {
    this.isVideoLoading = false;
  }

  copyToClipboard(text: string) {
    navigator.clipboard.writeText(text);
  }

}
