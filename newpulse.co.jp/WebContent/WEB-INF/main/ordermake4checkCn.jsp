<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/ordermake4checkCn.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery.numeric.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>
<style type="text/css">
#floatPanel .ctrolPanel{
			width:100px;
			height:206px;
			background:no-repeat left top;
			border:solid 0px #ddd;
			position:fixed;
			right:500px;
			top:100px;
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
			height:75px;
			line-height:28px;
			display:block;
			margin:1px auto;
			}

		#floatPanel .ctrolPanel .contact{
			height:95px;
			width:100px;
			display:block;
			}
.box{
	padding: 5px;
	boder:10px;
	border-radius: 10px;
	background: white;
	font-size: 0.8em;
}
#qtitle{
	border: 0px solid green;
	width:500px;
	font-size:25px;
	font-weight:bold;
	margin-left:auto;
	margin-right:auto;
	margin-top: 5px;
}
#sendtime,#narutime,#yxtime{
	border:0px solid;
	font-size:1em;
	font-weight:bold;
	width:100px
}
.binput{
	border:0px solid;
	width:220px;
}
.cinput{
	border:0px solid;
	width:140px;
}
.dinput{
	border:0px solid;
	width:400px;
}
.einput{
	border:0px solid;
	font-weight:bold;
	font-size:1.1em;
	width:400px;
}
.finput{
	border:0px solid;
	width:500px;
}
.ginput{
	border:0px solid;
	width:280px;
}
.hinput{
	border:0px solid;
	width:50px;
	text-align:right;
}
.iinput{
	border:0px solid;
	width:30px;
	text-align:center;
}
.jinput{
	border:0px solid;
	width:80px;
	text-align:right;
}
.kinput{
	border:0px solid;
	width:120px;
}

.idinput{
	border:0px solid;
	width:140px;
}

.inputa{
	border:0px solid;
	width:120px;
}

#thead td{
	font-weight:bold;
	text-align:center;
}
.spname{
	border:0px solid;
	overflow-y:hidden;
	height:20px;
	background-color:#CDFFFF;
}
.area{
	border:0px solid;

	height:13px;
	overflow-y:visible;
}
.je,#hjsum{
	text-align:right;

}

.bg{
	background-color:#CDFFFF;
}
</style>
</head>
<body onload="OrderSheet('${OrderId}')">
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
				  			<div id="qtitle" align="center" ><font size="6"><b>注&nbsp;&nbsp;&nbsp;文&nbsp;&nbsp;&nbsp;書</b></font></div>
					  		<table >
					  			<tr>
					  				<td>
					  					<table width="350px" height="320px" border="0">
					  						<tr height="20px">
					  							<input  id="supplierId"  type="text" style="display:none"/>
							  					<input  id="orderid"  type="text" style="display:none"/>
							  					<input id="tdmail"  type="text" style="display:none"/>
							  					<input id="address1"  type="text" style="display:none"/>
							  					<input id="address2"  type="text" style="display:none"/>
							  					<input id="address3"  type="text" style="display:none"/>
							  					<input id="enShortName"  type="text" style="display:none"/>
							  					<input id="userId"  type="text" style="display:none"/>
							  					<input id="currencyType"  type="text" style="display:none"/>

					  						</tr>
					  						<tr height="60px" style="vertical-align:top">
						  						<td>
								  					<div style="float:left;width:300px;height:23px;font-weight:bold;font-size:20px;border:1px solid;">
														<input class="bg" id="_select" value="" onBlur="keydownMsg(event)" onkeydown="changeName(event)" style="height:23px;width:300px;font-weight:bold;font-size:20px;border:0px;"/>
													</div>
						  						</td>
					  						</tr>
					  						<tr  height="100px">
					  							<td>
					  								<textarea  class="bg" id="word" style="font-weight:bold;width:300px;height:100%;resize:none;BORDER-BOTTOM-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-TOP-STYLE: none">拝啓　毎々お引き立てに預り有難くお礼申し上げます。さて、平成  年  月  日の見積もり知らせに基づき、下記の通り注文いたします。</textarea>
					  							</td>
					  						</tr>
					  						<tr  height="100px">
					  							<td>
						  							<textarea  id="title" style="min-width:300px;max-width:300px;font-weight:bold;font-size:15;BORDER-BOTTOM-STYLE: none; BORDER-LEFT-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-TOP-STYLE: none;background-color:#CDFFFF;" value="">
					  								</textarea>
					  							</td>
					  						</tr>
					  					</table>
					  				</td>
					  				<td>
					  					<table width="350px" height="320px" border="0px">
					  						<tr height="30px">
					  						</tr>
					  						<tr height="25px">
					  							<td width="80px"></td>
					  							<td colspan="2" align="left">
					  								<font size="2">注文書第：
					  								<input width="100%" id="ordercd" class="idinput" type="text" readonly/>
					  								号</font>
					  							</td>
					  						</tr>
					  						<tr height="25px">
					  							<td width="80px" ></td>
					  							<td align="left">
					  								<font size="2">発行日：<input  class="bg" id="sendtime"  type="text" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);"  >
					  								</font>
					  							</td>
					  						</tr>
					  						
					  						<tr height="25px">
					  							<td width="80px"></td>
					  							<td align="left">
					  								<font size="2">〒 170-0014</font>
					  							</td>
					  						</tr>
					  						
					  						<tr height="25px">
					  							<td width="80px"></td>
					  							<td align="left">
					  								<font size="2">東京都豊島区池袋2-61-4 NIビル７F</font>
					  							</td>
					  						</tr>
					  					
					  						
					  						<tr height="25px">
					  							<td width="80px"></td>
					  							<td align="left">
					  								<font size="2" face="verdana"><B>XAJ</b></font>
					  								<font size="2"><b>新安商事株式会社</B></font>
					  							</td>
					  						</tr>
					  						
					  						<tr height="25px">
					  							<td width="80px"></td>
					  							<td align="left">
					  								<font size="2">TEL ： 03－6912－7995</font>
					  							</td>
					  						</tr>
					  						
					  						<tr height="25px">
					  							<td width="80px"></td>
					  							<td align="left">
					  								<font size="2">FAX ： 03－6912－7996</font>
					  							</td>
					  						</tr>
					  						
					  						<tr>
					  							<td width="80px"></td>
					  							<td align="left">
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
					  										<td id="makerSeal" height="80px" style="border: 1px solid" align="center">
					  											<img width="60px" height="60px" id="user-seal" src="xinan/userseal.action?userId=${session.User.userId}"/>
					  										</td>
					  									</tr>
					  								</table>
					  							</td>
					  						</tr>
					  					</table>
					  				</td>
					  			</tr>
					  		</table>
					  		<table border="0"  style="width:700px;height:50px;font-size: 1em;margin-top:10px" cellspacing="0px" >

					  			<tr>
					  				<td><a style="font-weight:bold">納入場所：</a><input  class="bg" id="delivery_address" value="" type="text" style="height:23px;width:200px;font-weight:bold;border:0px;" /></td>
					  				<td><a style="font-weight:bold">支払条件：</a>
					  				<textarea id="payment_condition"  class="bg"    style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal; font-weight:bold;border:0px;"></textarea>
					  			</tr>
					  			<tr>
					  				<td><hr style="border:1px solid "/></td>
					  				<td><hr style="border:1px solid "/></td>
					  			</tr>
					  			<tr>
					  				<td><a style="font-weight:bold">納入日付 ：</a><input  class="bg" id="delivery_date" class="ginput" type="text" onfocus="setday('2',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);"  readonly="readonly" style="height:23px;width:200px;font-weight:bold;border:0px;"/></td>
					  				<td><a style="font-weight:bold">支払方法：</a>
					  				<input id="payment_method"  class="bg"    style="height:23px;width:200px;font-weight:bold;border:0px;"/>
					  			</tr>
					  			<tr>
					  				<td><hr style="border:1px solid "/></td>
					  				<td><hr style="border:1px solid "/></td>
					  			</tr>
					  			<tr height="20px"></tr>
					  		</table>
					 		<table border="1" id="sptable" style="width:700px;height:auto;font-size: 1em;border-bottom:1px solid;border-left:1px solid" cellspacing="0px" >

					 		</table>
					 		<p style="height: 0px;"></p>
					  		<table border="0px" style="width:700px;" cellspacing="0px">
					  			<tr style="height: 20px ;border:1px solid" >
					  				<td style="width: 230px;border:0px solid;border-bottom:0px;font-weight:bold;" align="center"></td>
					  				<td style="width: 15px;border-bottom:0px;border-top:0px"></td>
					  				<td style="width: 230px;border:1px solid;border-bottom:0px;font-weight:bold;" align="center">値引き</td>
					  				<td style="width: 15px;border-bottom:0px;border-top:0px"></td>
					  				<td style="width: 230px;border:1px solid;border-bottom:0px;font-weight:bold;" align="center">税 抜 合 計</td>
					  			</tr>
					  			<tr style="height: 45px;border:1px">
					  				<td style="width: 230px;border:0px solid" align="right" >
					  				</td>
					  				<td style="width: 15px;border-top:0px"></td>
					  				<td style="width: 230px;border:1px solid" align="right">
					  					<div id="disCurrency" style="border:0px solid;width:10px;height:10px;float:left">￥</div>
					  					<input value="0" class="colorInput"
															style="width: 150px; border: 0px; text-align: right; font-size: 16px"
															type="text" id="discount" onblur="discountOnBlur(this)">
					  				</td>
					  				<td style="width: 15px;border-top:0px"></td>
					  				<td style="width: 230px;border:1px solid" align="right">
					  					<div style="border:0px solid;width:10px;height:10px;float:left" id="monery1">￥</div><a id="tsum"></a>
					  				</td>
					  			</tr>
					  		</table>
					  		<p style=" height:10px; font-weight:bold;font-size:18px">備考</p>
					  		<table border="0"  style="width:700px;font-size: 1em;border-bottom:0px solid;border-left:0px solid;" cellspacing="0px">
					  			<tr>
					  				<td><textarea id="bknote" style="border:0px solid;overflow-y:hidden; min-width:700px;max-width:700px;height:50px;background-color:#CDFFFF;"></textarea></td>
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
				<input type="button" id="saveBT" value="保存" style="height:50px;width:80px" onclick="checkApproveStatus()"/>
				</a>
				<br/>
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
	$('#bknote').autosize({
		append: "\n"
	});
	$('#title').autosize({
		append: "\n"
	});
	$('#payment_condition').autosize({
		append: "\n"
	});
});
</script>
</html>