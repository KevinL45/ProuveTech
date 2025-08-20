import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { environment } from '../../../environments/environment';
import { UserService } from '../../services/user.service';
import { Project } from '../../models/Project';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  user: User | null = null;
  projects: Project[] = [];

  url: string = environment.apiUrl;
  showFullDescription = false;

  id: number | null = null


  mode: 'details-profile' | 'profil' = 'profil';

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {

  }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      const id = params.get("id");
      if (id) {
        this.id = Number(id);
      }
    })
    if (this.id) {

      this.loadSelectedProfile(this.id);

    } else {
      if (!this.userService.isAuthenticated()) {
        this.router.navigate(['/']);
        return;
      }
      this.loadProfile();

    }
  }

  loadProfile(): void {
    this.userService.getProfil().subscribe((data) => {
      this.user = data
    })
  }

  editProfile() {
    return this.router.navigate(["/modifier-profil"])
  }

  loadSelectedProfile(id: number): void {
    this.userService.getUser(id).subscribe((data) => {
      this.user = data
    })
  }

  toggleDescription() {
    this.showFullDescription = !this.showFullDescription;
  }

  isLongDescription(): boolean {
    return !!this.user?.description && this.user.description.length > 200;
  }

  MyProjectList() {
    return this.router.navigate(["/projets/mes-demos"])
  }

  ProjectList(id:number | undefined) {
    return this.router.navigate(["/projets/positionnement", id])
  }

  addProject() {
    return this.router.navigate(["/creer-projet"])
  }
}
