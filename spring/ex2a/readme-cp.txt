Par rapport à ex2 nous pouvons utiliser une annotation (@Usecase, définie dans AbstractCommand) pour indiquer quels handlers ont vocation à traiter quelles commandes.

Ainsi lorsqu'une commande est passée à la méthode process du CommandProcessor celle-ci peut examiner l'annotation @Usecase et en déduire les CommandHandler à qui transmettre la commande.

Cela n'exclue pas l'injection d'une map, au runtime la méthode process examine la présence de l'annotation UseCase et la map, espérant trouver dans une ou dans l'autre les handlers à solliciter pour traiter la commande.
