const path = require("path");
const express = require("express");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");

const usersRoutes = require("./routes/user");
const bookRoutes = require("./routes/book");
const suggestionRoutes = require("./routes/suggestion");

const app = express();

// local storage you need to install mongoDB
// mongoose.connect('mongodb://localhost:27017/myapp1', {useNewUrlParser: true})
//   .then(() => {
//     console.log("Connected to database!");
//   })
//   .catch(() => {
//     console.log("Connection failed!");
//   });


// server storage
mongoose.connect('mongodb://daarSearcher:helloworld@51.75.124.11:27017/admin', {useNewUrlParser: true})
  .then(() => {
    console.log("Connected to database!");
  })
  .catch(() => {
    console.log("Connection failed!");
  });



app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));


app.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  res.setHeader(
    "Access-Control-Allow-Methods",
    "GET, POST, PATCH, PUT, DELETE, OPTIONS"
  );
  next();
});

app.use("/api/user", usersRoutes);
app.use("/api/book", bookRoutes);
app.use("/api/suggestion", suggestionRoutes);


module.exports = app;
