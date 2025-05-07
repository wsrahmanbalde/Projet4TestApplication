import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { expect } from '@jest/globals';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(() => {
    authService = { login: jest.fn() } as any;
    sessionService = { logIn: jest.fn() } as any;
    router = { navigate: jest.fn() } as any;

    component = new LoginComponent(authService, new FormBuilder(), router, sessionService);
  });

  it('should call login and navigate on success', () => {
    // Setup mock response for the login service
    const mockResponse = { token: 'fakeToken' };
    authService.login = jest.fn().mockReturnValue(of(mockResponse));

    // Spy on navigate and logIn methods
    const routerSpy = jest.spyOn(router, 'navigate');
    const sessionServiceSpy = jest.spyOn(sessionService, 'logIn');

    // Simulate form submission
    component.form.setValue({ email: 'user@example.com', password: 'password123' });
    component.submit();

    // Assertions
    expect(authService.login).toHaveBeenCalledWith({
      email: 'user@example.com',
      password: 'password123',
    });

    expect(sessionServiceSpy).toHaveBeenCalledWith(mockResponse);

    expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
  });

  it('should set error flag on login failure', () => {
    // Setup mock failure for the login service
    authService.login = jest.fn().mockReturnValue(throwError('Login failed'));

    // Spy on navigate and logIn methods
    const routerSpy = jest.spyOn(router, 'navigate');
    const sessionServiceSpy = jest.spyOn(sessionService, 'logIn');

    // Simulate form submission
    component.form.setValue({ email: 'user@example.com', password: 'password123' });
    component.submit();

    // Assertions
    expect(authService.login).toHaveBeenCalledWith({
      email: 'user@example.com',
      password: 'password123',
    });

    // Check if sessionService and router are not called in case of error
    expect(sessionServiceSpy).not.toHaveBeenCalled();
    expect(routerSpy).not.toHaveBeenCalled();

    // Ensure the error flag is set
    expect(component.onError).toBe(true);
  });
});