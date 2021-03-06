/**
 * 变量
 */
pageIndex = 1; //评论第几页
pageSize = 10; //评论每页几条
pageMaxIndex = 1;  //评论最大页码
var user; //登录的用户信息

/**
 * 初始化评论页
 */
function initComment() {
    pageIndex = 1;
    pageSize = 10;
    pageMaxIndex = 1;
}

/**
 * 写评论
 */
function writeComment() {
    var commentEmail = document.getElementById("commentEmail");
    var commentName = document.getElementById("commentName");
    var commentInfo = document.getElementById("commentInfo");
    //昵称判空
    if (isEmpty(commentName)) {
        alert("昵称不能为空!")
    } else {
        //content判空
        if (isEmpty(commentInfo)) {
            alert("请随便写点什么在提交OVO.")
        } else {
            //post请求写评论
            $.ajax({
                type: "post",
                url: "/CommentService/WriteComment",
                data: {
                    commentInfo: commentInfo.value,
                    commentName: commentName.value,
                    commentEmail: commentEmail.value
                },
                headers: {
                    cookieId: getCookie("cookieId")
                },
                success: function (data) {
                    //如果处于未登录状态
                    if (user == null) {
                        commentEmail.value = "";
                        commentName.value = "";
                    }
                    commentInfo.value = "";
                    //初始化顶端
                    initComment();
                    //刷新
                    readComment(pageIndex, pageSize);
                }
            });
        }
    }
}

/**
 * 读评论
 * @param pageIndex
 * @param pageSize
 */
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
                    if (isEmpty(userName)) {
                        userName = "-"
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
                    if (isEmpty(email)) {
                        email = "-"
                    }
                    var userRole;
                    if (userId == -1) {
                        userRole = "匿名用户";
                    } else {
                        userRole = items[i].role;
                    }
                    if (isEmpty(userRole)) {
                        userRole = "-"
                    }
                    var divString = "<div class=\"margin-top-20\"><div class=\"col-sm-12 col-md-12 col-lg-12 margin-top-20 \"><span class=\"label label-default\" style=\"color: #FFFFFF;font-size: 12px; margin: 0px 5px\">用户名:" + userName + "</span><span class=\"label label-default\" style=\"color: #FFFFFF;font-size: 12px; margin: 0px 5px\">用户类型:" + userRole + "</span><span class=\"label label-default\" style=\"color: #FFFFFF;font-size: 12px; margin: 0px 5px\">邮箱:" + email + "</span></div><div class=\"col-sm-2 col-md-2 col-lg-2 margin-top-20\"><img src=\"https://anyongliang.oss-cn-beijing.aliyuncs.com/blog/web/user/%E5%A4%B4%E5%83%8F%20%E7%94%B7%E5%AD%A9.png\"></div><div class=\"col-sm-7 col-md-7 col-lg-7 margin-top-20\"><span style=\"color: #FFFFFF;font-size: 15px;\">" + content + "</span></div><div class=\"col-sm-3 col-md-3 col-lg-3 margin-top-20\"><span style=\"color: #FFFFFF;font-size: 15px;\">评论时间:</span><br><span style=\"color: #FFFFFF;font-size: 15px;\">" + timestamp + "</span></div><div class=\"col-sm-12 col-md-12 col-lg-12 margin-top-20\"                                             style=\"border-bottom: 1px solid #FFFFFF;\"><span style=\"color: #ffff00;font-size: 12px;\">楼层:" + commentId + "</span></div></div>"
                    commentList.innerHTML += divString;
                }
                //设置最大页数
                pageMaxIndex = (data.totalCount) / pageSize;
            }
        }
    });
}

/**
 * 上一页评论
 */
function previousComment() {
    if (pageIndex > 1) {
        pageIndex = pageIndex - 1;
        readComment(pageIndex, pageSize);
        document.body.scrollTop = document.documentElement.scrollTop = 0;
    } else {
        alert("到顶了！");
    }
}

/**
 * 下一页评论
 */
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

/**
 * 通用方法-时间戳转时间
 * @param timestamp
 * @returns {*}
 */
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


/**
 * 如果没有cookie,初始化
 */
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


/**
 * 通用方法-根据cookie的key获取对应value
 *
 * @param cookieName
 * @returns {string}
 */
function getCookie(cookieName) {
    var allcookies = document.cookie;
    var cookiePos = allcookies.indexOf(cookieName); //索引的长度
    if (cookiePos != -1) {
        // 把cookie_pos放在值的开始，只要给值加1即可。
        cookiePos += cookieName.length + 1;
        var cookieEnd = allcookies.indexOf(";", cookiePos);

        if (cookieEnd == -1) {
            cookieEnd = allcookies.length;
        }
        var value = decodeURI(allcookies.substring(cookiePos, cookieEnd));
    }
    return value;
}

/**
 * 读取用户信息
 */
function readUser() {
    $.ajax({
        type: "post",
        url: "/UserService/GetUserInfoByCookieId",
        data: {},
        headers: {
            cookieId: getCookie("cookieId")
        },
        success: function (data) {
            //如果成功读取到数据,更新到菜单栏
            if (data.isSuccess) {
                //给用户信息常量赋值
                user = data;
                var userName = data.userName;
                var role = data.role;
                var email = data.email;
                console.log(userName);
                console.log(role);
                //给状态赋值
                document.getElementById("userName").innerHTML = userName;
                document.getElementById("role").innerHTML = role;
                //给评论赋值
                document.getElementById("commentEmail").value = email;
                document.getElementById("commentName").value = userName;
                //设置不可输入,但可以提交数据
                document.getElementById("commentEmail").disabled = "true";
                document.getElementById("commentName").disabled = "true";
                //将loginOut显露,登录隐藏
                document.getElementById("string_loginInsert").style = "display:none;";
                document.getElementById("string_loginOut").style = "color: yellow;cursor: pointer;float: right;";
            }
        }
    });
}

/**
 * 注册并登陆
 */
function addUser() {
    //获取输入的用户名+密码
    var userName = document.getElementById("userName").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    if (!isEmpty(userName)) {
        if (!isEmpty(password)) {
            if (!isEmpty(email)) {
                $.ajax({
                    type: "post",
                    url: "/UserService/AddUser",
                    data: {userName: userName, password: password, email: email},
                    success: function (data) {
                        //如果注册成功,直接登陆
                        if (data.isSuccess) {
                            login()
                        } else {
                            alert("注册失败,请检查输入的原因!");
                        }
                    }
                });
            } else {
                alert("邮箱不能为空.");
            }
        } else {
            alert("密码不能为空.");
        }
    } else {
        alert("用户名不能为空.");
    }
}

/**
 * 登录
 */
function login() {
    //获取输入的用户名+密码
    var userName = document.getElementById("userName").value;
    var password = document.getElementById("password").value;
    if (!isEmpty(userName)) {
        if (!isEmpty(password)) {
            $.ajax({
                type: "post",
                url: "/UserService/Login",
                data: {userName: userName, password: password},
                headers: {
                    cookieId: getCookie("cookieId")
                },
                success: function (data) {
                    //如果登录成功
                    if (data.isSuccess) {
                        //跳转到主页面
                        doMain();
                    } else {
                        alert("登录失败,请确认用户名+密码是完全正确的!");
                    }
                }
            });
        } else {
            alert("密码不能为空.")
        }
    } else {
        alert("用户名不能为空.")
    }
}

/**
 * 退出登录
 */
function loginOut() {
    $.ajax({
        type: "post",
        url: "/UserService/LoginOut",
        data: {},
        headers: {
            cookieId: getCookie("cookieId")
        },
        success: function (data) {
            //如果登录成功
            if (data.isSuccess) {
                //跳转到主页面
                doMain();
            } else {
                alert("退出失败,请刷新一下!");
            }
        }
    });
}

/**
 * 通用方法-String 判空
 * @param obj
 * @returns {boolean}
 */
function isEmpty(obj) {
    if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true;
    } else {
        return false;
    }
}