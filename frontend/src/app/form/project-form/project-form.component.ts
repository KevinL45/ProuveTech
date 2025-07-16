import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';


import { Tool } from '../../models/Tool';
import { ProjectService } from '../../services/project.service';
import { ToolService } from '../../services/tool.service';
import { Project } from '../../models/Project';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-project-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgSelectModule],
  templateUrl: './project-form.component.html',
  styleUrl: './project-form.component.scss'
})
export class ProjectFormComponent implements OnInit {

  url:string = environment.apiUrl;

  projectForm: FormGroup;
  listTools: Tool[] = [];

  message: string = '';
  success: boolean = false;

  selectedVideo: File | null = null;
  selectedThumbnail: File | null = null;
  
  thumbnailUrl: string | null = null;
  videoUrl: string | null = null;

  id: number | null = null

  mode: 'update-project' | 'create-project' = 'create-project';

  constructor(
    private fb: FormBuilder,
    private toolService: ToolService,
    private projectService: ProjectService,
    private router: Router,
    private route:ActivatedRoute
  ) {
    this.projectForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required]],
      sourcecode: [''],
      tools: [[]] // tableau d'IDs
    });
  }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params =>{
        const id = params.get("id");
        if(id){
          this.id = Number(id);
        }
      })

    if(this.id){
      this.mode = 'update-project';
      this.loadProjectToEdit(this.id)
    }else{
      this.mode = 'create-project'; 
    }
    this.loadTools();
  }

  loadTools(): void {
    this.toolService.getAllTools().subscribe({
      next: (tools) => this.listTools = tools,
      error: (err) => console.error('Erreur chargement outils :', err)
    });
  }

  loadProjectToEdit(id:number):void{
    this.projectService.getMyProject(id).subscribe((project)=>{
      this.projectForm.patchValue({
        name:project.name,
        description: project.description,
        sourcecode: project.sourcecode,
        tools: project.tools?.map(t => t.id) || []
      });
        this.thumbnailUrl = this.url+project.link_thumbnail;
        this.videoUrl = this.url+project.link_video;
    })
  }

  onVideoChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedVideo = file;
    }
  }

  onThumbnailChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedThumbnail = file;
    }
  }

  submit(): void {
    if (this.projectForm.invalid) {
      this.message = 'Veuillez remplir correctement tous les champs requis.';
      this.success = false;
      return;
    }

      if (!this.selectedVideo && !this.videoUrl) {
      this.message = 'Veuillez ajouter une vidéo.';
      this.success = false;
      return;
    }

    if (!this.selectedThumbnail && !this.thumbnailUrl) {
      this.message = 'Veuillez ajouter une miniature.';
      this.success = false;
      return;
    }

    const formData = new FormData();
    formData.append('name', this.projectForm.value.name);
    formData.append('description', this.projectForm.value.description);
    formData.append('sourcecode', this.projectForm.value.sourcecode);
    if(this.selectedVideo){
      formData.append('video', this.selectedVideo!);
    }
    if(this.selectedVideo){
      formData.append('thumbnail', this.selectedThumbnail!);
    }


    this.projectForm.value.tools.forEach((toolId: string) => {
      formData.append('toolsIds', toolId);
    });

    const https = this.mode === 'create-project'
                ? this.projectService.addProject(formData)
                : this.projectService.updateProject(formData, this.id!);

    https.subscribe({
      next: (res) => {
        this.message = res.message
        this.success = true;
        this.router.navigate(['/profil']);
      },
      error: (err) => {
        console.error('Erreur du projet :', err);
        this.message = err.error?.message || 'Une erreur est survenue.';
        this.success = err.error?.success ?? false;
      }
    });

    


  }
}
