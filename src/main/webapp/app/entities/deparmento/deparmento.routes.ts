import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DeparmentoResolve from './route/deparmento-routing-resolve.service';

const deparmentoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/deparmento.component').then(m => m.DeparmentoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/deparmento-detail.component').then(m => m.DeparmentoDetailComponent),
    resolve: {
      deparmento: DeparmentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/deparmento-update.component').then(m => m.DeparmentoUpdateComponent),
    resolve: {
      deparmento: DeparmentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/deparmento-update.component').then(m => m.DeparmentoUpdateComponent),
    resolve: {
      deparmento: DeparmentoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default deparmentoRoute;
