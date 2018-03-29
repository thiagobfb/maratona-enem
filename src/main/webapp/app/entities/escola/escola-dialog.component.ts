import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Escola } from './escola.model';
import { EscolaPopupService } from './escola-popup.service';
import { EscolaService } from './escola.service';

@Component({
    selector: 'jhi-escola-dialog',
    templateUrl: './escola-dialog.component.html'
})
export class EscolaDialogComponent implements OnInit {

    escola: Escola;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private escolaService: EscolaService,
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
        if (this.escola.id !== undefined) {
            this.subscribeToSaveResponse(
                this.escolaService.update(this.escola));
        } else {
            this.subscribeToSaveResponse(
                this.escolaService.create(this.escola));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Escola>>) {
        result.subscribe((res: HttpResponse<Escola>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Escola) {
        this.eventManager.broadcast({ name: 'escolaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-escola-popup',
    template: ''
})
export class EscolaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private escolaPopupService: EscolaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.escolaPopupService
                    .open(EscolaDialogComponent as Component, params['id']);
            } else {
                this.escolaPopupService
                    .open(EscolaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
