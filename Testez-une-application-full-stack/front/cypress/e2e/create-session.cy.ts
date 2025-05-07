describe('Création de session avec un utilisateur admin simulé', () => {
  beforeEach(() => {
    // Mock de la réponse pour simuler un utilisateur admin
    cy.intercept('GET', '/me', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'yoga@studio.com',
        firstName: 'Admin',
        lastName: 'ADMIN',
        isAdmin: true // L'utilisateur est un administrateur
      }
    }).as('getUserMe');

    // Connexion avec un utilisateur
    cy.visit('/login');
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234');
    cy.get('button[type=submit]').click();

    // Attente de la réponse de /api/user/me pour garantir que l'utilisateur est admin
    //cy.wait('@getUserMe');
    
    // Vérifie que l'utilisateur est redirigé vers la page des sessions
    cy.url().should('include', '/sessions');
    cy.contains('Create').click(); // Clique sur le bouton "Create" pour créer une session
    cy.url().should('include', '/sessions/create'); // Vérifie la page de création
  });

  it('devrait remplir le formulaire et créer une session', () => {
    // Remplir le formulaire
    cy.get('input[formControlName=name]')
      .should('be.visible')
      .type('Session de test E2E');

    const isoDate = '2025-05-02'; // Format attendu par input type="date"
    cy.get('input[formControlName=date]')
      .should('be.visible')
      .type(isoDate);

    // Sélectionner le professeur "John Doe"
    cy.get('mat-select[formControlName=teacher_id]')
      .should('be.visible')
      .click(); // Ouvre le dropdown
    cy.get('mat-option')
      .contains('John Doe')
      .click(); // Sélectionne l'option du professeur

    // Saisir une description
    cy.get('textarea[formControlName=description]')
      .should('be.visible')
      .type('Description test E2E');

    // Soumettre le formulaire
    cy.get('button[type=submit]').click();

    // Vérification du message de succès
    cy.contains('Session created !').should('exist');
    cy.url().should('include', '/sessions'); // Vérifie que l'utilisateur est redirigé vers la page des sessions
  });
});