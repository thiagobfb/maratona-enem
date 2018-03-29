import { BaseEntity } from './../../shared';

export class Questao implements BaseEntity {
    constructor(
        public id?: string,
        public descricao?: string,
    ) {
    }
}
