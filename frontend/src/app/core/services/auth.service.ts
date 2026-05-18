import { inject, Injectable, PLATFORM_ID, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';

export interface LoginResponse {
  token: string;
  username: string;
  roles: string[];
}

const TOKEN_KEY = 'auth_token';
const USER_KEY = 'auth_user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly isBrowser = isPlatformBrowser(inject(PLATFORM_ID));

  private readonly _token = signal<string | null>(this.loadToken());
  private readonly _user = signal<LoginResponse | null>(this.loadUser());

  readonly isLoggedIn = computed(() => !!this._token());
  readonly isAdmin = computed(() =>
    this._user()?.roles.includes('ROLE_ADMIN') ?? false
  );
  readonly currentUser = computed(() => this._user());

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>('/api/auth/login', { username, password }).pipe(
      tap(response => {
        this._token.set(response.token);
        this._user.set(response);
        if (this.isBrowser) {
          localStorage.setItem(TOKEN_KEY, response.token);
          localStorage.setItem(USER_KEY, JSON.stringify(response));
        }
      })
    );
  }

  logout(): void {
    this._token.set(null);
    this._user.set(null);
    if (this.isBrowser) {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
    }
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this._token();
  }

  private loadToken(): string | null {
    if (typeof localStorage === 'undefined') return null;
    return localStorage.getItem(TOKEN_KEY);
  }

  private loadUser(): LoginResponse | null {
    if (typeof localStorage === 'undefined') return null;
    const raw = localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  }
}
