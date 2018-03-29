import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Disciplina } from './disciplina.model';
import { DisciplinaPopupService } from './disciplina-popup.service';
import { DisciplinaService } from './disciplina.service';

@Component({
    selector: 'jhi-disciplina-dialog',
    templateUrl: './disciplina-dialog.component.html'
})
export class DisciplinaDialogComponent implements OnInit {

    disciplina: Disciplina;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private disciplinaService: DisciplinaService,
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
        if (this.disciplina.id !== undefined) {
            this.subscribeToSaveResponse(
                this.disciplinaService.update(this.disciplina));
        } else {
            this.subscribeToSaveResponse(
                this.disciplinaService.create(this.disciplina));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Disciplina>>) {
        result.subscribe((res: HttpResponse<Disciplina>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Disciplina) {
        this.eventManager.broadcast({ name: 'disciplinaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-disciplina-popup',
    template: ''
})
export class DisciplinaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private disciplinaPopupService: DisciplinaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.disciplinaPopupService
                    .open(DisciplinaDialogComponent as Component, params['id']);
            } else {
                this.disciplinaPopupService
                    .open(DisciplinaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
