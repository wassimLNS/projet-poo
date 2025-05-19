# Gestion de Pharmacie

Application de gestion de pharmacie développée en Java avec interface graphique Swing.

## Fonctionnalités

1. Gestion des clients
   - Ajout, modification et suppression de clients
   - Gestion des cartes d'assurance maladie
   - Historique des achats

2. Gestion des fournisseurs
   - Ajout, modification et suppression de fournisseurs
   - Suivi des commandes par fournisseur

3. Gestion des médicaments
   - Ajout, modification et suppression de médicaments
   - Suivi des stocks
   - Alerte sur les dates de péremption
   - Différents types de médicaments (comprimés, sirops, injectables)

4. Gestion des ventes
   - Création de nouvelles ventes
   - Application des réductions selon la carte d'assurance
   - Historique des ventes

5. Gestion des commandes
   - Création de commandes auprès des fournisseurs
   - Suivi des commandes
   - Mise à jour automatique des stocks

## Prérequis

- Java JDK 11 ou supérieur
- Maven 3.6 ou supérieur

## Installation

1. Cloner le repository :
```bash
git clone [url-du-repo]
```

2. Se placer dans le répertoire du projet :
```bash
cd gestion-pharmacie
```

3. Compiler le projet avec Maven :
```bash
mvn clean package
```

4. Lancer l'application :
```bash
java -jar target/gestion-pharmacie-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Utilisation

1. **Gestion des clients**
   - Cliquer sur l'onglet "Clients"
   - Utiliser les boutons pour ajouter, modifier ou supprimer des clients
   - Consulter l'historique des achats d'un client

2. **Gestion des médicaments**
   - Cliquer sur l'onglet "Médicaments"
   - Ajouter de nouveaux médicaments avec leurs caractéristiques
   - Surveiller les stocks et les dates de péremption

3. **Réaliser une vente**
   - Cliquer sur l'onglet "Ventes"
   - Sélectionner "Nouvelle Vente"
   - Choisir le client
   - Ajouter les médicaments et les quantités
   - Valider la vente

4. **Gérer les commandes**
   - Cliquer sur l'onglet "Commandes"
   - Créer une nouvelle commande
   - Sélectionner le fournisseur
   - Ajouter les médicaments à commander

## Structure du projet

- `src/main/java/model/` : Classes modèles (Client, Medicament, etc.)
- `src/main/java/dao/` : Couche d'accès aux données
- `src/main/java/gui/` : Interface graphique
- `src/test/java/` : Tests unitaires

## Contribution

1. Fork le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Créer une Pull Request

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.
