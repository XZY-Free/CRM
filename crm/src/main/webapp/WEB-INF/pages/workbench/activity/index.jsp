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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.min.js"></script>

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
		$("#create_btn").click(function (){
			$("#createActivityForm").get(0).reset();
			$("#createActivityModal").modal("show");
		});
		$("#insert_button").click(function () {
			var owner=$("#create-marketActivityOwner option:selected").val();
			var name=$.trim($("#create-marketActivityName").val());
			var start_date=$("#create-startTime").val();
			var end_date=$("#create-endTime").val();
			var cost=$.trim($("#create-cost").val());
			var description=$.trim($("#create-describe").val());
			$.ajax({
				url:"workbench/activity/insert",
				type:"post",
				data:{
					owner:owner,
					name:name,
					startDate:start_date,
					endDate:end_date,
					cost:cost,
					description:description
				},
				dateType:"json",
				success:function (data){
					// *创建成功之后,关闭模态窗口,刷新市场活动列，显示第一页数据，保持每页显示条数不变
					// *创建失败,提示信息创建失败,模态窗口不关闭,市场活动列表也不刷新
					if (data.code=="200"){
						$("#createActivityModal").modal("hide");
						queryActivityByConditionForPage(1,$("#query_page").bs_pagination("getOption","rowsPerPage")
						);
						alert(data.message);
					}else{
						alert(data.message);
					}
				},
				beforeSend:function () {
					// 所有者和名称不能为空
					// *如果开始日期和结束日期都不为空,则结束日期不能比开始日期小
					// *成本只能为非负整数
					var reg=/^\d+$/;
					if (owner==""){
						alert("所有者不能为空！");
						return false;
					}else if (name==""){
						alert("名称不能为空！");
						return false;
					}else if (start_date==""){
						alert("开始日期不能为空!");
						return false;
					}else if (end_date==""){
						alert("结束日期不能为空！");
						return false;
					}else if (start_date>end_date){
						alert("填写日期不规范！");
						return false;
					}else if (!reg.test(cost)){
						alert("成本格式必须为非负整数");
						return false;
					}
					return  true;
				}
			})
		});
		//当市场活动主页面加载完成，查询所有数据的第一页集街所有数据的总条数
		queryActivityByConditionForPage(1, 5);
		//给查询按钮添加单击事件
		$("#query_btn").click(function () {
			//查询所有符合条件的数据
			queryActivityByConditionForPage(1,$("#query_page").bs_pagination("getOption","rowsPerPage"));
		});
		$("#selectAll_box").click(function () {
			$("#query_tbody input[type='checkbox']").prop("checked",this.checked)
		})

		$("#query_tbody").on("click","input[type='checkbox']",function () {
			if ($("#query_tbody input[type='checkbox']").size()==$("#query_tbody input[type='checkbox']:checked").size()){
				$("#selectAll_box").prop("checked",true);
			}else{
				$("#selectAll_box").prop("checked",false);
			}
		})
		$("#delete_btn").click(function () {
			if ($("#query_tbody input[type='checkbox']:checked").size()==0){
				alert("请选择要删除的记录！");
				return;
			}
			if (window.confirm("确认要删除？")){
				var ids='ids=';
				$.each($("#query_tbody input[type='checkbox']:checked"),function (index, obj) {
					ids+=obj.value+"&";
				})
				ids=ids.substring(0,ids.length-1);
				$.ajax({
					url:"workbench/activity/deleteByIds.do",
					data:ids,
					type:"post",
					dataType:'json',
					success:function (data) {
						if (data.code=="200"){
							alert("删除成功！");
							queryActivityByConditionForPage(1,$("#query_page").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert("删除失败！")
						}
					}
				})
			}
		});
		$("#update_btn").click(function () {
			var checkedObj=$("#query_tbody input[type='checkbox']:checked");
			if (checkedObj.size()==0){
				alert("请选择你要修改的记录!")
				return;
			}
			if (checkedObj.size()>1){
				alert("每次最多修改一条记录!")
				return;
			}
			var id=checkedObj[0].value;
			$.ajax({
				url:"workbench/activity/selectActivityById.do",
				data:{
					id:id
				},
				type:"post",
				dataType:'json',
				success:function (data) {
					$("#edit_id").val(data.id);
					$("#edit-marketActivityOwner").val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startTime").val(data.startDate);
					$("#edit-endTime").val(data.endDate);
					$("#edit-describe").val(data.description)
					$("#edit-cost").val(data.cost);
					$("#editActivityModal").modal("show");
				}
			})
		});
		$("#updateActivity_btn").click(function () {
			var id=$("#edit_id").val();
			var name=$("#edit-marketActivityName").val();
			var owner=$("#edit-marketActivityOwner").val();
			var startDate=$("#edit-startTime").val();
			var endDate=$("#edit-endTime").val();
			var description=$("#edit-describe").val();
			var cost=$("#edit-cost").val();
			$.ajax({
				url:"workbench/activity/updateActivityById.do",
				type:"post",
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				dateType:"json",
				success:function (data){
					if (data.code=="200"){
						$("#editActivityModal").modal("hide");
						queryActivityByConditionForPage($("#query_page").bs_pagination("getOption","currentPage"),$("#query_page").bs_pagination("getOption","rowsPerPage"));
						alert(data.message);
					}else{
						alert(data.message);
					}
				},
				beforeSend:function () {
					// 所有者和名称不能为空
					// *如果开始日期和结束日期都不为空,则结束日期不能比开始日期小
					// *成本只能为非负整数
					var reg=/^\d+$/;
					if (owner==""){
						alert("所有者不能为空！");
						return false;
					}else if (name==""){
						alert("名称不能为空！");
						return false;
					}else if (startDate==""){
						alert("开始日期不能为空!");
						return false;
					}else if (endDate==""){
						alert("结束日期不能为空！");
						return false;
					}else if (startDate>endDate){
						alert("填写日期不规范！");
						return false;
					}else if (!reg.test(cost)){
						alert("成本格式必须为非负整数");
						return false;
					}
					return  true;
				}
			})
		});
		$("#exportActivityAllBtn").click(function () {
			window.location.href="workbench/activity/exportActivities.do";
		})
		$("#exportActivityXzBtn").click(function () {
			var checkedObj=$("#query_tbody input[type='checkbox']:checked");
			if (checkedObj.size()==0){
				alert("请选择你要导出的记录!")
				return;
			}
			var id="id=";
			$.each(checkedObj,function (index, obj) {
				id+=obj.value+",";
			})
			id=id.substring(0,id.length-1);
			window.location.href="workbench/activity/exportActivitiesByIds.do?"+id;
		});
		$("#importActivityBtn").click(function () {
			var file=$("#activityFile").val();
			var suffix=file.substring(file.lastIndexOf(".")+1);
			if (suffix!="xls"){
				alert("目前仅支持xlsx后缀的文件！");
				var _file = document.getElementById("activityFile");
    			_file.outerHTML = _file.outerHTML;
				return;
			}
			var activityFile = $("#activityFile")[0].files[0];
			if (activityFile.size>1024*1024*5){
				alert("上传文件大小超过上限！");
				var _file = document.getElementById("activityFile");
    			_file.outerHTML = _file.outerHTML;
				return;
			}
			var formData = new FormData();
			formData.append("activityFile",activityFile);
			$.ajax({
				url:"workbench/activity/saveActivities.do",
				data:formData,
				contentType:false,
				processData:false,
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code=="200"){
						alert("导入成功！");
						$("#importActivityModal").modal("hide");
						var _file = document.getElementById("activityFile");
    					_file.outerHTML = _file.outerHTML;
						queryActivityByConditionForPage(1,$("#query_page").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("导入失败！");
						var _file = document.getElementById("activityFile");
    					_file.outerHTML = _file.outerHTML;
					}
				}
			})
		})

	});
	function queryActivityByConditionForPage(pageNo,pageSize) {
		var query_name=$("#query_name").val();
		var query_owner=$("#query_owner").val();
		var query_startDate=$("#query_startDate").val();
		var query_endDate=$("#query_endDate").val();
		$.ajax({
			url:"workbench/activity/selectByConditionForPage.do",
			data:{
				name:query_name,
				owner:query_owner,
				startDate:query_startDate,
				endDate:query_endDate,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				var html="";
				$.each(data.activityList,function (index,obj) {
					html+="<tr class=\"active\">"+
							"<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>"+
							"<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.html';\">"+obj.name+"</a></td>"+
							"<td>"+obj.owner+"</td>"+
							"<td>"+obj.startDate+"</td>"+
							"<td>"+obj.endDate+"</td>"+
							"</tr>;"
				})
				$("#query_tbody").html(html);

				if (data.totalCounts%pageSize==0){
					var totalPages=data.totalCounts/pageSize;
				}else{
					var totalPages=parseInt(data.totalCounts/pageSize)+1;
				}
				$("#query_page").bs_pagination({
					currentPage:pageNo,
					rowsPerPage: pageSize,
					maxRowsPerPage: 100,
					totalPages:totalPages,
					totalRows: data.totalCounts,

					visiblePageLinks: 5,

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: false,
					onChangePage:function(event,pageObj) {
						queryActivityByConditionForPage(pageObj.currentPage,
								pageObj.rowsPerPage)
					}
				})
			},
			beforeSend:function () {
				if (query_startDate!=null && query_endDate!=null){
					if (query_startDate>query_endDate){
						alert("日期填写不规范！");
						return false;
					}
				}
				return true;
			}
		})
	}
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal"id="createActivityForm" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
									<c:forEach items="${requestScope.userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label  for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label  for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control TimeInput" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control TimeInput" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="insert_button">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="edit_id">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">

							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${requestScope.userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control TimeInput" id="edit-startTime" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control TimeInput" id="edit-endTime" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateActivity_btn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query_name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query_owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control TimeInput" type="text" id="query_startDate" readonly/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control TimeInput" type="text" id="query_endDate" readonly/>
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="query_btn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="create_btn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="update_btn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delete_btn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="selectAll_box"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="query_tbody">
<%--						<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--							<td>2020-10-10</td>--%>
<%--							<td>2020-10-20</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>2020-10-10</td>--%>
<%--                            <td>2020-10-20</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
				<div id="query_page"></div>
			</div>
			
<%--			<div style="height: 50px; position: relative;top: 30px;">--%>
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>--%>
<%--				</div>--%>
<%--				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--					<div class="btn-group">--%>
<%--						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--							10--%>
<%--							<span class="caret"></span>--%>
<%--						</button>--%>
<%--						<ul class="dropdown-menu" role="menu">--%>
<%--							<li><a href="#">20</a></li>--%>
<%--							<li><a href="#">30</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--				</div>--%>
<%--				<div style="position: relative;top: -88px; left: 285px;">--%>
<%--					<nav>--%>
<%--						<ul class="pagination">--%>
<%--							<li class="disabled"><a href="#">首页</a></li>--%>
<%--							<li class="disabled"><a href="#">上一页</a></li>--%>
<%--							<li class="active"><a href="#">1</a></li>--%>
<%--							<li><a href="#">2</a></li>--%>
<%--							<li><a href="#">3</a></li>--%>
<%--							<li><a href="#">4</a></li>--%>
<%--							<li><a href="#">5</a></li>--%>
<%--							<li><a href="#">下一页</a></li>--%>
<%--							<li class="disabled"><a href="#">末页</a></li>--%>
<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
<%--			</div>--%>
			
		</div>
		
	</div>
</body>
</html>