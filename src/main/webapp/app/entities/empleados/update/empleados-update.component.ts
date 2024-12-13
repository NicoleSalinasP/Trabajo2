import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDeparmento } from 'app/entities/deparmento/deparmento.model';
import { DeparmentoService } from 'app/entities/deparmento/service/deparmento.service';
import { IEmpleados } from '../empleados.model';
import { EmpleadosService } from '../service/empleados.service';
import { EmpleadosFormGroup, EmpleadosFormService } from './empleados-form.service';

@Component({
  standalone: true,
  selector: 'jhi-empleados-update',
  templateUrl: './empleados-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmpleadosUpdateComponent implements OnInit {
  isSaving = false;
  empleados: IEmpleados | null = null;

  deparmentosSharedCollection: IDeparmento[] = [];

  protected empleadosService = inject(EmpleadosService);
  protected empleadosFormService = inject(EmpleadosFormService);
  protected deparmentoService = inject(DeparmentoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpleadosFormGroup = this.empleadosFormService.createEmpleadosFormGroup();

  compareDeparmento = (o1: IDeparmento | null, o2: IDeparmento | null): boolean => this.deparmentoService.compareDeparmento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empleados }) => {
      this.empleados = empleados;
      if (empleados) {
        this.updateForm(empleados);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empleados = this.empleadosFormService.getEmpleados(this.editForm);
    if (empleados.id !== null) {
      this.subscribeToSaveResponse(this.empleadosService.update(empleados));
    } else {
      this.subscribeToSaveResponse(this.empleadosService.create(empleados));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleados>>): void {
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

  protected updateForm(empleados: IEmpleados): void {
    this.empleados = empleados;
    this.empleadosFormService.resetForm(this.editForm, empleados);

    this.deparmentosSharedCollection = this.deparmentoService.addDeparmentoToCollectionIfMissing<IDeparmento>(
      this.deparmentosSharedCollection,
      empleados.deparmento,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.deparmentoService
      .query()
      .pipe(map((res: HttpResponse<IDeparmento[]>) => res.body ?? []))
      .pipe(
        map((deparmentos: IDeparmento[]) =>
          this.deparmentoService.addDeparmentoToCollectionIfMissing<IDeparmento>(deparmentos, this.empleados?.deparmento),
        ),
      )
      .subscribe((deparmentos: IDeparmento[]) => (this.deparmentosSharedCollection = deparmentos));
  }
}
