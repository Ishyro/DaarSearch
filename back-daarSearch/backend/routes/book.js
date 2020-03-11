const express = require("express");
const request = require("request");
const router = express.Router();
const checkAuth = require("../middleware/check-auth");


// get the name of the book or the regex to search..
// we call the jersey/jetty api later..

router.get("/:name", checkAuth, (req, res, next) => {


    request('http://localhost:8080/book-library/books-normal/'+req.body.name, (error, response, body)=> {
        if (!error && response.statusCode === 200) {
          const books = JSON.parse(body)
          console.log("Got a response: ", books)
          res.status(200).json(books);
        } else {
          console.log("Got an error: ", error, ", status code: ", response.statusCode)
        }
      })

});

router.get("/regex/:name", checkAuth, (req, res, next) => {

    request('http://localhost:8080/book-library/books-regex/'+req.body.name, (error, response, body)=> {
        if (!error && response.statusCode === 200) {
          const books = JSON.parse(body)
          console.log("Got a response: ", books)
          res.status(200).json(books);
        } else {
          console.log("Got an error: ", error, ", status code: ", response.statusCode)
        }
      })
});

module.exports = router;

