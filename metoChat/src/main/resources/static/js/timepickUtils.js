var week = ["월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"];

// 요일 selectbox 셋팅
function settingDays(){

    var dayOfWeekSelect = $("#daySelect");
    // 요일을 select 요소에 추가
    $.each(week, function(index, day) {
        dayOfWeekSelect.append($("<option>", {
            value: index,
            text: day
        }));
    });
}

// 시간 선택 후 등록
function addTime(){
    $("#addTimeBtn").click(function() {

        var dayOfWeek = $("#daySelect").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();

        // 중복 검사
         var isDuplicate = false;
         $.each(timeSlots, function(index, item) {
             if (item.dayOfWeek ==dayOfWeek && item.startTime == startTime && item.endTime == endTime) {
                 isDuplicate = true;
                 return false;
             }
         });

         if (isDuplicate) {
             alert("이미 선택하신 시간입니다.");
             return;
         }

        if (endTime <= startTime) {
            alert("멘토링 종료시간이 시작시간보다 전일 수 없습니다.");
            return;
        }

        var timeSlot = {
            dayOfWeek: dayOfWeek,
            startTime: startTime,
            endTime: endTime
        };

        timeSlots.push(timeSlot);
        console.log(timeSlots)

        // 화면에 선택한 값 출력
        var $newItem = $("<p class='mentoring-item'>").text( week[dayOfWeek] + " | " + startTime + " ~ " + endTime ).appendTo("#mentoringTimeBox");

        // 선택 값 배열 & 화면에서 삭제
        $newItem.click(function() {
            var index = $("#mentoringTimeBox").index($newItem);
            timeSlots.splice(index, 1);
            $newItem.remove();
        });

        console.log( timeSlots)
    });
}