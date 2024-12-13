import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '3de4d8c0-8121-4a8c-9e7e-b77d5df9b6f3',
};

export const sampleWithPartialData: IAuthority = {
  name: '4fdb13d7-a0c3-48b0-9cf1-42b19bed3f3f',
};

export const sampleWithFullData: IAuthority = {
  name: '060ac112-9e2f-4c70-ba78-2e9327839297',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
