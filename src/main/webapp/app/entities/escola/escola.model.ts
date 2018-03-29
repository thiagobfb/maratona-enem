import { BaseEntity } from './../../shared';

export const enum TipoEscola {
    'PUBLICA',
    'PARTICULAR'
}

export class Escola implements BaseEntity {
    constructor(
        public id?: string,
        public nome?: string,
        public tipoEscola?: TipoEscola,
    ) {
    }
}
