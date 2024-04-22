# Intégration et entrepôts de données : Projet de médiateur de sources de films

### Date de réalisation : 22/04/2024
### Année universitaire : 2023-2024
### Établissement : CY Cergy Paris Université

- Ceci est un tutoriel d'utilisation du médiateur Java qui combine plusieurs sources (BD MySQL locale, DBpedia, OMDB API) et extrait les informations de films.
- Ce projet est réalisé dans le cadre du module Intégration et Entrepôts de Données (M1-IISC) encadré par M. Dan Vodislav (dan.vodislav@u-cergy.fr) et M. Clément Agret (clement.agret@u-cergy.fr).

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Versions des outils et des frameworks utilisés

- Oracle JDK 17 -> https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- MySQL 8.0.36 -> https://dev.mysql.com/downloads/mysql/
- Apache Jena 4.2.0 -> https://jena.apache.org/download/
- Apache HOP 2.8.0 -> https://hop.apache.org/download/
- Jsoup 1.17.2 -> https://jsoup.org/download
- mysql-connector 8.3.0 -> https://dev.mysql.com/downloads/connector/j/?os=26

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Configuration de la base de données

Pour configurer le projet "Intégration et entrepôts de données", assurez-vous de modifier les paramètres de connexion à la base de données dans les attributs de la classe `JDBCClient.java`. Voici les valeurs à mettre à jour :

- **HOST** : L'adresse IP ou le nom d'hôte du serveur de base de données. Par exemple, "127.0.0.1".
- **BASE** : Le nom de votre base de données.
- **USER** : Le nom d'utilisateur pour accéder à la base de données.
- **PASSWORD** : Le mot de passe associé à cet utilisateur.

### Exemple de configuration :

```java

private static final String HOST = "127.0.0.1";
private static final String BASE = "nom_de_base_de_donnees";
private static final String USER = "utilisateur";
private static final String PASSWORD = "mot_de_passe";

```

------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## Interface graphique

Pour accéder à l'interface graphique, exécutez le code de la classe `FacadeClient.java` dans un IDE comme Eclipse ou IntelliJ IDEA. Une fois l'interface ouverte, vous verrez les éléments suivants :

- **Bouton "Recherche par :"** : Choisissez entre "Titre de film" ou "Nom d'acteur" comme critère de recherche.
- **Zone de texte** : Saisissez le titre du film ou le nom de l'acteur que vous souhaitez rechercher.
- **Bouton "Recherche"** : Cliquez pour lancer la recherche.
