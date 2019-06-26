//第几页
pageIndex = 1;
//每页几条评论
pageSize = 10;
//最大页码
pageMaxIndex = 1;

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
                    //刷新评论状态
                    readComment(pageIndex, pageSize);
                }
            });
        }
    }
}

//读评论
function readComment(pageIndex, pageSize) {
    //post请求读评论
    $.ajax({
        type: "post",
        url: "/CommentService/ReadComment",
        data: {pageIndex: pageIndex, pageSize: pageSize},
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
                    //组装要拼装的参数
                    var commentId = items[i].id;
                    var userId = items[i].userId;
                    var userName;
                    if (userId == -1) {
                        //用匿名
                        userName = items[i].userName;
                    } else {
                        //用用户名
                        userName = items[i].realName;
                    }
                    var content = items[i].content;
                    var timestamp = timestampToTime(items[i].timestamp);
                    var email;
                    if (userId == -1) {
                        //用匿名
                        email = items[i].email;
                    } else {
                        //用真实的email
                        email = items[i].realEmail;
                    }
                    var userRole;
                    if (userId == -1) {
                        userRole = "匿名用户";
                    } else {
                        userRole = items[i].role;
                    }
                    var divString = "<div class=\"margin-top-20\"><div class=\"col-sm-12 col-md-12 col-lg-12 margin-top-20 \"><span class=\"label label-default\" style=\"color: #FFFFFF;font-size: 12px; margin: 0px 5px\">用户名:" + userName + "</span><span class=\"label label-default\" style=\"color: #FFFFFF;font-size: 12px; margin: 0px 5px\">用户类型:" + userRole + "</span><span class=\"label label-default\" style=\"color: #FFFFFF;font-size: 12px; margin: 0px 5px\">邮箱:" + email + "</span></div><div class=\"col-sm-2 col-md-2 col-lg-2 margin-top-20\"><img src=\"https://anyongliang.oss-cn-beijing.aliyuncs.com/blog/web/user/%E5%A4%B4%E5%83%8F%20%E7%94%B7%E5%AD%A9.png\"></div><div class=\"col-sm-7 col-md-7 col-lg-7 margin-top-20\"><span style=\"color: #FFFFFF;font-size: 15px;\">" + content + "</span></div><div class=\"col-sm-3 col-md-3 col-lg-3 margin-top-20\"><span style=\"color: #FFFFFF;font-size: 15px;\">评论时间:</span><br><span style=\"color: #FFFFFF;font-size: 15px;\">" + timestamp + "</span></div><div class=\"col-sm-12 col-md-12 col-lg-12 margin-top-20\"                                             style=\"border-bottom: 1px solid #FFFFFF;\"><span style=\"color: #ffff00;font-size: 12px;\">楼层:" + commentId + "</span></div></div>"
                    commentList.innerHTML += divString;
                }
                //设置最大页数
                pageMaxIndex = (data.count) / pageSize;
            }
        }
    });
}

function previousComment() {
    if (pageIndex > 1) {
        pageIndex = pageIndex - 1;
        readComment(pageIndex, pageSize);
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    } else {
        alert("到顶了！");
    }
}

function nextComment() {
    //判断页数是否达到最大值
    if (pageIndex >= pageMaxIndex) {
        alert("到底了!");
    } else {
        pageIndex = pageIndex + 1;
        readComment(pageIndex, pageSize);
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    }
}

function timestampToTime(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y + M + D + h + m + s;
}


/*如果没有cookie,初始化*/
function initCookieId() {
    //如果不存在cookieId,从服务器获取
    if (document.cookie.indexOf("cookieId=") == -1) {
        //post请求cookieId
        $.ajax({
            type: "post",
            url: "/UserService/GetCookieId",
            data: {},
            success: function (data) {
                console.log(data)
                var cookieString = "cookieId" + "=" + data.cookieId;
                document.cookie = cookieString;
            }
        });
    }
}
