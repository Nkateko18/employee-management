import { Component, signal, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { RoleService } from './core/services/role.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive, TitleCasePipe],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
  protected readonly roleService = inject(RoleService);
  protected readonly roles = ['admin', 'manager', 'reader'];
}
