// cypress.config.ts

import { defineConfig } from "cypress";
const codeCoverageTask = require("@cypress/code-coverage/task");

export default defineConfig({
  projectId: "bwg4jz",
  videosFolder: "cypress/videos",
  screenshotsFolder: "cypress/screenshots",
  fixturesFolder: "cypress/fixtures",
  video: false,

  e2e: {
    baseUrl: "http://localhost:4200",
    setupNodeEvents(on, config) {
      codeCoverageTask(on, config);
      return config;
    },
  },

  component: {
    devServer: {
      framework: "angular",
      bundler: "webpack",
    },
    specPattern: "**/*.cy.ts",
  },
});
