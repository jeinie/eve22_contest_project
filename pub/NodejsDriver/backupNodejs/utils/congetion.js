// 혼잡도(0 : 없음, 3 : 여유, 4 : 보통, 5 : 혼잡, 6 : 매우혼잡)
function drawCongetion(congetion) {
    var canvas = document.getElementById("myCanvas1"); // canvas에 혼잡도 표시
    var ctx = canvas.getContext("2d");
    
    if(congetion == 0 || congetion == 3) { // 혼잡도가 '없음' or '여유'인 경우
        ctx.clearRect(130, 0, 40, 40);
        ctx.clearRect(65, 0, 40, 40);
        ctx.beginPath();
        ctx.fillStyle = "green";
        ctx.arc(20, 20, 20, 0, Math.PI * 2, true);
        ctx.fill(); // 초록색 동그라미
    }
    else if(congetion == 4) { // 혼잡도가 '보통'인 경우
        ctx.clearRect(0, 0, 40, 40);
        ctx.clearRect(130, 0, 40, 40);
        ctx.beginPath();
        ctx.fillStyle = "yellow";
        ctx.arc(85, 20, 20, 0, Math.PI * 2, true);
        ctx.fill(); // 노란색 동그라미
    }
    else { // 혼잡도가 '혼잡' or '매우 혼잡'인 경우
        ctx.clearRect(40, 0, 40, 40);
        ctx.clearRect(65, 0, 40, 40);
        ctx.beginPath();
        ctx.fillStyle = "red";
        ctx.arc(150, 20, 20, 0, Math.PI * 2, true);
        ctx.fill(); // 빨간색 동그라미
    }
} 