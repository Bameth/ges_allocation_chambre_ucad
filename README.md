Projet de Gestion de l'Allocation des Chambres à l'Ucad

Ce projet est une application de gestion des chambres pour l'université UCAD, permettant de gérer les étudiants, pavillons et chambres. Il existe deux versions :

Version utilisant les Listes (étape initiale).

Version basée sur une base de données MySQL (étape avancée).

Les deux versions illustrent l'application des principes SOLID et utilisent Lombok pour réduire le code boilerplate.

Fonctionnalités

Gestion des Pavillons :

Ajouter, modifier et lister des pavillons.

Associer des chambres existantes à un pavillon ou créer de nouvelles chambres lors de l'ajout d'un pavillon.

Gestion des Chambres :

Ajouter, modifier, lister et archiver des chambres.

Associer une chambre à un pavillon.

Gestion des Étudiants :

Ajouter des étudiants (étudiants boursiers non logés, boursiers logés et non boursiers) via un formulaire unique.

Affecter une chambre à un étudiant boursier logé.

Consultations :

Lister les chambres d’un pavillon.

Lister les étudiants d'une chambre.

Structure des Données

Étudiants

Matricule

Nom

Prénom

Email

Téléphone

Date de naissance

Type :

Boursiers

Demi-bourse : 20 000 FCFA

Bourse entière : 40 000 FCFA

Non boursiers

Adresse

Logés

Chambre assignée

Chambres

Numéro de chambre

Étage

Type : individuel ou à deux

Pavillons

Numéro de pavillon

Nombre d’étages

Fonctionnalités Techniques

Version avec Listes

Données gérées en mémoire avec des collections Java (é.g., ArrayList).

Requiert une réinitialisation des données à chaque exécution.

Structure simple et idéale pour une approche didactique.

Version avec MySQL

Utilisation d'une base de données relationnelle pour la persistance des données.

ORM : JDBC ou Hibernate pour la gestion des entités.

Schéma de base de données précis avec des relations entre étudiants, chambres et pavillons.

Plus robuste et extensible.

Principes SOLID Appliqués

Single Responsibility Principle (SRP) :

Chaque classe a une responsabilité unique, ég.: Etudiant, Chambre, Pavillon, etc.

Open/Closed Principle (OCP) :

Les classes sont ouvertes à l’extension mais fermées à la modification.

Exemple : Ajout de nouveaux types d’étudiants sans modifier la logique existante.

Liskov Substitution Principle (LSP) :

Les sous-classes (é.g., EtudiantBoursier, EtudiantNonBoursier) peuvent remplacer leur classe mère (Etudiant) sans altérer le comportement du programme.

Interface Segregation Principle (ISP) :

Les interfaces sont divisées en petits groupes fonctionnels (e.g., IPavillonService, IEtudiantService).

Dependency Inversion Principle (DIP) :

Utilisation de dépendances abstraites (ég., interfaces) au lieu de classes concrètes pour une meilleure flexibilité.

Lombok

Utilisation de Lombok pour :

Réduction du code boilerplate (é.g., getters, setters, toString, equals, hashCode).

Annotations : @Data, @Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor.

Prérequis

Java 8+

MySQL

Bibliothèques/Libraries :

Lombok

JDBC (pour la version MySQL)

Maven ou Gradle pour la gestion des dépendances

Installation et Exécution

Version avec Listes

Clonez le dépôt.

Compilez et exécutez le fichier principal (Main.java).

Version avec MySQL

Configurez la base de données avec le fichier schema.sql.

Mettez à jour les informations de connexion (ég., URL, utilisateur, mot de passe) dans le fichier de configuration.

Compilez et exécutez l'application.

Améliorations Futures

Intégration d'une interface graphique (Swing, JavaFX, ou application web avec Spring Boot).

Ajout de tests unitaires et de validation avec JUnit.

Gestion des erreurs et des exceptions pour une application plus robuste.

Auteur :

Projet réalisé en 2e année de licence.
