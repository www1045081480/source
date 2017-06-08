//----------------------------入荷済み一覧Find-----------------------------------------------
function PayMoneyFind(){
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	document.getElementById('Cyear').innerHTML = year; 
	document.getElementById('Cmon').innerHTML = month; 
	if(month<10){
		month = "0"+month;
	}
	var url = "PayMoneyFindAction.action?year="+year+"&month="+month;//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	//alert(url);
	$.ajax({
		type:"POST",
		url:url,
		async:"false",
		dataType:"json",
		error:function(data){
			alert("检索失败");
		}, 
		success:function(data){
			var Ctable=document.getElementById("Ctable");
			var str = "";
			//alert(data);
			for(var i =0;i<data.length;i++){
				str = str + "<tr  align='center'>";
				str = str + "<td width='200px'>"+data[i].supplierName+"</td>";
				var kind = data[i].categoryType;
				var currency = data[i].currency;
				if(kind == "1"){
					str = str + "<td width='80px'>材料</td>";
				}else if(kind == "2"){
					str = str + "<td width='80px'>生产设备</td>";
				}else if(kind == "3"){
					str = str + "<td width='80px'>检查设备</td>";
				}else if(kind == "4"){
					str = str + "<td width='80px'>其他</td>";
				}else{
					str = str + "<td width='80px'>其他</td>";
				}
				
				if(currency == "JP" && data[i].orderAmountOfThisMonth ==""){
					str = str + "<td width='80px' align='right'>\\0</td>";
				}else if(currency == "US" && data[i].orderAmountOfThisMonth ==""){
					str = str + "<td width='80px' align='right'>\$0.0</td>";
				}else if(currency == "CN" && data[i].orderAmountOfThisMonth ==""){
					str = str + "<td width='80px' align='right'>\R0.0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].orderAmountOfThisMonth+"</td>";
				}
				
				if(currency == "JP" && data[i].payAmount ==""){
					str = str + "<td width='80px' align='right'>\\0</td>";
				}else if(currency == "US" && data[i].payAmount ==""){
					str = str + "<td width='80px' align='right'>\$0.0</td>";
				}else if(currency == "CN" && data[i].payAmount ==""){
					str = str + "<td width='80px' align='right'>\R0.0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].payAmount+"</td>";
				}
				
				if(data[i].balance ==""){
					str = str + "<td width='80px' align='right'>\\0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].balance+"</td>";
				}
				
				if(data[i].balanceOfDollar ==""){
					str = str + "<td width='80px' align='right'>\$0.0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].balanceOfDollar+"</td>";
				}
				
				if(data[i].balanceOfChinese ==""){
					str = str + "<td width='80px' align='right'>\R0.0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].balanceOfChinese+"</td>";
				}
				
				str = str + "<td width='80px'>"+data[i].orderCdAmount+"</td>";
				str = str + "<td style='display:none'>"+data[i].payId+"</td>";
				str = str + "<td style='display:none'>"+data[i].supplierId+"</td>";
				str = str + "</tr>";
			}
			Ctable.innerHTML = str;
			//document.getElementById('customerID').value = "";
			//alert("检索成功等待返回值！")
	   
		}
	});
}
//----------------------------買掛金一览表Find-----------------------------------------------
function payFind(){
	var year = document.getElementById('year').value; 
	var month=document.getElementById('mon').value; 
	var customerID=document.getElementById('customerID').value; 
	var customerIDName=document.getElementById('customerIDName').value; 
	if(customerIDName == "" ){
		alert("仕入先名不能为空！");
		return false;
	}
	if(month<10){
		month = "0"+month;
	}
	var url1 = "PayFindAction.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=JP";//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	var url2 = "PayFindAction.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=US";
	var url3 = "PayFindAction.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=CN";
	//alert(url);
	//location.href = url;
	$.ajax({
		type:"POST",
		url:url1,
		async:"false",
		dataType:"json",
		error:function(data){
			alert("检索没有成功");
		}, 
		success:function(data){
			var Dtbody1=document.getElementById("Dtbody1");
//			var Dtbody2=document.getElementById("Dtbody2");
//			var Dtbody3=document.getElementById("Dtbody3");
			var str = "";
//			var str2 = "";
//			var str3 = "";
			for(var i =0;i<data.length;i++){
//				if(data[i].currency == "JP" || data[i].currency==null){
					str = str + "<tr  align='center'>";
					if(data[i].orderNo == "" || data[i].orderNo == null){
						if(!data[i].payDate == "" || !data[i].payDate == null){
							str = str + "<td width='90px' class='colorInput' >" +
							"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
							str = str + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
						}
					}else{
						str = str + "<td width='90px'>"+data[i].payDate+"</td>";
						str = str + "<td width='80px'>"+data[i].modelCd+"</td>";
					}
					str = str + "<td width='130px'>"+data[i].orderNo+"</td>";
					str = str + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
					str = str + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
					str = str + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
					if(data[i].payAmount == "" || data[i].payAmount == null){
						str = str + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryJP1(this);'  type='text' value=''/></td>";
					}else{
						str = str + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryJP1(this);'  type='text' value='"+data[i].payAmount+"'/></td>";
					}
					str = str + "<td width='80px' align='right'>"+data[i].balance+"</td>";
					str = str + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
					str = str + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
					str = str + "<td style='display:none'>"+data[i].orderId+"</td>";
					if(data[i].detailId == "" || data[i].detailId == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
					}
					if(data[i].supplierId == "" || data[i].supplierId == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].supplierId+"</td>";
					}
					str = str + "<td style='display:none'>"+data[i].currency+"</td>";
					str = str + "<td style='display:none'>"+data[i].balance+"</td>";
					str = str + "</tr>";
//				}
//				if(data[i].currency == "US" || 1==1){
//					str2 = str2 + "<tr  align='center'>";
//					if(data[i].orderNo!=""){
//						str2 = str2 + "<td width='90px'>"+data[i].payDate+"</td>";
//						str2 = str2 + "<td width='80px'>"+data[i].modelCd+"</td>";
//					}else{
//						if(data[i].payDate!=""){
//							str2 = str2 + "<td width='90px' class='colorInput' >" +
//							"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
//							str2 = str2 + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
//						}
//						
//					}
//					str2 = str2 + "<td width='130px'>"+data[i].orderNo+"</td>";
//					str2 = str2 + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
//					str2 = str2 + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
//					str2 = str2 + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
//	//				if(data[i].orderNo!=""){
//	//					str2 = str2 + "<td width='70px'></td>";
//	//				}else{
//						str2 = str2 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryUSA(this);' type='text' value=''/></td>";
//	//				}
//					str2 = str2 + "<td width='80px' align='right'>"+data[i].balance+"</td>";
//					str2 = str2 + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//					str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
//					str2 = str2 + "<td style='display:none'>"+data[i].orderId+"</td>";
//					str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					str2 = str2 + "<td style='display:none'>"+data[i].supplierId+"</td>";
//					str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
//					str2 = str2 + "</tr>";
//				}
//				if(data[i].currency == "CN" || 1==1){
//					str3 = str3 + "<tr  align='center'>";
//					if(data[i].orderNo!=""){
//						str3 = str3 + "<td width='90px'>"+data[i].payDate+"</td>";
//						str3 = str3 + "<td width='80px'>"+data[i].modelCd+"</td>";
//					}else{
//						if(data[i].payDate!=""){
//							str3 = str3 + "<td width='90px' class='colorInput' >" +
//							"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
//							str3 = str3 + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
//						}
//						
//					}
//					str3 = str3 + "<td width='130px'>"+data[i].orderNo+"</td>";
//					str3 = str3 + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
//					str3 = str3 + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
//					str3 = str3 + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
//	//				if(data[i].orderNo!=""){
//	//					str3 = str3 + "<td width='70px'></td>";
//	//				}else{
//						str3 = str3 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryRMB(this);' type='text' value=''/></td>";
//	//				}
//					str3 = str3 + "<td width='80px' align='right'>"+data[i].balance+"</td>";
//					str3 = str3 + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//					str3 = str3 + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].orderId+"</td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].supplierId+"</td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].currency+"</td>";
//					str3 = str3 + "</tr>";
//				}
				
				
			}

			Dtbody1.innerHTML = str;
//			Dtbody2.innerHTML = str2;
//			Dtbody3.innerHTML = str3;
			changeReceiveAmountPAYJP();
//			changeReceiveAmountPAYUS();
//			changeReceiveAmountPAYRMB();
			//alert(data.length);
			//alert("检索成功等待返回值！")
	   
		}
	});
	$.ajax({
		type:"POST",
		url:url2,
		async:"false",
		dataType:"json",
		error:function(data){
			alert("检索没有成功");
		}, 
		success:function(data){
//			var Dtbody1=document.getElementById("Dtbody1");
			var Dtbody2=document.getElementById("Dtbody2");
//			var Dtbody3=document.getElementById("Dtbody3");
//			var str = "";
			var str2 = "";
//			var str3 = "";
			for(var i =0;i<data.length;i++){
//				if(data[i].currency == "JP" || data[i].currency==null){
//					str = str + "<tr  align='center'>";
//					if(data[i].orderNo == "" || data[i].orderNo == null){
//						if(!data[i].payDate == "" || !data[i].payDate == null){
//							str = str + "<td width='90px' class='colorInput' >" +
//							"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
//							str = str + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
//						}
//					}else{
//						str = str + "<td width='90px'>"+data[i].payDate+"</td>";
//						str = str + "<td width='80px'>"+data[i].modelCd+"</td>";
//					}
//					str = str + "<td width='130px'>"+data[i].orderNo+"</td>";
//					str = str + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
//					str = str + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
//					str = str + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
//					if(data[i].payAmount == "" || data[i].payAmount == null){
//						str = str + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryJP1(this);'  type='text' value=''/></td>";
//					}else{
//						str = str + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryJP1(this);'  type='text' value='"+data[i].payAmount+"'/></td>";
//					}
//					str = str + "<td width='80px' align='right'>"+data[i].balance+"</td>";
//					str = str + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//					str = str + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
//					str = str + "<td style='display:none'>"+data[i].orderId+"</td>";
//					if(data[i].detailId == "" || data[i].detailId == null){
//						str = str + "<td style='display:none'></td>";
//					}else{
//						str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
//					}
//					if(data[i].supplierId == "" || data[i].supplierId == null){
//						str = str + "<td style='display:none'></td>";
//					}else{
//						str = str + "<td style='display:none'>"+data[i].supplierId+"</td>";
//					}
//					str = str + "<td style='display:none'>"+data[i].currency+"</td>";
//					str = str + "</tr>";
//				}
//				if(data[i].currency == "US" || 1==1){
					str2 = str2 + "<tr  align='center'>";
					if(data[i].orderNo == "" || data[i].orderNo == null){
						if(!data[i].payDate == "" || !data[i].payDate == null){
							str2 = str2 + "<td width='90px' class='colorInput' >" +
							"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
							str2 = str2 + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
						}
					}else{
						str2 = str2 + "<td width='90px'>"+data[i].payDate+"</td>";
						str2 = str2 + "<td width='80px'>"+data[i].modelCd+"</td>";
					}
					str2 = str2 + "<td width='130px'>"+data[i].orderNo+"</td>";
					str2 = str2 + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
					str2 = str2 + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
					str2 = str2 + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
					if(data[i].payAmount == "" || data[i].payAmount == null){
						str2 = str2 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryUSA(this);'  type='text' value=''/></td>";
					}else{
						str2 = str2 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryUSA(this);'  type='text' value='"+data[i].payAmount+"'/></td>";
					}
					str2 = str2 + "<td width='80px' align='right'>"+data[i].balance+"</td>";
					str2 = str2 + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
					str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
					str2 = str2 + "<td style='display:none'>"+data[i].orderId+"</td>";
					if(data[i].detailId == "" || data[i].detailId == null){
						str2 = str2 + "<td style='display:none'></td>";
					}else{
						str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
					}
					if(data[i].supplierId == "" || data[i].supplierId == null){
						str2 = str2 + "<td style='display:none'></td>";
					}else{
						str2 = str2 + "<td style='display:none'>"+data[i].supplierId+"</td>";
					}
					str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
					str2 = str2 + "<td style='display:none'>"+data[i].balance+"</td>";
					str2 = str2 + "</tr>";
//				}
//				if(data[i].currency == "CN" || 1==1){
//					str3 = str3 + "<tr  align='center'>";
//					if(data[i].orderNo!=""){
//						str3 = str3 + "<td width='90px'>"+data[i].payDate+"</td>";
//						str3 = str3 + "<td width='80px'>"+data[i].modelCd+"</td>";
//					}else{
//						if(data[i].payDate!=""){
//							str3 = str3 + "<td width='90px' class='colorInput' >" +
//							"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
//							str3 = str3 + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
//						}
//						
//					}
//					str3 = str3 + "<td width='130px'>"+data[i].orderNo+"</td>";
//					str3 = str3 + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
//					str3 = str3 + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
//					str3 = str3 + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
//	//				if(data[i].orderNo!=""){
//	//					str3 = str3 + "<td width='70px'></td>";
//	//				}else{
//						str3 = str3 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryRMB(this);' type='text' value=''/></td>";
//	//				}
//					str3 = str3 + "<td width='80px' align='right'>"+data[i].balance+"</td>";
//					str3 = str3 + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//					str3 = str3 + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].orderId+"</td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].supplierId+"</td>";
//					str3 = str3 + "<td style='display:none'>"+data[i].currency+"</td>";
//					str3 = str3 + "</tr>";
//				}
				
				
			}

//			Dtbody1.innerHTML = str;
			Dtbody2.innerHTML = str2;
//			Dtbody3.innerHTML = str3;
//			changeReceiveAmountPAYJP();
			changeReceiveAmountPAYUS();
//			changeReceiveAmountPAYRMB();
			//alert(data.length);
			//alert("检索成功等待返回值！")
	   
		}
	});
	

$.ajax({
	type:"POST",
	url:url3,
	async:"false",
	dataType:"json",
	error:function(data){
		alert("检索没有成功");
	}, 
	success:function(data){
//		var Dtbody1=document.getElementById("Dtbody1");
//		var Dtbody2=document.getElementById("Dtbody2");
		var Dtbody3=document.getElementById("Dtbody3");
//		var str = "";
//		var str2 = "";
		var str3 = "";
		for(var i =0;i<data.length;i++){
//			if(data[i].currency == "JP" || data[i].currency==null){
//				str = str + "<tr  align='center'>";
//				if(data[i].orderNo == "" || data[i].orderNo == null){
//					if(!data[i].payDate == "" || !data[i].payDate == null){
//						str = str + "<td width='90px' class='colorInput' >" +
//						"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
//						str = str + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
//					}
//				}else{
//					str = str + "<td width='90px'>"+data[i].payDate+"</td>";
//					str = str + "<td width='80px'>"+data[i].modelCd+"</td>";
//				}
//				str = str + "<td width='130px'>"+data[i].orderNo+"</td>";
//				str = str + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
//				str = str + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
//				str = str + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
//				if(data[i].payAmount == "" || data[i].payAmount == null){
//					str = str + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryJP1(this);'  type='text' value=''/></td>";
//				}else{
//					str = str + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryJP1(this);'  type='text' value='"+data[i].payAmount+"'/></td>";
//				}
//				str = str + "<td width='80px' align='right'>"+data[i].balance+"</td>";
//				str = str + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//				str = str + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
//				str = str + "<td style='display:none'>"+data[i].orderId+"</td>";
//				if(data[i].detailId == "" || data[i].detailId == null){
//					str = str + "<td style='display:none'></td>";
//				}else{
//					str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
//				}
//				if(data[i].supplierId == "" || data[i].supplierId == null){
//					str = str + "<td style='display:none'></td>";
//				}else{
//					str = str + "<td style='display:none'>"+data[i].supplierId+"</td>";
//				}
//				str = str + "<td style='display:none'>"+data[i].currency+"</td>";
//				str = str + "</tr>";
//			}
//			if(data[i].currency == "US" || 1==1){
//				str2 = str2 + "<tr  align='center'>";
//				if(data[i].orderNo!=""){
//					str2 = str2 + "<td width='90px'>"+data[i].payDate+"</td>";
//					str2 = str2 + "<td width='80px'>"+data[i].modelCd+"</td>";
//				}else{
//					if(data[i].payDate!=""){
//						str2 = str2 + "<td width='90px' class='colorInput' >" +
//						"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
//						str2 = str2 + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
//					}
//					
//				}
//				str2 = str2 + "<td width='130px'>"+data[i].orderNo+"</td>";
//				str2 = str2 + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
//				str2 = str2 + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
//				str2 = str2 + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
////				if(data[i].orderNo!=""){
////					str2 = str2 + "<td width='70px'></td>";
////				}else{
//					str2 = str2 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryUSA(this);' type='text' value=''/></td>";
////				}
//				str2 = str2 + "<td width='80px' align='right'>"+data[i].balance+"</td>";
//				str2 = str2 + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//				str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
//				str2 = str2 + "<td style='display:none'>"+data[i].orderId+"</td>";
//				str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
//				str2 = str2 + "<td style='display:none'>"+data[i].supplierId+"</td>";
//				str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
//				str2 = str2 + "</tr>";
//			}
//			if(data[i].currency == "CN" || 1==1){
				str3 = str3 + "<tr  align='center'>";
				if(data[i].orderNo == "" || data[i].orderNo == null){
					if(!data[i].payDate == "" || !data[i].payDate == null){
						str3 = str3 + "<td width='90px' class='colorInput' >" +
						"<input value='" +data[i].payDate+"'  type='text' class='colorInput' onfocus=\"setday(this,\'yyyy-MM-dd\',\'2000-01-01\',\'2100-12-30\',0);\" style='width:90px;border:0px;font-size:12px;text-align:center;' readonly='readonly' /></td>";
						str3 = str3 + "<td width='80px' class='aclass'><input  class='binput2'  type='text' value='"+data[i].modelCd+"'/></td>";
					}
				}else{
					str3 = str3 + "<td width='90px'>"+data[i].payDate+"</td>";
					str3 = str3 + "<td width='80px'>"+data[i].modelCd+"</td>";
				}
				str3 = str3 + "<td width='130px'>"+data[i].orderNo+"</td>";
				str3 = str3 + "<td width='80px' align='right'>"+data[i].purchaseAmount+"</td>";
				str3 = str3 + "<td width='80px' align='right'>"+data[i].exciseAmount+"</td>";
				str3 = str3 + "<td width='100px' align='right'>"+data[i].totalAmount+"</td>";
				if(data[i].payAmount == "" || data[i].payAmount == null){
					str3 = str3 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryRMB(this);'  type='text' value=''/></td>";
				}else{
					str3 = str3 + "<td width='80px' class='aclass'><input  class='binput3' onchange='changMoneryRMB(this);'  type='text' value='"+data[i].payAmount+"'/></td>";
				}
				str3 = str3 + "<td width='80px' align='right'>"+data[i].balance+"</td>";
				str3 = str3 + "<td width='50px' class='aclass'><input class='cinput1' value = '"+data[i].tradingNo+"' type='text'  /></td>";
				str3 = str3 + "<td width='100px' class='aclass'><input  class='dinput' type='text' value='"+data[i].note+"' /></td>";
				str3 = str3 + "<td style='display:none'>"+data[i].orderId+"</td>";
				if(data[i].detailId == "" || data[i].detailId == null){
					str3 = str3 + "<td style='display:none'></td>";
				}else{
					str3 = str3 + "<td style='display:none'>"+data[i].detailId+"</td>";
				}
				if(data[i].supplierId == "" || data[i].supplierId == null){
					str3 = str3 + "<td style='display:none'></td>";
				}else{
					str3 = str3 + "<td style='display:none'>"+data[i].supplierId+"</td>";
				}
				str3 = str3 + "<td style='display:none'>"+data[i].currency+"</td>";
				str3 = str3 + "<td style='display:none'>"+data[i].balance+"</td>";
				str3 = str3 + "</tr>";
//			}
			
			
		}

//		Dtbody1.innerHTML = str;
//		Dtbody2.innerHTML = str2;
		Dtbody3.innerHTML = str3;
//		changeReceiveAmountPAYJP();
//		changeReceiveAmountPAYUS();
		changeReceiveAmountPAYRMB();
		//alert(data.length);
		//alert("检索成功等待返回值！")
   
	}
});

}

function payClean(){
	document.getElementById('customerID').value =""; 
	document.getElementById('customerIDName').value =""; 
	
}
//---------------------------------仕入先別納品済（買掛金）一覧SAVE---------------------------
function paySAVE(){
	var checkRole=document.getElementById("checkRole").value;
	if(checkRole != 3){
		alert("只有<<财务>>权限才能保存！");
		return false;
	}
	var tbodyObj=document.getElementById("Dtbody1");
	var rows=tbodyObj.getElementsByTagName("tr");
	var tbodyObj2=document.getElementById("Dtbody2");
	var rows2=tbodyObj2.getElementsByTagName("tr");
	var tbodyObj3=document.getElementById("Dtbody3");
	var rows3=tbodyObj2.getElementsByTagName("tr");
	var str = "";
	var str1 = "";
	var str2 = "";
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		var t0=curRows.getElementsByTagName("td")[0];
		var t1=curRows.getElementsByTagName("td")[1];
		var t2=curRows.getElementsByTagName("td")[2];
		var t6=curRows.getElementsByTagName("td")[6];
		var t7=curRows.getElementsByTagName("td")[7];
		var t8=curRows.getElementsByTagName("td")[8];
		var t9=curRows.getElementsByTagName("td")[9];
		var t10=curRows.getElementsByTagName("td")[10];
		var t11=curRows.getElementsByTagName("td")[11];
		var t12=curRows.getElementsByTagName("td")[12];
		var t13=curRows.getElementsByTagName("td")[13];
		var valuet2 = t2.innerHTML;
		if(valuet2 == ""){
			var valuet0 = t0.getElementsByTagName("input")[0].value;
			var valuet1 = t1.getElementsByTagName("input")[0].value;
		}else{
			var valuet0 = t0.innerHTML;
			var valuet1 = t1.innerHTML;
		}
		var valuet6 = stringToInt1(t6.getElementsByTagName("input")[0].value);
		var valuet7 = t7.innerHTML; 
		var valuet8 = t8.getElementsByTagName("input")[0].value;
		var valuet9 = t9.getElementsByTagName("input")[0].value;
		var valuet10 = t10.innerHTML; 
		var valuet11 = t11.innerHTML; 
		var valuet12 = t12.innerHTML; 
		var valuet13 = t13.innerHTML; 
		str = str + valuet0 +"@";//0
		str = str + valuet1 +"@";//1
		str = str + valuet2 +"@";//2
		str = str + valuet6 +"@";//3
		str = str + valuet7 +"@";//4
		str = str + valuet8 +"@";//5
		str = str + valuet9 +"@";//6
		str = str + valuet10 +"@";//7
		str = str + valuet11 +"@";//8
		str = str + valuet12 +"@";//9
		str = str + valuet13 +"@";//10
		str = str + "end" +"FIN";
	}
	for(var i=0;i<rows2.length;i++){
		var curRows=rows2[i];
		var t0=curRows.getElementsByTagName("td")[0];
		var t1=curRows.getElementsByTagName("td")[1];
		var t2=curRows.getElementsByTagName("td")[2];
		var t6=curRows.getElementsByTagName("td")[6];
		var t7=curRows.getElementsByTagName("td")[7];
		var t8=curRows.getElementsByTagName("td")[8];
		var t9=curRows.getElementsByTagName("td")[9];
		var t10=curRows.getElementsByTagName("td")[10];
		var t11=curRows.getElementsByTagName("td")[11];
		var t12=curRows.getElementsByTagName("td")[12];
		var t13=curRows.getElementsByTagName("td")[13];
		var valuet2 = t2.innerHTML;
		if(valuet2 == ""){
			var valuet0 = t0.getElementsByTagName("input")[0].value;
			var valuet1 = t1.getElementsByTagName("input")[0].value;
		}else{
			var valuet0 = t0.innerHTML;
			var valuet1 = t1.innerHTML;
		}
		var valuet6 = stringToInt1(t6.getElementsByTagName("input")[0].value);
		var valuet7 = t7.innerHTML; 
		var valuet8 = t8.getElementsByTagName("input")[0].value;
		var valuet9 = t9.getElementsByTagName("input")[0].value;
		var valuet10 = t10.innerHTML; 
		var valuet11 = t11.innerHTML; 
		var valuet12 = t12.innerHTML; 
		var valuet13 = t13.innerHTML; 
		str1 = str1 + valuet0 +"@";//0
		str1 = str1 + valuet1 +"@";//1
		str1 = str1 + valuet2 +"@";//2
		str1 = str1 + valuet6 +"@";//3
		str1 = str1 + valuet7 +"@";//4
		str1 = str1 + valuet8 +"@";//5
		str1 = str1 + valuet9 +"@";//6
		str1 = str1 + valuet10 +"@";//7
		str1 = str1 + valuet11 +"@";//8
		str1 = str1 + valuet12 +"@";//9
		str1 = str1 + valuet13 +"@";//10
		str1 = str1 + "end" +"FIN";
	}
	for(var i=0;i<rows3.length;i++){
		var curRows=rows3[i];
		var t0=curRows.getElementsByTagName("td")[0];
		var t1=curRows.getElementsByTagName("td")[1];
		var t2=curRows.getElementsByTagName("td")[2];
		var t6=curRows.getElementsByTagName("td")[6];
		var t7=curRows.getElementsByTagName("td")[7];
		var t8=curRows.getElementsByTagName("td")[8];
		var t9=curRows.getElementsByTagName("td")[9];
		var t10=curRows.getElementsByTagName("td")[10];
		var t11=curRows.getElementsByTagName("td")[11];
		var t12=curRows.getElementsByTagName("td")[12];
		var t13=curRows.getElementsByTagName("td")[13];
		var valuet2 = t2.innerHTML;
		if(valuet2 == ""){
			var valuet0 = t0.getElementsByTagName("input")[0].value;
			var valuet1 = t1.getElementsByTagName("input")[0].value;
		}else{
			var valuet0 = t0.innerHTML;
			var valuet1 = t1.innerHTML;
		}
		var valuet6 = stringToInt1(t6.getElementsByTagName("input")[0].value);
		var valuet7 = t7.innerHTML; 
		var valuet8 = t8.getElementsByTagName("input")[0].value;
		var valuet9 = t9.getElementsByTagName("input")[0].value;
		var valuet10 = t10.innerHTML; 
		var valuet11 = t11.innerHTML; 
		var valuet12 = t12.innerHTML; 
		var valuet13 = t13.innerHTML; 
		str2 = str2 + valuet0 +"@";//0
		str2 = str2 + valuet1 +"@";//1
		str2 = str2 + valuet2 +"@";//2
		str2 = str2 + valuet6 +"@";//3
		str2 = str2 + valuet7 +"@";//4
		str2 = str2 + valuet8 +"@";//5
		str2 = str2 + valuet9 +"@";//6
		str2 = str2 + valuet10 +"@";//7
		str2 = str2 + valuet11 +"@";//8
		str2 = str2 + valuet12 +"@";//9
		str2 = str2 + valuet13 +"@";//10
		str2 = str2 + "end" +"FIN";
	}
	//alert(str);
	var url = "PayMoneySaveAction.action?str="+str+str1+str2;
	$.ajax({
		type:"POST",
		url:url,
		async:"false",
		dataType:"json",
		error:function(data){
			alert("保存失败!");
		}, 
		success:function(data){
			alert("保存成功!");
			
		}
	
	});
	
	
	
	
	
	
	
}




//-------------------------------------買掛金一覧金额计算-------------------------
//function changeSum(){
//	var tbodyObj=document.getElementById("Dtbody1");
//	var rows=tbodyObj.getElementsByTagName("tr");
//	for(var i=0;i<rows.length;i++){
//		var curRows=rows[i];
//		var t2=curRows.getElementsByTagName("td")[2];
//		var t5=curRows.getElementsByTagName("td")[5];
//		var t6=curRows.getElementsByTagName("td")[6];
//		var t7=curRows.getElementsByTagName("td")[7];
//		
//		var orderId=curRows.getElementsByTagName("td")[2];
//		if(orderId.trim()!=""){
//			alert(curRows.getElementsByTagName("td")[0].getElementsByTagName("input")[0].value);
//			
//			
//			
//		}
//	}
//}
function changMoneryJP1(e){
	if(!validate1(e)){
		e.value = "";
	}else{
		//变换成日元
		var monery = parseFloat(e.value).toLocaleString();
		e.value="\\" + monery;
		changeReceiveAmountPAYJP();
	}
}

function changMoneryUSA(e){
	if(!validate(e)){
		e.value = "";
	}else{
		//变换成美元
		var monery = parseFloat(e.value).toLocaleString();
		var str = monery.split(".");
		//alert(str.length);
		if(str.length==1){
			e.value="$" + monery +".00";
		}else{
			e.value="$" + monery;
		}
	}
	changeReceiveAmountPAYUS();
}
function changMoneryRMB(e){
	if(!validate(e)){
		e.value = "";
	}else{
		//变换成美元
		var monery = parseFloat(e.value).toLocaleString();
		var str = monery.split(".");
		//alert(str.length);
		if(str.length==1){
			e.value="R" + monery +".00";
		}else{
			e.value="R" + monery;
		}
	}
	changeReceiveAmountPAYRMB();
}
//支払金額入力触发事件JP
function changeReceiveAmountPAYJP(){
	var tbodyObj=document.getElementById("Dtbody1");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(123);
	var str = "";
	//alert(rows.length);
	var tr06 = stringToInt1(rows[0].getElementsByTagName("td")[6].getElementsByTagName("input")[0].value);
	if(tr06 != 0 && tr06 != null && tr06 != ""){
		rows[0].getElementsByTagName("td")[6].getElementsByTagName("input")[0].value = "\\"+ parseFloat(tr06).toLocaleString();
	}else{
		rows[0].getElementsByTagName("td")[6].getElementsByTagName("input")[0].value = "\\0";
	}
	var tr07 = rows[0].getElementsByTagName("td")[7].innerHTML;
	rows[0].getElementsByTagName("td")[7].innerHTML = toJP1(tr07);
	//var tr17 = rows[1].getElementsByTagName("td")[7].innerHTML;
	//rows[1].getElementsByTagName("td")[7].innerHTML = toJP1(tr17);
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		//注文番号
		var t2=curRows.getElementsByTagName("td")[2];//.innerHTML;
		var valuet2 = t2.innerHTML;
		//alert("2v"+valuet2);
		//購入金額
		var t3=curRows.getElementsByTagName("td")[3];//.innerHTML;
		var valuet3 = t3.innerHTML;
		//alert("3v"+valuet3);
		//没有值的时候初始化
		if(valuet3 =="" || valuet3==null){
			valuet3 = 0;
			if(valuet2 == null || valuet2 == ""){
				t3.innerHTML = "";
			}else{
				t3.innerHTML = "\\"+0;
			}
		}else {
			t3.innerHTML = toJP1(valuet3);
		}
		//消費税
		var t4=curRows.getElementsByTagName("td")[4]//;.innerHTML;
		var valuet4 = t4.innerHTML;
		if(valuet4 =="" || valuet4==null){
			valuet4 = 0;
			if(valuet2 == null || valuet2 == ""){
				t4.innerHTML = "";
			}else{
				t4.innerHTML = "\\"+0;
			}
		}else {
			t4.innerHTML = toJP1(valuet4);
		}
		//小計
		var t5=curRows.getElementsByTagName("td")[5]//;.innerHTML;
		var valuet5 = t5.innerHTML;
		if(valuet5 =="" || valuet5==null){
			valuet5 = 0;
			if(valuet2 == null || valuet2 == ""){
				t5.innerHTML = "";
			}else{
				t5.innerHTML = "\\"+0;
			}
		}else {
			t5.innerHTML = toJP1(valuet5);
		}
		//支付金额
		var t6=curRows.getElementsByTagName("td")[6].getElementsByTagName("input")[0];//;.innerHTML;
		var valuet6 = t6.value;
		if(valuet6 =="" || valuet6==null){
			valuet6 = 0;
			t6.value = "\\"+0;
		}else {
			t6.value = toJP1(valuet6);
		}
		//alert(valuet6);
		//残高
		var t7=curRows.getElementsByTagName("td")[7]//;.innerHTML;
		var valuet7 = t7.innerHTML;
		if(valuet7 =="" || valuet7==null){
			valuet7 = 0;
			t7.innerHTML = "\\"+0;
		}else {
			t7.innerHTML = toJP1(valuet7);
		}
		//残高计算
		////获得上一行残高
		if(i==0){
			var bCG = rows[0].getElementsByTagName("td")[14].innerHTML;
		}else{
			var bCG = rows[i-1].getElementsByTagName("td")[7].innerHTML;
		}
		if(bCG == "" || bCG == null || bCG == 0){
			var bCG = "\\0";
		}
		//alert(bCG);
		////上一行残高数+这一行小计-这一行支付
		var zCG = parseInt(stringToInt1(bCG)) + parseInt(stringToInt1(valuet5)) - parseInt(stringToInt1(valuet6));
		t7.innerHTML = "\\"+ parseFloat(zCG).toLocaleString();
	}
}
//支払金額入力触发事件US
function changeReceiveAmountPAYUS(){
	var tbodyObj=document.getElementById("Dtbody2");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(123);
	var str = "";
	//alert(rows.length);
	//var tr07 = rows[0].getElementsByTagName("td")[7].innerHTML;
	//rows[0].getElementsByTagName("td")[7].innerHTML = toJP1(tr07);
	//var tr17 = rows[1].getElementsByTagName("td")[7].innerHTML;
	//rows[1].getElementsByTagName("td")[7].innerHTML = toJP1(tr17);
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		//注文番号
		var t2=curRows.getElementsByTagName("td")[2];//.innerHTML;
		var valuet2 = t2.innerHTML;
		//alert("2v"+valuet2);
		//購入金額
		var t3=curRows.getElementsByTagName("td")[3];//.innerHTML;
		var valuet3 = t3.innerHTML;
		//alert("3v"+valuet3);
		//没有值的时候初始化
		if(valuet3 =="" || valuet3==null){
			valuet3 = 0;
			if(valuet2 == null || valuet2 == ""){
				t3.innerHTML = "";
			}else{
				t3.innerHTML = "\$0.00";
			}
		}else {
			t3.innerHTML = toUS1(valuet3);
		}
		//alert(t3.innerHTML);
		//消費税
		var t4=curRows.getElementsByTagName("td")[4]//;.innerHTML;
		var valuet4 = t4.innerHTML;
		if(valuet4 =="" || valuet4==null){
			valuet4 = 0;
			if(valuet2 == null || valuet2 == ""){
				t4.innerHTML = "";
			}else{
				t4.innerHTML = "\$0.00";
			}
		}else {
			t4.innerHTML = toUS1(valuet4);
		}
		//小計
		var t5=curRows.getElementsByTagName("td")[5]//;.innerHTML;
		var valuet5 = t5.innerHTML;
		if(valuet5 =="" || valuet5==null){
			valuet5 = 0;
			if(valuet2 == null || valuet2 == ""){
				t5.innerHTML = "";
			}else{
				t5.innerHTML = "\$0.00";
			}
				
		}else {
			t5.innerHTML = toUS1(valuet5);
		}
		//支付金额
		var t6=curRows.getElementsByTagName("td")[6].getElementsByTagName("input")[0];//;.innerHTML;
		var valuet6 = t6.value;
		if(valuet6 =="" || valuet6==null){
			valuet6 = 0;
			t6.value = "\$0.00";
		}else {
			
			t6.value = "\$" + parseFloat(stringToInt1(valuet6)).toLocaleString();
		}
		//alert(valuet6);
		//残高
		var t7=curRows.getElementsByTagName("td")[7]//;.innerHTML;
		var valuet7 = t7.innerHTML;
		if(valuet7 =="" || valuet7==null){
			valuet7 = 0;
			t7.innerHTML = "\$0.00";
		}else {
			t7.innerHTML = toUS1(valuet7);
		}
		//残高计算
		////获得上一行残高
		if(i==0){
			var bCG = rows[0].getElementsByTagName("td")[14].innerHTML;
		}else{
			var bCG = rows[i-1].getElementsByTagName("td")[7].innerHTML;
		}
		if(bCG == "" || bCG == null || bCG == 0){
			var bCG = "\$0.00";
		}
		//alert(bCG);
		////上一行残高数+这一行小计-这一行支付
		var zCG = parseFloat(stringToInt1(bCG)) + parseFloat(stringToInt1(valuet5)) - parseFloat(stringToInt1(valuet6));
		t7.innerHTML = "\$"+parseFloat(zCG).toLocaleString();
	}
}
//支払金額入力触发事件RMB
function changeReceiveAmountPAYRMB(){
	var tbodyObj=document.getElementById("Dtbody3");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(123);
	var str = "";
	//alert(rows.length);
	//var tr07 = rows[0].getElementsByTagName("td")[7].innerHTML;
	//rows[0].getElementsByTagName("td")[7].innerHTML = toJP1(tr07);
	//var tr17 = rows[1].getElementsByTagName("td")[7].innerHTML;
	//rows[1].getElementsByTagName("td")[7].innerHTML = toJP1(tr17);
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		//注文番号
		var t2=curRows.getElementsByTagName("td")[2];//.innerHTML;
		var valuet2 = t2.innerHTML;
		//alert("2v"+valuet2);
		//購入金額
		var t3=curRows.getElementsByTagName("td")[3];//.innerHTML;
		var valuet3 = t3.innerHTML;
		//alert("3v"+valuet3);
		//没有值的时候初始化
		if(valuet3 =="" || valuet3==null){
			valuet3 = 0;
			if(valuet2 == null || valuet2 == ""){
				t3.innerHTML = "";
			}else{
				t3.innerHTML = "\R0.00";
			}
		}else {
			t3.innerHTML = toRMB1(valuet3);
		}
		//alert(t3.innerHTML);
		//消費税
		var t4=curRows.getElementsByTagName("td")[4]//;.innerHTML;
		var valuet4 = t4.innerHTML;
		if(valuet4 =="" || valuet4==null){
			valuet4 = 0;
			if(valuet2 == null || valuet2 == ""){
				t4.innerHTML = "";
			}else{
				t4.innerHTML = "\R0.00";
			}
		}else {
			t4.innerHTML = toRMB1(valuet4);
		}
		//小計
		var t5=curRows.getElementsByTagName("td")[5]//;.innerHTML;
		var valuet5 = t5.innerHTML;
		if(valuet5 =="" || valuet5==null){
			valuet5 = 0;
			if(valuet2 == null || valuet2 == ""){
				t5.innerHTML = "";
			}else{
				t5.innerHTML = "\R0.00";
			}
		}else {
			t5.innerHTML = toRMB1(valuet5);
		}
		//支付金额
		var t6=curRows.getElementsByTagName("td")[6].getElementsByTagName("input")[0];//;.innerHTML;
		var valuet6 = t6.value;
		if(valuet6 =="" || valuet6==null){
			valuet6 = 0;
			t6.value = "\R0.00";
		}else {
			t6.value = "\R" + parseFloat(stringToInt1(valuet6)).toLocaleString();
		}
		//alert(valuet6);
		//残高
		var t7=curRows.getElementsByTagName("td")[7]//;.innerHTML;
		var valuet7 = t7.innerHTML;
		if(valuet7 =="" || valuet7==null){
			valuet7 = 0;
			t7.innerHTML = "\R0.00";
		}else {
			t7.innerHTML = toRMB1(valuet7);
		}
		//残高计算
		////获得上一行残高
		if(i==0){
			var bCG = rows[0].getElementsByTagName("td")[14].innerHTML;
		}else{
			var bCG = rows[i-1].getElementsByTagName("td")[7].innerHTML;
		}
		if(bCG == "" || bCG == null || bCG == 0){
			var bCG = "\R0.00";
		}
		//alert(bCG);
		////上一行残高数+这一行小计-这一行支付
		var zCG = parseFloat(stringToInt1(bCG)) + parseFloat(stringToInt1(valuet5)) - parseFloat(stringToInt1(valuet6));
		t7.innerHTML = "\R"+parseFloat(zCG).toLocaleString();
	}
}

//----------------------------入荷済み一覧Download-----------------------------------------------
function downloadArrival(){
	var status = document.getElementById('payTag1').getAttribute('aria-selected'); 
	
	if(status=="true"){
		downloadArrivalList();
	} else {
		downloadSupplierArrival();
	}
}

function downloadArrivalList(){
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	if(month<10){
		month = "0"+month;
	}
	var url = "xinan/downloadArrival.action?year="+year+"&month="+month;//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	window.open(url);
}
//----------------------------買掛金一览表Find-----------------------------------------------
function downloadSupplierArrival(){
	var year = document.getElementById('year').value; 
	var month=document.getElementById('mon').value; 
	var customerID=document.getElementById('customerID').value; 
	var customerIDName=document.getElementById('customerIDName').value; 
	if(customerID == ""){
		customerID = "";
	}
	if(month<10){
		month = "0"+month;
	}
	var url = "xinan/downloadSupplierArrival.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=JP";//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	window.open(url);

}
