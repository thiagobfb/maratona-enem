import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { QuestaoComponent } from './questao.component';
import { QuestaoDetailComponent } from './questao-detail.component';
import { QuestaoPopupComponent } from './questao-dialog.component';
import { QuestaoDeletePopupComponent } from './questao-delete-dialog.component';

export const questaoRoute: Routes = [
    {
        path: 'questao',
        component: QuestaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questaos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'questao/:id',
        component: QuestaoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questaos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questaoPopupRoute: Routes = [
    {
        path: 'questao-new',
        component: QuestaoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'questao/:id/edit',
        component: QuestaoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'questao/:id/delete',
        component: QuestaoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Questaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
