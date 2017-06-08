<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/orderall.js?${initParam.version}"></script>
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
</style>
</head>
<body onload="onLoad(${categoryType});">
	<table class="stable" id="wrapper" border="0">
		<tbody>
			<tr>
				<%@ include file="header.jsp" %>
			<tr>
				<td id="container" style="background: #d2e9ff;">
					<form id="focus" class="sform" style=" margin-left: auto; margin-right: auto;">
					<div class="box">
<div id="print">
  		<div  id="div1" >
		  		<div id="flip" >注文実績一覧<br/><br/>
		  			<div  id="flip1">
		  				<div  id="flip1">
		  				番号<input id="orderCd" value="${orderCd}" type="text" style="width:70px" onfocus=""  />
		  				仕入先<input id="supplierName" value="${supplierName}" type="text" style="width:100px" onblur="onBlur(this);"  />
		  				種別<select id="categoryType" value="${categoryType}">
		  					<option value="">--</option>
		  					<option value="1">材料</option>
		  					<option value="2">生产设备</option>
		  					<option value="3">检查设备</option>
		  					<option value="4">其他</option>
		  				</select>
		  				期間&nbsp;<input id="From" value="${dateFrom}" type="text" style="width:70px" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  					至
		  					<input id="To" value="${dateTo}" type="text" style="width:70px" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  				<input  value="検索" type="button" style="width:70px" onclick="search()"  />
		  				<input id="resetBt" value="クリア" onclick="reset123();" type="button" style="width:70px"/>
		  			</div>
		  			</div>
		  		</div>
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;">
		  			<thead>
		  			<tr height="20px">
		  				<td width="10%" align="center">注文書番号</td>
		  				<td width="10%" align="center">作成日付</td>
						<td width="20%" align="center">仕入先名</td>
						<td width="10%" align="center">品名/数量</td>
						<td width="5%" align="center">商品種別</td>
						<td width="10%" align="center">合計金額</td>
		  				<td width="10%" align="center">納期</td>
						<td width="10%" align="center">支払</td>
						<td width="10%" align="center">進捗</td>
						<td width="5%" align="center">操作</td>
		  			</tr>
		  			</thead>
		  			<tbody id="ctbody">
					<s:iterator value="OrderResult">
						<tr  align="center">
							<td style = "display:none"><s:property value="orderId"/></td>
							<td width="10%" align="left"><s:property value="orderCd"/></td>
							<td width="10%" align="center"><s:property value="issueDate"/></td>
							<td width="20%" align="left"><s:property value="supplierName"/></td>
							<td width="10%" align="center"><s:property value="modelCd"/></td>
							<td width="5%" align="center"><s:if test="categoryType==1">材料</s:if> 
								<s:if test="categoryType==2">生产设备</s:if> 
								<s:if test="categoryType==3">检查设备</s:if> 
								<s:if test="categoryType==4">其他</s:if></td>
							<td width="5%" align="right"><s:property value="amount"/></td>
							<td width="10%" align="center"><s:property value="deliveryDate"/></td>
							<td width="10%" align="center"><s:property value="payment"/></td>
							<td width="10%" align="center"><s:property value="status"/></td>
							<td width="5%">
							<input type='button' value='詳細' style='text-align:center;width:35px;height:25px' onclick = "readOneOrder(this);">
							</td>
							<td style = "display:none"><s:property value="langFlg"/></td>
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
				<a class="contact"   >
					<input type="button" value="印刷" onclick="$('#print').print()" style="height:50px;width:80px"/>
				</a>
				<a class="contact"   >
					<input type="button" value="Excel出力" onclick="download()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
		</div>
</body>
</html>