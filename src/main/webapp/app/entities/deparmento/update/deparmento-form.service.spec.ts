import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../deparmento.test-samples';

import { DeparmentoFormService } from './deparmento-form.service';

describe('Deparmento Form Service', () => {
  let service: DeparmentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeparmentoFormService);
  });

  describe('Service methods', () => {
    describe('createDeparmentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDeparmentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreDepartamento: expect.any(Object),
            ubicacionDepartamento: expect.any(Object),
            presupuestoDepartamento: expect.any(Object),
          }),
        );
      });

      it('passing IDeparmento should create a new form with FormGroup', () => {
        const formGroup = service.createDeparmentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreDepartamento: expect.any(Object),
            ubicacionDepartamento: expect.any(Object),
            presupuestoDepartamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getDeparmento', () => {
      it('should return NewDeparmento for default Deparmento initial value', () => {
        const formGroup = service.createDeparmentoFormGroup(sampleWithNewData);

        const deparmento = service.getDeparmento(formGroup) as any;

        expect(deparmento).toMatchObject(sampleWithNewData);
      });

      it('should return NewDeparmento for empty Deparmento initial value', () => {
        const formGroup = service.createDeparmentoFormGroup();

        const deparmento = service.getDeparmento(formGroup) as any;

        expect(deparmento).toMatchObject({});
      });

      it('should return IDeparmento', () => {
        const formGroup = service.createDeparmentoFormGroup(sampleWithRequiredData);

        const deparmento = service.getDeparmento(formGroup) as any;

        expect(deparmento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDeparmento should not enable id FormControl', () => {
        const formGroup = service.createDeparmentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDeparmento should disable id FormControl', () => {
        const formGroup = service.createDeparmentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
