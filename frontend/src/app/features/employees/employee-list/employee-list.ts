import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Employees } from '../../../core/models/employee.model';
import { EmployeeService } from '../../../core/services/employee.service';

@Component({
  selector: 'app-employee-list',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EmployeeList implements OnInit {
  employees: Employees[] = [];
  filtered: Employees[] = [];
  searchTerm: string = '';
  loading: boolean = false;
  error: string | null = null;

  constructor(
    private employeeService: EmployeeService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.loading = true;
    this.cdr.markForCheck();
    this.employeeService.getAllEmployees().subscribe({
      next: (data) => {
        this.employees = data;
        this.filtered = data;
        this.loading = false;
        this.error = null;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error fetching employees:', err);
        this.error = 'Failed to load employees. Please try again later.';
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }
  
  onSearch(): void {
    const term = this.searchTerm.toLowerCase();
    this.filtered = this.employees.filter(emp =>
      emp.firstName.toLowerCase().includes(term) ||
      emp.lastName.toLowerCase().includes(term) ||
      emp.email.toLowerCase().includes(term) ||
      emp.jobTitle.toLowerCase().includes(term) ||
      emp.department.toLowerCase().includes(term)
    );
    this.cdr.markForCheck();
  }

  delete(id: number): void {
    if (confirm('Are you sure you want to delete this employee?')) {
      this.employeeService.delete(id).subscribe({
        next: () => {
          this.loadEmployees();
        },
        error: (err) => {
          console.error('Error deleting employee:', err);
          this.error = 'Failed to delete employee. Please try again later.';
          this.cdr.markForCheck();
        },
      });
    }
  }

  deptClass(department: string): string {
    const map: Record<string, string> = {
      Engineering: 'dept-engineering',
      HR: 'dept-hr',
      Sales: 'dept-sales',
      Marketing: 'dept-marketing',
      Finance: 'dept-finance',
      Operations: 'dept-operations',
    };
    return map[department] || '';
  }
}
