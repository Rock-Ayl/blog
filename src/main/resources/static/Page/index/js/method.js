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
                data: {commentInfo: commentInfo, commentName: commentName, commentEmail: commentEmail},
                success: function (data) {
                    alert(data.message)
                }
            });
        }
    }
}

//读评论
function readComment() {
    //post请求读评论
    $.ajax({
        type: "post",
        url: "/CommentService/ReadComment",
        data: {},
        success: function (data) {
            //判断请求是否成功
            if (data.isSuccess) {
                //成功的话,取出items,遍历,给div
                var items = data.items;
                //获取div并初始化
                var commentList = document.getElementById("commentList");
                commentList.innerHTML = null;
                //组装评论
                for (var i = 0; i < items.length; i++) {
                    var userId = items[i].id;
                    var userName = items[i].userName;
                    var content = items[i].content;
                    var timestamp = items[i].timestamp;
                    var email = items[i].email;
                    commentList.innerHTML += "<div  style=\"cursor: pointer;color: yellow;\">" + userId + "</div>" +
                        "<div  style=\"cursor: pointer;color: yellow;\">" + userName + "</div>" +
                        "<div  style=\"cursor: pointer;color: yellow;\">" + content + "</div>" +
                        "<div  style=\"cursor: pointer;color: yellow;\">" + timestamp + "</div>" +
                        "<div  style=\"cursor: pointer;color: yellow;\">" + email + "</div>";
                }
            }
        }
    });
}