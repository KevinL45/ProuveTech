<p align="center">
  <img src="frontend/src/assets/logo/logo.png" alt="ProuveTech" width="120" />
</p>

<h1 align="center">ProuveTech</h1>

<p align="center">
  <em>La plateforme où les développeurs prouvent leurs compétences par la vidéo, plutôt que par un CV.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Angular-20-DD0031?logo=angular&logoColor=white" alt="Angular 20" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 3.4" />
  <img src="https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/statut-archivé-lightgrey" alt="Statut : archivé" />
</p>

---

## 📖 Le projet

**ProuveTech** part d'un constat simple : un CV ne montre pas ce qu'un développeur sait vraiment faire. Sur la plateforme, chaque développeur présente ses projets et les accompagne de **démos vidéo**. Les recruteurs et les autres acteurs de l'IT voient directement le travail réalisé.

Je l'ai développé seul pendant **près de deux ans** et mis en production sur `prouvetech.fr` (frontend sur Vercel, API sur un hébergement dédié). Le projet est aujourd'hui **arrêté**. Je le publie en open source pour que le code et ce qu'il m'a appris servent à d'autres.

## 🎬 Démo

▶️ [Voir la vidéo de présentation de la plateforme](frontend/src/assets/videos/plateforme_prouvetech.mp4)

## ✨ Fonctionnalités

- **Authentification** : inscription, connexion par **JWT**, gestion des rôles (utilisateur / administrateur)
- **Profil développeur** : profil public consultable, modifiable, avec avatar
- **Projets** : création, modification et suppression ; liste « mes projets » et consultation des projets des autres membres
- **Démos vidéo** : upload de vidéos rattachées aux projets, avec vérification du type réel des fichiers (Apache Tika)
- **Technologies** : tags d'outils et de langages avec recherche et ajout à la volée
- **Acteurs IT** : annuaire des membres de la plateforme
- **Administration** : espace réservé aux administrateurs
- **E-mails** : mail de bienvenue envoyé à l'inscription (SMTP)
- **Pages légales** : conditions générales d'utilisation

## 🛠️ Stack technique

| Couche | Technologies |
|---|---|
| **Frontend** | Angular 20, Bootstrap 5, ng-select, Zod |
| **Backend** | Spring Boot 3.4 (Web, Data JPA, Security, Mail), Java 21 |
| **Sécurité** | Spring Security, JWT (jjwt 0.11.5) |
| **Base de données** | MySQL, Hibernate |
| **Fichiers** | Apache Tika 2.9 (détection du type MIME réel) |
| **Configuration** | spring-dotenv (variables dans un `.env`) |
| **Déploiement** | Vercel (frontend), hébergement mutualisé (API + MySQL) |

## 🏗️ Architecture

```
┌──────────────────────┐   HTTPS / JSON + JWT   ┌──────────────────────┐        ┌─────────┐
│  Frontend Angular    │ ─────────────────────▶ │  API Spring Boot     │ ─────▶ │  MySQL  │
│  (SPA, Vercel)       │                        │  (REST, port 8080)   │        └─────────┘
└──────────────────────┘                        └──────────┬───────────┘
                                                           │
                                                           ├─▶ Stockage des fichiers (uploads/)
                                                           └─▶ Serveur SMTP (e-mails)
```

```
ProuveTech/
├── frontend/          # Application Angular (pages, formulaires, services HTTP)
│   └── src/app/
│       ├── pages/     # Accueil, profil, projets, démos, admin, CGU…
│       ├── form/      # Formulaires (inscription, connexion, projet, démo)
│       └── layout/    # Navbar, footer, layout principal
└── prouvetech/        # API Spring Boot
    └── src/main/java/com/prouvetech/prouvetech/
        ├── controller/  # Endpoints REST : /user, /project, /demo, /tool, /status
        ├── service/     # Logique métier
        ├── repository/  # Accès aux données (Spring Data JPA)
        ├── model/       # Entités : User, Project, Demo, Tool, Role, Status
        └── config/      # Spring Security, CORS, filtre JWT
```

## 🚀 Lancer le projet en local

### Prérequis

- Java 21
- Node.js 20+ et npm
- MySQL 8

### 1. Base de données

Crée une base vide :

```sql
CREATE DATABASE prouvetech;
```

> ℹ️ `application.properties` est configuré en `spring.jpa.hibernate.ddl-auto=validate`, qui exige que les tables existent déjà. Au **premier lancement**, passe temporairement cette valeur à `update` pour que Hibernate crée le schéma, puis remets `validate`.

### 2. Backend

```bash
cd prouvetech
cp .env.example .env      # puis remplis les valeurs
./mvnw spring-boot:run    # sous Windows : mvnw.cmd spring-boot:run
```

L'API démarre sur `http://localhost:8080`.

Variables attendues dans `.env` :

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | URL JDBC, ex. `jdbc:mysql://localhost:3306/prouvetech` |
| `SPRING_DATASOURCE_USERNAME` / `SPRING_DATASOURCE_PASSWORD` | Identifiants MySQL |
| `JWT_SECRET_KEY` | Clé secrète en Base64 (générable avec `utils/SecretKeyGenerator`) |
| `MAIL_HOST` / `MAIL_PORT` / `MAIL_USERNAME` / `MAIL_PASSWORD` | Serveur SMTP pour les e-mails |

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

L'application est disponible sur `http://localhost:4200` et appelle l'API définie dans `src/environments/environment.ts`.

## 💡 Ce que ce projet m'a appris

**Côté technique**
- Construire une application **full-stack complète** de bout en bout, de la base de données jusqu'à la mise en production.
- Sécuriser une API REST avec **Spring Security et JWT**, gérer les rôles et le CORS entre deux domaines.
- Gérer l'**upload de fichiers lourds** (vidéos) et ne pas faire confiance à l'extension : vérifier le vrai type du fichier.
- Déployer une SPA Angular 20 sur Vercel, ce qui demande quelques pièges à connaître (preset « Other », rendu côté client, réécritures vers `/`).
- Garder les secrets hors du code et **hors de l'historique git**.

**Côté produit**
- Un bon produit techniquement ne suffit pas : sans utilisateurs, une plateforme ne vit pas. La recherche d'utilisateurs et la diffusion méritent autant de temps que le code, et le plus tôt possible.
- Savoir arrêter un projet fait aussi partie du travail.

## 📄 Licence

Ce projet est publié sous licence [MIT](https://opensource.org/licenses/MIT). Tu peux librement le lire, le réutiliser et t'en inspirer.

## 👤 Auteur

**Kevin Lokoka** · [GitHub @KevinL45](https://github.com/KevinL45)
