//--------------------------------------月度販売仕入取引対照表Load---------------------------------------
function beforeAfterStar(){
	var date = new Date();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	var objYear = document.getElementById('bayear'); 
	var objMon=document.getElementById('bamon'); 
	//去年
	var beforeYear = year - 1;
	var longYear = year - 2;
	objYear.options.add(new Option(year,year));
	objYear.options.add(new Option(beforeYear,beforeYear));
	objYear.options.add(new Option(longYear,longYear));
	var hideYear = $("#beforeafteryear").val();
	var hideMonth = $("#beforeaftermm").val();
	if(hideYear != ""){
		objYear.value = hideYear;
		for(var i=1;i<=12;i++){
			objMon.options.add(new Option(i,i));
		};
		objMon.value = parseInt(hideMonth);
		
	}
	document.getElementById('tYear').innerHTML = objYear.value;
	document.getElementById('tMouth').innerHTML = objMon.value;
	
}
//----------------------------月度販売仕入取引対照表Find-----------------------------------------------
function beforeAfterFind (){
	
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	if(month<10){
		month = "0"+month;
	}
	var date = ""+year+ month;
	//alert(date);
	var url = "xinan/BeforeAfterLoadAction.action?year="+year+"&month="+month+"&now="+(new Date()).valueOf();
	location.href = url;
	
}

//----------------------------月度販売仕入取引対照表Save-----------------------------------------------


function beforeAfterSave(e){
	if(e != "3"){
		alert("您无权进行此操作");
		return;
	}
	/*var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	if(month<10){
		month = "0"+month;
	}*/
	//画面表格内容取得
	var tbodyObj=document.getElementById("Ctable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var str = "";
	for(var i=0;i<rows.length-3;i++){
		//获得每一行
		
		var curRows=rows[i];
		var adjustAmount=curRows.getElementsByTagName("td")[8].getElementsByTagName("input")[0].value;
		var deliveryAmount=curRows.getElementsByTagName("td")[10].getElementsByTagName("input")[0].value;
		//alert("2"+deliveryAmount);
		var payAmounts=curRows.getElementsByTagName("td")[12].getElementsByTagName("textarea")[0].value;
		//alert("3"+payAmounts);
		var payDates=curRows.getElementsByTagName("td")[13].getElementsByTagName("textarea")[0].value;
		//alert("4"+payDates);
		var deliveryDate=curRows.getElementsByTagName("td")[14].getElementsByTagName("input")[0].value;
		//alert("5"+deliveryDate);
		var tradingNo=curRows.getElementsByTagName("td")[18].getElementsByTagName("input")[0].value;
		var saleDate=curRows.getElementsByTagName("td")[19].innerHTML;
		//alert("6"+tradingNo);
		var orderDetailId=curRows.getElementsByTagName("td")[25].innerHTML;
		//alert("7"+orderDetailId);
		var invoiceDetailId=curRows.getElementsByTagName("td")[26].innerHTML;
		//alert("8"+invoiceDetailId);
		var estimationDetailId=curRows.getElementsByTagName("td")[27].innerHTML;
		//alert("9"+estimationDetailId);
		var orderId=curRows.getElementsByTagName("td")[28].innerHTML;
		//alert("10"+orderId);
		var invoiceId=curRows.getElementsByTagName("td")[29].innerHTML;
		//alert("11"+invoiceId);
		var estimationId=curRows.getElementsByTagName("td")[30].innerHTML;
		//alert("12"+estimationId);
		var supplierId=curRows.getElementsByTagName("td")[31].innerHTML;
		var currency=curRows.getElementsByTagName("td")[32].innerHTML;
		var grossMargin=curRows.getElementsByTagName("td")[22].innerHTML;
		var grossProfit=curRows.getElementsByTagName("td")[23].innerHTML;
		grossMargin = stringToInt(grossMargin);
		grossProfit = stringToInt(grossProfit);
		//alert("13"+supplierId);
		str = str + adjustAmount+"@"+deliveryAmount+"@"+payAmounts+"@"+payDates
		      +"@"+deliveryDate+"@"+tradingNo+"@"+orderDetailId
		      +"@"+invoiceDetailId+"@"+estimationDetailId+"@"+orderId+"@"+invoiceId
		      +"@"+estimationId+"@"+supplierId+"@"+currency+"@"+grossMargin
		      +"@"+grossProfit+"@"+saleDate+";";
	}
	var urlC="xinan/BeforeAfterSaveAction.action?&str="+escape(str);
	$.ajax({
		type:"POST",
		url:urlC,
		async:"false",
		dataType:"text",
		error:function(data){
			alert("保存失败!");
		}, 
		success:function(data){
			alert("保存成功!");
	   
		}
	});
}

//----------------------------月度販売仕入取引対照表金额加逗号-----------------------------------------------
function keyUp(evt,num){
	if(!validate(evt)){
		evt.value="";
		return;
	}
	var eparent = evt.parentNode;
	var e=evt.value;
	e=e.replace(/,/g,'');
	
	if(num==0){
		evt.style.border='0px solid';
		if(e == "") {
			eparent.getElementsByTagName('input')[0].value="";
		}else{
			eparent.getElementsByTagName('input')[0].value = parseFloat(e).toLocaleString();
		}
	}
	if(num==1){
		if(e == "") {
			eparent.getElementsByTagName('input')[0].value="";
		}else{
			eparent.getElementsByTagName('input')[0].value = parseFloat(e).toLocaleString();
		}
	}
	if(num==2){
		if(e == "") {
			eparent.getElementsByTagName('input')[1].value="";
		}else{
			eparent.getElementsByTagName('input')[1].value = parseFloat(e).toLocaleString();
		}
	}
	if(num==3){
		if(e == "") {
			eparent.getElementsByTagName('input')[2].value="";
		}else{
			eparent.getElementsByTagName('input')[2].value = parseFloat(e).toLocaleString();
		}
	}
	
}
//----------------------------月度販売仕入取引対照表Download-----------------------------------------------
function downloadTrading (){
	
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	if(month<10){
		month = "0"+month;
	}
	var date = ""+year+ month;
	//alert(date);
	var url = "xinan/downloadTrading.action?year="+year+"&month="+month+"&now="+(new Date()).valueOf();
	window.open(url);
	
}

//----------------------------月度販売仕入取引対照表  合計-----------------------------------------------
function amountCount(e){
	var orderAmount = e.parentNode.parentNode.getElementsByTagName('td')[7].innerHTML;
	var adjustAmount = e.parentNode.parentNode.getElementsByTagName('td')[8].getElementsByTagName('input')[0].value;
	var deliveryAmount = e.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML;
	var requireAmount = e.parentNode.parentNode.getElementsByTagName('td')[10].getElementsByTagName('input')[0].value;
	var currency = e.parentNode.parentNode.getElementsByTagName('td')[32].innerHTML;
	var sellAmount = e.parentNode.parentNode.getElementsByTagName('td')[20].innerHTML;
	var sellExcise = e.parentNode.parentNode.getElementsByTagName('td')[21].innerHTML;
	var tag = "\\";
	if("JP" == currency){
		tag = "\\";
	}else if("US" == currency){
		tag = "\$";
	}else if("CN" == currency){
		tag = "\R";
	}

	//	e.value = tag + parseFloat(e.value).toLocaleString();
	orderAmount = stringToInt(orderAmount);
	adjustAmount = stringToInt(adjustAmount);
	deliveryAmount = stringToInt(deliveryAmount);
	requireAmount = stringToInt(requireAmount);
	sellAmount = stringToInt(sellAmount);
	sellExcise = stringToInt(sellExcise);
	if(!orderAmount){
		orderAmount = 0;
	}
	if(!adjustAmount){
		adjustAmount = 0;
	}
	if(!deliveryAmount){
		deliveryAmount = 0;
	}
	if(!requireAmount){
		requireAmount = 0;
	}
	if(currency != "JP"){
		var amount = parseFloat(orderAmount) + parseFloat(adjustAmount)  + parseFloat(deliveryAmount)  + parseFloat(requireAmount) ;
		e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML = tag + parseFloat(amount).toFixed(2).toLocaleString();
		var grossProfit = parseFloat(sellAmount) + parseFloat(sellExcise)  - parseFloat(amount) ;
		e.parentNode.parentNode.getElementsByTagName('td')[23].innerHTML = tag + parseFloat(grossProfit).toFixed(2).toLocaleString();
	}else{
		var amount = parseFloat(orderAmount) + parseFloat(adjustAmount)  + parseFloat(deliveryAmount)  + parseFloat(requireAmount) ;
		e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML = tag + parseFloat(amount).toLocaleString();
		var grossProfit = parseFloat(sellAmount) + parseFloat(sellExcise)  - parseFloat(amount) ;
		e.parentNode.parentNode.getElementsByTagName('td')[23].innerHTML = tag + parseFloat(grossProfit).toLocaleString();
	}
	
	var grossMargin = (grossProfit/(parseFloat(sellAmount) + parseFloat(sellExcise)))*100;
	e.parentNode.parentNode.getElementsByTagName('td')[22].innerHTML = grossMargin.toFixed(0)+"%";
	amountSum();
}

function stringToInt(str){
	str = str.replace("%","");
	str = str.replace("\\","");
	str = str.replace("\R","");
	str = str.replace("\$","");
	str = str.split(",").join('');
	return str;
}

function amountSum(){
	var tbodyObj=document.getElementById("Ctable");
	var rows=tbodyObj.getElementsByTagName("tr");
	//合计
	var payAmountJP =0;
	var payAmountUS =0;
	var payAmountCN =0;
	//売上金額
	var recAmountJP =0;
	var recAmountUS =0;
	var recAmountCN =0;
	//消費税
	var exciseJP = 0;
	var exciseUS = 0;
	var exciseCN = 0;
	//粗利益率
	var grossMarginJP = 0;
	var grossMarginUS = 0;
	var grossMarginCN = 0;
	
	for(var i=0;i<rows.length-3;i++){
		var currency = rows[i].getElementsByTagName('td')[32].innerHTML;
		var pay =	stringToInt(rows[i].getElementsByTagName('td')[11].innerHTML);
		var payFloat = parseFloat(pay);
		var receive = stringToInt(rows[i].getElementsByTagName('td')[20].innerHTML);
		var receiveFloat = parseFloat(receive);
		var excise = stringToInt(rows[i].getElementsByTagName('td')[21].innerHTML);
		var exciseFloat = parseFloat(excise);
		if(currency == "JP"){
			payAmountJP += payFloat; 
			recAmountJP += receiveFloat; 
			exciseJP += exciseFloat; 
		}else if(currency == "US"){
			payAmountUS += payFloat; 
			recAmountUS += receiveFloat; 
			exciseUS += exciseFloat; 
		}else if(currency == "CN"){
			payAmountCN += payFloat; 
			recAmountCN += receiveFloat; 
			exciseCN += exciseFloat; 
		}
	}
	//合计
	document.getElementById("payAmountJP").innerHTML=payAmountJP;
	 $("#payAmountJP").parseNumber({format:"#,###", locale:"JP"});
	 $("#payAmountJP").formatNumber({format:"#,###", locale:"JP"});
	document.getElementById("payAmountUS").innerHTML=payAmountUS;
	 $("#payAmountUS").parseNumber({format:"#,###.00", locale:"US"});
	 $("#payAmountUS").formatNumber({format:"#,###.00", locale:"US"});
	document.getElementById("payAmountCN").innerHTML=payAmountCN;
	 $("#payAmountCN").parseNumber({format:"#,###.00", locale:"cha"});
	 $("#payAmountCN").formatNumber({format:"#,###.00", locale:"cha"});
	//売上金額
	document.getElementById("recAmountJP").innerHTML=recAmountJP;
	$("#recAmountJP").parseNumber({format:"#,###", locale:"JP"});
	 $("#recAmountJP").formatNumber({format:"#,###", locale:"JP"});
	document.getElementById("recAmountUS").innerHTML=recAmountUS;
	$("#recAmountUS").parseNumber({format:"#,###.00", locale:"US"});
	 $("#recAmountUS").formatNumber({format:"#,###.00", locale:"US"});
	document.getElementById("recAmountCN").innerHTML=recAmountCN;
	$("#recAmountCN").parseNumber({format:"#,###.00", locale:"cha"});
	 $("#recAmountCN").formatNumber({format:"#,###.00", locale:"cha"});
	//消費税
	document.getElementById("exciseJP").innerHTML=exciseJP;
	$("#exciseJP").parseNumber({format:"#,###", locale:"JP"});
	 $("#exciseJP").formatNumber({format:"#,###", locale:"JP"});
	document.getElementById("exciseUS").innerHTML=exciseUS;
	$("#exciseUS").parseNumber({format:"#,###.00", locale:"US"});
	 $("#exciseUS").formatNumber({format:"#,###.00", locale:"US"});
	document.getElementById("exciseCN").innerHTML=exciseCN;
	$("#exciseCN").parseNumber({format:"#,###.00", locale:"cha"});
	 $("#exciseCN").formatNumber({format:"#,###.00", locale:"cha"});
	//粗利 = 売上金額 - 合计
	var grossProfitJP = recAmountJP - payAmountJP;
	var grossProfitUS = recAmountUS - payAmountUS;
	var grossProfitCN = recAmountCN - payAmountCN;
	document.getElementById("grossProfitJP").innerHTML=grossProfitJP;
	$("#grossProfitJP").parseNumber({format:"#,###", locale:"JP"});
	 $("#grossProfitJP").formatNumber({format:"#,###", locale:"JP"});
	document.getElementById("grossProfitUS").innerHTML=grossProfitUS;
	$("#grossProfitUS").parseNumber({format:"#,###.00", locale:"US"});
	 $("#grossProfitUS").formatNumber({format:"#,###.00", locale:"US"});
	document.getElementById("grossProfitCN").innerHTML=grossProfitCN;
	$("#grossProfitCN").parseNumber({format:"#,###.00", locale:"cha"});
	 $("#grossProfitCN").formatNumber({format:"#,###.00", locale:"cha"});
	//粗利益率 = 粗利/(消費税 + 売上金額)*100+"%"
	 if((recAmountJP+exciseJP)>0){
		  grossMarginJP = grossProfitJP/(recAmountJP+exciseJP)*100;
	 }
	 if((recAmountUS+exciseUS)>0){
		  grossMarginUS = grossProfitUS/(recAmountUS+exciseUS)*100;
	 }
	 if((recAmountCN+exciseCN)>0){
		 
		  grossMarginCN = grossProfitCN/(recAmountCN+exciseCN)*100;
	 }
	document.getElementById("grossMarginJP").innerHTML=grossMarginJP.toFixed(0)+"%";
	document.getElementById("grossMarginUS").innerHTML=grossMarginUS.toFixed(0)+"%";
	document.getElementById("grossMarginCN").innerHTML=grossMarginCN.toFixed(0)+"%";
	
}
