# Questions de réflexion

## 1. Quels avantages apporte la fabrique simple ?

- Elle **centralise** la création des objets `HashCracker` dans une seule classe (`HashCrackerFactory`),
  au lieu de disperser des `new DictionaryHashCracker()` / `new BruteForceHashCracker()` dans tout le
  programme.
- Elle **découple** le code appelant (`Main`) des classes concrètes : `Main` ne manipule que le type
  `HashCracker`, jamais `DictionaryHashCracker` ou `BruteForceHashCracker` directement.
- Elle simplifie l'utilisation : un simple appel `HashCrackerFactory.create("DICO")` suffit, sans avoir à
  connaître les détails de construction de chaque stratégie.
- Elle rend le code plus lisible et plus facile à tester, puisque le point de création est unique et
  identifiable.

## 2. Quels sont ses inconvénients ?

- La fabrique doit **connaître à l'avance** toutes les stratégies existantes : elle contient un `switch`
  (ou `if/else`) qui énumère explicitement `"DICO"` et `"BRUTE"`.
- Elle **viole le principe Open/Closed** (voir question 4) : impossible d'ajouter une stratégie sans
  modifier son code.
- C'est une classe avec des méthodes **statiques**, donc difficile à sous-classer ou à remplacer par une
  autre implémentation (pas de polymorphisme sur la fabrique elle-même).
- Si le nombre de stratégies augmente beaucoup, la fabrique devient un point de couplage central qui grossit
  indéfiniment (elle doit importer et connaître toutes les classes concrètes).

## 3. Que faut-il modifier lorsqu'une nouvelle stratégie est ajoutée ?

Pour ajouter une nouvelle méthode de cassage (par exemple `RAINBOW` pour une attaque par rainbow table), il
faut :

1. Créer une nouvelle classe (ex. `RainbowTableHashCracker`) qui implémente `HashCracker`.
2. **Modifier le code de `HashCrackerFactory.create(...)`** pour ajouter un nouveau cas dans le `switch`
   (`case "RAINBOW": return new RainbowTableHashCracker();`).

Le point important est que cette deuxième étape oblige à **modifier une classe existante et déjà
fonctionnelle**, ce qui est risqué (régression possible) et contraire à l'idéal d'extensibilité.

## 4. La fabrique respecte-t-elle le principe Open/Closed ?

**Non.** Le principe Open/Closed (le "O" de SOLID) stipule qu'une classe doit être **ouverte à
l'extension mais fermée à la modification**. Or ici, ajouter une stratégie impose de modifier le corps de
`HashCrackerFactory.create(...)` (ajouter un `case` au `switch`), donc de rouvrir une classe déjà écrite et
testée.

Une fabrique simple (Simple Factory) n'est d'ailleurs pas un vrai patron de conception au sens du Gang of
Four : c'est un idiome pratique mais limité, souvent présenté comme point de départ avant d'évoluer vers un
**Factory Method** ou une **fabrique paramétrée par réflexion / registre de stratégies**, qui permettent
d'ajouter une stratégie sans toucher au code existant (chaque nouvelle classe s'enregistre elle-même auprès
de la fabrique). C'est précisément la limitation annoncée en fin de sujet, à corriger dans le mini-projet
suivant.
