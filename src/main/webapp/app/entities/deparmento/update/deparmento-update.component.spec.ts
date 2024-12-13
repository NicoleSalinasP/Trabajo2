import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IJefe } from 'app/entities/jefe/jefe.model';
import { JefeService } from 'app/entities/jefe/service/jefe.service';
import { DeparmentoService } from '../service/deparmento.service';
import { IDeparmento } from '../deparmento.model';
import { DeparmentoFormService } from './deparmento-form.service';

import { DeparmentoUpdateComponent } from './deparmento-update.component';

describe('Deparmento Management Update Component', () => {
  let comp: DeparmentoUpdateComponent;
  let fixture: ComponentFixture<DeparmentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let deparmentoFormService: DeparmentoFormService;
  let deparmentoService: DeparmentoService;
  let jefeService: JefeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DeparmentoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DeparmentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeparmentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    deparmentoFormService = TestBed.inject(DeparmentoFormService);
    deparmentoService = TestBed.inject(DeparmentoService);
    jefeService = TestBed.inject(JefeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Jefe query and add missing value', () => {
      const deparmento: IDeparmento = { id: 456 };
      const jefe: IJefe = { id: 26051 };
      deparmento.jefe = jefe;

      const jefeCollection: IJefe[] = [{ id: 25317 }];
      jest.spyOn(jefeService, 'query').mockReturnValue(of(new HttpResponse({ body: jefeCollection })));
      const additionalJefes = [jefe];
      const expectedCollection: IJefe[] = [...additionalJefes, ...jefeCollection];
      jest.spyOn(jefeService, 'addJefeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ deparmento });
      comp.ngOnInit();

      expect(jefeService.query).toHaveBeenCalled();
      expect(jefeService.addJefeToCollectionIfMissing).toHaveBeenCalledWith(
        jefeCollection,
        ...additionalJefes.map(expect.objectContaining),
      );
      expect(comp.jefesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const deparmento: IDeparmento = { id: 456 };
      const jefe: IJefe = { id: 12671 };
      deparmento.jefe = jefe;

      activatedRoute.data = of({ deparmento });
      comp.ngOnInit();

      expect(comp.jefesSharedCollection).toContain(jefe);
      expect(comp.deparmento).toEqual(deparmento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeparmento>>();
      const deparmento = { id: 123 };
      jest.spyOn(deparmentoFormService, 'getDeparmento').mockReturnValue(deparmento);
      jest.spyOn(deparmentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deparmento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deparmento }));
      saveSubject.complete();

      // THEN
      expect(deparmentoFormService.getDeparmento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(deparmentoService.update).toHaveBeenCalledWith(expect.objectContaining(deparmento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeparmento>>();
      const deparmento = { id: 123 };
      jest.spyOn(deparmentoFormService, 'getDeparmento').mockReturnValue({ id: null });
      jest.spyOn(deparmentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deparmento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deparmento }));
      saveSubject.complete();

      // THEN
      expect(deparmentoFormService.getDeparmento).toHaveBeenCalled();
      expect(deparmentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeparmento>>();
      const deparmento = { id: 123 };
      jest.spyOn(deparmentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deparmento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(deparmentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareJefe', () => {
      it('Should forward to jefeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(jefeService, 'compareJefe');
        comp.compareJefe(entity, entity2);
        expect(jefeService.compareJefe).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
