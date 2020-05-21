# Exercice 1 :

Objectif : 
- compréhension du pom.xml
- détection des dépendances
- préparation des injections
- déclaration des beans auprès de Spring

*****

1. écrire deux implémentations de l'interface `MovieDao` : `FoxMovieDaoImpl` et `WarnerMovieDaoImpl`
chacune renvoie un `Stream<Movie>` filtrée d'après le paramètre `title` de la méthode `retrieve`. A propos des streams : https://stackoverflow.com/a/24679745

2. écrire une implémentation `MovieServiceImpl` de `MovieService`, la méthode `find` utilise une des implémentations de `MovieDao` pour renvoyer un `Stream` de `Movie`

3. Comprendre la dépendance entre les implémentations de `MovieService` et les implémentations de `MovieDao`

4. Rendre possible l'injection d'une `MovieDao` dans la classe `MovieServiceImpl`

5. Ecrire une deuxième implémentation `SuperMovieServiceImpl` de l'interface `MovieService`. Cette deuxième implémentation doit être capable de recevoir plusieurs `MovieDao` et non plus une seule

6. (Créer une classe `com.acme.ex1.ApplicationFactory` chargée de gérer les composants de l'application, c'est à dire leur cycle de vie et les injections les uns dans les autres).

7. Créer une classe de configuration `ApplicationConfig` dans le package `com.acme.ex1`

8. Confier à Spring la gestion de nos composants : Spring va désormais jouer le rôle de *factory*, `ApplicationFactory` ne sera plus nécessaire.

9. Compléter les tests unitaires

10. Comprendre le problème qui se pose sur `MovieServiceImpl`, le résoudre.