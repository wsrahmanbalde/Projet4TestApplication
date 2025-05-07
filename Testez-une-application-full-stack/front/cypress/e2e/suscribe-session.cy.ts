describe('Création de session par un admin puis inscription par un utilisateur', () => {
    const sessionName = 'Session de test E2E';
  
    it('devrait créer une session avec l’admin puis permettre à un utilisateur de s’y inscrire', () => {
      // --- Étape 1 : Mock utilisateur admin ---
      cy.intercept('GET', '/me', {
        statusCode: 200,
        body: {
          id: 1,
          username: 'yoga@studio.com',
          firstName: 'Admin',
          lastName: 'ADMIN',
          isAdmin: true
        }
      }).as('getAdminMe');
  
      // Connexion admin
      cy.visit('/login');
      cy.get('input[formControlName=email]').type('yoga@studio.com');
      cy.get('input[formControlName=password]').type('test!1234');
      cy.get('button[type=submit]').click();
  
      cy.url().should('include', '/sessions');
      cy.contains('Create').click();
      cy.url().should('include', '/sessions/create');
  
      // Remplissage du formulaire
      cy.get('input[formControlName=name]').type(sessionName);
      cy.get('input[formControlName=date]').type('2025-06-15');
      cy.get('mat-select[formControlName=teacher_id]').click();
      cy.get('mat-option').contains('John Doe').click();
      cy.get('textarea[formControlName=description]').type('Session pour test E2E');
  
      // Soumission
      cy.get('button[type=submit]').click();
      cy.contains('Session created !').should('exist');
      cy.url().should('include', '/sessions');
  
      // --- Étape 1.5 : Déconnexion de l'admin ---
      cy.contains('Logout').should('be.visible').click();
  
      // --- Étape 2 : Connexion utilisateur simple (mock) ---
  
      // Préparation des mocks AVANT d'aller sur /login
      cy.intercept('GET', '/me', {
        statusCode: 200,
        body: {
          id: 2,
          username: 'user@studio.com',
          firstName: 'User',
          lastName: 'SIMPLE',
          isAdmin: false
        }
      }).as('getUserMe');
  
      cy.intercept('POST', '/api/auth/login', {
        statusCode: 200,
        body: { token: 'fake-jwt-token' }
      }).as('loginUser');
  
      cy.intercept('GET', '/api/sessions', {
        statusCode: 200,
        body: [
          {
            id: 1,
            name: sessionName,
            date: '2025-06-15',
            teacher: 'John Doe',
            description: 'Session pour test E2E'
          }
        ]
      }).as('getSessions');
  
      // Revenir à /login et se connecter avec utilisateur simple
      cy.visit('/login');
      cy.get('input[formControlName=email]').type('user@studio.com');
      cy.get('input[formControlName=password]').type('test!1234');
      cy.get('button[type=submit]').click();
  
      // Vérifications
      cy.url().should('include', '/sessions');
    });
  });