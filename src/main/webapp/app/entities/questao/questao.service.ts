import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Questao } from './questao.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Questao>;

@Injectable()
export class QuestaoService {

    private resourceUrl =  SERVER_API_URL + 'api/questaos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/questaos';

    constructor(private http: HttpClient) { }

    create(questao: Questao): Observable<EntityResponseType> {
        const copy = this.convert(questao);
        return this.http.post<Questao>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(questao: Questao): Observable<EntityResponseType> {
        const copy = this.convert(questao);
        return this.http.put<Questao>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<Questao>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Questao[]>> {
        const options = createRequestOption(req);
        return this.http.get<Questao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Questao[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Questao[]>> {
        const options = createRequestOption(req);
        return this.http.get<Questao[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Questao[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Questao = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Questao[]>): HttpResponse<Questao[]> {
        const jsonResponse: Questao[] = res.body;
        const body: Questao[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Questao.
     */
    private convertItemFromServer(questao: Questao): Questao {
        const copy: Questao = Object.assign({}, questao);
        return copy;
    }

    /**
     * Convert a Questao to a JSON which can be sent to the server.
     */
    private convert(questao: Questao): Questao {
        const copy: Questao = Object.assign({}, questao);
        return copy;
    }
}
