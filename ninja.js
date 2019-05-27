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

const NotificationSchema = new Schema({
  busName: String,
  adminName: String,
  adminDesig: String,
  message: String
});

const AdminSignUpSchema = new Schema({
  name: String,
  dept: String,
  busName: String,
  userName: String,
  password: String
});


const SignUp = mongoose.model('account',SignUpSchema);

const AdminSignUp = mongoose.model('adminAccount',AdminSignUpSchema);

const NotificationItem = mongoose.model('notification',NotificationSchema);


const Ninja = mongoose.model('ninja',NinjaSchema);


module.exports = {
    Ninja:Ninja,
    SignUp: SignUp,
    NotificationItem: NotificationItem,
    AdminSignUp: AdminSignUp

};
