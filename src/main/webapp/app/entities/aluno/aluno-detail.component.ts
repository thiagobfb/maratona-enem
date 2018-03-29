import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Aluno } from './aluno.model';
import { AlunoService } from './aluno.service';

@Component({
    selector: 'jhi-aluno-detail',
    templateUrl: './aluno-detail.component.html'
})
export class AlunoDetailComponent implements OnInit, OnDestroy {

    aluno: Aluno;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private alunoService: AlunoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAlunos();
    }

    load(id) {
        this.alunoService.find(id)
            .subscribe((alunoResponse: HttpResponse<Aluno>) => {
                this.aluno = alunoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAlunos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'alunoListModification',
            (response) => this.load(this.aluno.id)
        );
    }
}
