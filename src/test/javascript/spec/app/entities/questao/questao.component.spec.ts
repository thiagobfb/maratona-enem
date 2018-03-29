/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MaratonaEnemTestModule } from '../../../test.module';
import { QuestaoComponent } from '../../../../../../main/webapp/app/entities/questao/questao.component';
import { QuestaoService } from '../../../../../../main/webapp/app/entities/questao/questao.service';
import { Questao } from '../../../../../../main/webapp/app/entities/questao/questao.model';

describe('Component Tests', () => {

    describe('Questao Management Component', () => {
        let comp: QuestaoComponent;
        let fixture: ComponentFixture<QuestaoComponent>;
        let service: QuestaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MaratonaEnemTestModule],
                declarations: [QuestaoComponent],
                providers: [
                    QuestaoService
                ]
            })
            .overrideTemplate(QuestaoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuestaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Questao('123')],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.questaos[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
