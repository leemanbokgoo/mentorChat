//전역변수
var timeSlots = [];

$(document).ready(function() {

    addTime();
    settingDays();
    saveMentor();
});

function saveMentor(){
     $('#saveBtn').click(function(e) {
         e.preventDefault(); // 폼의 기본 동작 방지

         var data = {
             title: $('#title').val().trim(),
             occupation: $('#occupation').val().trim(),
             job: $('#job').val().trim(),
             career: $('#career').val().trim(),
             introduction: $('#introduction').val().trim(),
             notification: $('#notification').val().trim(),
             price: $('#price').val().trim(),
             mentoringTime : $('#mentoringTime').val().trim(),
             mentoringTimeList: timeSlots
         };

         console.log(data)

       $('.checkbox:checked').each(function() {
            data.daysOfWeek.push($(this).val());
        });

         // 유효성 검사
         for (var key in data) {
             if (!data[key]) {
                 alert("빈칸으로 둘 수 없습니다.");
                 return;
             }
         }

         $.ajax({
             type: 'POST',
             url: '/api/v1/mentor/',
             dataType: 'json',
             contentType:'application/json; charset=utf-8',
             data: JSON.stringify(data),
             success: function(response) {
                 alert(" 멘토설정이 등록 되었습니다.");
                 window.location.replace("/mentor");

             },
             error: function( error) {
                 console.log(error);
                 alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
             }
         });
     });
}