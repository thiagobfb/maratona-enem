/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MaratonaEnemTestModule } from '../../../test.module';
import { EscolaComponent } from '../../../../../../main/webapp/app/entities/escola/escola.component';
import { EscolaService } from '../../../../../../main/webapp/app/entities/escola/escola.service';
import { Escola } from '../../../../../../main/webapp/app/entities/escola/escola.model';

describe('Component Tests', () => {

    describe('Escola Management Component', () => {
        let comp: EscolaComponent;
        let fixture: ComponentFixture<EscolaComponent>;
        let service: EscolaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MaratonaEnemTestModule],
                declarations: [EscolaComponent],
                providers: [
                    EscolaService
                ]
            })
            .overrideTemplate(EscolaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscolaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscolaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Escola('123')],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.escolas[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
