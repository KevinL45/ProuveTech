import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout/layout.component';
import { HomeComponent } from './pages/home/home.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { DevelopersComponent } from './pages/developers/developers.component';
import { LoginComponent } from './form/login/login.component';
import { FormUserComponent } from './form/form-user/form-user.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent, 
    children: [
    { path: '', component: HomeComponent }, 
    { path: 'accueil', component: HomeComponent },
    { path: 'projets', component: ProjectsComponent },
    { path: 'developpeurs', component: DevelopersComponent },
    { path: 'profil', component: ProfileComponent },
    { path: 'connexion', component: LoginComponent },
    { path: 'inscription', component:  FormUserComponent},

    { path: '', component: HomeComponent }
    ]
  },

];

