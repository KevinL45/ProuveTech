import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User } from '../../models/User';
import { Status } from '../../models/Status';
import { StatusService } from '../../services/status.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-form',
    standalone: true,

  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent {


  mode: 'register' | 'login' = 'register';

  userForm: FormGroup;
  loginForm: FormGroup;

  listStatus: Status[] = [];

  message: string = '';
  success: boolean = false;

  constructor(private userService:UserService, private statusService:StatusService, private router:Router, private fb: FormBuilder){

    this.userForm =  this.fb.group({
      firstname: ['', [Validators.required, Validators.minLength(3)]],
      lastname: ['', [Validators.required, Validators.minLength(3)]],
      mail: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      statusId: [null, [Validators.required]],
    }, { validators: this.passwordsMatch });

    this.loginForm = fb.group({
      mail: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.mode = this.router.url.includes('connexion') ? 'login' : 'register';

    } 

    ngOnInit(): void {
      this.allStatus();
    }

    allStatus() : void{
      this.statusService.getAllStatus().subscribe(
        (data)=>{
          this.listStatus = data;
          console.log("Liste des status : "+this.listStatus);
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
          console.log('Utilisateur à envoyer :', user);



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
          console.log('Utilisateur à envoyer :', user);

        this.userService.login(user).subscribe({
              next: (res) => {
                  this.router.navigate(['/accueil']);
                  this.message = res.message
                  this.userService.saveToken(this.message);
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

    update(){
      
    }
}
