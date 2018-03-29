import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Escola } from './escola.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Escola>;

@Injectable()
export class EscolaService {

    private resourceUrl =  SERVER_API_URL + 'api/escolas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/escolas';

    constructor(private http: HttpClient) { }

    create(escola: Escola): Observable<EntityResponseType> {
        const copy = this.convert(escola);
        return this.http.post<Escola>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(escola: Escola): Observable<EntityResponseType> {
        const copy = this.convert(escola);
        return this.http.put<Escola>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<Escola>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Escola[]>> {
        const options = createRequestOption(req);
        return this.http.get<Escola[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Escola[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Escola[]>> {
        const options = createRequestOption(req);
        return this.http.get<Escola[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Escola[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Escola = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Escola[]>): HttpResponse<Escola[]> {
        const jsonResponse: Escola[] = res.body;
        const body: Escola[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Escola.
     */
    private convertItemFromServer(escola: Escola): Escola {
        const copy: Escola = Object.assign({}, escola);
        return copy;
    }

    /**
     * Convert a Escola to a JSON which can be sent to the server.
     */
    private convert(escola: Escola): Escola {
        const copy: Escola = Object.assign({}, escola);
        return copy;
    }
}
