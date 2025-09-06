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
  listTools: Tool[] = [];


  constructor(
    private toolService: ToolService
  ){

  }

  ngOnInit(): void {
    this.loadTools();
  }

  loadTools() {
    this.toolService.getAllTools().subscribe(data => this.listTools = data);
  }




}
