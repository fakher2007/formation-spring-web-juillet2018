# Exercice 3 (partie 1 : couplage Spring - JPA)

**Objectifs** : couplage Spring - JPA (Java persistence API)

*****

1. Confier à Spring la gestion de l'environnement technique d'accès aux données en inscrivant : 
   * un `EntityManagerFactory`
   * un `PlatformTransactionManager`

   Cela peut se faire par une classe de configuration : `com.acme.ex3.ApplicationConfig` 

   Ajouter une méthode productrice afin de permettre l'injection d'un `org.slf4j.Logger` dans nos composants.
   
2. Compléter la classe `JpaObjectStoreImpl` (pattern adapter) et l'annoter afin que les méthodes de cette classe ne puissent pas être
   exécutées en dehors d'une transaction.

3. Compléter le test `JpaObjectStoreImplTest` et exécuter les methodes de tests.
