🏨 Projet : Gestion de l'Allocation des Chambres à l'UCAD

✍️ Description du Projet

Ce projet, réalisé en console en ☕ Java, vise à gérer l'allocation des 🛏️ chambres à l'Université Cheikh Anta Diop de Dakar (UCAD). Deux versions du projet ont été développées :

📋 Version basée sur les 📜 Listes : Utilise les structures de données de Java pour stocker et manipuler les informations.

🗄️ Version avec base de données MySQL : Stocke les informations dans une base de données relationnelle et utilise Lombok 🛠️ pour simplifier la gestion des entités.

Le projet implémente certains principes de développement SOLID 🎯 afin de garantir un code modulaire, extensible et maintenable.

🌟 Fonctionnalités Clés

🏢 Gestion des Pavillons

➕ Ajouter un pavillon.

✏️ Modifier les informations d’un pavillon.

📜 Lister tous les pavillons.

🛠️ Associer des chambres à un pavillon existant lors de sa création.

🛏️ Gestion des Chambres

➕ Ajouter une chambre.

✏️ Modifier les informations d’une chambre.

📜 Lister les chambres.

🗃️ Archiver une chambre.

🏷️ Lister les chambres d’un pavillon donné.

🎓 Gestion des Étudiants

➕ Ajouter un étudiant (à travers un formulaire unique pour tous les types d’étudiants).

🎭 Différencier les types d’étudiants :

🏠 Boursiers Logés : Affectation d'une chambre.

🚶‍♂️ Boursiers Non Logés : Pas d’allocation de chambre.

🏡 Non Boursiers : Ajout d'une adresse.

📋 Lister les étudiants d’une chambre.

🧩 Caractéristiques des Entités

🎓 Étudiant

🆔 Matricule

✍️ Nom et prénom

📧 Email

📞 Téléphone

🎂 Date de naissance

🗂️ Types :

🎓 Boursiers : Demi-bourse (20,000 FCFA) ou bourse entière (40,000 FCFA).

🏡 Non Boursiers : Adresse.

🏠 Logés : Chambre assignée.

🛏️ Chambre

🔢 Numéro de chambre

🏢 Numéro d’étage

🛋️ Type (individuelle ou à deux)

🏢 Pavillon

🔢 Numéro de pavillon

🏗️ Nombre d’étages

👨‍💼 Responsable des Pavillons

🔑 Accès à toutes les opérations d'ajout, modification, listage et archivage d’éléments du système.

🛠️ Technologies Utilisées

📋 Version avec Listes

💻 Langage : Java

📚 Bibliothèques : Collections Framework (List, Map, etc.)

📐 Paradigmes : Mise en pratique des principes SOLID pour une meilleure architecture.

🗄️ Version avec MySQL

💻 Langage : Java

🗄️ Base de données : MySQL

⚙️ ORM Simplifié : Lombok pour la génération automatique des getters, setters et constructeurs.

🔗 Connexion à la BD : JDBC pour exécuter des opérations CRUD.

📂 Organisation du Code

🏗️ Architecture du Projet

Le projet est organisé selon une architecture MVC (Modèle-Vue-Contrôleur) :

📊 Modèle : Classes représentant les entités (Étudiant, Chambre, Pavillon) et accès aux données.

👓 Vue : Interface console permettant à l'utilisateur d'interagir avec le système.

🧠 Contrôleur : Classes gérant la logique métier et orchestrant les interactions entre les modèles et les vues.

✨ Exemple de Classes

🎓 Classe Étudiant (Version MySQL avec Lombok)

import lombok.Data;

@Data
public class Etudiant {
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Date dateNaissance;
    private String type; // Non boursier, Boursier, Logé
    private String adresse; // Non boursier
    private Chambre chambre; // Logé
    private double montantBourse; // Boursier
}

🏢 Classe Pavillon

import lombok.Data;

@Data
public class Pavillon {
    private int numero;
    private int nombreEtages;
    private List<Chambre> chambres;
}

⚙️ Instructions d'Installation

📋 Version Listes :

🖥️ Clonez le dépôt.

📂 Ouvrez le projet dans votre IDE Java (Eclipse, IntelliJ IDEA, etc.).

▶️ Exécutez le fichier Main.java.

🗄️ Version MySQL :

🖥️ Assurez-vous que MySQL est installé et configuré.

📥 Importez le fichier schema.sql fourni pour créer la base de données.

⚙️ Configurez les paramètres de connexion à la base de données dans le fichier DatabaseConfig.java.

📂 Ouvrez le projet dans votre IDE Java.

▶️ Exécutez le fichier Main.java.

🎯 Principes SOLID Appliqués

🧱 S : Responsabilité unique – Chaque classe a une seule responsabilité claire.

🚪 O : Ouvert/Fermé – Le code est conçu pour être extensible sans modification des classes existantes.

🔄 L : Substitution de Liskov – Les sous-classes peuvent être utilisées indifféremment de leur classe mère.

🧩 I : Ségrégation des interfaces – Les classes implémentent uniquement les interfaces pertinentes.

💉 D : Injection de dépendances – Les dépendances sont passées sous forme de paramètres (dans la version MySQL).

✍️ Auteur

Projet réalisé dans le cadre de la 2ème année de Licence en Génie Logiciel.

🚀 Améliorations Futures

🎨 Intégration d’une interface utilisateur graphique (JavaFX ou Swing).

🌐 Mise en place d’une API REST pour une version web.

✅ Ajout de tests unitaires et d’intégration pour garantir la qualité du code.

