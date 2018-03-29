import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaratonaEnemSharedModule } from '../../shared';
import {
    EscolaService,
    EscolaPopupService,
    EscolaComponent,
    EscolaDetailComponent,
    EscolaDialogComponent,
    EscolaPopupComponent,
    EscolaDeletePopupComponent,
    EscolaDeleteDialogComponent,
    escolaRoute,
    escolaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...escolaRoute,
    ...escolaPopupRoute,
];

@NgModule({
    imports: [
        MaratonaEnemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EscolaComponent,
        EscolaDetailComponent,
        EscolaDialogComponent,
        EscolaDeleteDialogComponent,
        EscolaPopupComponent,
        EscolaDeletePopupComponent,
    ],
    entryComponents: [
        EscolaComponent,
        EscolaDialogComponent,
        EscolaPopupComponent,
        EscolaDeleteDialogComponent,
        EscolaDeletePopupComponent,
    ],
    providers: [
        EscolaService,
        EscolaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MaratonaEnemEscolaModule {}
