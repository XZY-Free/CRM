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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>
<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.min.js"></script>

<script type="text/javascript">

	$(function(){
		queryClueByConditionForPage(1,5);
		$("#queryBtn").click(function () {
			queryClueByConditionForPage(1,$("#clue_page").bs_pagination("getOption","rowsPerPage"))
		})
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
			var owner=$("#create-clueOwner").val();
			var company=$("#create-company").val();
			var appellation=$("#create-call").val();
			var fullname=$("#create-surname").val();
			var job=$("#create-job").val();
			var email=$("#create-email").val();
			var phone=$("#create-phone").val();
			var website=$("#create-website").val();
			var mphone=$("#create-mphone").val();
			var status=$("#create-status").val();
			var source=$("#create-source").val();
			var description=$("#create-describe").val();
			var contactSummary=$("#create-contactSummary").val();
			var nextContactTime=$("#create-nextContactTime").val();
			var address=$("#create-address").val();
			$.ajax({
				url:'workbench/clue/save.do',
				data:{
					owner:owner,
					company:company,
					appellation:appellation,
					fullname:fullname,
					job:job,
					email:email,
					phone:phone,
					website:website,
					mphone:mphone,
					state:status,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address
				},

				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code=="200"){
						alert("创建线索成功！");
						$("#createClueModal").modal("hide");
						$("#createClueForm").get(0).reset();
						queryClueByConditionForPage(1,$("#clue_page").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("创建线索失败！");
					}
				}
			})
		});
		$("#updateBtn").click(function () {
			var checkedObj=$("#clue_tbody input[type='checkbox']:checked");
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
				url:"workbench/clue/selectClueById.do",
				data:{
					id:id
				},
				type:"post",
				dataType:'json',
				success:function (data) {
					$("#edit_id").val(data.id);
					$("#edit-surname").val(data.fullname)
					$("#edit-clueOwner").val(data.owner);
					$("#edit-call").val(data.appellation);
					$("#edit-company").val(data.company);
					$("#edit-job").val(data.job);
					$("#edit-describe").val(data.description)
					$("#edit-phone").val(data.phone);
					$("#edit-mphone").val(data.mphone);
					$("#edit-website").val(data.website);
					$("#edit-contactSummary").val(data.contactSummary);
					$("#edit-status").val(data.state);
					$("#edit-source").val(data.source);
					$("#edit-nextContactTime").val(data.nextContactTime);
					$("#edit-address").val(data.address);
					$("#editClueModal").modal("show");

				}
			})
		});
		$("#updateClueBtn").click(function () {
			var  id=$("#edit_id").val();
			var  fullname=$("#edit-surname").val()
			var  owner=$("#edit-clueOwner").val();
			var  appellation=$("#edit-call").val();
			var  company=$("#edit-company").val();
			var  job=$("#edit-job").val();
			var  description=$("#edit-describe").val()
			var  phone=$("#edit-phone").val();
			var  mphone=$("#edit-mphone").val();
			var  website=$("#edit-website").val();
			var  contactSummary=$("#edit-contactSummary").val();
			var  state=$("#edit-status").val();
			var  source=$("#edit-source").val();
			var  nextContactTime=$("#edit-nextContactTime").val();
			var  address=$("#edit-address").val();
			$.ajax({
				url:"workbench/clue/updateClueById.do",
				type:"post",
				data:{
					id:id,
					fullname:fullname,
					owner:owner,
					appellation:appellation,
					company:company,
					job:job,
					phone:phone,
					mphone:mphone,
					website:website,
					contactSummary:contactSummary,
					state:state,
					source:source,
					nextContactTime:nextContactTime,
					address:address,
					description:description
				},
				dateType:"json",
				success:function (data){
					if (data.code=="200"){
						$("#editClueModal").modal("hide");
						queryClueByConditionForPage($("#clue_page").bs_pagination("getOption","currentPage"),$("#clue_page").bs_pagination("getOption","rowsPerPage"));
						alert("修改成功！");
					}else{
						alert("系统忙，请稍后重试。。。");
					}
				}
			})
		});
		$("#deleteBtn").click(function () {
			if ($("#clue_tbody input[type='checkbox']:checked").size()==0){
				alert("请选择要删除的记录！");
				return;
			}
			if (window.confirm("确认要删除？")){
				var ids='ids=';
				$.each($("#clue_tbody input[type='checkbox']:checked"),function (index, obj) {
					ids+=obj.value+"&";
				})
				ids=ids.substring(0,ids.length-1);
				$.ajax({
					url:"workbench/clue/deleteByIds.do",
					data:ids,
					type:"post",
					dataType:'json',
					success:function (data) {
						if (data.code=="200"){
							alert("删除成功！");
							queryClueByConditionForPage(1,$("#clue_page").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert("删除失败！")
						}
					}
				})
			}
		});
		$("#selectAll_box").click(function () {
			$("#clue_tbody input[type='checkbox']").prop("checked",this.checked)
		})

		$("#clue_tbody").on("click","input[type='checkbox']",function () {
			if ($("#clue_tbody input[type='checkbox']").size()==$("#clue_tbody input[type='checkbox']:checked").size()){
				$("#selectAll_box").prop("checked",true);
			}else{
				$("#selectAll_box").prop("checked",false);
			}
		})
		function queryClueByConditionForPage(pageNo,pageSize) {
			var clueName=$("#clue_name").val();
			var clueOwner=$("#clue_owner").val();
			var clueIphone=$("#clue_iphone").val();
			var cluePhone=$("#clue_phone").val();
			var clueCompany=$("#clue_company").val();
			var clueState=$("#clue_state").val();
			var clueSource=$("#clue_source").val();
			$.ajax({
				url:"workbench/clue/queryClueForPage.do",
				data:{
					fullname:clueName,
					owner:clueOwner,
					phone:cluePhone,
					mphone:clueIphone,
					company:clueCompany,
					state:clueState,
					source:clueSource,
					pageNo:pageNo,
					pageSize:pageSize
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					var html="";
					$.each(data.clueList,function (index,obj) {
						html+="<tr>"+
							"<td><input type=\"checkbox\" value='"+obj.id+"'/></td>"+
							"<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clueDetail/index.do?id="+obj.id+"';\">"+obj.fullname+"</a></td>"+
							"<td>"+obj.company+"</td>"+
							"<td>"+obj.mphone+"</td>"+
							"<td>"+obj.phone+"</td>"+
							"<td>"+obj.source+"</td>"+
							"<td>"+obj.owner+"</td>"+
							"<td>"+obj.state+"</td>"+
						"</tr>"
					})
					$("#clue_tbody").html(html);
					if (data.totalCounts % pageSize==0){
						var totalPages=data.totalCounts/pageSize;
					}else{
						var totalPages=parseInt(data.totalCounts / pageSize)+1;
					}
					$("#clue_page").bs_pagination({
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
				}
			})
		}


});
	
</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="createClueForm">
					
						<div class="form-group">
							<label for="create-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueOwner">
									<c:forEach items="${requestScope.userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
									<c:forEach items="${requestScope.appellationList}" var="appellation">
										<option value="${appellation.id}">${appellation.text}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-status">
									<c:forEach items="${requestScope.clueStateList}" var="clueState">
										<option value="${clueState.id}">${clueState.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
									<c:forEach items="${requestScope.sourceList}" var="source">
										<option value="${source.id}">${source.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control TimeInput" id="create-nextContactTime" readonly>
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit_id">
						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueOwner">
									<c:forEach items="${requestScope.userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call">
									<option></option>
									<c:forEach items="${requestScope.appellationList}" var="appellation">
										<option value="${appellation.id}">${appellation.text}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-surname" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" >
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" >
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" >
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status">
								<c:forEach items="${requestScope.clueStateList}" var="clueState">
									<option value="${clueState.id}">${clueState.text}</option>
								</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
									<c:forEach items="${requestScope.sourceList}" var="source">
										<option value="${source.id}">${source.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" >
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateClueBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
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
				      <input class="form-control" type="text" id="clue_name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="clue_company">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="clue_phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="clue_source">
						  <option></option>
						  <c:forEach items="${requestScope.sourceList}" var="source">
							  <option value="${source.id}">${source.text}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="clue_owner">
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="clue_iphone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="clue_state">
						  <option></option>
						  <c:forEach items="${requestScope.clueStateList}" var="clueState">
							  <option value="${clueState.id}">${clueState.text}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="queryBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createClueModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="updateBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox"  id="selectAll_box"/></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="clue_tbody">
					</tbody>
				</table>
				<div id="clue_page"></div>
			</div>

			
		</div>
		
	</div>
</body>
</html>