import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DisciplinaComponent } from './disciplina.component';
import { DisciplinaDetailComponent } from './disciplina-detail.component';
import { DisciplinaPopupComponent } from './disciplina-dialog.component';
import { DisciplinaDeletePopupComponent } from './disciplina-delete-dialog.component';

export const disciplinaRoute: Routes = [
    {
        path: 'disciplina',
        component: DisciplinaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Disciplinas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'disciplina/:id',
        component: DisciplinaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Disciplinas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const disciplinaPopupRoute: Routes = [
    {
        path: 'disciplina-new',
        component: DisciplinaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Disciplinas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disciplina/:id/edit',
        component: DisciplinaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Disciplinas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disciplina/:id/delete',
        component: DisciplinaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Disciplinas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
