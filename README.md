# DaarSearch

## ui commands
* npm i         // to install the dependencies..
* npm start

## back server NodeJs
* `nodemon server.js`  or `node server.js` without nodemon

## server Jetty/jersey for data part
*  mvn clean install
* java -jar `the name of the generated jar`

## Architecture

* Angular < -- > NodeJs  // for authentication and logs and others..
* NodeJs < -- > Jetty/Jersey // for the data handeling..


## backlog
> ui-front-back
* Authentication 
* Search component ( word )
* Advanced Search ( regex )
* Document list component
* Document View component
* Adding logs to mongoDB for suggestion 
> back NodeJs/Jetty/jersey
* develop the api
