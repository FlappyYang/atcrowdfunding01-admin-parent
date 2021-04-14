<%--
  Created by IntelliJ IDEA.
  User: 33246
  Date: 2021/4/1
  Time: 20:35:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- http://localhost:8080/atcrowdfunding02_admin_webui/test/ssm.html -->
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                $.ajax({
                    "url": "send/array/one.html",
                    "type": "post",
                    "data": {
                        "array[]":[5,8,12]
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (request) {
                        alert(request);
                    }
                });
            });
            $("#btn2").click(function () {
                $.ajax({
                    "url": "send/array/two.html",
                    "type": "post",
                    "data": {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 2
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (request) {
                        alert(request);
                    }
                });
            });
            $("#btn3").click(function () {
                var array = [5,8,12];
                // 将JSON数组转换为JSON字符串
                var requsetBody = JSON.stringify(array);
                $.ajax({
                    "url": "send/array/three.html",
                    "type": "post",
                    "data": requsetBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (request) {
                        alert(request);
                    }
                });
            });
            $("#btn4").click(function () {
                // 准备要发送的数据
                var student = {
                    "stuId": 5,
                    "stuName": "tom",
                    "address": {
                        "province": "广东",
                        "city": "city",
                        "street":"street"
                    },
                    "subjectList":[{
                        "subjectName": "subjectName1",
                        "subjectScore": 20
                    },{
                        "subjectName":"subjectName2",
                        "subjectScore": 30
                    }],
                    "map": {
                        "k1":"v1",
                        "k2":"v2"
                    }
                };
                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/compose/object.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",
                    "success": function (response) {
                        alert(response + " success");
                    },
                    "error": function (response) {
                        alert(response +" error");
                    }
                })
            })
            $("#btn5").click(function () {
                layer.msg("layer");
            })
        })
    </script>
</head>
<body>
    <a href="test/ssm.html">SSM</a><br/>
    <button id="btn1">Send [5,8,12] one</button>
    <button id="btn2">Send [5,8,12] two</button>
    <button id="btn3">Send [5,8,12] three</button>
    <button id="btn4">Send Compose Object </button>
    <button id="btn5">点我弹框  layer</button>
</body>
</html>
