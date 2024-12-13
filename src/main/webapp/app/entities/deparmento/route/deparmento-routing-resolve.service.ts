import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeparmento } from '../deparmento.model';
import { DeparmentoService } from '../service/deparmento.service';

const deparmentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IDeparmento> => {
  const id = route.params.id;
  if (id) {
    return inject(DeparmentoService)
      .find(id)
      .pipe(
        mergeMap((deparmento: HttpResponse<IDeparmento>) => {
          if (deparmento.body) {
            return of(deparmento.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default deparmentoResolve;
