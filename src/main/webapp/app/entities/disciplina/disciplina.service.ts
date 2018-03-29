import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Disciplina } from './disciplina.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Disciplina>;

@Injectable()
export class DisciplinaService {

    private resourceUrl =  SERVER_API_URL + 'api/disciplinas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/disciplinas';

    constructor(private http: HttpClient) { }

    create(disciplina: Disciplina): Observable<EntityResponseType> {
        const copy = this.convert(disciplina);
        return this.http.post<Disciplina>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(disciplina: Disciplina): Observable<EntityResponseType> {
        const copy = this.convert(disciplina);
        return this.http.put<Disciplina>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<Disciplina>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Disciplina[]>> {
        const options = createRequestOption(req);
        return this.http.get<Disciplina[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Disciplina[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Disciplina[]>> {
        const options = createRequestOption(req);
        return this.http.get<Disciplina[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Disciplina[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Disciplina = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Disciplina[]>): HttpResponse<Disciplina[]> {
        const jsonResponse: Disciplina[] = res.body;
        const body: Disciplina[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Disciplina.
     */
    private convertItemFromServer(disciplina: Disciplina): Disciplina {
        const copy: Disciplina = Object.assign({}, disciplina);
        return copy;
    }

    /**
     * Convert a Disciplina to a JSON which can be sent to the server.
     */
    private convert(disciplina: Disciplina): Disciplina {
        const copy: Disciplina = Object.assign({}, disciplina);
        return copy;
    }
}
