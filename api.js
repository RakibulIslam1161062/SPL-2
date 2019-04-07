const express = require('express');
const router = express.Router();
const Ninja = require('../models/ninja');
const SignUp = require('../models/ninja');
const mongoose = require('mongoose');
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

router.get('/ninjas',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
  var dbo = db.db("ninjago");
  console.log("lol");

  dbo.collection("ninjas", function(err, collection) {
  collection
    .find()
    .sort({$natural: -1})
    .limit(1)
    .next()
    .then(
      function(doc) {
        console.log(doc);
        res.send(doc);
      },
      function(err) {
        console.log('Error:', err);
      }
    );
});

});

});


router.post('/login',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
  var dbo = db.db("ninjago");
  console.log("lol2");


  dbo.collection('accounts').findOne({ userName: req.body.userName}, function(err, user) {
        console.log(user.userName);
        // In case the user not found

        if(err) {
          console.log('THIS IS ERROR RESPONSE')
          res.json(err)
        }
        if (user.password == req.body.password){
          console.log('User and password is correct')
         res.json({userName: user.userName, password: user.password});
        } else {
          console.log("Credentials wrong");
          res.json({userName:"invalid", password: "invalid"});
        }
 });

});

});

router.post('/ninjas', function (req, res, next) {
  console.log(req.body);
  Ninja.create(req.body).then(function(ninja){
    res.send(ninja);
  }).catch(next);
  

});



router.post('/signup', function (req, res, next) {
  console.log(req.body);
  SignUp.create(req.body).then(function(account){
    res.send(account);
  }).catch(next);

});


router.put('/ninjas/:id',function (req, res, next) {
  res.send({type:'put'});
});

module.exports = router;
