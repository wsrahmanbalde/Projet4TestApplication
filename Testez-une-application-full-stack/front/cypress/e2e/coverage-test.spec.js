// cypress/e2e/coverage-test.spec.js
describe('Test Angular Code Coverage', () => {
    it('should load the application', () => {
      cy.visit('/'); // Visite la page d'accueil de ton application
      cy.get('app-root').should('exist'); // Vérifie si l'élément racine Angular existe
    });
  });