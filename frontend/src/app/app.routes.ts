import { Routes } from '@angular/router';
import { EmployeeList } from './features/employees/employee-list/employee-list';
import { EmployeeForm } from './features/employees/employee-form/employee-form';
import { Login } from './features/auth/login/login';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
    {
        path: 'login',
        component: Login
    },
    {
        path: '',
        component: EmployeeList
    },
    {
        path: 'add',
        component: EmployeeForm,
        canActivate: [adminGuard]
    },
    {
        path: 'edit/:id',
        component: EmployeeForm,
        canActivate: [adminGuard]
    },
    {
        path: '**',
        redirectTo: ''
    }
];
