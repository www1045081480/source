function readOneOrder(e){
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var langflg = e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML;
	location.href = "xinan/readOrder.action?OrderId="+value+"&langFlg="+langflg;
}

function onLoad(a){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	$("#year").text(year);
	$("#month").text(month);
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
	    $("#supplierName").autocomplete({
	      source: availableTags
			});
	   
		}
});
	onChage(a);
}

function search(){
	var orderCd = document.getElementById("orderCd").value;
	var supplierName = document.getElementById("supplierName").value;
	var categoryType = document.getElementById("categoryType").value;
	var From = document.getElementById("From").value;
	var To = document.getElementById("To").value;
	paramStr = "";
	if(orderCd != ""){
		paramStr += "orderCd=" + orderCd +"&";
	}
	if(supplierName != ""){
		paramStr += "supplierName=" + supplierName+"&";;
	}
	if(categoryType != ""){
		paramStr += "categoryType=" + categoryType+"&";;
	}
	if(From != ""){
		paramStr += "dateFrom=" + From+"&";;
	}
	if(To != ""){
		paramStr += "dateTo=" + To+"&";;
	}
	location.href = "xinan/orderall.action?"+paramStr;
}

function reset123(){
	document.getElementById("orderCd").value = "";
	document.getElementById("supplierName").value = "";
	document.getElementById("categoryType").value = "";
	document.getElementById("From").value = "";
	document.getElementById("To").value = "";
}

function exportData(){
	alert("loading .....");
}

function onBlur(evt) {
//	var e = document.getElementById("supplierName").value;
//	$.ajax({
//		type : "POST",
//		url : "xinan/supplierAddress.action?jpName=" + e,
//		async : "false",
//		dataType : "json",
//		error : function() {
//			alert("error");
//		},
//		success : function(data) {
//			$("#supplierName").val(data[0].jpName);
//		}
//	});
}

function download(){;
	var orderCd = document.getElementById("orderCd").value;
	var supplierName = document.getElementById("supplierName").value;
	var categoryType = document.getElementById("categoryType").value;
	var From = document.getElementById("From").value;
	var To = document.getElementById("To").value;
	paramStr = "";
	if(orderCd != ""){
		paramStr += "orderCd=" + orderCd +"&";
	}
	if(supplierName != ""){
		paramStr += "supplierName=" + supplierName+"&";;
	}
	if(categoryType != ""){
		paramStr += "categoryType=" + categoryType+"&";;
	}
	if(From != ""){
		paramStr += "dateFrom=" + From+"&";;
	}
	if(To != ""){
		paramStr += "dateTo=" + To+"&";;
	}
	window.open("xinan/downloadOrder.action?"+paramStr);
}

function onChage(a){
	if(a == 1){
		document.getElementById("categoryType").value="1";
		}
	if(a == 2){
		document.getElementById("categoryType").value="2";
	}
	if(a == 3){
		document.getElementById("categoryType").value="3";
	}
	if(a == 4){
		document.getElementById("categoryType").value="4";
	}
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
