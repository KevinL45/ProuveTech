import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'details-profil/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'projets/les-projets/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'modifier-projet/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'modifier-demo/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'details-projet/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'details-demo/:id',
    renderMode: RenderMode.Server
  },
  {
    path: '**',
    renderMode: RenderMode.Client
  }
];
