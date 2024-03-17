function sendSearch(){
    var checkedValues = [];

    $('.checkbox:checked').each(function() {
        checkedValues.push($(this).val());
    });

    var filter = checkedValues.join(',');

    if( checkedValues.length == 1 ){
        filter = checkedValues[0]
    }

    var search = $("#search").val();

    console.log(search)

    if( search == null ){
        alert("검색어를 입력해주세요.")
        return false;
    }

    window.location.replace("/?filter="+filter+"&search=" + search );
}