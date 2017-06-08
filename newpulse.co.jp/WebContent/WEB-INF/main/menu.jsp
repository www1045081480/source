<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="X-UA-Compatible">
<title>新安商事業務管理システム</title>
<link rel="stylesheet" type="text/css" href="css/content.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/default.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/stylea.css?${initParam.version}">

<script src="js/jquery.min.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery.marquee.min.js?${initParam.version}"></script>
<script type="text/javascript" src="js/CheckRole.js?${initParam.version}"></script>
<script type="text/javascript">
//--------------ユーザ管理画面权限----------------
function user(){
	//权限check
	//if(!checkRole(8)){
		//return;
	//};
	var url = "xinan/user.action";
	location.href = url;
}
//-------------見積書作成画面权限--------------------
function quoteload(){
	//权限check
	var Role = document.getElementById("checkRole").value;

	if(Role==1){
		if(!checkRole(1)){
		return;
		};
	};
	if(Role==7){
		if(!checkRole(7)){
		return;
		};
	};
	var url = "xinan/quoteload.action?langflg=CN";
	location.href = url;
}
//------------- 注文書作成画面权限--------------------
function noOrderEstimation(){
	//权限check
	var Role = document.getElementById("checkRole").value;

	if(Role==1){
		if(!checkRole(1)){
		return;
		};
	};
	if(Role==7){
		if(!checkRole(7)){
		return;
		};
	};
	var url = "xinan/noOrderEstimation.action";
	location.href = url;
}
//--------------見積依頼書作成画面权限----------------
function quotemake(){
	//权限check
	var Role = document.getElementById("checkRole").value;

	if(Role==1){
		if(!checkRole(1)){
		return;
		};
	};
	if(Role==7){
		if(!checkRole(7)){
		return;
		};
	};
	var url = "xinan/quotemake.action";
	location.href = url;
}
//------------当月配送一览--------------
function delivery(){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	if(month<10){
		month = "0"+month;
	}
	var date = ""+year+ month;
	//alert(date);
	var url = "xinan/deliveryallLoad.action?date="+date+"&now="+(new Date()).valueOf();
	location.href = url;
}
//------------翌月以後配送一覧--------------
function nextdelivery(){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 2;
	if(month == 13 ){
		year = year + 1;
		month = 1;
	}
	if(month<10){
		month = "0"+month;
	}
	var date = ""+year+ month;
	//alert(date);
	var url = "xinan/NextDeliveryLoadAction.action?date="+date+"&now="+(new Date()).valueOf();
	location.href = url;
}
//------------invoice配送实际一覧--------------
function invoiceall(e){
	var url = "xinan/invoiceallLoadAction.action?roleID="+e;
	location.href = url;
}
//------------月度販売仕入取引対照表--------------
function beforeAfter(){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	if(month<10){
		month = "0"+month;
	}
	//var date = ""+year+ month;
	//alert(date);
	var url = "xinan/BeforeAfterLoadAction.action?year="+year+"&month="+month+"&now="+(new Date()).valueOf();
	location.href = url;
}
//------------出荷済（売掛金）一覧--------------
function receivemoney(){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	if(month<10){
		month = "0"+month;
	}
	//var date = ""+year+ month;
	//alert(year);
	//alert(month);
	var url = "xinan/ReceiveMoneyLoadAction.action?year="+year+"&month="+month+"&now="+(new Date()).valueOf();
	//alert(url);
	location.href = url;
}
//------------納品済（買掛金）一覧--------------
function paymoney(){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	if(month<10){
		month = "0"+month;
	}
	//var date = ""+year+ month;
	//alert(date);
	var url = "xinan/PayMoneyLoadAction.action?year="+year+"&month="+month+"&now="+(new Date()).valueOf();
	location.href = url;
}

</script>
<script type="text/javascript">
     function load() {
        var input = document.getElementsByTagName('input');
        for (var i = 0; i < input.length; i ++){
            if (input[i].type == 'text') break;
        }
        var textbox = input[i];
        // テキストボックスにフォーカス
        if (textbox) {
          textbox.focus();
          return;
        }

        var input = document.getElementsByTagName('select');
        for (var i = 0; i < input.length; i ++){
          break;
        }
        var select = input[i];
        // テキストボックスにフォーカス
        if (select) {
          select.focus();
        }
    }
</script>
<style type="text/css">
ul.marquee { /* required styles */
	display: block;
	padding: 0;
	margin: 0;
	list-style: none;
	line-height: 1;
	position: relative;
	overflow: hidden; /* optional styles for appearance */
	width: 500px;
	height: 24px;
	/* height should be included to reserve visual space for the marquee */
	background-color: #222;
	border: 5px solid #0593FF;
	border-radius: 7px;
	-moz-border-radius: 7px;
	-webkit-border-radius: 7px
}

ul.marquee li { /* required styles */
	position: absolute;
	top: -999em;
	left: 0;
	display: block;
	white-space: nowrap; /* keep all text on a single line */
	/* optional styles for appearance */
	font-size: 1.2em;
	color: #eee;
	padding: 3px 5px
}

ul.marquee li a {
	color: #0593FF
}

ul.marquee li a:hover {
	color: red
}
</style>
</head>
<body onload="load()">
	<table width="970" id="wrapper" style="background: linear-gradient(#05FBFF, #1E00FF);">
		<tbody>
			<tr>
				<%@ include file="header.jsp" %>
			</tr>
			<tr>
				<td id="container">

					<form action="menu.html" method="POST">



<div>

<div style="left: 330px; top: 92px; position: absolute;" id="infoi">
		<s:set name="alarm" value="alarm" />
		<s:if test="alarm.hasDeliveries || alarm.hasNoPayableAmounts || alarm.hasNoReceivableAmounts">
		<img src="images/wicon.jpg" height="38px;"></img>
			</div>
				<div style="left: 380px; top: 93px; height: 38px; width: 510px; position: absolute;" id="infot">
					<ul class="marquee">
					<s:if test="alarm.hasDeliveries">
						<li>配送待ちの注文があり<a href="javascript:delivery()">(詳細確認)</a></li>
					</s:if>
					<s:if test="alarm.hasNoPayableAmounts">
						<li>支払期限を過ぎた買掛金があり<a href="javascript:paymoney()">(詳細確認)</a></li>
					</s:if>
					<s:if test="alarm.hasNoReceivableAmounts">
						<li>回収期限を過ぎた売掛金があり<a href="javascript:receivemoney()">(詳細確認)</a></li>
					</s:if>
					</ul>
				</div>
				</s:if>
			</div>
<p style="height:10px"></p>
<script type="text/javascript">
	$(function(){$('.marquee').marquee({showSpeed:1000,
	scrollSpeed: 10,
	yScroll:'bottom',
	direction:'left',
	pauseSpeed: 1000,
	duplicated: true
	});});
</script>
						<div class="group clearfix" id="menubox">

							<div class="half first">
								<div class="box">
									<table>
										<thead>
											<tr>
												<th class="red">マスタ管理</th>
											</tr>
										</thead>
										<tbody class="demo">
											<tr>
												<td>
													<div id="menu_v3">
														<ul>
															<li><a title="ユーザ管理" href="javascript:user()">ユーザ管理</A></li>
															<li><a title="得意先管理" href="xinan/xinan/customer.action">得意先管理</a></li>
															<li><a title="商品管理" href="xinan/xinan/item.action">商品管理</a></li>
															<li><a title="仕入先管理" href="xinan/xinan/supplier.action">仕入先管理</a></li>
														</ul>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>


							<div class="half">
								<div class="box">
									<table>
										<thead>
											<tr>
												<th class="grn">受発注管理</th>
											</tr>
										</thead>
										<tbody class="demo">
											<tr>
												<td>
													<div id="menu_v3">
														<ul>
															<li><a title="見積書作成" href="javascript:quoteload()">見積書作成</A></li>
															<li><a title="注文書作成" href="javascript:noOrderEstimation()">注文書作成</a></li>
															<li><a title="見積依頼書作成" href="javascript:quotemake()">見積依頼書作成</a></li>
															<li><a title="未承認見積・注文一覧" href="xinan/xinan/quotenone.action">未承認見積・注文一覧</a></li>
															<li><a title="見積実績一覧" href="xinan/xinan/QuoteallFindAction.action">見積実績一覧</a></li>
															
															<li><a title="注文実績一覧" href="xinan/xinan/orderall.action">注文実績一覧</a></li>
															
														</ul>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>


							<div class="half">
								<div class="box">
									<table>
										<thead>
											<tr>
												<th class="yel">出荷・入出金管理</th>
											</tr>
										</thead>
										<tbody class="demo">
											<tr>
												<td>
													<div id="menu_v3">
														<ul>
															<li><a title="当月配送一覧" href="javascript:delivery()">当月配送一覧</a></li>
															<li><a title="翌月以後配送一覧" href="javascript:nextdelivery()">翌月以後配送一覧</a></li>
															<li><a title="INVOICE配送実績一覧" href="javascript:invoiceall(${session.User.role})">INVOICE配送実績一覧</a></li>
															<li><a title="月度販売仕入取引対照表" href="javascript:beforeAfter()">月度販売仕入取引対照表</a></li>
															<li><a title="出荷済（売掛金）一覧" href="javascript:receivemoney()">出荷済（売掛金）一覧</a></li>
															<li><a title="納品済（買掛金）一覧" href="javascript:paymoney()">納品済（買掛金）一覧</a></li>
														</ul>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!--/group clearfix-->





					</form>
				</td>
			</tr>
			<tr>
				<td>
					<!-- footerここから -->
					<div id="footer">
						<p style="align: right; font-size: 12px; vertical-align: middle;">
							Copyright ©&nbsp;
							<script type="text/javascript">document.write(new Date().getFullYear());</script>
							&nbsp;XINAN SHANGSHI Co., Ltd. All rights reserved.
						</p>
					</div> <!-- footerここまで -->
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>


<script>

var ua = {};
ua.name = window.navigator.userAgent.toLowerCase();
 
ua.isIE = (ua.name.indexOf('msie') >= 0 || ua.name.indexOf('trident') >= 0);
ua.isiPhone = ua.name.indexOf('iphone') >= 0;
ua.isiPod = ua.name.indexOf('ipod') >= 0;
ua.isiPad = ua.name.indexOf('ipad') >= 0;
ua.isiOS = (ua.isiPhone || ua.isiPod || ua.isiPad);
ua.isAndroid = ua.name.indexOf('android') >= 0;
ua.isTablet = (ua.isiPad || (ua.isAndroid && ua.name.indexOf('mobile') < 0));
 
if (ua.isiOS || ua.isTablet) {
  $('#infoi').css('left',180);
  $('#infot').css('left',217);
}

</script>







