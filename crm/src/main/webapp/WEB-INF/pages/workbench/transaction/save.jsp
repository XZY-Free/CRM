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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript">
	$(function () {
		$(".TimeInput").datetimepicker({
			language:"zh-CN",
			format:"yyyy-mm-dd",
			autoclose:true,
			minView:"month",
			todayBtn:true,
			initialDate:new Date(),
			clearBtn:true
		});
		$("#saveBtn").click(function () {
			var customerName=$("#create-accountName").val();
			var possibility=$("#create-possibility").val();
			var stage=$("#create-transactionStage").val();
			var nextContactTime=$("#create-nextContactTime").val();
			var contactSummary=$("#create-contactSummary").val();
			var description=$("#create-describe").val();
			var source=$("#create-clueSource").val();
			var owner=$("#create-transactionOwner").val();
			var money=$("#create-amountOfMoney").val();
			var name=$("#create-transactionName").val();
			var expectedDate=$("#create-expectedClosingDate").val();
			var tranType=$("#create-transactionType").val();
			var activityId=$("#create-activitySrc").prop("activityId");
			var contactsId=$("#create-contactsName").prop("contactsId");
			debugger
			$.ajax({
				url:'workbench/tran/createTran.do',
				data:{
					owner:owner,
					money:money,
					tradeName:name,
					expectedTime:expectedDate,
					customerName:customerName,
					stage:stage,
					tranType:tranType,
					possibility:possibility,
					source:source,
					activityId:activityId,
					contactsId:contactsId,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code=="200"){
						alert("创建成功！");
						window.location.href="workbench/tran/index.do";
					}else{
						alert("创建失败！");
					}
				}
			})
		})
		$("#create-accountName").typeahead({
			source: function(query, process) {
				var name=$("#create-accountName").val();
				$.ajax({
					url:'workbench/tran/searchCustomerByName',
					data:{
						name:name
					},
					type:'post',
					dataType:'json',
					success:function (data) {
						process(data);
					}
				})
			}

		})
		$("#cancelBtn").click(function () {
			window.location.href="workbench/tran/index.do";
		});
		$("#create-transactionStage").change(function () {
			if ($("#create-transactionStage option:selected").text()==""){
				$("#create-possibility").val("");
				return;
			}

			var stage=$("#create-transactionStage").val();
			console.log(stage)
			$.ajax({
				url:'workbench/tran/stagePossibility',
				data:{
					stage:stage
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					$("#create-possibility").val(data+"%")
				}
			})
		})
		$("#contactsSearch").keyup(function () {
			var contactsName=$("#contactsSearch").val();
			$.ajax({
				url:'workbench/tran/selectContactsByName',
				data:{
					name:contactsName,
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					var html="";
					$.each(data,function (index, obj) {
						html+="<tr>"+
								"<td><input type=\"radio\" name=\"activity\" value=\""+obj.id+","+obj.fullname+"\"/></td>"+
								"<td>"+obj.fullname+"</td>"+
								"<td>"+obj.email+"</td>"+
								"<td>"+obj.mphone+"</td>"+
								"</tr>"
					});
					$("#contactsTby").html(html);
				}


			});
		});
		$("#contactsTby").on("click","input[type='radio']",function () {
			var obj=$(this).val().split(",");
			$("#create-contactsName").prop("contactsId",obj[0]);
			$("#create-contactsName").prop("value",obj[1]);
			$("#findContacts").modal("hide");
		})
		$("#ActivitySearch").keyup(function () {
			var activityName=$("#ActivitySearch").val();
			$.ajax({
				url:'workbench/convert/selectActivityByName',
				data:{
					name:activityName
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
			var obj=$(this).val().split(",");
			$("#create-activitySrc").prop("activityId",obj[0]);
			$("#create-activitySrc").prop("value",obj[1]);
			$("#findMarketActivity").modal("hide");
		})

	});
</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input id="ActivitySearch" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="queryTby">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input id="contactsSearch" type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contactsTby">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			<button type="button" class="btn btn-default" id="cancelBtn">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner">
					<c:forEach items="${requestScope.userList}" var="user" >
						<option value="${user.id}">${user.name}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control TimeInput" id="create-expectedClosingDate" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-accountName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage">
				  <option></option>
				  <c:forEach items="${requestScope.stageList}" var="stage">
					  <option value="${stage.id}">${stage.text}</option>
				  </c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType">
					<c:forEach items="${requestScope.transactionTypeList}" var="transactionType">
						<option value="${transactionType.id}">${transactionType.text}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource">
					<c:forEach items="${requestScope.sourceList}" var="source">
						<option value="${source.id}">${source.text}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activitySrc">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a  data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-contactsName">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control TimeInput" id="create-nextContactTime" readonly>
			</div>
		</div>
		
	</form>
</body>
</html>