<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/quotemake.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>
<style type="text/css">
.einput{
	border:0px solid;
	font-weight:bold;
	font-size:1.1em;
	width:600px;
}

#thead td{
	font-weight:bold;
	text-align:center;
	height:24px;
}
.spname{
	border:0px solid;
	overflow: hidden; 
	word-wrap: break-word; 
	resize: horizontal;
	height:20px;
	background-color:#CDFFFF;
}
.area{
	border:0px solid;
	background-color:#CDFFFF;
	height:20px;
	overflow: hidden; 
	word-wrap: break-word; 
	resize: horizontal;
}

body
{
	margin:0px;
	padding:0px;
	background-color:#E7EAEB;
	font-family:"微软雅黑","黑体","宋体";
	font-size:12px;height:36px;
}

</style>
<body onload="EstmationSheet()">
	<input id="sumje" type="hidden" value="">
	<input id="supplierId" type="hidden" value="">
	<input id="EstimationId" type="hidden" value="">
	<table class="stable" id="wrapper" border="0">
		<tbody>
			<tr>
				<%@ include file="header.jsp" %>
			</tr>
			<tr>
				<td id="container" style="background: #d2e9ff;">
					<form id="focus" class="sform" style=" margin-left: auto; margin-right: auto;">
					<div class="box">
					<div id="print">
					  		<div style="border: 0px solid green; width:700px;height:auto;margin-left:auto;margin-right:auto;">
					  			<!-- <div align="right" >平成 　 27 年 　 6 月 　 19  日</div> -->
					  			<div id="qtitle" align="center" ><font size="6">見&nbsp;&nbsp積&nbsp;&nbsp;依&nbsp;&nbsp;頼&nbsp;&nbsp;書</font></div>
						  		<table  border="0"  style="width:700px;height:300px;font-size: 1em;margin-top:10px" cellspacing="0px" >
						  			<tr>
							  			<td>
								  			<table>
									  			<tr style="height:24px">
									  				<td style="width:550px;height:24px"><input id="tdmail" class="cinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  				<td>〒<input class="cinput" type="text" value="170-0014" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  			</tr>
									  			<tr style="height:24px">
									  				<td><input id="address" class="dinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  				<td><input  class="binput" type="text" value="東京都豊島区池袋2-61-4" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  			</tr>						  			
									  			<tr style="height:24px">
									  				<td><input id="bilu" class="dinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>						  				
									  				<td><input  class="binput" type="text" value="NIビル７F" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  			</tr>
									  			<tr style="height:24px">
									  				<td></td>
													<td><input  class="binput" type="text" value="XAJ 新安商事株式会社" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  			</tr>
									  			<tr style="height:24px">
									  				<td>
									  					<div style="float:left;width:300px;height:28px;font-weight:bold;font-size:20px;border:1px solid;">
															<input id="_select"  class="colorInput" onBlur="keydownMsg(event)" onkeydown="changeName(event)" style="height:28px;width:300px;font-weight:bold;font-size:20px;border:0px;"/>
														</div> 
														<div style="float:left;width:28px;height:28px;font-weight:bold;font-size:18px;border:1px solid;">
														<input type="button" style="height:28px;width:28px;vertical-align:center" value="×" onclick="document.getElementById('_select').value='';"/>
														</div>
													</td>
									  				<td>TEL&nbsp;&nbsp; ：<input id="Tel" class="cinput" type="text" value="03－6912－7995" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  			</tr>
									  			<tr style="height:24px">
									  				<td></td>
									  				<td>FAX&nbsp;&nbsp; ：<input id="Fax" class="cinput" type="text" value="03－6912－7996" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
									  			</tr>
									  			<tr style="height:24px">
									  				<td colspan=2><input class="einput" type="text" value="拝啓　貴社ますますご繁栄のこととお喜び申し上げます。" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
									  			</tr>
									  			<tr style="height:24px">
									  				<td colspan=2><input class="einput" type="text" value="下記のとおり、お見積もりのほどよろしくお願い申し上げます。" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
									  			</tr>
									  		</table>
								  		</td>
							  		</tr>
						  		</table >
								<p style=" height:10px; font-weight:bold;font-size:18px">１．見積依頼品明細</p>
						 		<table border="1" id="sptable" style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid" cellspacing="0px" >
						 			<tr id="thead">
						 				<td width="24px">NO</td>
						 				<td width="240px">品名·規格</td>
						 				<td width="48px">数量</td>
						 				<td width="48px">単価</td>
						 				<td width="180px">摘要</td>
						 			</tr>
						 			<tbody id="addcol">
								 		<tr>
								 				<td><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td><textarea class="spname" style="min-width:360px; max-width:360px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td><input class="hinput" style=" width:48px;" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><textarea class="area" style="min-width:200px; max-width:200px;"></textarea></td>
								 		</tr>
								 		<tr>
								 				<td><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td><textarea class="spname" style="min-width:360px; max-width:360px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td><input class="hinput" style=" width:48px;" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><textarea class="area" style="min-width:200px; max-width:200px;"></textarea></td>
								 		</tr>
								 		<tr>
								 				<td><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td><textarea class="spname" style="min-width:360px; max-width:360px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td><input class="hinput" style=" width:48px;" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><textarea class="area" style="min-width:200px; max-width:200px;"></textarea></td>
								 		</tr>
								 		<tr>
								 				<td><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td><textarea class="spname" style="min-width:360px; max-width:360px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td><input class="hinput" style=" width:48px;" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><textarea class="area" style="min-width:200px; max-width:200px;"></textarea></td>
								 		</tr>
								 		<tr>
								 				<td><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td><textarea class="spname" style="min-width:360px; max-width:360px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td><input class="hinput" style=" width:48px;" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td><textarea class="area" style="min-width:200px; max-width:200px;"></textarea></td>
								 		</tr>
							 		</tbody>
						 		</table>
						 		<p style=" height:10px; font-weight:bold;font-size:18px">２．履行条件</p>
						 		<table border="1" id="sptable" style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid" cellspacing="0px" >
						 			<tbody id="addcol">
								 		<tr id="thead">
								 				<td width="60px" style="height:24px">納品期日</td>
								 				<td><input style="width:620" class="imaxinput" id="sendtime" value="" type="text" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" /></td>
								 		</tr>
								 		<tr id="thead">
								 				<td width="60px" style="height:48px">納品場所</td>
								 				<td><textarea class="area" style="min-width:620px;max-width:620px;height:36px" onBlur="sponBlur(this)" ></textarea></td>
								 		</tr>
								 		<tr id="thead">
								 				<td width="60px" style="height:48px">支払条件</td>
								 				<td><textarea class="area" style="min-width:620px;max-width:620px;height:36px" onBlur="sponBlur(this)" ></textarea></td>
								 		</tr>
							 		</tbody>
						 		</table>
						 		<p style=" height:10px; font-weight:bold;font-size:18px">３．見積書提出期限等</p>
						 		<table border="1" id="sptable" style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid" cellspacing="0px" >
						 			<tbody id="addcol">
								 		<tr id="thead">
								 				<td width="60px" style="height:24px">提出期限</td>
								 				<td><input style="width:620" class="imaxinput" id="yxtime" value="" type="text" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" /></td>
								 		</tr>
								 		<tr id="thead">
								 				<td width="60px" style="height:24px">提出方法</td>
								 				<td><textarea class="area" style="min-width:620px;max-width:620px;height:18px" onBlur="sponBlur(this)" ></textarea></td>
								 		</tr>
							 		</tbody>
						 		</table>
						  		<p style=" height:10px; font-weight:bold;font-size:18px">备注</p>
						  		<table border="0"  style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid" cellspacing="0px">
						  			<tr>
						  				<td><textarea id="bknote1" class="colorInput" style="border:0px solid;overflow-y:hidden; max-width:700px;min-width:700px;height:50px;"></textarea></td>
						  			</tr>
						  			<tr>
						  				<td><hr size="2px" ALIGN=LEFT NOSHADE/></td>
						  			</tr>
						  		</table>
						  		<table border="0"  style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid" cellspacing="0px">
						  			<tr>
						  				<td><textarea id="bknote2" class="colorInput" style="border:0px solid;overflow-y:hidden; max-width:700px;min-width:700px;height:50px;"></textarea></td>
						  			</tr>
						  			<tr>
						  				<td><hr size="2px" ALIGN=LEFT NOSHADE/></td>
						  			</tr>
						  		</table>
					  		</div>
					  </div>
					</div>
					</form>
					
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="footer.jsp" %>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="noprint">
	<div id="floatPanel" >
	  		<div class="ctrolPanel" style="right:10px;">
				<a class="arrow" href="#"><span>顶部</span></a>
				<a class="contact"   >
					<input type="button" value="印刷" onclick="print()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
	</div>
	</div>
</body>

<script>
$(document).ready(function() {
	$('.spname').autosize({
		append: "\n"
	});
	$('.area').autosize({
		append: "\n"
	});
	
	$('#bknote1').autosize({
		append: "\n"
	});
	
	$('#bknote2').autosize({
		append: "\n"
	});
	
	$('#bknote3').autosize({
		append: "\n"
	});
});
</script>

</html>