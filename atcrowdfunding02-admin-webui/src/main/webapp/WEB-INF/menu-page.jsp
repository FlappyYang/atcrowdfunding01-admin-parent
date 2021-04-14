<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="/WEB-INF/include_head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        generateTree();
        // 给添加子节点按钮绑定单击响应函数
        $("#treeDemo").on("click",".addBtn",function () {
            // 将当前节点的id，作为新节点的pid保存到全局变量
            window.pid = this.id;
            $("#menuAddModal").modal("show");
            return false;
        });
        // 给编辑子节点按钮绑定单击响应函数
        $("#treeDemo").on("click",".editBtn",function () {
            // 将当前节点的id，作为新节点的pid保存到全局变量
            window.id = this.id;
            // 打开模态框
            $("#menuEditModal").modal("show");
            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            // 根据id属性查节点对象
            zTreeObj.getNodeByParam()
            // 用户来搜的属性名 属性值
            var key = "id";
            var value = window.id;
            // 利用zTreeAPI获取用户当前点击的节点值然后回显 [注意这里的 currentNode 值来自数据库]
            var currentNode = zTreeObj.getNodeByParam(key, value);
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            // 找到icon这个name 根据currentNode的值就能进行回显
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
            return false;
        });
        // 给“×”按钮绑定单击响应函数
        $("#treeDemo").on("click",".removeBtn",function () {
            // 将当前节点的id，作为新节点的pid保存到全局变量
            window.id = this.id;
            // 打开模态框
            $("#menuConfirmModal").modal("show");
            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            // 根据id属性查节点对象
            zTreeObj.getNodeByParam()
            // 用户来搜的属性名 属性值
            var key = "id";
            var value = window.id;
            // 利用zTreeAPI获取用户当前点击的节点值然后回显 [注意这里的 currentNode 值来自数据库]
            var currentNode = zTreeObj.getNodeByParam(key, value);

            $("#removeNodeSpan").html("【<i class='" + currentNode.icon + "'></i>" + currentNode.name + "】");
            return false;
        });

        // 给添加子节点的模态框中的保存按钮绑定单击响应函数
        $("#menuSaveBtn").click(function () {
            // 收集表单项中用户输入的数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            // 单选按钮要定位到“被选中”的那一个
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
            $.ajax({
                "url": "menu/save.json",
                "type": "post",
                "data": {
                    "pid": window.pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("success");
                        // 重新加载树形结构，注意:要在确认服务器端完成保存操作后再刷新
                        // 否则有可能刷新不到最新的数据，因为这里是异步的
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("failed" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuAddModal").modal("hide");
            // 清空表单
            // jQuery对象调用click()函数，里面不传任何参数，相当于用户点击了一下
            $("#menuResetBtn").click

        });

        $("#menuEditBtn").click(function () {
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            // 定位到被选中的那个
            var icon = $("#menuEditModal [name=icon]:checked").val();

            $.ajax({
                "url": "menu/update.json",
                "type": "post",
                "data": {
                    "id": window.id,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("success");
                        // 重新加载树形结构，注意:要在确认服务器端完成保存操作后再刷新
                        // 否则有可能刷新不到最新的数据，因为这里是异步的
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.msg("failed" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            $("#menuEditModal").modal("hide");
        })

        $("#confirmBtn").click(function () {
            $.ajax({
                "url": "menu/remove.json",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("SUCCESS");
                        generateTree();
                    }
                    if (result == "FAILED") {
                        layer.message("FAILED" + response.message + " " + response.statusText)
                    }
                }
            });
            $("#menuConfirmModal").modal("hide");
        })
    })
</script>
<body>
<%@include file="/WEB-INF/include_nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <!-- 这个url标签是zTree动态生成的节点所依附的静态节点 -->
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp"%>
<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
<%@include file="/WEB-INF/modal-menu-edit.jsp"%>
</body>
</html>

