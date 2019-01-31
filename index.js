const express = require('express');
const routes = require('./routes/api')
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

const app = express();

mongoose.connect('mongodb://localhost/ninjago',{useNewUrlParser: true });
//mongoClient.connect('mongodb://localhost/ninjago', {useNewUrlParser: true });
mongoose.Promise = global.Promise;

app.use(bodyParser.json());
app.use(routes);

app.listen(process.env.port || 4000,function () {
  console.log("listening");
});
