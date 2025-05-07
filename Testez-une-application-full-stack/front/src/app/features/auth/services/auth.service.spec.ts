import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { expect } from '@jest/globals';

describe('AuthService', () => {
    let service: AuthService;
    let httpMock: HttpTestingController;
  
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [AuthService],
      });
      service = TestBed.inject(AuthService);
      httpMock = TestBed.inject(HttpTestingController);
    });
  
    afterEach(() => {
      httpMock.verify();
    });
  
    it('should be created', () => {
      expect(service).toBeTruthy();
    });
  
    it('should send a POST request on register and handle void response', () => {
      const mockRegisterData: RegisterRequest = {
        email: 'test@example.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'password123'
      };
  
      service.register(mockRegisterData).subscribe(response => {
        expect(response).toBeUndefined(); // No response body, so we expect undefined
      });
  
      const req = httpMock.expectOne('api/auth/register');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(mockRegisterData);
      req.flush(null); // Simulate a void response with null (should match Observable<void>)
    });
  
    it('should send a POST request on login and return session information', () => {
      const mockLoginData: LoginRequest = {
        email: 'test@example.com',
        password: 'password123'
      };
  
      const mockResponse: SessionInformation = {
        token: 'fake-jwt-token',
        type: 'Bearer',
        id: 1,
        username: 'john.doe',
        firstName: 'John',
        lastName: 'Doe',
        admin: false
      };
  
      service.login(mockLoginData).subscribe(response => {
        expect(response).toEqual(mockResponse); // Check if the response matches the mock session information
      });
  
      const req = httpMock.expectOne('api/auth/login');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(mockLoginData);
      req.flush(mockResponse); // Simulate a successful response with the mock session data
    });
  });