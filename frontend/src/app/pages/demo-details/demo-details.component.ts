import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Demo } from '../../models/Demo';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
import { DemoService } from '../../services/demo.service';
import { User } from '../../models/User';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-demo-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './demo-details.component.html',
  styleUrl: './demo-details.component.scss'
})
export class DemoDetailsComponent implements OnInit{

  id: number | null = null
  demo: Demo | null = null;
  url:string = environment.apiUrl
  isVideoLoading: boolean = true;
  isDemoLoading: boolean = true;

    constructor(
    private route:ActivatedRoute, 
    private demoService:DemoService,
    private router:Router,
    private userService: UserService
  ){

  }


  ngOnInit():void{
    
    if (!this.userService.isAuthenticated()) {
        this.router.navigate(['/']);
        return;
      }

    this.route.paramMap.subscribe(params =>{
      const id = params.get("id");
      if(id){
        this.id = Number(id);
        this.loadDemo(this.id)
      }
    })

  }

  loadDemo(id : number){
    this.isDemoLoading = true;
    this.demoService.getDemo(id).subscribe((data)=>{
      this.demo = data;
      this.isDemoLoading = false
    })
  }

   detailsUser(user: User  | undefined ) {
    return this.router.navigate(["/details-profil",user?.id])
  }

  truncate(title: string | undefined, maxLength: number): string {
    if (!title) return 'Aucun titre';
    return title.length > maxLength ? title.substring(0, maxLength) + '...' : title;
  }

  onVideoLoaded() {
    this.isVideoLoading = false;
  }

  copyToClipboard(text: string) {
    navigator.clipboard.writeText(text);
  }


}
