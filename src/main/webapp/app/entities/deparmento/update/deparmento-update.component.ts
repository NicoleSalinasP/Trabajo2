import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IJefe } from 'app/entities/jefe/jefe.model';
import { JefeService } from 'app/entities/jefe/service/jefe.service';
import { IDeparmento } from '../deparmento.model';
import { DeparmentoService } from '../service/deparmento.service';
import { DeparmentoFormGroup, DeparmentoFormService } from './deparmento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-deparmento-update',
  templateUrl: './deparmento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DeparmentoUpdateComponent implements OnInit {
  isSaving = false;
  deparmento: IDeparmento | null = null;

  jefesSharedCollection: IJefe[] = [];

  protected deparmentoService = inject(DeparmentoService);
  protected deparmentoFormService = inject(DeparmentoFormService);
  protected jefeService = inject(JefeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DeparmentoFormGroup = this.deparmentoFormService.createDeparmentoFormGroup();

  compareJefe = (o1: IJefe | null, o2: IJefe | null): boolean => this.jefeService.compareJefe(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deparmento }) => {
      this.deparmento = deparmento;
      if (deparmento) {
        this.updateForm(deparmento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deparmento = this.deparmentoFormService.getDeparmento(this.editForm);
    if (deparmento.id !== null) {
      this.subscribeToSaveResponse(this.deparmentoService.update(deparmento));
    } else {
      this.subscribeToSaveResponse(this.deparmentoService.create(deparmento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeparmento>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(deparmento: IDeparmento): void {
    this.deparmento = deparmento;
    this.deparmentoFormService.resetForm(this.editForm, deparmento);

    this.jefesSharedCollection = this.jefeService.addJefeToCollectionIfMissing<IJefe>(this.jefesSharedCollection, deparmento.jefe);
  }

  protected loadRelationshipsOptions(): void {
    this.jefeService
      .query()
      .pipe(map((res: HttpResponse<IJefe[]>) => res.body ?? []))
      .pipe(map((jefes: IJefe[]) => this.jefeService.addJefeToCollectionIfMissing<IJefe>(jefes, this.deparmento?.jefe)))
      .subscribe((jefes: IJefe[]) => (this.jefesSharedCollection = jefes));
  }
}
