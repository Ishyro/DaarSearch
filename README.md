# DaarSearch

## contributors
* julien BISSEY  : software engineer, M2 STL 2019/2020
* Mohammed-achraf CHARIF : software engineer, M2 STL 2019/2020

* The server : http://51.75.124.11:4200/
a modest server ;) be gentle


* to login you can use the account
email : daar@stl.fr
password : azer

* or you can create a new one !


## to run the ui `Angular/typescript`
* npm i         // to install the dependencies..
* npm start

## to run the server back `NodeJS/javascript / MongoDB`
* nodemon server.js   // not yet

## to run the server Data `Jetty/Jersey/java/shell`
* make
* java -jar ./target/daarSearch-1.0-SNAPSHOT.jar

## Architecture of the App

* Angular < -- > NodeJs  // for authentication and logs pagination and others..
* NodeJs < -- > Jetty/Jersey // for the data handeling
