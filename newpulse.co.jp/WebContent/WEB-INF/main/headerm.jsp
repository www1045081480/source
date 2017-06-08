<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String localpath = request.getContextPath();
String localbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+localpath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/CheckRole.js?${initParam.version}"></script>
</head>
<body>
		<td rowspan="3" id="wrapper-left">	</td>
		<td width="970">
			<!-- headerここから -->
			<div id="header">
				<input id="checkRole" type="hidden" value="${session.User.role}">
				<table>
					<tbody>
						<tr>
							<td><a href="<%=localbasePath%>xinan/xinan/menu.action"><img src="images/xlog.png" alt="XINAN SYSTEM" width="285" height="49"></a>
							</td>
						</tr>

					</tbody>
				</table>
			</div> <!-- headerここまで -->


			<div style="position: absolute;  margin-top: -80px;  text-align: left; margin-left: 680px;">
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
					<a href="xinan/logout.action">ログアウト</a>
				</p>
			</div>
			
		</td>
		<td rowspan="3" id="wrapper-right"><img src="images/spacer.gif" alt="" width="4" height="1"></td>
</body>
</html>