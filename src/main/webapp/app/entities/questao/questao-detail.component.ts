import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Questao } from './questao.model';
import { QuestaoService } from './questao.service';

@Component({
    selector: 'jhi-questao-detail',
    templateUrl: './questao-detail.component.html'
})
export class QuestaoDetailComponent implements OnInit, OnDestroy {

    questao: Questao;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private questaoService: QuestaoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuestaos();
    }

    load(id) {
        this.questaoService.find(id)
            .subscribe((questaoResponse: HttpResponse<Questao>) => {
                this.questao = questaoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuestaos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'questaoListModification',
            (response) => this.load(this.questao.id)
        );
    }
}
