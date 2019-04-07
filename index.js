const express = require('express');
const routes = require('./routes/api')
const bodyParser = require('body-parser');
var MongoClient = require('mongodb').MongoClient;
const Server = require('mongodb').Server;
const mongoose = require('mongoose');

const app = express();

mongoose.connect('mongodb://localhost/ninjago',{useNewUrlParser: true });
//MongoClient.connect('mongodb://localhost/ninjago', {useNewUrlParser: true });
//mongoose.Promise = global.Promise;

app.use(bodyParser.json());
app.use(routes);

app.use(function(err, req, res, next){
  //console.log(err);
  res.status(422).send({error: err.message});
});
app.listen(process.env.port || 4000,function () {
  console.log("listening");
});
