//전역변수
var timeSlots = [];

$(document).ready(function() {
    setTime();
    settingDays();
    addTime();

    deleteTimeSlots();
    updateMentor();
});

function deleteTimeSlots(){
    var mentoringItems = document.querySelectorAll('.mentoring-item');

    mentoringItems.forEach(function(item, index) {
        item.addEventListener('click', function() {
            // 화면제거
            item.remove();
            // 배열에서 요소 제거
            if (index !== -1) {
                timeSlots.splice(index, 1);
            }
            console.log(timeSlots);
        });
    });
}

// 조회환 시간 배열에 넣기
function setTime(){


    for (var i = 0; i < mentorTimeList.length; i++) {
            // mentorTimeList에서 현재 인덱스의 mentorTime을 가져옵니다.
            var mentorTime = mentorTimeList[i];
            var dayOfWeek = week.indexOf(mentorTime.dayOfWeek);

            var timeSlot = {
                dayOfWeek: dayOfWeek,
                startTime: mentorTime.startTime,
                endTime: mentorTime.endTime
            };
            timeSlots.push(timeSlot)
    }
 }

 function updateMentor(){
     $('#updateBtn').click(function(e) {



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
              type: 'PUT',
              url: '/api/v1/mentor/',
              dataType: 'json',
              contentType:'application/json; charset=utf-8',
              data: JSON.stringify(data),
              success: function(response) {
                  console.log(response);
                  alert(" 멘토설정이 수정 되었습니다.");
                  window.location.replace("/mentor");

              },
              error: function( error) {
                  console.log(error);
                  alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
              }
          });
      });
}
