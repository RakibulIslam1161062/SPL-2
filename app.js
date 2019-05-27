var express = require('express');
var bodyParser = require('body-parser');
var app = express();
//var bodyParser = require('body-parser');
var urlencodedParser = bodyParser.urlencoded({ extend: false });
app.engine('ejs', require('ejs').renderFile);
app.set('view engine', 'ejs');
//app.set('views',__dirname+'/partials');



app.get('/', function(req, res){
  res.render('index');
});
app.get('/contact', function(req, res){
//  console.log(req.query);
  res.render('contact',{qs: req.query});
});
app.get('/', function(req, res){
  res.render('index');
});

app.post('/contact', urlencodedParser, function(req, res){
console.log(req.body);
  res.render('contact',{qs: req.query});
});



app.get('/profile/:name', function(req, res)
{
  var data = {age: 20, job: 'ninja',hobbies: ['eating','fighting','fishing']};
  res.render('profile',{person: req.params.name, data: data});
});


app.listen(3000);
