import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { EmployeeService } from '../../../core/services/employee.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-employee-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './employee-form.html',
  styleUrl: './employee-form.css',
})
export class EmployeeForm {
  form!: FormGroup;
  isEdit = false;
  employeeId?: number;
  saving = false;

  departments = ['Engineering', 'HR', 'Finance', 'Operations'];

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName:  ['', [Validators.required, Validators.minLength(2)]],
      lastName:   ['', [Validators.required, Validators.minLength(2)]],
      email:      ['', [Validators.required, Validators.email]],
      department: ['', Validators.required],
      jobTitle:   ['', Validators.required],
      hireDate:   ['', Validators.required]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.employeeId = Number(id);
      this.employeeService.getEmployeeById(this.employeeId).subscribe(emp => this.form.patchValue(emp));
    }
  }

  get f() { return this.form.controls; }

  onSubmit(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.saving = true;

    const action = this.isEdit
      ? this.employeeService.update(this.employeeId!, this.form.value)
      : this.employeeService.create(this.form.value);

    action.subscribe({
      next: () => this.router.navigate(['/']),
      error: () => { alert('Failed to save. Please try again.'); this.saving = false; }
    });
  }

  cancel(): void { this.router.navigate(['/']); }
}
