const express = require('express');
const router = express.Router();
const Ninja = require('../models/ninja');

router.get('/ninjas',function (req, res) {
  res.send({type:'GET'});
});

 router.post('/ninjas',function (req, res) {
  console.log(req.body);
  Ninja.create(req.body).then(function(ninja){
    res.send(ninja);
  });

});
router.put('/ninjas/:id',function (req, res) {
  res.send({type:'put'});
});

module.exports = router;
