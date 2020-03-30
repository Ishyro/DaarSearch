# DaarSearch

* The server : http://51.75.124.11:4200/

## to run the ui
* npm i         // to install the dependencies..
* npm start

## to run the server back
* nodemon server.js   // not yet

## to run the server Data
*
*

## Architecture

* Angular < -- > NodeJs  // for authentication and logs and others..
* Angular < -- > Jetty/Jersey // for the data handeling..


## backlog
> ui-front-back
* Authentication
* Search component ( word )
* Advanced Search ( regex )
* Document list component
* Document View component
* Adding logs to mongoDB
* Ranking Component
* SimilarResponse component
*

> data server
* GET DocumentsList // get a word and return a list of documents that   contains the word in the table of indexes.
`Une fonctionnalit ́e explicite de “Recherche”`

* GET DocumentsList // get a Regex and return a list of documents that   contains the word in the table of indexes.
`Une fonctionnalit ́e explicite de “Recherche avanc ́ee”`

*  GET Ranking list after every query of DocumentList .
` Une fonctionnalit ́e implicite de classement (voir l’ ́enonc ́e du projet 2 pour plus de description, mˆeme dans lecas du CHOIX B)`


*  GET similar Response `Une fonctionnalit ́e implicite de suggestion`
*
*
