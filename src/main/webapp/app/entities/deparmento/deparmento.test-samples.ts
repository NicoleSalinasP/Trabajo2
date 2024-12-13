import { IDeparmento, NewDeparmento } from './deparmento.model';

export const sampleWithRequiredData: IDeparmento = {
  id: 2579,
};

export const sampleWithPartialData: IDeparmento = {
  id: 29562,
  nombreDepartamento: 'irk majestically',
  ubicacionDepartamento: 'boohoo who',
  presupuestoDepartamento: 'over heavily rotating',
};

export const sampleWithFullData: IDeparmento = {
  id: 25727,
  nombreDepartamento: 'supposing',
  ubicacionDepartamento: 'kinase enchanting brr',
  presupuestoDepartamento: 'till upward',
};

export const sampleWithNewData: NewDeparmento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
