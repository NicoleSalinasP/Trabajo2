import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmpleados, NewEmpleados } from '../empleados.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpleados for edit and NewEmpleadosFormGroupInput for create.
 */
type EmpleadosFormGroupInput = IEmpleados | PartialWithRequiredKeyOf<NewEmpleados>;

type EmpleadosFormDefaults = Pick<NewEmpleados, 'id'>;

type EmpleadosFormGroupContent = {
  id: FormControl<IEmpleados['id'] | NewEmpleados['id']>;
  nombresEmpleado: FormControl<IEmpleados['nombresEmpleado']>;
  apellidoEmpleado: FormControl<IEmpleados['apellidoEmpleado']>;
  telefono: FormControl<IEmpleados['telefono']>;
  correo: FormControl<IEmpleados['correo']>;
  deparmento: FormControl<IEmpleados['deparmento']>;
};

export type EmpleadosFormGroup = FormGroup<EmpleadosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpleadosFormService {
  createEmpleadosFormGroup(empleados: EmpleadosFormGroupInput = { id: null }): EmpleadosFormGroup {
    const empleadosRawValue = {
      ...this.getFormDefaults(),
      ...empleados,
    };
    return new FormGroup<EmpleadosFormGroupContent>({
      id: new FormControl(
        { value: empleadosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombresEmpleado: new FormControl(empleadosRawValue.nombresEmpleado),
      apellidoEmpleado: new FormControl(empleadosRawValue.apellidoEmpleado),
      telefono: new FormControl(empleadosRawValue.telefono),
      correo: new FormControl(empleadosRawValue.correo),
      deparmento: new FormControl(empleadosRawValue.deparmento),
    });
  }

  getEmpleados(form: EmpleadosFormGroup): IEmpleados | NewEmpleados {
    return form.getRawValue() as IEmpleados | NewEmpleados;
  }

  resetForm(form: EmpleadosFormGroup, empleados: EmpleadosFormGroupInput): void {
    const empleadosRawValue = { ...this.getFormDefaults(), ...empleados };
    form.reset(
      {
        ...empleadosRawValue,
        id: { value: empleadosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpleadosFormDefaults {
    return {
      id: null,
    };
  }
}
