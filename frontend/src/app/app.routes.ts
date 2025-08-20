import { Routes } from '@angular/router';
import { LayoutComponent } from './layout/layout/layout.component';
import { HomeComponent } from './pages/home/home.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { UserFormComponent } from './form/user-form/user-form.component';
import { ActorItComponent } from './pages/actor-it/actor-it.component';
import { ProjectFormComponent } from './form/project-form/project-form.component';
import { ProjectDetailsComponent } from './pages/project-details/project-details.component';
import { AdminComponent } from './pages/admin/admin.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent, 
    children: [
    { path: '', component: HomeComponent }, 
    { path: 'accueil', component: HomeComponent },

    { path: 'acteurs-it', component:  ActorItComponent},
    { path: 'profil', component: ProfileComponent },
    { path: 'admin', component: AdminComponent },
    { path: 'connexion', component: UserFormComponent },
    { path: 'inscription', component:  UserFormComponent},
    { path: 'modifier-profil', component:  UserFormComponent},
    { path: 'details-profil/:id', component: ProfileComponent },


    { path: 'projets', component: ProjectsComponent },
    { path: 'projets/mes-demos', component: ProjectsComponent },
    { path: 'projets/positionnement/:id', component: ProjectsComponent },


    { path: 'details-projet/:id', component:  ProjectDetailsComponent},
    { path: 'creer-projet', component:  ProjectFormComponent},
    { path: 'modifier-projet/:id', component:  ProjectFormComponent},




    { path: '', component: HomeComponent }
    ]
  },

];

