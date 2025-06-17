import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';


import { Tool } from '../../models/Tool';
import { ProjectService } from '../../services/project.service';
import { ToolService } from '../../services/tool.service';
import { Project } from '../../models/Project';

@Component({
  selector: 'app-project-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgSelectModule],
  templateUrl: './project-form.component.html',
  styleUrl: './project-form.component.scss'
})
export class ProjectFormComponent implements OnInit {

  projectForm: FormGroup;
  listTools: Tool[] = [];

  message: string = '';
  success: boolean = false;

  selectedVideo: File | null = null;
  selectedDocuments: File[] = [];

  constructor(
    private fb: FormBuilder,
    private toolService: ToolService,
    private projectService: ProjectService,
    private router: Router
  ) {
    this.projectForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required]],
      sourcecode: ['', [Validators.required]],
      tools: [[]] // tableau d'IDs
    });
  }

  ngOnInit(): void {
    this.loadTools();
  }

  loadTools(): void {
    this.toolService.getAllTools().subscribe({
      next: (tools) => this.listTools = tools,
      error: (err) => console.error('Erreur chargement outils :', err)
    });
  }

  onVideoChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedVideo = file;
    }
  }

  onDocumentsChange(event: any): void {
    this.selectedDocuments = Array.from(event.target.files);
  }

  submit(): void {
    if (this.projectForm.invalid) {
      this.message = 'Veuillez remplir correctement tous les champs requis.';
      this.success = false;
      return;
    }

    if (!this.selectedVideo) {
      this.message = 'Veuillez ajouter une vidéo.';
      this.success = false;
      return;
    }

    const formData = new FormData();
    formData.append('name', this.projectForm.value.name);
    formData.append('description', this.projectForm.value.description);
    formData.append('sourcecode', this.projectForm.value.sourcecode || '');

    this.projectForm.value.tools.forEach((toolId: string) => {
      formData.append('tools', toolId);
    });

    if (this.selectedVideo) {
      formData.append('video', this.selectedVideo);
    }

    this.selectedDocuments.forEach((file: File) => {
      formData.append('documents', file);
    });

    for (const [key, value] of formData.entries()) {
      console.log(`${key}:`, value);
    }

    const projectValues = this.projectForm.value;

    // const project = new Project
    // (projectValues.name,
    //   projectValues.description,
    //   projectValues.sourcecode,
    //   this.selectedVideo,
    //   projectValues.tools,
    //   this.selectedDocuments!
    // );

    this.projectService.addProject(projectValues).subscribe({
      next: (res) => {
        this.message = 'Projet créé avec succès.';
        this.success = true;
        this.router.navigate(['/projets']);
      },
      error: (err) => {
        console.error('Erreur création projet :', err);
        this.message = err.error?.message || 'Une erreur est survenue.';
        this.success = false;
      }
    });
  }
}
