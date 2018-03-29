import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EscolaComponent } from './escola.component';
import { EscolaDetailComponent } from './escola-detail.component';
import { EscolaPopupComponent } from './escola-dialog.component';
import { EscolaDeletePopupComponent } from './escola-delete-dialog.component';

export const escolaRoute: Routes = [
    {
        path: 'escola',
        component: EscolaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'escola/:id',
        component: EscolaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const escolaPopupRoute: Routes = [
    {
        path: 'escola-new',
        component: EscolaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'escola/:id/edit',
        component: EscolaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'escola/:id/delete',
        component: EscolaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Escolas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
