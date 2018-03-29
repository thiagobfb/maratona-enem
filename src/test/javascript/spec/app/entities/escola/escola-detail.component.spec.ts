/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MaratonaEnemTestModule } from '../../../test.module';
import { EscolaDetailComponent } from '../../../../../../main/webapp/app/entities/escola/escola-detail.component';
import { EscolaService } from '../../../../../../main/webapp/app/entities/escola/escola.service';
import { Escola } from '../../../../../../main/webapp/app/entities/escola/escola.model';

describe('Component Tests', () => {

    describe('Escola Management Detail Component', () => {
        let comp: EscolaDetailComponent;
        let fixture: ComponentFixture<EscolaDetailComponent>;
        let service: EscolaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MaratonaEnemTestModule],
                declarations: [EscolaDetailComponent],
                providers: [
                    EscolaService
                ]
            })
            .overrideTemplate(EscolaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscolaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscolaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Escola('123')
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.escola).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
