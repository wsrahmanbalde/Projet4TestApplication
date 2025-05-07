import { expect } from '@jest/globals';

import { TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';

describe('AuthService Integration Test', () => {
  let authService: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Remplace HttpClientModule par HttpClientTestingModule pour les tests
      providers: [AuthService]
    });

    authService = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Vérifie qu'aucune requête HTTP n'a échoué ou reste en attente
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(authService).toBeTruthy();
  });

  describe('login', () => {
    it('should make an HTTP POST request and return session information', () => {
      const mockSessionInformation: SessionInformation = {
        token: 'fake-jwt-token',
        type: 'Bearer',
        id: 1,
        username: 'johndoe@example.com',
        firstName: 'John',
        lastName: 'Doe',
        admin: true
      };

      const loginRequest: LoginRequest = {
        email: 'johndoe@example.com',
        password: 'password123'
      };

      // Appel à la méthode login du service
      authService.login(loginRequest).subscribe((sessionInformation) => {
        // Vérification que la réponse reçue est correcte
        expect(sessionInformation).toEqual(mockSessionInformation);
      });

      // On s'assure que l'appel HTTP a bien été fait avec la méthode POST vers l'URL correcte
      const req = httpTestingController.expectOne('api/auth/login');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(loginRequest);  // Vérifie que la requête contient bien les bonnes données

      // Simuler la réponse de l'API avec le mock de session
      req.flush(mockSessionInformation);  // Simule la réponse reçue de l'API

      // Vérifie qu'il n'y a pas d'autres appels HTTP en attente
      httpTestingController.verify();
    });
  });
});