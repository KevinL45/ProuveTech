import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  menuOpen: boolean = false;
  isScrolled: boolean = false;
  scrollProgress: number = 0;

  constructor(private userService:UserService, private router:Router){

  }

  ngOnInit(): void {
    
    // // Fermer le menu lors du changement de route
    // this.router.events.subscribe(() => {
    //   //this.closeMenu();
    // });
  }

  logout(){
    this.userService.logout();
    this.router.navigate(["/accueil"])
  }

  @HostListener('window:scroll')
  onScroll(): void {
    this.isScrolled = window.scrollY > 20;
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
    this.updateBodyScroll();
  }

  isAuthenticated():boolean{
    return this.userService.isAuthenticated()
  }

  isAdmin():boolean{
    return this.userService.isAdmin()
  }

  closeMenu(): void {
    this.menuOpen = false;
    this.updateBodyScroll();
  }

  private updateBodyScroll(): void {
    if (this.menuOpen) {
      // Empêcher le scroll du body quand le menu est ouvert
      document.body.style.overflow = 'hidden';
      document.body.style.position = 'fixed';
      document.body.style.width = '100%';
    } else {
      // Restaurer le scroll normal
      document.body.style.overflow = '';
      document.body.style.position = '';
      document.body.style.width = '';
    }
  }

}
