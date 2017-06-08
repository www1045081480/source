<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="X-UA-Compatible">
<title>新安商事業務管理システム</title>
<link rel="stylesheet" type="text/css" href="../css/commonj.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="../css/print.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="../css/content.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="../css/default.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="../css/stylea.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="../css/jquery-ui.css?${initParam.version}" />
<script type="text/javascript" src="../js/jquery.min.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/DatePicker4ordermake.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/DatePicker.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/zDialog.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/zDrag.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/jquery-1.8.3.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/jquery-ui.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/jQuery.print.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/delivery.js?${initParam.version}"></script>
<script src="js/hashtable.js?${initParam.version}"></script>
<script src="js/jquery.datef.js?${initParam.version}"></script>
<script src="js/jquery.numeric.js?${initParam.version}"></script>
<script src="js/jquery.numfm1.2.4.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/deliveryall.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/NextDeliveryall.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/beforeAfter.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/invoice.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/invoiceall.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/paymoney.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/receivemoney.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/CheckRole.js?${initParam.version}"></script>

<style type="text/css">


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
.ainput{
	border:0px solid;
	width:180px;
	background-color:#C4EFF4;
}
.binput{
	border:0px solid;
	width:90px;
	background-color:#C4EFF4;
	text-align:right;
}
.binput2{
	border:0px solid;
	width:80px;
	background-color:#C4EFF4;
	text-align:center;
}
.binput3{
	border:0px solid;
	width:80px;
	background-color:#C4EFF4;
	text-align:right;
}
.binput1{
	border:0px solid;
	width:110px;
	background-color:#C4EFF4;
	text-align:center;
}
.cinput{
			border:0px solid;
			width:80px;
			background-color:#C4EFF4;
			text-align:center;
}
.cinput1{
			border:0px solid;
			width:50px;
			background-color:#C4EFF4;
			text-align:center;
}
.dinput{
			border:0px solid;
			width:100px;
			background-color:#C4EFF4;
}
.einput{
		border:0px solid;
		width:200px;
		background-color:#C4EFF4;
}
.aclass{
	background-color:#C4EFF4;
}
#thead td{
	font-weight:bold;
	text-align:center;
}
.spname{
	border:0px solid;
	overflow-y:hidden;
	height:13px;
}
.area{
	border:0px solid;

	height:13px;
	overflow-y:visible;
}
.atr{
	background-color:#FFFF9B;
}
.btr{
	background-color:#C3C7C4;
}
 #userbody {
			PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BACKGROUND: ;PADDING-TOP: 0px;
			background:#e7f7fa;
		}
#floatPanel .ctrolPanel{
			width:100px;
			height:256px;
			background: no-repeat left top;
			border:solid 0px;
			position:fixed;
			right:500px;
			top:240px;
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
			height:55px;
			line-height:28px;
			display:block;
			margin:1px auto;
			}

#floatPanel .ctrolPanel .contact{
			height:60px;
			width:100px;
			display:block;
			}
#flip{
			padding:20px;
			text-align:center;
			background-color:#e5eecc;
			font-weight:bold;
			font-size:18px;
	}
#flip1{
			text-align:left;
			font-weight:bold;
			font-size:12px;
		}
#tabs1{
			width:800px;
			margin-left:auto;
			margin-right:auto;
		}
#tabs-1,#tabs-2,#tabs-3{
			border: 0px solid ;
			background-color:white;
			margin-left:auto;
			margin-right:auto;
}
</style>
</head>
<body onload="paymoneyLoad()">
	<table width="970" id="wrapper" border="0">
		<tbody>
			<tr>
				<td rowspan="3" id="wrapper-left">	</td>
		<td width="970">
			<!-- headerここから -->
			<div id="header">
				<input id="checkRole" type="hidden" value="${session.User.role}">
				<table>
					<tbody>
						<tr>
							<td><a href="xinan/xinan/menu.action"><img src="../images/xlog.png" alt="XINAN SYSTEM" width="285" height="49"></a>
							</td>
						</tr>

					</tbody>
				</table>
			</div> <!-- headerここまで -->


			<div style="position: absolute; line-height: 0.5em; font-size: 0.8em; margin-top: -80px; vertical-align: top; text-align: left; margin-left: 680px; display: inline-block">
				<p>
					現在時刻:&nbsp;<span id="view_clock"></span>
				</p>
				<script type="text/javascript">
					timerID = setInterval('clock()',1000); //1秒毎にclock()を実行

					function clock() {
						document.getElementById("view_clock").innerHTML = getNow();

					}

					function getNow() {
						var now = new Date();
						var year = now.getFullYear();
						var mon = now.getMonth()+1;
						var day = now.getDate();
						var hour = now.getHours();
						var min = now.getMinutes();
						var sec = now.getSeconds();

						//出力用
						var s = year + "年" + mon + "月" + day + "日&nbsp;" + hour + "時" + min + "分" + sec + "秒";
						return s;
					}
				</script>

				<p>ログイン:&nbsp;${session.User.jpName}&nbsp;様&nbsp;&nbsp;</p>
				<p id="outbutton">
					<a href="logout.action">ログアウト</a>
				</p>
			</div>

		</td>
			</tr>
			<tr>
				<td id="container" style="background: #d2e9ff;">
<div id="tabs">
	  <ul >
	    <li id="payTag1"><a  style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-1">入荷済（買掛金)統計一覧</a></li>
	    <li id="payTag2"><a style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-2">仕入先別納品済（買掛金）統計一覧</a></li>
	  </ul>
	<div id="print">
	    <div id="tabs-1">
	  		<div id="flip" ><a id="Cyear"></a>年<a id="Cmon"></a>月入荷済み一覧<br/><br/>
	  		<input id="receivemoneybayear" type="hidden" value="<s:property value='year'/>">
			<input id="receivemoneybamon" type="hidden" value="<s:property value='month'/>">
		  			<div  id="flip1">
		  				<select id="bayear"></select>年<select id="bamon"></select>月
		  				<input  value="検索" type="button" type="hidden" style="width:70px" onclick="PayMoneyFind()"  />&nbsp;&nbsp;&nbsp;
		  				<input  value="保存" type="hidden" style="width:70px" onclick="receivemoneySave()"  />&nbsp;&nbsp;&nbsp;
		  				<input  value="Excel出力" type="hidden" style="width:70px" onclick="nexdeliveryFind()" />&nbsp;&nbsp;&nbsp;
		  				<input  value="印刷" type="hidden" style="width:70px" onclick="nexdeliveryFind()"  />
		  			</div>
		  		</div>

		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			<thead>
		  			<tr class="atr">
		  				<td width="200px" align="center">仕入先</td>
		  				<td width="80px" align="center">商品種別</td>
						<td width="80px" align="center">今月注文金額</td>
						<td width="80px" align="center">出金金額</td>
						<td width="80px" align="center">期末残高</td>
						<td width="80px" align="center">ドル残高</td>
						<td width="80px" align="center">人民元残高</td>
						<td width="80px" align="center">期限超未払い（注文番号/金額）</td>
		  			</tr>
		  			</thead>
		  			<tbody id="Ctable">
					<s:iterator value="arrivalResult">
						<tr  align="center">
							<td><s:property value="supplierName"/></td>
							<td align="center">
								<s:if test="categoryType==1">材料</s:if> 
								<s:if test="categoryType==2">生产设备</s:if> 
								<s:if test="categoryType==3">检查设备</s:if> 
								<s:if test="categoryType==4">其他</s:if>
							</td>
							<td align="right"><s:property value="orderAmountOfThisMonth"/></td>
							<td align="right"><s:property value="payAmount"/></td>
							<td align="right"><s:property value="balance"/></td>
							<td align="right"><s:property value="balanceOfDollar"/></td>
							<td align="right"><s:property value="balanceOfChinese"/></td>
							<td><s:property value="orderCdAmount"/></td>
							<td style="display:none"><s:property value="payId"/></td>
							<td style="display:none"><s:property value="supplierId"/></td>
						</tr>
					</s:iterator>
					</tbody>
		  		</table>
	  	</div>
	    <div id="tabs-2">
	  		<div id="flip" ><select id="year"></select>年<select id="mon"></select>月末　仕入先別納品済（買掛金）一覧<br/><br/>
	  			<input id="receivemoneyyear" type="hidden" value="<s:property value='receivemoneyyear'/>">
				<input id="receivemoneymm" type="hidden" value="<s:property value='receivemoneymm'/>">
				<input id="customerID" type="hidden" value="">
				<input id="customerIDName1" type="hidden" value="">
		  			<div  id="flip1">
		  				仕入先名<input id="customerIDName" value="" type="text" style="width:200px;"    onBlur="payfindCoustemID(this);" onkeydown="paykeydown(event);"/>
		  				<input  value="検索" type="button" style="width:70px" onclick="payFind()"  />&nbsp;&nbsp;&nbsp;
						<input  value="クリア" type="button" style="width:70px" onclick="payClean()"  />
		  				<input  value="保存" type="hidden"style="width:70px" onclick="nexdeliveryFind()"  />&nbsp;&nbsp;&nbsp;
		  				<input  value="Excel出力" type="hidden" style="width:70px" onclick="nexdeliveryFind()" />&nbsp;&nbsp;&nbsp;
		  				<input  value="印刷" type="hidden"style="width:70px" onclick="nexdeliveryFind()"  />
		  			</div>
		  		</div>

		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			 <thead>
			  			<tr class="atr">
			  				<td width="90px" align="center">入荷日・支払日</td>
			  				<td width="80px" align="center">品名</td>
							<td width="130px" align="center">注文番号</td>
							<td width="80px" align="center">購入金額</td>
							<td width="80px" align="center">消費税</td>
							<td width="100px" align="center">小計</td>
							<td width="80px" align="center">支払金額</td>
			  				<td width="80px" align="center">残高</td>
			  				<td width="50px" align="center">取引No.</td>
			  				<td width="100px" align="center">備考</td>
			  			</tr>
		  			</thead> 
		  			<tbody id="Dtbody1">
					<%--  <s:iterator value="payableAmount"> 
						<tr  align="center">
							
							<td>
								<s:if test="%{#orderNo==null}">
									<input value="<s:property value='payDate'/>" type="text" class="colorInput" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="width:80px;border:0px;font-size:12px;text-align:center;" readonly="readonly" />
								</s:if> 
								<s:else>
									<s:property value="payDate"/>
								</s:else>
							</td>
							<td>
								<input value="<s:property value="modelCd"/>" type="text" class="colorInput"  style="width:150px;border:0px;font-size:12px;text-align:center;"  />
							</td>
							<td><s:property value="orderNo"/></td>
							<td><s:property value="purchaseAmount"/></td>
							<td><s:property value="exciseAmount"/></td>
							<td><s:property value="totalAmount"/></td>
							
							<td class="aclass"><input  class="binput" type="text" value="<s:property value="payAmount"/>" /></td>
							<td><s:property value="balance"/></td>
							<td class="aclass">
								<input class="cinput" value = "<s:property value="tradingNo"/>" type="text"  />
							</td>
							<td class="aclass"><input  class="dinput" type="text" value = "<s:property value="note"/>"/></td>
							<td style="display:none"><s:property value="orderId"/></td>
							<td style="display:none"><s:property value="detailId"/></td>
							<td style="display:none"><s:property value="supplierId"/></td>
						</tr>
					 </s:iterator> --%>
					</tbody>
		  		</table>
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			 <thead>
		  				<tr class="atr">
			  				<td width="90px" align="center">入荷日・支払日</td>
			  				<td width="80px" align="center">品名</td>
							<td width="130px" align="center">注文番号</td>
							<td width="80px" align="center">購入金額</td>
							<td width="80px" align="center">消費税</td>
							<td width="100px" align="center">小計</td>
							<td width="80px" align="center">支払金額</td>
			  				<td width="80px" align="center">残高</td>
			  				<td width="50px" align="center">取引No.</td>
			  				<td width="100px" align="center">備考</td>
			  			</tr>
		  			</thead> 
		  			<tbody id="Dtbody2">
		  			</tbody>
		  		</table>
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			 <thead>
		  				<tr class="atr">
			  				<td width="90px" align="center">入荷日・支払日</td>
			  				<td width="80px" align="center">品名</td>
							<td width="130px" align="center">注文番号</td>
							<td width="80px" align="center">購入金額</td>
							<td width="80px" align="center">消費税</td>
							<td width="100px" align="center">小計</td>
							<td width="80px" align="center">支払金額</td>
			  				<td width="80px" align="center">残高</td>
			  				<td width="50px" align="center">取引No.</td>
			  				<td width="100px" align="center">備考</td>
			  			</tr>
		  			</thead> 
		  			<tbody id="Dtbody3">
		  			</tbody>
		  		</table>
 		</div>
	</div>
</div>

				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="footer.jsp" %>
				</td>
			</tr>
		</tbody>
	</table>
	  <div id="floatPanel" >
	  	<div class="ctrolPanel" style="right:10px;">
				<a class="arrow" href="#"><span>顶部</span></a>
				<a class="contact" >
				<input type="button" id="save" value="保存" style="height:50px;width:80px" onclick="paySAVE()"/>
				</a><br/>
				<a class="contact" >
				<input type="button" id="saveBT" value="Excel出力" style="height:50px;width:80px" onclick="downloadArrival()"/>
				</a><br/>
				<a class="contact"   >
					<input type="button" value="印刷" onclick="$('#print').print()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
	</div>
</body>
</html>
