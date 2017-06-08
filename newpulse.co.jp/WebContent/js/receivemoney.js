




//----------------------------納品済（売掛金）一覧&入荷済み一覧Load-----------------------------------------------
function receivemoneyLoad(){
	
	//販売先初始化
//	var customerIDName1 = $("#customerIDName1").val();
//	$("#customerIDName").val(customerIDName1);
	//页面切换
	$( "#tabs" ).tabs(/*{
		 beforeActivate: function(event, ui){
			  alert(ui.newTab.index());
			 }
			}*/);
	
	
	var date = new Date();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	var objYear = document.getElementById('year'); 
	var obj=document.getElementById('mon'); 
	var bayear = document.getElementById('bayear'); 
	var bamon=document.getElementById('bamon'); 
	
	var nextfive = year+2;
	var nextoneba = year-2;
	var nextone = year-2;
	//出荷済み一覧年月
	for(nextoneba;nextoneba<=nextfive;nextoneba++){
		bayear.options.add(new Option(nextoneba,nextoneba));
	}
	for(var i=1;i<=12;i++){
		bamon.options.add(new Option(i,i));
	};
	//发货一览表年月
	for(nextone;nextone<=nextfive;nextone++){
		objYear.options.add(new Option(nextone,nextone));
	}
	for(var i=1;i<=12;i++){
		obj.options.add(new Option(i,i));
	};
	//出荷済み一覧年月处理
	var hidebayear = $("#receivemoneybayear").val();
	var hidebamon = $("#receivemoneybamon").val();
	if(hidebayear != ""){
		bayear.value = hidebayear;
		bamon.value = hidebamon;
		document.getElementById('Cyear').innerHTML = hidebayear;
		document.getElementById('Cmon').innerHTML = hidebamon;
		//document.getElementById('receivemoneybayear').value="";
	//	document.getElementById('receivemoneybamon').value="";
		
	}else{
		document.getElementById('Cyear').innerHTML = year;
		document.getElementById('Cmon').innerHTML = month;
		bayear.value = year;
		bamon.value= month;
	}			
	
	//发货一览表年月处理
	var hideYear = $("#receivemoneyyear").val();
	var hideMonth = $("#receivemoneymm").val();
	if(hideYear != ""){
			objYear.value = hideYear;
			obj.value = hideMonth;
			//document.getElementById('receivemoneyyear').value="";
		//	document.getElementById('receivemoneymm').value="";
			
	}else{
		objYear.value = year;
		obj.value= month;
	}
	$.ajax({
		type:"POST",
		url:"xinan/customername.action?",
		async:"false",
		dataType:"json",
		error:function(data){
			alert("公司名没有加载");
		}, 
		success:function(data){
			var availableTags= data.enShortName;
	    $("#customerIDName").autocomplete({
	      source: availableTags
			});
	   
	}
	});
	$.ajax({
		type:"POST",
		url:"xinan/suppliernames.action?",
		async:"false",
		dataType:"json",
		error:function(data){
			alert("公司名没有加载");
		}, 
		success:function(data){
			var availableTags= data.enShortName;
	    $("#customerIDName").autocomplete({
	      source: availableTags
			});
	   
	}
	});
}
function paymoneyLoad(){
	
	//販売先初始化
//	var customerIDName1 = $("#customerIDName1").val();
//	$("#customerIDName").val(customerIDName1);
	//页面切换
	$( "#tabs" ).tabs(/*{
		 beforeActivate: function(event, ui){
			  alert(ui.newTab.index());
			 }
			}*/);
	
	
	var date = new Date();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	var objYear = document.getElementById('year'); 
	var obj=document.getElementById('mon'); 
	var bayear = document.getElementById('bayear'); 
	var bamon=document.getElementById('bamon'); 
	
	var nextfive = year+2;
	var nextoneba = year-2;
	var nextone = year-2;
	//出荷済み一覧年月
	for(nextoneba;nextoneba<=nextfive;nextoneba++){
		bayear.options.add(new Option(nextoneba,nextoneba));
	}
	for(var i=1;i<=12;i++){
		bamon.options.add(new Option(i,i));
	};
	//发货一览表年月
	for(nextone;nextone<=nextfive;nextone++){
		objYear.options.add(new Option(nextone,nextone));
	}
	for(var i=1;i<=12;i++){
		obj.options.add(new Option(i,i));
	};
	//出荷済み一覧年月处理
	var hidebayear = $("#receivemoneybayear").val();
	var hidebamon = $("#receivemoneybamon").val();
	if(hidebayear != ""){
		bayear.value = hidebayear;
		bamon.value = hidebamon;
		document.getElementById('Cyear').innerHTML = hidebayear;
		document.getElementById('Cmon').innerHTML = hidebamon;
		//document.getElementById('receivemoneybayear').value="";
	//	document.getElementById('receivemoneybamon').value="";
		
	}else{
		document.getElementById('Cyear').innerHTML = year;
		document.getElementById('Cmon').innerHTML = month;
		bayear.value = year;
		bamon.value= month;
	}			
	
	//发货一览表年月处理
	var hideYear = $("#receivemoneyyear").val();
	var hideMonth = $("#receivemoneymm").val();
	if(hideYear != ""){
			objYear.value = hideYear;
			obj.value = hideMonth;
			//document.getElementById('receivemoneyyear').value="";
		//	document.getElementById('receivemoneymm').value="";
			
	}else{
		objYear.value = year;
		obj.value= month;
	}
	$.ajax({
		type:"POST",
		url:"xinan/suppliernames.action?",
		async:"false",
		dataType:"json",
		error:function(data){
			alert("公司名没有加载");
		}, 
		success:function(data){
			var availableTags= data.jpNames;
	    $("#customerIDName").autocomplete({
	      source: availableTags
			});
	   
	}
	});	
}
//失去焦点根据名字检索supplierID
function payfindCoustemID(evt) {
		      var e=document.getElementById("customerIDName").value;
              if (e.trim() == "")
                 return;
		    	  $.ajax({
						type:"POST",
						url:"xinan/supplierAddress.action?jpName="+e,
						async:"false",
						dataType:"json",
						error:function(){
						},  
						success:function(data){
							$("#customerIDName").val(data[0].jpName);
							$("#customerID").val(data[0].supplierId);
						}
		    	  });
}
//----------------------------出荷済（売掛金）統計一覧Find-----------------------------------------------
function receivemoneyFind(){
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	document.getElementById('Cyear').innerHTML = year; 
	document.getElementById('Cmon').innerHTML = month; 
	if(month<10){
		month = "0"+month;
	}
	var url = "ReceiveMoneyFindAction.action?year="+year+"&month="+month;//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
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
			//alert(data.length);
			
			for(var i =0;i<data.length;i++){
//				var customerName =  data[i].customerName;
//				var categoryType =  data[i].categoryType;
//				var sellAmountOfThisMonth =  data[i].sellAmountOfThisMonth;
//				var depositAmount =  data[i].depositAmount;
//				var balance =  data[i].balance;
//				var balanceOfDollar =  data[i].balanceOfDollar;
//				var balanceOfChinese =  data[i].balanceOfChinese;
//				var estimationCdAmount =  data[i].estimationCdAmount;
//				var estimationDetailId =  data[i].estimationDetailId;
//				var invoiceDetailId =  data[i].invoiceDetailId;
//				var customerId =  data[i].customerId;
				str = str + "<tr  align='center'>";
				str = str + "<td width='200px'>"+data[i].customerName+"</td>";
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
				if(currency == "JP" && data[i].sellAmountOfThisMonth ==""){
					str = str + "<td width='80px' align='right'>\\0</td>";
				}else if(currency == "US" && data[i].sellAmountOfThisMonth ==""){
					str = str + "<td width='80px' align='right'>\$0.0</td>";
				}else if(currency == "CN" && data[i].sellAmountOfThisMonth ==""){
					str = str + "<td width='80px' align='right'>\R0.0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].sellAmountOfThisMonth+"</td>";
				}
				
				
				if(currency == "JP" && data[i].depositAmount ==""){
					str = str + "<td width='80px' align='right'>\\0</td>";
				}else if(currency == "US" && data[i].depositAmount ==""){
					str = str + "<td width='80px' align='right'>\$0.0</td>";
				}else if(currency == "CN" && data[i].depositAmount ==""){
					str = str + "<td width='80px' align='right'>\R0.0</td>";
				}else{
					str = str + "<td width='80px' align='right'>"+data[i].depositAmount+"</td>";
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
				
				str = str + "<td width='80px'>"+data[i].estimationCdAmount+"</td>";
				str = str + "<td style='display:none'>"+data[i].estimationDetailId+"</td>";
				str = str + "<td style='display:none'>"+data[i].invoiceDetailId+"</td>";
				str = str + "<td style='display:none'>"+data[i].customerId+"</td>";
				str = str + "</tr>";
			}
			Ctable.innerHTML = str;
			//alert("检索成功等待返回值！")
	   
		}
	});
}
//----------------------------发货一览表Find-----------------------------------------------
function receiveFind(){
	var year = document.getElementById('year').value; 
	var month=document.getElementById('mon').value; 
	var customerID=document.getElementById('customerID').value; 
	var customerIDName=document.getElementById('customerIDName').value; 
	if(customerIDName == "" ){
		alert("販売先名不能为空！");
		return false;
	}
	if(month<10){
		month = "0"+month;
	}
	var url1 = "ReceiveFindAction.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=JP";//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	var url2 = "ReceiveFindAction.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=US";
	var url3 = "ReceiveFindAction.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=CN";
	//alert(url);
	$.ajax({
		type:"POST",
		url:url1,
		async:"false",
		dataType:"json",
		error:function(data){
			alert("检索没有成功");
		}, 
		success:function(data){
			var Dtbody=document.getElementById("Dtbody");
//			var Dtbody4=document.getElementById("Dtbody4");
//			var Dtbody5=document.getElementById("Dtbody5");
			var str = "";
//			var str1 = "";
//			var str2 = "";
			for(var i =0;i<data.length;i++){
				if(data[i].content != "" && data[i].content != null){
					str = str + "<tr  align='center'>";
					str = str + "<td width='100px' >"+data[i].deliveryDate+"</td>";
					str = str + "<td width='120px'>"+data[i].orderNo+"</td>";
					str = str + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
					str = str + "<td width='180px'>"+data[i].content+"</td>";
					if(data[i].earningAmount == "" || data[i].earningAmount == null){
						str = str + "<td width='90px' align='right'></td>";
					}else{
						str = str + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
					}
					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
						str = str + "<td width='90px' align='right'></td>";
					}else{
						str = str + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
					}
					if(data[i].content != "" && data[i].content != null){
						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;'  type='text' value=''/></td>";
							
						}else{
							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
						}
					}else{
							str = str + "<td width='70px' ><input class='binput2' readonly='readonly' type='text' value='' /></td>";
					}
					if(data[i].endingBalance == "" || data[i].endingBalance == null){
						str = str + "<td width='110px' align='right'></td>";
					}else{
						str = str + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
					}
					
					
					if(data[i].orderNo != "" && data[i].orderNo != null){
						str = str + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
						str = str + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
					}else{
						str = str + "<td width='50px' ><input  class='cinput9' readonly='readonly' type='text' value='' /></td>";
						str = str + "<td width='100px'><input  class='dinput9' readonly='readonly' type='text' value='' /></td>";
					}
					if(data[i].estimationId == "" || data[i].estimationId == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].estimationId+"</td>";
					}
					if(data[i].detailId == "" || data[i].detailId == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
					}
					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
						str = str + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
					}else{
						str = str + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
					}
					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
					}
					if(data[i].currency == "" || data[i].currency == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].currency+"</td>";
					}
					str = str + "</tr>";
				}else{
					str = str + "<tr  align='center' class='aclass1'>";
					str = str + "<td width='100px' >"+data[i].deliveryDate+"</td>";
					str = str + "<td width='120px'>"+data[i].orderNo+"</td>";
					str = str + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
					str = str + "<td width='180px'>"+data[i].content+"</td>";
					if(data[i].earningAmount == "" || data[i].earningAmount == null){
						str = str + "<td width='90px' align='right'></td>";
					}else{
						str = str + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
					}
					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
						str = str + "<td width='90px' align='right'></td>";
					}else{
						str = str + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
					}
					if(data[i].orderNo != "" && data[i].orderNo != null){
						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;background-color:#C0C0C0;'  type='text' value=''/></td>";
							
						}else{
							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;background-color:#C0C0C0;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
						}
					}else{
							str = str + "<td width='70px' ><input class='binput2' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
					}
					if(data[i].endingBalance == "" || data[i].endingBalance == null){
						str = str + "<td width='110px' align='right'></td>";
					}else{
						str = str + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
					}
					
					
					if(data[i].orderNo != "" && data[i].orderNo != null){
						str = str + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;background-color:#C0C0C0;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
						str = str + "<td width='100px' class='aclass'><input  class='dinput' style='background-color:#C0C0C0;' type='text' value='"+data[i].note+"' /></td>";
					}else{
						str = str + "<td width='50px' ><input  class='cinput9' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
						str = str + "<td width='100px'><input  class='dinput9' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
					}
					if(data[i].estimationId == "" || data[i].estimationId == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].estimationId+"</td>";
					}
					if(data[i].detailId == "" || data[i].detailId == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
					}
					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
						str = str + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
					}else{
						str = str + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
					}
					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
					}
					if(data[i].currency == "" || data[i].currency == null){
						str = str + "<td style='display:none'></td>";
					}else{
						str = str + "<td style='display:none'>"+data[i].currency+"</td>";
					}
					str = str + "</tr>";
					
					
					
				}
//		if(data[i].currency == "JP" || data[i].currency==null){
//					str1 = str1 + "<tr  align='center'>";
//					str1 = str1 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
//					str1 = str1 + "<td width='120px'>"+data[i].orderNo+"</td>";
//					str1 = str1 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
//					str1 = str1 + "<td width='180px'>"+data[i].content+"</td>";
//					if(data[i].earningAmount == "" || data[i].earningAmount == null){
//						str1 = str1 + "<td width='90px' align='right'></td>";
//					}else{
//						str1 = str1 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str1 = str1 + "<td width='90px' align='right'></td>";
//					}else{
//						str1 = str1 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//							str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value=''/></td>";
//							
//						}else{
//							str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//						}
//					}else{
//							str1 = str1 + "<td width='70px' ><input class='binput2'  type='text' value='' /></td>";
//					}
//					if(data[i].endingBalance == "" || data[i].endingBalance == null){
//						str1 = str1 + "<td width='110px' align='right'></td>";
//					}else{
//						str1 = str1 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
//					}
//					
//					
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						str1 = str1 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//						str1 = str1 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
//					}else{
//						str1 = str1 + "<td width='50px' ><input  class='cinput9' type='text' value='' /></td>";
//						str1 = str1 + "<td width='100px'><input  class='dinput9'  type='text' value='' /></td>";
//					}
//					str1 = str1 + "<td style='display:none'>"+data[i].estimationId+"</td>";
//					str1 = str1 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//						str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
//					}else{
//						str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str1 = str1 + "<td style='display:none'></td>";
//					}else{
//						str1 = str1 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].currency == "" || data[i].currency == null){
//						str1 = str1 + "<td style='display:none'></td>";
//					}else{
//						str1 = str1 + "<td style='display:none'>"+data[i].currency+"</td>";
//					}
//					str1 = str1 + "</tr>";
//				}
//	if(data[i].currency == "JP" || data[i].currency==null){
//					str2 = str2 + "<tr  align='center'>";
//					str2 = str2 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
//					str2 = str2 + "<td width='120px'>"+data[i].orderNo+"</td>";
//					str2 = str2 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
//					str2 = str2 + "<td width='180px'>"+data[i].content+"</td>";
//					if(data[i].earningAmount == "" || data[i].earningAmount == null){
//						str2 = str2 + "<td width='90px' align='right'></td>";
//					}else{
//						str2 = str2 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str2 = str2 + "<td width='90px' align='right'></td>";
//					}else{
//						str2 = str2 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//							str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value=''/></td>";
//							
//						}else{
//							str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//						}
//					}else{
//							str2 = str2 + "<td width='70px' ><input class='binput2'  type='text' value='' /></td>";
//					}
//					if(data[i].endingBalance == "" || data[i].endingBalance == null){
//						str2 = str2 + "<td width='110px' align='right'></td>";
//					}else{
//						str2 = str2 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
//					}
//					
//					
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						str2 = str2 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//						str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
//					}else{
//						str2 = str2 + "<td width='50px' ><input  class='cinput9' type='text' value='' /></td>";
//						str2 = str2 + "<td width='100px'><input  class='dinput9'  type='text' value='' /></td>";
//					}
//					str2 = str2 + "<td style='display:none'>"+data[i].estimationId+"</td>";
//					str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//						str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
//					}else{
//						str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str2 = str2 + "<td style='display:none'></td>";
//					}else{
//						str2 = str2 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].currency == "" || data[i].currency == null){
//						str2 = str2 + "<td style='display:none'></td>";
//					}else{
//						str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
//					}
//					str2 = str2 + "</tr>";
//				}
			}
			Dtbody.innerHTML = str;
//			Dtbody4.innerHTML = str1;
//			Dtbody5.innerHTML = str2;
			changeReceiveAmountJP();
//			changeReceiveAmountUS();
//			changeReceiveAmountRMB();
			//document.getElementById('customerID').value = "";
	   
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
//			var Dtbody=document.getElementById("Dtbody");
			var Dtbody4=document.getElementById("Dtbody4");
//			var Dtbody5=document.getElementById("Dtbody5");
//			var str = "";
			var str1 = "";
//			var str2 = "";
			for(var i =0;i<data.length;i++){
//				if(data[i].currency == "JP" || data[i].currency==null ){
//					str = str + "<tr  align='center'>";
//					str = str + "<td width='100px'>"+data[i].deliveryDate+"</td>";
//					str = str + "<td width='120px'>"+data[i].orderNo+"</td>";
//					str = str + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
//					str = str + "<td width='180px'>"+data[i].content+"</td>";
//					if(data[i].earningAmount == "" || data[i].earningAmount == null){
//						str = str + "<td width='90px' align='right'></td>";
//					}else{
//						str = str + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str = str + "<td width='90px' align='right'></td>";
//					}else{
//						str = str + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;'  type='text' value=''/></td>";
//							
//						}else{
//							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//						}
//					}else{
//							str = str + "<td width='70px' ><input class='binput2'  type='text' value='' /></td>";
//					}
//					if(data[i].endingBalance == "" || data[i].endingBalance == null){
//						str = str + "<td width='110px' align='right'></td>";
//					}else{
//						str = str + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
//					}
//					
//					
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						str = str + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//						str = str + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
//					}else{
//						str = str + "<td width='50px' ><input  class='cinput9' type='text' value='' /></td>";
//						str = str + "<td width='100px'><input  class='dinput9'  type='text' value='' /></td>";
//					}
//					str = str + "<td style='display:none'>"+data[i].estimationId+"</td>";
//					str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
//					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//						str = str + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
//					}else{
//						str = str + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str = str + "<td style='display:none'></td>";
//					}else{
//						str = str + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].currency == "" || data[i].currency == null){
//						str = str + "<td style='display:none'></td>";
//					}else{
//						str = str + "<td style='display:none'>"+data[i].currency+"</td>";
//					}
//					str = str + "</tr>";
//				}
		if(data[i].content != "" && data[i].content != null){
				str1 = str1 + "<tr  align='center'>";
				str1 = str1 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
				str1 = str1 + "<td width='120px'>"+data[i].orderNo+"</td>";
				str1 = str1 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
				str1 = str1 + "<td width='180px'>"+data[i].content+"</td>";
				if(data[i].earningAmount == "" || data[i].earningAmount == null){
					str1 = str1 + "<td width='90px' align='right'></td>";
				}else{
					str1 = str1 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
				}
				if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
					str1 = str1 + "<td width='90px' align='right'></td>";
				}else{
					str1 = str1 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
				}
				if(data[i].content != "" && data[i].content != null){
					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
						str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value=''/></td>";
						
					}else{
						str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
					}
				}else{
						str1 = str1 + "<td width='70px' ><input class='binput2' readonly='readonly'  type='text' value='' /></td>";
				}
				if(data[i].endingBalance == "" || data[i].endingBalance == null){
					str1 = str1 + "<td width='110px' align='right'></td>";
				}else{
					str1 = str1 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
				}
				
				
				if(data[i].orderNo != "" && data[i].orderNo != null){
					str1 = str1 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
					str1 = str1 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
				}else{
					str1 = str1 + "<td width='50px' ><input  class='cinput9' readonly='readonly' type='text' value='' /></td>";
					str1 = str1 + "<td width='100px'><input  class='dinput9' readonly='readonly'  type='text' value='' /></td>";
				}
				if(data[i].estimationId == "" || data[i].estimationId == null){
					str1 = str1 + "<td style='display:none'></td>";
				}else{
					str1 = str1 + "<td style='display:none'>"+data[i].estimationId+"</td>";
				}
				if(data[i].detailId == "" || data[i].detailId == null){
					str1 = str1 + "<td style='display:none'></td>";
				}else{
					str1 = str1 + "<td style='display:none'>"+data[i].detailId+"</td>";
				}
				if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
					str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
				}else{
					str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
				}
				if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
					str1 = str1 + "<td style='display:none'></td>";
				}else{
					str1 = str1 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
				}
				if(data[i].currency == "" || data[i].currency == null){
					str1 = str1 + "<td style='display:none'></td>";
				}else{
					str1 = str1 + "<td style='display:none'>"+data[i].currency+"</td>";
				}
				str1 = str1 + "</tr>";
		}else{
			str1 = str1 + "<tr  align='center' class='aclass1'>";
			str1 = str1 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
			str1 = str1 + "<td width='120px'>"+data[i].orderNo+"</td>";
			str1 = str1 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
			str1 = str1 + "<td width='180px'>"+data[i].content+"</td>";
			if(data[i].earningAmount == "" || data[i].earningAmount == null){
				str1 = str1 + "<td width='90px' align='right'></td>";
			}else{
				str1 = str1 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
			}
			if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
				str1 = str1 + "<td width='90px' align='right'></td>";
			}else{
				str1 = str1 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
			}
			if(data[i].orderNo != "" && data[i].orderNo != null){
				if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
					str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value=''/></td>";
					
				}else{
					str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
				}
			}else{
					str1 = str1 + "<td width='70px' ><input class='binput2' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
			}
			if(data[i].endingBalance == "" || data[i].endingBalance == null){
				str1 = str1 + "<td width='110px' align='right'></td>";
			}else{
				str1 = str1 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
			}
			
			
			if(data[i].orderNo != "" && data[i].orderNo != null){
				str1 = str1 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
				str1 = str1 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
			}else{
				str1 = str1 + "<td width='50px' ><input  class='cinput9' style='background-color:#C0C0C0;' readonly='readonly' type='text' value='' /></td>";
				str1 = str1 + "<td width='100px'><input  class='dinput9' style='background-color:#C0C0C0;' readonly='readonly'  type='text' value='' /></td>";
			}
			if(data[i].estimationId == "" || data[i].estimationId == null){
				str1 = str1 + "<td style='display:none'></td>";
			}else{
				str1 = str1 + "<td style='display:none'>"+data[i].estimationId+"</td>";
			}
			if(data[i].detailId == "" || data[i].detailId == null){
				str1 = str1 + "<td style='display:none'></td>";
			}else{
				str1 = str1 + "<td style='display:none'>"+data[i].detailId+"</td>";
			}
			if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
				str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
			}else{
				str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
			}
			if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
				str1 = str1 + "<td style='display:none'></td>";
			}else{
				str1 = str1 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
			}
			if(data[i].currency == "" || data[i].currency == null){
				str1 = str1 + "<td style='display:none'></td>";
			}else{
				str1 = str1 + "<td style='display:none'>"+data[i].currency+"</td>";
			}
			str1 = str1 + "</tr>";
		}
//	if(data[i].currency == "JP" || data[i].currency==null){
//					str2 = str2 + "<tr  align='center'>";
//					str2 = str2 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
//					str2 = str2 + "<td width='120px'>"+data[i].orderNo+"</td>";
//					str2 = str2 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
//					str2 = str2 + "<td width='180px'>"+data[i].content+"</td>";
//					if(data[i].earningAmount == "" || data[i].earningAmount == null){
//						str2 = str2 + "<td width='90px' align='right'></td>";
//					}else{
//						str2 = str2 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str2 = str2 + "<td width='90px' align='right'></td>";
//					}else{
//						str2 = str2 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//							str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value=''/></td>";
//							
//						}else{
//							str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//						}
//					}else{
//							str2 = str2 + "<td width='70px' ><input class='binput2'  type='text' value='' /></td>";
//					}
//					if(data[i].endingBalance == "" || data[i].endingBalance == null){
//						str2 = str2 + "<td width='110px' align='right'></td>";
//					}else{
//						str2 = str2 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
//					}
//					
//					
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						str2 = str2 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//						str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
//					}else{
//						str2 = str2 + "<td width='50px' ><input  class='cinput9' type='text' value='' /></td>";
//						str2 = str2 + "<td width='100px'><input  class='dinput9'  type='text' value='' /></td>";
//					}
//					str2 = str2 + "<td style='display:none'>"+data[i].estimationId+"</td>";
//					str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//						str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
//					}else{
//						str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str2 = str2 + "<td style='display:none'></td>";
//					}else{
//						str2 = str2 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].currency == "" || data[i].currency == null){
//						str2 = str2 + "<td style='display:none'></td>";
//					}else{
//						str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
//					}
//					str2 = str2 + "</tr>";
//				}
			}
//			Dtbody.innerHTML = str;
			Dtbody4.innerHTML = str1;
//			Dtbody5.innerHTML = str2;
//			changeReceiveAmountJP();
			changeReceiveAmountUS();
//			changeReceiveAmountRMB();
			//document.getElementById('customerID').value = "";
	   
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
//			var Dtbody=document.getElementById("Dtbody");
//			var Dtbody4=document.getElementById("Dtbody4");
			var Dtbody5=document.getElementById("Dtbody5");
//			var str = "";
//			var str1 = "";
			var str2 = "";
			for(var i =0;i<data.length;i++){
//				if(data[i].currency == "JP" || data[i].currency==null ){
//					str = str + "<tr  align='center'>";
//					str = str + "<td width='100px'>"+data[i].deliveryDate+"</td>";
//					str = str + "<td width='120px'>"+data[i].orderNo+"</td>";
//					str = str + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
//					str = str + "<td width='180px'>"+data[i].content+"</td>";
//					if(data[i].earningAmount == "" || data[i].earningAmount == null){
//						str = str + "<td width='90px' align='right'></td>";
//					}else{
//						str = str + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str = str + "<td width='90px' align='right'></td>";
//					}else{
//						str = str + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;'  type='text' value=''/></td>";
//							
//						}else{
//							str = str + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryJP(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//						}
//					}else{
//							str = str + "<td width='70px' ><input class='binput2'  type='text' value='' /></td>";
//					}
//					if(data[i].endingBalance == "" || data[i].endingBalance == null){
//						str = str + "<td width='110px' align='right'></td>";
//					}else{
//						str = str + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
//					}
//					
//					
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						str = str + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//						str = str + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
//					}else{
//						str = str + "<td width='50px' ><input  class='cinput9' type='text' value='' /></td>";
//						str = str + "<td width='100px'><input  class='dinput9'  type='text' value='' /></td>";
//					}
//					str = str + "<td style='display:none'>"+data[i].estimationId+"</td>";
//					str = str + "<td style='display:none'>"+data[i].detailId+"</td>";
//					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//						str = str + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
//					}else{
//						str = str + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str = str + "<td style='display:none'></td>";
//					}else{
//						str = str + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].currency == "" || data[i].currency == null){
//						str = str + "<td style='display:none'></td>";
//					}else{
//						str = str + "<td style='display:none'>"+data[i].currency+"</td>";
//					}
//					str = str + "</tr>";
//				}
//		if(data[i].currency == "JP" || data[i].currency==null){
//					str1 = str1 + "<tr  align='center'>";
//					str1 = str1 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
//					str1 = str1 + "<td width='120px'>"+data[i].orderNo+"</td>";
//					str1 = str1 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
//					str1 = str1 + "<td width='180px'>"+data[i].content+"</td>";
//					if(data[i].earningAmount == "" || data[i].earningAmount == null){
//						str1 = str1 + "<td width='90px' align='right'></td>";
//					}else{
//						str1 = str1 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str1 = str1 + "<td width='90px' align='right'></td>";
//					}else{
//						str1 = str1 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//							str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value=''/></td>";
//							
//						}else{
//							str1 = str1 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryUSA1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//						}
//					}else{
//							str1 = str1 + "<td width='70px' ><input class='binput2'  type='text' value='' /></td>";
//					}
//					if(data[i].endingBalance == "" || data[i].endingBalance == null){
//						str1 = str1 + "<td width='110px' align='right'></td>";
//					}else{
//						str1 = str1 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
//					}
//					
//					
//					if(data[i].orderNo != "" && data[i].orderNo != null){
//						str1 = str1 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
//						str1 = str1 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
//					}else{
//						str1 = str1 + "<td width='50px' ><input  class='cinput9' type='text' value='' /></td>";
//						str1 = str1 + "<td width='100px'><input  class='dinput9'  type='text' value='' /></td>";
//					}
//					str1 = str1 + "<td style='display:none'>"+data[i].estimationId+"</td>";
//					str1 = str1 + "<td style='display:none'>"+data[i].detailId+"</td>";
//					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
//						str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
//					}else{
//						str1 = str1 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
//					}
//					if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
//						str1 = str1 + "<td style='display:none'></td>";
//					}else{
//						str1 = str1 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
//					}
//					if(data[i].currency == "" || data[i].currency == null){
//						str1 = str1 + "<td style='display:none'></td>";
//					}else{
//						str1 = str1 + "<td style='display:none'>"+data[i].currency+"</td>";
//					}
//					str1 = str1 + "</tr>";
//				}
	if(data[i].content != "" && data[i].content != null){
				str2 = str2 + "<tr  align='center'>";
				str2 = str2 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
				str2 = str2 + "<td width='120px'>"+data[i].orderNo+"</td>";
				str2 = str2 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
				str2 = str2 + "<td width='180px'>"+data[i].content+"</td>";
				if(data[i].earningAmount == "" || data[i].earningAmount == null){
					str2 = str2 + "<td width='90px' align='right'></td>";
				}else{
					str2 = str2 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
				}
				if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
					str2 = str2 + "<td width='90px' align='right'></td>";
				}else{
					str2 = str2 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
				}
				if(data[i].content != "" && data[i].content != null){
					if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
						str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value=''/></td>";
						
					}else{
						str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
					}
				}else{
						str2 = str2 + "<td width='70px' ><input class='binput2' readonly='readonly' type='text' value='' /></td>";
				}
				if(data[i].endingBalance == "" || data[i].endingBalance == null){
					str2 = str2 + "<td width='110px' align='right'></td>";
				}else{
					str2 = str2 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
				}
				
				
				if(data[i].orderNo != "" && data[i].orderNo != null){
					str2 = str2 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
					str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
				}else{
					str2 = str2 + "<td width='50px' ><input  class='cinput9' readonly='readonly' type='text' value='' /></td>";
					str2 = str2 + "<td width='100px'><input  class='dinput9' readonly='readonly' type='text' value='' /></td>";
				}
				if(data[i].estimationId == "" || data[i].estimationId == null){
					str2 = str2 + "<td style='display:none'></td>";
				}else{
					str2 = str2 + "<td style='display:none'>"+data[i].estimationId+"</td>";
				}
				if(data[i].detailId == "" || data[i].detailId == null){
					str2 = str2 + "<td style='display:none'></td>";
				}else{
					str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
				}
				if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
					str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
				}else{
					str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
				}
				if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
					str2 = str2 + "<td style='display:none'></td>";
				}else{
					str2 = str2 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
				}
				if(data[i].currency == "" || data[i].currency == null){
					str2 = str2 + "<td style='display:none'></td>";
				}else{
					str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
				}
				str2 = str2 + "</tr>";
			
		}else{
			str2 = str2 + "<tr  align='center' class='aclass1'>";
			str2 = str2 + "<td width='100px'>"+data[i].deliveryDate+"</td>";
			str2 = str2 + "<td width='120px'>"+data[i].orderNo+"</td>";
			str2 = str2 + "<td width='120px'>"+data[i].xinanOrderNo+"</td>";
			str2 = str2 + "<td width='180px'>"+data[i].content+"</td>";
			if(data[i].earningAmount == "" || data[i].earningAmount == null){
				str2 = str2 + "<td width='90px' align='right'></td>";
			}else{
				str2 = str2 + "<td width='90px' align='right'>"+data[i].earningAmount+"</td>";
			}
			if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
				str2 = str2 + "<td width='90px' align='right'></td>";
			}else{
				str2 = str2 + "<td width='90px' align='right'>"+data[i].receivedAmount+"</td>";
			}
			if(data[i].orderNo != "" && data[i].orderNo != null){
				if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
					str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value=''/></td>";
					
				}else{
					str2 = str2 + "<td width='70px' class='aclass'><input  class='binput1' onchange='changMoneryRMB1(this);' style='text-align:right;'  type='text' value='"+data[i].receiveAmount+"'/></td>";
				}
			}else{
					str2 = str2 + "<td width='70px' ><input class='binput2' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
			}
			if(data[i].endingBalance == "" || data[i].endingBalance == null){
				str2 = str2 + "<td width='110px' align='right'></td>";
			}else{
				str2 = str2 + "<td width='110px' align='right'>"+data[i].endingBalance+"</td>";
			}
			
			
			if(data[i].orderNo != "" && data[i].orderNo != null){
				str2 = str2 + "<td width='50px' class='aclass'><input class='cinput'  style='text-align:center;' value = '"+data[i].tradingNo+"' type='text'  /></td>";
				str2 = str2 + "<td width='100px' class='aclass'><input  class='dinput'  type='text' value='"+data[i].note+"' /></td>";
			}else{
				str2 = str2 + "<td width='50px' ><input  class='cinput9' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
				str2 = str2 + "<td width='100px'><input  class='dinput9' readonly='readonly' style='background-color:#C0C0C0;' type='text' value='' /></td>";
			}
			if(data[i].estimationId == "" || data[i].estimationId == null){
				str2 = str2 + "<td style='display:none'></td>";
			}else{
				str2 = str2 + "<td style='display:none'>"+data[i].estimationId+"</td>";
			}
			if(data[i].detailId == "" || data[i].detailId == null){
				str2 = str2 + "<td style='display:none'></td>";
			}else{
				str2 = str2 + "<td style='display:none'>"+data[i].detailId+"</td>";
			}
			if(data[i].receiveAmount == "" || data[i].receiveAmount == null){
				str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value=''/></td>";
			}else{
				str2 = str2 + "<td style='display:none'><input  class='binput'  type='text' value='"+data[i].receiveAmount+"'/></td>";
			}
			if(data[i].receivedAmount == "" || data[i].receivedAmount == null){
				str2 = str2 + "<td style='display:none'></td>";
			}else{
				str2 = str2 + "<td style='display:none'>"+data[i].receivedAmount+"</td>";
			}
			if(data[i].currency == "" || data[i].currency == null){
				str2 = str2 + "<td style='display:none'></td>";
			}else{
				str2 = str2 + "<td style='display:none'>"+data[i].currency+"</td>";
			}
			str2 = str2 + "</tr>";
		
			
			
		}
			}
//			Dtbody.innerHTML = str;
//			Dtbody4.innerHTML = str1;
			Dtbody5.innerHTML = str2;
//			changeReceiveAmountJP();
//			changeReceiveAmountUS();
			changeReceiveAmountRMB();
			//document.getElementById('customerID').value = "";
	   
		}
	});
	
}
//----------------------------納品済（売掛金）一覧Save-----------------------------------------------
function receivemoneySave(){
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	if(month<10){
		month = "0"+month;
	}
	var date = ""+year+ month;
	//画面表格内容取得
	var tbodyObj=document.getElementById("Ctable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var urlC="ReceiveMoneySaveAction.action?&date="+date+"&data=";
	for(var i=0;i<=rows.length-1;i++){
		//获得每一行
		var curRows=rows[i];
		var td11=curRows.getElementsByTagName("td")[0];
		//alert(td11.innerHTML);
//		var td12=curRows.getElementsByTagName("td")[12];
//		var input12a=td12.getElementsByTagName("input")[0].value;
//		var input12b=td12.getElementsByTagName("input")[1].value;
//		var input12c=td12.getElementsByTagName("input")[2].value;
//		var td13=curRows.getElementsByTagName("td")[13];
//		var input13=td13.getElementsByTagName("input")[0].value;
//		var td16=curRows.getElementsByTagName("td")[16];
//		var input16=td16.getElementsByTagName("input")[0].value;
//		urlC = urlC+input11+"#";
//		urlC = urlC+input12a+"|";
//		urlC = urlC+input12b+"|";
//		urlC = urlC+input12c+"#";
//		urlC = urlC+input13+"#";
//		urlC = urlC+input16+"$";
		
	}
		//alert(urlC);
		location.href = urlC;
	$.ajax({
			type:"POST",
			url:urlC,
			async:"false",
			dataType:"json",
			error:function(data){
				alert("添加失败");
			}, 
			success:function(data){
				alert("保存成功");
		   
			}
		});
}
//----------------------------出荷済（売掛金）統計一覧Download-----------------------------------------------
function downloadShipping(){
	var status = document.getElementById('receiveTag1').getAttribute('aria-selected'); 
	
	if(status=="true"){
		downloadShippingList();
	} else {
		downloadCustomerShipping();
	}
}

function downloadShippingList(){
	var year = document.getElementById('bayear').value; 
	var month=document.getElementById('bamon').value; 
	document.getElementById('Cyear').innerHTML = year; 
	document.getElementById('Cmon').innerHTML = month; 
	if(month<10){
		month = "0"+month;
	}
	//alert(date);
	var url = "xinan/downloadShipping.action?year="+year+"&month="+month;//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	window.open(url);
}
//----------------------------发货一览表Download-----------------------------------------------
function downloadCustomerShipping(){
	var year = document.getElementById('year').value; 
	var month=document.getElementById('mon').value; 
	var customerID=document.getElementById('customerID').value; 
	var customerIDName=document.getElementById('customerIDName').value; 
	if(month<10){
		month = "0"+month;
	}
	var url = "xinan/downloadCustomerShipping.action?year="+year+"&month="+month+"&customerID="+customerID+"&customerIDName="+customerIDName+"&Currency=JP";//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	window.open(url);
	
}
function receiveClean(){
	document.getElementById('customerID').value =""; 
	document.getElementById('customerIDName').value =""; 
	
}


//---------------------------------得意先別出荷済（売掛金）一覧SAVE---------------------------
function receiveSAVE(){
	
	var checkRole=document.getElementById("checkRole").value;
	if(checkRole != 3){
		alert("只有<<财务>>权限才能保存！");
		return false;
	}
	var tbodyObj=document.getElementById("Dtbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	var tbodyObj4=document.getElementById("Dtbody4");
	var rows4=tbodyObj4.getElementsByTagName("tr");
	var tbodyObj5=document.getElementById("Dtbody5");
	var rows5=tbodyObj5.getElementsByTagName("tr");
	var str = "";
	var str1 = "";
	var str2 = "";
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		//var t0=curRows.getElementsByTagName("td")[0];
		//var t1=curRows.getElementsByTagName("td")[1];
		var t2=curRows.getElementsByTagName("td")[2];
		var t4=curRows.getElementsByTagName("td")[4];
		var t5=curRows.getElementsByTagName("td")[5];
		var t6=curRows.getElementsByTagName("td")[6];
		var t7=curRows.getElementsByTagName("td")[7];
		var t8=curRows.getElementsByTagName("td")[8];
		var t9=curRows.getElementsByTagName("td")[9];
		var t10=curRows.getElementsByTagName("td")[10];
		var t11=curRows.getElementsByTagName("td")[11];
		var t12=curRows.getElementsByTagName("td")[12];
		var t14=curRows.getElementsByTagName("td")[14];
		var valuet2 = t2.innerHTML;
//		if(valuet2 == ""){
//			var valuet0 = t0.getElementsByTagName("input")[0].value;
//			var valuet1 = t1.getElementsByTagName("input")[0].value;
//			var valuet6 = t6.getElementsByTagName("input")[0].value;
//		}else{
//			var valuet0 = t0.innerHTML;
//			var valuet1 = t1.innerHTML;
//			var valuet6 = t6.innerHTML;
//		}
		var valuet4 = t4.innerHTML; 
		var valuet5 = t5.innerHTML; 
		var valuet6 = t6.getElementsByTagName("input")[0].value;
		var valuet7 = t7.innerHTML; 
		var valuet8 = t8.getElementsByTagName("input")[0].value;
		var valuet9 = t9.getElementsByTagName("input")[0].value;
		var valuet10 = t10.innerHTML; 
		var valuet11 = t11.innerHTML; 
		var valuet12 = t12.getElementsByTagName("input")[0].value; 
		var valuet14 = t14.innerHTML; 
		//str = str + valuet0 +"@";//0
		//str = str + valuet1 +"@";//1
		str = str + valuet2 +"@";//0  
		str = str + valuet4 +"@";//1
		str = str + valuet5 +"@";//2
		str = str + valuet6 +"@";//3
		str = str + valuet7 +"@";//4
		str = str + valuet8 +"@";//5
		str = str + valuet9 +"@";//6
		str = str + valuet10 +"@";//7
		str = str + valuet11 +"@";//8
		str = str + valuet12 +"@";//9
		str = str + valuet14 +"@";//10
		str = str + "end" +"FIN";
	}
	for(var i=0;i<rows4.length;i++){
		var curRows=rows4[i];
		//var t0=curRows.getElementsByTagName("td")[0];
		//var t1=curRows.getElementsByTagName("td")[1];
		var t2=curRows.getElementsByTagName("td")[2];
		var t4=curRows.getElementsByTagName("td")[4];
		var t5=curRows.getElementsByTagName("td")[5];
		var t6=curRows.getElementsByTagName("td")[6];
		var t7=curRows.getElementsByTagName("td")[7];
		var t8=curRows.getElementsByTagName("td")[8];
		var t9=curRows.getElementsByTagName("td")[9];
		var t10=curRows.getElementsByTagName("td")[10];
		var t11=curRows.getElementsByTagName("td")[11];
		var t12=curRows.getElementsByTagName("td")[12];
		var t14=curRows.getElementsByTagName("td")[14];
		var valuet2 = t2.innerHTML;
//		if(valuet2 == ""){
//			var valuet0 = t0.getElementsByTagName("input")[0].value;
//			var valuet1 = t1.getElementsByTagName("input")[0].value;
//			var valuet6 = t6.getElementsByTagName("input")[0].value;
//		}else{
//			var valuet0 = t0.innerHTML;
//			var valuet1 = t1.innerHTML;
//			var valuet6 = t6.innerHTML;
//		}
		var valuet4 = t4.innerHTML; 
		var valuet5 = t5.innerHTML; 
		var valuet6 = t6.getElementsByTagName("input")[0].value;
		var valuet7 = t7.innerHTML; 
		var valuet8 = t8.getElementsByTagName("input")[0].value;
		var valuet9 = t9.getElementsByTagName("input")[0].value;
		var valuet10 = t10.innerHTML; 
		var valuet11 = t11.innerHTML; 
		var valuet12 = t12.getElementsByTagName("input")[0].value; 
		var valuet14 = t14.innerHTML; 
		//str = str + valuet0 +"@";//0
		//str = str + valuet1 +"@";//1
		str1 = str1 + valuet2 +"@";//0  
		str1 = str1 + valuet4 +"@";//1
		str1 = str1 + valuet5 +"@";//2
		str1 = str1 + valuet6 +"@";//3
		str1 = str1 + valuet7 +"@";//4
		str1 = str1 + valuet8 +"@";//5
		str1 = str1 + valuet9 +"@";//6
		str1 = str1 + valuet10 +"@";//7
		str1 = str1 + valuet11 +"@";//8
		str1 = str1 + valuet12 +"@";//9
		str1 = str1 + valuet14 +"@";//10
		str1 = str1 + "end" +"FIN";
	}
	for(var i=0;i<rows5.length;i++){
		var curRows=rows5[i];
		//var t0=curRows.getElementsByTagName("td")[0];
		//var t1=curRows.getElementsByTagName("td")[1];
		var t2=curRows.getElementsByTagName("td")[2];
		var t4=curRows.getElementsByTagName("td")[4];
		var t5=curRows.getElementsByTagName("td")[5];
		var t6=curRows.getElementsByTagName("td")[6];
		var t7=curRows.getElementsByTagName("td")[7];
		var t8=curRows.getElementsByTagName("td")[8];
		var t9=curRows.getElementsByTagName("td")[9];
		var t10=curRows.getElementsByTagName("td")[10];
		var t11=curRows.getElementsByTagName("td")[11];
		var t12=curRows.getElementsByTagName("td")[12];
		var t14=curRows.getElementsByTagName("td")[14];
		var valuet2 = t2.innerHTML;
//		if(valuet2 == ""){
//			var valuet0 = t0.getElementsByTagName("input")[0].value;
//			var valuet1 = t1.getElementsByTagName("input")[0].value;
//			var valuet6 = t6.getElementsByTagName("input")[0].value;
//		}else{
//			var valuet0 = t0.innerHTML;
//			var valuet1 = t1.innerHTML;
//			var valuet6 = t6.innerHTML;
//		}
		var valuet4 = t4.innerHTML; 
		var valuet5 = t5.innerHTML; 
		var valuet6 = t6.getElementsByTagName("input")[0].value;
		var valuet7 = t7.innerHTML; 
		var valuet8 = t8.getElementsByTagName("input")[0].value;
		var valuet9 = t9.getElementsByTagName("input")[0].value;
		var valuet10 = t10.innerHTML; 
		var valuet11 = t11.innerHTML; 
		var valuet12 = t12.getElementsByTagName("input")[0].value; 
		var valuet14 = t14.innerHTML; 
		//str = str + valuet0 +"@";//0
		//str = str + valuet1 +"@";//1
		str2 = str2 + valuet2 +"@";//0  
		str2 = str2 + valuet4 +"@";//1
		str2 = str2 + valuet5 +"@";//2
		str2 = str2 + valuet6 +"@";//3
		str2 = str2 + valuet7 +"@";//4
		str2 = str2 + valuet8 +"@";//5
		str2 = str2 + valuet9 +"@";//6
		str2 = str2 + valuet10 +"@";//7
		str2 = str2 + valuet11 +"@";//8
		str2 = str2 + valuet12 +"@";//9
		str2 = str2 + valuet14 +"@";//10
		str2 = str2 + "end" +"FIN";
	}
	var url = "ReceiveMoneySaveAction.action?str="+str+str1+str2;
	//alert(url);
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
//日元
function changMoneryJP(e){
	//var t = stringToInt1("\\1231\$234234\,234234\R");
	//alert(t);
	//var str = "";
	//alert(toJP1(str));
	if(!validate1(e)){
		e.value = "";
	}else{
		//变换成日元
		var monery = parseFloat(e.value).toLocaleString();
		e.value="\\" + monery;
		changeReceiveAmountJP();
	}
}
function changMoneryUSA1(e){
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
	changeReceiveAmountUS();
}
function changMoneryRMB1(e){
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
	changeReceiveAmountRMB();
}
//当月回款入力触发事件JP
function changeReceiveAmountJP(){
	
	var tbodyObj=document.getElementById("Dtbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(123);
	var str = "";
	//alert(rows.length);
	var tr07 = rows[0].getElementsByTagName("td")[7].innerHTML;
	rows[0].getElementsByTagName("td")[7].innerHTML = toJP1(tr07);
	//var tr17 = rows[1].getElementsByTagName("td")[7].innerHTML;
	//rows[1].getElementsByTagName("td")[7].innerHTML = toJP1(tr17);
	for(var i=1;i<rows.length;i++){
		var curRows=rows[i];
		//内容t3
		var t3=curRows.getElementsByTagName("td")[3];//.innerHTML;
		var valuet3 = t3.innerHTML;
		//alert("v1"+valuet1);
		//新增销售t4
		var t4=curRows.getElementsByTagName("td")[4];//.innerHTML;
		var valuet4 = t4.innerHTML;
		////没有值的时候初始化
		if(valuet4 =="" || valuet4==null){
			valuet4 = 0;
		}else {
			t4.innerHTML = toJP1(valuet4);
		}
		//alert("v4"+valuet4);
		//回款金额t5
		var t5=curRows.getElementsByTagName("td")[5]//;.innerHTML;
		var valuet5 = t5.innerHTML;
		//没有值的时候初始化
		if(valuet5 =="" || valuet5==null){
			valuet5 = 0;
		}else {
			t5.innerHTML = toJP1(valuet5);
		}
		//alert("v5"+toJP1(valuet5));
		//回款金额隐藏行t13
		var t13=curRows.getElementsByTagName("td")[13]//;.innerHTML;
		var valuet13 = stringToInt1(t13.innerHTML);
		//alert(valuet13);
		if(valuet13=="" || valuet13==null){
			valuet13 = 0;
		}
		//alert("13v"+valuet13);
		//当月回款t6
		var t6=curRows.getElementsByTagName("td")[6].getElementsByTagName("input")[0];//.value;
		var valuet6 = stringToInt1(t6.value);
		if(valuet6=="" || valuet6==null){
			valuet6 = 0;
		}
		//alert("66v"+valuet6);
		//当月回款隐藏行t12
		var t12=curRows.getElementsByTagName("td")[12].getElementsByTagName("input")[0];//.value;
		var value12 = stringToInt1(t12.value);
		if(value12=="" || value12==null){
			value12 = 0;
		}
		//alert("12v"+value12);
		//期末余额
		var t7=curRows.getElementsByTagName("td")[7];//.innerHTML;
		var valuet7 = t7.innerHTML;
		if(valuet7=="" || valuet7==null){
			valuet7 = 0;
		}
		//alert("7v"+valuet7);
		//上一行期末余额
		var bvaluet7 = rows[i-1].getElementsByTagName("td")[7].innerHTML;
		if(bvaluet7=="" || bvaluet7==null){
			bvaluet7 = 0;
		}else{
			//rows[i-1].getElementsByTagName("td")[7].innerHTML = toJP1(bvaluet7);
		}
		//alert("bvaluet7-----"+bvaluet7);
		//alert(valuet1);
		if(valuet3 == "" || valuet3 == null){
			rows[i-1].getElementsByTagName("td")[7].innerHTML = toJP1(bvaluet7);
			t7.innerHTML = toJP1(bvaluet7);
		}else{
			//回款金额计算
			var JPHK = parseInt(valuet13) - parseInt(value12) + parseInt(valuet6);
			//var HK = t5.innerHTML;
			t5.innerHTML = "\\" + parseFloat(JPHK).toLocaleString();
			//期末余额
			t7.innerHTML = "\\" + parseFloat(parseInt(stringToInt1(bvaluet7)) + parseInt(stringToInt1(valuet4)) -  parseInt(JPHK)).toLocaleString();
		}
	}
	
	
	
	
	
}
//当月回款入力触发事件US
function changeReceiveAmountUS(){
	
	var tbodyObj=document.getElementById("Dtbody4");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(123);
	var str = "";
	//alert(rows.length);
	var tr07 = rows[0].getElementsByTagName("td")[7].innerHTML;
	rows[0].getElementsByTagName("td")[7].innerHTML = toUS1(tr07);
	//var tr17 = rows[1].getElementsByTagName("td")[7].innerHTML;
	//rows[1].getElementsByTagName("td")[7].innerHTML = toJP1(tr17);
	for(var i=1;i<rows.length;i++){
		var curRows=rows[i];
		//内容t3
		var t3=curRows.getElementsByTagName("td")[3];//.innerHTML;
		var valuet3 = t3.innerHTML;
		//alert("v1"+valuet1);
		//新增销售t4
		var t4=curRows.getElementsByTagName("td")[4];//.innerHTML;
		var valuet4 = t4.innerHTML;
		////没有值的时候初始化
		if(valuet4 =="" || valuet4==null){
			valuet4 = 0;
		}else {
			t4.innerHTML = toUS1(valuet4);
		}
		//alert("v4"+valuet4);
		//回款金额t5
		var t5=curRows.getElementsByTagName("td")[5]//;.innerHTML;
		var valuet5 = t5.innerHTML;
		//没有值的时候初始化
		if(valuet5 =="" || valuet5==null){
			valuet5 = 0;
		}else {
			t5.innerHTML = toUS1(valuet5);
		}
		//alert("v5"+toJP1(valuet5));
		//回款金额隐藏行t13
		var t13=curRows.getElementsByTagName("td")[13]//;.innerHTML;
		var valuet13 = stringToInt1(t13.innerHTML);
		if(valuet13=="" || valuet13==null){
			valuet13 = 0;
		}
		//alert("13v"+valuet13);
		//当月回款t6
		var t6=curRows.getElementsByTagName("td")[6].getElementsByTagName("input")[0];//.value;
		var valuet6 = stringToInt1(t6.value);
		if(valuet6=="" || valuet6==null){
			valuet6 = 0;
		}
		//alert("66v"+valuet6);
		//当月回款隐藏行t12
		var t12=curRows.getElementsByTagName("td")[12].getElementsByTagName("input")[0];//.value;
		var value12 = stringToInt1(t12.value);
		if(value12=="" || value12==null){
			value12 = 0;
		}
		//alert("12v"+value12);
		//期末余额
		var t7=curRows.getElementsByTagName("td")[7];//.innerHTML;
		var valuet7 = t7.innerHTML;
		if(valuet7=="" || valuet7==null){
			valuet7 = 0;
		}
		//alert("7v"+valuet7);
		//上一行期末余额
		var bvaluet7 = rows[i-1].getElementsByTagName("td")[7].innerHTML;
		if(bvaluet7=="" || bvaluet7==null){
			bvaluet7 = 0;
		}else{
			//bvaluet7 = stringToInt2(bvaluet7);
			//rows[i-1].getElementsByTagName("td")[7].innerHTML = toJP1(bvaluet7);
		}
		//alert("bvaluet7-----"+bvaluet7);
		//alert(valuet1);
		if(valuet3 == "" || valuet3 == null){
			rows[i-1].getElementsByTagName("td")[7].innerHTML = toUS1(bvaluet7);
			t7.innerHTML = toUS1(bvaluet7);
		}else{
			//回款金额计算
			var JPHK = parseFloat(valuet13) - parseFloat(value12) + parseFloat(valuet6);
			//var HK = t5.innerHTML;
			t5.innerHTML = "\$" + parseFloat(JPHK).toLocaleString();
			//期末余额
			t7.innerHTML = "\$" + parseFloat(parseFloat(stringToInt1(bvaluet7)) + parseFloat(stringToInt1(valuet4)) -  parseFloat(JPHK)).toLocaleString();
		}
	}
	
	
	
	
	
}
//当月回款入力触发事件RMB
function changeReceiveAmountRMB(){
	
	var tbodyObj=document.getElementById("Dtbody5");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(123);
	var str = "";
	//alert(rows.length);
	var tr07 = rows[0].getElementsByTagName("td")[7].innerHTML;
	rows[0].getElementsByTagName("td")[7].innerHTML = toRMB1(tr07);
	//var tr17 = rows[1].getElementsByTagName("td")[7].innerHTML;
	//rows[1].getElementsByTagName("td")[7].innerHTML = toJP1(tr17);
	for(var i=1;i<rows.length;i++){
		var curRows=rows[i];
		//内容t3
		var t3=curRows.getElementsByTagName("td")[3];//.innerHTML;
		var valuet3 = t3.innerHTML;
		//alert("v1"+valuet1);
		//新增销售t4
		var t4=curRows.getElementsByTagName("td")[4];//.innerHTML;
		var valuet4 = t4.innerHTML;
		////没有值的时候初始化
		if(valuet4 =="" || valuet4==null){
			valuet4 = 0;
		}else {
			t4.innerHTML = toRMB1(valuet4);
		}
		//alert("v4"+valuet4);
		//回款金额t5
		var t5=curRows.getElementsByTagName("td")[5]//;.innerHTML;
		var valuet5 = t5.innerHTML;
		//没有值的时候初始化
		if(valuet5 =="" || valuet5==null){
			valuet5 = 0;
		}else {
			t5.innerHTML = toRMB1(valuet5);
		}
		//alert("v5"+toJP1(valuet5));
		//回款金额隐藏行t13
		var t13=curRows.getElementsByTagName("td")[13]//;.innerHTML;
		var valuet13 = stringToInt1(t13.innerHTML);
		if(valuet13=="" || valuet13==null){
			valuet13 = 0;
		}
		//alert("13v"+valuet13);
		//当月回款t6
		var t6=curRows.getElementsByTagName("td")[6].getElementsByTagName("input")[0];//.value;
		var valuet6 = stringToInt1(t6.value);
		if(valuet6=="" || valuet6==null){
			valuet6 = 0;
		}
		//alert("66v"+valuet6);
		//当月回款隐藏行t12
		var t12=curRows.getElementsByTagName("td")[12].getElementsByTagName("input")[0];//.value;
		var value12 = stringToInt1(t12.value);
		if(value12=="" || value12==null){
			value12 = 0;
		}
		//alert("12v"+value12);
		//期末余额
		var t7=curRows.getElementsByTagName("td")[7];//.innerHTML;
		var valuet7 = t7.innerHTML;
		if(valuet7=="" || valuet7==null){
			valuet7 = 0;
		}
		//alert("7v"+valuet7);
		//上一行期末余额
		var bvaluet7 = rows[i-1].getElementsByTagName("td")[7].innerHTML;
		if(bvaluet7=="" || bvaluet7==null){
			bvaluet7 = 0;
		}else{
			//rows[i-1].getElementsByTagName("td")[7].innerHTML = toJP1(bvaluet7);
		}
		//alert("bvaluet7-----"+bvaluet7);
		//alert(valuet1);
		if(valuet3 == "" || valuet3 == null){
			rows[i-1].getElementsByTagName("td")[7].innerHTML = toRMB1(bvaluet7);
			t7.innerHTML = toRMB1(bvaluet7);
		}else{
			//回款金额计算
			var JPHK = parseFloat(valuet13) - parseFloat(value12) + parseFloat(valuet6);
			//var HK = t5.innerHTML;
			t5.innerHTML = "\R" + parseFloat(JPHK).toLocaleString();
			//期末余额
			t7.innerHTML = "\R" + parseFloat(parseFloat(stringToInt1(bvaluet7)) + parseFloat(stringToInt1(valuet4)) -  parseFloat(JPHK)).toLocaleString();
		}
	}
	
	
	
	
	
}
//字符串变成日元
function toJP1(e){
		//判断是否为空
	//alert(e);
	if(e == 0){
		return 0;
	}else if(e == "" || e == null){
		return "";
	}else{
		//去掉特殊符号
		var JPM1 = stringToInt1(e);
		// 变成String 为 int
		var JPM2 = parseInt(JPM1);
		//变成日元
		var JPM3 = "\\" + parseFloat(JPM2).toLocaleString();
		return JPM3;
	}
}
//字符串变成美元
function toUS1(e){
		//判断是否为空
	//alert(e);
	if(e == 0){
		return "\$0.00";
	}else if(e == "" || e == null){
		return "";
	}else{
		//去掉特殊符号
		var JPM1 = stringToInt1(e);
		// 变成String 为 int
		var JPM2 = parseInt(JPM1);
		//变成美元
		var JPM3 = "\$" + parseFloat(JPM2).toLocaleString()+".00";
		return JPM3;
	}
}
//字符串变成RMB
function toRMB1(e){
		//判断是否为空
	//alert(e);
	if(e == 0){
		return "\R0.00";
	}else if(e == "" || e == null){
		return "";
	}else{
		//去掉特殊符号
		var JPM1 = stringToInt1(e);
		// 变成String 为 int
		var JPM2 = parseInt(JPM1);
		//变成RMB
		var JPM3 = "\R" + parseFloat(JPM2).toLocaleString()+".00";
		return JPM3;
	}
}
//日元转换成数字
function JPtoInt(e){
	if(e == "" || e == null){
		return "";
	}else{
		var JPI1 = stringToInt1(e);
		var JPI2 = parseInt(JPI1);
		return JPI2;
	}
}
//美元
function changMoneryUS1(e){
	if(!validate(e)){
		e.value = "";
	}else{
		//变换成日元
		var monery = parseFloat(e.value).toLocaleString();
		var str = monery.split(".");
		//alert(str.length);
		if(str.length==1){
			e.value="$" + monery +".00";
		}else{
			e.value="$" + monery;
		}
	}
}


function stringToInt1(str){
	if(str==0){
		return 0;
	}
	str = str.replace("%","");
	str = str.replace("\\","");
	str = str.replace("\R","");
	str = str.replace("\$","");
	str = str.split(",").join('');
	return str;
		
}
function stringToInt2(str){
	if(str==0){
		return 0;
	}
	str = str.replace("%","");
	str = str.replace("\\","");
	str = str.replace("\R","");
	str = str.replace("\$","");
	str = str.replace(".","");
	str = str.split(",").join('');
	return str;
		
}
