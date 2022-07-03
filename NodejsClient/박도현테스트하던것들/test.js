const express = require('express');
const app = express();

const bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true}));

const request = require('request')
const serviceKey = require('./keys/key') // 서비스 인증키

const locationdata = (vehId, callback) => {
    const url = 'http://ws.bus.go.kr/api/rest/buspos/getBusPosByVehId';
    var ServiceKey = serviceKey.publicPortalKey;
    var queryParams = '?' + encodeURIComponent('serviceKey') + '=' + ServiceKey;
    queryParams += '&' + encodeURIComponent('vehId') + '=' + encodeURIComponent(111033352);
    queryParams += '&' + encodeURIComponent('resultType') + '=' + encodeURIComponent('json');
    request({
        url: url + queryParams,
        method: 'GET'
    }, function (error, response, body) {
        console.log(body)
        const location = JSON.parse(body)
        console.log('location', location)
        callback(undefined, {
            location:location
        })
    });
}


app.get('/', (req, res)=>{
    locationdata(req.body.number, (error, {location}={})=>{
        if(error){
            return res.send({error});
        }
        
        

        var number = req.body.number; // 버스 ID
        var id =  location['msgBody']['itemList'][0]['vehId'];
        var veh_num = location['msgBody']['itemList'][0]['plainNo'];
        var congetion = location['msgBody']['itemList'][0]['congetion'];
        var posX = location['msgBody']['itemList'][0]['posX'];
        var posY = location['msgBody']['itemList'][0]['posY'];
        //차량 고유번호도 좋은데 그냥 plainNo (서울75사2644 이런 고유번호로 서버에 req주면 안되나?)
        var plainNo = location['msgBody']['itemList'][0]['plainNo'];
        
        console.log(`버스번호 ${number}`);
        console.log(id);
        console.log(veh_num);
        console.log(congetion);
        console.log(posX);
        console.log(posY);
    })
})

app.listen(3000,()=>{
    console.log('3000번으로 서버 생성');
})