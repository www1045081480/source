<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/quotenone.js?${initParam.version}"></script>

<style type="text/css">
.flip {
	padding: 20px;
	text-align: center;
	background-color: #e5eecc;
	border: solid 1px #c3c3c3;
	font-weight: bold;
	font-size: 18px;
}
.flip1 {
	text-align: left;
	font-weight: bold;
	font-size: 12px;
}
#flip2 {
	text-align: left;
	font-weight: bold;
	font-size: 12px;
}
#panel,#panel1 {
	padding: 50px;
	border: solid 1px #c3c3c3;
}
</style>
<body>

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
	  		<div  id="div1" >
			  	<div class="flip" >未承認見積一覧<br/><br/>
			  	</div>
				  		<table id="estimationTable" border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;margin-top:0px;">
				  			<tr style="height:20px">
				  				<td width="5%" align="center"><input id="checkallEs" type="checkbox" onclick="checkAllEstimation();" /></td>
				  				<td width="10%" align="center">見積書番号</td>
				  				<td width="10%" align="center">作成日付</td>
				  				<td width="15%" align="center">得意先名</td>
				  				<td width="10%" align="center">品名</td>
				  				<td width="5%" align="center">数量</td>
				  				<td width="10%" align="center">合計金額</td>
				  				<td width="10%" align="center">納期</td>
				  				<td width="10%" align="center">承認状態</td>
				  				<td width="20%"  align="center">操作</td>
				  			</tr >
								<s:iterator  value="noApproveEstimation">
									<tr  align="center" style="height:20px">
										<td style = "display:none"><s:property value="estimationId"/></td>
										<td width="5%" align="center"><input  type="checkbox"  name="quoten" onclick= "canChAll4Es(this);" /></td>
										<td width="10%"  align="left"><s:property value="estimationCd"/></td>
										<td width="10%" align="center"><s:property value="issueDate"/></td>
										<td width="15%"  align="center"><s:property value="customerName"/></td>
										<td width="10%"  align="center"><s:property value="name"/></td>
										<td width="5%"  align="center"><s:property value="quantity"/></td>
										<td width="10%"  align="right"><s:property value="amount"/></td>
										<td width="10%"  align="center"><s:property value="arriveDate"/></td>
										<td width="10%"  align="center"><s:property value="approvedStates"/></td>
										<td width="15%"  align="center"><s:property value=""/>
										<input type='button' value='詳細' style='text-align:center;width:35px;height:25px' onclick = "readOneEstimation(this);">
										<input type='button' value='承认' style='text-align:center;width:35px;height:25px' onclick = "approveOneEstimation(this,${session.User.role})">
										<input type='button' value='删除' style='text-align:center;width:35px;height:25px' onclick = "deleOneEstimation(this);">
										</td>
										<td  align="center" style = "display:none"><s:property value="langFlg"/></td>
										
									</tr>
								</s:iterator> 
				  		</table>
				  			    &nbsp;
					  			<input style="width:50px;font-size:0.6em;algin-left:40px" type="button" value="选中承认" onClick="getSelectedEs2approve(${session.User.role});"/>&nbsp;&nbsp;
					  			<input style="width:50px;font-size:0.6em;"  type="button" value="选中删除" onClick="getSelectedEs2cancel();"/>
			  	<div class="flip" >未承認注文一覧<br/><br/>
			  	</div>
				  		<table id="orderTable" border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;margin-top:0px;">
				  			<tr>
				  				<td width="5%" align="center"><input id="checkallOd" type="checkbox" onclick="checkAllOrder();" /></td>
				  				<td width="10%" align="center">注文書番号</td>
				  				<td width="10%" align="center">作成日付</td>
				  				<td width="15%" align="center">仕入先名</td>
				  				<td width="10%" align="center">品名</td>
				  				<td width="5%" align="center">数量</td>
				  				<td width="10%" align="center">合計金額</td>
				  				<td width="10%" align="center">納期</td>
				  			<!--  
					  			<td width="50px" align="center">支払</td>
				  			-->	
				  				<td width="10%" align="center">承認状態</td> 
				  				<td width="15%" align="center">操作</td>
				  			</tr>
				  			<s:iterator  value="noApproveOrder">
									<tr  align="center">
										<td style = "display:none"><s:property value="orderId"/></td>
										<td width="5%"><input  type="checkbox"  name="quoten"  onClick="canChAll4Od(this)"/></td>
										<td width="10%" align="center"><s:property value="orderCd"/></td>
										<td width="10%" align="center"><s:property value="issueDate"/></td>
										<td width="15%" align="center"><s:property value="supplierName"/></td>
										<td width="10%" align="center"><s:property value="name"/></td>
										<td width="5%" align="center"><s:property value="quantity"/></td>
										<td width="10%" align="right"><s:property value="amount"/></td>
										<td width="10%" align="center"><s:property value="arriveDate"/></td>
									<!-- 
										 <td><s:property value="patment"/></td>
									 -->		
										<td><s:property value="approvedStates"/></td>
										<td width="15%">
										<input type='button' value='詳細' style='text-align:center;width:35px;height:25px' onclick = "readOneOrder(this);">
										<input type='button' value='承认' style='text-align:center;width:35px;height:25px' onclick = "approveOneOrder(this,${session.User.role});">
										<input type='button' value='删除' style='text-align:center;width:35px;height:25px' onclick = "deleOneOrder(this);">
										</td>
										<td style = "display:none"><s:property value="langFlg"/></td>
									</tr>
								</s:iterator> 
				  		</table>
				  		&nbsp;
				  		<input style="width:50px;font-size:0.6em;algin-left:40px" type="button" value="选中承认" onClick="getSelectedOd2approve(${session.User.role});"/>&nbsp;&nbsp;
					    <input style="width:50px;font-size:0.6em;"  type="button" value="选中删除" onClick="getSelectedOd2cancel();"/>
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
				<a class="contact">
					<input type="button" value="印刷" onclick="$('#print').print()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
		</div>
</body>
</html>