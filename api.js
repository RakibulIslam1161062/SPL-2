const express = require('express');
const router = express.Router();
mongo = require('mongodb');
const models = require('../models/ninja');

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
        //console.log(user.userName);
        // In case the user not found


        if(err) {
          console.log('THIS IS ERROR RESPONSE')
          res.json({userName:"invalid", password: "invalid"});
        }
          if(user== null) {
              res.json({userName:"invalid", password: "invalid"});
          }
        else if (user.password == req.body.password){
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
//  console.log(req.body);
  models.Ninja.create(req.body).then(function(ninja){
    res.send(ninja);
  }).catch(next);


});


router.post('/signup', function (req, res, next) {
  console.log(req.body);
  models.SignUp.create(req.body).then(function(account){
    res.send(account);
  }).catch(next);

});
router.post('/sendNotification', function (req, res, next) {
  console.log(req.body);
  models.NotificationItem.create(req.body).then(function(notification){
    res.send(notification);
  }).catch(next);

});



router.put('/ninjas/:id',function (req, res, next) {
  res.send({type:'put'});
});




// var cursor = db.collection('test').find();
//
// // Execute the each command, triggers for each document
//
// cursor.each(function(err, item) {
//     // If the item is null then the cursor is exhausted/empty and closed
//     if(item == null) {
//         db.close(); // you may not want to close the DB if you have more code....
//         return;
//     }
//     else{
//
//     }
//     // otherwise, do something with the item
// })

//for test
router.get('/newUser',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;

  console.log("lol");
    var dbo = db.db("ninjago");
    var collection = dbo.collection('accounts')
    collection.find().toArray(function(err, items) {
              if (err) {
                reject(err);
              } else {
                console.log(items);
                res.send(items);

              }
      });
    });
});

router.get('/getnotification',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;

  console.log("lol");
    var dbo = db.db("ninjago");
    var collection = dbo.collection('notifications')
    collection.find().toArray(function(err, items) {
              if (err) {
                reject(err);
              } else {
                console.log(items);
                res.send(items);

              }
      });
    });
});

router.get('/users',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
  var dbo = db.db("ninjago");
  console.log("lol");
  var collection = dbo.collection('accounts')
  collection.find().toArray(function(err, items) {
            if (err) {
              reject(err);
            } else {
              console.log(items);
              res.send(items);

            }
    });

});

});



router.post('/usersDelete',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
var id = req.body._id;
  console.log(id);
    var dbo = db.db("ninjago");
    var collection = dbo.collection('accounts')
    collection.deleteOne({ _id: new mongo.ObjectId(id) }, function (err, results) {
    });

    res.json(id);

    });
});

router.post('/adminDelete',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
var id = req.body._id;
  console.log(id);
    var dbo = db.db("ninjago");
    var collection = dbo.collection('adminAccounts')
    collection.deleteOne({ _id: new mongo.ObjectId(id) }, function (err, results) {
    });

    res.json(id);

    });
});



router.post('/adminSignup', function (req, res, next) {
  console.log(req.body);
  models.AdminSignUp.create(req.body).then(function(adminAccount){
    res.send(adminAccount);
  }).catch(next);

});

router.post('/adminLogin',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
  var dbo = db.db("ninjago");
  console.log("lol2");


  dbo.collection('adminaccounts').findOne({ userName: req.body.userName}, function(err, user) {
        //console.log(user.userName);
        // In case the user not found

        console.log(res.body);
        if(err) {
          console.log('THIS IS ERROR RESPONSE')
          res.json({userName:"invalid", password: "invalid"});
        }
          if(user== null) {
              res.json({userName:"invalid", password: "invalid"});
          }
        else if (user.password == req.body.password){
          console.log('User and password is correct')
         res.json({userName: user.userName, password: user.password});
        } else {
          console.log("Credentials wrong");
          res.json({userName:"invalid", password: "invalid"});
        }
 });

});

});

router.get('/adminUsers',  async function  (req, res) {
    MongoClient.connect(url,async function(err, db) {
  if (err) throw err;
  var dbo = db.db("ninjago");
  console.log("lol");
  var collection = dbo.collection('adminaccounts')
  collection.find().toArray(function(err, items) {
            if (err) {
              reject(err);
            } else {
              console.log(items);
              res.send(items);

            }
    });

});

});



module.exports = router;
