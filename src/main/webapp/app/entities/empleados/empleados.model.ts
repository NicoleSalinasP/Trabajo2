import { IDeparmento } from 'app/entities/deparmento/deparmento.model';

export interface IEmpleados {
  id: number;
  nombresEmpleado?: string | null;
  apellidoEmpleado?: string | null;
  telefono?: string | null;
  correo?: string | null;
  deparmento?: Pick<IDeparmento, 'id'> | null;
}

export type NewEmpleados = Omit<IEmpleados, 'id'> & { id: null };
