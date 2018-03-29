import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MaratonaEnemSharedModule } from '../../shared';
import {
    QuestaoService,
    QuestaoPopupService,
    QuestaoComponent,
    QuestaoDetailComponent,
    QuestaoDialogComponent,
    QuestaoPopupComponent,
    QuestaoDeletePopupComponent,
    QuestaoDeleteDialogComponent,
    questaoRoute,
    questaoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...questaoRoute,
    ...questaoPopupRoute,
];

@NgModule({
    imports: [
        MaratonaEnemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        QuestaoComponent,
        QuestaoDetailComponent,
        QuestaoDialogComponent,
        QuestaoDeleteDialogComponent,
        QuestaoPopupComponent,
        QuestaoDeletePopupComponent,
    ],
    entryComponents: [
        QuestaoComponent,
        QuestaoDialogComponent,
        QuestaoPopupComponent,
        QuestaoDeleteDialogComponent,
        QuestaoDeletePopupComponent,
    ],
    providers: [
        QuestaoService,
        QuestaoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MaratonaEnemQuestaoModule {}
