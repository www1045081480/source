<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/estimationConfirm.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery.numeric.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>
<style type="text/css">
.colorInput{
	background-color:#CDFFFF;
}
#floatPanel .ctrolPanel {
	width: 100px;
	height: 206px;
	background: no-repeat left top;
	border: solid 0px #ddd;
	position: fixed;
	right: 500px;
	top: 100px;
}

#floatPanel .ctrolPanel a {
	width: 34px;
	font-size: 12px;
	color: #ff6600;
	letter-spacing: 1px;
	text-align: center;
	overflow: hidden;
	text-decoration: none;
}

#floatPanel .ctrolPanel .arrow {
	height: 75px;
	line-height: 28px;
	display: block;
	margin: 1px auto;
}

#floatPanel .ctrolPanel .contact {
	height: 95px;
	width: 100px;
	display: block;
}

.box {
	padding: 5px;
	boder: 10px;
	border-radius: 10px;
	background: white;
	font-size: 0.8em;
}

#qtitle {
	border: 0px solid green;
	width: 500px;
	font-size: 25px;
	font-weight: bold;
	margin-left: auto;
	margin-right: auto;
	margin-top: 5px;
}

#sendtime, #narutime, #yxtime {
	border: 0px solid;
	font-size: 1em;
	font-weight: bold;
	width: 100px
}

.binput {
	border: 0px solid;
	width: 220px;
}

.cinput {
	border: 0px solid;
	width: 140px;
}

.dinput {
	border: 0px solid;
	width: 400px;
}

.einput {
	border: 0px solid;
	font-weight: bold;
	font-size: 1.1em;
	width: 400px;
}

.finput {
	border: 0px solid;
	width: 500px;
}

.ginput {
	border: 0px solid;
	width: 280px;
	background-color:#CDFFFF;
}

.hinput {
	border: 0px solid;
	width: 50px;
	text-align: right;
	background-color:#CDFFFF;
}

.iinput {
	border: 0px solid;
	width: 30px;
	text-align: center;
	background-color:#CDFFFF;
}

.jinput {
	border: 0px solid;
	width: 80px;
	text-align: right;
	background-color:#CDFFFF;
}

.kinput {
	border: 0px solid;
	width: 120px;
}

#thead td {
	font-weight: bold;
	text-align: center;
}

.spname {
	border: 0px solid;
	overflow: hidden; 
	word-wrap: break-word; 
	resize: horizontal;
	height: 13px;
	background-color:#CDFFFF;
}

.area {
	border: 0px solid;
	height: 13px;
	overflow: hidden; 
	word-wrap: break-word; 
	resize: horizontal;
	background-color:#CDFFFF;
}

.je, #hjsum {
	text-align: right;
}
</style>
</head>
<body onload="load('${EstimationId}')">
	<input id="sumje" type="hidden" value="">
	<input id="langflg" type="hidden"   value="">
	<table class="stable" id="wrapper" border="0">
		<tbody>
			<tr>
			<%@ include file="header.jsp" %>
			</tr>
			<tr>
				<td id="container" style="background: #d2e9ff;">
					<form id="focus" class="sform"
						style="margin-left: auto; margin-right: auto;">
						<div class="box">
							<div id="print">
								<div
									style="border: 0px solid green; width: 700px; height: auto; margin-left: auto; margin-right: auto;">
									<div id="qtitle" align="center">受&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;&nbsp;书</div>
									<table border="0"
										style="width: 700px; height: 300px; font-size: 1em; margin-top: 10px"
										cellspacing="0px">
										<tr>
											<td style="width: 550px;"><a style="font-weight: bold">订单番号:</a><input
												id="orderCd" type="text" class="colorInput"></td>
											<td><a style="font-weight: bold;border:0px">报价单号:</a><input
												 class="kinput"
												id="estimationCd" readonly="readonly" type="text">
												<input id="estimationId" style="display: none" type="text">
												<input id="customerId" style="display: none" type="text">
												<input id="hjAmount" style="display: none" type="text">
												<input id="saveTag" style="display: none" type="text">
												</td>
										</tr>
										<tr>
											<td><a style="font-weight: bold">受注日:</a><input
												id="confirmdate" class="colorInput"
												onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);"
												type="text" readonly="readonly" style="border: 0px solid;width:80px" /></td>
											<td><a style="font-weight: bold">报价单作成日：</a><input
												id="sendtime" value="" type="text" readonly="readonly" /></td>
										</tr>
										<tr>
											<td><input id="poscode" class="cinput" type="text"
												value="" /></td>
											<td>〒<input class="cinput" type="text" value="170-0014"
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'" /></td>
										</tr>
										<tr>
											<td><input id="address1" class="dinput" type="text"
												value="" /></td>
											<td><input class="binput" type="text"
												value="東京都豊島区池袋2-61-4"
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'" /></td>
										</tr>
										<tr>
											<td><input id="address2" class="dinput" type="text"
												value="" /></td>
											<td><input class="binput" type="text" value="NIビル７F"
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'" /></td>
										</tr>
										<tr>
											<td><input type="text" value="" id="customerName"
												style="width:400px;font-weight:bold;font-size:20px;border: 0px solid;" /></td>
											<td><input class="binput" type="text" value="XAJ 新安商事株式会社"
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'" /></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td><input class="einput" type="text" value="敬启   承蒙贵公司的惠顾关照，非常感谢！"
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'" /></td>

											<td>TEL&nbsp;&nbsp; ：<input id="tel" class="cinput"
												type="text" value="" /></td>
										</tr>
										<tr>
											<td style="font-weight: bold; font-size: 1.1em;">製品名：<input
												id="firstName" class="einput" type="text" value=""
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'" /></td>
											<td>Email&nbsp;：<input id="fax" class="cinput"
												type="text" value="" /></td>
										</tr>
										<tr>
											<td>
												<table cellspacing="0px">
													<tr>
														<td
															style="border: 2px solid; border-right: 0px; height: 36px; width: 150px; font-weight: bold;">
															&nbsp;&nbsp;&nbsp;&nbsp;订单金額<br />
														</td>
														<td
															style="border: 2px solid; height: 36px; width: 220px; font-weight: bold; font-size: 20px;"
															align="right"><div id="money2"
																style="border: 0px solid; width: 10px; height: 10px; float: left">￥</div>
															<input value="" class="colorInput"
															style="width: 150px; border: 0px; text-align: right; font-weight: bold; font-size: 20px"
															type="text" id="order_monery" onblur="keyUp(this.value);">-</td>
													</tr>
													<tr height="10px"></tr>
													<tr>
														<td
															style="border: 2px solid; border-right: 0px; height: 36px; width: 150px; font-weight: bold;">
															&nbsp;&nbsp;&nbsp;&nbsp;受注书金额<br />
														</td>
														<td
															style="border: 2px solid; height: 36px; width: 220px; font-weight: bold; font-size: 20px;"
															align="right"><div id="monery1"
																style="border: 0px solid; width: 10px; height: 10px; float: left">￥</div>
															<a id="amount"></a>-</td>
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
					  										<td id="maxApprove" height="80px" style="border: 1px solid;text-align:center;">
					  										</td>
					  				 						<td id="minApprove" height="80px" style="border: 1px solid;text-align:center;" >
					  				 						</td>
					  										<td height="80px" style="border: 1px solid" align="center">
					  											<img width="60px" height="60px" id="user-seal" src="xinan/userseal.action?userId=${session.User.userId}"/>
					  										</td>
					  									</tr>
					  								</table>
											</td>
										</tr>
									</table>
									<table border="0"
										style="width: 700px; height: 50px; font-size: 1em; margin-top: 10px"
										cellspacing="0px">
										<tr>
											<td colspan="1"><a style="font-weight:bold;" width='50px'>交货方式：</a>
							  					<div style="position:relative;">
													<div style="width:100px;position:absolute;left:70px;top:-15px;">
													<span style="margin-left:100px;width:18px;overflow:hidden;">
													<select style="width:118px;margin-left:-100px" onchange="this.parentNode.nextSibling.value=this.value">
															<option value="FOB">FOB</option>
															<option value="CIF">CIF</option>
															<option value="EMS">EMS</option>
													</select></span><input id='delivery_method' name="box"  style="width:100px;position:absolute;left:0px;">
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
											<td width="380px"><hr style="border: 1px solid" /></td>
											<td><hr style="border: 1px solid" /></td>
										</tr>
										<tr>
											<td><a style="font-weight: bold">交货地点：</a><input
												id="deliveryAddress" class="ginput" type="text" value=""
												order='1px solid' /></td>
											<td><a style="font-weight: bold">报价有效期：</a><input
												id="yxtime" value="" class="colorInput" type="text"
												onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);"
												readonly="readonly" /></td>
										</tr>
										<tr>
											<td><hr style="border: 1px solid" /></td>
											<td><hr style="border: 1px solid" /></td>
										</tr>
										<tr>
											<td><a style="font-weight: bold;">交货期&nbsp;&nbsp;：</a><input class="binput"
												id="deliveryLimitDays" value="" type="text" style="background-color:#CDFFFF;" /></td>
											<td><a style="font-weight: bold;">支付条件&nbsp;&nbsp;：</a><textarea id="paymentMethord"
												class="binput" style="background-color:#CDFFFF;  min-width: 220px; max-width: 220px; overflow: hidden; word-wrap: break-word; resize: horizontal;"
												onmousedown="this.style.border='1px solid'"
												onblur="this.style.border='0px solid'">銀行振込</textarea></td>
										</tr>
										<tr>
											<td><hr style="border: 1px solid" /></td>
											<td><hr style="border: 1px solid" /></td>
										</tr>

									</table>
									<p style="height: 10px; font-weight: bold; font-size: 18px">报价清单</p>
									<table border="1" id="sptable"
										style="width: 700px;  font-size: 1em; border-bottom: 0px solid; border-left: 0px solid"
										cellspacing="0px">
										<tr id="thead">
											<td width="30px">番号</td>
											<td width="130px">品名</td>
											<td width="170px">仕様・寸法</td>
											<td class="sNoDisplay" width="80px">型番</td>
											<td width="50px">数量</td>
											<td width="30px">単位</td>
											<td width="80px">单价</td>
											<td width="100px">金額
												<select id="monery" onchange="gradeChange()" class="colorInput">
							 						<option value="JP">￥</option>
						 						<option value="US">$</option>
						 						<option value="CN">R</option>
						 						</select>

											</td>
											<td>备注</td>
											<td class="sNoDisplay"></td>
										</tr>
										<tbody id="addcol">
										</tbody>
							 			<tr >
							 				<td colspan="6" style="border-left:0px solid;border-bottom:0px solid;"></td>
							 				<td align="center"  style="font-weight:bold;border-bottom:1px solid;">合計</td>
							 				<td height="30px" colspan="2" style="border-bottom:1px solid;" id="hjsum"></td>
							 			</tr>
							 		</table>
									<p style="height: 0px;"></p>
									<p style="height: 10px; font-weight: bold; font-size: 18px">备注</p>
									<table border="0"
										style="width: 700px; font-size: 1em; border-bottom: 0px solid; border-left: 0px solid"
										cellspacing="0px">
										<tr>
											<td><textarea id="bknote" class="colorInput"
													style="border: 0px solid; min-width: 700px; max-width: 700px; overflow: hidden; word-wrap: break-word; resize: horizontal;height:50px;"></textarea></td>
										</tr>
										<tr>
											<td><hr size="2px" ALIGN=LEFT NOSHADE /></td>
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
	<div id="floatPanel">
		<div class="ctrolPanel" style="right:10px;">
				<a class="arrow" href="#"><span>顶部</span></a>
				<br/>
				<a class="contact" >
				<input type="button" id="saveBT" value="保存" style="height:50px;width:80px" onclick="save()"/>
				</a>
				<a class="contact" >
				<input type="button" id="zwBT" value="注文书做成" style="height:50px;width:80px" onclick="orderSheet()"/>
				</a>
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
	
	$('#bknote').autosize({
		append: "\n"
	});
	
	$('#paymentMethord').autosize({
		append: "\n"
	});
	
	$(".spname").parent("td").css("ver","");
	$(".spname").parent("td").css("ver","");
	
});
</script>

</html>