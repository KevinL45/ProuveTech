import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { environment } from '../../../environments/environment';
import { UserService } from '../../services/user.service';
import { Project } from '../../models/Project';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { DemoService } from '../../services/demo.service';
import { Demo } from '../../models/Demo';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  fabMenuOpen = false;

  user: User | null = null;
  projects: Project[] = [];
  demos: Demo[] = [];

  url: string = environment.apiUrl;
  showFullDescription = false;

  id: number | null = null


  mode: 'details-profile' | 'profil' = 'profil';

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService,
    private demoService: DemoService
  ) {

  }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      const id = params.get("id");
      if (id) {
        this.id = Number(id);
      }
    })

    //Si il voit les détails du profil
    if (this.id) {

      this.loadSelectedProfile(this.id);
      this.loadSelectedProjects(this.id);
      this.loadSelectedDemos(this.id);

    } else {
      //Si il est connecté
      if (!this.userService.isAuthenticated()) {
        this.router.navigate(['/']);
        return;
      }
      this.loadProfile();
      this.loadMyProjects();
      this.loadMyDemos();

    }
  }

  loadProfile(): void {
    this.userService.getProfil().subscribe((data) => {
      this.user = data
    })
  }

  loadMyProjects():void{
    this.userService.getMyProjects().subscribe((data)=>{
      this.projects = data
    })

  }

    loadMyDemos(): void {
    this.demoService.getAllDemos().subscribe((data) => {
      this.demos =data
    });
  }

  editProfile() {
    return this.router.navigate(["/modifier-profil"])
  }

  addDemo() {
    return this.router.navigate(["/creer-demo"])
  }

  loadSelectedProfile(id: number): void {
    this.userService.getUser(id).subscribe((data) => {
      this.user = data
    })
  }

  loadSelectedProjects(id:number) : void{
    this.userService.getProjectsUser(id).subscribe((data)=>{
      this.projects = data
    })
  }

  loadSelectedDemos(id: number): void {
    this.demoService.getAllDemos().subscribe((data) => {
      this.demos = data.filter(demo => {
        return demo.project?.user?.id === id;
      });
    });
  }

  toggleDescription() {
    this.showFullDescription = !this.showFullDescription;
  }

  isLongDescription(): boolean {
    return !!this.user?.description && this.user.description.length > 200;
  }

  MyProjectList() {
    return this.router.navigate(["/projets/mes-projets"])
  }

  ProjectList(id:number | undefined) {
    return this.router.navigate(["/projets/les-projets", id])
  }

  addProject() {
    return this.router.navigate(["/creer-projet"])
  }

  detailsProject(project: Project) {
    return this.router.navigate(["/details-projet", project.id])
  }
}
