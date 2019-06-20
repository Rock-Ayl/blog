//写入评论
function writeComment() {
    var commentEmail = document.getElementById("commentEmail").value;
    var commentName = document.getElementById("commentName").value;
    var commentInfo = document.getElementById("commentInfo").value;
    $.ajax({
        type: "post",
        url: "/CommentService/WriteComment",
        data: {commentInfo: commentInfo, commentName: commentName, commentEmail: commentEmail},
        success: function (data) {
            alert(data.message)
        }
    });
}