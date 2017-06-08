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
<body onload="allLoad()">
	<table class="stable" id="wrapper" border="0">
		<tbody>
			<tr>
				<%@ include file="header.jsp" %>
			<tr>
				<td id="container" style="background: #d2e9ff;">
					<form id="focus" style=" width: 850px; margin-left: auto; margin-right: auto;">
					<div class="box">
<div id="print">
  		<div  id="div1" >
  				<input id="invoiceCD" type="hidden" value="<s:property value="invoiceCD"/>">
  				 <input id="customerID" type="hidden" value="<s:property value="customerID"/>">
  				<input id="InForm" type="hidden" value="<s:property value="InForm"/>">
  				<input id="InTo" type="hidden" value="<s:property value="InTo"/>">
  				<input id="roleID" type="hidden" value="<s:property value="roleID"/>">

		  		<div id="flip" >INVOICE配送実績一覧<br/><br/>
		  			<div  id="flip1">
		  				INVOICE番号<input id="INVOICECD" value="" type="text" style="width:100px" onfocus=""  />
		  				 販売先<input id="customerIDNO" value="${customerIDNO}" type="text" style="width:110px" onBlur="findCoustemID(this);" onkeydown="keydownCoustemID(event);" /> 
		  				<!-- 販売先注文番号<input id="customerno" value="" type="text" style="width:70px" onfocus=""  /> -->
		  				期間&nbsp;<input id="inform" value="" type="text" style="width:70px" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  					至
		  					<input id="into" value="" type="text" style="width:70px" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  				<input id="Form" value="検索" type="button" style="width:70px" onclick="invoiceallFind()"  />
						<input  value="クリア" type="button" style="width:70px" onclick="cleanIN()"  />
		  			</div>
		  		</div>

		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;margin-top:0px;">
		  			<thead>
		  			<tr>
		  				<td width="100px" align="center">INVOICE番号</td>
		  				<td width="160px" align="center">販売先</td>
						<td width="100px" align="center">販売先注文番号</td>
						<td width="100px" align="center">INVOICE作成日</td>
						<td width="160px" align="center">品名</td>
						<td width="80px" align="center">規格</td>
						<td width="30px" align="center">数量</td>
						<td width="100px" align="center">販売金額</td>
		  				<td style="display:none" width="90px" align="center">仕入納期</td>
						<td width="60px" align="center">貿易条件</td>
						<td width="50px" align="center">運賃諸経費</td>
						<td width="40px" align="center">操作</td>
		  			</tr>
		  			</thead>
		  			<tbody id="ctbody">
					<s:iterator value="deliveryResult">
						<tr  align="center">
							<td><s:property value="invoiceCd"/></td>
							<td><s:property value="customerName"/></td>
							<td><s:property value="orderCd"/></td>
							<td><s:property value="issueDate"/></td>
							<td><s:property value="itemName"/></td>
							<td><s:property value="PartsCd"/></td>
							<td><s:property value="quantity"/></td>
							<td align="right"><s:property value="amount"/></td>
							<td style="display:none"><s:property value="arrivalDate"/></td>
							<td><s:property value="deliveryType"/></td>
							<td class="aclass"><input id="carringAmount" class="aclass" value="<s:property value="carringAmount"/>" type="text" style="width:50px;border:0px;text-align:right" /></td>
							<td style="display:none"><s:property value="invoiceId"/></td>
							<td style="display:none"><s:property value="estimationCd"/></td>
							<td style="display:none"><s:property value="deliveryType"/></td>
							<td style="display:none"><s:property value="shippingCompany"/></td>
							<td style="display:none"><s:property value="packingNumber"/></td>
							<td >
							</td>
							<td style="display:none"><s:property value="approvedId"/></td>
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
				<a class="arrow" href="#"><span>顶部</span></a>

				<a class="contact" >
				<input type="button" value="保存" style="height:50px;width:80px" onclick="invoiceallSave()"/>
				</a>
				<a class="contact"   >
					<input type="button" value="Excel出力" onclick="downloadInvoice()" style="height:50px;width:80px"/>
				</a>
				<a class="contact"   >
					<input type="button" value="印刷" onclick="amountSum()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
		</div>
</body>
</html>