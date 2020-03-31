const express = require("express");
const request = require("request");
const router = express.Router();
const checkAuth = require("../middleware/check-auth");


// get the name of the book or the regex to search..
// we call the jersey/jetty api later..

router.get("/:name/:page", checkAuth, (req, res, next) => {
    //console.log("ohayo :: "+ req.params.name);
    request('http://localhost:8080/search/recoSearch/'+req.params.name+"/"+req.params.page, (error, response, body)=> {
        if (!error && response.statusCode === 200) {
          const books = JSON.parse(body)
          //console.log("Got a response: ", books)
          res.status(200).json(books);
        } else {
          //console.log("Got an error: ", error, ", status code: ", response.statusCode)
        }
      })

});


module.exports = router;
