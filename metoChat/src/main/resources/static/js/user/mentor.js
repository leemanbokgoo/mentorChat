// 멘토링 받는지 안 받을지 설정하는 함수
function mentoringState(state){

    console.log(state)

    if( state == false ){
        state = true;
    } else {
        state = false;
    }

     $.ajax({
         type: 'PUT',
         url: '/api/v1/mentor/state/'+ state,
         dataType: 'json',
         contentType:'application/json; charset=utf-8',
         success: function(response) {

            if( response.data == false ){
                alert(" 멘토링 신청을 받지않도록 변경되었습니다.");
                location.reload();

            } else if (response.data == true) {
                alert(" 멘토링 신청을 받도록 변경되었습니다.");
                location.reload();
            }
             window.location.replace("/mentor");
         },
         error: function( error) {
             console.log(error);
             alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
         }
     });
}