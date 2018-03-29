import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MaratonaEnemQuestaoModule } from './questao/questao.module';
import { MaratonaEnemAlunoModule } from './aluno/aluno.module';
import { MaratonaEnemEscolaModule } from './escola/escola.module';
import { MaratonaEnemDisciplinaModule } from './disciplina/disciplina.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MaratonaEnemQuestaoModule,
        MaratonaEnemAlunoModule,
        MaratonaEnemEscolaModule,
        MaratonaEnemDisciplinaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MaratonaEnemEntityModule {}
