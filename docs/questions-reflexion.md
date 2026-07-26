# Questions de réflexion

## 1. Quels avantages apporte la fabrique simple ?

Le principal avantage, c'est que toute la création des objets `HashCracker` est centralisée dans une seule
classe (`HashCrackerFactory`), au lieu d'avoir des `new DictionaryHashCracker()` ou
`new BruteForceHashCracker()` éparpillés dans le programme. Ça découple aussi `Main` des classes concrètes :
il ne manipule que le type `HashCracker`, jamais les implémentations directement. Pour l'utiliser, un simple
appel `HashCrackerFactory.create("DICO")` suffit sans connaître les détails de construction de chaque
stratégie, ce qui rend le code plus lisible et plus facile à tester.

## 2. Quels sont ses inconvénients ?

Le principal inconvénient, c'est que la fabrique doit connaître à l'avance toutes les stratégies
existantes : elle contient un `switch` (ou `if/else`) qui énumère explicitement `"DICO"` et `"BRUTE"`. Ça
viole aussi le principe Open/Closed (voir question 4), puisqu'on ne peut pas ajouter une stratégie sans
modifier son code. En plus ses méthodes sont statiques, donc difficile à sous-classer ou à remplacer par
une autre implémentation. Et si le nombre de stratégies augmente beaucoup, la fabrique devient un point de
couplage central qui grossit indéfiniment, puisqu'elle doit importer et connaître toutes les classes
concrètes.

## 3. Que faut-il modifier lorsqu'une nouvelle stratégie est ajoutée ?

Pour ajouter une nouvelle méthode de cassage (par exemple `RAINBOW` pour une attaque par rainbow table), il
faut :

1. Créer une nouvelle classe (ex. `RainbowTableHashCracker`) qui implémente `HashCracker`.
2. Modifier le code de `HashCrackerFactory.create(...)` pour ajouter un nouveau cas dans le `switch`
   (`case "RAINBOW": return new RainbowTableHashCracker();`).

Le point important, c'est que cette deuxième étape oblige à modifier une classe existante et déjà
fonctionnelle, ce qui est risqué (régression possible) et contraire à l'idéal d'extensibilité.

## 4. La fabrique respecte-t-elle le principe Open/Closed ?

**Non.** Le principe Open/Closed (le "O" de SOLID) stipule qu'une classe doit être **ouverte à
l'extension mais fermée à la modification**. Or ici, ajouter une stratégie impose de modifier le corps de
`HashCrackerFactory.create(...)` (ajouter un `case` au `switch`), donc de rouvrir une classe déjà écrite et
testée.

D'ailleurs la Simple Factory n'est pas vraiment un patron de conception officiel du Gang of Four, plutôt un
idiome pratique mais limité. C'est justement la limitation annoncée à la fin du sujet, qu'on va corriger
dans le prochain mini-projet, sûrement avec un Factory Method ou un système d'enregistrement de stratégies.
