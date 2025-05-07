import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { of ,throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceMock: { register: jest.Mock };
  let routerMock: { navigate: jest.Mock };

  beforeEach(async () => {
    authServiceMock = {
      register: jest.fn()
    };

    routerMock = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call register and navigate on success', () => {
    component.form.setValue({
      email: 'john@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'securePass123'
    });

    authServiceMock.register.mockReturnValue(of(undefined)); // simulate void return

    component.submit();

    expect(authServiceMock.register).toHaveBeenCalledWith({
      email: 'john@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'securePass123'
    });

    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
    expect(component.onError).toBe(false);
  });

  it('should set onError to true on failure', () => {
    component.form.setValue({
      email: 'john@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'securePass123'
    });

    authServiceMock.register.mockReturnValue(throwError(() => new Error('error')));

    component.submit();

    expect(component.onError).toBe(true);
  });
});