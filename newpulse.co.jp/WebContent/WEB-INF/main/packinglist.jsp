<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	
	<style type="text/css">
		#div1
		{
			
        }
        #div2
		{
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
		PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-TOP: 0px;
		background:#e7f7fa;
		}
		<!--70f8a9
		#b8fcfa
		--> 
	</style>
	<script type="text/javascript">
	
	
	
	</script>
  </head>
  
  <body id="userbody">
  <a href="xinan/menu.jsp" style="text-decoration:none;float:left;"><input type="button" value="·µ»Ø" style="color:white;background:url('images/home_13.png');width:60px;height:60px;border:0px" /></a>
  <div id="div2" style="border-top: 1px solid ;border-color:white; width:770px;height:1030px;background-color:white;" >
  		<div style="border: 0px solid ; width:100%;height:130px;background-color:white;">
  			<table border="0px" cellspacing="0px" style="width:650px; margin-left:30px;margin-top:21px">
  				<tr style="height: 40px ;border:1px solid">
  					<td rowspan="3" style="width: 230px;border:1px solid;border-top:0px;border-left:0px;border-bottom:0px;font-size:60px;font-style:italic;" align="right">
  					XAJ&nbsp;
  					</td>
  					<td align="center" style="font-weight: bold;border:1px solid;b">XIN-AN&nbsp;&nbsp;&nbsp;TRADING&nbsp;&nbsp;&nbsp;COMPANY,LTD.</td>
  				</tr>
  				<tr>
  					<td style="height:30px;border:1px solid;border-bottom:0px;border-top:0px;font-size:12px;" align="center">
  						7F,NI&nbsp;BLDG2-61-4&nbsp;IKEBUKURO&nbsp;TOSHIMA&nbsp;KU&nbsp;TOKYO,JAPAN
  					</td>
  				</tr>
  				<tr>
  					<td style="height:30px;border:1px solid;border-top:0px;font-size:12px;" align="center">
  						<a style="font-weight:bold;">TEL:</a>+81-3-6912-7995£¬&nbsp;<a style="font-weight:bold;">FAX:</a>+81-3-6912-7996
  					</td>
  				</tr>
  			</table>
  		
  		</div>
  		<div style="border: 0px solid ; width:100%;height:310px;">
  			<a style="margin-left: 255px;font-weight:bold;">PACKING&nbsp;LIST/WEIGHT&nbsp;MEMO</a>
  			<table border="0px" cellspacing="0px" style="width:88%; margin-left:40px;margin-top:10px;font-size:12px">
  				<tr style="height: 20px">
  					<td style="border-top:1px solid;width:328px;font-weight:bold;">TO:</td>
  					<td style="border-top:1px solid;border-left:1px solid;font-weight:bold;">Invoice&nbsp;No.
  					<a style="margin-left:120px;font-weight:bold;">Date</a>
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td >ATG</td>
  					<td style="border-left:1px solid;border-bottom:1px solid;">
  					<input type="text" onfocus="javascript:if(this.value=='XAJ-A15-009')this.value='';" value= "XAJ-A15-009"/>
  					<a style="border:0px solid;margin-left:112px">27,Mar.2015</a>
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td >Anqing&nbsp;TP&nbsp;Goetze&nbsp;Piston&nbsp;Ring&nbsp;Co.,Ltd.</td>
  					<td style="border-left:1px solid;font-weight:bold;">L/C&nbsp;No.
  					<a style="margin-left:100px;font-weight:bold;">Date</a>
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td>No.16&nbsp;Ying&nbsp;Bin&nbsp;Avenue,Development&nbsp;Zone,Anqing,</td>
  					<td style="border-left:1px solid;border-bottom:1px solid;">
  					T/T<a style="margin-left:130px">/ &nbsp;&nbsp;/ &nbsp;&nbsp;/</a>
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td>Anhui&nbsp;Province,P.R&nbsp;of&nbsp;China</td>
  					<td style="border-left:1px solid;font-weight:bold;">
  					Issued&nbsp;by:<a style="margin-left:89px;font-weight:bold;">Contract&nbsp;No.</a>
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td style="border-bottom: 1px solid;">TEL:0556-5303727&nbsp;&nbsp;&nbsp;&nbsp;FAX:0556-5305790</td>
  					<td style="border-left:1px solid;border-bottom:1px solid;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/ &nbsp;&nbsp;/ &nbsp;&nbsp;/</td>
  				</tr>
  				<tr style="height: 20px;font-weight:bold;">
  					<td>
  						Shipped&nbsp;by<a style="margin-left:100px;font-weight:bold;">Sailing&nbsp;on&nbsp;or&nbsp;about</a>
  					</td>
  					<td style="border-left:1px solid; font-weight:bold;">
  						From<a style="margin-left:120px">To</a>
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td style="border-bottom:1px solid;">
  						AIR&nbsp;FRIGHT<a style="margin-left:120px">31,Mar.2015</a>
  					</td>
  					<td style="border-left:1px solid; border-bottom:1px solid;">
  						OSAKAJAPAN<a style="margin-left:70px">ANQING,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CHINA</a>
  					</td>
  				</tr>
  				<tr style="height: 20px;font-weight:bold;">
  					<td>Consignee</td>
  					<td style="border-left:1px solid;">Notify&nbsp;Party</td>
  				</tr>
  				<tr style="height: 20px">
  					<td>ATG</td>
  					<td style="border-left:1px solid;">DELIVER&nbsp;TO:ZHANG&nbsp;YUNXIA</td>
  				</tr>
  				<tr style="height: 20px">
  					<td>Anqing&nbsp;TP&nbsp;Goetze&nbsp;Piston&nbsp;Ring&nbsp;Co.,Ltd.</td>
  					<td style="border-left:1px solid;">
  						SINOTRANS&nbsp;AIR&nbsp;TRANSPORTAION
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td>No.16&nbsp;Ying&nbsp;Bin&nbsp;Avenue,Development&nbsp;Zone,Anqing,</td>
  					<td style="border-left:1px solid;">
  						DEVELOPMENT&nbsp;CO.,LTD.ANHUI&nbsp;BRANCH
  					</td>
  				</tr>
  				<tr style="height: 20px">
  					<td>Anhui&nbsp;Province,P.R&nbsp;of&nbsp;China</td>
  					<td style="border-left:1px solid;">TEL:0551-63431581&nbsp;&nbsp;EXT.8808</td>
  				</tr>
  				<tr style="height: 20px">
  					<td style="border-bottom:1px solid;">TEL:0556-5303727&nbsp;&nbsp;&nbsp;&nbsp;FAX:0556-5305790</td>
  					<td style="border-left:1px solid; border-bottom:1px solid;">
  						FAX:0551-63431580
  					</td>
  				</tr>
  			</table>
  		</div>
  		<div style="border-bottom: 1px solid ; border-top: 1px solid ;width:88%;height:300px;margin-left:40px;margin-top:2px;">
  			<table border="0px" cellspacing="0px" style="width:100%; margin-top:0px;font-size:12px">
  				<tr style="height: 20px;border-top:1px solid;font-weight:bold;">
  					<td >Marks&nbsp;&#38&nbsp;Numbers</td>
  					<td  width="20%px" align="right">Discrption&nbsp;of&nbsp;Goods</td>
  					<td  width="15%px" align="right">Quantity</td>
  					<td  width="15%px" align="right">N/W</td>
  					<td  width="15%px" align="right">G/W</td>
  					<td  width="15%px" align="right">M3</td>
  				</tr>
  				<tr style="height: 100px">
  					<td>
  						ATG<br/>
  						XAJ-A15-009&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2~5<br/>
  						TOKUSEN&nbsp;TWO&nbsp;KINDS<br/>
  						C/NO.1-5<br/>
  						MADE&nbsp;IN&nbsp;JAPAN
  					</td>
  					<td width="20%px" align="right">
  						STEEL&nbsp;TAPE<br/>
  						XL3525-8A<br/>
  						(P1411206)
  					</td>
  					<td width="15%px" align="right">399</td>
  					<td width="15%px" align="right">399.0</td>
  					<td width="15%px" align="right">511.5</td>
  					<td width="15%px" align="right">2.1991</td>
  				</tr>
  				<tr style="height: 100px">
  					<td >
  						<a style="margin-left:100px">1</a>
  					</td>
  					<td  width="20%px" align="right">STEEL&nbsp;TAPE<br/>
  						L3524-8A<br/>
  						(P1412091)
  					</td>
  					<td  width="15%px" align="right">129.7</td>
  					<td  width="15%px" align="right">129.7</td>
  					<td  width="15%px" align="right">160.5</td>
  					<td  width="15%px" align="right">0.5498</td>
  				</tr>
  			</table>
  		</div>
  		<div style="border-bottom: 0px solid ; border-top: 1px solid ;width:88%;height:200px;margin-left:40px;margin-top:2px;">
  			<table border="0px" cellspacing="0px" style="width:100%; margin-top:3px;font-size:12px">
  				<tr style="height: 10px;border-top:1px solid;">
  					<td align="center">TOTAL</td>
  					<td  width="20%px" align="LEFT">5PALLET(S)</td>
  					<td  width="15%px" align="right">528.7&nbsp;kgs</td>
  					<td  width="15%px" align="right">528.7&nbsp;kgs</td>
  					<td  width="15%px" align="right">672.0&nbsp;kgs</td>
  					<td  width="15%px" align="right">2.7489&nbsp;M3</td>
  				</tr>
  				<tr style="height: 80px;border:1px solid;font-weight:bold;">
  					<td colspan="3"></td>
  					<td colspan="3"  align="center">XIN-AN&nbsp;TRADING&nbsp;COMPANY,LTD.</td>
  				</tr>
  				<tr style="height: 80px;border:1px solid;font-weight:bold;">
  					<td colspan="3"></td>
  					<td colspan="3"  align="center">
  					<hr/>
  					MANAGER,OVERSEAS&nbsp;OPERATIONS&nbsp;DEPT.</td>
  				</tr>
  			</table>
  		</div>
  </div>	
  </body>
</html>
