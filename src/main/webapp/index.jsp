<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/30
  Time: 16:06下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <%--引入js文件--%>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <%--通过config接口注入权限验证配置--%>
   <script type="text/javascript">
    $(function(){

    $.ajax({
    url:"${pageContext.request.contextPath}/getWxInfo1",
    type:"post",
    dataType:"json",
    data:{
    url1:"http://www.maomi.xn--fiqs8s/getDetails/1524409397585"
    /*  opreatopn:"dd"*/
    },
    success:function(res){
    window.noncestr=res.noncestr;
    window.timestamp=res.timestamp;
    window.signature=res.signature;
    console.log("请求成功！"+noncestr)
    },
    error:function() {
    console.log("数据返回失败！")
    }
    });
    })
   </script>


    <script type="text/javascript">
        wx.config({
            debug: false,
            appId: 'wx246bae8548b676c6',
            timestamp: timestamp,
            nonceStr: noncestr,
            signature: signature,
            jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage'
            ]
        });
        wx.ready(function () {
            <%--公共方法--%>
            var shareData = {
                title: '${title}',
                desc: '${description}',
                link: '${url}',
                imgUrl: '${headImgUrl}',
                success: function (res) {
                    //alert('已分享');
                },
                cancel: function (res) {
                }
            };
            <%--分享给朋友接口--%>
            wx.onMenuShareAppMessage({
                title: '${title}',
                desc: '${description}',
                link: '${url}',
                imgUrl: '${headImgUrl}',
                trigger: function (res) {
                    //  alert('用户点击发送给朋友');
                },
                success: function (res) {
                    //alert('已分享');
                },
                cancel: function (res) {
                    //alert('已取消');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
            <%--分享到朋友圈接口--%>
            wx.onMenuShareTimeline(shareData);
        });
        <%--处理失败验证--%>
        wx.error(function (res) {
            alert("error: " + res.errMsg);
        });
    </script>
</head>
<body>
<div>TEST</div>
<a href="${pageContext.request.contextPath}/getWxInfo">点击测试</a>
</body>
</html>
