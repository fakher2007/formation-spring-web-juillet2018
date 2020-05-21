# Exercice 6 (Websocket, pub sub) :

1. Examiner les dépendances `spring-boot-starter-web` et `spring-boot-starter-websocket`, puis les dépendances transitives.

2. Lancer la méthode `main` dans `com.acme.ex7.MyApplication`

3. Examiner le code javascript du fichier `src/main/resources/static/index.html`

3. Ouvrir http://127.0.0.1:8080 depuis un navigateur. Poster un message sur le chat.

4. Décommenter les lignes 45 à 65 dans la classe `MyApplication` et relancer l'application.

5. Dans index.html : changer les souscriptions et les publications : 
   * la souscription doit porter sur outgoing-messages
   * la publication doit porter sur incoming-messages

   recharger la page, observer la différence. Désormais le programme Java est 'intercalé' entre les émeteurs et les récepteurs de messages