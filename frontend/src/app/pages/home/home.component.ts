import { Component, ViewChild, ElementRef } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ActorItComponent } from "../actor-it/actor-it.component";
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [RouterModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  @ViewChild('videoElement') videoElement!: ElementRef<HTMLVideoElement>;

  constructor(private userService: UserService){

  }

  isAuthenticated():boolean{
    return this.userService.isAuthenticated()
  }

  onVideoLoaded(): void {
    // Ajouter la classe 'loaded' pour afficher la vidéo
    if (this.videoElement) {
      this.videoElement.nativeElement.classList.add('loaded');
    }
  }

}
