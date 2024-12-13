import { IJefe } from 'app/entities/jefe/jefe.model';

export interface IDeparmento {
  id: number;
  nombreDepartamento?: string | null;
  ubicacionDepartamento?: string | null;
  presupuestoDepartamento?: string | null;
  jefe?: Pick<IJefe, 'id'> | null;
}

export type NewDeparmento = Omit<IDeparmento, 'id'> & { id: null };
