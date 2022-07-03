// 공공데이터 포털 연결
// Decoding: 원본, Encoding: Decoding값 URLEncode
// 키 정보 POST > Decoding / GET > Encoding
const request = require('request')
const serviceKey = require('../keys/key') // 서비스 인증키

const locationdata = (vehId, callback) => {
    const url = 'http://ws.bus.go.kr/api/rest/buspos/getBusPosByVehId';
    var ServiceKey = serviceKey.publicPortalKey;
    var queryParams = '?' + encodeURIComponent('serviceKey') + '=' + ServiceKey;
    queryParams += '&' + encodeURIComponent('vehId') + '=' + encodeURIComponent(vehId);
    queryParams += '&' + encodeURIComponent('resultType') + '=' + encodeURIComponent('json');

    request({
        url: url + queryParams,
        method: 'GET'
    }, function (error, response, body) {
        //console.log('Status', response.statusCode);
        //console.log('Headers', JSON.stringify(response.headers));
        //console.log('Reponse received', body);
        console.log(body)
        const location = JSON.parse(body)
        console.log('location', location)
        callback(undefined, {
            location:location
        })
    });
}
module.exports = locationdata;