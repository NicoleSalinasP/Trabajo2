export interface IDeparmento {
  id: number;
  nombreDepartamento?: string | null;
  ubicacionDepartamento?: string | null;
  presupuestoDepartamento?: string | null;
}

export type NewDeparmento = Omit<IDeparmento, 'id'> & { id: null };
