//function fetchNotApproveList(){
//		$.ajax({
//			type:"POST",
//			url:"xinan/fetchNotApproveList.action?",
//			async:"false",
//			dataType:"json",
//			error:function(){
//				alert("查询失败");
//				
//			}, 
//			success:function(data){
//				alert("查询成功");
//			}
//		});
//	}

function deleOneEstimation(e){
	if(!cancelConfirm())
		return;
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var urlstr="&EstimationId="+value;
	$.ajax({
		type:"POST",
		url:"xinan/cancelEstimation.action?",
		async:"false",
		data:urlstr,
		dataType:"text",
		error:function(){
			alert("delete失败");
		}, 
		success:function(data){
			e.parentNode.parentNode.parentNode.removeChild(e.parentNode.parentNode);
		}
	});
}

function approveOneEstimation(e,userId){
	if(userId !="4" && userId !="5"){
		alert("权限不足");
		return;
	}
	if(!approveConfirm())
		return;
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var urlstr="&EstimationId="+value;
	$.ajax({
		type:"POST",
		url:"xinan/approveEstimation.action?",
		async:"true",
		data:urlstr,
		dataType:"text",
		error:function(){
			alert("approve失败");
		}, 
		success:function(data){
//			e.parentNode.parentNode.parentNode.removeChild(e.parentNode.parentNode);
			location.href = "xinan/quotenone.action";
		}
	});
}

function deleOneOrder(e){
	if(!cancelConfirm())
		return;
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var urlstr="&OrderId="+value;
	$.ajax({
		type:"POST",
		url:"xinan/cancelOrder.action?",
		async:"false",
		data:urlstr,
		dataType:"text",
		error:function(){
			alert("delete失败");
		}, 
		success:function(data){
			e.parentNode.parentNode.parentNode.removeChild(e.parentNode.parentNode);
		}
	});
}

function approveOneOrder(e,userId){
	if(userId !="4" && userId !="5"){
		alert("权限不足");
		return;
	}
	if(!approveConfirm())
		return;
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var urlstr="&OrderId="+value;
	$.ajax({
		type:"POST",
		url:"xinan/approveOrder.action?",
		async:"true",
		data:urlstr,
		dataType:"text",
		error:function(){
			alert("approve失败");
		}, 
		success:function(data){
//			e.parentNode.parentNode.parentNode.removeChild(e.parentNode.parentNode);
			location.href = "xinan/quotenone.action";
		}
	});
}

function checkAllEstimation(){
	var tag = document.getElementById("checkallEs").checked
	var table = document.getElementById("estimationTable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	for(var i=1;i<rows.length;i++){					
		rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked = tag;
	}
}

function checkAllOrder(){
	var tag = document.getElementById("checkallOd").checked
	var table = document.getElementById("orderTable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	for(var i=1;i<rows.length;i++){					
		rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked = tag;
	}
}

function getSelectedEs2approve(userId){
	if(userId !="4" && userId !="5"){
		alert("权限不足");
		return;
	}
	if(!approveConfirm())
		return;
	var tag = document.getElementById("checkallEs").checked
	var table = document.getElementById("estimationTable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	for(var i=rows.length-1;i>0;i--){	
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			var EstimationId = rows[i].getElementsByTagName("td")[0].innerHTML;
			var urlstr="&EstimationId="+EstimationId;
			
			$.ajax({
				type:"POST",
				url:"xinan/approveEstimation.action?",
				async:"false",
				data:urlstr,
				dataType:"text",
				error:function(){
					alert("approve失败");
				}, 
				success:function(data){
				location.href = "xinan/quotenone.action";
				}
			});
		}
	}
	for(var i=rows.length-1;i>0;i--){	
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			rows[i].parentNode.removeChild(rows[i]);
			}
		}
	document.getElementById("checkallEs").checked = false;
}

function getSelectedOd2approve(userId){
	if(userId !="4" && userId !="5"){
		alert("权限不足");
		return;
	}
	if(!approveConfirm())
		return;
	var tag = document.getElementById("checkallOd").checked
	var table = document.getElementById("orderTable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	for(var i=1;i<rows.length;i++){					
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			var OrderId = rows[i].getElementsByTagName("td")[0].innerHTML;
			var urlstr="&OrderId="+OrderId;
			$.ajax({
				type:"POST",
				url:"xinan/approveOrder.action?",
				async:"true",
				data:urlstr,
				dataType:"text",
				error:function(){
					alert("approve失败");
				}, 
				success:function(data){
					location.href = "xinan/quotenone.action";
				}
			});
		}
	}
	for(var i=rows.length-1;i>0;i--){	
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			rows[i].parentNode.removeChild(rows[i]);
			}
		}
	document.getElementById("checkallOd").checked = false;
}


function canChAll4Es(e){
	if(!e.checked){
		document.getElementById("checkallEs").checked = false;
	}
}

function canChAll4Od(e){
	if(!e.checked){
		document.getElementById("checkallOd").checked = false;
	}
}

function getSelectedEs2cancel(){
	if(!cancelConfirm())
		return;
	var tag = document.getElementById("checkallEs").checked
	var table = document.getElementById("estimationTable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	for(var i=rows.length-1;i>0;i--){	
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			var EstimationId = rows[i].getElementsByTagName("td")[0].innerHTML;
			var urlstr="&EstimationId="+EstimationId;
			
			$.ajax({
				type:"POST",
				url:"xinan/cancelEstimation.action?",
				async:"false",
				data:urlstr,
				dataType:"text",
				error:function(){
					alert("cancel失败");
				}, 
				success:function(data){
					location.href = "xinan/quotenone.action";
				}
			});
		}
	}
	for(var i=rows.length-1;i>0;i--){	
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			rows[i].parentNode.removeChild(rows[i]);
			}
		}
	document.getElementById("checkallEs").checked = false;
}

function getSelectedOd2cancel(){
	if(!cancelConfirm())
		return;
	var tag = document.getElementById("checkallOd").checked
	var table = document.getElementById("orderTable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	for(var i=1;i<rows.length;i++){					
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			var OrderId = rows[i].getElementsByTagName("td")[0].innerHTML;
			var urlstr="&OrderId="+OrderId;
			$.ajax({
				type:"POST",
				url:"xinan/cancelOrder.action?",
				async:"true",
				data:urlstr,
				dataType:"text",
				error:function(){
					alert("cancel失败");
				}, 
				success:function(data){
					location.href = "xinan/quotenone.action";
				}
			});
		}
	}
	for(var i=rows.length-1;i>0;i--){	
		var checked = rows[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].checked;
		if(checked){
			rows[i].parentNode.removeChild(rows[i]);
			}
		}
	document.getElementById("checkallOd").checked = false;
}

function cancelConfirm(){
	var se=confirm("是否进行删除操作？");
	if (se==true)
	  {
		return true;
	  }
	else
	  {
		return false;
	  }
}

function approveConfirm(){
	var se=confirm("是否进行承认操作？");
	if (se==true)
	  {
		return true;
	  }
	else
	  {
		return false;
	  }
}

function estimationSearch(){
	var id = document.getElementById("esId").value;
	var date = document.getElementById("esDate").value;
	var name = document.getElementById("esName").value;
	alert("功能开发中");
}

function orderSearch(){
	var id = document.getElementById("odId").value;
	var date = document.getElementById("odDate").value;
	var name = document.getElementById("odName").value;
	alert("功能开发中");
}

function readOneEstimation(e){
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var langFlg = e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML;
	location.href = "xinan/readEstimation.action?EstimationId="+value+"&type=read"+"&langFlg="+langFlg;
}

function readOneOrder(e){
	var value = e.parentNode.parentNode.getElementsByTagName('td')[0].innerHTML;
	var langFlg = e.parentNode.parentNode.getElementsByTagName('td')[11].innerHTML;
	location.href = "xinan/readOrder.action?OrderId="+value+"&langFlg="+langFlg;
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
