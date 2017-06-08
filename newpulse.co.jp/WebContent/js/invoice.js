//invoice 初始化

function adjustH( textarea ){
	  var textarea = textarea;
	  textarea.style.overflow='hidden';
	  var defaultHeight = textarea.offsetHeight;

	    textarea.style.height = defaultHeight+'px';
	    var tmp_sh = textarea.scrollHeight;
	    while( tmp_sh > textarea.scrollHeight ){
	      tmp_sh = textarea.scrollHeight;
	      textarea.scrollHeight ++;
	    }
	    if( textarea.scrollHeight > textarea.offsetHeight ){
	      textarea.style.height = textarea.scrollHeight+'px';
	    }
}

function LoadInvoice(){
	var KIND = document.getElementById('KIND').value;
	if(KIND=="FlagCreatI"){
		//alert("当月配送一览");
		invoiceLoad();
	}else{
		//alert("invoice配送一览");
		invoiceLoadA();
//		if(kind=="approve"){
//		}
//		if(kind=="change"){
//			invoiceLoadC();
//		}
//		if(kind=="show"){
//			invoiceLoadS();
//		}
	}
	
	
}
//--------------------------------------invoice SAVE -------------------
function invoiceSAVE(){
	//---------------------invoice star------------------------
	
	var CustomerId = $("#CustomerId").val();
	//first
	var InvoiceCD = document.getElementById('invoiceNo').value;
	var oldInvoiceCD = $("#InvoiceCD").val();
	if(InvoiceCD != oldInvoiceCD) {
		checkInvoiceCD(InvoiceCD);
	} else {
		invoiceSaveSheet();
	}
}

function invoiceSaveSheet(){
	//---------------------invoice star------------------------
	
	var CustomerId = $("#CustomerId").val();
	//first
	var InvoiceCD = document.getElementById('invoiceNo').value;
	var oldInvoiceCD = $("#InvoiceCD").val();
	//change
	//var InvoiceCD = $("#InvoiceCd").val();
	var InvoiceID = $("#InvoiceID").val();
	var PackingListId = $("#PackingListId").val();
	var IssueDate = $("#itoday").val();
	var CustomerName = $("#enShortName").val();
	var Address1 = $("#enName").val();
	var Address2 = $("#enAddress2").val();
	var Address3 = $("#enAddress3").val();
	var Tel = $("#tel1").val();
	var ShippingType = $("#arriveWay").val();
	var ShippingDate = $("#saday").val();
	var DeliveryType = $("#arriveWay").val();
	//alert(DeliveryType);
	var ShippingFrom = $("#invoiceFROM").val();
	var ShippingTo = $("#invoiceTO").val();
	var OrderNo = $("#ContractNo").val();
	var ReceiptNo = $("#LCNOIN").val();
	var Title = $("#lcday").val();
	var Receiver = $("#isday").val();
	var orderId = $("#orderId").val();
	var MarksI =$("#marksI").val();
	var MarksP =$("#marksP").val();
	
	//ShippingCompany
	var CustomerName1 = $("#enShortName1").val();
	var Address11 = $("#enName1").val();
	var Address21 = $("#enAddress21").val();
	var Address31 = $("#enAddress31").val();
	var Tel1 = $("#tel11").val();
	//var ShippingCompany = CustomerName1 + "@;"+Address11+"@;"+Address21+"@;"+Address31+"@;"+Tel1;
	var ShippingCompany = $("#ShippingType1").val();
	//Notify
	var CustomerName2 = $("#enShortName2").val();
	var Address12 = $("#enName2").val();
	var Address22 = $("#enAddress22").val();
	var Address32 = $("#enAddress32").val();
	var Tel2 = $("#tel12").val();
	var Notify = CustomerName2 + "@;"+Address12+"@;"+Address22+"@;"+Address32+"@;"+Tel2;
	
	var Quantity =document.getElementById('sumKGS').innerHTML;
	//alert(Quantity);
	var Unit = $("#unitType").val();
	var Amount = $("#sumMONEY").val();
	if(Amount==""){
		Amount = 0;
	}
	var Currency = $("#Currency").val();
	
	
	//---------------------package---------------------------
	var QuantityPUnit = $("#QuantityPUnit").val();
	var NwUnit = $("#NwUnit").val();
	var GwUnit = $("#GwUnit").val();
	var M3Unit = $("#M3Unit").val();
	
	
	//---------------------invoice end------------------------
	//---------------------package star-----------------------
	
	
	var PalletQuantity = $("#PalletQuantity").val();
	var QuantityP = $("#QuantityP").val();
	var Nw = $("#Nw").val();
	var Gw = $("#Gw").val();
	var M3 = $("#M3").val();
	
	//---------------------package end------------------------
	var urlstr="&InvoiceCD="+InvoiceCD;
	urlstr=urlstr+"&InvoiceID="+InvoiceID;
	urlstr=urlstr+"&PackingListId="+PackingListId;
	urlstr=urlstr+"&IssueDate="+IssueDate;
	urlstr=urlstr+"&CustomerName="+CustomerName;
	urlstr=urlstr+"&Address1="+Address1;
	urlstr=urlstr+"&Address2="+Address2;
	urlstr=urlstr+"&Address3="+Address3;
	urlstr=urlstr+"&Tel="+Tel;
	urlstr=urlstr+"&ShippingType="+ShippingType;
	urlstr=urlstr+"&ShippingDate="+ShippingDate;
	urlstr=urlstr+"&DeliveryType="+DeliveryType;
	urlstr=urlstr+"&ShippingFrom="+ShippingFrom;
	urlstr=urlstr+"&ShippingTo="+ShippingTo;
	urlstr=urlstr+"&OrderNo="+OrderNo;
	urlstr=urlstr+"&ReceiptNo="+ReceiptNo;
	urlstr=urlstr+"&Title="+Title;
	urlstr=urlstr+"&Receiver="+Receiver;
	urlstr=urlstr+"&ShippingCompany="+ShippingCompany;
	urlstr=urlstr+"&Notify="+Notify;
	urlstr=urlstr+"&Quantity="+Quantity;
	urlstr=urlstr+"&Unit="+Unit;
	urlstr=urlstr+"&Amount="+Amount;
	urlstr=urlstr+"&Currency="+Currency;
	urlstr=urlstr+"&PalletQuantity="+PalletQuantity;
	urlstr=urlstr+"&QuantityP="+QuantityP;
	urlstr=urlstr+"&Nw="+Nw;
	urlstr=urlstr+"&Gw="+Gw;
	urlstr=urlstr+"&M3="+M3;
	urlstr=urlstr+"&MarksI="+encodeURI(MarksI);
	urlstr=urlstr+"&MarksP="+encodeURI(MarksP);
	urlstr=urlstr+"&orderId="+orderId;
	urlstr=urlstr+"&CustomerId="+CustomerId;
	urlstr=urlstr+"&NGM="+QuantityPUnit+"@"+NwUnit+"@"+GwUnit+"@"+M3Unit;
	
	//alert(urlstr);
    $.ajax({
		type:"post",
		url:"xinan/saveInvoicePAction.action?"+urlstr,
		async:"true",
		//data:urlstr,
		dataType:"json",
		error:function(data){
			alert("保存失败！");
		},
		success: function(data){
			//alert(data.InvoiceID);
			//alert(data.PackingListId);
			$("#InvoiceID").val(data.InvoiceID);
			$("#PackingListId").val(data.PackingListId);
			detailSave(data.InvoiceID,data.PackingListId);
			
		}
	});
	
}
//--------------------------------------invoice detail SAVE -------------------
function detailSave(e,evt){
	var tbodyObj=document.getElementById("invoiceTab");
	var rows=tbodyObj.getElementsByTagName("tr");
	var tbodyObj1=document.getElementById("packageTab1");
	var rows1=tbodyObj1.getElementsByTagName("tr");
	var url="xinan/saveInDteailAction.action?InvoiceID="+e+"&PackingListId="+evt+"&str=";
	var params = "";
	for(var i=0;i<rows.length;i++){
		//------invoive----------
		var curRows=rows[i];
		var p=curRows.getElementsByTagName("td")[0];
		var p1=curRows.getElementsByTagName("td")[1];
		var p2=curRows.getElementsByTagName("td")[2];
		var p3=curRows.getElementsByTagName("td")[3];
		var p4=curRows.getElementsByTagName("td")[4];
		var p5=curRows.getElementsByTagName("td")[5];
		var textArea = p.getElementsByTagName("textarea")[0].value;
		var q = p1.getElementsByTagName("input")[0].value;
		var up = p2.getElementsByTagName("input")[0].value;
		var ta1 = p3.getElementsByTagName("input")[0].value;
//		var tamount = new Array();
//		tamount = ta1.split(",");
//		var ta="";
//		for(var j=0;j<tamount.length;j++){
//			ta = ta + tamount[j];
//		}
		//itemId
		var itemId = p4.getElementsByTagName("input")[0].value;
		var ModelCd = p5.getElementsByTagName("input")[0].value;
		//-------package-------
		var curRows1=rows1[i];
		var pack1=curRows1.getElementsByTagName("td")[0];
		var pack2=curRows1.getElementsByTagName("td")[1];
		var pack3=curRows1.getElementsByTagName("td")[2];
		var pack4=curRows1.getElementsByTagName("td")[3];
		var pack5=curRows1.getElementsByTagName("td")[4];
		var pack6=curRows1.getElementsByTagName("td")[5];
		var pack7=curRows1.getElementsByTagName("td")[6];
		var packV1 = pack1.getElementsByTagName("textarea")[0].value;
		var packV2 = pack2.getElementsByTagName("input")[0].value;
		var packV3 = pack3.getElementsByTagName("input")[0].value;
		var packV4 = pack4.getElementsByTagName("input")[0].value;
		var packV5 = pack5.getElementsByTagName("input")[0].value;
		//itemId
		var packV6 = pack6.getElementsByTagName("input")[0].value;
		var packV7 = pack7.getElementsByTagName("input")[0].value;
		params = params + textArea + "FLG"+ q + "FLG"+ up + "FLG"+ ta1+"FLG"+itemId+"FLG"+packV1+ "FLG"+packV2+ "FLG"+packV3+ "FLG"+packV4+ "FLG"+packV5+"FLG"+packV6+"FLG"+ModelCd+"FLG"+packV7+"FLG"+0+ "SPLIT";
	};
	url = url + encodeURI(params);
	 $.ajax({
			type:"post",
			url:url,
			async:"true",
			dataType:"json",
			error:function(data){
				alert("保存失败！");
			},
			success: function(data){
				alert("保存成功！");
				
			}
		});
	//alert(url);
}


//---------------------------invoice SUM-------------------------
//总计合计
function invoiceSum(e){
	e.style.border="0px";
	var Currency=document.getElementById("Currency").value;
	if(Currency == "JP"){
		if(!validate1(e)){
			e.value = "";
		}
	}else{
		if(!validate(e)){
			e.value = "";
		}
	}
	
	var tbodyObj=document.getElementById("invoiceTab");
	var rows=tbodyObj.getElementsByTagName("tr");
	var sumMONEY = document.getElementById("sumMONEY");
	var sumKGS = document.getElementById("sumKGS");
	var trsum=0;
	var trkgs = 0;
	for(var i=0;i<rows.length;i++){
		//总计
		var curRows=rows[i];
		var tdprice=curRows.getElementsByTagName("td")[1];
		var tdprice1=curRows.getElementsByTagName("td")[2];
		var tdprice2=curRows.getElementsByTagName("td")[3];
		var tinput=stringToInt1(tdprice.getElementsByTagName("input")[0].value);
		var tinput1=stringToInt1(tdprice1.getElementsByTagName("input")[0].value);
		//tinput.numeric({decimal:true});
		//tinput1.numeric({decimal:true});
		if(""==tinput.trim()){
			tinput=0;
		}
		if(""==tinput1.trim()){
			tinput1=0;
		}
		var tdsum= parseFloat(tinput1)* parseFloat(tinput);
		if(trsum==""){
			trsum=0;
		}
		tdprice1.getElementsByTagName("input")[0].value = parseFloat(tinput1).toLocaleString();
		trsum=parseFloat(trsum)+parseFloat(tdsum);
		trkgs = parseFloat(trkgs) + parseFloat(tinput);
		sumKGS.innerHTML = parseFloat(trkgs).toLocaleString();
		
		var s=parseFloat(tdsum).toLocaleString();
		if(s==0){
			s="";
		}
		tdprice2.getElementsByTagName("input")[0].value=s;
		sumMONEY.innerHTML=parseFloat(trsum).toLocaleString();
	};
	
}
function invoiceSum1(){
	var tbodyObj=document.getElementById("invoiceTab");
	var rows=tbodyObj.getElementsByTagName("tr");
	var sumMONEY = document.getElementById("sumMONEY");
	var sumKGS = document.getElementById("sumKGS");
	var trsum=0;
	var trkgs = 0;
	for(var i=0;i<rows.length;i++){
		//总计
		var curRows=rows[i];
		var tdprice=curRows.getElementsByTagName("td")[1];
		var tdprice1=curRows.getElementsByTagName("td")[2];
		var tdprice2=curRows.getElementsByTagName("td")[3];
		var tinput=tdprice.getElementsByTagName("input")[0].value;
		var tinput1=tdprice1.getElementsByTagName("input")[0].value;
		if(""==tinput.trim()){
			tinput=0;
		}
		if(""==tinput1.trim()){
			tinput1=0;
		}
		var tdsum= parseInt(tinput1)* parseInt(tinput);
		if(trsum==""){
			trsum=0;
		}
		
		trsum=parseInt(trsum)+parseInt(tdsum);
		trkgs = parseInt(trkgs) + parseInt(tinput);
		sumKGS.innerHTML = parseFloat(trkgs).toLocaleString();
		
		var s=parseFloat(tdsum).toLocaleString();
		if(s==0){
			s="";
		}
		tdprice2.getElementsByTagName("input")[0].value=s;
		sumMONEY.innerHTML=parseFloat(trsum).toLocaleString();
	};
	
}
//======================钱单位变换-----------------
function moneyChange(e){
	if(e==1){
		document.getElementById("moneytype").innerHTML="￥";
	}
	if(e==2){
		document.getElementById("moneytype").innerHTML="$";

	}
	
	
}
//---------------------------package SUM-------------------------
//总计合计
function packageSum(e){
	//alert(999);
	e.style.border="0px";
	var tbodyObj1=document.getElementById("packageTab1");
	var rows=tbodyObj1.getElementsByTagName("tr");
	var QuantityP = document.getElementById("QuantityP");
	var Nw = document.getElementById("Nw");
	var Gw = document.getElementById("Gw");
	var M3 = document.getElementById("M3");
	var trQ=0;
	var trN = 0;
	var trG = 0;
	var trM = 0;
	for(var i=0;i<rows.length;i++){
		//总计
		var curRows=rows[i];
		var tdprice=curRows.getElementsByTagName("td")[1];
		var tdprice1=curRows.getElementsByTagName("td")[2];
		var tdprice2=curRows.getElementsByTagName("td")[3];
		var tdprice3=curRows.getElementsByTagName("td")[4];
		var tinput=tdprice.getElementsByTagName("input")[0].value;
		var tinput1=tdprice1.getElementsByTagName("input")[0].value;
		var tinput2=tdprice2.getElementsByTagName("input")[0].value;
		var tinput3=tdprice3.getElementsByTagName("input")[0].value;
		if(""==tinput.trim()){
			tinput=0;
		}
		if(""==tinput1.trim()){
			tinput1=0;
		}
		if(""==tinput2.trim()){
			tinput2=0;
		}
		if(""==tinput3.trim()){
			tinput3=0;
		}
		var trQ= parseFloat(trQ)+ parseFloat(tinput);
		var trN= parseFloat(trN)+ parseFloat(tinput1);
		var trG= parseFloat(trG)+ parseFloat(tinput2);
		var trM= parseFloat(trM)+ parseFloat(tinput3);
		QuantityP.value=Math.round(trQ*1000)/1000;
		Nw.value=Math.round(trN*1000)/1000;
		Gw.value=Math.round(trG*1000)/1000;
		M3.value=Math.round(trM*1000)/1000;
		
	};
	
}

function packageSum1(){
	var tbodyObj1=document.getElementById("packageTab1");
	var rows=tbodyObj1.getElementsByTagName("tr");
	var QuantityP = document.getElementById("QuantityP");
	var Nw = document.getElementById("Nw");
	var Gw = document.getElementById("Gw");
	var M3 = document.getElementById("M3");
	var trQ=0;
	var trN = 0;
	var trG = 0;
	var trM = 0;
	for(var i=0;i<rows.length;i++){
		//总计
		var curRows=rows[i];
		var tdprice=curRows.getElementsByTagName("td")[1];
		var tdprice1=curRows.getElementsByTagName("td")[2];
		var tdprice2=curRows.getElementsByTagName("td")[3];
		var tdprice3=curRows.getElementsByTagName("td")[4];
		var tinput=tdprice.getElementsByTagName("input")[0].value;
		var tinput1=tdprice1.getElementsByTagName("input")[0].value;
		var tinput2=tdprice2.getElementsByTagName("input")[0].value;
		var tinput3=tdprice3.getElementsByTagName("input")[0].value;
		if(""==tinput.trim()){
			tinput=0;
		}
		if(""==tinput1.trim()){
			tinput1=0;
		}
		if(""==tinput2.trim()){
			tinput2=0;
		}
		if(""==tinput3.trim()){
			tinput3=0;
		}
		var trQ= parseInt(trQ)+ parseInt(tinput);
		var trN= parseInt(trN)+ parseInt(tinput1);
		var trG= parseInt(trG)+ parseInt(tinput2);
		var trM= parseInt(trM).toFixed(3)+ parseInt(tinput3).toFixed(3);
		QuantityP.value=Math.round(trQ*1000)/1000;
		Nw.value=Math.round(trN*1000)/1000;
		Gw.value=Math.round(trG*1000)/1000;
		M3.value=Math.round(trM*1000)/1000;
	};
	
}


//invoice承认
function invoiceApprve(){
	
	var InvoiceID = document.getElementById('InvoiceID').value;
	if(InvoiceID==""){
		alert("请在保存后在确认！");
	}else{
		$.ajax({
			type:"POST",
			url:"xinan/InvoiceApprove.action?InvoiceID="+InvoiceID,
			async:"false",
			dataType:"json",
			error:function(data){
				alert("承认失败");
			}, 
			success:function(data){
				if(data){
					document.getElementById('user-sealI').style="display:true";
					document.getElementById('user-sealI1').style="display:true";
					document.getElementById('user-sealI2').style="display:true";
					document.getElementById('approveI').style="display:none";
					document.getElementById('SaveInvoice').style="display:none";
					alert("承认成功");
					
				}
		   
			}
		});
		
	}
	
	
}

function checkInvoiceCD(InvoiceCD) {
	var ret;
	$.ajax({
		type:"POST",
		url:"xinan/CheckInvoiceCd.action?InvoiceCd="+InvoiceCD,
		async:"false",
		dataType:"json",
		error:function(data){
		    alert("Invoice No 重複");
		    ret = false;
		    return;
		}, 
		success:function(data){
		    ret = true;
		}
	}).done(function(data) {
		if(ret) {
			invoiceSaveSheet();
		} else {
			alert("Invoice No 重複");
			return;
		}
	});
	
}

