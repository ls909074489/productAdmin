<%@ page contentType="text/html;charset=UTF-8"%>


<div class="page-content hide" id="yy-page-edit">
		<div class="row yy-toolbar">
			<button id="yy-btn-save">
				<i class="fa fa-save"></i> 保存
			</button>
			<button id="yy-btn-cancel" class="btn blue btn-sm">
				<i class="fa fa-rotate-left"></i> 取消
			</button>
		</div>

		<div class="row">
			<form id="yy-form-edit"  class="form-horizontal yy-form-edit">			
				<input name="uuid" type="hidden"/>
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
			</form>
		</div>
</div>
