$(document).ready(function() {
    saveMentor();
});

function saveMentor(){
    $('#saveBtn').click(function(e) {
        e.preventDefault(); // 폼의 기본 동작 방지

        var data = {
            mentorId : mentorId,
            email: $('#email').val().trim(),
            phone: $('#phone').val().trim(),
            content: $('#content').val(),
            startTime: $('#startTime').val(),
            endTime : $('#endTime').val(),
            mentoringDate: $("#datepicker").val()
        };

        console.log(data)

        // 유효성 검사
        for (var key in data) {
            if (!data[key]) {
                alert("빈칸으로 둘 수 없습니다.");
                return;
            }
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/mentoring/'+ mentorId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function(response) {
                alert(" 멘토링이 신청되었습니다.");
                window.location.replace("/user/mentoring");

            },
            error: function( error) {
                console.log(error);
                alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
            }
        });
    });
}

// 날짜와 멘토 id를 주고 해당 요일에 멘토링 시간을 받아오는 함수
function sendDayOfWeek(id){

    var value = $("#datepicker").val();
    var selectedDate = $("#datepicker").datepicker('getDate');
    var dayOfWeek = selectedDate.toLocaleDateString('en-US', { weekday: 'short' }); // 영어 요일 구하기
    var dayOfWeek = selectedDate.getDay(); // 0 (일요일)부터 6 (토요일)까지의 숫자를 반환
    var dayOfWeek = (selectedDate.getDay() + 6) % 7; // 0 (월요일)부터 6 (일요일)까지의 숫자로 변환
    var adjustedDayOfWeek = (dayOfWeek === 0) ? 6 : dayOfWeek - 1; // 0 (월요일)부터 5 (토요일)까지의 숫자로 변환

    console.log(id)

    var mentoringDate = $("#datepicker").val();
    var url = "/api/v1/mentorTime/day/"+ dayOfWeek +"/"+ id +"/"+ mentoringDate;
    $.ajax({
        type: 'GET',
        url: url,
        contentType:'charset=utf-8',
        success: function(response) {
            $("#timeBox").empty();
            console.log(response)

            var data = response.data;

            if ( data != null ) {
                for (var i = 0; i < data.length; i++) {
                    var buttonText = data[i].startTime + " : " +  data[i].endTime;

                    var html = "";
                    if (data[i].state == '1') {
                        html = "<button class='timeButton ' onclick='mentoringTime(\"" + data[i].startTime + "\", \"" + data[i].endTime + "\", \"" + data[i].state + "\")'>" + buttonText + "</button>";
                    } else {
                        html = "<button class='timeButton inactive'>" + buttonText + "</button>";
                    }

                    $("#timeBox").append(html);
                }
                // mentoringTime 함수 호출
                mentoringTime(data[0].startTime, data[0].endTime, data[0].state);
            }
        },
        error: function( error) {
            console.log(error);
            alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    });

}

// mentoringTime 함수 수정
function mentoringTime(startTime, endTime, state){

    if ( state == '1' ){
        $("#startTime").val(startTime);
        $("#endTime").val(endTime);
    }
}
