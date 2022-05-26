var request = require('request');

var url = 'http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll';
var queryParams = '?' + encodeURIComponent('serviceKey') + '=39ekznXIYt+J64ptAzOuc/AGITrE4ju50rRkR6mc1/lsgwNln9+l33LiEPxKA5lQduSuD2ZTAHRBH7WGXyCH/Q=='; /* Service Key*/
queryParams += '&' + encodeURIComponent('busRouteId') + '=' + encodeURIComponent('100100500'); /* */

request({
    url: url + queryParams,
    method: 'GET'
}, function (error, response, body) {
    console.log('Status', response.statusCode);
    console.log('Headers', JSON.stringify(response.headers));
    console.log('Reponse received', body);
});