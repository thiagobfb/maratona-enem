import { BaseEntity } from './../../shared';

export const enum TipoDisciplina {
    'EXATAS',
    'HUMANAS'
}

export class Disciplina implements BaseEntity {
    constructor(
        public id?: string,
        public nome?: string,
        public tipoDisciplina?: TipoDisciplina,
    ) {
    }
}
