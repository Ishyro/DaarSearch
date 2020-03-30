const mongoose = require("mongoose");
const uniqueValidator = require("mongoose-unique-validator");

const suggestionSchema = mongoose.Schema({
  name: { type: String, required: true, unique: true },
});

suggestionSchema.plugin(uniqueValidator);

module.exports = mongoose.model("Suggestion", suggestionSchema);
