import { BaseEntity } from './../../shared';

export class Aluno implements BaseEntity {
    constructor(
        public id?: string,
        public nome?: string,
        public email?: string,
        public pontuacao?: number,
    ) {
    }
}
