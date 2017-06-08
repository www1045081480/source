var estimationId;

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
//日期初始化
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
	document.getElementById("confirmdate").value=time;
	var tonclick=document.getElementById("ctd");
	}
//总计合计
function sum(e){
	e.style.border="0px";
	if(e.value == "") return;
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var hjsum = document.getElementById("hjsum");
	var amount = document.getElementById("amount");
	var sumje = document.getElementById("sumje");
	//var trsum;
	//var subsum=document.getElementById("subtd");
	//var taxsum=document.getElementById("tax");
	//var ttsum=document.getElementById("tsum");
	//var tmon=document.getElementById("mon");
	var trsum=0;
	for(var i=1;i<rows.length-1;i++){
		//总计
		var curRows=rows[i];
		var tdprice=curRows.getElementsByTagName("td")[4];
		var tdprice1=curRows.getElementsByTagName("td")[6];
		var tdprice2=curRows.getElementsByTagName("td")[7];
		var tinput=tdprice.getElementsByTagName("input")[0].value;
		var tinput1=tdprice1.getElementsByTagName("input")[0].value;
		tinput = tinput.toString().split(",").join('');
		tinput1 = tinput1.toString().split(",").join('');
		if(""==tinput.trim()){
			tinput=0;
			//alert(123);
		}
		if(""==tinput1.trim()){
			tinput1=0;
			//alert(456);
		}
		var tdsum=tinput1*tinput;
		//alert(tdsum);
//		if(""==tdsum.trim()){
//			tdsum = 0;
//		}
		if(trsum==""){
			trsum=0;
		}
		trsum=trsum+tdsum;
		//alert(trsum);
		if(trsum==0){
			trsum="";
		}
		
		var s=parseFloat(tdsum).toLocaleString();
		if(s==0){
			s="";
		}
		tdprice2.innerHTML=s;
		if(trsum==0){
			hjsum.innerHTML = trsum;
			amount.innerHTML = trsum;
			sumje.value=trsum;
			document.getElementById("hjAmount").value = trsum;
		}else{
			hjsum.innerHTML=parseFloat(trsum).toLocaleString();
			amount.innerHTML=parseFloat(trsum).toLocaleString();
			sumje.value=trsum;
			document.getElementById("hjAmount").value = trsum;
		}
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

function load(e){
	$.ajax({
		type:"post",
		url:"xinan/readEstimationApproveStatus.action?",
		async:"true",
		data:{"EstimationId":e},
		dataType:"json",
		error:function(){
			alert("error");
		}, 
		success:function(data){
			if(data.presidentId){
				document.getElementById("maxApprove").innerHTML = 
				"<img width='60px' height='60px7 id='maxseal'  src='xinan/userseal.action?userId="+data.presidentId+"' />"
			}
			if(data.vicePresidentId)
				document.getElementById("minApprove").innerHTML = 
				"<img width='60px' height='60px7 id='minseal'  src='xinan/userseal.action?userId="+data.vicePresidentId+"' />"
			}
		});
	$.ajax({
		type:"post",
		url:"xinan/getEstmationSheet.action?",
		async:"true",
		data:{"EstimationId":e},
		dataType:"json",
		error:function(){
			alert("error");
		}, 
		success:function(data){
			document.getElementById("estimationCd").value=data[0].estimationCd;
			document.getElementById("sendtime").value=data[0].issueDate;
			document.getElementById("poscode").value=data[0].postCode;
			document.getElementById("address1").value=data[0].address1;
			document.getElementById("address2").value=data[0].address2;
			//document.getElementById("staffName").value=data[0].staffName;
			document.getElementById("tel").value=data[0].tel;
			document.getElementById("fax").value=data[0].fax;
			document.getElementById("customerName").value=data[0].customerName;
			document.getElementById("deliveryAddress").value=data[0].deliveryAddress;
			document.getElementById("amount").innerHTML=parseFloat(data[0].amount).toLocaleString();
		//	document.getElementById("estimationId").value=data[0].estimationId;
			document.getElementById("deliveryLimitDays").value=data[0].deliveryLimitDays;
			document.getElementById("customerId").value=data[0].customerId;
			document.getElementById("firstName").value=data[0].title;
			document.getElementById("hjsum").innerHTML=data[0].amount;
			document.getElementById("paymentMethord").value=data[0].paymentCondition;
			document.getElementById("monery").value=data[0].currency;
			document.getElementById("bknote").value=data[0].note;
			document.getElementById("delivery_method").value=data[0].deliveryType;
			document.getElementById("yxtime").value=data[0].estimationOkDays;
			document.getElementById("estimationId").value=data[0].estimationId;
			document.getElementById("hjAmount").value=data[0].amount;
			document.getElementById("langflg").value=data[0].langFlg;

			estimationId = data[0].estimationId;
			getEstmationDetail(estimationId);
			gradeChange();
			freshItemNames();
		}});
	star();
	$.ajax({
		type:"POST",
		url:"xinan/itemname.action?",
		async:"true",
		//data:{"CnName":cname,"JpName":jname,"EnName":ename,"Role":roleid,"UserID":id},
		//"CnName="+cname+"&JpName="+jname+"&EnName="+ename+"&Role="+roleid,
		dataType:"json",
		error:function(data){
			alert("商品名没有加载");
			//alert(arguments[1]);
		}, 
		success:function(data){
			//数组合并
			//var c=data.enNames.concat(data.enShortName);
			//var d=c.concat(data.cnNames);
			//var e=d.concat(data.jpNames);
			//var availableTags= data.jpNames;
			//加载中国商品名
			var availableTags= data.itemCnNames;
			//alert(availableTags);
		    //搜索选择
	    $(".spname").autocomplete({
		      source: availableTags,
		
			  select: function( event, ui ) {
					//alert(ui.item);
					//keydownMsg(evt);
				  //event.preventDefault();
					//alert(document.getElementById("_select").value);
					// event 是当前事件对象
					
					// ui对象仅有一个item属性，它表示当前被选中的菜单项对应的数据源对象
					// 该对象具有label和value属性，以及其它自定义(如果有的话)的属性
				}

  			});
		}
		});
	}

function getEstmationDetail(estimationId){
	$.ajax({
		type:"post",
		url:"xinan/getEstmationDetail.action?",
		async:"true",
		data:{"EstimationId":estimationId},
		dataType:"json",
		error:function(){
			alert("error");
		}, 
		success:function(data){
			var tbodyObj=document.getElementById("sptable");
			var rows=tbodyObj.getElementsByTagName("tr");
			for(var i = 0; i <data.length; i++){ 
				//动态生成表格----------开始-------------
				   var Row = document.getElementById('addcol');
				   var tr = document.createElement("tr");
				   var td1 = document.createElement("td");
				   var td2 = document.createElement("td");
				   var td3 = document.createElement("td");
				   var td4 = document.createElement("td");
				   td4.className="sNoDisplay";
				   var td5 = document.createElement("td");
				   var td6 = document.createElement("td");
				   var td7 = document.createElement("td");
				   var td8 = document.createElement("td");
				   td8.className="je";
				   var td9 = document.createElement("td");
				   var td10 = document.createElement("td");
				   td10.className="sNoDisplay";
				   
				   td2.setAttribute('style', 'vertical-align: top;');
				   td3.setAttribute('style', 'vertical-align: top;');
				   td9.setAttribute('style', 'vertical-align: top;');
				   
				   td1.innerHTML="<input class='iinput' type='text'  onblur='deleNum(this)' />";
				   td2.innerHTML="<textarea class='spname' style='min-width: 130px; max-width: 100px;' onBlur='sponBlur(this)' ></textarea>";
				   td3.innerHTML="<textarea class='area' style=' min-width: 170px; max-width: 120px;'>";
				   td4.innerHTML="<textarea class='area' style='min-width: 120px; max-width: 120px;'></textarea>";
				   td5.innerHTML="<input class='hinput' type='text' onBlur='hinputOnBlur(this)'/>";
				   td6.innerHTML="<input class='iinput' type='text' />";
				   td7.innerHTML="<input class='jinput' type='text' onBlur='jinputOnBlur(this)'/>";
				   td9.innerHTML="<textarea class='area' style='min-width: 100px; max-width: 100px;'></textarea>";
				   td10.innerHTML="<textarea></textarea>";
				   tr.appendChild(td1);
				   tr.appendChild(td2);
				   tr.appendChild(td3);
				   tr.appendChild(td4);
				   tr.appendChild(td5);
				   tr.appendChild(td6);
				   tr.appendChild(td7);
				   tr.appendChild(td8);
				   tr.appendChild(td9);
				   tr.appendChild(td10);
				   Row.appendChild(tr);
				 //动态生成表格----------结束-------------
				var curRows=rows[i+1];
				document.getElementById("typekind").value = data[i].type;
				curRows.getElementsByTagName("td")[0].getElementsByTagName('input')[0].value = i+1;
				curRows.getElementsByTagName("td")[1].getElementsByTagName('textarea')[0].value = data[i].name;
				curRows.getElementsByTagName("td")[2].getElementsByTagName('textarea')[0].value = data[i].partsCd;
				curRows.getElementsByTagName("td")[3].getElementsByTagName('textarea')[0].value = data[i].modelCd;
				curRows.getElementsByTagName("td")[4].getElementsByTagName('input')[0].value = data[i].quantity.toLocaleString();;
				curRows.getElementsByTagName("td")[5].getElementsByTagName('input')[0].value = data[i].unit;
				curRows.getElementsByTagName("td")[6].getElementsByTagName('input')[0].value = data[i].unitPrice.toLocaleString();;
				curRows.getElementsByTagName("td")[7].innerHTML = data[i].amount.toLocaleString();
				curRows.getElementsByTagName("td")[8].getElementsByTagName('textarea')[0].value = data[i].note;
				curRows.getElementsByTagName("td")[9].getElementsByTagName('textarea')[0].value = data[i].itemId;
			}
			//表格最后一行----------开始-------------
			 var Row = document.getElementById('addcol');
			   var tr = document.createElement("tr");
			   var td1 = document.createElement("td");
			   var td2 = document.createElement("td");
			   var td3 = document.createElement("td");
			   var td4 = document.createElement("td");
			   td4.className="sNoDisplay";
			   var td5 = document.createElement("td");
			   var td6 = document.createElement("td");
			   var td7 = document.createElement("td");
			   var td8 = document.createElement("td");
			   td8.className="je";
			   var td9 = document.createElement("td");
			   var td10 = document.createElement("td");
			   td10.className="sNoDisplay";
			   
			   td2.setAttribute('style', 'vertical-align: top;');
			   td3.setAttribute('style', 'vertical-align: top;');
			   td9.setAttribute('style', 'vertical-align: top;');
			   
			   td1.innerHTML="<input class='iinput' type='text'  onblur='deleNum(this)'/>";
			   td2.innerHTML="<textarea class='spname' style='min-width: 130px; max-width: 100px;' onBlur='sponBlur(this)' ></textarea>";
			   td3.innerHTML="<textarea class='area' style=' min-width: 170px; max-width: 120px;'>";
			   td4.innerHTML="<textarea class='area' style='min-width: 120px; max-width: 120px;'></textarea>";
			   td5.innerHTML="<input class='hinput' type='text' onBlur='hinputOnBlur(this)'/>";
			   td6.innerHTML="<input class='iinput' type='text' />";
			   td7.innerHTML="<input class='jinput' type='text' onBlur='jinputOnBlur(this)'/>";
			   td9.innerHTML="<textarea class='area' style='min-width: 100px; max-width: 100px;'></textarea>";
			   td10.innerHTML="<textarea></textarea>";
			   tr.appendChild(td1);
			   tr.appendChild(td2);
			   tr.appendChild(td3);
			   tr.appendChild(td4);
			   tr.appendChild(td5);
			   tr.appendChild(td6);
			   tr.appendChild(td7);
			   tr.appendChild(td8);
			   tr.appendChild(td9);
			   tr.appendChild(td10);
			   Row.appendChild(tr);
			 //表格最后一行----------结束-------------
//			var amount = document.getElementById("amount").innerHTML;
//			var htmlStr =document.getElementById("sptable").innerHTML;
//			for(var i = 0; i <data.length; i++){ 
//				htmlStr = htmlStr+"<tr><td style='text-align:right'>"+
//				+(i+1)+"</td><td  style='text-align:right'>"+
//				data[i].name+"</td><td  style='text-align:right'>"+
//				data[i].type+"</td><td  style='text-align:right'>"+
//				data[i].modelCd+"</td><td style='text-align:right'><input  type='text' value='"+
//				data[i].quantity+"' style='min-width: 120px; max-width: 120px;text-align:right;border:0px' onblur='quChange(this.value);'></td><td style='text-align:right'>"+
//				data[i].unit+"</td><td  style='text-align:right'>"+
//				data[i].unit+"</td><td style='text-align:right'>"+
//				data[i].unitPrice.toLocaleString()+"</td><td style='text-align:right' value=''>"+data[i].note+"</td></tr>";
//				}
////			htmlStr = htmlStr + "<tr height='10px'><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>"
//			document.getElementById("sptable").innerHTML=htmlStr;
			
			   freshItemNames();
				$('.spname').autosize({
					append: "\n"
				});
				$('.area').autosize({
					append: "\n"
				});
				adjustH(document.getElementById('paymentMethord'));
	}});
}

function save(){
	var order_monery = document.getElementById("order_monery").value;
	var amount = document.getElementById("amount").innerHTML;
	var orderNo = document.getElementById("orderCd").value;
	if(orderNo == null || orderNo==""){
		alert("请正确输入订单号");
		return;
	}
	if(order_monery != amount){
		alert("订单金额与受注书金额不一致");
		return;
	}
	//客户ID
	var CustomerId = $("#customerId").val();
	if(CustomerId == ""){
		alert("请填写顾客信息");
		return;
	}
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var rowSize=0;
	for(var i=1;i<=rows.length-2;i++){
	var curRows=rows[i];
	//获得第二列的单元格
	var tspn=curRows.getElementsByTagName("td")[1];
	//获得单元格里面的值
	var tspnv=tspn.getElementsByTagName("textarea")[0].value;
	if(tspnv != ""){
		rowSize+=1;
		//数量value
		var tnum = curRows.getElementsByTagName("td")[4];
		var tnumv = tnum.getElementsByTagName("input")[0].value.split(",").join('');
		//単価value
		var tprice = curRows.getElementsByTagName("td")[6];
		var tpricev = tprice.getElementsByTagName("input")[0].value.split(",").join('');
		if(tnumv == ""||tpricev==""){
			alert("请将商品信息填写完整");
			return;
		}
	}
	}
	if(rowSize == 0){
		alert("请填写商品信息");
		return;
	}
	saveSheet();
}

//保存
function saveSheet(){
	//orderNo
	var orderCd = document.getElementById("orderCd").value;
	
	var estimationCd = $("#estimationCd").val();
	//発行日
	var IssueDate = $("#sendtime").val();
	//客户ID
	var CustomerId = $("#customerId").val();
	//客户姓名
	var CustomerName = $("#customerName").val();
	//客户邮编
	var PostCode = $("#poscode").val();
	//客户地址1
	var Address1 = $("#address1").val();
	//客户地址2
	var Address2 = $("#address2").val();
	//制品名
	var Title = $("#firstName").val();
	//担当者姓名
	var StaffName = $("#StaffName").val();
	//电话
	var Tel = $("#tel").val();
	//fax
	var Fax = $("#fax").val();
	//总价     
	var Amount = document.getElementById("hjAmount").value;
	if(Amount==""){
		Amount = 0;
	}
	//交货方式
	var DeliveryType = $("#delivery_method").val();
	//交货场所
	var DeliveryAddress = $("#deliveryAddress").val();
	//交货日期
	var DeliveryDate = $("#deliveryLimitDays").val();
	//お支払条件
	var PaymentCondition = $("#paymentMethord").val();
	//支付方式
//	var PaymentMethord = $("#PaymentMethord").val();
	//折扣数额
//	var DiscountAmount = $("#DiscountAmount").val();
//	if(DiscountAmount==""){
//		DiscountAmount = 0;
//	}
	//货币类型
	var Currency = $("#monery").val();
	
	//备注
	var Note = $("#bknote").val();
	// estimationid
	var EstimationId = document.getElementById("estimationId").value;
	var langflg = document.getElementById("langflg").value;
	
	var yxtime = document.getElementById("yxtime").value;
	var urlstr="&OrderCd="+orderCd+"&IssueDate="+IssueDate;
	urlstr=urlstr+"&estimationId="+EstimationId;
	urlstr=urlstr+"&CustomerId="+CustomerId;
	urlstr=urlstr+"&CustomerName="+CustomerName;
	urlstr=urlstr+"&PostCode="+PostCode;
	urlstr=urlstr+"&Address1="+Address1;
	urlstr=urlstr+"&Address2="+Address2;
	urlstr=urlstr+"&Title="+Title;
	urlstr=urlstr+"&StaffName="+StaffName;
	urlstr=urlstr+"&Tel="+Tel;
	urlstr=urlstr+"&Fax="+Fax;
	urlstr=urlstr+"&Amount="+Amount;
	urlstr=urlstr+"&DeliveryType="+DeliveryType;
	urlstr=urlstr+"&DeliveryAddress="+DeliveryAddress;
	urlstr=urlstr+"&DeliveryDate="+DeliveryDate;
	urlstr=urlstr+"&PaymentCondition="+PaymentCondition;
//	urlstr=urlstr+"&PaymentMethord="+PaymentMethord;
	//urlstr=urlstr+"&DiscountAmount="+DiscountAmount;
	urlstr=urlstr+"&Currency="+Currency;
	urlstr=urlstr+"&Note="+Note;
	urlstr=urlstr+"&estimationCd="+estimationCd;

	urlstr=urlstr+"&yxtime="+yxtime;
	urlstr=urlstr+"&langFlg="+langflg;
    $.ajax({
		type:"post",
		url:"xinan/addEstmationConfirmSheet.action",
		async:"true",
		data:urlstr,
		dataType:"text",
		error:function(data){
			//alert(123);
			alert("sheet添加失败");
		},
		success: function(data){
			alert("保存成功")
			var str = data;
			document.getElementById("estimationId").value = str;
			saveDetail(str);
		}
	});
	
	//savems();
}

//保存明细
function saveDetail(e){
	//見積書番号
	var EstimationId = "EstimationId"
	//商品
	var sp="Name";
	//说明
	var desc="PartsCd";
	//商品种类(素材等）
	var type = "Type"
	//商品型番
	var kind="modelCd"
	//数量
	var num = "Quantity";
	//单位
	var unit = "Unit";
	//単価
	var price = "UnitPrice";
	//金額
	var je = "Amount";
	//备考
	var note = "Note";
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	//商品Id
    var	id="itemId"

	//for(var i=1;i<=rows.length-2;i++){
	var i=1
	//関数fooを再帰実装
	var foo = function(){
		var ulstr="";
		var curRows=rows[i];
		//获得第二列的单元格
		var tspn=curRows.getElementsByTagName("td")[1];
		//获得单元格里面的值
		var tspnv=tspn.getElementsByTagName("textarea")[0].value;
		if(tspnv != ""){
			//商品说明value
			var tdesc = curRows.getElementsByTagName("td")[2];
			var tdescv = tdesc.getElementsByTagName("textarea")[0].value;
			//商品种类(素材等）
			var typekind = document.getElementById("typekind").value;
			//商品型番value
			var tkind = curRows.getElementsByTagName("td")[3];
			var tkindv = tkind.getElementsByTagName("textarea")[0].value;
			//数量value
			var tnum = curRows.getElementsByTagName("td")[4];
			var tnumv = tnum.getElementsByTagName("input")[0].value.split(",").join('');
			//单位value
			var tunit = curRows.getElementsByTagName("td")[5];
			var tunitv = tunit.getElementsByTagName("input")[0].value;
			//単価value
			var tprice = curRows.getElementsByTagName("td")[6];
			var tpricev = tprice.getElementsByTagName("input")[0].value.split(",").join('');
			//备考value
			var tnote = curRows.getElementsByTagName("td")[8];
			var tnotev = tnote.getElementsByTagName("textarea")[0].value;
			//商品Id
			var titemId = curRows.getElementsByTagName('td')[9];
			var titemIdv = titemId.getElementsByTagName('textarea')[0].value;

			ulstr = ulstr +"&"+EstimationId+"="+e;
			ulstr = ulstr +"&"+type+"="+typekind;
			ulstr = ulstr +"&"+sp+"="+tspnv;
			ulstr = ulstr +"&"+desc+"="+tdescv;
			ulstr = ulstr +"&"+kind+"="+tkindv;
			ulstr = ulstr +"&"+num+"="+tnumv;
			ulstr = ulstr +"&"+unit+"="+tunitv;
			ulstr = ulstr +"&"+price+"="+tpricev;
			ulstr = ulstr +"&"+je+"="+tnumv*tpricev;
			ulstr = ulstr +"&"+note+"="+tnotev;
			ulstr = ulstr +"&"+id+"="+titemIdv;
			 $.ajax({
					type:"post",
					url:"xinan/addDetail.action",
					async:"true",
					data:ulstr,
					dataType:"text",
					error:function(data){
						//alert(123);
						alert("detail添加失败");
					},
					success: function(data){
						document.getElementById("saveTag").value ="true";
					}
						
					//done()メソッドが実行結果の順が正しいです	
			 }).done(function(data) {
				 if(i<=rows.length-2){
					 i=i+1;
					 foo();
					 }
				 });
		}
		
	};
	//関数fooを再帰呼び出
	foo();
}

function orderSheet(){
	var order_monery = document.getElementById("order_monery").value;
	var amount = document.getElementById("amount").innerHTML;
	var orderNo = document.getElementById("orderCd").value;
	var saveTag = document.getElementById("saveTag").value;
	if(orderNo == null || orderNo==""){
		alert("请正确输入订单号");
		return;
	}
	if(order_monery != amount){
		alert("订单金额与受注书金额不一致");
		return;
	}
	if(saveTag != "true"){
		alert("请保存受注书后再进行后续操作");
		return;
	}
	var id = document.getElementById("estimationId").value.trim();
	var url = "xinan/ordermake.action?EstimationId="+id+"&langFlg=JP";
	location.href = url;
}

function quChange(e){
	var sum = e;
	var count = document.getElementById("order_monery").value;
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	alert(rows.length);
}

function gradeChange(){
	var monery = document.getElementById("monery").value;
	if(monery=="JP"){
		document.getElementById("monery1").innerHTML="￥";
		document.getElementById("money2").innerHTML="￥";
	};
	if(monery=="US"){
		document.getElementById("monery1").innerHTML="$";
		document.getElementById("money2").innerHTML="$";
	};
	if(monery=="CN"){
		document.getElementById("monery1").innerHTML="R";
		document.getElementById("money2").innerHTML="R";
	};
}

//选中商品名字其他信息自动填入其他对应的表格中	
function sponBlur(evt) {
		
      var e = evt.value;
      var eparent = evt.parentNode;
      var tparent = eparent.parentNode;
      var tdid = tparent.getElementsByTagName('td')[0].getElementsByTagName('input')[0];
	  var tname = tparent.getElementsByTagName('td')[1].getElementsByTagName('textarea')[0];
      var tjpDesc = tparent.getElementsByTagName('td')[2].getElementsByTagName('textarea')[0];
      var ttype = tparent.getElementsByTagName('td')[3].getElementsByTagName('textarea')[0];
      var tje = tparent.getElementsByTagName('td')[6].getElementsByTagName('input')[0];
	  //商品ID
	  var titemId = tparent.getElementsByTagName('td')[9].getElementsByTagName('textarea')[0];
	  
	  $.ajax({
			type:"POST",
			url:"xinan/findItem.action?cnName="+e,
			async:"false",
			dataType:"json",
			error:function(){
			},  
			success:function(data){
				//判断商品名是否重复
				if (data.itemId == "" || checkDouble(data.itemId) == true){
					return;
					}

				var langflg = document.getElementById("langflg").value;
				//商品
				if(langflg == "CN"){
					if(data.cnName){
					tname.value=data.cnName;
					}
					if(data.cnDesc){
						tjpDesc.value=data.cnDesc;
					}
				}
				if(langflg == "JP"){
					if(data.jpName){
						tname.value=data.jpName;
					}
					if(data.jpDesc){
						tjpDesc.value=data.jpDesc;
					}
				}

				//商品型番
				if(data.modelCd){
					ttype.value=data.modelCd;
				}
				//商品Id
				if(data.itemId){
					titemId.value=data.itemId;
				}
				//番号自动生成
				changeNum();
				//EstmationSheet();
				//判断是否增加表格
				addCol();
			}
	    });
		//判断商品名是否重复
		function checkDouble(Id){
			var tbodyObj=document.getElementById("sptable");
			var rows=tbodyObj.getElementsByTagName("tr");
			 for(var i=1;i<=rows.length-2;i++){
				 var curRows=rows[i];
	  			 //品名
	  		     var ttext=curRows.getElementsByTagName("td")[9];
	  		     var ttextarea=ttext.getElementsByTagName("textarea")[0].value;
	  		     var tnum=curRows.getElementsByTagName("td")[0];	  
	  		     if(Id !="" && ttextarea.trim()!="" && Id == ttextarea && tparent.rowIndex != i){
				  alert("商品已经存在清单中！");
				  e="";
				  tparent.getElementsByTagName("td")[1].getElementsByTagName("textarea")[0].value="";
				  return true;
				  };
			};
	  };
}

//番号自动生成	
function changeNum(){
	var bnum=0;
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var title=0;
	for(var i=1;i<=rows.length-2;i++){
		var curRows=rows[i];
		var ttext=curRows.getElementsByTagName("td")[1];
		var ttextarea=ttext.getElementsByTagName("textarea")[0].value;
		var tnum=curRows.getElementsByTagName("td")[0];
		if(ttextarea.trim()!=""){
			tnum.getElementsByTagName("input")[0].value=bnum+1;
			bnum = bnum + 1;
		};
	};
	for(var i=1;i<=rows.length-2;i++){
		//alert(1888);
		var curRows=rows[i];
		var ttext=curRows.getElementsByTagName("td")[1];
		var ttextarea=ttext.getElementsByTagName("textarea")[0].value;
		//alert(ttextarea);
		var tnum=curRows.getElementsByTagName("td")[0];
		var ttnumber = tnum.getElementsByTagName("input")[0].value;
		//alert(ttnumber);
		if(ttnumber=="1"){
			document.getElementById("firstName").value=ttextarea;
			break;
		};
	};
	
}
//增加表格
function addCol(){
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var title=0;
	var i=rows.length-2;
	var text= rows[i].getElementsByTagName("td")[1].getElementsByTagName("textarea")[0].value;
	if(text!=""){
		//alert(123);
		var Row = document.getElementById('addcol');
		   var tr = document.createElement("tr");
		   var td1 = document.createElement("td");
		   var td2 = document.createElement("td");
		   var td3 = document.createElement("td");
		   var td4 = document.createElement("td");
		   td4.className="sNoDisplay";
		   var td5 = document.createElement("td");
		   var td6 = document.createElement("td");
		   var td7 = document.createElement("td");
		   var td8 = document.createElement("td");
		   td8.className="je";
		   var td9 = document.createElement("td");
		   var td10 = document.createElement("td");
		   td10.className="sNoDisplay";
		   
		   td1.innerHTML="<input class='iinput' type='text'  onblur='deleNum(this)'/>";
		   td2.innerHTML="<textarea class='spname' style='min-width: 130px; max-width: 100px;' onBlur='sponBlur(this)' ></textarea>";
		   td3.innerHTML="<textarea class='area' style=' min-width: 170px; max-width: 120px;'>";
		   td4.innerHTML="<textarea class='area' style='min-width: 120px; max-width: 120px;'></textarea>";
		   td5.innerHTML="<input class='hinput' type='text' onBlur='hinputOnBlur(this)'/>";
		   td6.innerHTML="<input class='iinput' type='text' />";
		   td7.innerHTML="<input class='jinput' type='text'onBlur='jinputOnBlur(this)' />";
		   td9.innerHTML="<textarea class='area' style='min-width: 100px; max-width: 100px;'></textarea>";
		   td10.innerHTML="<textarea></textarea>";
		   tr.appendChild(td1);
		   tr.appendChild(td2);
		   tr.appendChild(td3);
		   tr.appendChild(td4);
		   tr.appendChild(td5);
		   tr.appendChild(td6);
		   tr.appendChild(td7);
		   tr.appendChild(td8);
		   tr.appendChild(td9);
		   tr.appendChild(td10);
		   Row.appendChild(tr);
		   
	}
	//EstmationSheet();
	freshItemNames();
}
//删除番号对应的这一条信息都删除
function deleNum(evt){
	evt.style.border="0px";
	var e = evt.value;
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
    var tname = tparent.getElementsByTagName('td')[1].getElementsByTagName('textarea')[0];
    var tdesc = tparent.getElementsByTagName('td')[2].getElementsByTagName('textarea')[0];
    var tkind = tparent.getElementsByTagName('td')[3].getElementsByTagName('textarea')[0];
    var tnumber = tparent.getElementsByTagName('td')[4].getElementsByTagName('input')[0];
    var tunit = tparent.getElementsByTagName('td')[5].getElementsByTagName('input')[0];
    var tprice = tparent.getElementsByTagName('td')[6].getElementsByTagName('input')[0];
    var tje = tparent.getElementsByTagName('td')[7];
    var tnote = tparent.getElementsByTagName('td')[8].getElementsByTagName('textarea')[0];
    var titemId = tparent.getElementsByTagName('td')[9].getElementsByTagName('textarea')[0];
    
	if(e.trim()==""){
		tname.value="";
		tdesc.value="";
		tkind.value="";
		tnumber.value="";
		tunit.value="";
		tprice.value="";
		tje.innerHTML="";
		tnote.value="";
		titemId.value="";

	}
	changeNum();
	sum(evt);
	
	//
}

function freshItemNames(){
	$.ajax({
		type:"POST",
		url:"xinan/itemname.action?",
		async:"true",
		//data:{"CnName":cname,"JpName":jname,"EnName":ename,"Role":roleid,"UserID":id},
		//"CnName="+cname+"&JpName="+jname+"&EnName="+ename+"&Role="+roleid,
		dataType:"json",
		error:function(data){
			alert("商品名没有加载");
			//alert(arguments[1]);
		}, 
		success:function(data){
			//数组合并
			//var c=data.enNames.concat(data.enShortName);
			//var d=c.concat(data.cnNames);
			//var e=d.concat(data.jpNames);
			//var availableTags= data.jpNames;
			//加载中国商品名
			var availableTags= data.itemCnNames;
			//alert(availableTags);
		    //搜索选择
	    $(".spname").autocomplete({
		      source: availableTags,
		
			  select: function( event, ui ) {
					//alert(ui.item);
					//keydownMsg(evt);
				  //event.preventDefault();
					//alert(document.getElementById("_select").value);
					// event 是当前事件对象
					
					// ui对象仅有一个item属性，它表示当前被选中的菜单项对应的数据源对象
					// 该对象具有label和value属性，以及其它自定义(如果有的话)的属性
				}

  			});
		}
		});
}
function keyUp(e){
	e=e.replace(/,/g,'');
	if(e == "") 
		e = 0;
	document.getElementById("order_monery").value = parseFloat(e).toLocaleString();
}

function hinputOnBlur(e){
	 $(e).parseNumber({format:"#,###", locale:""});
	   $(e).formatNumber({format:"#,###", locale:""});
	   sum(e);
}

function jinputOnBlur(e){
	 var tag = document.getElementById("monery").value;
	 if(tag == "JP"){
		 $(e).parseNumber({format:"#,###", locale:""});
		   $(e).formatNumber({format:"#,###", locale:""});
	 }else{
		 $(e).parseNumber({format:"#,###.00", locale:""});
		   $(e).formatNumber({format:"#,###.00", locale:""});
	 }
	 sum(e);
}