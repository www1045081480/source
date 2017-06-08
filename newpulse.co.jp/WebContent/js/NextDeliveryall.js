//--------------------------------------次月配送一览---------------------------------------

function nexdeliveryStar(){
	var customerName1 = $("#customerName1").val();
	var customerOrder1 = $("#customerOrder1").val();
	$("#customerName").val(customerName1);
	$("#customerOrder").val(customerOrder1);
	var year1 = $("#year1").val();
	var month1 = $("#month1").val();
	$("#year").val(year1);
	
	//alert(month1);
	var date = new Date();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	//month = 6;
	if(month == 12){
		year = year+1;
	}
	var nextyear = year + 1;
	var nextmonth =month + 1;
	var cnextmonth = month + 1;
	var objYear = document.getElementById('year'); 
	var obj=document.getElementById('mon'); 
		var nextfive = year+2;
		var nextone = year-2;
		
		for(nextone;nextone<=nextfive;nextone++){
			objYear.options.add(new Option(nextone,nextone));
		}
		if(nextmonth == 13){
			nextmonth = 1;
		}
		for(nextmonth;nextmonth<=12;nextmonth++){
			obj.options.add(new Option(nextmonth,nextmonth));
		}

		var hideYear = $("#nexdeliveryyear").val();
		var hideMonth = $("#nexdeliverymm").val();
		if(hideYear != ""){
			objYear.value = hideYear;
			obj.innerHTML="";
			for(var i=1;i<=12;i++){
				obj.options.add(new Option(i,i));
			};
			obj.value = parseInt(hideMonth);
			//document.getElementById('nexdeliveryyear').value="";
			//document.getElementById('nexdeliverymm').value="";
			
		}else{
			for(nextmonth;nextmonth<=12;nextmonth++){
				obj.options.add(new Option(nextmonth,nextmonth));
			}
		}
		
		
	objYear.onchange=function(){
			obj.innerHTML="";
			for(var i=1;i<=12;i++){
				obj.options.add(new Option(i,i));
			};
	};
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
	    $("#customerName").autocomplete({
	      source: availableTags
			});
	   
		}
});
}
//回车公司 名字地址变更
function changeName1(evt){
	 evt = (evt) ? evt : ((window.event) ? window.event : "");
     var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
      var e=document.getElementById("customerName").value;
      if (e.trim() == "")
    	  return;
      if (keyCode == 13) {
    	  $.ajax({
				type:"POST",
				url:"xinan/customeraddress.action?enShortName="+e,
				async:"true",
				dataType:"json",
				success:function(data){
					$("#customerName").val(data[0].cnName);
					$("#customerID").val(data[0].customerId);
				}
    	  });
			
		
      }
}

function receiveClean1(){
	document.getElementById('customerID').value =""; 
	document.getElementById('customerName').value =""; 
	document.getElementById('customerOrder').value =""; 
	
}
//失去焦点公司 名字地址变更
function keydownMsg1(evt) {
		     // evt = (evt) ? evt : ((window.event) ? window.event : "");
		    //  keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
		      var e=document.getElementById("customerName").value;
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
						success:function(data){
							$("#customerName").val(data[0].cnName);
							$("#customerID").val(data[0].customerId);
						}
		    	  });
		      }
//---------次月配送一览検索---------
function nexdeliveryFind(){
	var customerName = $("#customerName").val();
	var customerOrder = $("#customerOrder").val();
	var customerID = $("#customerID").val();
	var year = $("#year").val();
	var month = $("#mon").val();
	if(month<10){
		month = "0"+month;
	}
	
	var date = ""+year+ month;
	var url = "";
	url = url + "xinan/NextDeliveryLoadAction.action?";
	url = url + "&customerName=" + customerName;
	url = url + "&customerOrder=" + customerOrder;
	url = url + "&customerID=" + customerID;
	url = url + "&date=" + date+"&now="+(new Date()).valueOf();
	location.href = url;
}