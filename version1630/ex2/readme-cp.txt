Chaque commande correspond à un cas d'utilisation et contient : 
* des champs utilisés par l'appelant (disons l'IHM) pour "poser sa question"
* des champs utilisées par l'application pour fournir en sortie la "réponse" à la question.
* éventuellement : des routines de validation avant traitement/aprés traitement, car la commande est la mieux placée pour savoir si elle est en état d'être traitée ou non.

Ainsi la commande est une structure d'échange entre l'IHM et l'application, il est possible d'ajouter de nouvelles propriétés
sans rien changer à la manière d'échanger les messages, c'est à dire sans breaking changes => favorise l'évolutivité et l'acceptation de nouvelles demandes de la part de la MOA.

Dans AbstractCommand, les méthodes validateStateBeforeHandling et validateStateAfterHandling permettent de vérifier les pré conditions et les post conditions.

une fois la commande reçue par le CommandProcessor (méthode process), celui-ci publie un évenement contenant la commande. Celle-ci est alors transmise aux handlers capables de la traiter.

Enfin la gestion des exceptions se fait grâce à la classe CommandException : seules les CommandException et les RuntimeException peuvent sortir des CommandHandler et du CommandProcessor.
L'interface CommandExceptionHandler permet d'effectuer des traitements d'exception, ses implémentations sont injectées dans le CommandProcessor et sont appelées par lui en cas d'exception lors du traitement d'une commande

Au runtime, le CommandProcessor orchestre le traitement des commandes mais en ignorant tout des commandes concrètes ou des handlers concrets :
il est agnostique et c'est pour cette raison qu'il est dans "common". Il est possible d'abonner / désabonner des handlers à une commande, 
encore une fois sans breaking changes. => favorise l'évolutivité et l'adaptation de l'application à de nouveaux besoins.