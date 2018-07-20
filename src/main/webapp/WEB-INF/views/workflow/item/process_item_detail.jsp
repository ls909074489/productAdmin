<%@ page contentType="text/html;charset=UTF-8"%>
<div class="page-content hide" id="yy-page-detail">
	<div class="row yy-toolbar">

		<button id="yy-btn-backtolist" class="btn blue btn-sm">
			<i class="fa fa-mail-reply"></i> 返回
		</button>
	</div>
	<div class="row">
		<form id="yy-form-detail"  class="form-horizontal yy-form-detail">
		<input name="uuid" type="hidden" />
		<input name="uuid" type="hidden"> 
			<input name="processgroup.uuid" type="hidden">
			<div class="row">
					<div class="col-md-4" id="outletsCodeDiv">
						<div class="form-group">
							<label class="control-label col-md-4 required">流程项编码</label>
							<div class="col-md-8">
								<input id="org_code" name="code" type="text" class="form-control" value="">
							</div>
						</div>
					</div>
					<div class="col-md-8">
						<div class="form-group">
							<label class="control-label col-md-2 required" id="outletsNameLabel">流程项名称</label>
							<div class="col-md-10">
								<input class="form-control " name="name" type="text">
							</div>
						</div>
						
					</div>
			</div>
			<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label col-md-4">单据类型</label>
								<div class="col-md-8"><input class="form-control " name="billtype"  type="text"></div>
							</div>
						</div>	
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label col-md-4">操作</label>
								<div class="col-md-8"><input class="form-control " name="action"  type="text"></div>
							</div>
						</div>	
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label col-md-4">业务类型</label>
								<div class="col-md-8"><input class="form-control " name="businesstype"  type="text"></div>
							</div>
						</div>	
			</div>
			<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label col-md-4">流程类型</label>
								<div class="col-md-8"><input class="form-control " name="flowtype"  type="text"></div>
							</div>
						</div>	
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label col-md-4">流程组</label>
								<div class="col-md-8"><input class="form-control " name="flowgroup"  type="text"></div>
							</div>
						</div>	
						<div class="col-md-4">
							<div class="form-group">
								<label class="control-label col-md-4">流程编号</label>
								<div class="col-md-8"><input class="form-control " name="flowcode"  type="text"></div>
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