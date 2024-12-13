import { IEmpleados, NewEmpleados } from './empleados.model';

export const sampleWithRequiredData: IEmpleados = {
  id: 9918,
};

export const sampleWithPartialData: IEmpleados = {
  id: 15476,
  nombresEmpleado: 'regarding less while',
  apellidoEmpleado: 'when',
  telefono: 'upwardly',
  correo: 'yuck likable jubilant',
};

export const sampleWithFullData: IEmpleados = {
  id: 23139,
  nombresEmpleado: 'before haze meanwhile',
  apellidoEmpleado: 'mechanically',
  telefono: 'microblog fervently gloomy',
  correo: 'athwart powerless refer',
};

export const sampleWithNewData: NewEmpleados = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
