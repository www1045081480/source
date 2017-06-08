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

//添加
function save(){
	var jpname = document.getElementById("jpname").value ;
	var jpdesc = document.getElementById("jpdesc").value ;
	var cnname = document.getElementById("cnname").value ;
	var cndesc = document.getElementById("cndesc").value;
	var enname = document.getElementById("enname").value ;
	var endesc = document.getElementById("endesc").value ;
	var categorytype = document.getElementById("categorytype").value ;
	var family = document.getElementById("family").value ;
	var partscd = document.getElementById("partscd").value ;
	var modelcd = document.getElementById("modelcd").value ;
	if(categorytype == "" || categorytype.indexOf("(例)")==0){
		alert("大分類を入力してください！");
		return false;
	}
	if(family == "" || family.indexOf("(例)")==0){
		alert("小分類を入力してください！");
		return false;
	}
	if(jpname == "" || jpname.indexOf("(例)")==0){
		alert("品名（日本語）を入力してください！");
		return false;
	}
//	if(jpdesc == "" || jpdesc.indexOf("(例)")==0){
//		alert("仕様（日本語））を入力してください！");
//		return false;
//	}
	if(cnname == "" || cnname.indexOf("(例)")==0){
		alert("品名（中国語）を入力してください！");
		return false;
	}
//	if(cndesc == "" || cndesc.indexOf("(例)")==0){
//		alert("仕様（中国語）を入力してください！");
//		return false;
//	}
	if(enname == "" || enname.indexOf("(例)")==0){
		alert("品名（英語）を入力してください！");
		return false;
	}
//	if(endesc == "" || endesc.indexOf("(例)")==0){
//		alert("仕様（英語）を入力してください！");
//		return false;
//	}
//	if(partscd == "" || partscd.indexOf("(例)")==0){
//		alert("品番を入力してください！");
//		return false;
//	}
//	if(modelcd == "" || modelcd.indexOf("(例)")==0){
//		alert("型番を入力してください！");
//		return false;
//	}
	document.getElementById("itemid").value = "";
	$.ajax({
	    type: 'post',
	    url:"xinan/saveItem.action?",
	    data: $("form").serialize(),
	    error: function() {
	    	alert("登録しませんでした")
	    },
	    success: function(data) {
	    	alert("登録しました");
//	    	 location.href = "xinan/item.action";
	    	reset();
	    	find();
	    	
	    }
	});
}
//查询
function find(){
	var jpname = document.getElementById("jpname").value ;
	var jpdesc = document.getElementById("jpdesc").value ;
	var cnname = document.getElementById("cnname").value ;
	var cndesc = document.getElementById("cndesc").value;
	var enname = document.getElementById("enname").value ;
	var endesc = document.getElementById("endesc").value ;
	var categorytype = document.getElementById("categorytype").value ;
	var family = document.getElementById("family").value ;
	var partscd = document.getElementById("partscd").value ;
	var modelcd = document.getElementById("modelcd").value ;
	
	var urlstr = "";
	if(jpname != "" && jpname.indexOf("(例)")!=0){
		urlstr="&jpname="+jpname;
	}
	if(jpdesc != "" && jpdesc.indexOf("(例)")!=0){
		urlstr=urlstr+"&jpdesc="+jpdesc;
	}
	if(cnname != "" && cnname.indexOf("(例)")!=0){
		urlstr=urlstr+"&cnname="+cnname;
	}
	if(cndesc != "" && cndesc.indexOf("(例)")!=0){
		urlstr=urlstr+"&cndesc="+cndesc;
	}
	if(enname != "" && enname.indexOf("(例)")!=0){
		urlstr=urlstr+"&enname="+enname;
	}
	if(endesc != "" && endesc.indexOf("(例)")!=0){
		urlstr=urlstr+"&endesc="+endesc;
	}
	if(categorytype != "" && categorytype.indexOf("(例)")!=0){
		urlstr=urlstr+"&categorytype="+categorytype;
	}
	if(family != "" && family.indexOf("(例)")!=0){
		urlstr=urlstr+"&family="+family;
	}
	if(partscd != "" && partscd.indexOf("(例)")!=0){
		urlstr=urlstr+"&partscd="+partscd;
	}
	if(modelcd != "" && modelcd.indexOf("(例)")!=0){
		urlstr=urlstr+"&modelcd="+modelcd;
	}
    $.ajax({
		type:"post",
		url:"xinan/seachItems.action?",
		async:"false",
		data:urlstr,
		dataType:"json",
		error:function() {  
		}, 
		success: function(data){
			var table = document.getElementById('sample');
//			var len = data.length;
			var th = "<tbody><tr>"+
						"<th  style='display: none'>商品ID</th>"+
						"<th  style='width:120px;'>大分類</th>"+
						"<th  style='width:180px;'>小分類</th>"+
						"<th  style='width:250px;'>品名（日本語）</th>"+
						"<th  style='display: none'>品名（中国語）</th>"+
						"<th  style='display: none'>品名（英語）</th>"+
						"<th  style='width:300px;'>規格（日本語）</th>"+
				//		"<th  style='width:150px;'>仕様（中国語）</th>"+
				//		"<th  style='width:150px;'>仕様（英語）</th>"+
						"<th  style='display: none'>品番</th>"+
						"<th  style='display: none'>型番</th>"+
						"<th   style='width:30px;'>操作</th>"+
					"</tr>";
			for(var i=0;i<data.length;i++){
				var jpName = "";
				var jpDesc = "";
				var cnName = "";
				var cnDesc = "";
				var enName = "";
				var enDesc = "";
				var partsCd = "";
				var modelCd = "";
				var categoryType = "";
				var family = "";
				if(data[i].jpName){
					jpName = data[i].jpName
				};
				if(data[i].jpDesc){
					jpDesc = data[i].jpDesc
				};
				if(data[i].cnName){
					cnName = data[i].cnName
				};
				if(data[i].cnDesc){
					cnDesc = data[i].cnDesc
				};
				if(data[i].enName){
					enName = data[i].enName
				};
				if(data[i].enDesc){
					enDesc = data[i].enDesc
				};
				if(data[i].partsCd){
					partsCd = data[i].partsCd
				};
				if(data[i].modelCd){
					modelCd = data[i].modelCd
				};
				if(data[i].categoryType){
					categoryType = data[i].categoryType
				};
				if(data[i].family){
					family = data[i].family
				};
				
			 th = th + "<tr>"+
			 		"<td style='display: none'>"+data[i].itemId+"</td>"+
			 		"<td class='categoryType' style='width:100px;text-align:left;'>"+format(categoryType)+"</td>"+
			 		"<td style='text-align:left;'>"+family+"</td>"+
			 		"<td style='text-align:left;'>"+jpName+"</td>"+
			 		"<td style='display: none'>"+cnName+"</td>"+
			 		"<td style='display: none'>"+enName+"</td>"+
			 		"<td>"+jpDesc+"</td>"+
			 		"<td style='display: none'>"+cnDesc+"</td>"+
			 		"<td style='display: none'>"+enDesc+"</td>"+
			 		"<td style='display: none'>"+partsCd+"</td>"+
			 		"<td style='display: none'>"+modelCd+"</td>"+
			 		"<td> <input  class='listbt' type='button' value='編集' onclick='change(this);'/></td>"+
					"</tr>";
			 }
			th = th + "</tbody>";
			table.innerHTML = th;
		}
	});
//	 location.href = "xinan/seachItems.action?" + urlstr;
}

//
function dele(evt){
	if(confirm("商品は削除しますか？")){
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
    var itemid = tparent.getElementsByTagName('td')[0].innerHTML;
    $.ajax({
		type:"post",
		url:"xinan/deleItem.action?itemid="+itemid,
		async:"false",
		dataType:"text",
		error:function(XMLHttpRequest, textStatus, errorThrown) {  
			alert('削除しませんでした');   
		}, 
		success: function(data){
			evt.parentNode.parentNode.parentNode.removeChild(evt.parentNode.parentNode);
			alert("削除しました");
		}
	});
    }else{
    	return false;
    }
	
}
//修改
function change(evt){
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
    
    var itemid = tparent.getElementsByTagName('td')[0].innerHTML;
    var categorytype =tparent.getElementsByTagName('td')[1].innerHTML;
    if(categorytype.trim() == "材料"){
    	categorytype = "1";
    }
    if(categorytype.trim() == "生产设备"){
    	categorytype = "2";
    }
    if(categorytype.trim() == "检查设备"){
    	categorytype = "3";
    }
    if(categorytype.trim() == "其他"){
    	categorytype = "4";
    }
    var family = tparent.getElementsByTagName('td')[2].innerHTML;
	var jpname = tparent.getElementsByTagName('td')[3].innerHTML;
	var cnname = tparent.getElementsByTagName('td')[4].innerHTML;
	var enname = tparent.getElementsByTagName('td')[5].innerHTML;
	var jpdesc = tparent.getElementsByTagName('td')[6].innerHTML;
	var cndesc = tparent.getElementsByTagName('td')[7].innerHTML;
	var endesc = tparent.getElementsByTagName('td')[8].innerHTML;
	var partscd = tparent.getElementsByTagName('td')[9].innerHTML;
	var modelcd =tparent.getElementsByTagName('td')[10].innerHTML;
	
	document.getElementById("itemid").value = itemid;
	document.getElementById("jpname").value = jpname;
	document.getElementById("jpdesc").value = jpdesc;
	document.getElementById("cnname").value = cnname;
	document.getElementById("cndesc").value = cndesc;
	document.getElementById("enname").value = enname;
	document.getElementById("endesc").value = endesc;
	document.getElementById("categorytype").value = categorytype;
	document.getElementById("family").value = family;
	document.getElementById("partscd").value = partscd;
	document.getElementById("modelcd").value = modelcd;
	
	hAdjuster(document.getElementById('jpdesc'));
	hAdjuster(document.getElementById('cndesc'));
	hAdjuster(document.getElementById('endesc'));
	
}


function updata(){

	var jpname = document.getElementById("jpname").value ;
	var jpdesc = document.getElementById("jpdesc").value ;
	var cnname = document.getElementById("cnname").value ;
	var cndesc = document.getElementById("cndesc").value;
	var enname = document.getElementById("enname").value ;
	var endesc = document.getElementById("endesc").value ;
	var categorytype = document.getElementById("categorytype").value ;
	var family = document.getElementById("family").value ;
	var partscd = document.getElementById("partscd").value ;
	var modelcd = document.getElementById("modelcd").value ;
//	var type =  document.getElementById("type").value ;
	var itemid = document.getElementById("itemid").value ;

	if(itemid == ""){
		alert("データ　選択してください！");
		return false;
	}
	if(jpname == "" || jpname.indexOf("(例)")==0){
		alert("品名（日本語）を入力してください！");
		return false;
	}
//	if(jpdesc == "" || jpdesc.indexOf("(例)")==0){
//		alert("仕様（日本語））を入力してください！");
//		return false;
//	}
	if(cnname == "" || cnname.indexOf("(例)")==0){
		alert("品名（中国語）を入力してください！");
		return false;
	}
//	if(cndesc == "" || cndesc.indexOf("(例)")==0){
//		alert("仕様（中国語）を入力してください！");
//		return false;
//	}
	if(enname == "" || enname.indexOf("(例)")==0){
		alert("品名（英語）を入力してください！");
		return false;
	}
//	if(endesc == "" || endesc.indexOf("(例)")==0){
//		alert("仕様（英語）を入力してください！");
//		return false;
//	}
	if(categorytype == "" || categorytype.indexOf("(例)")==0){
		alert("大分類を入力してください！");
		return false;
	}
	if(family == "" || family.indexOf("(例)")==0){
		alert("小分類を入力してください！");
		return false;
	}
//	if(partscd == "" || partscd.indexOf("(例)")==0){
//		alert("品番を入力してください！");
//		return false;
//	}
//	if(modelcd == "" || modelcd.indexOf("(例)")==0){
//		alert("型番を入力してください！");
//		return false;
//	}
//	var eparent = evt.parentNode;
//    var tparent = eparent.parentNode;
//    alert(tparent);

	$.ajax({
	    type: 'post',
	    url:"xinan/saveItem.action?",
	    data: $("form").serialize(),
	    error:function(){
	    	alert("更新しませんでした");
	    },
	    success: function(data) {
	    	alert("更新しました");
//	    	location.href = "xinan/item.action";
	    	reset();
	    	findAll();
//	    	 tparent.getElementsByTagName('td')[1].innerHTML = document.getElementById("jpname").value;
//	    	 tparent.getElementsByTagName('td')[2].innerHTML = document.getElementById("cnname").value;
//	    	 tparent.getElementsByTagName('td')[3].innerHTML = document.getElementById("enname").value;
//	    	 tparent.getElementsByTagName('td')[4].innerHTML = document.getElementById("partscd").value;
//	    	 tparent.getElementsByTagName('td')[5].innerHTML = document.getElementById("modelcd").value;
//	    	 tparent.getElementsByTagName('td')[6].innerHTML = document.getElementById("jpdesc").value;
//	    	 tparent.getElementsByTagName('td')[7].innerHTML = document.getElementById("cndesc").value;
//	    	 tparent.getElementsByTagName('td')[8].innerHTML = document.getElementById("endesc").value;
//	    	 tparent.getElementsByTagName('td')[9].innerHTML = document.getElementById("categorytype").value;
//	    	 tparent.getElementsByTagName('td')[10].innerHTML = document.getElementById("family").value;
	    }
	});
}
function reset(){
	document.getElementById("form").reset();
	sampleText_init();
}

function findAll(){
	 $.ajax({
			type:"post",
			url:"xinan/seachItems.action?",
			async:"false",
			dataType:"json",
			error:function() {  
			}, 
			success: function(data){
				var table = document.getElementById('sample');
//				var len = data.length;
				var th = "<tbody><tr>"+
							"<th  style='display: none'>商品ID</th>"+
							"<th  style='width:120px;'>大分類</th>"+
							"<th  style='width:180px;'>小分類</th>"+
							"<th  style='width:250px;'>品名（日本語）</th>"+
							"<th  style='display: none'>品名（中国語）</th>"+
							"<th  style='display: none'>品名（英語）</th>"+
							"<th  style='width:300px;'>規格（日本語）</th>"+
					//		"<th  style='width:150px;'>仕様（中国語）</th>"+
					//		"<th  style='width:150px;'>仕様（英語）</th>"+
							"<th  style='display: none'>品番</th>"+
							"<th  style='display: none'>型番</th>"+
							"<th   style='width:30px;'>操作</th>"+
							"</tr>";
				for(var i=0;i<data.length;i++){
					var jpName = "";
					var jpDesc = "";
					var cnName = "";
					var cnDesc = "";
					var enName = "";
					var enDesc = "";
					var partsCd = "";
					var modelCd = "";
					var categoryType = "";
					var family = "";
					if(data[i].jpName){
						jpName = data[i].jpName
					};
					if(data[i].jpDesc){
						jpDesc = data[i].jpDesc
					};
					if(data[i].cnName){
						cnName = data[i].cnName
					};
					if(data[i].cnDesc){
						cnDesc = data[i].cnDesc
					};
					if(data[i].enName){
						enName = data[i].enName
					};
					if(data[i].enDesc){
						enDesc = data[i].enDesc
					};
					if(data[i].partsCd){
						partsCd = data[i].partsCd
					};
					if(data[i].modelCd){
						modelCd = data[i].modelCd
					};
					if(data[i].categoryType){
						categoryType = data[i].categoryType
					};
					if(data[i].family){
						family = data[i].family
					};
					
					 th = th + "<tr>"+
				 		"<td style='display: none'>"+data[i].itemId+"</td>"+
				 		"<td style='text-align:left;'>"+format(categoryType)+"</td>"+
				 		"<td style='text-align:left;'>"+family+"</td>"+
				 		"<td style='text-align:left;'>"+jpName+"</td>"+
				 		"<td style='display: none;text-align:left;'>"+cnName+"</td>"+
				 		"<td style='display: none;text-align:left;'>"+enName+"</td>"+
				 		"<td>"+jpDesc+"</td>"+
				 		"<td style='display: none'>"+cnDesc+"</td>"+
				 		"<td style='display: none'>"+enDesc+"</td>"+
				 		"<td style='display: none;text-align:left;'>"+partsCd+"</td>"+
				 		"<td style='display: none;text-align:left;'>"+modelCd+"</td>"+
				 		"<td> <input  class='listbt' type='button' value='編集' onclick='change(this);'/></td>"+
						"</tr>";
				 }
				th = th + "</tbody>";
				table.innerHTML = th;
			}
		});
}

function format(categorytype){
	
	if(categorytype.trim() == 1){
		categorytype = "材料";
	}
	if(categorytype.trim() == 2){
		categorytype = "生产设备";
	}
	if(categorytype.trim() == 3){
		categorytype = "检查设备";
	}
	if(categorytype.trim() == 4){
		categorytype = "其他";
	}
	return categorytype;
}