<%@ page contentType="text/html;charset=UTF-8"%>
<div class="page-content hide" id="yy-page-edit">
	<div class="row yy-toolbar">
		<button id="yy-btn-save" class="btn blue btn-sm">
			<i class="fa fa-save"></i> 保存
		</button>
		<button id="yy-btn-cancel" class="btn blue btn-sm">
			<i class="fa fa-rotate-left"></i> 取消
		</button>
	</div>

	<div class="row">
		<form id="yy-form-edit" class="form-horizontal yy-form-edit">
			<input name="uuid" type="hidden"> 
			<input name="processgroup.uuid" type="hidden">
			<div class="row">
				<div class="col-md-4" id="outletsCodeDiv">
					<div class="form-group">
						<label class="control-label col-md-4 required">流程编码</label>
						<div class="col-md-8">
							<input id="code" name="code" type="text" class="form-control" value="">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4 required" id="outletsNameLabel">流程名称</label>
						<div class="col-md-8">
							<input class="form-control " name="name" type="text">
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4" id="outletsNameLabel">下一流程编码</label>
						<div class="col-md-8">
							<input class="form-control " name="nextflowcode" type="text">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">流程类型</label>
						<div class="col-md-8"><select class="yy-input-enumdata form-control" id="flowtype"
								name="flowtype" data-enum-group="flow_type" ></select></div>
					</div>
				</div>
				
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">流程节点</label>
						<div class="col-md-8"><select class="yy-input-enumdata form-control" id="flownode"
								name="flownode" data-enum-group="flow_node" ></select></div>
					</div>
				</div>
				
				
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">流程动作</label>
						<div class="col-md-8"><select class="yy-input-enumdata form-control" id="flowaction" 
								name="flowaction" data-enum-group="flow_action" ></select></div>
					</div>
				</div>
				
			</div>
			
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">发送用户组</label>
						<div class="col-md-8">
							<div class="input-group">
								<input id="usergroupId" name="usergroup.uuid" type="hidden"> 
								<input id="usergroupName" name="usergroupName" type="text" 
								class="form-control" readonly="readonly" disabled="disabled">
								<span class="input-group-btn">
									<button id="yy-usergroup-select" class="btn btn-default btn-ref" type="button">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-md-4">发送类型</label>
						<div class="col-md-8"><select class="yy-input-enumdata form-control" id="flowsend" 
								name="flowsend" data-enum-group="flow_send" ></select></div>
					</div>
				</div>
				
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">链接URL</label>
						<div class="col-md-10"><input class="form-control " name="url"  type="text"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<div class="form-group">
						<label class="control-label col-md-2">流程项描述</label>
						<div class="col-md-10">
							<!-- <input class="form-control" name="description"  type="text"> -->
							<textarea id="roleDescId" rows="" cols="" class="form-control" name="description" style="width: 100%;"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>