import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToolService } from '../../services/tool.service';
import { Tool } from '../../models/Tool';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {
  url:string = environment.apiUrl;

  message: string = '';
  success: boolean = false;
  
  toolForm: FormGroup;

  selectedLogo: File | null = null;
  previewLogo: string | null = null;

  listTools: Tool[] = [];


  constructor(
    private toolService: ToolService, private fb:FormBuilder
  ) {

    this.toolForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  ngOnInit(): void {
    this.loadTools();
  }

  loadTools() {
    this.toolService.getAllTools().subscribe(data => this.listTools = data);
  }

  onLogoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedLogo = input.files[0];

      // Preview
      const reader = new FileReader();
      reader.onload = () => {
        this.previewLogo = reader.result as string;
      };
      reader.readAsDataURL(this.selectedLogo);
    }
  }

  submit(): void {

    if (this.toolForm.invalid) {
      this.message = 'Veuillez remplir correctement tous les champs requis.';
      this.success = false;
      return;
    }

    if (!this.selectedLogo) {
      this.message = 'Veuillez ajouter un logo.';
      this.success = false;
      return;
    }

    const formData = new FormData();
    formData.append('name', this.toolForm.value.name);
    console.log("Nom : "+this.toolForm.value.name)

    if (this.selectedLogo) {
      formData.append('fileLogo', this.selectedLogo);
      console.log("Logo : "+this.selectedLogo)
    }

    this.toolService.addTool(formData).subscribe({
      next: (res) => {
        this.message = res.message
        this.success = true;
        this.loadTools();
      },
      error: (err) => {
        console.error('Erreur de l\'outil :', err);
        this.message = err.error?.message || 'Une erreur est survenue.';
        this.success = err.error?.success ?? false;
      }
    });


  }


}
