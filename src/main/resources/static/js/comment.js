
function post() {
    var questionId = $("#question_id").val();
    var content = $("#content").val();

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parent_id": questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            console.log(response.code);
            if(response.code == 200){
                $("#comment_section").hide();
            }else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}