describe('Édition d’une session avec un utilisateur admin simulé', () => {
  const sessionId = 1;
  const sessionName = 'Session de test E2E';
  const nouveauNom = 'Session mise à jour';
  const nouvelleDate = '2025-06-10';
  const nouvelleDescription = 'Nouvelle description mise à jour';

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

    // Mock enseignants
    cy.intercept('GET', '/api/teachers', [
      { id: 1, name: 'John Doe' },
      { id: 2, name: 'Jane Smith' }
    ]).as('getTeachers');

    // Mock update session
    cy.intercept('PUT', `/api/sessions/${sessionId}`, {
      statusCode: 200,
      body: {
        id: sessionId,
        name: nouveauNom,
        date: nouvelleDate,
        description: nouvelleDescription,
        teacher: { id: 1, name: 'John Doe' }
      }
    }).as('updateSession');

    // Connexion
    cy.visit('/login');
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234');
    cy.get('button[type=submit]').click();

    cy.url().should('include', '/sessions');
  });

  it('modifie une session existante', () => {
    // Sélectionne la première carte contenant le nom exact de la session
    cy.get('mat-card').contains('mat-card-title', sessionName)
      .parents('mat-card')
      .first() // ⬅ garantit un seul mat-card
      .within(() => {
        cy.contains('Edit').click();
      });

    // Vérifie qu'on est bien redirigé sur la page d'édition
    cy.url().should('match', /\/sessions\/update\/\d+$/);

    // Mise à jour du formulaire
    cy.get('input[formControlName=name]').clear().type(nouveauNom);
    cy.get('input[formControlName=date]').clear().type(nouvelleDate);
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('John Doe').click();
    cy.get('textarea[formControlName=description]').clear().type(nouvelleDescription);

    // Soumettre le formulaire
    cy.get('button[type=submit]').click();

    // Vérifie qu'on est redirigé vers la page des sessions et que le nouveau nom apparaît
    cy.url().should('include', '/sessions');
    cy.contains('mat-card-title', nouveauNom).should('be.visible');
  });
});