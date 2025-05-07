import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { expect } from '@jest/globals';

describe('AuthRoutingModule', () => {
  let router: Router;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([]), AuthRoutingModule],
      declarations: [LoginComponent, RegisterComponent]
    }).compileComponents();

    router = TestBed.inject(Router);
    location = TestBed.inject(Location);

    router.initialNavigation();
  });

  it('should navigate to /login and load LoginComponent', fakeAsync(() => {
    router.navigate(['/login']);
    tick();
    expect(location.path()).toBe('/login');
  }));

  it('should navigate to /register and load RegisterComponent', fakeAsync(() => {
    router.navigate(['/register']);
    tick();
    expect(location.path()).toBe('/register');
  }));
});