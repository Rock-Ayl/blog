//写入评论
function writeComment() {
    var commentEmail = document.getElementById("commentEmail").value;
    var commentName = document.getElementById("commentName").value;
    var commentInfo = document.getElementById("commentInfo").value;
    //昵称判空
    if (commentName.length == 0) {
        alert("昵称不能为空!")
    } else {
        //content判空
        if (commentInfo.length == 0) {
            alert("请随便写点什么在提交OVO.")
        } else {
            //post请求写评论
            $.ajax({
                type: "post",
                url: "/CommentService/WriteComment",
                data: { commentInfo: commentInfo, commentName: commentName, commentEmail: commentEmail },
                success: function (data) {
                    alert(data.message)
                }
            });
        }
    }
}