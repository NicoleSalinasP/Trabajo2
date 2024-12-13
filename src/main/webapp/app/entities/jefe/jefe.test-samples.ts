import { IJefe, NewJefe } from './jefe.model';

export const sampleWithRequiredData: IJefe = {
  id: 25527,
};

export const sampleWithPartialData: IJefe = {
  id: 32195,
  nombreJefe: 'huzzah when',
  telefonoJefe: 'besides',
};

export const sampleWithFullData: IJefe = {
  id: 3644,
  nombreJefe: 'than kosher uh-huh',
  telefonoJefe: 'out yin',
};

export const sampleWithNewData: NewJefe = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
