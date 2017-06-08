function search() {
	var From = document.getElementById("Form").value;
	var To = document.getElementById("To").value;
	paramStr = "";
	if (From != "") {
		paramStr += "Form=" + From + "&";
		;
	}
	if (To != "") {
		paramStr += "To=" + To + "&";
		;
	}
	location.href = "xinan/noOrderEstimation.action?" + paramStr;
}

function resetForm(){
	document.getElementById("Form").value = "";
	document.getElementById("To").value = "";
}

function checkAll(){
	var collid = document.getElementById("checkall");
	var coll = document.getElementsByName("quoten");
		  if (collid.checked){
		     for(var i = 0; i < coll.length; i++)
		       coll[i].checked = true;
		  }else{
		     for(var i = 0; i < coll.length; i++)
		       coll[i].checked = false;
		  }

}	

function order2make(){
	var tbodyObj=document.getElementById("noOrder");
	var rows=tbodyObj.getElementsByTagName("tr");
	var coll = document.getElementsByName("quoten");
	var reqStr="";
	//alert(rows.length);
	var checkedNu=false;
	var checkedLan=true;
	var checkedLanOld="";
	var checkedLanNow="";
	for(var i=0;i<rows.length;i++){
		var curRows=rows[i];
		var tspn=curRows.getElementsByTagName("td")[0];
		var titemId=curRows.getElementsByTagName("td")[1];
		if(coll[i].checked == true){
			if(checkedLanOld == ""){
				checkedLanOld = rows[i].getElementsByTagName("td")[11].innerHTML;
			}
			checkedLanNow = rows[i].getElementsByTagName("td")[11].innerHTML;
			if(checkedLanOld != checkedLanNow){checkedLan=false;}
			checkedNu=true;
			reqStr = reqStr+tspn.innerHTML+","+titemId.innerHTML+";";
		}
		//var checkbox = tspn.getElementsByTagName("input")[0].value;
	};
	if(!checkedLan){
		alert("币种不一致,请重新选折");
		return;
	}
	if(!checkedNu){
		alert("请至少选择一条数据");
		return;
	}
	location.href = "xinan/ordermake.action?reqStr="+reqStr+"&langFlg=JP";;
}