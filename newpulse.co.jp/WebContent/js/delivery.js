
//----------------------------------------------EMS SHEET ADD END----------------------------------------
function invoiceLoad(){
	invoiceSum1();
	packageSum1()
//		// 页面浮动面板
//		$("#floatPanel a.arrow").eq(0).click(function(){
//			$("html,body").animate({scrollTop :0}, 300);
//			return false;
//		});
//		$("#floatPanel a.arrow").eq(1).click(function(){
//			$("html,body").animate({scrollTop : $(document).height()}, 300);
//			return false;
//		});
		//页面切换
		$( "#tabs" ).tabs();
		//InvoiceCD
		var InvoiceCD = document.getElementById('InvoiceCD').value;
		var way = document.getElementById('way').value;
		//invoiceNo
		document.getElementById('invoiceNo').value = InvoiceCD;
		//packageNo
		document.getElementById('pinvoiceNo').innerHTML = InvoiceCD;
		//Declaration
		document.getElementById('dinvoiceNo').innerHTML = InvoiceCD;
		//arriveWayd
		var arrive = document.getElementById('arrive').value;
		if(arrive=="CIF"){
			document.getElementById('arriveWay').value = arrive + "  ANQING";
		}
		if(arrive=="FOB"){
			document.getElementById('arriveWay').value = arrive + "  TOKYO";
		}
		
		//document.getElementById('arriveWay').value = document.getElementById('arrive').value + "  JAPAN";
		
		
		
		//客户信息加载invoice
		document.getElementById('enShortName').value = document.getElementById('EnShortName').value;
		document.getElementById('enName').value = document.getElementById('EnName').value;
		document.getElementById('enAddress2').value = document.getElementById('EnAddress1').value + document.getElementById('EnAddress2').value;
		document.getElementById('enAddress3').value = document.getElementById('EnAddress3').value;
		document.getElementById('tel1').value = "TEL:"+document.getElementById('Tel1').value +"    "+"FAX:"+document.getElementById('Fax').value;
		//客户信息加载invoice1
		document.getElementById('enShortName1').value = document.getElementById('EnShortName').value;
		document.getElementById('enName1').value = document.getElementById('EnName').value;
		document.getElementById('enAddress21').value = document.getElementById('EnAddress1').value + document.getElementById('EnAddress2').value;
		document.getElementById('enAddress31').value = document.getElementById('EnAddress3').value;
		document.getElementById('tel11').value = "TEL:"+document.getElementById('Tel1').value +"    "+"FAX:"+document.getElementById('Fax').value;
		//客户信息加载invoice2
		if(way=="A"){
			document.getElementById('enShortName2').value = "DELIVER TO:ZHANG YUNXIA";
			document.getElementById('enName2').value = "SINOTRANS AIR TRANSPORTAION";
			document.getElementById('enAddress22').value = "DEVELOPMENT CO.,LTD.ANHUI BRANCH";
			document.getElementById('enAddress32').value = "TEL:0551-63431581 EXT.8008";
			document.getElementById('tel12').value = "FAX:0551-63446340";
		} else {
			document.getElementById('enShortName2').value = "";
			document.getElementById('enName2').value = "SAME AS CONSIGNEE";
			document.getElementById('enAddress22').value = "";
			document.getElementById('enAddress32').value = "";
			document.getElementById('tel12').value = "";
		}
		//客户信息加载package
		document.getElementById('enShortNameP').value = document.getElementById('EnShortName').value;
		document.getElementById('enNameP').value = document.getElementById('EnName').value;
		document.getElementById('enAddress2P').value = document.getElementById('EnAddress1').value + document.getElementById('EnAddress2').value;
		document.getElementById('enAddress3P').value = document.getElementById('EnAddress3').value;
		document.getElementById('tel1P').value = "TEL:"+document.getElementById('Tel1').value +"    "+"FAX:"+document.getElementById('Fax').value;
		//客户信息加载package1
		document.getElementById('enShortNameP1').value = document.getElementById('EnShortName').value;
		document.getElementById('enNameP1').value = document.getElementById('EnName').value;
		document.getElementById('enAddress2P1').value = document.getElementById('EnAddress1').value + document.getElementById('EnAddress2').value;
		document.getElementById('enAddress3P1').value = document.getElementById('EnAddress3').value;
		document.getElementById('tel1P1').value = "TEL:"+document.getElementById('Tel1').value +"    "+"FAX:"+document.getElementById('Fax').value;
		//客户信息加载package2
		if(way=="A"){
			document.getElementById('enShortNameP2').value = "DELIVER TO:ZHANG YUNXIA";
			document.getElementById('enNameP2').value = "SINOTRANS AIR TRANSPORTAION";
			document.getElementById('enAddress2P2').value = "DEVELOPMENT CO.,LTD.ANHUI BRANCH";
			document.getElementById('enAddress3P2').value = "TEL:0551-63431581 EXT.8008";
			document.getElementById('tel1P2').value = "FAX:0551-63446340";
		} else {
			document.getElementById('enShortNameP2').value = "";
			document.getElementById('enNameP2').value = "SAME AS CONSIGNEE";
			document.getElementById('enAddress2P2').value = "";
			document.getElementById('enAddress3P2').value = "";
			document.getElementById('tel1P2').value = "";
		}
		//Marks加载
		//var x= document.getElementsByName('MenShortName').length;
		//for(var k=0;k<x;k++){
			document.getElementById('marksP').innerHTML = document.getElementById('EnShortName').value + "\n" +document.getElementById('InvoiceCD').value;
			document.getElementById('marksI').innerHTML = document.getElementById('EnShortName').value + "\n" +document.getElementById('InvoiceCD').value;
			//document.getElementsByName('MNO')[k].value = document.getElementById('InvoiceCD').value;
			
		//}
		//PALLET加载
		document.getElementById('PALLET').innerHTML = document.getElementById('PalletQuantity').value;
		//DQuantity加载
		document.getElementById('DQuantity').innerHTML = document.getElementById('Gw').value + "   "+document.getElementById('GwUnit').value;
		//DCommodity加载
		var tbodyObj=document.getElementById("invoiceTab");
		var rows=tbodyObj.getElementsByTagName("tr");
		var curRows=rows[0];
		var tdprice=curRows.getElementsByTagName("td")[0];
		document.getElementById('DCommodity').innerHTML = tdprice.getElementsByTagName("textarea")[0].innerHTML;
		
		//承认按钮隐藏
		document.getElementById('approveI').style="display:none";
		
		
		if(way=="S"|| way=="A"){
			document.getElementById('EMSTab').style.display="none";
		}else{
			document.getElementById('declarationTab').style.display="none";
		}
		
		var Currency = document.getElementById('Currency').value;
		if(Currency == "JP"){
			document.getElementById('moneytype').innerHTML="\\";
		}else if (Currency == "US"){
			document.getElementById('moneytype').innerHTML="\$";
		}else if (Currency == "CN"){
			document.getElementById('moneytype').innerHTML="\R";
		}
		
		if(way=="S"){
			document.getElementById('ShippingType1').value = "";
			document.getElementById('ShippedBy').value = "";
		}
		
		if(way=="E"){
			document.getElementById('ShippingType1').value = "EMS";
			document.getElementById('ShippedBy').value = "EMS";
		}
		
		star();
		EMS();
		//alert();
}
//invoice修改初始化
function invoiceLoadA(){
	$( "#tabs" ).tabs();
	//document.getElementById('print').getElementsByTagName('input').style="background-color:white";
	//$('input[type=text]').style="border:1px solid;";
	//区分页面是修改还是承认还是表示
	var kind = document.getElementById('kind').value;
	
	//alert(1123);
	invoiceSum1();
	packageSum1();
	//页面切换
	var InvoiceCd = document.getElementById('InvoiceCD').value;
	var CustomerId = document.getElementById('CustomerId').value;
	var CustomerName = document.getElementById('CustomerName').value;
	var Address1 = document.getElementById('Address1').value;
	var Address2 = document.getElementById('Address2').value;
	var Address3 = document.getElementById('Address3').value;
	var Tel = document.getElementById('Tel').value;
	var Fax = document.getElementById('Fax').value;
	var Title = document.getElementById('Title').value;
	var Amount = document.getElementById('Amount').value;
	var DeliveryType = document.getElementById('DeliveryType').value;
	var ShippingType = document.getElementById('ShippingType').value;
	var Currency = document.getElementById('Currency').value;
	var OrderNo = document.getElementById('OrderNo').value;
	var ReceiptNo = document.getElementById('ReceiptNo').value;
	var Receiver = document.getElementById('Receiver').value;
	var ShippingCompany = document.getElementById('ShippingCompany').value;
	var ShippingFrom = document.getElementById('ShippingFrom').value;
	var ShippingTo = document.getElementById('ShippingTo').value;
	var IssueDate = document.getElementById('IssueDate').value;
	if(IssueDate != ""){
		var s1 = IssueDate.substring(0,4);
		var s2 = IssueDate.substring(4,6);
		var s3 = IssueDate.substring(6,8);
		IssueDate = s1+"-"+s2+"-"+s3;
	}
	var Notify = document.getElementById('Notify').value;
	var Marks = document.getElementById('Marks').value;
	var PackingListMarks = document.getElementById('PackingListMarks').value;
	var Quantity = document.getElementById('Quantity').value;
	var Unit = document.getElementById('Unit').value;
	var UserId = document.getElementById('UserId').value;
	var ShippingDate = document.getElementById('ShippingDate').value;
	
	if(ShippingDate != ""){
		var s1 = ShippingDate.substring(0,4);
		var s2 = ShippingDate.substring(4,6);
		var s3 = ShippingDate.substring(6,8);
		ShippingDate = s1+"-"+s2+"-"+s3;
	}
	
	var nw = document.getElementById('nw').value;
	var gw = document.getElementById('gw').value;
	var m3 = document.getElementById('m3').value;
	var palletQuantity = document.getElementById('palletQuantity').value;
	//-------------package unit
	var NGM1 = document.getElementById('NGM').value;
	var NGMArray = new Array();
	NGMArray = NGM1.split("@");
	document.getElementById('QuantityPUnit').value = NGMArray[0];
	document.getElementById('NwUnit').value = NGMArray[1];
	document.getElementById('GwUnit').value = NGMArray[2];
	document.getElementById('M3Unit').value = NGMArray[3];
	packageSum1();
	//页面sheet页显示方式
	var wayKind = new Array();
	wayKind = InvoiceCd.split("-");
	var way = wayKind[1].substr(0,1);
	if(way=="S"|| way=="A"){
		document.getElementById('EMSTab').style.display="none";
	}else{
		document.getElementById('declarationTab').style.display="none";
	}
	//invoice sheet页里面填值
	document.getElementById('enShortName').value = CustomerName;
	//document.getElementById('enShortName').style="background-color:white;";
	//document.getElementById('enShortName').readOnly="true";
	document.getElementById('enName').value = Address1;
	document.getElementById('enAddress2').value = Address2;
	document.getElementById('enAddress3').value = Address3;
	document.getElementById('tel1').value = Tel;
	document.getElementById('invoiceNo').value = InvoiceCd;
	document.getElementById('pinvoiceNo').innerHTML = InvoiceCd;
	document.getElementById('dinvoiceNo').innerHTML = InvoiceCd;
	
	document.getElementById('itoday').value = IssueDate;
	
	document.getElementById("ptoday").value = IssueDate;
	document.getElementById("dtoday").value = IssueDate;
	
	document.getElementById('LCNOIN').value = ReceiptNo;
	document.getElementById('pLCNO').value = ReceiptNo;
	
	document.getElementById('lcday').value = Title;
	document.getElementById('plcday').value = Title;
	
	
	
	document.getElementById('isday').value = Receiver;
	document.getElementById('disday').value = Receiver;
	
	document.getElementById('ContractNo').value = OrderNo;
	document.getElementById('ContractNO').value = OrderNo;
	
	document.getElementById('ShippingType1').value = ShippingCompany;
	document.getElementById('ShippedBy').value = ShippingCompany;

	document.getElementById('ShippingType').value = ShippingType;
	document.getElementById('saday').value = ShippingDate;
	document.getElementById('dsaday').value = ShippingDate;
	
	
	document.getElementById('invoiceFROM').value = ShippingFrom;
	document.getElementById('invoiceTO').value = ShippingTo;
	document.getElementById('enShortName1').value = CustomerName;
	document.getElementById('enName1').value = Address1;
	document.getElementById('enAddress21').value = Address2;
	document.getElementById('enAddress31').value = Address3;
	document.getElementById('tel11').value = Tel;
	if(way=="A"){
		document.getElementById('enShortName2').value = "DELIVER TO:ZHANG YUNXIA";
		document.getElementById('enName2').value = "SINOTRANS AIR TRANSPORTAION";
		document.getElementById('enAddress22').value = "DEVELOPMENT CO.,LTD.ANHUI BRANCH";
		document.getElementById('enAddress32').value = "TEL:0551-63431581 EXT.8008";
		document.getElementById('tel12').value = "FAX:0551-63446340";
	} else {
		document.getElementById('enShortName2').value = "";
		document.getElementById('enName2').value = "SAME AS CONSIGNEE";
		document.getElementById('enAddress22').value = "";
		document.getElementById('enAddress32').value = "";
		document.getElementById('tel12').value = "";
	}
	document.getElementById('marksI').innerHTML = Marks;
	document.getElementById('sumKGS').value = Unit;
	document.getElementById('sumMONEY').value = Amount;
	document.getElementById('arriveWay').value = DeliveryType;
	
	//document.getElementById('QuantityP').value = Quantity;
	document.getElementById('Nw').value = nw;
	document.getElementById('Gw').value = gw;
	document.getElementById('M3').value = m3;
	
	document.getElementById('enShortNameP').value = CustomerName;
	document.getElementById('enNameP').value = Address1;
	document.getElementById('enAddress2P').value = Address2;
	document.getElementById('enAddress3P').value = Address3;
	document.getElementById('tel1P').value = Tel;
	
	
	document.getElementById('enShortNameP1').value = CustomerName;
	document.getElementById('enNameP1').value = Address1;
	document.getElementById('enAddress2P1').value = Address2;
	document.getElementById('enAddress3P1').value = Address3;
	document.getElementById('tel1P1').value = Tel;
	//alert(2);
	if(way=="A"){
		document.getElementById('enShortNameP2').value = "DELIVER TO:ZHANG YUNXIA";
		document.getElementById('enNameP2').value = "SINOTRANS AIR TRANSPORTAION";
		document.getElementById('enAddress2P2').value = "DEVELOPMENT CO.,LTD.ANHUI BRANCH";
		document.getElementById('enAddress3P2').value = "TEL:0551-63431581 EXT.8008";
		document.getElementById('tel1P2').value = "FAX:0551-63446340";
	} else {
		document.getElementById('enShortNameP2').value = "";
		document.getElementById('enNameP2').value = "SAME AS CONSIGNEE";
		document.getElementById('enAddress2P2').value = "";
		document.getElementById('enAddress3P2').value = "";
		document.getElementById('tel1P2').value = "";
	}
	document.getElementById('marksP').innerHTML = PackingListMarks;
	
	document.getElementById('PALLET').innerHTML = document.getElementById('PalletQuantity').value;
	//DQuantity加载
	document.getElementById('DQuantity').innerHTML = document.getElementById('Gw').value + "   "+document.getElementById('GwUnit').value;
	//DCommodity加载
	var tbodyObj=document.getElementById("invoiceTab");
	var rows=tbodyObj.getElementsByTagName("tr");
	var curRows=rows[0];
	var tdprice=curRows.getElementsByTagName("td")[0];
	document.getElementById('DCommodity').innerHTML = tdprice.getElementsByTagName("textarea")[0].innerHTML;
	
	document.getElementById('approveI').style="display:none";
	
	
	var Currency = document.getElementById('Currency').value;
	if(Currency == "JP"){
		document.getElementById('moneytype').innerHTML="\\";
	}else if (Currency == "US"){
		document.getElementById('moneytype').innerHTML="\$";
	}else if (Currency == "CN"){
		document.getElementById('moneytype').innerHTML="\R";
	}
	
	
	if(kind=="show" || kind=="approve"){
		//初始化时背景颜色调整
		document.getElementById('invoiceNo').style="background-color:white;";
		document.getElementById('enShortName').style="background-color:white;";
		document.getElementById('enName').style="background-color:white;";
		document.getElementById('enAddress2').style="background-color:white;";
		document.getElementById('enAddress3').style="background-color:white;";
		document.getElementById('tel1').style="background-color:white;";
		
		document.getElementById('itoday').style="background-color:white;border:0px;";
		
		document.getElementById("ptoday").style="background-color:white;border:0px;";
		document.getElementById("dtoday").style="background-color:white;border:0px;font-size:15px;font-weight:bold;";
		
		document.getElementById('LCNOIN').style="background-color:white;border:0px;";
		document.getElementById('lcday').style="background-color:white;border:0px;";
		
		document.getElementById('isday').style="background-color:white;border:0px;";
		document.getElementById('ContractNo').style="background-color:white;border:0px;";
		document.getElementById('ShippingType1').style="background-color:white;border:0px;";
		document.getElementById('saday').style="background-color:white;border:0px;";
		document.getElementById('invoiceFROM').style="background-color:white;border:0px;";
		document.getElementById('invoiceTO').style="background-color:white;border:0px;";
		document.getElementById('enShortName1').style="background-color:white;border:0px;";
		document.getElementById('enName1').style="background-color:white;border:0px;";
		document.getElementById('enAddress21').style="background-color:white;border:0px;";
		document.getElementById('enAddress31').style="background-color:white;border:0px;";
		document.getElementById('tel11').style="background-color:white;";
		document.getElementById('enShortName2').style="background-color:white;";
		document.getElementById('enName2').style="background-color:white;";
		document.getElementById('enAddress22').style="background-color:white;";
		document.getElementById('enAddress32').style="background-color:white;";
		document.getElementById('tel12').style="background-color:white;";
		document.getElementById('marksI').style="background-color:white;";
		document.getElementById('sumKGS').style="background-color:white;";
		document.getElementById('sumMONEY').style="background-color:white;";
		document.getElementById('arriveWay').style="background-color:white;border:0px;";
		document.getElementById('arriveWay').style="background-color:white;border:0px;";
		
		
		document.getElementById('QuantityP').style="background-color:white;text-align:right;width:40px;height:20px;border:0px solid;font-size:13px;";
//		document.getElementById('Nw').style="background-color:white;";
//		document.getElementById('Gw').style="background-color:white;";
//		document.getElementById('M3').style="background-color:white;";
		
		document.getElementById('enShortNameP').style="background-color:white;";
		document.getElementById('enNameP').style="background-color:white;";
		document.getElementById('enAddress2P').style="background-color:white;";
		document.getElementById('enAddress3P').style="background-color:white;";
		document.getElementById('tel1P').style="background-color:white;";
		
		
		document.getElementById('enShortNameP1').style="background-color:white;";
		document.getElementById('enNameP1').style="background-color:white;";
		document.getElementById('enAddress2P1').style="background-color:white;";
		document.getElementById('enAddress3P1').style="background-color:white;";
		document.getElementById('tel1P1').style="background-color:white;";
		//alert(2);
		document.getElementById('enShortNameP2').style="background-color:white;";
		document.getElementById('enNameP2').style="background-color:white;";
		document.getElementById('enAddress2P2').style="background-color:white;";
		document.getElementById('enAddress3P2').style="background-color:white;";
		document.getElementById('tel1P2').style="background-color:white;";
		//document.getElementById('DCommodity').style="background-color:white;border:0px";
		document.getElementById('unitType').style="background-color:white;border:0px";
		document.getElementById('marksP').style="background-color:white;border:0px";
		document.getElementById('PalletQuantity').style="background-color:white;border:0px";
		document.getElementById('QuantityPUnit').style="background-color:white;border:0px;text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;";
		document.getElementById('NwUnit').style="background-color:white;border:0px;text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;";
		document.getElementById('GwUnit').style="background-color:white;border:0px;text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;";
		document.getElementById('M3Unit').style="background-color:white;border:0px;text-align:right;width:80px;height:20px;border:0px solid;font-size:13px;";
		
		//初始化只读属性
		document.getElementById('invoiceNo').readOnly="true";
		document.getElementById('enShortName').readOnly="true";
		document.getElementById('enName').readOnly="true";
		document.getElementById('enAddress2').readOnly="true";
		document.getElementById('enAddress3').readOnly="true";
		document.getElementById('tel1').readOnly="true";
		
		
		document.getElementById('itoday').readOnly="true";
		
		document.getElementById("ptoday").readOnly="true";
		document.getElementById("dtoday").readOnly="true";
		
		document.getElementById('LCNOIN').readOnly="true";
		document.getElementById('lcday').readOnly="true";
		
		document.getElementById('isday').readOnly="true";
		document.getElementById('ContractNo').readOnly="true";
		document.getElementById('ShippingType1').readOnly="true";
		document.getElementById('saday').readOnly="true";
		document.getElementById('invoiceFROM').readOnly="true";
		document.getElementById('invoiceTO').readOnly="true";
		document.getElementById('enShortName1').readOnly="true";
		document.getElementById('enName1').readOnly="true";
		document.getElementById('enAddress21').readOnly="true";
		document.getElementById('enAddress31').readOnly="true";
		document.getElementById('tel11').readOnly="true";
		document.getElementById('enShortName2').readOnly="true";
		document.getElementById('enName2').readOnly="true";
		document.getElementById('enAddress22').readOnly="true";
		document.getElementById('enAddress32').readOnly="true";
		document.getElementById('tel12').readOnly="true";
		document.getElementById('sumKGS').readOnly="true";
		document.getElementById('sumMONEY').readOnly="true";
		document.getElementById('arriveWay').readOnly="true";
		document.getElementById('marksP').readOnly="true";
		document.getElementById('PalletQuantity').readOnly="true";
		document.getElementById('QuantityPUnit').readOnly="true";
		document.getElementById('NwUnit').readOnly="true";
		document.getElementById('GwUnit').readOnly="true";
		document.getElementById('M3Unit').readOnly="true";
		
		document.getElementById('QuantityP').readOnly="true";
//		document.getElementById('Nw').readOnly="true";
//		document.getElementById('Gw').readOnly="true";
//		document.getElementById('M3').readOnly="true";
		
		document.getElementById('enShortNameP').readOnly="true";
		document.getElementById('enNameP').readOnly="true";
		document.getElementById('enAddress2P').readOnly="true";
		document.getElementById('enAddress3P').readOnly="true";
		document.getElementById('tel1P').readOnly="true";
		
		
		document.getElementById('enShortNameP1').readOnly="true";
		document.getElementById('enNameP1').readOnly="true";
		document.getElementById('enAddress2P1').readOnly="true";
		document.getElementById('enAddress3P1').readOnly="true";
		document.getElementById('tel1P1').readOnly="true";
		//alert(2);
		document.getElementById('enShortNameP2').readOnly="true";
		document.getElementById('enNameP2').readOnly="true";
		document.getElementById('enAddress2P2').readOnly="true";
		document.getElementById('enAddress3P2').readOnly="true";
		document.getElementById('tel1P2').readOnly="true";
		document.getElementById('unitType').readOnly="true";
		
		
		
		var tbodyObj=document.getElementById("invoiceTab");
		var rows=tbodyObj.getElementsByTagName("tr");
		for(var i=0;i<rows.length;i++){
			var curRows=rows[i];
			curRows.getElementsByTagName("td")[0].getElementsByTagName("textarea")[0].style="background-color:white;border:0px";
			curRows.getElementsByTagName("td")[0].getElementsByTagName("textarea")[0].readOnly="true";
			curRows.getElementsByTagName("td")[1].getElementsByTagName("input")[0].style="background-color:white;border:0px";
			curRows.getElementsByTagName("td")[1].getElementsByTagName("input")[0].readOnly="true";
			curRows.getElementsByTagName("td")[2].getElementsByTagName("input")[0].style="background-color:white;border:0px";
			curRows.getElementsByTagName("td")[2].getElementsByTagName("input")[0].readOnly="true";
		}
		
		var tbodyObj=document.getElementById("packageTab1");
		var rows=tbodyObj.getElementsByTagName("tr");
		for(var i=0;i<rows.length;i++){
			var curRows=rows[i];
			curRows.getElementsByTagName("td")[0].getElementsByTagName("textarea")[0].style="background-color:white;border:0px";
			curRows.getElementsByTagName("td")[0].getElementsByTagName("textarea")[0].readOnly="true";
			curRows.getElementsByTagName("td")[1].getElementsByTagName("input")[0].style="background-color:white;text-align:right;width:80px;height:20px;border:0px;font-size:13px";
			curRows.getElementsByTagName("td")[1].getElementsByTagName("input")[0].readOnly="true";
			curRows.getElementsByTagName("td")[2].getElementsByTagName("input")[0].style="background-color:white;text-align:right;width:80px;height:20px;border:0px;font-size:13px";
			curRows.getElementsByTagName("td")[2].getElementsByTagName("input")[0].readOnly="true";
			curRows.getElementsByTagName("td")[3].getElementsByTagName("input")[0].style="background-color:white;text-align:right;width:80px;height:20px;border:0px;font-size:13px";
			curRows.getElementsByTagName("td")[3].getElementsByTagName("input")[0].readOnly="true";
			curRows.getElementsByTagName("td")[4].getElementsByTagName("input")[0].style="background-color:white;text-align:right;width:80px;height:20px;border:0px;font-size:13px";
			curRows.getElementsByTagName("td")[4].getElementsByTagName("input")[0].readOnly="true";
		}
		
		
		
		if(kind=="approve"){
			document.getElementById('user-sealI').style="display:none";
			document.getElementById('SaveInvoice').style="display:none";
			document.getElementById('approveI').style="display:true";
		}
		if(kind=="change"){
			document.getElementById('approveI').style="display:none";
		}
		if(kind=="show"){
			document.getElementById('SaveInvoice').style="display:none";
			document.getElementById('approveI').style="display:none";
		}
		
		
		var Currency = document.getElementById('Currency').value;
		if(Currency == "JP"){
			document.getElementById('moneytype').innerHTML="\\";
		}else if (Currency == "US"){
			document.getElementById('moneytype').innerHTML="\$";
		}else if (Currency == "CN"){
			document.getElementById('moneytype').innerHTML="\R";
		}
		if(kind=="show"){
			document.getElementById('user-sealI').style="display:true";
			document.getElementById('user-sealI1').style="display:true";
			document.getElementById('user-sealI2').style="display:true";
			document.getElementById('approveI').style="display:none";
			document.getElementById('SaveInvoice').style="display:none";
		}
//		$.ajax({
//			type:"POST",
//			url:"xinan/approveEstimation.action?",
//			async:"true",
//			data:urlstr,
//			dataType:"text",
//			error:function(){
////				alert("approve失败");
//			}, 
//			success:function(data){
////				alert("approve成功");
////				document.getElementById("maxseal").style.display="block";
////				document.getElementById("maxbt").style.display="none";
//				document.getElementById("maxApprove").innerHTML = 
//					"<img width='60px' height='60px' id='maxseal'  src='xinan/userseal.action?userId="+UserId+"' />"
//			}
//		});
		
		
		
		
		
	}
	EMS();
	
//	if(way=="S"){
//		document.getElementById('ShippingType1').value = "";
//		document.getElementById('ShippedBy').value = "";
//	}
	
//	if(way=="E"){
//		document.getElementById('ShippingType1').value = "EMS";
//		document.getElementById('ShippedBy').value = "EMS";
//	}

}
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
	document.getElementById("itoday").value=time;
	document.getElementById("ptoday").value=time;
	document.getElementById("dtoday").value=time;
	//document.getElementById("saday").value=time;
	//document.getElementById("dsaday").value=time;
	
}
//=====================================changeLCNO联动处理=========================
//InvoiceNO联动
function changeInvoiceNo(e){
	e.style.border="0px solid";
	document.getElementById('pinvoiceNo').innerHTML = e.value;
	document.getElementById('dinvoiceNo').innerHTML = e.value;
}
//L/C&nbsp;No.联动
function changeLCNO(e){
	e.style.border="0px solid";
	document.getElementById("pLCNO").value = e.value;
}
//ContractNO联动
function changeContractNo(e){
	e.style.border="0px solid";
	document.getElementById("ContractNO").value = e.value;
}
//FROM联动
function changeFROM(e){
	e.style.border="0px solid";
	document.getElementById("packageFROM").value = e.value;
	var arrive = document.getElementById('arrive').value;
	if(arrive=="FOB"){
		document.getElementById('arriveWay').value = arrive + "  "+e.value;
	}
}
//TO联动
function changeTO(e){
	e.style.border="0px solid";
	document.getElementById("packageTO").value = e.value;
	var arrive = document.getElementById('arrive').value;
	if(arrive=="CIF"){
		document.getElementById('arriveWay').value = arrive + "  "+e.value;
	}
}
//ShippedBy联动
function changeShippedBy(e){
	e.style.border="0px solid";
	document.getElementById("ShippedBy").value = e.value;
}
//enShortName联动
function enShortNamechange(e){
	document.getElementById("enShortNameP").value = e.value;
}
function enShortNamechange1(e){
	document.getElementById("enShortNameP1").value = e.value;
}
function enShortNamechange2(e){
	document.getElementById("enShortNameP2").value = e.value;
}
//enName联动
function enNamechange(e){
	document.getElementById("enNameP").value = e.value;
}
function enNamechange1(e){
	document.getElementById("enNameP1").value = e.value;
}
function enNamechange2(e){
	document.getElementById("enNameP2").value = e.value;
}
//enAddress2联动
function enAddress2change(e){
	document.getElementById("enAddress2P").value = e.value;
}
function enAddress2change1(e){
	document.getElementById("enAddress2P1").value = e.value;
}
function enAddress2change2(e){
	document.getElementById("enAddress2P2").value = e.value;
}
//enAddress3联动
function enAddress3change(e){
	document.getElementById("enAddress3P").value = e.value;
}
function enAddress3change1(e){
	document.getElementById("enAddress3P1").value = e.value;
}
function enAddress3change2(e){
	document.getElementById("enAddress3P2").value = e.value;
}
//tel1联动
function tel1change(e){
	document.getElementById("tel1P").value = e.value;
}
function tel1change1(e){
	document.getElementById("tel1P1").value = e.value;
}

function tel1change2(e){
	document.getElementById("tel1P2").value = e.value;
}
//PALLET联动
function palletChange(e){
	document.getElementById("PALLET").innerHTML = e.value;
}
//DQuantityChange联动
function DQuantityChange(e){
	document.getElementById("DQuantity").innerHTML = document.getElementById("Gw").value + "   " + e.value;
}
function DQuantityChange1(e){
	packageSum(e);
	document.getElementById("DQuantity").innerHTML = document.getElementById("Gw").value + "   " + document.getElementById("GwUnit").value;
}
//Invoicename联动
function changeInvoicename(e){
	e.style.border="0px solid";
	document.getElementById('DCommodity').innerHTML = e.value;
}

//检查是否为同一个公司
function check(){
		//return true;
	//alert(123);
	var flg = 1;
	var tbodyObj=document.getElementById("ctbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	//alert(rows.length);
	//checkbox
	var coll = document.getElementsByName("deliveryall");
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		var couName=curRows.getElementsByTagName("td")[0].innerHTML;
		var currency=curRows.getElementsByTagName("td")[16].innerHTML;
		var ORDERID=curRows.getElementsByTagName("td")[14].innerHTML;
		var checkedLanOld="";
		var checkedLanNow="";
		//alert(couName);
		//alert("i="+i);
		if(coll[i].checked){
			flg = 2;
			for(var j=i;j<rows.length;j++){
				//alert("j="+j);
				if(coll[j].checked){
					var curRows1=rows[j];
					var couName1=curRows1.getElementsByTagName("td")[0].innerHTML;
					var currency1=curRows1.getElementsByTagName("td")[16].innerHTML;
				//	var orderId1=curRows1.getElementsByTagName("td")[14].innerHTML;
					if(couName != couName1){
					alert("不相同的公司不能invoice作成");
					return false;
					}
					if(currency != currency1){
						alert("不相同货币种类不能invoice作成");
						return false;
					}
					document.getElementById("Currency").value = currency;
//					if(orderId != orderId1){
//						alert("不相同注文商品不能invoice作成");
//						return false;
//					}
					if(checkedLanOld == ""){
						checkedLanOld = rows[i].getElementsByTagName("td")[11].innerHTML;
					}
					checkedLanNow = rows[i].getElementsByTagName("td")[11].innerHTML;
					if(checkedLanOld != checkedLanNow){
						alert("币种不一致,请重新选折");
						return false;	
					}
				}
			}
		}
		
	};
	if(flg == 2){
		return true;
	}else {
		alert("至少选择一条!");
		return false;}
}


////---------当月配送一览検索---------
//function deliveryFind(){
//	var nowdate = new Date();
//	var year = nowdate.getFullYear();
//	var month = nowdate.getMonth() + 1;
//	if(month<10){
//		month = "0"+month;
//	}
//	var date = ""+year+ month;
//	var customerName = $("#customerName").val();
//	var customerOrder = $("#customerOrder").val();
////	alert(customerName);
////	alert(customerOrder);
////	alert(year);
////	alert(mon);
//	var url = "";
//	url = url + "xinan/deliveryallLoad.action?";
//	url = url + "&customerName=" + customerName;
//	url = url + "&customerOrder=" + customerOrder;
//	url = url + "&date=" + date+"&now="+(new Date()).valueOf();
//	location.href = url;
//}



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
	var url = "xinan/downloadTrading.action?date="+date+"&now="+(new Date()).valueOf();
	window.open(url);
	
}
//----------------------------納品済（売掛金）一覧Load-----------------------------------------------
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
//	var receivemoneyyear = document.getElementById('receivemoneyyear').value; 
//	var receivemoneymm = document.getElementById('receivemoneymm').value; 
//  if(receivemoneymm<10){
//		receivemoneymm = "0"+receivemoneymm;
//	}
	//var date = ""+year+ month;
	//alert(date);
	var url = "ReceiveMoneyFindAction.action?year="+year+"&month="+month;//+"&receivemoneyyear="+receivemoneyyear+"&receivemoneymm="+receivemoneymm+"&now="+(new Date()).valueOf();
	//alert(url);
	//location.href = url;
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
				str = str + "<tr  align='center'>";
				str = str + "<td width='200px'>"+data[i].customerName+"</td>";
				str = str + "<td width='80px'>"+data[i].categoryType+"</td>";
				str = str + "<td width='80px'>"+data[i].sellAmountOfThisMonth+"</td>";
				str = str + "<td width='80px'>"+data[i].depositAmount+"</td>";
				str = str + "<td width='80px'>"+data[i].balance+"</td>";
				str = str + "<td width='80px'>"+data[i].balanceOfDollar+"</td>";
				str = str + "<td width='80px'>"+data[i].balanceOfChinese+"</td>";
				str = str + "<td width='80px'>"+data[i].estimationCdAmount+"</td>";
				str = str + "<td style='display:none'>"+data[i].estimationDetailId+"</td>";
				str = str + "<td style='display:none'>"+data[i].invoiceDetailId+"</td>";
				str = str + "<td style='display:none'>"+data[i].customerId+"</td>";
				str = str + "</tr>";
			}
			
			
			
			
			
			str = str + "<tr  align='center'>";
			str = str + "<td width='200px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td width='80px'>"+data[i]+"</td>";
			str = str + "<td style='display:none'>"+data[i]+"</td>";
			str = str + "<td style='display:none'>"+data[i]+"</td>";
			str = str + "<td style='display:none'>"+data[i]+"</td>";
			str = str + "</tr>";
			
			
			
			Ctable.innerHTML = str;
			//alert(data.length);
			alert("检索成功等待返回值！")
	   
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

//--------------------------------------------------------------------------
//回车根据名字检索coustemID
function receivekeydown(evt){
	 evt = (evt) ? evt : ((window.event) ? window.event : "");
      var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
      var e=document.getElementById("customerIDName").value;
      if (keyCode == 13) {
    	  $.ajax({
				type:"POST",
				url:"xinan/customeraddress.action?enShortName="+e,
				async:"false",
				dataType:"json",
				success:function(data){
					$("#customerIDName").val(data[0].cnName);
					$("#customerID").val(data[0].customerId);
				}
    	  });
			
		
      }
}
//失去焦点根据名字检索coustemID
function receivefindCoustemID(evt) {
		      var e=document.getElementById("customerIDName").value;
		      if (e.trim() ==  "")
		    	  return;
		    	  $.ajax({
						type:"POST",
						url:"xinan/customeraddress.action?enShortName="+e,
						async:"false",
						dataType:"json",
						error:function(){
							
							
						},  
						success:function(data){
							$("#customerIDName").val(data[0].cnName);
							$("#customerID").val(data[0].customerId);
						}
		    	  });
 }
//--------------------------------------------------------------------------
//回车根据名字检索supplierID
function paykeydown(evt){
	 evt = (evt) ? evt : ((window.event) ? window.event : "");
    var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
    var e=document.getElementById("customerIDName").value;
    if (keyCode == 13) {
  	  $.ajax({
				type:"POST",
				url:"xinan/supplierAddress.action?enShortName="+e,
				async:"false",
				dataType:"json",
				success:function(data){
					$("#customerIDName").val(data[0].cnName);
					$("#customerID").val(data[0].supplierId);
				}
  	  });
			
		
    }
}
//失去焦点根据名字检索supplierID
function payfindCoustemID(evt) {
		      var e=document.getElementById("customerIDName").value;
              if (e.trim() == "")
                 return;
		    	  $.ajax({
						type:"POST",
						url:"xinan/supplierAddress.action?enShortName="+e,
						async:"false",
						dataType:"json",
						error:function(){
						},  
						success:function(data){
							$("#customerIDName").val(data[0].cnName);
							$("#customerID").val(data[0].supplierId);
						}
		    	  });
}



	
//--------------------Invoiceall一覧Download-----------------------
function downloadInvoice(){
	var invoiceCD = document.getElementById("INVOICECD").value;
	var customerID = document.getElementById("customerID").value;
	//var customerNO = document.getElementById("customerno").value;
	var InForm = document.getElementById("inform").value;
	var InTo = document.getElementById("into").value;
	var url="xinan/downloadInvoice.action?";
	url = url + "&invoiceCD=" + invoiceCD;
	url = url + "&customerID=" + customerID;
	//url = url + "&customerNO=" + customerNO;
	url = url + "&InForm=" + InForm;
	url = url + "&InTo=" + InTo;
	window.open(url);
}
