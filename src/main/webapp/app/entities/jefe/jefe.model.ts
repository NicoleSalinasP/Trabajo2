export interface IJefe {
  id: number;
  nombreJefe?: string | null;
  telefonoJefe?: string | null;
}

export type NewJefe = Omit<IJefe, 'id'> & { id: null };
