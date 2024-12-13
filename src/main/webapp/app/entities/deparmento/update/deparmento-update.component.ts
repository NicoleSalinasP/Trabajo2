import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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

  protected deparmentoService = inject(DeparmentoService);
  protected deparmentoFormService = inject(DeparmentoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DeparmentoFormGroup = this.deparmentoFormService.createDeparmentoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deparmento }) => {
      this.deparmento = deparmento;
      if (deparmento) {
        this.updateForm(deparmento);
      }
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
  }
}
