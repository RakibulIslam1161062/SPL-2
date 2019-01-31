const express = require('express');
const router = express.Router();
const Ninja = require('../models/ninja');

router.get('/ninjas',function (req, res, next) {
  //res.send({type:'GET'});
//  Ninja.geoNear(
//    {
//      type:'Point',coordinates:[parseFloat(req.query.lng),parseFloat(req.query.lat)]
//    },
//    {maxDistance: 100000, spherical:true}
//  ).then(function(ninja){
  //  res.send(ninja);
//  });
Ninja.aggregate([{ $geoNear: { near: {type: 'Point', coordinates: [parseFloat(req.query.lng), parseFloat(req.query.lat)]}, spherical: true, maxDistance: 100000, distanceField: "dist.calculated" } }]).then(function(results){ res.send(results); });

});

router.post('/ninjas',function (req, res, next) {
  console.log(req.body);
  Ninja.create(req.body).then(function(ninja){
    res.send(ninja);
  }).catch(next);

});
router.put('/ninjas/:id',function (req, res, next) {
  res.send({type:'put'});
});

module.exports = router;
