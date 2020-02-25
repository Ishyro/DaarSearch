const express = require("express");

const router = express.Router();
const checkAuth = require("../middleware/check-auth");


// get the name of the book or the regex to search..
// we call the jersey/jetty api later..

router.get("/:name", checkAuth, (req, res, next) => {

    // make the call to jersey/jetty api to get the right book..
    console.log("requesting a book with normal string ");
    const books = [
        { name: "book1_normalString", content: "content of book 1_normalString" },
        { name: "book2_normalString", content: "content of book 2_normalString" },
        { name: "book3_normalString", content: "content of book 3_normalString" }
    ];
    res.status(200).json(books);
});

router.get("/regex/:name", checkAuth, (req, res, next) => {

    // make the call to jersey/jetty api to get the right book with regex..
    console.log("requesting a book with regex ");
    const books = [
        { name: "book1_regex", content: "content of book 1_regex" },
        { name: "book2_regex", content: "content of book 2_regex" },
        { name: "book3_regex", content: "content of book 3_regex" }
    ];
    res.status(200).json(books);
});

module.exports = router;

