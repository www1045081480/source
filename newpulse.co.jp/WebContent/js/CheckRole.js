function checkRole(e){
	
	var Role = document.getElementById("checkRole").value;
	if(Role==5 || Role==4 || Role==6 || Role==e){
		return true;
	}else{
		var rolestr ="";
		var estr="";
		if(Role==1){
			rolestr = "営業"
		}
		if(Role==2){
			rolestr = "物流"
		}
		if(Role==3){
			rolestr = "財務"
		}
		if(Role==7){
			rolestr = "営業物流"
		}
		if(e==1){
			estr = "営業"
		}
		if(e==2){
			estr = "物流"
		}
		if(e==3){
			estr = "財務"
		}
		if(e==7){
			estr = "営業物流"
		}
		if(e==8){
			alert("您当前权限为<<"+rolestr+">>禁止进行此操作！\n"+"只有<<副社長-社長-管理员>>才能操作！");
			return false;
		}
		alert("    您当前权限为<<"+rolestr+">>禁止进行此操作！\n"+"只有<<"+estr+"-副社長-社長-管理员>>才能操作！");
		return false;
	}
	
}

//-------------------------数字校验-------------------------------------
function validate(obj){  
	fomat = obj.value;
	var str= new Array();
	str = fomat.split(",");
	var ObjValue = "";
	for(var i=0;i<str.length;i++){
		ObjValue = ObjValue+str[i];
	}
	str1 = ObjValue.split(".");
	var ObjValue1 = "";
	if(str1.length>2){
		alert("请输入数字!");
	    return false;
	}
	for(var i=0;i<str1.length;i++){
		ObjValue1 = ObjValue1+str1[i];
	}
	//var num = parseInt(ObjValue);
    var reg = new RegExp("^[0-9]*$");  
 if(!reg.test(ObjValue1)){  
     alert("请输入数字!");
     return false;
 }  
 if(!/^[0-9]*$/.test(ObjValue1)){  
     alert("请输入数字!"); 
     return false;
 }  
 return true;
 
} 
//针对日元check
function validate1(obj){  
	fomat = obj.value;
	var str= new Array();
	str = fomat.split(",");
	var ObjValue = "";
	for(var i=0;i<str.length;i++){
		ObjValue = ObjValue+str[i];
	}
//	str1 = ObjValue.split(".");
//	var ObjValue1 = "";
//	for(var i=0;i<str1.length;i++){
//		ObjValue1 = ObjValue1+str1[i];
//	}
	//var num = parseInt(ObjValue);
    var reg = new RegExp("^[0-9]*$");  
 if(!reg.test(ObjValue)){  
     alert("请输入数字!");
     return false;
 }  
 if(!/^[0-9]*$/.test(ObjValue)){  
     alert("请输入数字!"); 
     return false;
 }  
 return true;
 
} 