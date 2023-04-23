<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
		$(".TimeInput").datetimepicker({
			language:"zh-CN",
			format:"yyyy-mm-dd",
			autoclose:true,
			minView:"month",
			todayBtn:true,
			initialDate:new Date(),
			clearBtn:true
		});
		$("#ActivitySearch").keyup(function () {
			var clueId=$("#clueId").val();
			var activityName=$("#ActivitySearch").val();
			$.ajax({
				url:'workbench/convert/selectActivity',
				data:{
					name:activityName,
					id:clueId
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					var html="";
					$.each(data,function (index, obj) {
						html+="<tr>"+
							"<td><input type=\"radio\" name=\"activity\"value=\""+obj.id+","+obj.name+"\"/></td>"+
							"<td>"+obj.name+"</td>"+
							"<td>"+obj.startDate+"</td>"+
							"<td>"+obj.endDate+"</td>"+
							"<td>"+obj.owner+"</td>"+
						"</tr>"
					});
					$("#queryTby").html(html);
				}

			});
		});
		$("#queryTby").on("click","input[type='radio']",function () {
			var check=$(this).val();
			var inputValue=check.split(",");
			$("#activity").val(inputValue[1]);
			$("#activity").prop("activityId",inputValue[0]);
			$("#searchActivityModal").modal("hide");
		});
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
		$("#convertBtn").click(function () {
			var isCreate=$("#isCreateTransaction").prop("checked");
			var money=$("#amountOfMoney").val();
			var tradeName=$("#tradeName").val();
			var expectedTime=$("#expectedClosingDate").val();
			var stage=$("#stage").val();
			var activityId=$("#activity").prop("activityId");
			var clueId=$("#clueId").val();
			$.ajax({
				url:'workbench/convert/toConvert',
				data:{
					clueId:clueId,
					isCreate:isCreate,
					money:money,
					tradeName:tradeName,
					expectedTime:expectedTime,
					stage:stage,
					activityId:activityId
				},
				type:'post',
				dataType: 'json',
				success:function (data) {
					if (data.code=="200"){
						alert("转换成功！");
						window.location.href="workbench/clue/index.do";
					}else{
						alert("系统忙，请稍后重试！")
					}
				}
			})
		})
	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询" id="ActivitySearch">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="queryTby">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<input type="hidden" id="clueId" value="${requestScope.clue.id}">
		<h4>转换线索 <small>${requestScope.clue.fullname}${requestScope.clue.appellation}-${requestScope.clue.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${requestScope.clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${requestScope.clue.fullname}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="${requestScope.clue.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control TimeInput" id="expectedClosingDate" readonly>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
				<c:forEach items="${requestScope.stageList}" var="stage">
					<option value="${stage.id}">${stage.text}</option>
				</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a data-toggle="modal" data-target="#searchActivityModal" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${requestScope.clue.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" value="转换" id="convertBtn">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>