import { IDeparmento, NewDeparmento } from './deparmento.model';

export const sampleWithRequiredData: IDeparmento = {
  id: 10895,
};

export const sampleWithPartialData: IDeparmento = {
  id: 1511,
  nombreDepartamento: 'but',
};

export const sampleWithFullData: IDeparmento = {
  id: 28408,
  nombreDepartamento: 'up since seldom',
  ubicacionDepartamento: 'bicycle',
  presupuestoDepartamento: 'vibraphone whoa',
};

export const sampleWithNewData: NewDeparmento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
