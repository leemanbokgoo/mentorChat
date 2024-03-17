//전역변수
var timeSlots = [];

$(document).ready(function() {
    validationImg();
    updateProfile();
});



function validationImg(){
     $('#profileImg').change(function() {
       var fileSize = this.files[0].size; // 파일 크기 (바이트 단위)
       var maxSize = 1000 * 1024; // 최대 허용 파일 크기 (100KB)

       if (fileSize > maxSize) {
           alert('이미지 파일 크기는 1MB 이하여야 합니다.');
           $(this).val(''); // 파일 입력 필드 비우기
       }
    });
}

function uploadImg(file){
        console.log("???????")
        var formData = new FormData();
        formData.append('image', file); // FormData에 이미지 파일 추가

        $.ajax({
            url: '/api/v1/S3/profileImg/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log(response);
            },
            error: function(error) {
                console.error(error);
            }
        });
}

function deleteImg(){
$.ajax({
        type: 'GET',
        url: "/api/v1/S3/profileImg/delete?imgAddr="+ oriProfileImg,
        contentType:'charset=utf-8',
        success: function(response) {
                console.log(response);
        },
        error: function( error) {
            console.log(error);
            alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    });

}

function uploadData(){

    var data = {
      name: $('#name').val().trim(),
    };

      // 유효성 검사
      for (var key in data) {
          if (!data[key]) {
              alert("빈칸으로 둘 수 없습니다.");
              return;
          }
      }

      $.ajax({
          type: 'PUT',
          url: '/api/v1/mypage',
          dataType: 'json',
          contentType:'application/json; charset=utf-8',
          data: JSON.stringify(data),
          success: function(response) {
              console.log(response);
              alert(" 마이페이지가 수정 되었습니다.");
          },
          error: function( error) {
              console.log(error);
              alert("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
          }
      });
}

 function updateProfile(){
     $('#updateBtn').click(function(e) {
          e.preventDefault();
          uploadData();

        // 기존 이미지가 있다면 삭제
        if (oriProfileImg != null ){
            deleteImg();
        }

        // 파일이 있다면
        var file = $('#profileImg')[0].files[0]
        if( file != null ){
            uploadImg(file);
        }

      });
}
