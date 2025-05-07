describe('Suppression d’une session avec un utilisateur admin simulé', () => {
  const sessionId = 1;
  const sessionName = 'Session de test E2E';

  beforeEach(() => {
    // Mock utilisateur admin
    cy.intercept('GET', '/me', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'yoga@studio.com',
        firstName: 'Admin',
        lastName: 'ADMIN',
        isAdmin: true
      }
    }).as('getUserMe');

    // Mock liste des sessions
    cy.intercept('GET', '/api/sessions', [
      {
        id: sessionId,
        name: sessionName,
        date: '2025-05-01',
        description: 'Ancienne description',
        teacher: { id: 1, name: 'John Doe' }
      }
    ]).as('getSessions');

    // Mock de la session individuelle (pour la page de détail)
    cy.intercept('GET', `/api/sessions/${sessionId}`, {
      id: sessionId,
      name: sessionName,
      date: '2025-05-01',
      description: 'Ancienne description',
      teacher: { id: 1, name: 'John Doe' },
      participants: []
    }).as('getSessionDetail');

    // Mock suppression de session
    cy.intercept('DELETE', `/api/sessions/${sessionId}`, {
      statusCode: 204
    }).as('deleteSession');

    // Connexion
    cy.visit('/login');
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234');
    cy.get('button[type=submit]').click();

    cy.url().should('include', '/sessions');
  });

  it('supprime une session existante', () => {
    // Sélectionner la carte contenant le nom de la session
    cy.get('mat-card').contains('mat-card-title', sessionName)
      .parents('mat-card')
      .first() // ⬅ garantit un seul mat-card
      .within(() => {
        // Cliquer sur "Detail"
        cy.contains('Detail').click();
      });

    // Vérifier qu'on est bien redirigé vers la page de détail
    cy.url().should('match', /\/sessions\/detail\/\d+$/);

    // Cliquer sur "Delete"
    cy.contains('Delete').click();



    // Vérifier la redirection vers la page des sessions
    cy.url().should('include', '/sessions');

  });
});