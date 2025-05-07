import { defineConfig } from 'cypress';
import codeCoverage from '@cypress/code-coverage/task';

export default defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      codeCoverage(on, config); // ✅ Appelle correctement la fonction
      return config;
    },
  },
});