<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/quoteorder.js?${initParam.version}"></script>

<style type="text/css">
#flip {
	padding: 20px;
	text-align: center;
	background-color: #e5eecc;
	border: solid 1px #c3c3c3;
	font-weight: bold;
	font-size: 18px;
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
		  		<div id="flip" >未注文一覧
		  		</br>
		  		</br>
		  			<div  id="flip1">受注日検索：
		  			<input id="Form" value="${Form}" type="text" style="width:70px" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  			至
		  			<input id="To" value="${To}" type="text" style="width:70px" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  			<input  value="検索" type="button" style="width:70px" onclick="search()"  />	
		  			<input  value="重置" type="button" style="width:70px" onclick="resetForm()"  />	
		  			</div>
		  		</div>
		  		
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;margin-top:0px;">
		  			<tr>
		  				<td style = "display:none"></td>
		  				<td style = "display:none"></td>
		  				<td width="40px"><input id="checkall" type="checkbox" onclick="checkAll()" />全选</td>
		  				<td width="70px" align="center">見積書番号</td>
		  				<td width="150px" align="center">販売先</td>
						<td width="50px" align="center">受注日</td>
						<td width="80px" align="center">納期</td>
						<td width="70px" align="center">品名</td>
						<td width="30px" align="center">数量</td>
						<td width="60px" align="center">規格</td>
						<td width="50px" align="center">販売金額</td>
		  				
		  			</tr>
		  			<tbody id="noOrder">
					<s:iterator  value="noOrderEstimation">
						<tr  align="center">
							<td style = "display:none"><s:property value="estimationId"/></td>
							<td style = "display:none"><s:property value="itemId"/></td>
							<td><input  type="checkbox"  name="quoten"  /></td>
							<td><s:property value="estimationCd"/></td>
							<td><s:property value="sellerName"/></td>
							<td><s:property value="orderDate"/></td>
							<td><s:property value="acceptDate"/></td>
							<td><s:property value="itemName"/></td>
							<td><s:property value="quantity"/></td>
							<td><s:property value="partCd"/></td>
							<td><s:property value="amount"/></td>
							<td style = "display:none"><s:property value="currency"/></td>
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
				
				<br/>
				<a class="contact" >
				<input type="button" value="注文書作成" style="height:50px;width:80px" onclick="order2make()"/>
				</a>
				<br/>
				<a class="contact"   >
					<input type="button" value="印刷" onclick="$('#print').print()" style="height:50px;width:80px"/>
				</a>
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
		</div>
</body>
</html>