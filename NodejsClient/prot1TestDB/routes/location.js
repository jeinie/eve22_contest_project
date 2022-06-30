const express = require('express');
const locationdata = require('../utils/locationdata')

const router = express.Router();

router.post('/', (req, res)=>{
    locationdata(req.body.number, (error, {location}={})=>{
        if(error){
            return res.send({error});
        }
        return res.render('location', {
            title: '버스 단말기',
            number: req.body.number, // 버스 ID
            id: location['msgBody']['itemList'][0]['vehId'],
            veh_num: location['msgBody']['itemList'][0]['plainNo'],
            congetion: location['msgBody']['itemList'][0]['congetion'],
            posX: location['msgBody']['itemList'][0]['posX'],
            posY: location['msgBody']['itemList'][0]['posY']
        })
    })
})

module.exports = router;