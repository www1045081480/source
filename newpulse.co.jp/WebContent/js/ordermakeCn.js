var ItemidNum = 0;	
var NoNum = 1;	
var NameNum = 2;	
var DescNum = 3;	
var QuantityNum = 4;	
var UnitNum = 5;	
var UnitpriceNum = 6;	
var AmountNum = 7;	
var TimeNum = 8;	
var OperateNum = 9;
var EstimationIdNum = 10;	
var TypeNum = 11;	
var PartsCd = 12;
var ModelCdNum = 13;	

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

//总计合计
	function sum(e){
		e.style.border="0px";
		var tbodyObj=document.getElementById("sptable");
		var rows=tbodyObj.getElementsByTagName("tr");
		var hjsum = document.getElementById("hjsum");
		var zsum = document.getElementById("zsum");
		var sumje = document.getElementById("sumje");
		var trsum=0;
		for(var i=1;i<=rows.length-1;i++){
			//总计
			var curRows=rows[i];
			var tdprice=curRows.getElementsByTagName("td")[QuantityNum];
			var tdprice1=curRows.getElementsByTagName("td")[UnitpriceNum];
			var tdprice2=curRows.getElementsByTagName("td")[AmountNum];
			var tinput=tdprice.getElementsByTagName("input")[0].value;
			var tinput1=tdprice1.getElementsByTagName("input")[0].value;
			if(""==tinput.trim()){
				tinput=0;
			}
			if(""==tinput1.trim()){
				tinput1=0;
			}
			var tdsum=tinput1*tinput;
			trsum=trsum+tdsum;
			if(trsum==0){
				trsum="";
			}
			
			var s=parseFloat(tdsum).toLocaleString();
			if(s==0){
				s="";
			}
			tdprice2.innerHTML=s;
			hjsum.innerHTML=parseFloat(trsum).toLocaleString();
			
			zsum.innerHTML=parseFloat(trsum).toLocaleString();
			sumje.value=trsum;
		};
		
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
	
//页面初始化
function OrderSheet(e,reqStr){
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
		document.getElementById("sendtime").value=time;
		
		$.ajax({
			type:"post",
			url:"xinan/getECurrency.action?",
			async:"true",
			data:{"EstimationId":e,"reqStr":reqStr},
			dataType:"json",
			error:function(){
				alert("getEstmationSheet error");
			}, 
			success:function(data){
				document.getElementById("currencyType").value=data.currency;
				gradeChange();
			}
		});		
		
	$.ajax({
		type:"post",
		url:"xinan/loadOrderDetail.action?",
		async:"true",
		data:{"EstimationId":e,"reqStr":reqStr,"lanCd":'Cn'},
		dataType:"json",
		error:function(){
			alert("error");
		}, 
		success:function(data){
			var item; 
			var table = document.getElementById('sptable');
			var $table= $("#sptable");
			var tableStr = "<tr id='thead'><td width='50px'>序号</td>" + 
					"<td width='115px'>品名</td><td width='115px'>规格・尺寸</td><td width='50px'>数量</td>"+
					"<td width='30px'>单位</td><td width='80px'>单价</td>"+
					"<td width='80'>金额</td><td width='90px'>交货期</td>" +
					"<td class='noprint' width='30px'>操作</td></tr>";
			$table.append(tableStr);
			var len = data.length;  
		    for ( var i = 0; i < len; i++) {  
		    	var  onfocus= "setday('1',this,'yyyy-MM-dd','2000-01-01','2100-12-30',0);";
//		    	   tableStr = tableStr + 
		    	var tr = 
			        "<tr id='tr"+i+"'>" + 
			        "<td width='' style='display:none;'><input   id='itemId"+i+"' value='"+data[i].itemId+"'/></td>"+
			        "<td width='30px' align='center'>"+(i+1)+"</td>"+
			        "<td style='vertical-align: top;' width='115px' id='name"+i+"' align='center'><textarea class='spname' style='min-width:115px;max-width:115px'>"+data[i].name+"</textarea></td>"+  
			        "<td style='vertical-align: top;' width='115px' id='type"+i+"' align='center'><textarea class='spname' style='min-width:115px;max-width:115px'>"+data[i].partsCd+"</textarea></td>"+  
			        "<td style='vertical-align: top;' width='30px' id='quantity"+i+"' align='center'>" +
			        		"<input class='spname'  id='quantityInput"+i+"' onblur='quantityOnBlur(this)' align='center' style='border:0px;text-align:center;width:50px' value='"+data[i].quantity.toLocaleString()+"'/></td>"+  
			        "<td style='vertical-align: top;' width='30px' id='unit"+i+"' align='center'>" +
			        		"<input class='spname' type='text' value='"+data[i].unit+"'style='border:0px;text-align:center;width:30px'></td>"+  
			        "<td style='vertical-align: top;' width='80px' id='unitPrice"+i+"' align='center'>" +
			        		"<input class='spname' id='priceInput"+i+"' onblur='unitPriceOnBlur(this)'  style='border:0px;text-align:center;width:80px' value=''/></td>"+  
			        "<td style='vertical-align: top;' width='115px' id='amount"+i+"' align='center' width=''>0</td>"+  
			        "<td style='vertical-align: top;'  width='90px' id='time"+i+"' align='center'>"+
			        "<input  class='spname' style='text-align:center;width:90px' id='delivery_date"+i+"'  type='text'  onfocus="+
			        onfocus+"></td>" +"<td  class='noprint' width='30px' align='center'><input  type='button' value='删除' style='border:0px;text-align:center;width:30px' onclick = 'deleClick(this);'> </td>" +
			        "<td width='' style='display:none;'><input   value='"+data[i].estimationId+"'/></td>"+
			        "<td width='' style='display:none;'><input   value='"+data[i].type+"'/></td>"+
			        "<td width='' style='display:none;'><input   value='"+data[i].partsCd+"'/></td>"+
			        "<td width='' style='display:none;'><input   value='"+data[i].modelCd+"'/></td>"+		
			        "</tr>"
			        $table.append(tr);
		    }  
		    if(len==0){  
		        str =  "<tr style='text-align: center'>"  
		        +"<td colspan='8'><font color='#cd0a0a'> 数据读取错误 </font></td>"  
		        +"</tr>";  
		    	 $table.append(str);
		    }else{
		    	document.getElementById("title").value = data[0].name;
		    }  
		    
		    $('.spname').autosize({
		    	append: "\n"
		    });
		    adjustH(document.getElementById('payment_condition'));
		    
		    changeSubtd();

		}});
}	
function changeSubtd(){
		var count = 0;
		document.getElementById("tsum").innerHTML=count;
		$.ajax({
					type:"POST",
					url:"xinan/suppliernames.action?",
					async:"true",
					dataType:"json",
					error:function(){
						alert("fail");
					}, 
					success:function(data){
					//数组合并
					var availableTags= data.jpNames;
					//搜索选择
				    $("#_select").autocomplete({
				      source: availableTags
		  			});
					}
		 });
		
  	}
	//回车公司 名字地址变更
	function changeName(evt){
		 evt = (evt) ? evt : ((window.event) ? window.event : "");
	      keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
	      var e=document.getElementById("_select").value;
	      if (keyCode == 13) {
	    	  $.ajax({
					type:"POST",
					url:"xinan/supplierAddress.action?jpName="+e,
					async:"true",
					dataType:"json",
					success:function(data){
						$("#_select").val(data[0].cnName);
						$("#tdmail").val("〒"+data[0].postCd);
						$("#address1").val(data[0].address1);
						$("#address2").val(data[0].address2);
						$("#address3").val(data[0].address3);
						$("#supplierId").val(data[0].supplierId);
						$("#enShortName").val(data[0].enShortName);
					}
	    	  });
				
			
	      }
	}
	//失去焦点公司 名字地址变更
	function keydownMsg(evt) {
			      var e=document.getElementById("_select").value;
			    	  $.ajax({
							type:"POST",
							url:"xinan/supplierAddress.action?jpName="+e,
							async:"false",
							dataType:"json",
							 error:function(){
								alert(123);
								alert(arguments[1]);
							},  
							success:function(data){
								$("#_select").val(data[0].cnName);
								$("#tdmail").val("〒"+data[0].postCd);
								$("#address1").val(data[0].address1);
								$("#address2").val(data[0].address2);
								$("#address3").val(data[0].address3);
								$("#supplierId").val(data[0].supplierId);
								$("#enShortName").val(data[0].enShortName);
							}
			    	  });
			      }


//保存
function save(){
	var supplierName = document.getElementById("_select").value;
	if(supplierName == null || supplierName==""){
		alert("请选择供货商");
		return;
	}
	var delivery_date = document.getElementById("delivery_date").value;
	if(delivery_date == null || delivery_date==""){
		alert("请选择交货期");
		return;
	}
	
	var table = document.getElementById("sptable");
	var rows=table.getElementsByTagName("tr");
	var length = rows.length;
	
	
	var estimationIds = "";
	for(var i=1;i<length;i++){
		var OrderId = rows[i].getElementsByTagName("td")[EstimationIdNum].getElementsByTagName("input")[0].value;
		estimationIds = estimationIds + OrderId + ";";
		var curRows=rows[i];
		var tspn=curRows.getElementsByTagName("td")[NameNum];
		var tspnv=tspn.getElementsByTagName("textarea")[0].value;
		if(tspnv != ""){
			//数量value
			var tnum = curRows.getElementsByTagName("td")[QuantityNum];
			var tnumv = tnum.getElementsByTagName("input")[0].value.split(",").join('');
			//単価value
			var tprice = curRows.getElementsByTagName("td")[UnitpriceNum];
			var tpricev = tprice.getElementsByTagName("input")[0].value.split(",").join('');
			if(tnumv == ""||tpricev==""){
				alert("请将商品信息填写完整");
				return;
			}
		}
	} 
	
	document.getElementById("tsum").innerHTML=count.toLocaleString();
	
	
	//発行日
	var IssueDate = $("#sendtime").val();
	//供应商
	var SupplierName = $("#_select").val();
	//供应商ID
	var supplierId = $("#supplierId").val();
	//供应商邮编
	var PostCode = $("#tdmail").val();
	//地址1
	var Address1 = $("#address1").val();
	//地址2
	var Address2 = $("#address2").val();
	//地址3
	var Address3 = $("#address3").val();
	//电话
	var Tel = $("#Tel").val();
	//fax
	var Fax = $("#Fax").val();
	//担当者姓名
	var StaffName = $("#StaffName").val();
	
	//总价格
	var amount = count;
	//备注
	var Note = $("#bknote").val();
	//交货期
	var delivery_date = $("#delivery_date").val();
	//交货地点
	var DeliveryAddress = $("#delivery_address").val();
	//支付条件
	var payment_condition = encodeURI($("#payment_condition").val());
	//包装要求
	var package_method = $("#package_method").val();
	
	var enShortName = $("#enShortName").val();
	var orderCd = document.getElementById("ordercd").value;
	//estimationId
	var orderid = document.getElementById("orderid").value;
	var payment_method = document.getElementById("payment_method").value;
	var userId = document.getElementById("userId").value;
	//货币类型
	var currency = $("#currencyType").val();
	//discount
	var discount = $("#discount").val();
	
	var urlstr="&IssueDate="+IssueDate+"&supplierId="+supplierId+"&orderCd="+orderCd;
	urlstr=urlstr+"&SupplierName="+SupplierName+"&PostCode="+PostCode;
	urlstr=urlstr+"&Address1="+Address1+"&Address2="+Address2+"&Address3="+Address3;
	urlstr=urlstr+"&Tel="+Tel;
	urlstr=urlstr+"&Fax="+Fax+"&StaffName="+StaffName;
	urlstr=urlstr+"&amount="+amount+"&Note="+Note;
	urlstr=urlstr+"&DeliveryDate="+delivery_date;
	urlstr=urlstr+"&DeliveryAddress="+DeliveryAddress;
	urlstr=urlstr+"&payment_condition="+payment_condition;
	urlstr=urlstr+"&package_method="+package_method;
	urlstr=urlstr+"&orderId="+orderid;
	urlstr=urlstr+"&enShortName="+enShortName;
	urlstr=urlstr+"&estimationIds="+estimationIds;
	urlstr=urlstr+"&payment_method="+payment_method;
	urlstr=urlstr+"&userId="+userId;
	urlstr=urlstr+"&currency="+currency;
	urlstr=urlstr+"&langFlg=CN";
	urlstr=urlstr+"&discount="+discount;
	
    $.ajax({
		type:"post",
		url:"xinan/addOrderSheet.action",
		async:"false",
		data:urlstr,
		dataType:"json",
		error:function(data){
			alert("sheet失败");
		},
		success: function(data){
			document.getElementById("ordercd").value = data.orderCd;
			document.getElementById("orderid").value = data.orderId;
			document.getElementById("userId").value = data.userId;
			saveDetail(data.orderId);
			alert("保存成功");
		}
	});
	
	//savems();
}
//保存明细
function saveDetail(id){
	//orderId
	var orderId = id;
	// 品名
	var name;
	// 数量
	var quantity;
	// 单位
	var unit;
	// 单价
	var unitPrice;
	// 金额
	var amount;
	// 纳期
	var time
	//ItemId
	var itemId;
	// 
	var type;
	// 
	var partsCd;
	// 
	var modelCd;
	
	var size = document.getElementById('sptable').rows.length;
	for(var i=1;i<size;i++){
		var x=document.getElementById('sptable').rows[i].cells;
		name = x[NameNum].getElementsByTagName('textarea')[0].value;
		quantity = x[QuantityNum].getElementsByTagName('input')[0].value.split(",").join('');
		unit = x[UnitNum].getElementsByTagName('input')[0].value;
		unitPrice = x[UnitpriceNum].getElementsByTagName('input')[0].value.split(",").join('');
		amount = parseFloat(x[QuantityNum].getElementsByTagName('input')[0].value.split(",").join('')*x[UnitpriceNum].getElementsByTagName('input')[0].value.split(",").join(''));
		time = x[TimeNum].getElementsByTagName('input')[0].value;
		type = x[TypeNum].getElementsByTagName('input')[0].value;
		partsCd = x[DescNum].getElementsByTagName('textarea')[0].value;
		modelCd = x[ModelCdNum].getElementsByTagName('input')[0].value;
		itemId = x[ItemidNum].getElementsByTagName('input')[0].value;
		var urlstr="&order_id="+orderId+
					"&name="+name+
					"&quantity="+quantity+
					"&unit="+unit+
					"&unit_price="+unitPrice+
					"&amount="+amount+
					"&deliveryDate="+time+
					"&item_id="+itemId+
					"&type="+type+
					"&partsCd="+partsCd+
					"&modelCd="+modelCd;
	  $.ajax({
			type:"post",
			url:"xinan/addOrderDetail.action",
			async:"false",
			data:urlstr,
			error:function(data){
				alert("detail失败");
			},
			success: function(data){
			}
		});
	}
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

	function fillTime(tag){
		var length = document.getElementById('sptable').rows.length;
		if(Tag == '2'){
			for(var i=1;i<length;i++){
				var x=document.getElementById('sptable').rows[i].cells;
				x[TimeNum].getElementsByTagName('input')[0].value = document.getElementById("delivery_date").value;
			}
		}
	}
	
	var count = 0; 
	function quantityChange(e){
		if(e.value == "") return;
		var sumcount = 0;
		var size = document.getElementById('sptable').rows.length;
		var quantity = e.parentNode.parentNode.getElementsByTagName('td')[QuantityNum].getElementsByTagName('input')[0].value;
		var unitPrice = e.parentNode.parentNode.getElementsByTagName('td')[UnitpriceNum].getElementsByTagName('input')[0].value;
		if(quantity != "" || unitPrice != ""){
			e.parentNode.parentNode.getElementsByTagName('td')[AmountNum].innerHTML = (quantity.split(",").join('')*unitPrice.split(",").join('')).toLocaleString();
		}
		count = 0;
		for(var i=1;i<size;i++){
			var x=document.getElementById('sptable').rows[i].cells;
			count =parseFloat(x[QuantityNum].getElementsByTagName('input')[0].value.split(",").join('')*x[UnitpriceNum].getElementsByTagName('input')[0].value.split(",").join(''))  + parseFloat(count);
		} 
		count = count - parseFloat(document.getElementById("discount").value.split(",").join(''));
		document.getElementById("tsum").innerHTML=count.toLocaleString();
	}

	function deleClick(e){
		var size = document.getElementById('sptable').rows.length;
		if(size > 2){
		e.parentNode.parentNode.parentNode.removeChild(e.parentNode.parentNode);
		count = 0;
		for(var i=1;i<size-1;i++){
			var x=document.getElementById('sptable').rows[i].cells;
			x[1].innerHTML = i;
			count =parseFloat(x[QuantityNum].getElementsByTagName('input')[0].value.split(",").join('')*x[UnitpriceNum].getElementsByTagName('input')[0].value.split(",").join(''))  + parseFloat(count);
		} 
		count = count - parseFloat(document.getElementById("discount").value.split(",").join(''));
		document.getElementById("tsum").innerHTML=count.toLocaleString();
		}else
		{
			alert("已经是最后一行，不能删除")
		}
	}
	
	function maxApprove(e,a){
		if(e == '5'){
			var value = document.getElementById("orderid").value;
			if(value == "" || value == "undefined" || value== null){
				alert("请先保存该订单信息");
				return;
			}
			if(!approveConfirm())
				return;
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
					document.getElementById("maxApprove").innerHTML = 
						"<img width='60px' height='60px' id='maxseal'  src='xinan/userseal.action?userId="+a+"' />"
				}
			});
			
		}else{
			alert("权限不足")
		}
	}

	function minApprove(e,a){
		if(e == '4'){
			var value = document.getElementById("orderid").value;
			if(value == "" || value == "undefined" || value== null){
				alert("请先保存该订单信息");
				return;
			}
			if(!approveConfirm())
				return;
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
					document.getElementById("minApprove").innerHTML = 
						"<img width='60px' height='60px' id='minseal'  src='xinan/userseal.action?userId="+a+"' />"
				}
			});
		}else{
			alert("权限不足")
		}
	}

	function gradeChange(){
		var monery = document.getElementById("currencyType").value;
		if(monery=="JP"){
			document.getElementById("monery1").innerHTML="￥";
			document.getElementById("disCurrency").innerHTML="￥";
		};
		if(monery=="US"){
			document.getElementById("monery1").innerHTML="$";
			document.getElementById("disCurrency").innerHTML="$";
		};
		if(monery=="CN"){
			document.getElementById("monery1").innerHTML="R";
			document.getElementById("disCurrency").innerHTML="R";
		};
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
	
	function changeLangEN(a,b){
		if(a){
			
			location.href = "xinan/ordermake.action?&EstimationId="+a+"&langFlg=EN";
		}
		if(b){
			location.href = "xinan/ordermake.action?reqStr="+b+"&langFlg=EN";
		}
	}
	function changeLangCN(a,b){
		if(a){
			
			location.href = "xinan/ordermake.action?&EstimationId="+a+"&langFlg=CN";
		}
		if(b){
			location.href = "xinan/ordermake.action?reqStr="+b+"&langFlg=CN";
		}
	}
	function changeLangJP(a,b){
		if(a){
			
			location.href = "xinan/ordermake.action?&EstimationId="+a+"&langFlg=JP";
		}
		if(b){
			location.href = "xinan/ordermake.action?reqStr="+b+"&langFlg=JP";
		}
	}
	function checkApproveStatus(){
		var id = document.getElementById("orderid").value;
		if(id != ""){
		$.ajax({
			type:"post",
			url:"xinan/readOrderApproveStatus.action?",
			async:"true",
			data:{"OrderId":id},
			dataType:"json",
			error:function(){
				alert("error");
				return;
			}, 
			success:function(data){
				if(data.presidentId || data.vicePresidentId){
					alert("注文书已经承认，无法进行修改");
				}else{
					save();
				}
				}
			});
		}else{
			save();
		}
	}
	function quantityOnBlur(e){
		 $(e).parseNumber({format:"#,###", locale:""});
		   $(e).formatNumber({format:"#,###", locale:""});
		   quantityChange(e);
	}

	function unitPriceOnBlur(e){
		 var tag = document.getElementById("currencyType").value;
		 if(tag == "JP"){
			 $(e).parseNumber({format:"#,###", locale:""});
			   $(e).formatNumber({format:"#,###", locale:""});
		 }else{
			 $(e).parseNumber({format:"#,###.00", locale:""});
			   $(e).formatNumber({format:"#,###.00", locale:""});
		 }
		 quantityChange(e);
	}
	function discountOnBlur(e){
		 $(e).parseNumber({format:"#,###", locale:""});
		   $(e).formatNumber({format:"#,###", locale:""});
		   if(e.value.trim()==""){
			   e.value = 0;
		   }
		   discountChange(e);
	}
	
	function discountChange(e){
		count = 0;
		var size = document.getElementById('sptable').rows.length;
		for(var i=1;i<size;i++){
			var x=document.getElementById('sptable').rows[i].cells;
			count =parseFloat(x[QuantityNum].getElementsByTagName('input')[0].value.split(",").join('')*x[UnitpriceNum].getElementsByTagName('input')[0].value.split(",").join(''))  + parseFloat(count);
		} 
		count = count - parseFloat(document.getElementById("discount").value.split(",").join(''));
		if(count > 0){
			document.getElementById("tsum").innerHTML=count.toLocaleString();
		}else{
			alert("输入值不合法:超过总价")
			e.value = 0;
		}
	}