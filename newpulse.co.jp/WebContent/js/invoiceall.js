function invoiceallSave(){
	var tbodyObj=document.getElementById("ctbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	var url ="xinan/InvoicellSave.action?str="
	for(var i=0;i<rows.length;i++){
			var curRows=rows[i];
			//invoiceId
			var invoiceId=curRows.getElementsByTagName("td")[11].innerHTML;
			//alert(invoiceId);
			//INVOICE番号
			var invoiceCd=curRows.getElementsByTagName("td")[0].innerHTML;
//			//販売先
//			var customerName=curRows.getElementsByTagName("td")[1].innerHTML;
//			//販売先注文番号
//			var orderCd=curRows.getElementsByTagName("td")[2].innerHTML;
//			//INVOICE作成日
//			var issueDate=curRows.getElementsByTagName("td")[3].innerHTML;
//			//品名
//			var itemName=curRows.getElementsByTagName("td")[4].innerHTML;
//			//型番
//			var modelCd=curRows.getElementsByTagName("td")[5].innerHTML;
//			//数量
//			var quantity=curRows.getElementsByTagName("td")[6].innerHTML;
//			//alert(quantity);
//			//販売金額
//			var amount=curRows.getElementsByTagName("td")[7].innerHTML;
//			//仕入納期
//			var arrivalDate=curRows.getElementsByTagName("td")[8].innerHTML;
//			//貿易条件
//			var shippingType=curRows.getElementsByTagName("td")[9].innerHTML;
			//運賃諸経費
			var carringAmount=curRows.getElementsByTagName("td")[10].getElementsByTagName("input")[0].value;
			//alert(carringAmount);
			//orderCd
			var estimationCd=curRows.getElementsByTagName("td")[12].innerHTML;
			//deliveryType
//			var deliveryType=curRows.getElementsByTagName("td")[13].innerHTML;
//			//shippingCompany
//			var shippingCompany=curRows.getElementsByTagName("td")[14].innerHTML;
//			//packingNumber
//			var packingNumber=curRows.getElementsByTagName("td")[15].innerHTML;
			if(carringAmount != "" && carringAmount != null){
				url = url  + invoiceId + ";" + invoiceCd + ";" +carringAmount  + "@"; 
			}
//					+ issueDate + "@" + itemName + "@"+ modelCd+ "@" 
//					+ quantity + "@" +amount + "@" + arrivalDate + "@"+ shippingType + "@" +carringAmount + "@"  
//					+ orderCd + "@" +deliveryType+ "@" +shippingCompany+ "@" +packingNumber +",";
			
	};
	//alert(url);
	$.ajax({
		type:"POST",
		url:url,
		async:"false",
		dataType:"json",
		error:function(data){
			alert("保存成功！");
		}, 
		success:function(data){
			alert("保存成功！");
		}
	});
}

function cleanIN(){
	document.getElementById("INVOICECD").value="";
	document.getElementById("inform").value="";
	document.getElementById("into").value="";
	document.getElementById("customerIDNO").value="";
	document.getElementById("customerID").value="";
}
//===========================invoice实际一览初始化allLoad=======================
function allLoad(){
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
	    $("#customerIDNO").autocomplete({
	      source: availableTags
			});
	   
		}
	});
	document.getElementById("INVOICECD").value = document.getElementById("invoiceCD").value;
	document.getElementById("customerID").value = document.getElementById("customerID").value;
	//document.getElementById("customerno").value = document.getElementById("customerNO").value;
	document.getElementById("inform").value = document.getElementById("InForm").value;
	document.getElementById("into").value = document.getElementById("InTo").value;
	var roleID=document.getElementById("roleID").value;
	var tbodyObj=document.getElementById("ctbody");
	var rows=tbodyObj.getElementsByTagName("tr");
	
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		//操作一行
		var cz=curRows.getElementsByTagName("td")[16];
		//承认状态
		var flag=curRows.getElementsByTagName("td")[17].innerHTML;
		if(flag != ""){
				cz.innerHTML="<input type='button' value='表示' onclick='showIN(this)'/>";
		}else{
			if(roleID==3){
				cz.innerHTML="<input type='button' value='承认' onclick ='approveIN(this);'/>";
		 	}else{
				cz.innerHTML="<input type='button' value='修改' onclick='changeIN(this);' />";
			}
		}
	}
}
//--------------------invoiceallFind-----------------------
function invoiceallFind(){
	var invoiceCD = document.getElementById("INVOICECD").value;
	var customerID = document.getElementById("customerID").value;
	var customerIDNO = document.getElementById("customerIDNO").value;
	//alert(2);
	//var customerNO = document.getElementById("customerno").value;
	
	
	
	var InForm = document.getElementById("inform").value;
	//alert(3);
	var InTo = document.getElementById("into").value;
	
	
	//时间check
	var iform = new Array();
	iform = InForm.split("-");
	var InFormDay = ""+iform[0]+iform[1]+iform[2];
	var ito = new Array();
	ito = InTo.split("-");
	var InToDay = ""+ito[0]+ito[1]+ito[2];
	if(parseInt(InFormDay)>parseInt(InToDay)){
		alert("开始时间不能大于结束时间！");
		return;
	}
	
	
	//alert(4);
	var url="xinan/invoiceallFindAction.action?";
	//alert(url);
	url = url + "&invoiceCD=" + invoiceCD;
	url = url + "&customerID=" + customerID;
	//url = url + "&customerNO=" + customerNO;
	url = url + "&InForm=" + InForm;
	url = url + "&InTo=" + InTo;
	url = url + "&customerIDNO=" + customerIDNO;
	
	location.href = url;
}
//---------------------------------------------------------------------------
//回车根据名字检索coustemID
function keydownCoustemID(evt){
	 evt = (evt) ? evt : ((window.event) ? window.event : "");
      var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
      var e=document.getElementById("customerIDNO").value;
      if (keyCode == 13) {
    	  $.ajax({
				type:"POST",
				url:"xinan/customeraddress.action?enShortName="+e,
				async:"false",
				dataType:"json",
				success:function(data){
					$("#customerIDNO").val(data[0].jpName);
					$("#customerID").val(data[0].customerId);
				}
    	  });
			
		
      }
}
//失去焦点根据名字检索coustemID
function findCoustemID(evt) {
	//alert(456);
		     // evt = (evt) ? evt : ((window.event) ? window.event : "");
		    //  keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
		      var e=document.getElementById("customerIDNO").value;
		      if (e.trim() == "")
		    	  return;
		    //  if (keyCode == 13) {
		    	  //alert("开始");
		    	  //alert(e);
		    	  $.ajax({
						type:"POST",
						url:"xinan/customeraddress.action?enShortName="+e,
						async:"false",
						//data:{"CnName":cname,"JpName":jname,"EnName":ename,"Role":roleid,"UserID":id},
						//"CnName="+cname+"&JpName="+jname+"&EnName="+ename+"&Role="+roleid,
						dataType:"json",
						 error:function(){
//							alert(123);
//							alert(arguments[1]);
						},  
						success:function(data){
								$("#customerIDNO").val(data[0].jpName);
								$("#customerID").val(data[0].customerId);
						}
		    	  });
 }
//--------------------Invoiceall一覧Download-----------------------
function downloadInvoice(){

	var invoiceCD = document.getElementById("INVOICECD").value;
	var customerIDNO = document.getElementById("customerID").value;
	var InForm = document.getElementById("inform").value;
	var InTo = document.getElementById("into").value;
	var iform = new Array();
	iform = InForm.split("-");
	var InFormDay = ""+iform[0]+iform[1]+iform[2];
	var ito = new Array();
	ito = InTo.split("-");
	var InToDay = ""+ito[0]+ito[1]+ito[2];
	if(parseInt(InFormDay)>parseInt(InToDay)){
		alert("开始时间不能大于结束时间！");
		return;
	}
	
	var url="xinan/downloadInvoice.action?";
	url = url + "&invoiceCD=" + invoiceCD;
	url = url + "&customerID=" + customerIDNO;
	url = url + "&InForm=" + InForm;
	url = url + "&InTo=" + InTo;

	window.open(url);
}
//----------------------------------invoiceall-------------------------------------------

function approveIN(evt){
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
   // alert(tparent);
    var invoiceID = tparent.getElementsByTagName('td')[11].innerHTML;
    //alert(invoiceID);
	var url="xinan/invoiceChange.action?invoiceID="+invoiceID+"&kind=approve";
	location.href = url;
}
function changeIN(evt){
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
    // alert(tparent);
    var invoiceID = tparent.getElementsByTagName('td')[11].innerHTML;
    //alert(invoiceID);
	var url="xinan/invoiceChange.action?invoiceID="+invoiceID+"&kind=change";
	location.href = url;
}
function showIN(evt){
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
   // alert(tparent);
    var invoiceID = tparent.getElementsByTagName('td')[11].innerHTML;
    //alert(invoiceID);
	var url="xinan/invoiceChange.action?invoiceID="+invoiceID+"&kind=show&hidButton=hide";
	location.href = url;
}	
