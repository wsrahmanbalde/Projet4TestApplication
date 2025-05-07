describe('Login Page', () => {
  it('should log in successfully with valid credentials (mocked)', () => {
    // 👇 On intercepte l'appel POST vers /api/auth/login
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        token: 'fake-jwt-token',
        user: {
          id: 1,
          email: 'yoga@studio.com',
          name: 'Test User',
        },
      },
    }).as('loginRequest'); // 👈 On nomme cette interception

    cy.visit('/login');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234');
    cy.get('button[type=submit]').click();

    // 👇 On attend que la requête interceptée soit envoyée
    cy.wait('@loginRequest');

    // 👇 On vérifie la redirection et la présence du texte
    cy.url().should('include', '/sessions');
    cy.contains('Sessions').should('exist');
  });
});