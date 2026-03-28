import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';

import { Tool } from '../../models/Tool';
import { ProjectService } from '../../services/project.service';
import { ToolService } from '../../services/tool.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-project-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgSelectModule],
  templateUrl: './project-form.component.html',
  styleUrl: './project-form.component.scss'
})
export class ProjectFormComponent implements OnInit {

  url: string = environment.apiUrl;

  projectForm: FormGroup;
  listTools: Tool[] = [];

  message: string = '';
  success: boolean = false;

  selectedThumbnail: File | null = null;
  thumbnailUrl: string | null = null;
  thumbnailPreview: string | null = null;

  id: number | null = null;
  mode: 'update-project' | 'create-project' = 'create-project';
  currentStep: number = 1;

  constructor(
    private fb: FormBuilder,
    private toolService: ToolService,
    private projectService: ProjectService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.projectForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(1000)]],
      sourcecode: ['', Validators.maxLength(255)],
      tools: [[], [this.maxToolsValidator(3)]]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.id = Number(id);
      }
    });
    if (this.id) {
      this.mode = 'update-project';
      this.loadProjectToEdit(this.id);
    } else {
      this.mode = 'create-project';
    }
    this.loadTools();
  }

  isStepValid(): boolean {
    if (this.currentStep === 1) {
      const name = this.projectForm.get('name');
      const description = this.projectForm.get('description');
      return (name?.valid ?? false) && (description?.valid ?? false);
    }
    if (this.currentStep === 2) {
      const tools = this.projectForm.get('tools');
      const sourcecode = this.projectForm.get('sourcecode');
      return (tools?.valid ?? false) && (sourcecode?.valid ?? false);
    }
    return true;
  }

  nextStep(): void {
    if (this.isStepValid() && this.currentStep < 3) {
      this.currentStep++;
    }
  }

  prevStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  maxToolsValidator(max: number) {
    return (control: any) => {
      if (control.value && control.value.length > max) {
        return { maxTools: true };
      }
      return null;
    };
  }

  loadTools(): void {
    this.toolService.getAllTools().subscribe({
      next: (tools) => this.listTools = tools,
      error: (err) => console.error('Erreur chargement outils :', err)
    });
  }

  loadProjectToEdit(id: number): void {
    this.projectService.getMyProject(id).subscribe((project) => {
      this.projectForm.patchValue({
        name: project.name,
        description: project.description,
        sourcecode: project.sourcecode,
        tools: project.tools?.map((t: any) => t.id) || []
      });
      this.thumbnailUrl = this.url + project.link_thumbnail;
    });
  }

  onThumbnailChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedThumbnail = file;
      const reader = new FileReader();
      reader.onload = (e) => {
        this.thumbnailPreview = e.target?.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  submit(): void {
    if (this.projectForm.invalid) {
      this.message = 'Veuillez remplir correctement tous les champs requis.';
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

    if (this.selectedThumbnail) {
      formData.append('thumbnail', this.selectedThumbnail);
    }

    this.projectForm.value.tools.forEach((toolId: string) => {
      formData.append('toolsIds', toolId);
    });

    const request = this.mode === 'create-project'
      ? this.projectService.addProject(formData)
      : this.projectService.updateProject(formData, this.id!);

    request.subscribe({
      next: (res) => {
        this.message = res.toString();
        this.success = true;
        this.router.navigate(['/creer-demo']);
      },
      error: (err) => {
        console.error('Erreur du projet :', err);
        this.message = err.error?.message || 'Une erreur est survenue.';
        this.success = err.error?.success ?? false;
      }
    });
  }
}