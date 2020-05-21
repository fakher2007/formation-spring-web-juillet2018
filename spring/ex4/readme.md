# Exercice 4 (REST avec Spring MVC) :

Objectifs : 
- mise en oeuvre de spring mvc pour concevoir une API REST
- couplage avec bean-validation

*****

1. Examiner les dépendances suivantes dans le pom.xml :
	* `spring-webmvc`
	* `jackson-databind` pour la sérialisation et déserialisation en json.
	* `jackson-dataformat-xml` pour la sérialisation et déserialisation en xml.

2. Décommenter les 3 premières lignes de la méthode `onStartup` de la classe `WebAppInitializer`, déployer le projet en local avec le goal maven `jetty:run`

   Le contexte applicatif d'ex3 est désormais chargé au démarrage de la webapp. Nous pourrions ici utiliser autre chose que Spring MVC et disposer quand même des beans issus du projet ex3.

3. Dans la classe `WebAppInitializer` : Compléter la classe `RestConfiguration` puis décommenter les lignes correspondant à l'activation de la `DispatcherSerlvet`

   La servlet de Spring est désormais active, le servlet container lui déléguera tous les appels aux URI commencant par /api/

4. Créer une classe `com.acme.ex4.endpoint.BookEndpoint` et ajouter une méthode capable de recevoir un **GET** sur http://localhost:8080/api/books et de renvoyer une liste de représentations simplifiées de livres (`List<BookDto>`). Cette liste sera construite à partir du traitement par le `CommandProcessor` d'une `FindBookCommand` :

    ```
    FindBookCommand cmd = new FindBookCommand();
    BookFilter filter = new BookFilter();
    cmd.setFilter(filter);
    cmd = this.processor.process(cmd);
    List<Book> results =  cmd.getMultipleResults().getItems();
    return results.stream().map(b -> new BookDto(b, false)).collect(Collectors.toList());
    ```

5. Réfléchir à un moyen de soumettre la recherche en POST (le `BookFilter` sera alors passé dans le body de la requête) tout en respectant les principes de design d'une API REST.

6. Reprendre la classe `BookFilter` (exercice 3) et poser une contrainte de validation sur le champ `title` : la valeur doit être non nulle et d'une taille comprise entre 2 et 10. Relancer un `maven install`. Annoter ensuite les paramètres de type `BookFilter` afin que Spring renvoie une erreur 400 si le filter n'est pas valide.

7. Ajouter dans le `_ControllerAdvice` deux gestionnaires d'exception (un pour les exception de type `BindException`, l'autre pour les exceptions de type `MethodArgumentNotValidException`) afin de controler le body de la réponse 400 à retourner en cas d'erreur de validation (utiliser pour cela la méthode `onValidationError` présente dans l'advice).

8. Ajouter à la classe `com.acme.ex4.endpoint.BookEndpoint` une méthode capable de recevoir un **GET** sur http://localhost:8080/api/books/{id}. Une telle requête doit permettre d'obtenir la représentation simplifiée (classe `BookDto`) du livre numéro {id}. Attention, s'il n'y a pas de livre numéro {id} il ne faut pas renvoyer une réponse avec un body vide et un statut 200... Début de l'implémentation :
    ```
    FindBookCommand cmd = new FindBookCommand();
    cmd.setId(id);
    cmd = this.processor.process(cmd);
    Book result =  cmd.getSingleResult();
    ```