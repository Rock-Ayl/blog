var LANGUAGE_CODE = "en_US"; //标识语言
/*国际化配置信息*/
function loadProperties(type) {
    jQuery.i18n.properties({
        name: 'strings', // 资源文件名称
        path: 'properties', // 资源文件所在目录路径
        mode: 'map', // 模式：变量或 Map
        language: type, // 对应的语言
        cache: false,
        encoding: 'UTF-8',
        callback: function () { // 回调方法
            //左下 右下
            $('#sl').html($.i18n.prop('string_sl'))
            $('#string_version').html($.i18n.prop('string_version'))
            $('#string_RecordNum').html($.i18n.prop('string_RecordNum'))
            $('#skillBy').html($.i18n.prop('string_skillBy'))
            $('#webSite').html($.i18n.prop('string_webSite'))
            $('#anyongliang').html($.i18n.prop('string_anyongliang'))
            $('#switch').html($.i18n.prop('string_switch'))
            $('#string_loginName').html($.i18n.prop('string_loginName'))
            $('#string_loginState').html($.i18n.prop('string_loginState'))
            //主页目录
            $('#skillBolg').html($.i18n.prop('string_skillBolg'))
            $('#string_Sharing').html($.i18n.prop('string_Sharing'))
            $('#string_UtilOnline').html($.i18n.prop('string_UtilOnline'))
            $('#resume').html($.i18n.prop('string_resume'))
            $('#aboutMe').html($.i18n.prop('string_aboutMe'))
            $('#resourceFolder').html($.i18n.prop('string_resourceFolder'))
            //主页标题
            $('#string_h1').html($.i18n.prop('string_h1'))
            $('#string_h2_1').html($.i18n.prop('string_h2_1'))
            $('#string_h2_2').html($.i18n.prop('string_h2_2'))
            $('#string_Tit').html($.i18n.prop('string_Tit'))
            $('#string_loginInsert').html($.i18n.prop('string_loginInsert'))
            //次级页面返回按钮
            $('#string_returnMain1').html($.i18n.prop('string_returnMain1'))
            $('#string_returnMain2').html($.i18n.prop('string_returnMain2'))
            $('#string_returnMain3').html($.i18n.prop('string_returnMain3'))
            $('#string_returnMain4').html($.i18n.prop('string_returnMain4'))
            $('#string_returnMain5').html($.i18n.prop('string_returnMain5'))
            $('#string_returnMain6').html($.i18n.prop('string_returnMain6'))
            //次级页面标题
            $('#string_csdnUrl').html($.i18n.prop('string_csdnUrl'))
            $('#string_GitHubUrl').html($.i18n.prop('string_GitHubUrl'))
            $('#string_commintsBuilding').html($.i18n.prop('string_commintsBuilding'))
            $('#string_aboutMe2').html($.i18n.prop('string_aboutMe2'))
            $('#string_ResumeOnline').html($.i18n.prop('string_ResumeOnline'))
            $('#string_ResumeDownload').html($.i18n.prop('string_ResumeDownload'))
        }
    });
}

function switchLang() {
    LANGUAGE_CODE = LANGUAGE_CODE == 'zh_CN' ? 'en_US' : 'zh_CN';
    loadProperties(LANGUAGE_CODE);
}

$(document).ready(function () {
    LANGUAGE_CODE = jQuery.i18n.normaliseLanguageCode({}); //获取浏览器的语言
    loadProperties(LANGUAGE_CODE);
})