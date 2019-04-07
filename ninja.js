const mongoose = require('mongoose');
const Schema = mongoose.Schema;


const NinjaSchema = new Schema({
  lon: Number,
  lat: Number

});

const SignUpSchema = new Schema({
  name: String,
  dept: String,
  userName: String,
  password: String



});

const Ninja = mongoose.model('ninja',NinjaSchema);
module.exports = Ninja;

const SignUp = mongoose.model('account',SignUpSchema);
module.exports = SignUp;
