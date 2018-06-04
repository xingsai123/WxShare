<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/30
  Time: 16:06下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">

       /* $(function () {

        });*/

        $(document).ready(function () {



            $.ajax({
                url:"${pageContext.request.contextPath}/getWxInfo",
                type:"get",
                dataType:"json",
                data:{
                    url1:"http://www.maomi.xn--fiqs8s/getDetails/1524409397585"
                  /*  opreatopn:"dd"*/
                },
                success:function(res){
                  console.log("请求成功！")
                },
                error:function() {
                }
            });
        })





    </script>
</head>
<body>
<div>TEST</div>
<a href="${pageContext.request.contextPath}/getWxInfo1">点击测试</a>
</body>
</html>
