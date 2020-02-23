Ce projet est un clone de egrep codé en Java.
On utilise 3 méthodes différentes :

- La méthode d'Aho-Ullman à base d'automate reconnaissant tous les regex ('.','*','|','(',')','ASCII').
Les sources sont dans le dossier "automata".

- la méthode Knuth-Morris-Pratt reconnaissant uniquement les caractères ASCII.
Les sources sont dans le dossier "kmp".

- Une méthode à base de radix-tree reconnaissant uniquement les lettres majuscules et miniscules.
Les sources sont dans le dossier "index".

Le dossier "scripts" contient des scripts utilisés par le Makefile pour produire les fichiers de tests et les graphes.

Le dossier "test" contient les résultats de ses scripts ainsi que des fichiers contenant les regex utilisés pour ses tests.

Le dossier "data" contient les livres (obtenus à partir de la base Gutenberg) utilisés pour tester nos algorithmes.

___________________________________________

Le script egrep.sh utilise notre code pour rendre un résultat semblable à celui de egrep.
Il s'utilise de la manière suivante :
./egrep.sh <regex> <file.txt>

exemple : ./egrep.sh "S(a|r|g)*on" data/Babylonia2.txt

(si <file> n'a pas l'extension .txt, on cherchera un fichier <file.XXX.txt> pour la méthode radix-tree,
mais cela n'empêchera pas le script de fonctionner)

- si <regex> contient des caractères non reconnus par KMP, le script lance :
java automata.SearchInText <regex> <file.txt>
et utilise donc la méthode d'Aho-Ullman.

- si <regex> ne contient que des lettres ASCII et que le fichier "file.index" existe, le script lance :
java index.SearchInText <regex> <file.txt> <file.index>
et utilise donc la méthode à base de radix-tree.

- sinon, le script lance :
java kmp.SearchInText <regex> <file.txt>
et utilise donc la méthode Knuth-Morris-Pratt.

Le script va dire quelle méthode il utilise.
Tout ce qui suit doit être le clone de egrep (de fait, la méthode à base de radix-tree match uniquement les mots
et ne donnera pas toujours tous les résultats attendus).

___________________________________________

Makefile :

make | make all :
compile les sources

make index :
produit les fichiers data/*.index à partir des livres data/*.txt, nécessaire pour l'utilisation de la méthode à base de radix-tree

make test : (dure quelques minutes)
produit des fichiers de tests de performances dans le dossier test/results à partir des livres dans data et des regexes dans les fichiers
"test/unique_regexes", "test/multiple_regexes" et "test/multiple_regexes_no_automata".

make graph :
produit des fichiers jpg dans le dossier test/graphs à partir des tests de performances dans le dossier test/results (lance les tests s'ils n'ont pas été faits).

make clean :
efface les .class

make clean_index :
efface les .index

make clean_test :
efface les .*results

make clean_graph :
efface les .jpg

make clean_all :
appelle toutes les règles clean exceptée clean_graph (on suppose que l'utilisateur veut garder le résultat final des tests)

___________________________________________

Scripts :

Les scripts sont utilisés par le makefile pour produire les graphes.

De manière générale, les scripts lancent des egrep avec des méthodes différentes, ignore les résultats et conservent les temps d'exécutions.
Ensuite, on utilise awk pour calculer les moyennes et écart-types de ces résultats.
On donne ces nouvelles valeurs à gnuplot pour produire des graphes.

Note : dans les cas des recherches mutliples, on ne fait qu'une seule série de recherches par fichier,
il n'y a donc pas de moyenne ni d'écart-type à calculer.
