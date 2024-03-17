// 멘토링 받는지 안 받을지 설정하는 함수
function stateMentoring(mentoringId,state){

    console.log(state)

     $.ajax({
         type: 'PUT',
         url: '/api/v1/mentoring/state?mentoringId='+ mentoringId +"&state=" + state,
         dataType: 'json',
         contentType:'application/json; charset=utf-8',
         success: function(response) {

         console.log(response)
            if( response.result == true ){
                var string = "";

                if (state == 1) {
                    string = "예약이 확정되었습니다."

                } else if( state == 0) {
                    string = "예약이 취소되었습니다."
                }
                alert(string);
                location.reload();
            }

         },
         error: function( error) {
             console.log(error);
             alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
         }
     });
}