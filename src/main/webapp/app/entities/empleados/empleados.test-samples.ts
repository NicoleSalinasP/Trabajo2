import { IEmpleados, NewEmpleados } from './empleados.model';

export const sampleWithRequiredData: IEmpleados = {
  id: 20450,
};

export const sampleWithPartialData: IEmpleados = {
  id: 14768,
  nombresEmpleado: 'scared acidly',
  apellidoEmpleado: 'undergo',
  correo: 'polyester provided alb',
};

export const sampleWithFullData: IEmpleados = {
  id: 23533,
  nombresEmpleado: 'sternly',
  apellidoEmpleado: 'especially ill-fated',
  telefono: 'lifestyle',
  correo: 'square drat yellowish',
};

export const sampleWithNewData: NewEmpleados = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
