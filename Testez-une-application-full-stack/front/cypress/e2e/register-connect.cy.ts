describe('Inscription et connexion d’un nouvel utilisateur (mocked)', () => {
  const firstName = 'Test';
  const lastName = 'User';
  const email = `testuser${Date.now()}@example.com`;
  const password = 'Test@1234';

  it('devrait s’inscrire puis se connecter avec succès', () => {
    // Mock de l’inscription
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 201,
      body: {
        message: 'User registered successfully',
      },
    }).as('registerRequest');

    // Mock de la connexion
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        token: 'fake-jwt-token',
        user: {
          id: 2,
          email,
          firstName,
          lastName,
        },
      },
    }).as('loginRequest');

    // Étape 1 : Inscription
    cy.visit('/register');

    cy.get('input[formControlName=firstName]').type(firstName);
    cy.get('input[formControlName=lastName]').type(lastName);
    cy.get('input[formControlName=email]').type(email);
    cy.get('input[formControlName=password]').type(password);
    cy.get('button[type=submit]').click();

    // Attendre que la requête d’inscription soit bien envoyée
    cy.wait('@registerRequest');

    // Vérifier la redirection vers /login
    cy.url().should('include', '/login');

    // Étape 2 : Connexion
    cy.get('input[formControlName=email]').type(email);
    cy.get('input[formControlName=password]').type(password);
    cy.get('button[type=submit]').click();

    // Attendre la requête de login
    cy.wait('@loginRequest');

    // Vérifier redirection et présence de contenu
    cy.url().should('include', '/sessions');
    cy.contains('Rentals available').should('be.visible');
  });
});