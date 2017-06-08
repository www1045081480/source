
function readOneEstimation(e){
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var langFlg = e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML;
	location.href = "xinan/readEstimation.action?EstimationId="+value+"&type=read"+"&langFlg="+langFlg;
}


function onLoad(a){
	var nowdate = new Date();
	var year = nowdate.getFullYear();
	var month = nowdate.getMonth() + 1;
	$("#year").text(year);
	$("#month").text(month);
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
	onChage(a);
}

function search(){
	var estimationCd = document.getElementById("estimationCd").value;
	var customerName = document.getElementById("customerName").value;
	var categoryType = document.getElementById("categoryType").value;
	var From = document.getElementById("From").value;
	var To = document.getElementById("To").value;
	paramStr = "";
	if(estimationCd != ""){
		paramStr += "estimationCd=" + estimationCd +"&";
	}
	if(customerName != ""){
		paramStr += "customerName=" + customerName+"&";;
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
	location.href = "xinan/QuoteallFindAction.action?"+paramStr;
}

function reset123(){
	document.getElementById("estimationCd").value = "";
	document.getElementById("customerName").value = "";
	document.getElementById("categoryType").value = "";
	document.getElementById("From").value = "";
	document.getElementById("To").value = "";
}


function onBlur(evt) {
     var e=document.getElementById("customerName").value;
     if (e.trim() == "")
    	 return;
   	  $.ajax({
				type:"POST",
				url:"xinan/customeraddress.action?enShortName="+e,
				async:"false",
				dataType:"json",
				 error:function(){
					alert("error");
				},  
				success:function(data){
					$("#customerName").val(data[0].cnName);
				}
   	  });
}

function copyOneEstimation(e){
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var langFlg = e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML;
	location.href = "xinan/readEstimation.action?EstimationId="+value+"&type=copy"+"&langFlg="+langFlg;
}


function download(){
	var estimationCd = document.getElementById("estimationCd").value;
	var customerName = document.getElementById("customerName").value;
	var categoryType = document.getElementById("categoryType").value;
	var From = document.getElementById("From").value;
	var To = document.getElementById("To").value;
	paramStr = "";
	if(estimationCd != ""){
		paramStr += "estimationCd=" + estimationCd +"&";
	}
	if(customerName != ""){
		paramStr += "customerName=" + customerName+"&";;
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
	window.open("xinan/downloadEstimation.action?"+paramStr);
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

