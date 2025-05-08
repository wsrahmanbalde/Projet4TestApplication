# Yoga App

## 📦 Technologies utilisées

- **Frontend** : Angular 14
- **Backend** : Spring Boot (Java)
- **Base de données** : MySQL
- **Tests Front-end** : Jest (unitaires, intégration, end-to-end avec Cypress)
- **Tests Back-end** : JUnit (unitaires, intégration), Jacoco (couverture)

---

## 🛠️ Installation

### 📂 Cloner le projet

```bash
git clone https://github.com/ton-utilisateur/yoga-app.git
cd yoga-app
```

---

## 🗃️ Installation de la base de données

> La base de données MySQL est configurée automatiquement par Spring Boot via le fichier `application.yml` ou `application.properties`.

Assure-toi que :
- MySQL est installé et fonctionne.
- Le fichier de configuration Spring contient les bonnes informations :

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yoga
    username: root
    password: root
```

⚠️ Crée la base de données vide `yoga` si elle n’est pas auto-générée :
```sql
CREATE DATABASE yoga;
```

---

## ⚙️ Installation du backend (Spring Boot)

```bash
cd back
mvn clean install
```

---

## 💻 Installation du frontend (Angular)

```bash
cd front
npm install
npm start
```

---

## 🚀 Lancer l'application

### Backend

```bash
cd back
mvn spring-boot:run
```

### Frontend

```bash
cd front
npm start
```

L’application sera disponible sur :  
🔗 `http://localhost:4200`

---

## 🧪 Lancer les tests

### ✅ Tests unitaires et intégration (Front - Jest)

```bash
cd front
npm run test -- --coverage
```

### ✅ Tests end-to-end (Front - Cypress)

```bash
cd front
npm run test:coverage
npm run e2e:coverage
```

### ✅ Tests unitaires et intégration (Back - JUnit)

```bash
cd back
mvn test
```

---

## 📊 Générer les rapports de couverture

### 🧩 Couverture Frontend (Jest)

- Un rapport HTML est généré dans :
```bash
front/coverage/lcov-report/index.html
```

### 🧩 Couverture End-to-End (Cypress)

- Un rapport HTML est généré dans :
```bash
front/coverage/lcov-report/index.html
```

### 🧩 Couverture Backend (Jacoco)

- Un rapport HTML est généré dans :
```bash
back/target/site/jacoco/index.html
```

---

## 🖼️ Captures d’écran

Les captures d’écran des rapports de couverture sont disponibles dans le dossier :

```bash
/docs/screenshots
```

- ✅ Couverture Frontend (Jest)
- ✅ Couverture End-to-End (Cypress)
- ✅ Couverture Backend (Jacoco)

---

## ✅ Vérification finale

Avant soumission, vous pouvez tester les instructions du README :

```bash
git clone https://github.com/ton-utilisateur/yoga-app.git test-readme
cd test-readme
```

Puis reprendre les étapes d’installation.

---

## 👨‍💻 Auteur

BALDE Abdourahamane  
Projet de session – Yoga App  
UQAM – DESS en génie logiciel
