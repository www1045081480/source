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
<script type="text/javascript" src="js/receivemoney.js?${initParam.version}"></script>
<script type="text/javascript" src="js/beforeAfter.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>

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
	.ainput{
			border:0px solid;
			border-bottom:1px solid;
			min-width:50px;
			max-width:50px;
			background-color:#CDFFFF;
			text-align:right;
	}
	.binput{
			border:0px solid;
			width:70px;
			background-color:#CDFFFF;
			text-align:right;
	}
	.cinput{
			border:0px solid;
			width:60px;
			background-color:#CDFFFF;
			text-align:right;
	}
	.dinput{
			border:0px solid;
			width:50px;
			background-color:#CDFFFF;
			text-align:right;
	}
	.aclass{
			background-color:#CDFFFF;

	}
	.atbody{
			font-size:1em;
	}
	.atextarea{
			border:0px solid;
			height:35px;
			width:70px;
			overflow-y:hidden;
			resize: none;
			overflow-x:hidden;

	}
	.JP{
			width:50px;
			text-align:center;
	}
	.US{
			width:50px;
			text-align:center;
	}
	.CN{
			width:50px;
			text-align:center;
	}
	.JPborder{
			height:20px;
			background-color:#F0FFF0;
	}
	.USborder{
			height:20px;
			background-color:#EEEE00;
	}
	.CNborder{
			height:20px;
			background-color:#EEE0E5;
	}
</style>
</head>
<body onload="beforeAfterStar();">
	<input id="beforeafteryear" type="hidden" value="<s:property value='year'/>">
	<input id="beforeaftermm" type="hidden" value="<s:property value='month'/>">
	<table width="970" id="wrapper" border="0">
		<tbody>
			<tr>
				<%@ include file="header.jsp" %>
			<tr>
				<td id="container" style="background: #d2e9ff;">
					<form id="focus" style=" width: 850px; margin-left: auto; margin-right: auto;">
					<div class="box">
<div id="print">
  		<div  id="div1" >
		  		<div id="flip" ><a id="tYear"></a>年<a id="tMouth"></a>月月度販売仕入取引対照表<br/>
		  			<div  id="flip1">
		  				<select id="bayear"></select>年<select id="bamon"></select>月
		  				<input  value="検索" type="button" style="width:70px" onclick="beforeAfterFind()"  />
		  			</div>
		  		</div>
<div>
<div style=" height: 600px; overflow: scroll;">
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 1650px;margin-top:0px;">
		  			<thead>
			  			<tr style="height: 15px;font-weight:bold">
			  				<td width="24px"  rowspan="2" align="center" >順番</td>
			  				<td width="44px"  rowspan="2" align="center">客先</td>
			  				<td colspan="13" align="center">仕入取引</td>
			  				<td width="4px"  align="center" rowspan="8" style="border-top:0px solid;border-bottom:0px solid;"></td>
			  				<td width="54px" colspan="9" align="center">販売取引</td>
		  				</tr>
			  			<tr style="height: 15px;font-weight:bold">
			  				<td width="80px" align="center">発注先</td>
			  				<td width="80px" align="center">注文No</td>
			  				<td width="80px"  align="center">品名/規格</td>
			  				<td width="80px"  align="center">新安発注日付</td>
			  				<td width="60px"  align="center">発注数量</td>
			  				<td width="80px" align="center">仕入金額</td>
			  				<td width="50px" align="center">調整金額</td>
			  				<td width="50px"  align="center">消費税</td>
			  				<td width="50px"  align="center">送料</td>
			  				<td width="80px"  align="center">合計</td>
			  				<td width="50px"  align="center">支払金額</td>
			  				<td width="50px"  align="center">支払日</td>
			  				<td width="50px"  align="center">納入日</td>
			  				<td width="60px"  align="center">客先</td>
			  				<td width="100px"  align="center">インボイスNO.</td>
							<td width="65px"  align="center">取引NO.</td>
			  				<td width="90px"  align="center">売上日付</td>
			  				<td width="100px"  align="center">売上金額</td>
			  				<td width="100px"  align="center">消費税</td>
			  				<td width="70px"  align="center">粗利益率</td>
			  				<td width="100px"  align="center">粗利</td>
			  				<td width="120px"  align="center">入金日＆金額</td>
			  			</tr>
		  			</thead>
		  			<tbody class="atbody" id="Ctable">
					<s:iterator value="sellOrderInfo" status="L">
						<tr  align="center">
							<td width="24px"><s:property value="#L.index+1"/></td>
							<td width="44px"><s:property value="customerName"/></td>
							<td width="80px"><s:property value="supplierName"/></td>
							<td width="44px"><s:property value="orderCd"/></td>
							<td width="80px"><s:property value="modeCd"/></td>
							<td width="80px"><s:property value="xinanOrderDate"/></td>
							<td width="60px"><s:property value="orderQuantity"/></td>
							<td width="80px" align="right"><s:property value="orderAmount"/></td>
							<td width="50px" class="aclass"><input class="dinput" value = "<s:property value="adjustAmount" />"  type="text" /></td>
							<td width="50px"><s:property value="orderExcise"/></td>
							<td width="50px" class="aclass" align="right"><input class="dinput" value = "<s:property value="deliveryAmount"/>"   type="text" /></td>
							<td  width="80px" class="amount" align="right"><s:property value="requireAmount"/></td>
							<td  style='vertical-align: top;' width="50px" class="aclass">
								<textarea class="ainput"  style="background-color:#CDFFFF;height:10px;border:0px solid;" onblur=""><s:property value="payAmounts"/></textarea>
							</td>
							<td   style='vertical-align: top;' width="50px" class="aclass">
								<textarea class="ainput"  style="background-color:#CDFFFF;height:10px;border:0px solid;" onblur=""><s:property value="payDates"/></textarea>
							</td>
							<td  width="50px" class="aclass">
								<input class="cinput" value = "<s:property value="deliveryDate"/>" type="text" />
							</td>
							<td width="60px" style="border-top:0px;border-bottom:0px"></td>
							<td width="60px"><s:property value="customerName"/></td>
							<td width="100px"><s:property value="invoiceCd"/></td>
							<td width="65px" class="aclass">
								<input class="cinput" value = "<s:property value="tradingNo"/>" type="text"/>
							</td>
							<td width="90px"><s:property value="sellDate"/></td>
							<td width="100px" align="right"><s:property value="sellAmount"/></td>
							<td width="100px" align="right"><s:property value="sellExcise"/></td>
							<td width="70px" align="right"><s:property value="grossMargin"/></td>
							<td width="100px" align="right"><s:property value="grossProfit"/></td>
							<td width="120px"><s:property value="receivedDate"/>＆<s:property value="receivedAmount"/></td>
							<td style="display:none"><s:property value="orderDetailId"/></td>
							<td style="display:none"><s:property value="invoiceDetailId"/></td>
							<td style="display:none"><s:property value="estimationDetailId"/></td>
							<td style="display:none"><s:property value="orderId"/></td>
							<td style="display:none"><s:property value="invoiceId"/></td>
							<td style="display:none"><s:property value="estimationId"/></td>
							<td style="display:none"><s:property value="supplierId"/></td>
							<td style="display:none"><s:property value="currency"/></td>
						</tr>
				</s:iterator>
					<tr class="JPborder">
						<td colspan="10" class=""></td>
						<td class="JP">日元</td>
						<td id="payAmountJP" align="right">${sell.japanese.requireAmount}</td>
						<td colspan="8" class=""></td>
						<td id="recAmountJP" align="right">${sell.japanese.sellAmount}</td>
						<td id="exciseJP" align="right">${sell.japanese.sellExcise}</td>
						<td id="grossMarginJP" align="right">${sell.japanese.grossMargin}</td>
						<td id="grossProfitJP" align="right">${sell.japanese.grossProfit}</td>
						<td class="borderNone"></td>
					</tr>
					<tr  class="USborder">
						<td colspan="10" class=""></td>
						<td class="JP">美元</td>
						<td id="payAmountUS" align="right">${sell.doller.requireAmount}</td>
						<td colspan="8" class=""></td>
						<td id="recAmountUS" align="right">${sell.doller.sellAmount}</td>
						<td id="exciseUS" align="right">${sell.doller.sellExcise}</td>
						<td id="grossMarginUS" align="right">${sell.doller.grossMargin}</td>
						<td id="grossProfitUS" align="right">${sell.doller.grossProfit}</td>
						<td class="borderNone"></td>
					</tr>
					<tr  class="CNborder">
						<td colspan="10" class=""></td>
						<td class="JP">人民币</td>
						<td id="payAmountCN" align="right">${sell.chinese.requireAmount}</td>
						<td colspan="8" class=""></td>
						<td id="recAmountCN" align="right">${sell.chinese.sellAmount}</td>
						<td id="exciseCN" align="right">${sell.chinese.sellExcise}</td>
						<td id="grossMarginCN" align="right">${sell.chinese.grossMargin}</td>
						<td id="grossProfitCN" align="right">${sell.chinese.grossProfit}</td>
						<td class="borderNone"></td>
					</tr>
					</tbody>
		  		</table>

				<p style="height:10px"></p>
				</div>
				</div>
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
					<input type="button" value="保存" onclick="beforeAfterSave(${session.User.role})" style="height:50px;width:80px"/>
				</a>
				<a class="contact"   >
					<input type="button" value="Excel出力" onclick="downloadTrading()" style="height:50px;width:80px"/>
				</a>
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
$(".dinput").blur(function(){
	 var tag = this.parentNode.parentNode.getElementsByTagName('td')[32].innerHTML;
	 if(tag=="JP"){
		 $(this).parseNumber({format:"#,###", locale:"jp"});
		 $(this).formatNumber({format:"#,###", locale:"jp"});
	 }else{
		 if(tag == "US"){
			 $(this).parseNumber({format:"#,###.00", locale:"us"});
		 	 $(this).formatNumber({format:"#,###.00", locale:"us"});
		 }else{
			 $(this).parseNumber({format:"#,###.00", locale:"cha"});
			 $(this).formatNumber({format:"#,###.00", locale:"cha"});
		 }
	 }
	 amountCount(this);
	});
$('.ainput').autosize({
	append: "\n"
});
</script>
</html>