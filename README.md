# INF103
## Introduction
Ce dépôt contient le projet du cours **INF103** : **Langage JAVA** de Télécom Paris pour l'année 2022-2023. L'objectif était de concevoir une application pour gérer et résoudre des labyrinthes hexagonaux. 
## Comment l'utiliser ?
### Lancer le programme
Il faut exécuter la classe `GUI.java` qui se trouve dans `projet/src/ui`.
### Édition des cases du labyrinthe
Mon but était de faire les contrôles de la façon la plus *user friendly* possible.  
* Cliquer sur une case vide ou mur pour interchanger vide <-> mur
* Cliquer sur une case vide pour en faire un mur et maintenir et glisser : changement de toutes les cases vides en mur, et inversement
* Pour déplacer les cases de départ et d'arrivée, maintenir et glisser
### Affichage de la solution
* Le bouton bascule en bas de l'écran permet de choisir si on affiche ou non le chemin
* On peut éditer le labyrinthe avec le chemin affiché : il s'actualise en direct
* S'il n'y a pas de chemin, l'application affiche un message d'erreur et désactive l'affichage (bug graphique au premier message d'erreur)
### Gestion des labyrinthes
* Le bouton `New` permet de générer un nouveau labyrinthe vide de taille renseignée par l'utilisateur
* Le bouton `Load` permet de charger un labyrinthe du dossier `data` du projet
    * Le dossier contient une collection de labyrinthes préexistants pour expérimenter différentes tailles de labyrinthes : `error.maze` n'est pas valide pour montrer la gestion d'erreurs, et `test.maze` ne comporte pas de solution
* L'application empêchera l'existence de labyrinthes invalides, par exemple ne comportant pas le bon nombre de cases de départ ou d'arrivée
* Le bouton `Save` permet de sauvegarder un labyrinthe dans le dossier `data`, **il faut écrire le nom du fichier sans oublier l'extension `.maze`**
* Si un labyrinthe est modifié et non enregistré, quitter l'application, créer un nouveau labyrinthe ou charger un autre labyrinthe proposera à l'utilisateur d'enregistrer le labyrinthe courant sous le nom de son choix : *ici, pas besoin de mettre l'extension `.maze`*
## Justifications de quelques choix
* Le *pattern* MVC est implémenté de la manière suivante :
    * La classe `Maze.java` est le **modèle**
    * La classe `GUI.java` est la **vue**. J'ai fait ces deux choix pour respecter les différents exemples du cours MVC
    * Le **contrôleur** est implémenté par les différentes méthodes `actionPerformed` des éléments de la **vue**
    * Le *pattern* `Observeur - Observable` est implémenté avec la **vue** et le **modèle**, en effet la classe `Maze.java` notifie `GUI.java` avec la méthode `stateChanged()` lors d'une modification, conformément au cours.
* J'ai choisi de mémoriser les polygones sous la forme de `hexagonList`, cela peut sembler inutile car on a déjà `MazeBox[][]` mais pour détecter les clics de souris sur les hexagones, j'utilise la méthode `contains` de la classe `Polygon`. C'est pour cela que je sauvegarde les hexagones (qui sont des polygones), au lieu les recalculer à chaque appui de souris par exemple.
* Les opérations et données relatives à la **vue**, comme par exemple la couleur des cases, la taille des cases ou les méthodes pour dessiner les cases sont stockées dans le **modèle**, donc sous la forme d'attributs des objets de la classe `Maze` pour respecter le principe de déléguation (la **vue** demande aux hexagones de se dessiner eux-mêmes par exemple) et les exemples du cours MVC. Toutes les données doivent être stockées dans le **modèle**
* J'ai utilisé des `switch(box.getLabel())` ou `switch("E")` et similaire, car cela était nécessaire à deux endroits : quand il s'agissait d'initialiser le labyrinthe à partir du fichier texte, de mettre à jour une case avec un nouveau type. C'est justifié selon moi, car ces modifications nécessitent de regarder le texte "E" ou "W" ou autre directement, il n'y a pas d'autres façons de faire.