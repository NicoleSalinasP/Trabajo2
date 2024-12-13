import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeparmento, NewDeparmento } from '../deparmento.model';

export type PartialUpdateDeparmento = Partial<IDeparmento> & Pick<IDeparmento, 'id'>;

export type EntityResponseType = HttpResponse<IDeparmento>;
export type EntityArrayResponseType = HttpResponse<IDeparmento[]>;

@Injectable({ providedIn: 'root' })
export class DeparmentoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/deparmentos');

  create(deparmento: NewDeparmento): Observable<EntityResponseType> {
    return this.http.post<IDeparmento>(this.resourceUrl, deparmento, { observe: 'response' });
  }

  update(deparmento: IDeparmento): Observable<EntityResponseType> {
    return this.http.put<IDeparmento>(`${this.resourceUrl}/${this.getDeparmentoIdentifier(deparmento)}`, deparmento, {
      observe: 'response',
    });
  }

  partialUpdate(deparmento: PartialUpdateDeparmento): Observable<EntityResponseType> {
    return this.http.patch<IDeparmento>(`${this.resourceUrl}/${this.getDeparmentoIdentifier(deparmento)}`, deparmento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeparmento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeparmento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDeparmentoIdentifier(deparmento: Pick<IDeparmento, 'id'>): number {
    return deparmento.id;
  }

  compareDeparmento(o1: Pick<IDeparmento, 'id'> | null, o2: Pick<IDeparmento, 'id'> | null): boolean {
    return o1 && o2 ? this.getDeparmentoIdentifier(o1) === this.getDeparmentoIdentifier(o2) : o1 === o2;
  }

  addDeparmentoToCollectionIfMissing<Type extends Pick<IDeparmento, 'id'>>(
    deparmentoCollection: Type[],
    ...deparmentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const deparmentos: Type[] = deparmentosToCheck.filter(isPresent);
    if (deparmentos.length > 0) {
      const deparmentoCollectionIdentifiers = deparmentoCollection.map(deparmentoItem => this.getDeparmentoIdentifier(deparmentoItem));
      const deparmentosToAdd = deparmentos.filter(deparmentoItem => {
        const deparmentoIdentifier = this.getDeparmentoIdentifier(deparmentoItem);
        if (deparmentoCollectionIdentifiers.includes(deparmentoIdentifier)) {
          return false;
        }
        deparmentoCollectionIdentifiers.push(deparmentoIdentifier);
        return true;
      });
      return [...deparmentosToAdd, ...deparmentoCollection];
    }
    return deparmentoCollection;
  }
}
