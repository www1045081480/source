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
<script src="js/jquery.numfm1.2.4.js?${initParam.version}"></script>
<script src="js/jquery.numeric.js?${initParam.version}"></script>
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
.binput1{
	border:0px solid;
	width:80px;
	background-color:#C4EFF4;
	text-align:right;
}
.binput2{
	border:0px solid;
	width:80px;
	text-align:right;
}
.cinput{
			border:0px solid;
			width:50px;
			background-color:#C4EFF4;
			text-align:right;
}
.dinput{
			border:0px solid;
			width:100px;
			background-color:#C4EFF4;
}
.cinput9{
			border:0px solid;
			width:50px;
			text-align:right;
}
.dinput9{
			border:0px solid;
			width:100px;
}
.aclass{
	background-color:#C4EFF4;
}
.aclass1{
	background-color:#C0C0C0;
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
<body onload="receivemoneyLoad()">
	<table class="stable" id="wrapper" border="0">
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
	    <li id="receiveTag1"><a  style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-1" >出荷済（売掛金）統計一覧</a></li>
	    <li id="receiveTag2"><a style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-2">得意先別出荷済（売掛金）一覧</a></li>
	  </ul>
	<div id="print">
	    <div id="tabs-1" >
	  		<div id="flip" ><a id="Cyear"></a>年<a id="Cmon"></a>月出荷済み一覧<br/><br/>
	  		<input id="receivemoneybayear" type="hidden" value="<s:property value='year'/>">
			<input id="receivemoneybamon" type="hidden" value="<s:property value='month'/>">
		  			<div  id="flip1">
		  				<select id="bayear"></select>年<select id="bamon"></select>月
		  				<input  value="検索" type="button" style="width:70px" onclick="receivemoneyFind()"  />&nbsp;&nbsp;&nbsp;
						
		  				<input  value="保存" type="hidden" style="width:70px" onclick="receivemoneySave()"  />&nbsp;&nbsp;&nbsp;
		  				<input  value="Excel出力" type="hidden" style="width:70px" class="binput" />&nbsp;&nbsp;&nbsp;
		  				<input  value="印刷" type="hidden" style="width:70px" onclick="nexdeliveryFind()"  />
		  			</div>
		  		</div>

		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			<thead>
		  			<tr class="atr">
		  				<td width="200px" align="center">得意先</td>
		  				<td width="80px" align="center">商品種別</td>
						<td width="80px" align="center">今月販売金額</td>
						<td width="80px" align="center">入金金額</td>
						<td width="80px" align="center">期末残高</td>
						<td width="80px" align="center">ドル残高</td>
						<td width="80px" align="center">人民元残高</td>
						<td width="80px" align="center">四ヶ月超未回収（見積番号/金額）</td>
		  			</tr>
		  			</thead>
		  			<tbody id="Ctable">
					<s:iterator value="shipResult">
						<tr  align="center">
							<td ><s:property value="customerName"/></td>
							<td>
								<s:if test="categoryType==1">材料</s:if> 
								<s:if test="categoryType==2">生产设备</s:if> 
								<s:if test="categoryType==3">检查设备</s:if> 
								<s:if test="categoryType==4">其他</s:if>
							</td>
							<td align="right"><s:property value="sellAmountOfThisMonth"/></td>
							<td align="right"><s:property value="depositAmount"/></td>
							<td align="right"><s:property value="balance"/></td>
							<td align="right"><s:property value="balanceOfDollar"/></td>
							<td align="right"><s:property value="balanceOfChinese"/></td>
							<td><s:property value="estimationCdAmount"/></td>
							<td style="display:none"><s:property value="estimationDetailId"/></td>
							<td style="display:none"><s:property value="invoiceDetailId"/></td>
							<td style="display:none"><s:property value="customerId"/></td>
						</tr>
					</s:iterator>
					</tbody>
		  		</table>
	  	</div>
	    <div id="tabs-2">
	  		<div id="flip" ><select id="year"></select>年<select id="mon"></select>月末　得意先別出荷済（売掛金）一覧<br/><br/>
	  			<input id="receivemoneyyear" type="hidden" value="<s:property value='receivemoneyyear'/>">
				<input id="receivemoneymm" type="hidden" value="<s:property value='receivemoneymm'/>">
				<input id="customerID" type="hidden" value="<s:property value='customerID'/>">
				<input id="customerIDName1" type="hidden" value="<s:property value='customerIDName'/>">
		  			<div  id="flip1">
		  				販売先名<input id="customerIDName" value="" type="text" style="width:200px;"    onBlur="receivefindCoustemID(this);" onkeydown="receivekeydown(event);"/>
		  				<input  value="検索" type="button" style="width:70px" onclick="receiveFind()"  />&nbsp;&nbsp;&nbsp;
						<input  value="クリア" type="button" style="width:70px" onclick="receiveClean()"  />
		  				<input  value="保存" type="hidden"style="width:70px" onclick="nexdeliveryFind()"  />&nbsp;&nbsp;&nbsp;
		  				<input  value="Excel出力" type="hidden" style="width:70px" onclick="nexdeliveryFind()" />&nbsp;&nbsp;&nbsp;
		  				<input  value="印刷" type="hidden"style="width:70px" onclick="nexdeliveryFind()"  />
		  			</div>
		  		</div>

		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			<thead>
		  			<tr class="atr">
		  				<td width="100px" align="center">发货日期</td>
		  				<td width="120px" align="center">订单号</td>
						<td width="120px" align="center">新安发票号</td>
						<td width="180px" align="center">内容</td>
						<td width="90px" align="center">新增销售</td>
						<td width="90px" align="center">回款金额</td>
		  				<td width="70px" align="center">当月回款</td>
						<td width="110px" align="center">期末余额</td>
						<td width="50px" align="center">取引No</td>
		  				<td width="100px" align="center">备注</td>
		  			</tr>
		  			<tr align="center" class="atr">
		  				<td>计算式</td>
		  				<td></td>
						<td></td>
						<td></td>
						<td>①</td>
						<td>②</td>
						<td></td>
						<td>③=③＋①－②</td>
						<td></td>
						<td></td>
		  			</tr>
		  			</thead>
		  			<tbody id="Dtbody">
					<%-- <s:iterator value="receivableAmount"> 
						<tr  align="center" class="btr" >
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td><s:property value="customerName"/>1,234,567</td>
							<td></td>
							<td></td>
						</tr>
						<tr  align="center">
							<td><s:property value="deliveryDate"/></td>
							<td><s:property value="orderNop"/></td>
							<td><s:property value="xinanOrderNop"/></td>
							<td><s:property value="content"/></td>
							<td><s:property value="earningAmount"/></td>
							<td><s:property value="receiveAmount"/></td>
							<td class="aclass"><input  class="binput" type="text" value="" /></td>
							<td><s:property value="endingBalance"/></td>
							<td class="aclass">
								<input class="cinput" value = "<s:property value="tradingNo"/>" type="text"  />
							</td>
							<td class="aclass"><input  class="dinput" type="text" value = "<s:property value="note"/>"/></td>
							<td style="display:none"><s:property value="estimationId"/></td>
							<td style="display:none"><s:property value="detailId"/></td>
							<td style="display:none"><s:property value="currency"/></td>
						</tr>
						
					</s:iterator>  --%>
					</tbody>
		  		</table>
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			<thead>
		  			<tr class="atr">
		  				<td width="100px" align="center">发货日期</td>
		  				<td width="120px" align="center">订单号</td>
						<td width="120px" align="center">新安发票号</td>
						<td width="180px" align="center">内容</td>
						<td width="90px" align="center">新增销售</td>
						<td width="90px" align="center">回款金额</td>
		  				<td width="70px" align="center">当月回款</td>
						<td width="110px" align="center">期末余额</td>
						<td width="50px" align="center">取引No</td>
		  				<td width="100px" align="center">备注</td>
		  			</tr>
		  			<tr align="center" class="atr">
		  				<td>计算式</td>
		  				<td></td>
						<td></td>
						<td></td>
						<td>①</td>
						<td>②</td>
						<td></td>
						<td>③=③＋①－②</td>
						<td></td>
						<td></td>
		  			</tr>
		  			</thead>
		  			<tbody id="Dtbody4">
		  			</tbody>
		  		</table>
		  	<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 900px;margin-top:0px;">
		  			<thead>
		  			<tr class="atr">
		  				<td width="100px" align="center">发货日期</td>
		  				<td width="120px" align="center">订单号</td>
						<td width="120px" align="center">新安发票号</td>
						<td width="180px" align="center">内容</td>
						<td width="90px" align="center">新增销售</td>
						<td width="90px" align="center">回款金额</td>
		  				<td width="70px" align="center">当月回款</td>
						<td width="110px" align="center">期末余额</td>
						<td width="50px" align="center">取引No</td>
		  				<td width="100px" align="center">备注</td>
		  			</tr>
		  			<tr align="center" class="atr">
		  				<td>计算式</td>
		  				<td></td>
						<td></td>
						<td></td>
						<td>①</td>
						<td>②</td>
						<td></td>
						<td>③=③＋①－②</td>
						<td></td>
						<td></td>
		  			</tr>
		  			</thead>
		  			<tbody id="Dtbody5">
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
				<input type="button" id="save" value="保存" style="height:50px;width:80px" onclick="receiveSAVE()"/>
				</a><br/>
				<a class="contact" >
				<input type="button" id="saveBT" value="Excel出力" style="height:50px;width:80px" onclick="downloadShipping()"/>
				</a><br/>
				<a class="contact"   >
					<input type="button" value="印刷" onclick="$('#print').print()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
	</div>
</body>
</html>
