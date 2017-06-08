<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/quoteCn.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>
<style type="text/css">
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
#narutime{
	border:0px solid;
	font-size:1em;
	font-weight:bold;
	width:280px;
}
</style>
<body onload="EstmationSheet()">
	<input id="sumje" type="hidden" value="">
	<input id="customerId" type="hidden" value="">
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
					  			<div id="qtitle" align="center" ><font size="6">报&nbsp;&nbsp;&nbsp;价&nbsp;&nbsp;&nbsp;单</font></div>
						  		<table  border="0"  style="width:700px;height:300px;font-size: 1em;margin-top:10px" cellspacing="0px" >
						  			<input id="userId" type="hidden">
						  			<input id="lang_flg" value="CN" type="hidden">
						  			<tr>
						  				<td style="width:550px;"><input id="tdmail" class="cinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td ><a style="font-weight:bold">报价单号：</a><input  id="EstimationCd" class="kinput" type="text" value=""  onblur="this.style.border='0px solid'" readonly="readonly"/></td>
						  			</tr>
						  			<tr>
						  				<td><input id="address" class="dinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td><a style="font-weight:bold">作成日：</a><input id="sendtime" value="" type="text" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" /></td>
						  			</tr>
						  			<tr>
						  				<td><input id="bilu" class="dinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td>〒<input class="cinput" type="text" value="170-0014" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  			</tr>
						  			<tr>
						  				<td>
						  					<div style="float:left;width:340px;height:23px;font-weight:bold;font-size:20px;border:1px solid;">
												<input id="_select"  class="colorInput" onBlur="keydownMsg(event)" onkeydown="changeName(event)" style="height:23px;width:340px;font-weight:bold;font-size:20px;border:0px;"/>
											
											</div> 
											<div class="noprint">
											<div id="btx" style="float:left;width:30px;height:23px;font-weight:bold;font-size:18px;border:1px solid;">
											<input type="button" value="x" style="height:23px;width:30px;" onclick="document.getElementById('_select').value='';"/>
											</div>
											</div>
										</td>
						  				<td><input  class="binput" type="text" value="東京都豊島区池袋2-61-4 NIビル７F" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  			</tr>
						  			<tr>
						  				<td></td>
						  				<td><input  class="binput" type="text" value="XAJ 新安商事株式会社" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  			</tr>
						  			
						  			<tr>
						  				<td><input class="einput" type="text" value="敬启   承蒙贵公司的惠顾关照，非常感谢！" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td>TEL&nbsp;&nbsp; ：<input id="Tel" class="cinput" type="text" value="03－6912－7995" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  			</tr>
						  			<tr>
						  				<td style="font-weight:bold;font-size:1.1em;">製品名：<input id="firstName" class="einput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td>FAX&nbsp;&nbsp; ：<input id="Fax" class="cinput" type="text" value="03－6912－7996" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  			</tr>
						  			<tr>
						  				<td>
						  					<table cellspacing="0px">
												<tr>
													<td style="border:2px solid;border-right:0px; height:36px; width:150px;font-weight:bold;">
														&nbsp;&nbsp;&nbsp;&nbsp;报价总金额<br/>
													</td >
													<td style="border:2px solid; height:36px; width:220px;font-weight:bold;font-size:20px;" align="right"><div id="monery1" style="border:0px solid;width:10px;height:10px;float:left">￥</div><a id="zsum"></a>-</td>
												</tr>
											</table>
						  				</td>
						  				<td>
						  						<table cellspacing="0px">
					  									<tr width="300px">
						  									<td  width="80px" align="center" style="border: 1px solid">社&nbsp;&nbsp;&nbsp;長</td>
						  									<td  width="80px" align="center" style="border: 1px solid">副社長</td>
										  					<td  width="80px" align="center" style="border: 1px solid">
										  					担&nbsp;&nbsp;&nbsp;当
										  					</td>
					  									</tr>
					  									<tr style="border: 1px solid">
					  										<td id ="maxApprove" height="80px" style="border: 1px solid;text-align:center;">
					  											<input id="maxbt" type="button" value="承认"    onclick="maxApprove(${session.User.role},${session.User.userId})"/>
					  										</td>
					  				 						<td id ="minApprove"  height="80px" style="border: 1px solid;text-align:center;" >
					  				 							<input id="minbt" type="button" value="承认"   onclick="minApprove(${session.User.role},${session.User.userId})"/>
					  				 						</td>
					  										<td height="80px" style="border: 1px solid" align="center">
					  											<img width="60px" height="60px" id="user-seal" src="xinan/userseal.action?userId=${session.User.userId}"/>
					  										</td>
					  									</tr>
					  								</table>
						  				
						  				
							  					<!-- <div style="border:1px solid;width:60px;height:80px;float:left;margin-left:0px" >
										  			<table border="0" style="width:100%" cellspacing="0px">
										  				<tr style="height:30px" >
										  					<td style="border-bottom: 1px solid" align="center">
										  					社&nbsp;&nbsp;&nbsp;长
										  					</td>
										  				</tr>
										  				<tr style="height:60px" >
										  					<td>
										  					
										  					</td>
										  				</tr>
										  			</table>
										  		</div>
										  		<div style="border:1px solid;width:60px;height:80px;float:left;border-left:0px;" >
										  			<table border="0" style="width:100%" cellspacing="0px">
										  				<tr style="height:30px" >
										  					<td style="border-bottom: 1px solid" align="center">
										  					副社长
										  					</td>
										  				</tr>
										  				<tr style="height:60px" >
										  					<td>
										  					
										  					</td>
										  				</tr>
										  			</table>
										  		</div>
										  		<div style="border:1px solid;width:60px;height:80px;float:left;border-left:0px;" >
										  			<table border="0" style="width:100%" cellspacing="0px">
										  				<tr style="height:30px" >
										  					<td style="border-bottom: 1px solid" align="center">
										  					担&nbsp;&nbsp;&nbsp;当
										  					
										  					</td>
										  				</tr>
										  				<tr style="height:60px" >
										  					<td>
										  					
										  					</td>
										  				</tr>
										  			</table>
										  		</div> -->
						  				</td>
						  			</tr>
						  		</table >
						  		<table border="0"  style="width:700px;height:50px;font-size: 1em;margin-top:10px" cellspacing="0px" >
						  			<tr>
						  				<td colspan="1"><a style="font-weight:bold;" width='50px'>交货方式：</a>
						  					<div style="position:relative;">
												<div style="width:100px;position:absolute;left:70px;top:-15px;">
												<span style="margin-left:100px;width:18px;overflow:hidden;">
												<select style="width:118px;margin-left:-100px"  class="colorInput" onchange="this.parentNode.nextSibling.value=this.value">
														<option value="FOB">FOB</option>
														<option value="CIF">CIF</option>
														<option value="EMS">EMS</option>
												</select></span><input  class="colorInput" id='delivery_method' name="box" value="FOB"  style="width:100px;position:absolute;left:0px;">
												</div>
											</div>
						  				</td>
						  				<td>
						  				<select id="typekind" class="colorInput">
						  					<option value="1" selected>材料</option>
						  					<option value="2">生产设备</option>
						  					<option value="3">检查设备</option>
						  					<option value="4">其他</option>
						  				</select></td>
						  			</tr>
						  			<tr>
						  				<td width="380px" ><hr style="border:1px solid "/></td>
						  				<td><hr style="border:1px solid "/></td>
						  			</tr>
						  			<tr>
						  				<td><a style="font-weight:bold">交货地点：</a><input id="DeliveryAddress"  class="ginput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td><a style="font-weight:bold">报价有效期 ：</a><input id="yxtime" class="colorInput" value="" type="text" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" /></td>
						  			</tr>
						  			<tr>
						  				<td><hr style="border:1px solid "/></td>
						  				<td><hr style="border:1px solid "/></td>
						  			</tr>
						  			<tr>
						  				<td><a style="font-weight:bold">交&nbsp;货&nbsp;期&nbsp;：</a><input id="narutime"  class="ginput" value="" type="text" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
						  				<td><a style="font-weight:bold">支&nbsp;付&nbsp;条&nbsp;件&nbsp;：</a><textarea id="PaymentCondition"   class="binput"  style="background-color:#CDFFFF; min-width: 220px; max-width: 220px;" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'">到货后第二个月付款</textarea></td>
						  			</tr>
						  			<tr>
						  				<td><hr style="border:1px solid "/></td>
						  				<td><hr style="border:1px solid "/></td>
						  			</tr>
						  		
						  		</table>
								<p style=" height:10px; font-weight:bold;font-size:18px">报价清单</p>
						 		<table border="1" id="sptable" style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid;border-right:0px solid" cellspacing="0px" >
						 			<tr id="thead">
						 				<td width="30px">番号</td>
						 				<td width="130px">品名</td>
						 				<td width="170px">仕様・寸法</td>
						 				<td class="sNoDisplay" width="80px">型番</td>
						 				<td width="50px">数量</td>
						 				<td width="30px">单位</td>
						 				<td width="80px">单价</td>
						 				<td width="100px">金額
						 					<select id="monery" onchange="gradeChange()" class="colorInput">
						 						<option value="JP">￥</option>
						 						<option value="US">$</option>
						 						<option value="CN">R</option>
						 					</select>
						 					
						 				</td>
						 				<td  style="border-right:1px solid">备注</td>
										<td class="sNoDisplay"></td>
						 			</tr>
						 			<tbody id="addcol">
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'" /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td><textarea class="area" style="min-width:100px; max-width:100px; width:100px;border-right:1px solid"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
								 		<tr >
								 				<td><input class="no" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="deleNum(this)"/></td>
								 				<td style="vertical-align: top;"><textarea class="spname" style="min-width: 130px; max-width: 100px; width:100px;" onBlur="sponBlur(this)" ></textarea></td>
								 				<td style="vertical-align: top;"><textarea class="area" style="min-width:170px; max-width:120px; width:120px;"></textarea></td>
								 				<td class="sNoDisplay" ><textarea class="area" style="min-width:80px; max-width:80px; width:80px;"></textarea></td>
								 				<td style="vertical-align: top;"><input class="hinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;"><input class="iinput" type="text" value="" onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
								 				<td style="vertical-align: top;"><input class="jinput" type="text" value="" onmousedown="this.style.border='1px solid'"  /></td>
								 				<td style="vertical-align: top;" class="je"></td>
								 				<td style="vertical-align: top;border-right:1px solid"><textarea class="area" style="min-width:100px; max-width:100px; width:100px;"></textarea></td>
												<td class="sNoDisplay"><textarea></textarea></td>
								 		</tr>
							 		</tbody>
						 			
						 			<tr >
						 				<td colspan="5" style="border-left:0px solid;border-bottom:0px solid;"></td>
						 				<td align="center"  style="font-weight:bold;border-bottom:1px solid;">合計</td>
						 				<td height="30px" colspan="1" style="border-bottom:1px solid;" id="hjsum"></td>
						 				<td style="border-left:1px solid;border-bottom:0px solid;"></td>
						 			</tr>
						 		</table>
						  		<p style=" height:10px; font-weight:bold;font-size:18px">备注</p>
						  		<table border="0"  style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid" cellspacing="0px">
						  			<tr>
						  				<td><textarea id="bknote" class="colorInput" style="border:0px solid;min-width: 700px; max-width: 700px; overflow: hidden; word-wrap: break-word; resize: horizontal;height:50px;"></textarea></td>
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
				<br/>
				<a class="contact" >
				<input type="button" id="change" value="日文版" style="height:50px;width:80px" onclick="pageChange()"/>
				</a>
				<a class="contact" >
				<input type="button" id="save" value="保存" style="height:50px;width:80px" onclick="checkApproveStatus()"/>
				</a>
		<!--  	<a class="contact" >
				<input type="button" id="saveBT" value="受注确定" style="height:50px;width:80px" onclick="estimationConfirm()"/>
				</a>
		-->		
				<a class="contact"   >
					<input type="button" value="印刷" onclick="print()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
	</div>
	</div>
</body>
<script type="text/javascript" src="js/jquery.numeric.js?${initParam.version}"></script>
<!-- //整数のみ入力可能 -->
<script>
$(".hinput").numeric({ decimal:false });
$(".hinput").blur(function(){
	 $(this).parseNumber({format:"#,###", locale:""});
	   $(this).formatNumber({format:"#,###", locale:""});
	   sum(this);
});
$(".jinput").blur(function(){
	 var tag = document.getElementById("monery").value;
	 if(tag == "JP"){
		 $(this).parseNumber({format:"#,###", locale:""});
		   $(this).formatNumber({format:"#,###", locale:""});
	 }else{
		 $(this).parseNumber({format:"#,###.00", locale:""});
		   $(this).formatNumber({format:"#,###.00", locale:""});
	 }
	 sum(this);
	});
	
$('.spname').autosize({
	append: "\n"
});
$('.area').autosize({
	append: "\n"
});

$('#bknote').autosize({
	append: "\n"
});
$('#PaymentCondition').autosize({
	append: "\n"
});
</script>
</html>
</html>