import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ActorItComponent } from "../actor-it/actor-it.component";

@Component({
  selector: 'app-home',
  imports: [RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
