import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'trabajo2App.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'empleados',
    data: { pageTitle: 'trabajo2App.empleados.home.title' },
    loadChildren: () => import('./empleados/empleados.routes'),
  },
  {
    path: 'deparmento',
    data: { pageTitle: 'trabajo2App.deparmento.home.title' },
    loadChildren: () => import('./deparmento/deparmento.routes'),
  },
  {
    path: 'jefe',
    data: { pageTitle: 'trabajo2App.jefe.home.title' },
    loadChildren: () => import('./jefe/jefe.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
