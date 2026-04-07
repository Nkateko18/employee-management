import { Routes } from '@angular/router';
import { EmployeeList } from './features/employees/employee-list/employee-list';
import { EmployeeForm } from './features/employees/employee-form/employee-form';

export const routes: Routes = [
    {
        path: '',
        component: EmployeeList
    },
    {
        path: 'add',
        component: EmployeeForm
    },
    {
        path: 'edit/:id',
        component: EmployeeForm
    },
    {
        path: '**',
        redirectTo: ''
    }
];
