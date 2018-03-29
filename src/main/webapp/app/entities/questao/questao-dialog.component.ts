import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Questao } from './questao.model';
import { QuestaoPopupService } from './questao-popup.service';
import { QuestaoService } from './questao.service';

@Component({
    selector: 'jhi-questao-dialog',
    templateUrl: './questao-dialog.component.html'
})
export class QuestaoDialogComponent implements OnInit {

    questao: Questao;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private questaoService: QuestaoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.questao.id !== undefined) {
            this.subscribeToSaveResponse(
                this.questaoService.update(this.questao));
        } else {
            this.subscribeToSaveResponse(
                this.questaoService.create(this.questao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Questao>>) {
        result.subscribe((res: HttpResponse<Questao>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Questao) {
        this.eventManager.broadcast({ name: 'questaoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-questao-popup',
    template: ''
})
export class QuestaoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private questaoPopupService: QuestaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.questaoPopupService
                    .open(QuestaoDialogComponent as Component, params['id']);
            } else {
                this.questaoPopupService
                    .open(QuestaoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
