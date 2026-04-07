import { inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class RoleService {
  private readonly STORAGE_KEY = 'appUserRole';
  private readonly isBrowser = isPlatformBrowser(inject(PLATFORM_ID));

  readonly currentRole = signal<string>(
    this.isBrowser ? (localStorage.getItem(this.STORAGE_KEY) ?? 'reader') : 'reader'
  );

  isAdmin(): boolean {
    return this.currentRole() === 'admin';
  }

  setRole(role: string): void {
    if (this.isBrowser) {
      localStorage.setItem(this.STORAGE_KEY, role);
    }
    this.currentRole.set(role);
  }
}
