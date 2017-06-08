<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <title>INVOICE</title>
	
	<style type="text/css">
        #tabs-1,#tabs-2,#tabs-3
		{
			width:1008px;
            height:459px;
            margin-left:auto;
            margin-right:auto;
        }
        .tr1{
        	background-color:#c0f3b6;
        	border:1px solid #00bb5e;
        }
        
        .sub{
            background:url('images/must12.png');
            BORDER-TOP-WIDTH: 0px; 
            BORDER-LEFT-WIDTH: 0px; 
            BORDER-BOTTOM-WIDTH: 0px; 
            BORDER-RIGHT-WIDTH: 0px; 
            WIDTH: 180px; 
            HEIGHT: 60px;
            font-size:large;"
        }
        #userbody {
		PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BACKGROUND: ;PADDING-TOP: 0px;
		background:#e7f7fa;
		}
		#floatPanel .ctrolPanel{
			width:100px;
			height:206px;
			background:#e7f7fa no-repeat left top;
			border:solid 0px #ddd;
			position:fixed;
			right:500px;
			top:80px;
		}
		#floatPanel .ctrolPanel a{
			width:34px;
			font-size:12px;
			color:#ff6600;
			letter-spacing:1px;
			text-align:center;
			overflow:hidden;
			text-decoration:none;
			}
		#floatPanel .ctrolPanel .arrow{
			height:45px;
			line-height:28px;
			display:block;
			margin:1px auto;
			}
		
		#floatPanel .ctrolPanel .contact{
			height:60px;
			width:100px;
			display:block;
			}
		 #tabs .tabs-spacer { float: left; height: 200px; }
		  .tabs-bottom .ui-tabs-nav { clear: left; padding: 0 .2em .2em .2em; }
		  .tabs-bottom .ui-tabs-nav li { top: auto; bottom: 0; margin: 0 .2em 1px 0; border-bottom: auto; border-top: 0; }
		  .tabs-bottom .ui-tabs-nav li.ui-tabs-active { margin-top: -1px; padding-top: 1px; }
	</style>
	<script type="text/javascript" src="js/DatePicker.js?${initParam.version}"></script>
	<script type="text/javascript" src="js/jquery.min.js?${initParam.version}"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css?${initParam.version}" />
	<script src="http://code.jquery.com/jquery-1.8.3.js?${initParam.version}"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js?${initParam.version}"></script>
	<link rel="stylesheet" href="/resources/demos/style.css?${initParam.version}" />
	<script type="text/javascript">
		$(document).ready(function(){
 			 $("#b01").click(function(){
  				htmlobj=$.ajax({url:"xinan/test.txt",async:false});
  					$("#myDiv").html(htmlobj.responseText);
  				});
			});
			
		 $(function() {
			    var availableTags = [
			      "ActionScript",
			      "AppleScript",
			      "Asp",
			      "BASIC",
			      "C",
			      "C++",
			      "Clojure",
			      "COBOL",
			      "ColdFusion",
			      "Erlang",
			      "Fortran",
			      "Groovy",
			      "Haskell",
			      "Java",
			      "JavaScript",
			      "Lisp",
			      "Perl",
			      "PHP",
			      "Python",
			      "Ruby",
			      "Scala",
			      "Scheme"
			    ];
		    $( "#tags" ).autocomplete({
		      source: availableTags
  			});
  			});
  			
  			
  			function keydownMsg(evt) {
			      evt = (evt) ? evt : ((window.event) ? window.event : "");
			      keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
			      if (keyCode == 13) {
			      	//alert(123);
			        alert(document.getElementById("tags").value);//回车键弹出文本框信息
			      }
    		}
  			
  		/*document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if(e && e.keyCode==27){ // 按 Esc 
                //要做的事情
              }
            if(e && e.keyCode==113){ // 按 F2 
                 //要做的事情
               }            
             if(e && e.keyCode==13){ // enter 键
                 alert(123);
                 //要做的事情
            }
        }; */
	</script>
  </head>
  
  <body id="userbody" onload="star()">
 	<div id="myDiv"><h2>通过 AJAX 改变文本</h2></div>
	<button id="b01" type="button">改变内容</button>
 	<div class="ui-widget">
  <label for="tags">Tags: </label>
  <input id="tags"  onkeydown="keydownMsg(event)" />
</div> 
  </body>
</html>


