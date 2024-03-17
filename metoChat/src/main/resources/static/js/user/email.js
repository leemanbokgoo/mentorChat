var timerElement = $("#timer");
var timer;
var duration = 180; // 타이머 시간 (초)
var startTime;

$(document).ready(function(){
    sendEmailNum();
    getEmailNum();
    // 이메일 다시 요청
    resendEmail();
    // 타이머
    startTime = Date.now();
    startTimer();
 });


// 이메일 인증을 위한 랜덤 6자리 받아오기
function getEmailNum(){

     $.ajax({
         type: 'GET',
         url: '/api/v1/email/',
         dataType: 'json',
         contentType:'application/json; charset=utf-8',
         success: function(response) {
                console.log(response)

            if ( response.result == true ){
                console.log(response.data)
                setCookie('email', response.data,3);
            }
         },
         error: function( error) {
             console.log(error);
             alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
         }
     });
}

// 이메일 다시 요청
function resendEmail(){
    $('#emailSendBtn').click(function(e) {
        // 새 이메일 보내기
        getEmailNum();
        // 기존에 돌던 setInterval 멈추기
        stopTimer();
        startTime = Date.now();
        startTimer();
    });
}

// 이메일 인증 요청
function sendEmailNum(){

    $('#emailNumBtn').click(function(e) {
        var sendRandomNum = getCookie("email");
         $.ajax({
             type: 'GET',
             url: '/api/v1/email/matches' ,
             dataType: 'json',
             data: {
                     sendRandomNum: sendRandomNum, // 요청 파라미터
                     userNum: $("#email_num").val().trim()
                     // 필요한 만큼의 요청 파라미터 추가
                 },
             contentType:'application/json; charset=utf-8',
             success: function(response) {

               if (response.result === true && response.data === true) {
                   window.location.replace("/mypage/update");

               } else if (response.result === true && response.data === false) {
                   alert("인증번호를 확인해주세요");
               }
             },
             error: function( error) {
                 console.log(error);
                 alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
             }
         });
    });
}

//타이머시작
function startTimer() {
  timer = setInterval(function() {
      var elapsedTime = Math.floor((Date.now() - startTime) / 1000);
      var remainingTime = duration - elapsedTime;

      // 만약 타이머가 끝난다면
      if (remainingTime <= 0) {
          clearInterval(timer);
          timerElement.text("00:00").css("color", "red");
          deleteCookie("email")

      } else {
          var minutes = Math.floor(remainingTime / 60);
          var seconds = remainingTime % 60;

          // 1자리 숫자인 경우 앞에 0을 붙여서 표시
          var formattedMinutes = ("0" + minutes).slice(-2);
          var formattedSeconds = ("0" + seconds).slice(-2);

          timerElement.text(formattedMinutes + ":" + formattedSeconds);
      }
  }, 1000);
}
function stopTimer() {
    clearInterval(timer);
}