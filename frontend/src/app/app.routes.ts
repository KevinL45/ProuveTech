import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout/layout.component';
import { HomeComponent } from './pages/home/home.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { DevelopersComponent } from './pages/developers/developers.component';
import { RecruitsComponent } from './pages/recruits/recruits.component';
import { UserFormComponent } from './form/user-form/user-form.component';
import { ActorItComponent } from './pages/actor-it/actor-it.component';
import { ProjectFormComponent } from './form/project-form/project-form.component';
import { ProjectDetailsComponent } from './pages/project-details/project-details.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent, 
    children: [
    { path: '', component: HomeComponent }, 
    { path: 'accueil', component: HomeComponent },
    { path: 'projets', component: ProjectsComponent },
    { path: 'developpeurs', component: DevelopersComponent },
    { path: 'recruteurs', component:  RecruitsComponent},
    { path: 'acteurs-it', component:  ActorItComponent},
    { path: 'profil', component: ProfileComponent },

    { path: 'connexion', component: UserFormComponent },
    { path: 'inscription', component:  UserFormComponent},
    { path: 'modifier-profil', component:  UserFormComponent},

    { path: 'creer-projet', component:  ProjectFormComponent},
    { path: 'modifier-projet', component:  ProjectFormComponent},
    { path: 'details-projet/:id', component:  ProjectDetailsComponent},


    { path: '', component: HomeComponent }
    ]
  },

];

