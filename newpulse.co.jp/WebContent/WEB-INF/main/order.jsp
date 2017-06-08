<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注文書</title>
	
	<style type="text/css">
		#div1
		{
			width:300px;
            height:459px;
            float: left;
        }
        #div2
		{
			width:1008px;
            height:459px;
           margin-left:auto;
           margin-right:auto;
        }
        .tr1{
        	background-color:#c0f3b6;
        	border:1px solid #00bb5e;
        }
        
        .sub{
            background:url('images/must12.png');
            BORDER-TOP-WIDTH: 0px; 
            BORDER-LEFT-WIDTH: 0px; 
            BORDER-BOTTOM-WIDTH: 0px; 
            BORDER-RIGHT-WIDTH: 0px; 
            WIDTH: 180px; 
            HEIGHT: 60px;
            font-size:large;"
        }
        #userbody {
		PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BACKGROUND: ;PADDING-TOP: 0px;
		background:#e7f7fa;
		}
		#floatPanel .ctrolPanel{
			width:120px;
			height:356px;
			background:#e7f7fa no-repeat left top;
			border:solid 0px #ddd;
			position:fixed;
			right:500px;
			top:230px;
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
	</style>
	<script type="text/javascript" src="js/DatePicker.js?${initParam.version}"></script>
	<script type="text/javascript" src="js/jquery.min.js?${initParam.version}"></script>
	<script type="text/javascript">
	//日期初始化
		function star(){
		var year;
		var month;
		var day;
		var time;
		var d = new Date()
		year=d.getFullYear();
		month=d.getMonth()+1;
		day=d.getDate();
		if(month<10){
		month="0"+month;
		}
		if(day<10){
		day="0"+day;
		}
		time=year+"-"+month+"-"+day;
		document.getElementById("endtime").value=time;
		document.getElementById("ntime").value=time;
		
		}
		//总计合计
	function sum(e){
		e.style.border="0px";
		var tbodyObj=document.getElementById("tbody1");
		var rows=tbodyObj.getElementsByTagName("tr");
		var trsum;
		//税前金额
		var subsum=document.getElementById("subtd");
		//税费
		var taxsum=document.getElementById("tax");
		//总金额
		var ttsum=document.getElementById("tsum");
		var trsum=0;
		for(var i=1;i<=rows.length;i++){
			//总计
			var curRows=rows[i];
			var tdprice=curRows.getElementsByTagName("td")[2];
			var tdprice1=curRows.getElementsByTagName("td")[3];
			var tdprice2=curRows.getElementsByTagName("td")[5];
			var tinput=tdprice.getElementsByTagName("input")[0].value;
			var tinput1=tdprice1.getElementsByTagName("input")[0].value;
			if(""==tinput.trim()){
				tinput=0;
			}
			if(""==tinput1.trim()){
				tinput1=0;
			}
			var tdsum=tinput1*tinput;
			trsum=trsum+tdsum;
			if(trsum==0){
				trsum="";
			}
			var s=parseFloat(tdsum).toLocaleString();
			if(s==0){
				s="";
			}
			tdprice2.innerHTML=s;
			subsum.innerHTML=parseFloat(trsum).toLocaleString();
			taxsum.innerHTML=parseFloat(trsum*0.08).toLocaleString();
			ttsum.innerHTML=parseFloat(trsum*1.08).toLocaleString();
		};
		
	}	
	$(function(){
			// 页面浮动面板
			$("#floatPanel a.arrow").eq(0).click(function(){
				$("html,body").animate({scrollTop :0}, 300);
				return false;
			});
			$("#floatPanel a.arrow").eq(1).click(function(){
				$("html,body").animate({scrollTop : $(document).height()}, 300);
				return false;
			});
			
			});
	</script>
  </head>
  
  <body id="userbody" onload="star()" >
  <a href="xinan/menu.jsp" style="text-decoration:none;float:left"><input type="button" value="返回" style="color:white;background:url('images/home_13.png');width:60px;height:60px;border:0px" /></a>
  <div id="div2" style="border: 0px solid ; width:770px;height:1350px" >
  		<div style="border: 0px solid ; height:1270px; width:770px ;background-color:white">
	  		<table border="0" style="width:100%" cellspacing="0px"> 	
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td></td>
	  			</tr>
	  			<tr style="height: 40px">
	  				<td colspan="4" align="center" style="font-weight: bold;font-size:28px">注　文　書</td>
	  			</tr>
	  			
	  			<tr style="height: 40px ">
	  				<td style="width: 21px "></td>
	  				<td style="width: 459px "></td>
	  				<td></td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td>〒	104-0032 </td>
	  				<td style="font-weight:bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  				NO.&nbsp;<input type="text"  value="NPS20150108"  style="border:0px;width:150px;font-size:18px;font-weight:bold;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/></td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td>東京都中央区八丁堀４－１０－２</td>
	  				<td><hr/></td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  					<input id="endtime" value="" type="text" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="border:0px;font-size:18px;font-weight:bold;" readonly="readonly" />
	  				</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td style="font-weight:bold;font-size:20px;">ナナ·コーポレーション株式会社</td>
	  				<td><hr/></td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td><a style="font-weight:bold;font-size:18px;">オカワ</a>　様</td>
	  				<td>〒 170-0014	</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td>東京都豊島区池袋2-61-4</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td>NIビル７F</td>
	  			</tr>
	  			
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td>新安商事株式会社</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td><a style="font-weight:bold;">担当者名：
	  				</a>
	  					<input type="text"  value="臼井　麟"  style="border:0px;width:170px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  			　       </td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td><a style="font-weight:bold;">TEL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</a>
						<input type="text"  value="03－6383－1190"  style="border:0px;width:170px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td><a style="font-weight:bold;">FAX&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</a>
						<input type="text"  value="03－6383－1191"  style="border:0px;width:170px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td><a style="font-weight:bold;">携帯&nbsp;&nbsp;&nbsp;&nbsp;：</a>
	  					<input type="text"  value="080-3027-9898"  style="border:0px;width:170px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td><hr/></td>
	  			</tr>
	  			
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td></td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td colspan="3" style="font-weight:bold;">平素は格別のご高配を賜り、厚く御礼申し上げます。下記の通り、注文させていただきます。</td>
	  				
	  			</tr>
	  			<tr style="height: 20px">
	  				<td colspan="3" style="font-weight:bold;">ご確認くださいますようお願い申し上げます。</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td></td>
	  				<td></td>
	  				<td></td>
	  			</tr>
	  		</table>
	  		<table border="1" style="width:721px; margin-left:21px;" cellspacing="0px" >
	  			<tbody id="tbody1">
	  			<tr style="height: 20px" align="center">
	  				<td style="width: 70px; font-weight:bold;">商品CD</td>
	  				<td style="width: 300px; font-weight:bold;" align="center" >品名</td>
	  				<td style="width: 100px; font-weight:bold;" align="center">単価</td>
	  				<td style="width: 70px; font-weight:bold;" align="center">個数</td>
	  				<td style="width: 60px; font-weight:bold;" align="center">単位</td>
	  				<td style="width: 100px; font-weight:bold;" align="center">金額</td>
	  			</tr>
	  		
	  			<tr style="height: 20px">
	  				<td align="center">1</td>
	  				<td>
	  					<input type="text"  value="お掃除サービス料(給湯室)"  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value="10000"  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value="1"  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value="個"  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right"> 10,000    
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center">2</td>
	  				<td>
	  					<input type="text"  value="お掃除サービス料（シャワールーム）"  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value="15000"  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value="1"  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value="個"  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">15,000   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center">3</td>
	  				<td>
	  					<input type="text"  value="部品代（換気扇フィルター）"  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value="3000"  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value="1"  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value="個"  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">3,000
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center"></td>
	  				<td>
	  					<input type="text"  value=""  style="border:0px;width:300px;font-size:15px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">
	  					<input type="text"  value=""  style="border:0px;width:80px;font-size:15px;text-align:right;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:70px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="sum(this)"/>
	  				</td>
	  				<td align="center">
	  					<input type="text"  value=""  style="border:0px;width:60px;font-size:15px;text-align:center;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  				<td align="right">   
					</td>
	  			</tr>
	  			</tbody>
	  		</table>
	  		<table border="1" style="width:721px; margin-left:21px; " cellspacing="0px" >
	  			<tr style="height: 20px">
	  				<td align="center" style="width: 121px;border-top:0px;font-weight:bold;">納入期間　：</td>
	  				<td style="border-top:0px;"><input id="ntime" value="" type="text" onfocus="setday(this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);" style="border:0px;font-size:18px;width:100px" readonly="readonly" />まで</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center" style="font-weight:bold;">納入場所　：</td>
	  				<td >
	  				<input type="text"  value="弊社オフィス"  style="border:0px;font-size:15px;width:580px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  			</tr>
	  			<tr style="height: 20px">
	  				<td align="center" style="font-weight:bold;" >支払条件　：</td>
	  				<td >
	  				<input type="text"  value="納品検証合格、一ヶ月以内"  style="border:0px;font-size:15px;width:580px;"  onmousedown="this.style.border='1px solid'" onblur="this.style.border='0px solid'"/>
	  				</td>
	  			</tr>
	  		</table>
	  		<p style="height: 0px;"></p>
	  		<table border="0px" style="width:721px; margin-left:21px;" cellspacing="0px"> 
	  			<tr style="height: 20px ;border:1px solid" >
	  				<td style="width: 230px;border:1px solid;border-bottom:0px;font-weight:bold;" align="center">税 抜 合 計</td>
	  				<td style="width: 15px;border-bottom:0px;border-top:0px"></td>
	  				<td style="width: 230px;border:1px solid;border-bottom:0px;font-weight:bold;" align="center">税　  額</td>
	  				<td style="width: 15px;border-bottom:0px;border-top:0px"></td>
	  				<td style="width: 230px;border:1px solid;border-bottom:0px;font-weight:bold;" align="center">税 込 合 計</td>
	  			</tr>
	  			<tr style="height: 45px;border:1px">
	  				<td style="width: 230px;border:1px solid" align="right" >
	  					<div style="border:0px solid;width:10px;height:10px;float:left">￥</div><a id="subtd">28,000</a></td>
	  				<td style="width: 15px;border-top:0px"></td>
	  				<td style="width: 230px;border:1px solid" align="right">
	  					<div style="border:0px solid;width:10px;height:10px;float:left">￥</div><a id="tax">2,240</a></td>
	  				<td style="width: 15px;border-top:0px"></td>
	  				<td style="width: 230px;border:1px solid" align="right">
	  					<div style="border:0px solid;width:10px;height:10px;float:left">￥</div><a id="tsum">30,240</a></td>
	  			</tr>
	  		</table>
	  		<p style="height:0px"></p>
	  		<div style="border:1px solid;width:100px;height:120px;float:right;margin-right:28px" >
	  			<table border="0" style="width:100%" cellspacing="0px">
	  				<tr style="height:30px" >
	  					<td style="border-bottom: 1px solid" align="center">
	  					担&nbsp;&nbsp;&nbsp;当
	  					</td>
	  				</tr>
	  				<tr style="height:100px" >
	  					<td>
	  					
	  					</td>
	  				</tr>
	  			</table>
	  		</div>
	  		<div style="border:1px solid;width:100px;height:120px;float:right;border-right:0px;" >
	  			<table border="0" style="width:100%" cellspacing="0px">
	  				<tr style="height:30px" >
	  					<td style="border-bottom: 1px solid" align="center">
	  					副社长
	  					</td>
	  				</tr>
	  				<tr style="height:100px" >
	  					<td>
	  					
	  					</td>
	  				</tr>
	  			</table>
	  		</div>
	  		<div style="border:1px solid;width:100px;height:120px;float:right;border-right:0px;" >
	  			<table border="0" style="width:100%" cellspacing="0px">
	  				<tr style="height:30px" >
	  					<td style="border-bottom: 1px solid" align="center">
	  					社&nbsp;&nbsp;&nbsp;长
	  					</td>
	  				</tr>
	  				<tr style="height:100px" >
	  					<td>
	  					
	  					</td>
	  				</tr>
	  			</table>
	  		</div>
  		</div>
		<div class="noprint">
  		<div id="floatPanel" >
	  		<div class="ctrolPanel" style="right:20px;">
				<a class="arrow" href="#"><span>顶部</span></a>
				<a class="contact"  href="xinan/quote1.jsp">
					<input type="button" value="返回受注書" style="height:50px;width:105px"/>
				</a><br/>
				<a class="contact"  href="xinan/delivery.jsp">
					<input type="button" value="当月配送一覧" style="height:50px"/>
				</a><br/>
				<a class="contact"  href="xinan/quote1.jsp">
					<input type="button" value="印刷" style="height:50px;width:105px"/>
				</a><br/>
				
				
				<a class="arrow" href="#"><span>底部</span></a>
			</div>
		</div>
		</div>
		
  </div>
  </body>
</html>
