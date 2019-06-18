var host = "http://www.anyongliang.cn";

//home跳转
function doMain() {
    window.location.href = host + "/Page/Index";
}

//错误跳转
function doError() {
    window.location.href = host + "/Page/Error";
}

//登录跳转
function doLogin() {
    window.location.href = host + "/Page/UserLogin";
}

//简历
function doResume() {
    window.location.href = host + "/Page/Resume";
}

//刷新页面
function reload() {
    window.location.reload()
}
