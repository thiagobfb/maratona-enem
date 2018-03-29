/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MaratonaEnemTestModule } from '../../../test.module';
import { QuestaoDetailComponent } from '../../../../../../main/webapp/app/entities/questao/questao-detail.component';
import { QuestaoService } from '../../../../../../main/webapp/app/entities/questao/questao.service';
import { Questao } from '../../../../../../main/webapp/app/entities/questao/questao.model';

describe('Component Tests', () => {

    describe('Questao Management Detail Component', () => {
        let comp: QuestaoDetailComponent;
        let fixture: ComponentFixture<QuestaoDetailComponent>;
        let service: QuestaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MaratonaEnemTestModule],
                declarations: [QuestaoDetailComponent],
                providers: [
                    QuestaoService
                ]
            })
            .overrideTemplate(QuestaoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuestaoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Questao('123')
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.questao).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
