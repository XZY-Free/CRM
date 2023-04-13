
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<base href="<%=basePath%>">
<head>
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
	$(function () {
		$(window).keydown(function (event) {
			if (event.keyCode==13){
				$("#login_button").click();
			}
		})
		$("#login_button").click(function () {
			var loginAct=$.trim($("#loginAct").val());
			var loginPwd=$.trim($("#loginPwd").val());
			var isRem=$("#isRem").prop("checked");
			console.log(isRem);
			$.ajax({
				url:"settings/qx/user/login.do",
				type:'post',
				data:{
					loginAct:loginAct,
					loginPwd:loginPwd,
					isRem:isRem
				},
				dataType:'json',
				success:function(data){
					if (data.code=="200"){
						window.location.href = "workbench/index";
					}else{
						$("#msg").text(data.message);
					}
				},
				beforeSend:function () {
					if (loginAct==""){
						$("#msg").text("用户名不能为空！");
						return false;
					}
					if (loginPwd==""){
						$("#msg").text("密码不能为空！");
						return  false;
					}
					$("#msg").text("正在验证。。。")
					return true;
				}
			})
		});
	})
</script>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;</div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" value="${cookie.loginAct.value}" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" value="${cookie.loginPwd.value}" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
							<input type="checkbox" id="isRem" checked> 十天内免登录
						</c:if>
						<c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
							<input type="checkbox" id="isRem"> 十天内免登录
						</c:if>
<%--						<input type="checkbox" id="isRem" checked> 十天内免登录--%>
						<span id="msg"></span>
					</div>
					<button id="login_button" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>