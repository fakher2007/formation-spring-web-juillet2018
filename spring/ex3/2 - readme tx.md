# Exercice 3 (partie 2) :

**Objectifs** : 
* délégation à Spring de la gestion des transactions
* application des invocation asynchrones

*****

1. Comprendre le pattern command : 
   * la classe `com.acme.common.service.impl.CommandProcessorImpl`
   * l'interface `com.acme.common.business.CommandHandler`
   * la classe `com.acme.common.service.AbstractCommand`

2. Annoter les commandes avec l'annotation `Usecase` pour préciser par quel(s) `CommandHandler` elles ont vocation à être traitées.

3. Compléter la classe `CommandProcessorImpl` :
   * afin d'en faire un bean spring
   * afin qu'une transaction entoure l'invocation de la méthode process
   * suivre les TODO

4. Compléter les implémentations de `CommandHandler` (package com.acme.ex3.business.impl) :
   * afin d'en faire des beans spring
   * afin que l'`ObjectStore` soit injecté.
   * afin de garantir qu'une transaction soit déjà ouverte lorsque la méthode `handle` est appelée.
   * suivre les TODO dans `MemberRegistrationCommandHandler` et `ReservationCommandHandler`

5. Compléter et lancer le test unitaire `com.acme.common.service.impl.CommandProcessorImplTest`

6. Ajouter à la classe `CommandProcessorImpl` une méthode `processAsync` capable de traiter les commandes de manière asynchrone puis :
   * L'ajouter à l'interface `CommandProcessor`
   * Décommenter le code dans les deux tests de la méthode `processAsync`
