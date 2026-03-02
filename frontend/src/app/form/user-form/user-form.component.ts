import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User } from '../../models/User';
import { Status } from '../../models/Status';
import { StatusService } from '../../services/status.service';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

@Component({
  selector: 'app-user-form',
    standalone: true,

  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent {


mode: 'register' | 'login' | 'update' = 'register';

  url: string = environment.apiUrl;

  userForm: FormGroup;
  loginForm: FormGroup;
  updateForm: FormGroup;
  listStatus: Status[] = [];

  user: User | null = null;
  selectedPicture: File | null = null;
  pictureUrl: string | null = null;


  message: string = '';
  success: boolean = false;

  constructor(private userService:UserService, private statusService:StatusService, 
    private router:Router, private fb: FormBuilder){

    this.userForm =  this.fb.group({
      firstname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      lastname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      mail: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255), Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      confirmPassword:  ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
      statusId: [null, [Validators.required]],
    }, { validators: this.passwordsMatch });

    this.loginForm = fb.group({
      mail: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255), Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255)]]
    });

    this.updateForm = this.fb.group({
      firstname: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      lastname: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      statusId: ["", [Validators.required]],
      title: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(5000)]],
    });

 

    } 

    ngOnInit(): void {
      // if(!this.userService.isAdmin()){
      //   this.router.navigate(['/']);
      // }

      
      this.allStatus();

      if(this.userService.isAuthenticated()){
        this.loadProfileToEdit();
      }

      this.mode =
        this.router.url.includes('connexion') ? 'login' :
        this.router.url.includes('modifier-profil') ? 'update' :
        'register';
      }

    loadProfileToEdit():void{
      this.userService.getProfil().subscribe((user)=>{
          this.updateForm.patchValue({
              firstname: user.firstname,
              lastname: user.lastname,
              title: user.title,
              description: user.description,
              mail: user.mail,
              statusId: user.status?.id,
          });
        this.pictureUrl = this.url + user.linkPhoto;
      })
    }

    allStatus() : void{
      this.statusService.getAllStatus().subscribe(
        (data)=>{
          this.listStatus = data;
          },
        (error) => {
          console.error('Erreur sur les statuts:', error);
        }
      )
    }
    
    passwordsMatch(group: FormGroup) {
      const password = group.get('password')?.value;
      const confirmPassword = group.get('confirmPassword')?.value;
      return password === confirmPassword ? null : { notMatching: true };
    }
    
    register(){

      if(this.userForm.valid){

        const user = new User(this.userForm.value.mail, 
          this.userForm.value.password, 
          this.userForm.value.firstname, 
          this.userForm.value.lastname, 
          this.userForm.value.statusId);

        this.userService.register(user).subscribe({
              next: (res) => {
                this.message = res.message;
                this.success = res.success;

                  this.router.navigate(['/connexion']);
              },
              error: (err) => {
                  console.error('Erreur serveur:', err);
                  this.message = err.error?.message || 'Une erreur est survenue.';
                  this.success = err.error?.success ?? false;
              }
         });
        } else {
            this.message = 'Formulaire invalide.';
            this.success = false;
                }
    }

    auth(){

        if(this.loginForm.valid){

        const user = new User(this.loginForm.value.mail, 
        this.loginForm.value.password);
        this.userService.login(user).subscribe({
              next: (res) => {
                  this.userService.saveToken(res.message);
                  this.userService.saveRoles(res.roles ?? []);
                  this.router.navigate(['/accueil']);
              },
              error: (err) => {
                  console.error('Erreur serveur:', err);
                  this.message = err.error?.message || 'Une erreur est survenue.';
                  this.success = err.error?.success ?? false;
              }
         });
        } else {
            this.message = 'Formulaire invalide.';
            this.success = false;
                }

    }

    onPictureSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedPicture = input.files[0];
    }
  }

  updateUser(){


    if (!this.selectedPicture && !this.pictureUrl) {
      this.message = 'Veuillez ajouter une photo de profil';
      this.success = false;
      return;
    }

    const formData = new FormData();

    if (this.selectedPicture) {
      formData.append('photo', this.selectedPicture);
    }

    formData.append('firstname', this.updateForm.value.firstname);
    formData.append('lastname', this.updateForm.value.lastname);
    formData.append('title', this.updateForm.value.title);
    formData.append('description', this.updateForm.value.description);
    formData.append('statusId', this.updateForm.value.statusId);

    this.userService.updateUser(formData).subscribe({
              next: (res) => {
                this.message = res.message;
                this.success = res.success;
                this.router.navigate(['/profil']);
              },
              error: (err) => {
                  console.error('Erreur serveur:', err);
                  this.message = err.error?.message || 'Une erreur est survenue.';
                  this.success = err.error?.success ?? false;
              }
         });


      
    }
}
