<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="comheaderj.jsp"%>
<script type="text/javascript" src="js/quoteall.js?${initParam.version}"></script>
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
		  		<div id="flip" >見積実績一覧<br/><br/>
		  		<div  id="flip1">
		  				番号<input id="estimationCd" value="${estimationCd}" type="text" style="width:70px" onfocus=""  />	
		  				 得意先<input id="customerName" value="${customerName}" type="text" style="width:100px" onblur="onBlur(this);"  />
		  				種別<select id="categoryType">
		  					<option value="">--</option>
		  					<option value="1">材料</option>
		  					<option value="2">生产设备</option>
		  					<option value="3">检查设备</option>
		  					<option value="4">其他</option>
		  				</select>
		  				期間&nbsp;<input id="From" value="${dateFrom}" type="text" style="width:70px" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  					至
		  					<input id="To" value="${dateTo}" type="text" style="width:70px" onfocus="setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" readonly="readonly" />
		  				<input  value="検索" type="button" style="width:70px" onclick="search()()"  />	
		  				<input id="resetBt" value="クリア" onclick="reset123();" type="button" style="width:70px"/>	
		  			</div>
		  		</div>
		  		<table  border="1px　solid" cellspacing="0px" style="font-size:0.6em;margin-left:auto;margin-right:auto;width: 830px;margin-top:0px;">
		  			<thead>
		  			<tr height="20px">
		  				<td width="10%" align="center">見積書番号</td>
		  				<td width="10%" align="center">作成日付</td>
						<td width="15%" align="center">得意先名</td>
						<td width="10%" align="center">品名/数量</td>
						<td width="5%" align="center">商品種別</td>
						<td width="10%" align="center">合計金額</td>
		  				<td width="10%" align="center">納期</td>						
		  				<td width="10%" align="center">支払</td>						
						<td width="10%" align="center">進捗</td>
						<td width="10%" align="center">操作</td>
		  			</tr>
		  			</thead>
		  			<tbody id="ctbody">
					<s:iterator value="estimationResult">
						<tr  align="center" height="20px">
							<td style = "display:none"><s:property value="estimationId"/></td>
							<td width="10%" align="left"><s:property value="estimationCd"/></td>
							<td width="10%" align="center"><s:property value="issueDate"/></td>
							<td width="15%" align="left"><s:property value="customerName"/></td>
							<td width="10%" align="left"><s:property value="modelCd"/></td>
							<td width="5%" align="center">
								<s:if test="categoryType==1">材料</s:if> 
								<s:if test="categoryType==2">生产设备</s:if> 
								<s:if test="categoryType==3">检查设备</s:if> 
								<s:if test="categoryType==4">其他</s:if>
							</td>
							<td width="10%" align="right"><s:property value="amount"/></td>
							<td width="10%" align="center"><s:property value="estimationOkDays"/></td>
							<td width="10%" align="left"><s:property value="payment"/></td>
							<td width="10%" align="left"><s:property value="status"/></td>
							<td width="10%">
							<input type='button' value='詳細' style='text-align:center;width:35px;height:25px' onclick = "readOneEstimation(this);">
							<input type='button' value='COPY' style='text-align:center;width:35px;height:25px' onclick = "copyOneEstimation(this)">
							 </td>
							<td style = "display:none"><s:property value="langFlg"/></td>
						</tr>
					</s:iterator> 
					</tbody>
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