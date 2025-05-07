import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { expect } from '@jest/globals';

describe('RegisterComponent (Integration)', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceMock: any;
  let routerMock: any;

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

  it('should submit form and navigate on success', fakeAsync(() => {
    // Remplir le formulaire
    component.form.setValue({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    });

    authServiceMock.register.mockReturnValue(of(void 0)); // simulate success

    component.submit();
    tick();

    expect(authServiceMock.register).toHaveBeenCalledWith({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    });
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
    expect(component.onError).toBe(false);
  }));

  it('should set onError to true on error', fakeAsync(() => {
    component.form.setValue({
      email: 'test@example.com',
      firstName: 'Jane',
      lastName: 'Smith',
      password: 'pwd456'
    });

    authServiceMock.register.mockReturnValue(throwError(() => new Error('Erreur serveur')));

    component.submit();
    tick();

    expect(authServiceMock.register).toHaveBeenCalled();
    expect(component.onError).toBe(true);
  }));
});