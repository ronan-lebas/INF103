# Projet Java, Ronan LEBAS
## Comment l'utiliser ?
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
* L'application empêchera l'existence de labyrinthes invalides, par exemple ne comportant pas le bon nombre de cases de départ ou d'arrivée
* Le bouton `Save` permet de sauvegarder un labyrinthe dans le dossier `data`, **il faut écrire le nom du fichier sans oublier l'extension `.maze`**
* Si un labyrinthe est modifié et non enregistré, quitter l'application, créer un nouveau labyrinthe ou charger un autre labyrinthe proposera à l'utilisateur d'enregistrer le labyrinthe courant sous le nom de son choix : *ici, pas besoin de mettre l'extension `.maze`*