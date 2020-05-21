# Exercice 2 :

Objectif :
- ajout des dépendances maven
- découverte du pattern command et observer (voir le fichier readme-cp.txt)
- couplage de Spring et Junit
*****

1. Compléter le fichier pom.xml en déclarant la dépendance vers 
   * `org.springframework spring-context`
   * `org.springframework spring-test`
   
2. Créer une classe `com.acme.ex2.ApplicationConfig`, qui sera la classe de configuration du contexte Spring.

3. Ajouter une méthode productrice afin que le logger slf4j soient injectable dans nos beans. Récupération d'un logger : `Logger logger = LoggerFactory.getLogger`

4. Annoter la classe `ApplicationProperties` pour qu'elle soit un bean Spring et que le champ `folder` soit valorisé sur la base de la propriété `folder` du ficher conf.properties

5. Annoter les implémentations de `CommandHandler` afin qu'elles soient gérées par Spring. Activer l'injection de dépendances sur `logger` et `applicationProperties`.

6. Compléter la classes `CommandProcessorImpl` (suivre les TODO) et les implémentations de l'interface `CommandHandler` (package `com.acme.ex2.domain.business.impl`) afin que la publication de la commande en tant qu'évement depuis la méthode `process` du `CommandProcessorImpl` conduise à invoquer les méthodes `handle` des `CommandHandler`

7. Compléter la classe de configuration `ApplicationConfig` pour que soient ajoutés au contexte spring : 

   * le `CommandProcessorImpl` (notre service)
   * les `CommandHandler` (notre couche business)

8. Compléter le test unitaire `TranslationCommandTest` en couplant Junit à Spring.

9. Faire en sortie que la publication de l'évènement ne soit pas bloquante, comprendre le problème qui se pose alors.
