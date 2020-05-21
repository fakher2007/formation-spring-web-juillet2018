# Exercice 2a :

Objectifs :
* invocation asynchrones
* programmation orientée aspect
* jmx
*****

1. Créer une classe de configuration `com.acme.ex2.ApplicationConfig`, celle-ci devra intégrer le fichier conf.properties afin que les propriétés soient 
	* récupérables par l'environnement Spring (méthode `getProperty` du type `org.springframework.core.env.Environment`)
	* injectables dans nos beans (annotation `@Value` au lieu de `@Autowired`)

2. Ajouter une méthode productrice afin que le logger slf4j soient injectable dans nos beans. Récupération d'un logger : `Logger logger = LoggerFactory.getLogger`

3. Annoter la classe ApplicationProperties pour qu'elle soit un bean Spring et que le champ `folder` soit valorisé sur la base de la propriété `folder` du ficher conf.properties

4. Annoter les implémentations de `CommandHandler` afin qu'elles soient gérées par Spring

5. Examiner la classe `CommandProcessorImpl`, compléter les TODO

6. Compléter la classe de configuration `ApplicationConfig` pour que soient ajoutés au contexte spring : 

   * le `CommandProcessorImpl` (notre service)
   * les `CommandHandler` (notre couche business)

7. Compléter et lancer le test unitaire `TranslationCommandTest`

8. Réfléchir à une manière de déclarer les `CommandHandler` en _lazy_ afin qu'il ne soient construits que la première fois qu'ils sont sollicités.

9. Réfléchir à un moyen de créer une spécialisation de `@Component` (nommée par exemple `@Handler`) et qui factoriserait `@Component` et `@Lazy`


10. Créer un aspect `com.acme.ex2.aspect.HandlerPerformanceMonitor` afin d'intercepter toutes les invocations aux méthodes `handle` des `CommandHandler`. Dans le corps de la méthode responsable de l'interception : 

    ```java
    long n = System.currentTimeMillis();
    // appel du joinpoint
    long n2 = System.currentTimeMillis();
    // log de la différence entre n2 et n pour indiquer le temps d'exécution de la méthode.
    // renvoi du résultat renvoyé par le joinpoint
    ``` 
    Relancer le test unitaire pour vérifier la bonne application de l'aspect et la non regression.

11. Réfléchir à la possibilité d'activer ou désactiver au runtime le log du temps d'exécution en fonction de la valeur d'un champ booléen (que nous pouvons appeler 'enabled').
   Comment agir sur la valeur de ce champ alors que l'application est déployée ?