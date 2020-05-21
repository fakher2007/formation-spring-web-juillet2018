# Exercice 3 (partie 3 : JMX)

1. Dans la classe `DefaultCommandExceptionHandler` :
   * Annoter la classe afin qu'elle soit un MBean
   * Annoter les méthodes `isEnabled` et `setEnabled` afin qu'elles soient invocables par JMX

2. Vérifier dans la classe `ApplicationConfig` que la classe `DefaultCommandExceptionHandler` sera bien prise en compte par Spring.