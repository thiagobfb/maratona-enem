import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaratonaEnemSharedModule } from '../../shared';
import {
    AlunoService,
    AlunoPopupService,
    AlunoComponent,
    AlunoDetailComponent,
    AlunoDialogComponent,
    AlunoPopupComponent,
    AlunoDeletePopupComponent,
    AlunoDeleteDialogComponent,
    alunoRoute,
    alunoPopupRoute,
    AlunoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...alunoRoute,
    ...alunoPopupRoute,
];

@NgModule({
    imports: [
        MaratonaEnemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AlunoComponent,
        AlunoDetailComponent,
        AlunoDialogComponent,
        AlunoDeleteDialogComponent,
        AlunoPopupComponent,
        AlunoDeletePopupComponent,
    ],
    entryComponents: [
        AlunoComponent,
        AlunoDialogComponent,
        AlunoPopupComponent,
        AlunoDeleteDialogComponent,
        AlunoDeletePopupComponent,
    ],
    providers: [
        AlunoService,
        AlunoPopupService,
        AlunoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MaratonaEnemAlunoModule {}
