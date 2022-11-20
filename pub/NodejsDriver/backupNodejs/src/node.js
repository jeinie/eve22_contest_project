const path = require('path')
const express = require('express')
const bodyParser = require('body-parser')
const locationdata = require('../utils/locationdata')
const app = express()
const port = process.env.PORT || 3000 // Heroku

const publicDir = path.join(__dirname, '../public')
const viewPath = path.join(__dirname, '../views')

app.use(bodyParser.urlencoded({extended: true})) // 등록
app.use(express.static(publicDir))
app.set('view engine', 'ejs')
app.set('views', viewPath)

app.get('/', (req, res)=>{
    res.render('index', { // index.ejs rendering
        title: '버스 단말기'
    })
})

app.post('/location', (req, res)=>{
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

app.post('/location/congetion', (req, res)=>{
    congetiondata(req.body.congetion, (error, {congetion}={})=>{
        if(error){
            return res.send({error})
        }
        return res.render('congetion', {
            
        })
    })
})

app.listen(port, ()=>{
    console.log(`Server is up and running at ${port} port!`);
})
