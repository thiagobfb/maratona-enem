import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Disciplina } from './disciplina.model';
import { DisciplinaPopupService } from './disciplina-popup.service';
import { DisciplinaService } from './disciplina.service';

@Component({
    selector: 'jhi-disciplina-delete-dialog',
    templateUrl: './disciplina-delete-dialog.component.html'
})
export class DisciplinaDeleteDialogComponent {

    disciplina: Disciplina;

    constructor(
        private disciplinaService: DisciplinaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.disciplinaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'disciplinaListModification',
                content: 'Deleted an disciplina'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-disciplina-delete-popup',
    template: ''
})
export class DisciplinaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private disciplinaPopupService: DisciplinaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.disciplinaPopupService
                .open(DisciplinaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
