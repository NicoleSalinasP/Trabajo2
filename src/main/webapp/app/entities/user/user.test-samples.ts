import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 14521,
  login: 'Gt@lF2KP',
};

export const sampleWithPartialData: IUser = {
  id: 13392,
  login: 'w',
};

export const sampleWithFullData: IUser = {
  id: 468,
  login: 'K2S',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
