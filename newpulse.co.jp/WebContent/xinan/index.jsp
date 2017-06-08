  <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
  <%@ page language="java" import="java.net.URLDecoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String name="";  
String psw="";  
String checked="";  
Cookie[] cookies=request.getCookies();  
if(cookies!=null&&cookies.length>0){   
    //遍历Cookie  
    for(int i=0;i<cookies.length;i++){  
        Cookie cookie=cookies[i];  
        //此处类似与Map有name和value两个字段,name相等才赋值,并处理编码问题   
        if("name".equals(cookie.getName())){  
            name=URLDecoder.decode(cookie.getValue(),"utf-8");  
            //将"记住我"设置为勾选   
            checked="checked";  
        }  
        if("psw".equals(cookie.getName())){  
            psw=cookie.getValue();  
        }  
    }  
}  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="X-UA-Compatible">
<title>新安商事業務管理システム</title>
<script language="JavaScript" src="../js/common.js?${initParam.version}"></script>
<link rel="stylesheet" type="text/css" href="../css/content.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="../css/default.css?${initParam.version}">
<script type="text/javascript" src="../js/jquery.min.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/thickbox.js?${initParam.version}"></script>
<script type="text/javascript" src="../js/window.js?${initParam.version}"></script>
<script type="text/javascript">
      
        // 第一項目にフォーカスを移動
        window.onload = function() {
            var userAgent = navigator.userAgent;
            if ((userAgent.indexOf("Chrome") == -1) && (userAgent.indexOf("Firefox") ==-1)){
            	document.getElementById("login-lead").innerText = "Firefoxを使ってください！";
            	document.getElementById("login").disabled=true ;
            	return;
            }
            
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
        //-->
        </script></head>
        <body onkeydown="changeKeyCodeEntertoTab();"><table width="978" id="wrapper" class="login">    <tbody><tr>
        <td rowspan="3" id="wrapper-left"><img src="../images/spacer.gif" alt="" width="4" height="1"></td><td width="970">        <!-- headerここから -->
        <div id="header">
            <table>
                            <tbody><tr>
                                <td><a><img src="../images/xlog.png" alt="XINAN SYSTEM" width="285" height="49"></a></td>
                            </tr>
                        </tbody></table>        </div>
        <!-- headerここまで -->
        </td>
        <td rowspan="3" id="wrapper-right"><img src="../images/spacer.gif" alt="" width="4" height="1"></td>
    </tr>
    <tr>
        <td id="container">

					<p id="login-lead">
					ログインを頂き、メニュー画面へお進み下さい。
                    </p>
					<form action="xinan/login.action" method="post">
						<div id="login-box">
							<table>
								<tbody>
								<tr>
									<th>ユーザーＩＤ</th>
									<td><input name="userId" type="text" maxlength="13" style="width:86%; ime-mode:inactive" value="<%=name %>">
									</td>
								</tr>
								<tr>
									<th>パスワード</th>
									<td><input name="passWord" type="password" maxlength="13" value="<%=psw %>" style="width:86%;">
									</td>
								</tr>
								<tr>
									<th>&nbsp;</th>
									<td><input name="rememberMe" type="checkbox" checked="checked" style="width:20px;">ログイン情報保持</td>
								
								</tr>
							</tbody></table>
							<p><input  id="login" name="login" type="submit" src="../images/btn_login.jpg" alt="ログイン"></p>
						</div>
					</form>
				</td>
    </tr>
    <tr>
        <td>
        <!-- footerここから -->
        <div id="footer">
            <p></p>
        </div>
        <!-- footerここまで -->
        </td>
    </tr>
</tbody></table>


</body></html>
