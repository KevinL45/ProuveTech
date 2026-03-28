import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { DemoService } from '../../services/demo.service';
import { Project } from '../../models/Project';

@Component({
  selector: 'app-demo-form',
  imports: [CommonModule, ReactiveFormsModule, NgSelectModule],
  templateUrl: './demo-form.component.html',
  styleUrl: './demo-form.component.scss'
})
export class DemoFormComponent implements OnInit {

  projects: Project[] = [];

  url: string = environment.apiUrl;

  demoForm: FormGroup;

  message: string = '';
  success: boolean = false;

  selectedVideo: File | null = null;
  selectedThumbnail: File | null = null;

  thumbnailUrl: string | null = null;
  thumbnailPreview: string | null = null;
  videoUrl: string | null = null;
  videoPreview: string | null = null;

  id: number | null = null;
  mode: 'update-demo' | 'create-demo' = 'create-demo';
  currentStep: number = 1;

  constructor(
    private fb: FormBuilder,
    private demoService: DemoService,
    private router: Router,
    private route: ActivatedRoute,
    private projectService: UserService,
  ) {
    this.demoForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(1000)]],
      projectId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.allMyProject();

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.id = Number(id);
      }
    });
    if (this.id) {
      this.mode = 'update-demo';
      this.loadDemoToEdit(this.id);
    } else {
      this.mode = 'create-demo';
    }
  }

  isStepValid(): boolean {
    if (this.currentStep === 1) {
      const name = this.demoForm.get('name');
      const description = this.demoForm.get('description');
      const projectId = this.demoForm.get('projectId');
      return (name?.valid ?? false) && (description?.valid ?? false) && (projectId?.valid ?? false);
    }
    if (this.currentStep === 2) {
      return !!(this.selectedThumbnail || this.thumbnailUrl);
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

  loadDemoToEdit(id: number): void {
    this.demoService.getMyDemo(id).subscribe((demo) => {
      this.demoForm.patchValue({
        name: demo.name,
        description: demo.description,
        projectId: demo.project?.id
      });
      this.thumbnailUrl = this.url + demo.link_thumbnail;
      this.videoUrl = this.url + demo.link_video;
    });
  }

  allMyProject(): void {
    this.projectService.getMyProjects().subscribe(
      (data) => { this.projects = data; },
      (error) => { console.error('Erreur sur les projets:', error); }
    );
  }

  onThumbnailChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (file && this.isValidImage(file)) {
      this.selectedThumbnail = file;
      const reader = new FileReader();
      reader.onload = (e) => {
        this.thumbnailPreview = e.target?.result as string;
      };
      reader.readAsDataURL(file);
    } else {
      this.message = 'Veuillez sélectionner une image valide (JPG, PNG, GIF, WebP).';
      this.success = false;
    }
  }

  onVideoChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (file && this.isValidVideo(file)) {
      this.selectedVideo = file;
      this.videoPreview = URL.createObjectURL(file);
    } else {
      this.message = 'Veuillez sélectionner une vidéo valide (MP4, WebM, Ogg).';
      this.success = false;
    }
  }

  private isValidImage(file: File): boolean {
    const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
    const maxSize = 5 * 1024 * 1024;
    return validTypes.includes(file.type) && file.size <= maxSize;
  }

  private isValidVideo(file: File): boolean {
    const validTypes = ['video/mp4', 'video/webm', 'video/ogg'];
    const maxSize = 500 * 1024 * 1024;
    return validTypes.includes(file.type) && file.size <= maxSize;
  }

  submit(): void {
    if (this.demoForm.invalid) {
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
    formData.append('name', this.demoForm.value.name);
    formData.append('description', this.demoForm.value.description);
    formData.append('project.id', this.demoForm.value.projectId);

    if (this.selectedVideo) {
      formData.append('video', this.selectedVideo);
    }
    if (this.selectedThumbnail) {
      formData.append('thumbnail', this.selectedThumbnail);
    }

    const request = this.mode === 'create-demo'
      ? this.demoService.addDemo(formData)
      : this.demoService.updateDemo(formData, this.id!);

    request.subscribe({
      next: (res) => {
        this.message = res.toString();
        this.success = true;
        this.router.navigate(['/details-projet', this.demoForm.value.projectId]);
      },
      error: (err) => {
        console.error('Erreur de la démo :', err);
        this.message = err.error?.message || 'Une erreur est survenue.';
        this.success = err.error?.success ?? false;
      }
    });
  }
}