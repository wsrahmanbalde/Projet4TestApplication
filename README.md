# 🧹 Projet Full Stack Angular / Spring Boot

Ce projet est une application web complète développée avec Angular (frontend) et Spring Boot (backend). Il utilise une base de données MySQL. Des tests unitaires, d’intégration et end-to-end sont mis en place pour garantir la qualité du code.

---

## 📦 Technologies utilisées

* **Frontend** : Angular
* **Backend** : Spring Boot (Java, Maven)
* **Base de données** : MySQL
* **Tests Frontend** : Jest (unitaires, intégration, end-to-end)
* **Tests Backend** : JUnit + Jacoco (unitaires, intégration)

---

## 🛠️ Installation

### 🔗 Prérequis

* [Node.js](https://nodejs.org/)
* [Angular CLI](https://angular.io/cli)
* [Java 17+](https://adoptium.net/)
* [Maven 3.9+](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/)

---

## 📔️ Base de données

La base de données est initialisée automatiquement par Spring Boot via la configuration `application.yml`.

### Par défaut :

* **Nom de la base** : `test`
* **Utilisateur** : `root`
* **Mot de passe** : `root`

Assurez-vous que les informations sont correctes dans le fichier `src/main/resources/application.yml`.

---

## ⚙️ Installation du backend

```bash
cd backend/
mvn clean install
mvn spring-boot:run
```

---

## 💻 Installation du frontend

```bash
cd frontend/
npm install
ng serve
```

L'application sera accessible à [http://localhost:4200](http://localhost:4200)

---

## ✅ Lancer les tests

### 🔹 Tests Frontend (Jest)

#### ➔ Tests unitaires & d’intégration :

```bash
npm run test
```

#### ➔ Tests end-to-end :

```bash
npm run test:e2e
```

> Assurez-vous que l'application est lancée avant d’exécuter les tests E2E.

---

## 📊 Rapports de couverture (Frontend)

```bash
npm run test -- --coverage
```

Les rapports seront générés dans le dossier `coverage/`.

Ouvrir `coverage/index.html` dans un navigateur.

---

## 🔺 Tests Backend (JUnit + Jacoco)

```bash
mvn clean test
mvn jacoco:report
```

Le rapport de couverture sera généré dans :
`target/site/jacoco/index.html`

---

## 📁 Structure du projet

```
├── backend/
│   ├── src/
│   └── pom.xml
├── frontend/
│   ├── src/
│   └── angular.json
└── README.md
```

---

## 📌 Bonnes pratiques

* Cloner le projet dans un nouveau dossier pour tester toutes les instructions du README.
* Vérifier les versions de Java, Node.js et MySQL.
* Adapter les ports ou chemins si nécessaire.

---

## ✉️ Auteur

Ce projet a été développé dans le cadre d’une formation.
N'hésitez pas à me contacter pour toute question ou suggestion.
