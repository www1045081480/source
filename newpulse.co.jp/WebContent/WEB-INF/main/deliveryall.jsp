<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/delivery.js?${initParam.version}"></script>
<script type="text/javascript" src="js/DatePicker.js?${initParam.version}"></script>
<script type="text/javascript" src="js/deliveryall.js?${initParam.version}"></script>
<script type="text/javascript" src="js/NextDeliveryall.js?${initParam.version}"></script>
<script type="text/javascript" src="js/beforeAfter.js?${initParam.version}"></script>
<script type="text/javascript" src="js/invoice.js?${initParam.version}"></script>
<script type="text/javascript" src="js/invoiceall.js?${initParam.version}"></script>
<script type="text/javascript" src="js/paymoney.js?${initParam.version}"></script>
<script type="text/javascript" src="js/receivemoney.js?${initParam.version}"></script>

<style type="text/css">
	#div2{
			border: 0px solid ;
			border-left:0px;
			border-right:0px;

			width : 1450px;
			background-color:white;
			margin-left:auto;
			margin-right:auto;
        }
    #div1{
			background-color:white;
			margin-left:auto;
			margin-right:auto;
			border:0px solid;
        }
   #table1 tr td{
        	border-bottom:1px solid;
        	border-left:1px solid;
        }
   #userbody {
			PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BACKGROUND: ;PADDING-TOP: 0px;
			background:#e7f7fa;
		}
	#floatPanel .ctrolPanel{
			width:100px;
			height:356px;
			background: no-repeat left top;
			border:solid 0px #ddd;
			position:fixed;
			right:500px;
			top:100px;
			z-index:10000;
			}
	#floatPanel .ctrolPanel a{
			width:54px;
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
			width:120px;
			display:block;
			}
	#table1 tr{
			height:35px;
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
	#panel,#panel1{
			padding:50px;
			display:none;
			border:solid 1px #c3c3c3;
		}
	.aclass{
			background-color:#8DF8F2;
	}
</style>
</head>
<body onload="deliveryallLoad();">
	<table class="stable" id="wrapper" border="0">
		<tbody>
			<tr>
				<%@ include file="header.jsp" %>
			<tr>
				<td id="container" style="background: #d2e9ff;">
					<form id="focus" class="sform" style="margin-left: auto; margin-right: auto;">
					<div class="box">
<div id="print">
  		<div  id="div1" >
		  		<div id="flip" ><a id="year"></a>年<a id="month"></a>月当月配送一覧<br/><br/>
		  			<div  id="flip1">
		  				<!-- 販売先<input id="customerName" value="" type="text" style="width:200px;"    onBlur="keydownMsg(event)" onkeydown="changeName(event)" />
		  				販売先注文番号<input id="customerOrder" value="" type="text" style="width:70px"   />
		  				<input  value="検索" type="button" style="width:70px" onclick="deliveryFind()"  /> -->
		  			</div>
		  		</div>
		  		<input id="customerName1" type="hidden" value="<s:property value="customerName"/>">
		  		<input id="customerOrder1" type="hidden" value="<s:property value="customerOrder"/>">
		  		<input type="hidden" id="Currency"  value="">
		  		<input type="hidden" id="orderId"  value="">
		  		<table border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;margin-top:0px;">
		  			<thead>
		  			<tr>
		  				<td width="180px" align="center">販売先</td>
		  				<td width="100px" align="center">販売先注文番号</td>
						<td width="70px" align="center">品名</td>
						<td width="70px" align="center" style="display:none">品番</td>
						<td width="60px" align="center">規格</td>
						<td width="30px" align="center">数量</td>
						<td width="80px" align="center">販売金額</td>
		  				<td width="100px" align="center">注文書番号</td>
						<td width="180px" align="center">仕入先</td>
						<td width="60px" align="center">仕入納期</td>
						<td width="60px" align="center">変更納期</td>
						<td width="50px" align="center">選択</td>
		  			</tr>
		  			</thead>
		  			<tbody id="ctbody">
					<s:iterator value="deliveryInfo">
						<tr  align="center">
							<td><s:property value="customerName"/></td>
							<td><s:property value="customerOrderNo"/></td>
							<td><s:property value="itemName"/></td>
							<td style="display:none"><s:property value="modelCd"/></td>
							<td><s:property value="partsCd"/></td>
							<td><s:property value="quantity"/></td>
							<td><s:property value="amount"/></td>
							<td><s:property value="orderCd"/></td>
							<td><s:property value="supplierName"/></td>
							<td><input  value="<s:property value='deliveryDate'/>" type="text" onfocus="setday123(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="text-align:center;border:0px; width:85px;"  readonly="readonly" /></td>
							<td class="aclass"><input class="aclass" value="" type="text" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="text-align:center;border:0px; width:85px;"  readonly="readonly" /></td>
							<%-- <s:property value='newDeliveryDate'/> --%>
							<td><input  type="checkbox"  name="deliveryall"  /></td>
							<td style="display:none"><s:property value="detailId"/></td>
							<td style="display:none"><s:property value="itemId"/></td>
							<td style="display:none"><s:property value="orderId"/></td>
							<td style="display:none"><s:property value="customerId"/></td>
							<td style="display:none"><s:property value="currency"/></td>
							

						</tr>
					</s:iterator>
					</tbody>
		  		</table>
				<p style="height:10px"></p>
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
	<div id="floatPanel" >
	  		<div class="ctrolPanel" style="right:10px;">

				<a class="contact" >
				<input type="button" value="invoice作成" style="height:50px;width:80px" onclick="invoiceCreate()"/>
				</a>
				<a class="contact" >
				<input type="button" value="変更納期" style="height:50px;width:80px" onclick="naqiChange(1)"/>
				</a>
				<a class="contact" >
				<input type="button" value="納品完了" style="height:50px;width:80px" onclick="napinFinsh(1)"/>
				</a>
				<a class="contact"   >
				<input type="button" value="印刷" onclick="$('#print').print()" style="height:50px;width:80px"/>
				</a>

			</div>
		</div>
</body>
</html>