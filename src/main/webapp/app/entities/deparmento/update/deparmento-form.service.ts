import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDeparmento, NewDeparmento } from '../deparmento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDeparmento for edit and NewDeparmentoFormGroupInput for create.
 */
type DeparmentoFormGroupInput = IDeparmento | PartialWithRequiredKeyOf<NewDeparmento>;

type DeparmentoFormDefaults = Pick<NewDeparmento, 'id'>;

type DeparmentoFormGroupContent = {
  id: FormControl<IDeparmento['id'] | NewDeparmento['id']>;
  nombreDepartamento: FormControl<IDeparmento['nombreDepartamento']>;
  ubicacionDepartamento: FormControl<IDeparmento['ubicacionDepartamento']>;
  presupuestoDepartamento: FormControl<IDeparmento['presupuestoDepartamento']>;
};

export type DeparmentoFormGroup = FormGroup<DeparmentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DeparmentoFormService {
  createDeparmentoFormGroup(deparmento: DeparmentoFormGroupInput = { id: null }): DeparmentoFormGroup {
    const deparmentoRawValue = {
      ...this.getFormDefaults(),
      ...deparmento,
    };
    return new FormGroup<DeparmentoFormGroupContent>({
      id: new FormControl(
        { value: deparmentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombreDepartamento: new FormControl(deparmentoRawValue.nombreDepartamento),
      ubicacionDepartamento: new FormControl(deparmentoRawValue.ubicacionDepartamento),
      presupuestoDepartamento: new FormControl(deparmentoRawValue.presupuestoDepartamento),
    });
  }

  getDeparmento(form: DeparmentoFormGroup): IDeparmento | NewDeparmento {
    return form.getRawValue() as IDeparmento | NewDeparmento;
  }

  resetForm(form: DeparmentoFormGroup, deparmento: DeparmentoFormGroupInput): void {
    const deparmentoRawValue = { ...this.getFormDefaults(), ...deparmento };
    form.reset(
      {
        ...deparmentoRawValue,
        id: { value: deparmentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DeparmentoFormDefaults {
    return {
      id: null,
    };
  }
}
