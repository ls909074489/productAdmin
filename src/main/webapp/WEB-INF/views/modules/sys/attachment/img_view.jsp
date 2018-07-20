<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<%-- <script type="text/javascript" src="${ctx}/assets/iviewer/jquery.js" ></script> --%>

<script type="text/javascript">
jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
</script>
<script type="text/javascript" src="${ctx}/assets/iviewer/jqueryui.js" ></script>
<script type="text/javascript" src="${ctx}/assets/iviewer/jquery.mousewheel.min.js" ></script>
<script type="text/javascript" src="${ctx}/assets/iviewer/jquery.iviewer.js" ></script>

        
<link rel="stylesheet" href="${ctx}/assets/iviewer/jquery.iviewer.css" />

 <style>
      .viewer
      {
          width: 100%;
          height: 500px;
          border: 1px solid white;
          position: relative;
      }

      .wrapper
      {
          overflow: hidden;
      }
  </style>
</head>
  
<body>
	<!-- 公用脚本 -->
	<div class="container-fluid page-container page-content">
		<div class="row">
			<div class="col-md-12" style="padding-left: 0px;">
				<div class="row" style="width: 100%;height: 100%;">
					 <div id="viewer2" class="viewer" style="width: 100%;"></div>
				</div>
			</div>
		</div>
	</div>
	
	<c:choose>
		<c:when test="${not empty pk}">
			<script type="text/javascript">
			    var $ = jQuery;
			    $(document).ready(function(){
			          var iv1 = $("#viewer").iviewer({
			               src: "test_image.jpg",
			               update_on_resize: false,
			               zoom_animation: false,
			               mousewheel: false,
			               onMouseMove: function(ev, coords) { },
			               onStartDrag: function(ev, coords) { return false; }, //this image will not be dragged
			               onDrag: function(ev, coords) { }
			          });
			
			           $("#in").click(function(){ iv1.iviewer('zoom_by', 1); });
			           $("#out").click(function(){ iv1.iviewer('zoom_by', -1); });
			           $("#fit").click(function(){ iv1.iviewer('fit'); });
			           $("#orig").click(function(){ iv1.iviewer('set_zoom', 100); });
			           $("#update").click(function(){ iv1.iviewer('update_container_info'); });
			
			          var iv2 = $("#viewer2").iviewer(
			          {
			        	  src: "${ctx}/frame/attachment/downloadOssFile?pk=${pk}"
			        	  //src: "${ctx}/assets/iviewer/3.jpg"
			          });
			
			          $("#chimg").click(function()
			          {
			            iv2.iviewer('loadImage', "test_image.jpg");
			            return false;
			          });
			
			          var fill = false;
			          $("#fill").click(function()
			          {
			            fill = !fill;
			            iv2.iviewer('fill_container', fill);
			            return false;
			          });
			    });
			</script>
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
			    var $ = jQuery;
			    $(document).ready(function(){
			          var iv1 = $("#viewer").iviewer({
			               src: "test_image.jpg",
			               update_on_resize: false,
			               zoom_animation: false,
			               mousewheel: false,
			               onMouseMove: function(ev, coords) { },
			               onStartDrag: function(ev, coords) { return false; }, //this image will not be dragged
			               onDrag: function(ev, coords) { }
			          });
			
			           $("#in").click(function(){ iv1.iviewer('zoom_by', 1); });
			           $("#out").click(function(){ iv1.iviewer('zoom_by', -1); });
			           $("#fit").click(function(){ iv1.iviewer('fit'); });
			           $("#orig").click(function(){ iv1.iviewer('set_zoom', 100); });
			           $("#update").click(function(){ iv1.iviewer('update_container_info'); });
			
			          var iv2 = $("#viewer2").iviewer(
			          {
			        	  src: "${imgurl}"
			        	  //src: "${ctx}/assets/iviewer/3.jpg"
			          });
			
			          $("#chimg").click(function()
			          {
			            iv2.iviewer('loadImage', "test_image.jpg");
			            return false;
			          });
			
			          var fill = false;
			          $("#fill").click(function()
			          {
			            fill = !fill;
			            iv2.iviewer('fill_container', fill);
			            return false;
			          });
			    });
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>
