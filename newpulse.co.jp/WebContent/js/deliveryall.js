//-------------------------------------------------当月配送一览初始化方法-------------------------------------
function deliveryallLoad(){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	$("#year").text(year);
	$("#month").text(month);
	var customerName1 = $("#customerName1").val();
	var customerOrder1 = $("#customerOrder1").val();
	$("#customerName").val(customerName1);
	$("#customerOrder").val(customerOrder1);
	$.ajax({
		type:"POST",
		url:"xinan/customername.action?",
		async:"false",
		//data:{"CnName":cname,"JpName":jname,"EnName":ename,"Role":roleid,"UserID":id},
		//"CnName="+cname+"&JpName="+jname+"&EnName="+ename+"&Role="+roleid,
		dataType:"json",
		error:function(data){
			//alert(data);
			alert("公司名没有加载");
		}, 
		success:function(data){
			//数组合并
			//var c=data.enNames.concat(data.enShortName);
			//var availableTags= data.jpNames;
			
			//默认为英文缩写
			var availableTags= data.enShortName;
			//alert(availableTags);
		    //搜索选择
	    $("#customerName").autocomplete({
	      source: availableTags
			});
	   
		}
	});
}



//-----------------------------------------------------------invoice作成onLoad----------------------
function invoiceCreate(){
	//alert(123);
	//权限check
	var Role = document.getElementById("checkRole").value;

	if(Role==2){
		if(!checkRole(2)){
		return;
		};
	};
	if(Role==7){
		if(!checkRole(7)){
		return;
		};
	};
	//检查是否为相同公司如果相同可以作成一个invoice，如果不同不能作成一个invoice.
	if(!check()){return false};
	var diag = new Dialog();
	diag.Title = "选择运货方式";
	diag.URL = "test.html";
	diag.Width = "80%";
	diag.Height = "10%";
	diag.OKEvent = function(){iClick(diag)};
	//function(){$id('getval').value = diag.innerFrame.contentWindow.document.getElementById('a').value;diag.close();};
	diag.show();
	var doc=diag.innerFrame.contentWindow.document;
	doc.open();
	doc.write("<html><body><br/><br/>交货方式：&nbsp;CIF<input type='radio' name='arriveway' value='CIF'>" +
			"&nbsp;FOB<input type='radio' name='arriveway' value='FOB'><br/><br/>" +
			"货运方式：&nbsp;S<input type='radio' name='deliveryWay' value='S'>&nbsp;" +
			"A<input type='radio' name='deliveryWay' value='A'>&nbsp;" +
			"E<input type='radio' name='deliveryWay' value='E'>" +
			"</html>") ;
	doc.close();
}
//-------------------------------------------------------------纳期变更方法-----------------------------
function naqiChange(e){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	if(month<10){
		month = "0"+month;
	}
	var year1 = $("#year").val();
	var mon1 = $("#mon").val();
	if(mon1<10){
		mon1 = "0"+mon1;
	}
	if(e==1){
		var url = "xinan/naqiChangeAction.action?str=";
		var date = ""+year+ month;
	}
	if(e==2){
		var url = "xinan/naqiNextChangeAction.action?str=";
		var date = ""+year1+ mon1;
	}
	var tbodyObj=document.getElementById("ctbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	var flag=1;
	//checkbox
	var coll = document.getElementsByName("deliveryall");
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		//変更納期
		var couName10=curRows.getElementsByTagName("td")[10].getElementsByTagName("input")[0].value;
		if(couName10!="" && couName10 !=null){
			flag =2;
			//販売先注文番号 为estimation_confirm_tbl表中对应customer_order_no字段
			var couName1=curRows.getElementsByTagName("td")[1].innerHTML;
			//注文番号
			var couName7=curRows.getElementsByTagName("td")[7].innerHTML;
			//仕入納期
			var couName9=curRows.getElementsByTagName("td")[9].getElementsByTagName("input")[0].value;
			//注文詳細ID
			var couName12=curRows.getElementsByTagName("td")[12].innerHTML;
			//品ID 
			var couName13=curRows.getElementsByTagName("td")[13].innerHTML;
			//注文ID
			var couName14=curRows.getElementsByTagName("td")[14].innerHTML;
			//販売先ID
			var couName15=curRows.getElementsByTagName("td")[15].innerHTML;
			url = url + couName7 + ";"+couName9+";"+ couName10 + ";"+ couName12 + ";"+ couName13 + ";"+ couName14 + ";"+ couName15 + ",";
		}
	}
	if(flag == 2){
		location.href = url + "&date=" + date;
	}else{
		alert("至少填写一条进行変更納期！");
	}
}
//-----------------------------------------------------------纳品完了方法----------------------------
function napinFinsh(e){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	if(month<10){
		month = "0"+month;
	}
	var year1 = $("#year").val();
	var mon1 = $("#mon").val();
	if(mon1<10){
		mon1 = "0"+mon1;
	}
	if(e==1){
		var url = "xinan/napinFinshAction.action?str=";
		var date = ""+year+ month;
	}
	if(e==2){
		var url = "xinan/napinNextFinshAction.action?str=";
		var date = ""+year1+ mon1;
	}
	var tbodyObj=document.getElementById("ctbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	var flag=1;
	//checkbox
	var coll = document.getElementsByName("deliveryall");
	for(var i=0;i<rows.length;i++){
		if(coll[i].checked){
			flag =2;
			var curRows=rows[i];
			//販売先注文番号 为estimation_confirm_tbl表中对应customer_order_no字段
			var couName1=curRows.getElementsByTagName("td")[1].innerHTML;
			//注文番号
			var couName7=curRows.getElementsByTagName("td")[7].innerHTML;
			//注文詳細ID
			var couName12=curRows.getElementsByTagName("td")[12].innerHTML;
			//品ID 
			var couName13=curRows.getElementsByTagName("td")[13].innerHTML;
			//注文ID
			var couName14=curRows.getElementsByTagName("td")[14].innerHTML;
			//販売先ID
			var couName15=curRows.getElementsByTagName("td")[15].innerHTML;
			url = url +couName7 + ";" + couName12 + ";" + couName13 + ";" + couName14 + ";"+ couName15+ ","; 
		}
	}
	if(flag == 2){
		location.href = url + "&date=" + date;
	}else{
		alert("至少选择一条！");
	}
}

//invoice作成确认处理
function iClick(diag){
	var deliveryWay = diag.innerFrame.contentWindow.document.getElementsByName('deliveryWay');
	var arriveway = diag.innerFrame.contentWindow.document.getElementsByName('arriveway');
	var way = "";
	var arrive = "";
	 for(var i=0;i<deliveryWay.length;i++)
	  {
	     if(deliveryWay[i].checked)
	    	 way  = deliveryWay[i].value;
	     		
	  }
	 for(var i=0;i<arriveway.length;i++)
	  {
	     if(arriveway[i].checked)
	    	 arrive  = arriveway[i].value;
	  }
	 if (way==""){
		 alert("请选择货运方式");
		 return false;
	 }
	 if (arrive==""){
		 alert("请选择交货方式");
		 return false;
	 }
	var str = checkUrl(); 
	var Currency = document.getElementById("Currency").value;
	var url="xinan/invoiceCreate.action?";
	//var orderCd = "X15-ATG-0824";
	//url = url + "&orderCd="+orderCd;
	url = url + "&way="+way;
	url = url + "&arrive="+arrive;
	url = url + "&str="+str;
	url = url + "&KIND=FlagCreatI";
	url = url + "&Currency="+Currency;
	//alert(url);
	location.href = url;
	diag.close();
	
}
//checkbox中要传的url
function checkUrl(){
	var url = "";
	var tbodyObj=document.getElementById("ctbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	//checkbox
	var coll = document.getElementsByName("deliveryall");
	for(var i=0;i<rows.length;i++){
		if(coll[i].checked){
			var curRows=rows[i];
			var detailId=curRows.getElementsByTagName("td")[12].innerHTML;
			var orderId=curRows.getElementsByTagName("td")[14].innerHTML;
			var customerId=curRows.getElementsByTagName("td")[15].innerHTML;
			var customerOrderNo=curRows.getElementsByTagName("td")[1].innerHTML;
			url = url + detailId + ";" +customerId + ";"+orderId+";"+",";
		}
	}
	return url;
}



//----------------------------------------------EMS SHEET ADD STAR----------------------------------------
function EMS(){
	var tbodyObj=document.getElementById("invoiceTab");
	var rows=tbodyObj.getElementsByTagName("tr");
	var tbodyObj1=document.getElementById("packageTab1");
	var rows1=tbodyObj1.getElementsByTagName("tr");
	var name1 = rows[0].getElementsByTagName("td")[0].getElementsByTagName("textarea")[0].innerHTML;
	var quantity1 = rows[0].getElementsByTagName("td")[1].getElementsByTagName("input")[0].value;
	var unitPrice1 = rows[0].getElementsByTagName("td")[2].getElementsByTagName("input")[0].value;
	var tbodyObj1=document.getElementById("EMStable1");
	var tbodyObj2=document.getElementById("EMStable2");
	var tbodyObj3=document.getElementById("EMStable3");
	var invoiceID = document.getElementById("InvoiceCD").value;
	var unitType = document.getElementById("unitType").value;
	document.getElementById("EMSDay").value = document.getElementById("itoday").value;
	document.getElementById("EMSDay1").value = document.getElementById("itoday").value;
	document.getElementById("EMSDay2").value = document.getElementById("itoday").value;
	document.getElementById("EMSCouNo").value = document.getElementById("customerOrderNo").value;
	document.getElementById("EMSCouNo1").value = document.getElementById("customerOrderNo").value;
	document.getElementById("EMSCouNo2").value = document.getElementById("customerOrderNo").value;
	
	
	var str = "";
	str = str + "<tr style='height:25px'><td colspan='2' align='center'>"+1+"</td><td colspan='5' align='left' style='vertical-align: top;'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:110px;max-width:110px'>"+ name1 +
	"</textarea></td><td colspan='5' align='center' style='vertical-align: top;'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:110px;max-width:110px'>F-06</textarea></td><td  style='vertical-align: top;' colspan='2' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:42px;max-width:42px;text-align:right;'>" + unitType + "</textarea></td>" +
			"<td  style='vertical-align: top;' colspan='2' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:42px;max-width:42px;text-align:right;' onBlur='EMCSUM1()'>"+quantity1+
	"</textarea></td><td  style='vertical-align: top;' colspan='5' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:110px;max-width:110px;text-align:right;' onBlur='EMCSUM1()'>" + unitPrice1 + "</textarea></td></td><td  style='vertical-align: top;text-align:right;' colspan='5' align='center'>" + quantity1*unitPrice1 + "</td>"+
	"<td rowspan='"+rows.length+"' colspan='5' align='center'>" + invoiceID + "</td><td style='vertical-align: top;' colspan='2' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:45px;max-width:45px'></textarea></tr>";
	for(var i=1;i<rows.length;i++){
		var curRows=rows[i];
		var curRows1=rows1[i];
		var name = curRows.getElementsByTagName("td")[0].getElementsByTagName("textarea")[0].innerHTML;
		var quantity = curRows.getElementsByTagName("td")[1].getElementsByTagName("input")[0].value;
		var unitPrice = curRows.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value;
		str = str + "<tr style='height:25px'><td  colspan='2' align='center'>"+(i+1)+"</td><td style='vertical-align: top;' colspan='5' align='left'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:110px;max-width:110px'>"+name+
		"</textarea></td><td  style='vertical-align: top;' colspan='5' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:110px;max-width:110px'>F-06</textarea></td><td  style='vertical-align: top;' colspan='2' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:42px;max-width:42px;text-align:right;'>" + unitType + "</textarea></td><td style='vertical-align: top;' colspan='2' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:42px;max-width:42px;text-align:right;' onBlur='EMCSUM1()'"+quantity+
		"</textarea></td><td  style='vertical-align: top;' colspan='5' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:110px;max-width:110px;text-align:right;' onBlur='EMCSUM1()' >" + unitPrice + "</textarea></td>"+"</td><td  style='vertical-align: top;' colspan='5' align='center'></td>"+
		"<td colspan='2' style='vertical-align: top;' align='center'><textarea  style='background-color:#CDFFFF;border:0px solid;overflow-y:hidden;height:25px;min-width:45px;max-width:45px'></textarea></td></tr>";
	}
	tbodyObj1.innerHTML = str;
	tbodyObj2.innerHTML = str;
	tbodyObj3.innerHTML = str;
	EMCSUM();
	
	$('textarea').autosize({
		append: "\n"
	});
	
}
//function test(){
//	alert(123);
//}
function EMCSUM(){
	var tbodyObj1 = document.getElementById("EMStable1");
	var tbodyObj2 = document.getElementById("EMStable2");
	var tbodyObj3 = document.getElementById("EMStable3");
	var rows = tbodyObj1.getElementsByTagName("tr");
	var rows1 = tbodyObj2.getElementsByTagName("tr");
	var rows2 = tbodyObj3.getElementsByTagName("tr");
	var EMSZ = document.getElementById("EMSZ");
	var EMSZ1 = document.getElementById("EMSZ1");
	var EMSZ2 = document.getElementById("EMSZ2");
	var num = 0;
	var num1 = 0;
	var num2 = 0;
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		var unitType = curRows.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].innerHTML;
		curRows.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].innerHTML = parseFloat(unitType).toLocaleString();

		var unitPrice = "";
		var targets = curRows.getElementsByTagName("td")[5].getElementsByTagName("textarea");
		if (targets.length > 0){
			unitPrice = targets[0].innerHTML;
			targets[0].innerHTML = parseFloat(unitPrice).toLocaleString();
	    }
		unitType = parseInt(unitType);
		unitPrice = parseInt(unitPrice);
		curRows.getElementsByTagName("td")[6].innerHTML = parseFloat(unitType*unitPrice).toLocaleString();
		num = num +parseInt(unitType*unitPrice);
		var curRows1=rows1[i];
		var unitType1 = curRows1.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].innerHTML;
		curRows1.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].innerHTML = parseFloat(unitType1).toLocaleString();
		
		var unitPrice1 = "";
		targets = curRows1.getElementsByTagName("td")[5].getElementsByTagName("textarea");
		if (targets.length > 0){
			unitPrice1 = targets[0].innerHTML;
			targets[0].innerHTML = parseFloat(unitPrice1).toLocaleString();
		}

		unitType1 = parseInt(unitType1);
		unitPrice1 = parseInt(unitPrice1);
		curRows1.getElementsByTagName("td")[6].innerHTML = parseFloat(unitType1*unitPrice1).toLocaleString();
		num1 = num1 +parseInt(unitType1*unitPrice1);
		var curRows2=rows2[i];
		var unitType2 = curRows2.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].innerHTML;
		curRows2.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].innerHTML = parseFloat(unitType2).toLocaleString();
		
		var unitPrice2 = [0].innerHTML;
		targets = curRows2.getElementsByTagName("td")[5].getElementsByTagName("textarea");
		if (targets.length > 0){
			unitPrice2 = targets[0].innerHTML;
			targets[0].innerHTML = parseFloat(unitPrice2).toLocaleString();
		}
		
		unitType2 = parseInt(unitType2);
		unitPrice2 = parseInt(unitPrice2);
		curRows2.getElementsByTagName("td")[6].innerHTML = parseFloat(unitType2*unitPrice2).toLocaleString();
		num2 = num2 +parseInt(unitType2*unitPrice2);
	}
	EMSZ.innerHTML = parseFloat(num).toLocaleString();
	EMSZ1.innerHTML = parseFloat(num1).toLocaleString();
	EMSZ2.innerHTML = parseFloat(num2).toLocaleString();
}
function EMCSUM1(){
	var tbodyObj1 = document.getElementById("EMStable1");
	var tbodyObj2 = document.getElementById("EMStable2");
	var tbodyObj3 = document.getElementById("EMStable3");
	var rows = tbodyObj1.getElementsByTagName("tr");
	var rows1 = tbodyObj2.getElementsByTagName("tr");
	var rows2 = tbodyObj3.getElementsByTagName("tr");
	var EMSZ = document.getElementById("EMSZ");
	var EMSZ1 = document.getElementById("EMSZ1");
	var EMSZ2 = document.getElementById("EMSZ2");
	var num = 0;
	var num1 = 0;
	var num2 = 0;
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		var unitType = curRows.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].value;
		curRows.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].value = parseFloat(stringToInt1(unitType)).toLocaleString();
		var unitPrice = curRows.getElementsByTagName("td")[5].getElementsByTagName("textarea")[0].value;
		curRows.getElementsByTagName("td")[5].getElementsByTagName("textarea")[0].value = parseFloat(stringToInt1(unitPrice)).toLocaleString();
		unitType = parseInt(stringToInt1(unitType));
		unitPrice = parseInt(stringToInt1(unitPrice));
		curRows.getElementsByTagName("td")[6].innerHTML = parseFloat(unitType*unitPrice).toLocaleString();
		num = num +parseInt(unitType*unitPrice);
		
		
		
		var curRows1=rows1[i];
		var unitType1 = curRows1.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].value;
		curRows1.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].value = parseFloat(stringToInt1(unitType1)).toLocaleString();
		var unitPrice1 = curRows1.getElementsByTagName("td")[5].getElementsByTagName("textarea")[0].value;
		curRows1.getElementsByTagName("td")[5].getElementsByTagName("textarea")[0].value = parseFloat(stringToInt1(unitPrice1)).toLocaleString();
		unitType1 = parseInt(stringToInt1(unitType1));
		unitPrice1 = parseInt(stringToInt1(unitPrice1));
		curRows1.getElementsByTagName("td")[6].innerHTML = parseFloat(unitType1*unitPrice1).toLocaleString();
		num1 = num1 +parseInt(unitType1*unitPrice1);
		
		
		var curRows2=rows2[i];
		var unitType2 = curRows2.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].value;
		curRows2.getElementsByTagName("td")[4].getElementsByTagName("textarea")[0].value = parseFloat(stringToInt1(unitType2)).toLocaleString();
		var unitPrice2 = curRows2.getElementsByTagName("td")[5].getElementsByTagName("textarea")[0].value;
		curRows2.getElementsByTagName("td")[5].getElementsByTagName("textarea")[0].value = parseFloat(stringToInt1(unitPrice2)).toLocaleString();
		unitType2 = parseInt(stringToInt1(unitType2));
		unitPrice2 = parseInt(stringToInt1(unitPrice2));
		curRows2.getElementsByTagName("td")[6].innerHTML = parseFloat(unitType2*unitPrice2).toLocaleString();
		num2 = num2 +parseInt(unitType2*unitPrice2);
	}
	EMSZ.innerHTML = parseFloat(num).toLocaleString();
	EMSZ1.innerHTML = parseFloat(num1).toLocaleString();
	EMSZ2.innerHTML = parseFloat(num2).toLocaleString();
}
