//日期初始化
	function star(){
		var year;
		var month;
		var day;
		var time;
		var d = new Date();
		year=d.getFullYear();
		month=d.getMonth()+1;
		var nextmonth =d.getMonth()+2;
		day=d.getDate();
		if(month<10){
		month="0"+month;
		}
		if(day<10){
		day="0"+day;
		}
		time=year+"-"+month+"-"+day;
		var time1=year+"-"+nextmonth+"-"+day;
		document.getElementById("sendtime").value=time;
//		document.getElementById("narutime").value=time;
		document.getElementById("yxtime").value=time1;
		var tonclick=document.getElementById("ctd");
		tonclick.onclick=function(){
			document.getElementById("_select").style.display='block';
		};
		
		}
	//总计合计
	function sum(e){
		e.style.border="0px";
		var tbodyObj=document.getElementById("sptable");
		var rows=tbodyObj.getElementsByTagName("tr");
		var hjsum = document.getElementById("hjsum");
		var zsum = document.getElementById("zsum");
		var sumje = document.getElementById("sumje");
		//var trsum;
		//var subsum=document.getElementById("subtd");
		//var taxsum=document.getElementById("tax");
		//var ttsum=document.getElementById("tsum");
		//var tmon=document.getElementById("mon");
		var trsum=0;
		for(var i=1;i<=rows.length-1;i++){
			//总计
			var curRows=rows[i];
			var tdprice=curRows.getElementsByTagName("td")[4];
			var tdprice1=curRows.getElementsByTagName("td")[6];
			var tdprice2=curRows.getElementsByTagName("td")[7];
			var tinput=tdprice.getElementsByTagName("input")[0].value;
			var tinput1=tdprice1.getElementsByTagName("input")[0].value;
			//alert(tinput);
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
//			if(""==tdsum.trim()){
//				tdsum = 0;
//			}
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
				zsum.innerHTML = trsum;
				sumje.value=trsum;
			}else{
				hjsum.innerHTML=parseFloat(trsum).toLocaleString();
				zsum.innerHTML=parseFloat(trsum).toLocaleString();
				sumje.value=trsum;
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
	
	
	/*$(function() {*/
			
//見積書页面初始化
function EstmationSheet(){
//		alert(123);
		//customername
		$.ajax({
					type:"POST",
					url:"xinan/suppliernames.action?",
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
				    $("#_select").autocomplete({
				      source: availableTags
		  			});
				   
					}
		 });
		//見積明細
		//#_select
		$.ajax({
			type:"POST",
			url:"xinan/itemname.action?",
			async:"false",
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
		//alert(strC);
		star();
  	}
	//回车公司 名字地址变更
	function changeName(evt){
		 evt = (evt) ? evt : ((window.event) ? window.event : "");
	      var keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
	      var e=document.getElementById("_select").value;
	      if (keyCode == 13) {
	    	  $.ajax({
					type:"POST",
					url:"xinan/supplierAddress.action?enShortName="+e,
					async:"true",
					dataType:"json",
					success:function(data){
						$("#_select").val(data[0].cnName);
						$("#tdmail").val("〒"+data[0].postCd);
						$("#address").val(data[0].address1+data[0].address2);
						$("#bilu").val(data[0].address3);
						$("#supplierId").val(data[0].customerId);
					}
	    	  });
				
			
	      }
	}
	//失去焦点公司 名字地址变更
	function keydownMsg(evt) {
			     // evt = (evt) ? evt : ((window.event) ? window.event : "");
			    //  keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
			      var e=document.getElementById("_select").value;
			    //  if (keyCode == 13) {
			    	  //alert("开始");
			    	  //alert(e);
			    	  $.ajax({
							type:"POST",
							url:"xinan/supplierAddress.action?enShortName="+e,
							async:"false",
							//data:{"CnName":cname,"JpName":jname,"EnName":ename,"Role":roleid,"UserID":id},
							//"CnName="+cname+"&JpName="+jname+"&EnName="+ename+"&Role="+roleid,
							dataType:"json",
							 error:function(){
								alert(123);
								alert(arguments[1]);
							},  
							success:function(data){
								$("#_select").val(data[0].cnName);
								$("#tdmail").val("〒"+data[0].postCd);
								$("#address").val(data[0].address1+data[0].address2);
								$("#bilu").val(data[0].address3);
								$("#supplierId").val(data[0].customerId);
							}
			    	  });
			      }
//选中商品名字其他信息自动填入其他对应的表格中	
	function sponBlur(evt) {
			
	      var e = evt.value;
	      var eparent = evt.parentNode;
	      var tparent = eparent.parentNode;
	      var tdid = tparent.getElementsByTagName('td')[0].getElementsByTagName('input')[0];
	      var tjpDesc = tparent.getElementsByTagName('td')[2].getElementsByTagName('textarea')[0];
	      var ttype = tparent.getElementsByTagName('td')[3].getElementsByTagName('textarea')[0];
	      var tje = tparent.getElementsByTagName('td')[6].getElementsByTagName('input')[0];
	      //判断商品名是否重复
	      var tbodyObj=document.getElementById("sptable");
	  	  var rows=tbodyObj.getElementsByTagName("tr");
	      for(var i=1;i<=rows.length-2;i++){
	  		var num = 0;
	  		var curRows=rows[i];
	  		//品名
	  		var ttext=curRows.getElementsByTagName("td")[1];
	  		var ttextarea=ttext.getElementsByTagName("textarea")[0].value;
	  		var tnum=curRows.getElementsByTagName("td")[0];
	  		for(var j=1;j<=rows.length-2;j++){
	  			var curRows1=rows[j];
	  			var ttext1=curRows1.getElementsByTagName("td")[1];
	  			var ttextarea1=ttext1.getElementsByTagName("textarea")[0].value;
	  			if(ttextarea1.trim()!="" && ttextarea.trim()!="" && ttextarea1 == ttextarea){
	  				num = num + 1;
	  			};
	  			if(num == 2){
	  				alert("商品已经存在清单中！");
	  				e="";
	  				curRows1.getElementsByTagName("td")[1].getElementsByTagName("textarea")[0].value="";
	  				return;
	  			};

	  		};
	      
	      };
	      //番号自动生成
	      changeNum();
	      //EstmationSheet();
	      //判断是否增加表格
	      addCol();
	      $.ajax({
					type:"POST",
					url:"xinan/findItem.action?cnName="+e,
					async:"false",
					dataType:"json",
					 error:function(){
						alert(123);
						alert(arguments[1]);
					},  
					success:function(data){
						//商品说明
						tjpDesc.value=data[0].partsCd;
						//商品型番
						ttype.value=data[0].modelCd;
						
					}
	    	  });
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
		   var td5 = document.createElement("td");
		   var td6 = document.createElement("td");
		   var td7 = document.createElement("td");
		   var td8 = document.createElement("td");
		   td8.className="je";
		   var td9 = document.createElement("td");
		   
		   td1.innerHTML="<input class='iinput' type='text'  onblur='deleNum(this)'/>";
		   td2.innerHTML="<textarea class='spname' style='width:100px;' onBlur='sponBlur(this)' ></textarea>";
		   td3.innerHTML="<textarea class='area' style=' width:120px;'>";
		   td4.innerHTML="<textarea class='area' style='width:80px;'></textarea>";
		   td5.innerHTML="<input class='hinput' type='text' onblur='sum(this)'/>";
		   td6.innerHTML="<input class='iinput' type='text' />";
		   td7.innerHTML="<input class='jinput' type='text' onblur='sum(this)'/>";
		   td9.innerHTML="<textarea class='area' style='width:100px;'></textarea>";
		   tr.appendChild(td1);
		   tr.appendChild(td2);
		   tr.appendChild(td3);
		   tr.appendChild(td4);
		   tr.appendChild(td5);
		   tr.appendChild(td6);
		   tr.appendChild(td7);
		   tr.appendChild(td8);
		   tr.appendChild(td9);
		   Row.appendChild(tr);
		   
	}
	//EstmationSheet();
}
//
//番号自动生成	
function changeNum(){
	var bnum=0;
	var tbodyObj=document.getElementById("sptable");
	var rows=tbodyObj.getElementsByTagName("tr");
	var title=0;
	for(var i=1;i<=rows.length-2;i++){
		var num = 0;
		var curRows=rows[i];
		//品名
		var ttext=curRows.getElementsByTagName("td")[1];
		var ttextarea=ttext.getElementsByTagName("textarea")[0].value;
		var tnum=curRows.getElementsByTagName("td")[0];
		if(ttextarea.trim()!=""){
			tnum.getElementsByTagName("input")[0].value=bnum+1;
			bnum = bnum + 1;
		};
	};
	//判断是否为第一个名字如果是把名字給title
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
    
	if(e.trim()==""){
		tname.value="";
		tdesc.value="";
		tkind.value="";
		tnumber.value="";
		tunit.value="";
		tprice.value="";
		tje.innerHTML="";
		tnote.value="";
		
	}
	changeNum();
	sum(evt);
	
	//
}
//
function gradeChange(){
	var monery = document.getElementById("monery").value;
	if(monery=="1"){
		document.getElementById("monery1").innerHTML="￥";
	};
	if(monery=="2"){
		document.getElementById("monery1").innerHTML="$";
	};
	if(monery=="3"){
		document.getElementById("monery1").innerHTML="円";
	};
}
//保存
function save(){
	//报价单踩番
	var EstimationId = $("#EstimationId").val();
	//报价单号
	var EstimationCd = $("#EstimationCd").val();
	//発行日
	var IssueDate = $("#sendtime").val();
	//客户ID
	var CustomerId = $("#customerId").val();
	//客户姓名
	var CustomerName = $("#_select").val();
	//客户邮编
	var PostCode = $("#tdmail").val();
	//客户地址1
	var Address1 = $("#address").val();
	//客户地址2
	var Address2 = $("#bilu").val();
	//制品名
	var Title = $("#firstName").val();
	//担当者姓名
	var StaffName = $("#StaffName").val();
	//电话
	var Tel = $("#Tel").val();
	//fax
	var Fax = $("#Fax").val();
	//总价     
	var Amount = $("#sumje").val();
	if(Amount==""){
		Amount = 0;
	}
	//交货方式
	var DeliveryType = $("#delivery_method").val();
	//交货场所
	var DeliveryAddress = $("#DeliveryAddress").val();
	//交货日期
	var DeliveryDate = $("#narutime").val();
	//报价单有效日
	var EstmationOKDays = $("#yxtime").val();
	//お支払条件
	var PaymentCondition = $("#PaymentCondition").val();
	//支付方式
	var PaymentMethord = $("#PaymentMethord").val();
	//折扣数额
//	var DiscountAmount = $("#DiscountAmount").val();
//	if(DiscountAmount==""){
//		DiscountAmount = 0;
//	}
	//货币类型
	var Currency = $("#monery").val();
	
	//备注
	var Note = $("#bknote").val();
	
	//var EstimationId = $("#EstimationId").val();
	var urlstr="&IssueDate="+IssueDate;
	urlstr=urlstr+"&EstimationId="+EstimationId;
	urlstr=urlstr+"&EstimationCd="+EstimationCd;
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
	urlstr=urlstr+"&EstmationOKDays="+EstmationOKDays;
	urlstr=urlstr+"&PaymentCondition="+PaymentCondition;
	urlstr=urlstr+"&PaymentMethord="+PaymentMethord;
	//urlstr=urlstr+"&DiscountAmount="+DiscountAmount;
	urlstr=urlstr+"&Currency="+Currency;
	urlstr=urlstr+"&Note="+Note;
	
	//alert(urlstr);
    $.ajax({
		type:"post",
		url:"xinan/addEstmationSheet.action",
		async:"true",
		data:urlstr,
		dataType:"text",
		error:function(data){
			//alert(data);
			alert("未找到对应的客户信息请添加！");
		},
		success: function(data){
			//alert(data);
			var str = data.split("@");
			var estimationId = str[0];
			var estimationCd = str[1];
			$("#EstimationId").val(estimationId.trim());
			$("#EstimationCd").val(estimationCd);
			//alert(str);
			alert("保存成功！");
				saveDetail(estimationId.trim());
				//document.getElementById("saveBT").value="受注确定";
//				document.getElementById('saveBT').onclick = function(){ 
//					estimationConfirm(); 
//					}; 
			
		}
	});
	
	//savems();
}
//打印
	function myPrint(obj){
//			document.getElementById("endtime1").style.display="block";
//			document.getElementById("endtime1").innerHTML=document.getElementById("endtime").value;
//			document.getElementById("ntime1").style.display="block";
//			document.getElementById("ntime1").innerHTML=document.getElementById("ntime").value;
//			document.getElementById("ytime1").style.display="block";
//			document.getElementById("ytime1").innerHTML=document.getElementById("ytime").value;
//			document.getElementById("endtime").style.display="none";
//			document.getElementById("ntime").style.display="none";
//			document.getElementById("ytime").style.display="none";
//			//document.getElementById("_select").style.border="0px";
//
//			var newWindow=window.open("1","123");//打印窗口要换成页面的url
//			var docStr = obj.innerHTML;
//			newWindow.document.write(docStr);
//			newWindow.document.close();
//			//newWindow.save();
//			newWindow.print();
//			document.getElementById("endtime1").style.display="none";
//			document.getElementById("endtime").style.display="block";
//			document.getElementById("ntime1").style.display="none";
//			document.getElementById("ntime").style.display="block";
//			document.getElementById("ytime1").style.display="none";
//			document.getElementById("ytime").style.display="block";
//			//document.getElementById("_select").style.border="1px";
//			
//			newWindow.close();
		}
