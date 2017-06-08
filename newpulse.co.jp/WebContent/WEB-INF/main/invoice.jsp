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
<script type="text/javascript" src="../js/receivemoney.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/CheckRole.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="../js/jquery.autosize.min.js"></script>

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
.zinput{
	width:335px;
	border:0px solid;
}
.zzzinput{
	width:335px;
	border:0px solid;
	background-color:#CDFFFF;
}
.MNinput{
	border:0px solid;
	background-color:#CDFFFF;
	width:165px;
}
.MNinput1{
	border:0px solid;
	background-color:#CDFFFF;
	width:195px;
}
.colorInput{
	background-color:#CDFFFF;
}
.colorInput1{
	background-color:#CDFFFF;
	width:175px;
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
.je,#hjsum{
	text-align:right;

}
.zclass{
	width:300px;

}
.xclass{

}
.yclass{


}
.xxinput{
	text-align:right;
	width:124px;
	height:20px;
	border:0px;
	font-size:13px;


}
.yyinput{
	text-align:right;
	width:120px;
	height:20px;
	border:0px;
	font-size:13px;

}
.zzinput{
	text-align:right;
	width:80px;
	height:21px;
	border:0px;
	font-size:13px;

}
.ssinput{
	border:0px solid;
	overflow: hidden; 
	word-wrap: break-word; 
	resize: horizontal;
	height:21px;
	min-width:170px;
	max-width:170px;
}
.ssinput1{
	border:0px solid;
	overflow: hidden; 
	word-wrap: break-word; 
	resize: horizontal;
	min-width:150px;
	max-width:150px;
}
.bank_input{
	border:0px solid;
	width:350px;
	font-weight:bold;

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
#tabs{
			width:800px;
			margin-left:auto;
			margin-right:auto;
		}
#tabs-1,#tabs-2,#tabs-3{
			border: 0px solid ;
			width: 649px;
			height:auto;

			background-color:white;
			margin-left:auto;
			margin-right:auto;
}
</style>
</head>
<body onload="LoadInvoice()">
    <input id="PackingListId" type="hidden" value="<s:property value='PackingListId'/>">

	<input id="InvoiceID" type="hidden" value="<s:property value='InvoiceID'/>">
	<!--  用来加载画面值的-->
	 <input id="InvoiceCD" type="hidden" value="<s:property value='InvoiceCD'/>"> 
	<input id="arrive" type="hidden" value="<s:property value='arrive'/>">
	<input id="way" type="hidden" value="<s:property value='way'/>">
	<input id="EnShortName" type="hidden" value="<s:property value='EnShortName'/>">
	<input id="EnAddress1" type="hidden" value="<s:property value='EnAddress3'/>">
	<input id="EnAddress2" type="hidden" value="<s:property value='EnAddress2'/>">
	<input id="EnAddress3" type="hidden" value="<s:property value='EnAddress1'/>">
	<input id="EnName" type="hidden" value="<s:property value='EnName'/>">
	<input id="Tel1" type="hidden" value="<s:property value='Tel1'/>">
	<input id="Fax" type="hidden" value="<s:property value='Fax'/>">
	
	<!-- 用来区分invoice做成还是invoice修改 -->
	<input id="KIND" type="hidden" value="<s:property value='KIND'/>">
	<!-- 用来区分invoice表示，修改，承认 -->
	<input id="kind" type="hidden" value="<s:property value='kind'/>">
	
	<%-- <input id="userId" type="hidden" value="${session.User.userId}"> --%>
	
	
	<!-- invoice修改加载属性-->
<%-- 	<input type="hidden" id="InvoiceCd" value="<s:property value='InvoiceCd'/>"> --%>
	<input type="hidden" id="CustomerId" value="<s:property value='CustomerId'/>">
	<input  type="hidden" id="CustomerName"  value="<s:property value='CustomerName'/>">
	<input  type="hidden" id="Address1"  value="<s:property value='Address1'/>">
	<input type="hidden" id="Address2"  value="<s:property value='Address2'/>">
	<input  type="hidden" id="Address3" value="<s:property value='Address3'/>">
	<input type="hidden" id="Tel"  value="<s:property value='Tel'/>">
	<input type="hidden" id="Fax"  value="<s:property value='Fax'/>">
	<input type="hidden" id="Title"  value="<s:property value='Title'/>">
	<input type="hidden" id="Amount"  value="<s:property value='Amount'/>">
	<input type="hidden" id="DeliveryType"  value="<s:property value='DeliveryType'/>">
	<input  type="hidden" id="ShippingType"  value="<s:property value='ShippingType'/>">
	<input type="hidden" id="Currency"  value="<s:property value='Currency'/>">
	<input type="hidden" id="OrderNo"  value="<s:property value='OrderNo'/>">
	<input  type="hidden" id="ReceiptNo"  value="<s:property value='ReceiptNo'/>">
	<input  type="hidden" id="Receiver"  value="<s:property value='Receiver'/>">
	<input  type="hidden" id="ShippingCompany"  value="<s:property value='ShippingCompany'/>">
	<input type="hidden" id="ShippingFrom" value="<s:property value='ShippingFrom'/>">
	<input  type="hidden" id="ShippingTo"  value="<s:property value='ShippingTo'/>">
	<input  type="hidden" id="IssueDate" value="<s:property value='IssueDate'/>">
	<input  type="hidden" id="Notify" value="<s:property value='Notify'/>">
	<input type="hidden" id="Marks" value="<s:property value='Marks'/>">
	<input type="hidden" id="Quantity"  value="<s:property value='Quantity'/>">
	<input type="hidden" id="Unit"  value="<s:property value='Unit'/>">
	<input type="hidden" id="UserId"  value="<s:property value='UserId'/>">
	<input  type="hidden" id="ShippingDate"  value="<s:property value='ShippingDate'/>">
	<%--保存invoice和package关联用的orderId集合 --%>
	<input type="hidden" id="orderId" value="<s:property value='orderId'/>">
	
	<input type="hidden" id="InFlgChange" value="<s:property value='InFlgChange'/>">
	
	
	
	
	<input type="hidden" id="nw"  value="<s:property value='nw'/>">
	<input type="hidden" id="gw" value="<s:property value='gw'/>">
	<input type="hidden" id="m3" value="<s:property value='m3'/>">
	<input type="hidden"id="palletQuantity"  value="<s:property value='palletQuantity'/>">
	<input type="hidden" id="NGM" value="<s:property value='NGM'/>">
	
	
	
	
	
	<%-- <input  type="hidden" id="ORDERID"  value="<s:property value='ORDERID'/>"> --%>
	<table class="stable" id="wrapper" border="0">
		<tbody>
			<tr>
				<td rowspan="3" id="wrapper-left">	</td>
		<td class="stable">
			<!-- headerここから -->
			<div id="header">
				<input id="checkRole" type="hidden" value="${session.User.role}">
				<table>
					<tbody>
						<tr>
							<td><a href="xinan/menu.action"><img src="../images/xlog.png" alt="XINAN SYSTEM" width="285" height="49"></a>
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
	<div class="noprint">
	  <ul >
	    <li><a  id="invoiveTab" style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-1">INVOICE</a></li>
	    <li><a  id="packageTab" style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-2">PACKING LISET</a></li>
	    <li><a  id="declarationTab" style="color: blue; font-size:20px;font-weight:bold; " href="#tabs-3">DECLARATION</a></li>
	    <li><a  id="EMSTab" style="color: blue; font-size:20px;font-weight:bold;" href="#tabs-4">EMS</a></li>
	  </ul>
	</div>
	<div id="print">
	    <div id="tabs-1" >
	  			<table border="0px" cellspacing="0px" style="margin-left:auto;margin-right:auto;width: 650px;margin-top:40px;">
	  				<tr style="height: 40px ;border:1px solid">
	  					<td rowspan="3" style="border:1px solid;border-top:0px;border-left:0px;border-bottom:0px;font-size:60px;font-style:italic;" align="right">
	  					XAJ&nbsp;
	  					</td>
	  					<td align="center" style="font-weight: bold;font-size:24px;border:1px solid;height:20px;">XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
	  				</tr>
	  				<tr>
	  					<td style="border:1px solid;border-bottom:0px;border-top:0px;font-size:15px;" align="center">
	  						7F,NI&nbsp;BLDG2-61-4&nbsp;IKEBUKURO&nbsp;TOSHIMA&nbsp;KU&nbsp;TOKYO,JAPAN
	  					</td>
	  				</tr>
	  				<tr>
	  					<td style="border:1px solid;border-top:0px;font-size:15px;" align="center">
	  						<a style="font-weight:bold;">TEL:</a>+81-3-6912-7995，&nbsp;<a style="font-weight:bold;">FAX:</a>+81-3-6912-7996
	  					</td>
	  				</tr>
	  			</table>
	  		<!-- </div>
	  		<div style="border: 0px solid green;margin-top:3px;height:500px"> -->
	  			<p style="width:640px;margin-left:auto;margin-right:auto;border:0px solid" align="center"><u style="font-weight:bold;font-size:22px;">INVOICE</u></p>

	  			<table border="0px" cellspacing="0px" style="width:640px;margin-left:auto;margin-right:auto;margin-top:10px;font-size:13px">
	  				<tr style="font-weight:bold;">
	  					<td colspan="2" style="border-top:1px solid;width:335px">TO:</td>
	  					<td style="border-top:1px solid;border-left:1px solid;width:140px">Invoice&nbsp;No.</td>
	  					<td style="border-top:1px solid;width:155px">Date</td>
	  				</tr>
	  				<tr>
	  					<td rowspan="5" colspan="2" style="width:335px;border-bottom:1px solid;" >
	  						<input type="text" id="enShortName" class="zzzinput" value="" onblur="enShortNamechange(this);"/><br/>
	  						<input type="text" id="enName" class="zzzinput" value="" onblur="enNamechange(this);"/><br/>
	  						<input type="text" id="enAddress2" class="zzzinput" value="" onblur="enAddress2change(this);"/><br/>
	  						<input type="text" id="enAddress3" class="zzzinput" value="" onblur="enAddress3change(this);"/><br/>
	  						<input type="text" id="tel1" class="zzzinput" value="" onblur="tel1change(this);"/>
	  					<td style="border-bottom:1px solid;"><input type="text" id="invoiceNo" value="" class="colorInput" style="width:140px;border:0px;font-size:13;"  onmousedown="this.style.border='1px solid'" onblur="changeInvoiceNo(this);"/></td>
	  					<td style="width:155px;border-bottom:1px solid;"><input id="itoday" value="" type="text" class="colorInput" onfocus="setday1(2,this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="width:155px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td style="border-left:1px solid;">L/C&nbsp;No.</td>
	  					<td>Date</td>
	  				</tr>

	  				<tr>
	  					<td style="border-left:1px solid;border-bottom:1px solid;"><input type="text"  id="LCNOIN" value="T/T" class="colorInput" style="width:140px;border:0px;font-size:13;"  onmousedown="this.style.border='1px solid'" onblur="changeLCNO(this);"/></td>
	  					<td style="border-bottom:1px solid;"><input id="lcday" value="/&nbsp;&nbsp;/&nbsp;&nbsp;/" type="text" onfocus="setday1(3,this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" class="colorInput" style="width:155px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  				</tr>

	  				<tr style="font-weight:bold;">
	  					<td style="border-left:1px solid;">Issued&nbsp;by:</td>
	  					<td >Contract&nbsp;No.</td>
	  				</tr>
	  				<tr>
	  					<td style="border-left:1px solid;border-bottom:1px solid;"><input id="isday" class="colorInput" value="/&nbsp;&nbsp;/&nbsp;&nbsp;/" type="text" onfocus="setday1(4,this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="width:140px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;"><input id="ContractNo" type="text"  value="" class="colorInput" style="width:160px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="changeContractNo(this);"/></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td style="width:200px">Shipped&nbsp;by</td>
	  					<td style="width:135px">Sailing&nbsp;on&nbsp;or&nbsp;about</td>
	  					<td style="border-left:1px solid;width:140px">From</td>
	  					<td style="width:155px">To</td>
	  				</tr>
	  				<tr >
	  					<td style="border-bottom:1px solid;"><input  id="ShippingType1" type="text" class="colorInput" value="AIR FRIGHT"  style="width:200px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="changeShippedBy(this);"/></td>
	  					<td style="border-bottom:1px solid;"><input id="saday" class="colorInput" value="/&nbsp;&nbsp;/&nbsp;&nbsp;/" type="text" onfocus="setday1(5,this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="width:135px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;border-left:1px solid;"><input type="text" class="colorInput" id="invoiceFROM" value="TOKYO,JAPAN"  style="width:140px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="changeFROM(this);"/></td>
	  					<td style="border-bottom:1px solid;"><input type="text" id="invoiceTO" class="colorInput" value="ANQING,CHINA"  style="width:155px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="changeTO(this);"/></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td style="width:200px">Consignee</td>
	  					<td style="width:135px"></td>
	  					<td style="border-left:1px solid;width:140px">Notify Party</td>
	  					<td style="width:155px"></td>
	  				</tr>
	  				<tr>
	  					<td colspan="2" rowspan="5" style="border-bottom:1px solid;" >
	  						<input type="text" id="enShortName1" class="zzzinput" value="" onblur="enShortNamechange1(this);"/><br/>
	  						<input type="text" id="enName1" class="zzzinput" value="" onblur="enNamechange1(this);"/><br/>
	  						<input type="text" id="enAddress21" class="zzzinput" value="" onblur="enAddress2change1(this);"/><br/>
	  						<input type="text" id="enAddress31" class="zzzinput" value="" onblur="enAddress3change1(this);"/><br/>
	  						<input type="text" id="tel11" class="zzzinput" value="" onblur="tel1change1(this);"/>
	  					</td>
	  					<td colspan="2" rowspan="5" style="border-bottom:1px solid;border-left:1px solid;" >
<!-- 	  					<textarea style="border:0px solid;resize:none;overflow-y:hidden; width:295px;height:100px;">DELIVER TO:ZHANG YUNXIA 					SINOTRANS AIR TRANSPORTAION					DEVELOPMENT CO.,LTD.ANHUI BRANCH 						TEL:0551-63431581  EXT.8808 			FAX:0551-63431580</textarea>
 -->	  					<input type="text" id="enShortName2" class="zzzinput" value="" onblur="enShortNamechange2(this);"/><br/>
	  						<input type="text" id="enName2" class="zzzinput" value="" onblur="enNamechange2(this);"/><br/>
	  						<input type="text" id="enAddress22" class="zzzinput" value="" onblur="enAddress2change2(this);"/><br/>
	  						<input type="text" id="enAddress32" class="zzzinput" value="" onblur="enAddress3change2(this);"/><br/>
	  						<input type="text" id="tel12" class="zzzinput" value="" onblur="tel1change2(this);"/>
	  					</td>
	  				</tr>
	  			</table>
	  			<table border="0px" cellspacing="0px" style="width:664px;margin-left:auto;margin-right:auto;margin-top:2px;font-size:13px">
	  				<tr style="font-weight:bold;">
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:170px">Marks&nbsp;&#38&nbsp;Numbers</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:170px" >Discrption&nbsp;of&nbsp;Goods</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:80px" 	align="right">Quantity</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:124px;height:30px"   align="right">Unit&nbsp;Price
	  					</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:120px"  align="right">Total&nbsp;Amount</td>
	  				</tr>
	  			</table>
	  			<table border="0px" cellspacing="0px" style="width:664px;margin-left:auto;margin-right:auto;margin-top:2px;font-size:13px">
	  				<tr>
	  					<td colspan="5" style="border-bottom:1px solid;"></td>
	  				</tr>
	  				<tr>
	  					<td colspan="2"></td>
	  					<td colspan="3" align="center"><input type="text"  id="arriveWay" value="" class="colorInput" style="text-align:center;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
	  				</tr>
	  				<tr>
		  				<td  style="width:390px;vertical-align: top;">
							<!-- <input type="text" name="MenShortName" class="MNinput" value="" onblur="MshortName(this);"/><br/> -->
							<textarea class="ssinput1"  id="marksI" style="background-color:#CDFFFF;"></textarea>
						</td>
	  					<td colspan="4" style="border-top:0px solid; vertical-align: top;">
	  						<table border="0px" id="invoiceTab" cellspacing="0px" style="width:278px;margin-left:auto;margin-right:auto;margin-top:2px;padding-top:1px;font-size:13px">
					  				<s:iterator value="orderDetail">
					  					<tr>
		  									<td id="Invoicename" style="vertical-align: top;">
												<textarea onblur="changeInvoicename(this);" class="ssinput" style="background-color:#CDFFFF;"><s:property value="name"/>/<s:property value="PartsCd"/></textarea>
		  									</td>
						  					<td class="zclass" style="vertical-align: top;">
						  						<input  class="zzinput" style="background-color:#CDFFFF;" type="text"  value="<s:property value="quantity"/>"  onmousedown="this.style.border='1px solid'" onblur="invoiceSum(this)"/>

						  					</td>
						  					<td class="xclass" style="vertical-align: top;">
						  						<input  class="xxinput" style="background-color:#CDFFFF;" type="text"  value="<s:property value="unitPrice"/>"    onmousedown="this.style.border='1px solid'" onblur="invoiceSum(this)"/>

						  					</td>
						  					<td class="yclass" style="vertical-align: top;">
						  						<input  class="yyinput"  type="text"  value=""  readonly="readonly"/>
						  					</td>
						  					<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="itemId"/>"  readonly="readonly"/>
						  					</td>
						  					<%-- <td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="invoiceId"/>"  readonly="readonly"/>
						  					</td> --%>
						  					<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="modelCd"/>"  readonly="readonly"/>
						  					</td>
						  					
	  									</tr>
	  								</s:iterator>
									<s:iterator value="invoiceDetail">
					  					<tr>
		  									<td id="Invoicename" style="vertical-align: top;">
												<textarea onblur="changeInvoicename(this);" class="ssinput" style="background-color:#CDFFFF;"><s:property value="name"/>/<s:property value="PartsCd"/></textarea>
		  									</td>
						  					<td class="zclass" style="vertical-align: top;">
						  						<input  class="zzinput" style="background-color:#CDFFFF;" type="text"  value="<s:property value="quantity"/>"  onmousedown="this.style.border='1px solid'" onblur="invoiceSum(this)"/>

						  					</td>
						  					<td class="xclass" style="vertical-align: top;">
						  						<input  class="xxinput" style="background-color:#CDFFFF;" type="text"  value="<s:property value="unitPrice"/>"    onmousedown="this.style.border='1px solid'" onblur="invoiceSum(this)"/>

						  					</td>
						  					<td class="yclass" style="vertical-align: top;">
						  						<input  class="yyinput"  type="text"  value="<s:property value="amount"/>"  readonly="readonly"/>
						  					</td>
						  					<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="itemId"/>"  readonly="readonly"/>
						  					</td>
						  					<%-- <td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="invoiceId"/>"  readonly="readonly"/>
						  					</td> --%>
						  					<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="modelCd"/>"  readonly="readonly"/>
						  					</td>
	  									</tr>
									</s:iterator>
	  						</table>
	  					</td>
	  				</tr>
	  				<tr style="height: 20px">
	  					<td colspan="5" style="border-bottom:1px solid;"></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td></td>
	  					<td style="width:225px">GRAND TOTAL</td>
	  					<td align="right" style="width:185px"> <a id="sumKGS"></a><input  id="unitType"  style="background-color:#CDFFFF;width:50px" type="text" value="kgs"/></td>
	  					<td style="width:85px"></td>
	  					<td style="width:200px" align="right" ><a id="moneytype">￥</a> <a id="sumMONEY"></a></td>
	  				</tr>
	  			</table>
	  	<!-- 	</div>
	  		<div style="border:0px solid red;width:640px;height:200px;margin-top:20px;"> -->
	  			<table border="0px" cellspacing="0px" style="width:644px;margin-left:auto;margin-right:auto; margin-top:40px;font-size:13px">

	  				<tr style="height: 110px;border:1px solid;font-weight:bold;">
	  					<td  style="width: 300px"></td>
	  					<td  align="LEFT" >
		  					<input type="text" value="XIN-AN TRADING COMPANY,LTD." class="bank_input"/><br/>
		  					<input type="text" value="MIZUHO BANK,LTD." class="bank_input"/><br/>
		  					<input type="text" value="KYOBASHI BRANCH" class="bank_input"/><br/>
		  					<input type="text" value="2-7-19 KYOBASHI,CHUO-CTTY,TOKUO JAPAN" class="bank_input"/><br/>
		  					<input type="text" value="0153580(JPY)" class="bank_input"/><br/>
	  					</td>
	  				</tr>
	  				<tr style="height: 30px;border:1px solid;font-weight:bold;">
	  					<td ></td>
	  					<td align="center">&nbsp;&nbsp;XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
	  				</tr>

	  				<tr style="height: 50px;border:1px solid;font-weight:bold;">
	  					<td ></td>
	  					<td align="center">
	  					<div id="aproveImg">
	  						<img width="60px" height="60px" id="user-sealI" style="display:none" src="xinan/usersign.action?userId=${session.User.userId}"/>
	  						<input type="button" id="approveI" value="承认"  onclick="invoiceApprve()"/>
	  					</div>
	  					<hr style="width:220px; margin-left:90px; height: 0px;border:1px solid;font-weight:bold"/>
	  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MANAGER,OVERSEAS&nbsp;OPERATIONS&nbsp;DEPT.</td>
	  				</tr>
	  			</table>
	  	</div>

	    <div id="tabs-2" >
	  		<div style="border:0px solid red;">
	  			<table border="0px" cellspacing="0px" style="margin-left:auto;margin-right:auto;width: 650px;margin-top:40px;">
	  				<tr style="height: 40px ;border:1px solid">
	  					<td rowspan="3" style="border:1px solid;border-top:0px;border-left:0px;border-bottom:0px;font-size:60px;font-style:italic;" align="right">
	  					XAJ&nbsp;
	  					</td>
	  					<td align="center" style="font-weight: bold;font-size:24px;border:1px solid;height:20px;">XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
	  				</tr>
	  				<tr>
	  					<td style="border:1px solid;border-bottom:0px;border-top:0px;font-size:15px;" align="center">
	  						7F,NI&nbsp;BLDG2-61-4&nbsp;IKEBUKURO&nbsp;TOSHIMA&nbsp;KU&nbsp;TOKYO,JAPAN
	  					</td>
	  				</tr>
	  				<tr>
	  					<td style="border:1px solid;border-top:0px;font-size:15px;" align="center">
	  						<a style="font-weight:bold;">TEL:</a>+81-3-6912-7995，&nbsp;<a style="font-weight:bold;">FAX:</a>+81-3-6912-7996
	  					</td>
	  				</tr>
	  			</table>
	  		<!-- </div>
	  		<div style="border: 0px solid green;margin-top:3px;height:500px"> -->
	  			<p style="width:640px;margin-left:auto;margin-right:auto;border:0px solid;font-weight:bold;font-size:22px;" align="center">PACKING LIST/WEIGHT MEMO</p>

	  			<table border="0px" cellspacing="0px" style="width:640px;margin-left:auto;margin-right:auto;margin-top:10px;font-size:13px">
	  				<tr style="font-weight:bold;">
	  					<td colspan="2" style="border-top:1px solid;width:335px">TO:</td>
	  					<td style="border-top:1px solid;border-left:1px solid;width:140px">Invoice&nbsp;No.</td>
	  					<td style="border-top:1px solid;width:155px">Date</td>
	  				</tr>
	  				<tr>
	  					<td rowspan="5" colspan="2" style="width:335px;border-bottom:1px solid;" >
	  						<input type="text" id="enShortNameP" class="zinput" value="" readonly="readonly" /><br/>
	  						<input type="text" id="enNameP" class="zinput" value="" readonly="readonly" /><br/>
	  						<input type="text" id="enAddress2P" class="zinput" value="" readonly="readonly" /><br/>
	  						<input type="text" id="enAddress3P" class="zinput" value="" readonly="readonly" /><br/>
	  						<input type="text" id="tel1P" class="zinput" value="" readonly="readonly" />
	  					</td>
	  					<td id="pinvoiceNo" style="width:140px;border-left:1px solid;border-bottom:1px solid;"></td>
	  					<td style="width:155px;border-bottom:1px solid;"><input id="ptoday" value="" type="text"  style="width:155px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td style="border-left:1px solid;">L/C&nbsp;No.</td>
	  					<td>Date</td>
	  				</tr>

	  				<tr>
	  					<td style="border-left:1px solid;border-bottom:1px solid;"><input type="text"  id="pLCNO" value="T/T"  style="width:140px;border:0px;font-size:13;"  readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;"><input id="plcday" value="/&nbsp;&nbsp;/&nbsp;&nbsp;/" type="text"  style="width:155px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  				</tr>

	  				<tr style="font-weight:bold;">
	  					<td style="border-left:1px solid;">Issued&nbsp;by:</td>
	  					<td >Contract&nbsp;No.</td>
	  				</tr>
	  				<tr>
	  					<td style="border-left:1px solid;border-bottom:1px solid;"><input id="disday" value="/&nbsp;&nbsp;/&nbsp;&nbsp;/" type="text"  style="width:140px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;"><input type="text"  id="ContractNO" readonly="readonly"  value=""  style="width:160px;border:0px;font-size:13px;"  /></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td style="width:200px">Shipped&nbsp;by</td>
	  					<td style="width:135px">Sailing&nbsp;on&nbsp;or&nbsp;about</td>
	  					<td style="border-left:1px solid;width:140px">From</td>
	  					<td style="width:155px">To</td>
	  				</tr>
	  				<tr >
	  					<td style="border-bottom:1px solid;"><input type="text"  value="AIR FRIGHT"  id="ShippedBy" style="width:200px;border:0px;font-size:13px;"  readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;"><input id="dsaday" value="/&nbsp;&nbsp;/&nbsp;&nbsp;/" type="text"  style="width:135px;border:0px;font-size:13px;" readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;border-left:1px solid;"><input type="text"  id="packageFROM" value="TOKYO,JAPAN"  style="width:140px;border:0px;font-size:13px;"  readonly="readonly" /></td>
	  					<td style="border-bottom:1px solid;"><input type="text" id="packageTO" value="ANQING,CHINA"  style="width:155px;border:0px;font-size:13px;"  readonly="readonly" /></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td style="width:200px">Consignee</td>
	  					<td style="width:135px"></td>
	  					<td style="border-left:1px solid;width:140px">Notify Party</td>
	  					<td style="width:155px"></td>
	  				</tr>
	  				<tr>
	  					<td colspan="2" style="border-bottom:1px solid;" >
	  						<input type="text" id="enShortNameP1" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="enNameP1" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="enAddress2P1" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="enAddress3P1" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="tel1P1" class="zinput" value=""readonly="readonly" />
	  					</td>
	  					<td colspan="2" style="border-bottom:1px solid;border-left:1px solid;" >
	  						<input type="text" id="enShortNameP2" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="enNameP2" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="enAddress2P2" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="enAddress3P2" class="zinput" value=""readonly="readonly" /><br/>
	  						<input type="text" id="tel1P2" class="zinput" value=""readonly="readonly" />
	  					</td>
	  				</tr>
	  			</table>
	  			<table border="0px" cellspacing="0px" style="width:668px;margin-left:auto;margin-right:auto;margin-top:2px;font-size:13px">
	  				<tr style="font-weight:bold;">
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:220px">Marks&nbsp;&#38&nbsp;Numbers</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:170px" >Discrption&nbsp;of&nbsp;Goods</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:120px"  align="right"	>Quantity</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:100px"  align="center" >N/W</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:80px"  align="center">G/W</td>
	  					<td style="border-top:1px solid;border-bottom:1px solid;width:80px"  align="right">M3</td>
	  				</tr>
	  			</table>
	  			<table border="0px" cellspacing="0px" style="width:668px;margin-left:auto;margin-right:auto;margin-top:2px;font-size:13px">

	  				<tr>
	  					<td colspan="6" style="border-bottom:1px solid;"></td>
	  				</tr>
	  				<tr>
	  					<td  style="width:180px;vertical-align: top;" >
	  						<textarea class="ssinput1" id="marksP" style="background-color:#CDFFFF;"></textarea>
						</td>
	  					<td colspan="5" style="vertical-align: top;">
	  						<table id="packageTab1" border="0px" cellspacing="0px" style="width:278px;margin-left:auto;margin-right:auto;margin-top:2px;font-size:13px">
	  							<s:iterator value="orderDetail">
	  								<tr>
										<td style="vertical-align: top;">
												<textarea class="ssinput" style="background-color:#CDFFFF;"><s:property value="name"/>/<s:property value="PartsCd"/></textarea>
	  									</td>

	  									<td style="vertical-align: top;">
	  										<input   class="colorInput" type="text"  value="<s:property value="quantity"/>"  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)" />
	  									</td>
					  					<td style="vertical-align: top;">
					  						<input   class="colorInput" type="text"  value=""  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)" />
					  					</td>
					  					<td style="vertical-align: top;">
					  						<input   class="colorInput" type="text"  value=""  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'"  onblur="DQuantityChange1(this)"/>
					  					</td>

					  					<td style="vertical-align: top;">
					  						<input   class="colorInput" type="text"  value=""  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)"/>
					  					</td>
					  					<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="itemId"/>"  readonly="readonly"/>
						  				</td>
						  				<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="modelCd"/>"  readonly="readonly"/>
						  				</td>
					  				</tr>
	  							</s:iterator>
	  							<s:iterator value="packingListDetail">
	  								<tr>
	  									<td style="vertical-align: top;">
												<textarea class="ssinput" style="background-color:#CDFFFF;"><s:property value="name"/>/<s:property value="PartsCd"/></textarea>
	  									</td>
	  									<td style="vertical-align: top;">
	  										<input   class="colorInput" type="text"  value="<s:property value="quantity"/>"  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)" />
	  									</td>
					  					<td style="vertical-align: top;">
					  						<input   class="colorInput" type="text"  value="<s:property value="nw"/>"  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)" />
					  					</td>
					  					<td style="vertical-align: top;">
					  						<input   class="colorInput" type="text"  value="<s:property value="gw"/>"  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)"/>
					  					</td>

					  					<td style="vertical-align: top;">
					  						<input   class="colorInput" type="text"  value="<s:property value="m3"/>"  style="text-align:right;width:80px;height:20px;border:0px;font-size:13px;"  onmousedown="this.style.border='1px solid'" onblur="packageSum(this)"/>
					  					</td>
					  					<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="itemId"/>"  readonly="readonly"/>
						  				</td>
						  				<td style="display:none">
						  						<input  style="display:none"  type="text"  value="<s:property value="modelCd"/>"  readonly="readonly"/>
						  				</td>
					  				</tr>
	  							</s:iterator>
	  						</table>
	  					</td>
	  				</tr>
	  				<tr style="height: 20px">
	  					<td colspan="6" style="border-bottom:1px solid;"></td>
	  				</tr>
	  				<tr style="font-weight:bold;">
	  					<td colspan="6">
	  						<table id="" border="0px" cellspacing="0px" style="width:668px;margin-left:auto;margin-right:auto;margin-top:2px;font-size:13px">
				  				<tr style="font-weight:bold;">
				  					<td style="width:410px">GRAND TOTAL</td>
				  					<td style="width:200px"><input  type="text" class="colorInput"  id="PalletQuantity" value="<s:property value='palletQuantity'/>"  style="text-align:center;width:150px;height:20px;border:0px solid;font-size:13px;" onblur="palletChange(this)" /></td>
				  					<!-- <td><input  type="text"  id="QuantityP" value=""  style="text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;"  readonly="readonly" /> -->
				  					<td align="right" style="width:150px"><input  type="text"  id="QuantityP" value=""  style="text-align:right;width:40px;height:20px;border:0px solid;font-size:13px;"  readonly="readonly" />
				  					<input  type="text"  id="QuantityPUnit" value="" class="colorInput" style="text-align:right;width:30px;height:20px;border:0px solid;font-size:13px;"   />
				  						<!-- <input  type="text"  id="QuantityPUnit" value="" class="colorInput" style="text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"   /> -->

				  					</td>
				  					<td ><input  type="text"  id="Nw" value=""  style="text-align:right;width:70px;height:20px;border:0px solid;font-size:13px;"  readonly="readonly" />
				  						<!-- <input  type="text"  id="NwUnit" value="Kgs" class="colorInput" style="margin-right:0px;text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"   /> -->
				  					</td>
				  					<td ><input  type="text"  id="Gw" value=""  style="text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;"  readonly="readonly" />
				  						<!-- <input  type="text"  id="GwUnit" value="Kgs" class="colorInput" style="text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"  onblur="DQuantityChange(this)" /> -->
				  					</td>
				  					<td ><input  type="text"  id="M3" value=""  style="text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;"  readonly="readonly" />
				  						<!-- <input  type="text"  id="M3Unit" value="M3" class="colorInput" style="text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"   /> -->
				  					</td>
				  				</tr>
				  				<tr style="font-weight:bold;">
				  					<td style="width:410px" ></td>
				  					<td style="width:200px"></td>
				  					<td style="margin-right:auto;" align="right" >
				  						

				  					</td>
				  					<td  align="right">
				  						<input  type="text"  id="NwUnit" value="Kgs" class="colorInput" style="margin-right:0px;text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"   />
				  					</td>
				  					<td align="right">
				  						<input  type="text"  id="GwUnit" value="Kgs" class="colorInput" style="text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"  onblur="DQuantityChange(this)" />
				  					</td>
				  					<td align="right">
				  						<input  type="text"  id="M3Unit" value="M3" class="colorInput" style="text-align:right;width:60px;height:20px;border:0px solid;font-size:13px;"   />
				  					</td>
				  				</tr>
	  						</table>

	  					</td>
	  				</tr>
	  			</table>
	  			<table border="0px" cellspacing="0px" style="width:644px;margin-left:auto;margin-right:auto; margin-top:40px;font-size:13px">


	  				<tr style="height: 30px;border:1px solid;font-weight:bold;">
	  					<td  style="width: 300px"></td>
	  					<td  align="center">&nbsp;&nbsp;XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
	  				</tr>
	  				<tr style="height: 100px;border:1px solid;font-weight:bold;">
	  					<td ></td>
	  					<td align="center">
	  					<div id="aproveImg">
	  						<img width="60px" height="60px" id="user-sealI1" style="display:none" src="xinan/usersign.action?userId=${session.User.userId}"/>
	  					</div>
	  					<hr style="width:220px; margin-left:90px;" />
	  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MANAGER,OVERSEAS&nbsp;OPERATIONS&nbsp;DEPT.</td>
	  				</tr>
	  			</table>
	  		</div>
	  	</div>
	  	<div id="tabs-3">
  			<div style="height:80px;border:0px solid red;">
	  			<table border="0px" cellspacing="0px" style="margin-left:auto;margin-right:auto;width: 650px;margin-top:40px;">
	  				<tr style="height: 40px ;border:1px solid">
	  					<td rowspan="3" style="border:1px solid;border-top:0px;border-left:0px;border-bottom:0px;font-size:60px;font-style:italic;" align="right">
	  					XAJ&nbsp;
	  					</td>
	  					<td align="center" style="font-weight: bold;font-size:24px;border:1px solid;height:20px;">XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
	  				</tr>
	  				<tr>
	  					<td style="border:1px solid;border-bottom:0px;border-top:0px;font-size:15px;" align="center">
	  						7F,NI&nbsp;BLDG2-61-4&nbsp;IKEBUKURO&nbsp;TOSHIMA&nbsp;KU&nbsp;TOKYO,JAPAN
	  					</td>
	  				</tr>
	  				<tr>
	  					<td style="border:1px solid;border-top:0px;font-size:15px;" align="center">
	  						<a style="font-weight:bold;">TEL:</a>+81-3-6912-7995，&nbsp;<a style="font-weight:bold;">FAX:</a>+81-3-6912-7996
	  					</td>
	  				</tr>
	  			</table>
	  		</div>
		  		<div style="border: 0px solid ; width:650px;height:300px;margin-top:10px;">
		  			<p style="font-weight:bold;font-size:22px;width:100%;"><a style="margin-left: 350px">Date:
		  				<input id="dtoday" value="" type="text"  style="border:0px;font-size:15px;font-weight:bold;" readonly="readonly" />
		  			</a>
		  			</p>
			  			<div  align="CENTER">		<u style="font-size:26px;">Declaration of no-wood packing material</u></div>
			  		<div style="border: 0px solid ;font-size:20px; width:650px;margin-top:2px;" align="LEFT">
			  				<br/><br/>
							To the service of China Entry & Exit Inspection and Quarantine:
							<br/><br/>
							It is declared that shipment Invoce No. <a id="dinvoiceNo"></a>
							<br/><br/>
							dose not contain wood packing materials.
		  			</div>
		  		</div>
		  		<div align="CENTER" style="border: 0px solid ; width:650px;height:160px;margin-top:0px;">
		  				<table border="0px" cellspacing="0px" style="width:500px; margin-top:20px;font-size:18px">
			  				<tr>
			  					<td height="20px" >Commodity</td>
			  					<td >Quantity</td>
			  				</tr>
			  				<tr>
			  					<td height="60px" >
								<textarea id="DCommodity" onblur="changeInvoicename(this);" class="colorInput" style="resize: horizontal;width:240px;border:0px;"></textarea>
								</td>
			  					<td id="DQuantity" style="vertical-align: top;"></td>
			  				</tr>
		  				</table>
		  		</div>
		  		<div>
		  			TOTAL:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="PALLET"></a>
		  		</div>
	  		<div style="border:0px solid red;width:650px;margin-top:0px;">
	  			<table border="0px" cellspacing="0px" style="width:644px;margin-left:auto;margin-right:auto; margin-top:150px;font-size:13px">


	  				<tr style="height: 30px;border:1px solid;font-weight:bold;">
	  					<td  style="width: 300px"></td>
	  					<td  align="center">&nbsp;&nbsp;XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
	  				</tr>
	  				<tr  style="height: 30px;">
	  					<td></td>
	  				</tr>
	  				<tr style="height: 100px;border:1px solid;font-weight:bold;">
	  					<td ></td>
	  					<td align="center">
	  					<div id="aproveImg">
	  						<img width="60px" height="60px" id="user-sealI2" style="display:none" src="xinan/usersign.action?userId=${session.User.userId}"/>
	  					</div>
	  					<hr style="width:220px; margin-left:90px;" />
	  					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MANAGER,OVERSEAS&nbsp;OPERATIONS&nbsp;DEPT.</td>
	  				</tr>
	  			</table>
	  		</div>
 		</div>
 		<input id="customerOrderNo" type="hidden" value="<s:property value='customerOrderNo'/>">
 		<div id="tabs-4" >
				<table border="1px" cellspacing="0px" style="font-size:0.5em;margin-left:auto;margin-right:auto;width: 750px;margin-top:40px;table-layout:fixed;">
	  				<tr style="font-weight:bold;">
	  					<td colspan="7" style="font-weight:bold;" align="center" >
	  						客户订单号:
	  					</td>
	  					<td colspan="9" style="font-weight:bold;" align="left" class="colorInput" >
	  						<input id="EMSCouNo" value="" type="text" style="width:175px;border:0px solid;" class="colorInput"  >
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="left">

	  					</td>
	  					<td colspan="12" style="font-weight:bold;" align="left">
	  						XAJ发货日期：<input id="EMSDay" value="" type="text"  style="width:120px;border:0px;"  readonly="readonly">
	  					</td>
	  				</tr>
	  				<tr style="font-weight:bold;height:40px">
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						No
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						品名
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						规格型号
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						单位
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						数量
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						单价(日元)
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						金额
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						发票号
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						备注
	  					</td>
	  				</tr>
	  			<tbody id="EMStable1">
	  					
	  			</tbody>
	  				<tr style="height:30px">
	  					<td colspan="2" align="center">

	  					</td>
	  					<td colspan="5" align="left">
	  						以下空白
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  				</tr>
	  				<tr style="height:30px">
	  					<td colspan="2" align="center">

	  					</td>
	  					<td colspan="5" align="left">
	  					</td>
	  					<td colspan="5" align="left">
	  						合计
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="right" id="EMSZ">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  				</tr>	
	  			</table>
	  			<table border="1px" cellspacing="0px" style="font-size:0.5em;margin-left:auto;margin-right:auto;width: 750px;margin-top:40px;table-layout:fixed;">
	  				<tr style="font-weight:bold;">
	  					<td colspan="7" style="font-weight:bold;" align="center" >
	  						客户订单号:
	  					</td>
	  					<td colspan="9" style="font-weight:bold;" align="left" class="colorInput">
	  						<input id="EMSCouNo1" value="" type="text" style="width:175px;border:0px solid;"  class="colorInput" >
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="left">

	  					</td>
	  					<td colspan="12" style="font-weight:bold;" align="left">
	  						XAJ发货日期：<input id="EMSDay1" value="" type="text"  style="width:120px;border:0px;"  readonly="readonly">
	  					</td>
	  				</tr>
	  				<tr style="font-weight:bold;height:40px">
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						No
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						品名
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						规格型号
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						单位
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						数量
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						单价(日元)
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						金额
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						发票号
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						备注
	  					</td>
	  				</tr>
	  			<tbody id="EMStable2">
	  					
	  			</tbody>
	  				<tr style="height:30px">
	  					<td colspan="2" align="center">

	  					</td>
	  					<td colspan="5" align="left">
	  						以下空白
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  				</tr>
	  				<tr style="height:30px">
	  					<td colspan="2" align="center">

	  					</td>
	  					<td colspan="5" align="left">
	  					</td>
	  					<td colspan="5" align="left">
	  						合计
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="right" id="EMSZ1">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  				</tr>	
	  			</table>
	  			<table border="1px" cellspacing="0px" style="font-size:0.5em;margin-left:auto;margin-right:auto;width: 750px;margin-top:40px;table-layout:fixed;">
	  				<tr style="font-weight:bold;">
	  					<td colspan="7" style="font-weight:bold;" align="center" >
	  						客户订单号:
	  					</td>
	  					<td colspan="9" style="font-weight:bold;" align="left" class="colorInput">
	  						<input id="EMSCouNo2" value="" type="text" style="width:175px;border:0px solid;"  class="colorInput" >
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="left">

	  					</td>
	  					<td colspan="12" style="font-weight:bold;" align="left">
	  						XAJ发货日期：<input id="EMSDay2" value="" type="text"  style="width:120px;border:0px;"  readonly="readonly">
	  					</td>
	  				</tr>
	  				<tr style="font-weight:bold;height:40px">
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						No
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						品名
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						规格型号
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						单位
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						数量
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						单价(日元)
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						金额
	  					</td>
	  					<td colspan="5" style="font-weight:bold;" align="center">
	  						发票号
	  					</td>
	  					<td colspan="2" style="font-weight:bold;" align="center">
	  						备注
	  					</td>
	  				</tr>
	  			<tbody id="EMStable3">
	  					
	  			</tbody>
	  				<tr style="height:30px">
	  					<td colspan="2" align="center">

	  					</td>
	  					<td colspan="5" align="left">
	  						以下空白
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  				</tr>
	  				<tr style="height:30px">
	  					<td colspan="2" align="center">

	  					</td>
	  					<td colspan="5" align="left">
	  					</td>
	  					<td colspan="5" align="left">
	  						合计
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="5" align="right" id="EMSZ2" >
	  					</td>
	  					<td colspan="5" align="center">
	  					</td>
	  					<td colspan="2" align="center">
	  					</td>
	  				</tr>	
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
	<div class="noprint">
	<div id="floatPanel" >
			<div class="ctrolPanel" style="right:10px;">
				<a class="arrow" href="#"><span>顶部</span></a>

				<a class="contact" >
				<input type="button" value="保存" id="SaveInvoice" style="height:50px;width:80px" onclick="invoiceSAVE();"/>
				</a>
				<br/>
				<a class="contact"   >
					<input type="button" value="印刷"  onclick="print()" style="height:50px;width:80px"/>
				</a>
				<br/>
				<a class="arrow" href="#"><span>底部</span></a>

			</div>
	</div>
	</div>
</body>

<script type="text/javascript" >
	$('.ssinput1').autosize({
		append: "\n"
	});
	$('.ssinput').autosize({
		append: "\n"
	});
</script>
</html>
