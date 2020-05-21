#  Exercice 5 (IHM avec Spring MVC) :

Objectifs : 
- mise en oeuvre de spring mvc pour concevoir une IHM
- couplage avec JSP
- couplage avec bean-validation

*****

1. Examiner le pom.xml et notamment les dépendances vers :
	* `javax.servlet-api`
	* `spring-webmvc`
	* `taglibs`
   
2. Observer la classe `com.acme.ex5.WebAppInitializer`, comparer avec la classe du même nom dans ex4

3. Compléter la classe de `MvcConfiguration`, qui sera le contexte web.
	* faire de cette classe une implémentation de ...
	* redéfinir la méthode `configureViewResolvers` pour indiquer à Spring comment rapprocher un nom de vue des fichiers jsp qui se trouvent dans `/WEB-INF/views`
	
4. Créer un controleur `com.acme.ex5.controller.BookController`, ajouter une méthode afin qu'un `GET` sur _/books_ déclenche le rendu de la vue `/WEB-INF/views/books/list.jsp`
    
5. Ajouter à la vue `/WEB-INF/views/books/list.jsp` un formulaire de recherche par _titre_ et par _nom d'auteur_, ajouter à la classe `BoookController` une méthode permettant de répondre à une soumission de ce formulaire et de présenter les résultats dans la vue. 

	Bien réfléchir à l'objet qui peut servir au 2 ways binding : 
	* DON'T : récupérér une à une les valeurs saisies dans le formulaire avec des paramètres annotés par `@RequestParam`
	* DO : récupérer les valeurs saisies dans le formulaire dans un objet reconstruit par Spring.

	Début du code d'implémentation de cette méthode : 

    ```
    FindBookCommand cmd = new FindBookCommand();
    //valorisation de la propriété filter
    cmd = this.processor.process(cmd);
    Results<Book> results =  cmd.getMultipleResults();
    ```

6. Appliquer la validation de saisie sur la méthode créée en 5, faire apparaître le message d'erreur à côté du champ de saisie dans la vue.

7. Dans la classe `BookController`, ajouter une méthode afin qu'un `GET` sur _/books/{id}_ déclenche le rendu de la vue `/WEB-INF/views/books/detail.jsp`. Cette méthode doit permettre de transmettre à la vue le livre {id} sous le nom `entity`. Code pour récupérer le livre {id} auprès de ex3 : 

    ```
    FindBookCommand cmd = new FindBookCommand();
    cmd.setId(id);
    cmd = this.processor.process(cmd);
    Book entity =  cmd.getSingleResult();
    ```
    
8. Ajouter à la vue `/WEB-INF/views/books/detail.jsp` un formulaire de saisie permettant de réserver un livre (**POST** sur _/reservations_). Bien réfléchir à l'objet qui va recevoir la saisie utilisateur.

9. Créer dans `BookController` la méthode permettant de répondre à une soumission du formulaire créé en 8. Cette méthode doit rediriger vers la liste de résultats.

10. Appliquer la validation de saisie sur la réservation d'un livre, faire apparaître le message d'erreur à côté des champs de saisie du formulaire créé en 8.

11. Ajouter un mécanisme de gestion des exceptions pour associer 
	* les exceptions métiers (`CommandException`) à `_errors/command-exception.jsp`
	* les autres exceptions (`Throwable`) à `_errors/other-exception.jsp`

12. Compléter la page `_errors/command-exception.jsp` afin que le message afficher soit le message de l'exception (si le champ `messageI18nKey` vaut `false` ) ou la traduction de ce message dans la bonne langue (si le champ `messageI18nKey` vaut `true`).