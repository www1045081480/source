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
//		tonclick.onclick=function(){
//			document.getElementById("_select").style.display='block';
//		};
		
		}
	//总计合计
	function sum(e){
		e.style.border="0px";
		if(e.value == "") return;
//		e.value = parseFloat(e.value.split(",").join("")).toLocaleString();
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
		//alert(123);
		//customername
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
				    $("#_select").autocomplete({
				      source: availableTags
		  			});
				   
					}
		 });
		
		//見積明細
		$.ajax({
			type:"POST",
			url:"xinan/itemname.action?",
			async:"false",
			dataType:"json",
			error:function(data){
				alert("商品名没有加载");
			}, 
			success:function(data){
				//加载中国商品名
				var availableTags= data.itemCnNames;
			    //搜索选择
		    $(".spname").autocomplete({
			      source: availableTags,
				  select: function( event, ui ) {
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
					url:"xinan/customeraddress.action?enShortName="+e,
					async:"true",
					dataType:"json",
					success:function(data){
						if(data[0].cnName){
							$("#_select").val(data[0].cnName);
						}
//						else{
//							$("#_select").val(data[0].jpName);
//							$("#address").val(data[0].address1);
//							$("#bilu").val(data[0].address2+data[0].address3);
//						}
						$("#tdmail").val("〒"+data[0].postCd);
						$("#address").val(data[0].address3+data[0].address2);
						$("#bilu").val(data[0].address1);
						$("#customerId").val(data[0].customerId);
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
							url:"xinan/customeraddress.action?enShortName="+e,
							async:"false",
							//data:{"CnName":cname,"JpName":jname,"EnName":ename,"Role":roleid,"UserID":id},
							//"CnName="+cname+"&JpName="+jname+"&EnName="+ename+"&Role="+roleid,
							dataType:"json",
							 error:function(){
//								alert(123);
//								alert(arguments[1]);
							},  
							success:function(data){
								if(data[0].cnName){
									$("#_select").val(data[0].cnName);
								}
//								else{
//									$("#_select").val(data[0].jpName);
//									$("#address").val(data[0].address1);
//									$("#bilu").val(data[0].address2+data[0].address3);
//								}
								$("#tdmail").val("〒"+data[0].postCd);
								$("#address").val(data[0].address3+data[0].address2);
								$("#bilu").val(data[0].address1);
								$("#customerId").val(data[0].customerId);
							}
			    	  });
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
	      //単位：
	      var unit = tparent.getElementsByTagName('td')[5].getElementsByTagName('input')[0];
	      if (unit.value =="")
	    	  unit.value = "套";
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
						//商品
						if(data.cnName){
							tname.value=data.cnName;
						}
						//商品说明
						if(data.cnDesc){
							tjpDesc.value=data.cnDesc;
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
		   
		   td2.setAttribute('style', 'vertical-align: top;');
		   td3.setAttribute('style', 'vertical-align: top;');
		   td4.setAttribute('style', 'vertical-align: top;');
		   td5.setAttribute('style', 'vertical-align: top;');
		   td6.setAttribute('style', 'vertical-align: top;');
		   td7.setAttribute('style', 'vertical-align: top;');
		   td8.setAttribute('style', 'vertical-align: top;');
		   td9.setAttribute('style', 'vertical-align: top;');
		   
		   td1.innerHTML="<input class='iinput' type='text'  onblur='deleNum(this)'/>";
		   td2.innerHTML="<textarea class='spname' style='width:130px;' onBlur='sponBlur(this)' ></textarea>";
		   td3.innerHTML="<textarea class='area' style=' width:170px;'>";
		   td4.innerHTML="<textarea class='area' style='width:80px;'></textarea>";
		   td5.innerHTML="<input class='hinput' type='text' />";
		   td6.innerHTML="<input class='iinput' type='text' />";
		   td7.innerHTML="<input class='jinput' type='text' />";
		   td9.innerHTML="<textarea class='area' style='width:100px;'></textarea>";
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
	freshItemNames();
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
//
function gradeChange(){
	var monery = document.getElementById("monery").value;
	if(monery=="JP"){
		document.getElementById("monery1").innerHTML="￥";
	};
	if(monery=="US"){
		document.getElementById("monery1").innerHTML="$";
	};
	if(monery=="CN"){
		document.getElementById("monery1").innerHTML="R";
	};
}

function checkApproveStatus(){
	var id = document.getElementById("EstimationId").value;
	if(id != ""){
	$.ajax({
		type:"post",
		url:"xinan/readEstimationApproveStatus.action?",
		async:"true",
		data:{"EstimationId":id},
		dataType:"json",
		error:function(){
			alert("error");
			return;
		}, 
		success:function(data){
			if(data.presidentId || data.vicePresidentId){
				alert("报价已经承认，无法进行修改");
			}else{
				save();
			}
			}
		});
	}else{
		save();
	}
}
//保存
function save(){
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
	//报价单踩番
	var EstimationId = $("#EstimationId").val();
	//报价单号
	var EstimationCd = $("#EstimationCd").val();
	//発行日
	var IssueDate = $("#sendtime").val();
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
//	var StaffName = $("#StaffName").val();
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
	var PaymentCondition = encodeURI($("#PaymentCondition").val());
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
	
	var userId = $("#userId").val();
	
	var lang_flg =  $("#lang_flg").val();
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
//	urlstr=urlstr+"&StaffName="+StaffName;
	urlstr=urlstr+"&Tel="+Tel;
	urlstr=urlstr+"&Fax="+Fax;
	urlstr=urlstr+"&Amount="+Amount;
	urlstr=urlstr+"&DeliveryType="+DeliveryType;
	urlstr=urlstr+"&DeliveryAddress="+DeliveryAddress;
	urlstr=urlstr+"&DeliveryDate="+DeliveryDate;
	urlstr=urlstr+"&EstmationOKDays="+EstmationOKDays;
	urlstr=urlstr+"&PaymentCondition="+PaymentCondition;
//	urlstr=urlstr+"&PaymentMethord="+PaymentMethord;
	//urlstr=urlstr+"&DiscountAmount="+DiscountAmount;
	urlstr=urlstr+"&Currency="+Currency;
	urlstr=urlstr+"&Note="+Note;
	urlstr=urlstr+"&userId="+userId;
	urlstr=urlstr+"&lang_flg="+lang_flg;
//	
//	alert(urlstr);
    $.ajax({
		type:"post",
		url:"xinan/addEstmationSheet.action",
		async:"true",
		data:urlstr,
		dataType:"text",
		error:function(data){
			//alert(data);
			alert("保存错误！");
		},
		success: function(data){
			//alert(data);
			var str = data.split("@");
			var estimationId = str[0];
			var estimationCd = str[1];
			var userId = str[2];
			$("#EstimationId").val(estimationId.trim());
			$("#EstimationCd").val(estimationCd);
			$("#userId").val(userId);
			//alert(str);
				saveDetail(estimationId.trim());
				alert("保存成功！");
				//document.getElementById("saveBT").value="受注确定";
//				document.getElementById('saveBT').onclick = function(){ 
//					estimationConfirm(); 
//					}; 
			
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
		//alert(1888);
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
			//alert(ulstr);
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
						//alert("保存成功！");
					}
			//done()メソッドが実行結果の順が正しいです	
			 }).done(function(data) {
				 if(i<=rows.length-2){
					 i=i+1;
					 foo();
					 }
				 });
			 
//			$.ajax({
//					type:"post",
//					url:"xinan/addDetail.action",
//					async:"false",
//					data:ulstr,
//					dataType:"json",
//					error:function(data){
//						alert(data);
//						alert("detail添加失败");
//					},
//					success: function(data){
//						$("#EstimationId").val(data);
//					}
//				});
		
		}
		
	};
	//関数fooを再帰呼び出
	foo();
	//ulstr = ulstr + "&bnum="+bnum;
	 
	//alert(ulstr);
}
//未注文一览初始化
//function NoOrderEstimation(){
//	$.ajax({
//		type:"POST",
//		url:"xinan/noOrderEstimation.action?",
//		async:"false",
//		dataType:"json",
//		error:function(){
//			//alert("查询失败");
//			
//		}, 
//		success:function(data){
//			alert("查询成功");
//	   
//		}
//	});
//}
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
//打印
	function myPrint(obj){
			document.getElementById("endtime1").style.display="block";
			document.getElementById("endtime1").innerHTML=document.getElementById("endtime").value;
			document.getElementById("ntime1").style.display="block";
			document.getElementById("ntime1").innerHTML=document.getElementById("ntime").value;
			document.getElementById("ytime1").style.display="block";
			document.getElementById("ytime1").innerHTML=document.getElementById("ytime").value;
			document.getElementById("endtime").style.display="none";
			document.getElementById("ntime").style.display="none";
			document.getElementById("ytime").style.display="none";
			//document.getElementById("_select").style.border="0px";

			var newWindow=window.open("1","123");//打印窗口要换成页面的url
			var docStr = obj.innerHTML;
			newWindow.document.write(docStr);
			newWindow.document.close();
			//newWindow.save();
			newWindow.print();
			document.getElementById("endtime1").style.display="none";
			document.getElementById("endtime").style.display="block";
			document.getElementById("ntime1").style.display="none";
			document.getElementById("ntime").style.display="block";
			document.getElementById("ytime1").style.display="none";
			document.getElementById("ytime").style.display="block";
			//document.getElementById("_select").style.border="1px";
			
			newWindow.close();
		}
	//页面版本切换
	function pageChange(){
		var url = "xinan/quoteload.action?langflg=JP";
		location.href = url;
	}
	//受注确定
	function estimationConfirm(){
		var id=document.getElementById("EstimationId").value;
		if(id == null || id == ""){
			alert("不能受注");
			return;
		}
		$.ajax({
			type:"post",
			url:"xinan/readEstimationApproveStatus.action?",
			async:"true",
			data:{"EstimationId":id},
			dataType:"json",
			error:function(){
				alert("error");
						}, 
			success:function(data){
				if(data.presidentId && data.vicePresidentId){
					location.href = "xinan/loadEstmationConfirm.action?EstimationId="+id;
				}else{
					alert("报价单尚未承认");
				}
				}
			});
	}
	
	function ordermake(){
		var tbodyObj=document.getElementById("noOrder");
		var rows=tbodyObj.getElementsByTagName("tr");
		var coll = document.getElementsByName("quoten");
		var reqStr="";
		//alert(rows.length);
		for(var i=0;i<rows.length;i++){
			var curRows=rows[i];
			var tspn=curRows.getElementsByTagName("td")[0];
			var titemId=curRows.getElementsByTagName("td")[1];
			if(coll[i].checked == true){
				//alert(tspn.innerHTML);
				reqStr = reqStr+tspn.innerHTML+","+titemId.innerHTML+";";
			}
			//var checkbox = tspn.getElementsByTagName("input")[0].value;
			
		};
		//alert(reqStr);
		
		
		location.href = "xinan/ordermake.action?reqStr="+reqStr;
		
	}
	
	function maxApprove(e,a){
		if(e == '5'){
			var value = document.getElementById("EstimationId").value;
			if(value == "" || value == "undefined" || value== null){
				alert("请先保存该订单信息");
				return;
			}
			if(!approveConfirm())
				return;
			var urlstr="&EstimationId="+value;
			$.ajax({
				type:"POST",
				url:"xinan/approveEstimation.action?",
				async:"true",
				data:urlstr,
				dataType:"text",
				error:function(){
//					alert("approve失败");
				}, 
				success:function(data){
//					alert("approve成功");
//					document.getElementById("maxseal").style.display="block";
//					document.getElementById("maxbt").style.display="none";
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
			var value = document.getElementById("EstimationId").value;
			if(value == "" || value == "undefined" || value== null){
				alert("请先保存该订单信息");
				return;
			}
			if(!approveConfirm())
				return;
			var urlstr="&EstimationId="+value;
			$.ajax({
				type:"POST",
				url:"xinan/approveEstimation.action?",
				async:"true",
				data:urlstr,
				dataType:"text",
				error:function(){
//					alert("approve失败");
				}, 
				success:function(data){
//					alert("approve成功");
//					document.getElementById("minseal").style.display="block";
//					document.getElementById("minbt").style.display="none";
					document.getElementById("minApprove").innerHTML = 
						"<img width='60px' height='60px' id='minseal'  src='xinan/userseal.action?userId="+a+"' />"
				}
			});
		}else{
			alert("权限不足")
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

	function freshItemNames(){
		$.ajax({
			type:"POST",
			url:"xinan/itemname.action?",
			async:"true",
			dataType:"json",
			error:function(data){
				alert("商品名没有加载");
			}, 
			success:function(data){
				var availableTags= data.itemCnNames;
			    //搜索选择
		    $(".spname").autocomplete({
			      source: availableTags,
				  select: function( event, ui ) {
					}

	  			});
			}
			});
	}
	function search(){
		var From = document.getElementById("Form").value;
		var To = document.getElementById("To").value;
		paramStr = "";
		if(From != ""){
			paramStr += "Form=" +From+"&";;
		}
		if(To != ""){
			paramStr += "To=" + To+"&";;
		}
		location.href = "xinan/noOrderEstimation.action?"+paramStr;
	}