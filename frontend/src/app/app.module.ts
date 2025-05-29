import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { routes } from './app.routes';
import { LayoutComponent } from './layout/layout/layout.component';

@NgModule({
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    LayoutComponent // ✅ Ajout ici, car c’est un composant standalone
  ],
  declarations: [
  ],
})
export class AppModule {}
