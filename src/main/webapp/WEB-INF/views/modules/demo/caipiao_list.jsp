<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serviceurl" value="${ctx}/caipiao" />
<style type="text/css" id="yangshi"></style>


<div class="page-content" id="yy-page-list" style="" align="center">
		<div class="row yy-toolbar">
			<button id="yy-btn-add" class="btn blue btn-sm">
				<i class="fa fa-plus"></i> 新增
			</button>
			<button id="yy-btn-remove" class="btn blue btn-sm">
				<i class="fa fa-trash-o"></i> 删除
			</button>
			<button id="yy-btn-refresh" class="btn blue btn-sm">
				<i class="fa fa-refresh"></i> 刷新
			</button>
			<button id="yy-btn-export" class="btn btn-sm btn-info">
				<i class="fa fa-file-excel-o"></i> 导出
			</button>
			<button id="yy-btn-cancel" class="btn blue btn-sm btn-info">
				<i class="fa fa-rotate-left"></i> 取消
			</button>
			<button id="yy-btn-save" class="btn blue btn-sm btn-info">
				<i class="fa fa-save"></i> 保存
			</button>
		</div>
		<hr>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div class="col-md-12">
						日期插件：<input class="Wdate" type="text" onClick="WdatePicker()">   onClick="WdatePicker()"
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="col-md-12">
						带时间：<input type="text" id="d241" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
						class="Wdate" style="width:150px"/> 
						 WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="col-md-12">
						时间范围：<input class="Wdate" type="text" onClick="WdatePicker({minDate:'%y-%M-%d',maxDate:'%y-%M-{%d+7}'})">
					</div>
				</div>
			</div>
		</div>	
		<div class="row">
			<table id="yy-table-list" class="yy-table">
					<tbody>
						<c:forEach var="list" items="${resultList}" varStatus="varIndex">
							<tr>	
								<td>${varIndex.index+3}</td>
								<c:forEach var="list2" items="${list}" varStatus="rowIndex">
									<c:choose>
										<c:when test="${list2.money gt 0}">
											<td style="background-color:#ef616e; ">${list2.money}(${list2.sameCount}同-${list2.times}次)</td>
										</c:when>
										<c:otherwise>
											<td style="background-color: #9dc75e;">${list2.money}(${list2.sameCount}同-${list2.times}次)</td>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
							<c:if test="${(varIndex.index+1) eq fn:length(resultList) }">
								<tr>
									<td></td>
									<c:forEach var="list2" items="${list}" varStatus="trStatus">
										<td style="background-color:#9ea2c7; ">${trStatus.index+2}次</td>
									</c:forEach>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
		</div>	
		<hr>
		<div class="row">
			<table id="yy-table-countlist" class="yy-table">
			
			</table>
		</div>	
		
		<div class="row">
		   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		    <div id="main1" style="width: 100%;height:400px;"></div>
		    <script type="text/javascript">
		        // 基于准备好的dom，初始化echarts实例
		        var myChart1 = echarts.init(document.getElementById('main1'));
		
		        var xAxisData=new Array();
		        var seriesArr=new Array();
		    	var InfoLoading = layer.load(2);
				$.ajax({
					type : "POST",
					data :{"uuid": '${entity.uuid}'},
					url : "${serviceurl}/numStatis",
					async : true,
					dataType : "json",
					success : function(data) {
						layer.close(InfoLoading);
						console.info();
						var tDays=data.days;
						for(var i=0;i<tDays.length;i++){
							xAxisData.push(tDays[i]);
						}
						
						var tResult=data.result;
						
						var countListStr="<tr><td>日期</td>";
						for(var i=1;i<=16;i++){
							countListStr+="<td>"+i+"</td>"
						}
						countListStr+="</tr>";
						for(var i=0;i<tResult.length;i++){
							var tScoreArry=new Array();
							var tRow=tResult[i];
							countListStr+="<tr><td>"+tRow[0].day+"</td>";
							for(var j=0;j<tRow.length;j++){
								countListStr+="<td>"+tRow[j].sameCount+"</td>"
							}
							countListStr+="</tr>";
						}
						
						$("#yy-table-countlist").html(countListStr);
						
						console.info(tResult);
						for(var i=0;i<tResult.length;i++){
							var tScoreArry=new Array();
							var tRow=tResult[i];
							for(var j=0;j<tRow.length;j++){
								tScoreArry.push(tRow[j].sameCount);
							}
							console.info(tScoreArry);
							seriesArr.push({name:i,type:'bar',data:tScoreArry});
						}
						
						 // 指定图表的配置项和数据
				        var option1 = {
				        	    title : {
				        	        text: '统计',
				        	        subtext: '纯属虚构'
				        	    },
				        	    tooltip : {
				        	        trigger: 'axis'
				        	    },
				        	    legend: {
				        	        data:['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15']
				        	    },
				        	    toolbox: {
				        	        show : true,
				        	        feature : {
				        	            mark : {show: true},
				        	            dataView : {show: true, readOnly: false},
				        	            magicType : {show: true, type: ['line', 'bar']},
				        	            restore : {show: true},
				        	            saveAsImage : {show: true}
				        	        }
				        	    },
				        	    calculable : true,
				        	    xAxis : [
				        	        {
				        	            type : 'category',
				        	            data : xAxisData
				        	        }
				        	    ],
				        	    yAxis : [
				        	        {
				        	            type : 'value'
				        	        }
				        	    ],
				        	    series : seriesArr
				        	};
				        	                    
				        // 使用刚指定的配置项和数据显示图表。
				        myChart1.setOption(option1);
					},
					error : function(data) {
						layer.close(InfoLoading);
						YYUI.promAlert(YYMsg.alertMsg('sys-submit-http',null));
					}
				});
		       
		    </script>
		 </div>	
</div>
	
<script type="text/javascript">

	$(document).ready(function() {
		console.info(111);
	
	});
	
</script>