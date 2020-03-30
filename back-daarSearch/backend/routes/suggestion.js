const express = require("express");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

const Suggestion = require("../models/suggestion");

const router = express.Router();

router.post("/logs", (req, res, next) => {
  const suggestion = new Suggestion({
    name: req.body.name
  });
  suggestion
  .save()
  .then(result => {
    console.log("add new suggestion ");
    res.status(201).json({
      message: "new log for suggestion created!",
      result: result
    });
  })
  .catch(err => {
    res.status(500).json({
      error: err
    });
  });

});

router.get("/logs", (req, res, next) => {
  console.log("requesting the log list ..");
  Suggestion.find({})
  .then(result => {
    console.log(result);

    res.status(201).json({
      result: result
    });
  })
  .catch(err => {
    res.status(500).json({
      error: err
    });
  });
});

module.exports = router;
