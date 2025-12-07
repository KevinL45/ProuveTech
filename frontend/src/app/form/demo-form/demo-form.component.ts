import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { DemoService } from '../../services/demo.service';

@Component({
  selector: 'app-demo-form',
  imports: [CommonModule, ReactiveFormsModule, NgSelectModule],
  templateUrl: './demo-form.component.html',
  styleUrl: './demo-form.component.scss'
})
export class DemoFormComponent implements OnInit {

  url: string = environment.apiUrl;

  demoForm: FormGroup;

  message: string = '';
  success: boolean = false;

  selectedVideo: File | null = null;
  selectedThumbnail: File | null = null;

  thumbnailUrl: string | null = null;
  videoUrl: string | null = null;

  id: number | null = null

  mode: 'update-demo' | 'create-demo' = 'create-demo';

    constructor(
    private fb: FormBuilder,
    private demoService: DemoService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService
  ) {
    this.demoForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(1000)]],
      sourcecode: ['', Validators.maxLength(255)],
    });

    
  }

    ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      const id = params.get("id");
      if (id) {
        this.id = Number(id);
      }
    })
      if (this.id) {
        this.mode = 'update-demo';
        this.loadDemoToEdit(this.id)
      } else {
        this.mode = 'create-demo';
      }
  }

    loadDemoToEdit(id: number): void {
    this.demoService.getMyDemo(id).subscribe((demo) => {
      this.demoForm.patchValue({
        name: demo.name,
        description: demo.description,
      });
      this.thumbnailUrl = this.url + demo.link_thumbnail;
    })
  }

  onThumbnailChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedThumbnail = file;
    }
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

    if (this.selectedVideo) {
      formData.append('video', this.selectedVideo!);
    }
    if (this.selectedThumbnail) {
      formData.append('thumbnail', this.selectedThumbnail!);
    }

    const https = this.mode === 'create-demo'
      ? this.demoService.addDemo(formData)
      : this.demoService.updateDemo(formData, this.id!);

    https.subscribe({
      next: (res) => {
        this.message = res.toString()
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
