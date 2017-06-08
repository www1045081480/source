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
	var cnname = document.getElementById("cnname").value ;
	var enname = document.getElementById("enname").value ;
	var enshortname = document.getElementById("enshortname").value ;
	var country = document.getElementById("country").value ;
	var address1 = document.getElementById("address1").value ;
	var address2 = document.getElementById("address2").value ;
	var address3 = document.getElementById("address3").value ;
	var enaddress1 = document.getElementById("enaddress1").value ;
	var enaddress2 = document.getElementById("enaddress2").value ;
	var enaddress3 = document.getElementById("enaddress3").value ;
	var postcd = document.getElementById("postcd").value ;
	var tel1 = document.getElementById("tel1").value ;
	var tel2 = document.getElementById("tel2").value ;
	var fax = document.getElementById("fax").value ;
	
	
//	会社名を入力してください
	if(cnname.indexOf("(例)XXX机械制造厂")==0){
		cnname = "";
	}
	if(enname.indexOf("(例)XXX mechanism")==0){
		enname = "";
	}
	if(jpname.indexOf("(例)XXX機械製作所")==0){
		jpname = "";
	}	
	if(jpname.trim() == "" && cnname.trim() == "" && enname.trim() == ""){
		alert("会社名を入力してください！");
		return false;
	}
//	会社名（英語略語）を入力してください
	if(enshortname.trim() == "" || enshortname.indexOf("(例)")==0){
		alert("会社名（英語略語）を入力してください！");
		return false;
	}
// 	所在国を入力してください
	if(country.trim() == "" || country.indexOf("(例)")==0){
		alert("所在国を入力してください！");
		return false;
	}
	
//	住所（県、市、区）を入力してください！
	if(address1.indexOf("(例)愛知県")==0){
		address1 = "";
	}
	if(enaddress1.indexOf("(例) Anqing City,Anhui Province")==0){
		enaddress1 = "";
	}
	if(address1.trim() == "" &&enaddress1.trim() == ""){
		alert("住所（県、市、区）を入力してください！");
		return false;
	}	
//	住所（町）を入力してください！
	if(address2.indexOf("(例)岡崎市美合町")==0){
		address2 = "";
	}
	if(enaddress2.indexOf("(例)Yingbin Road of Development Area")==0){
		enaddress2 = "";
	}
	if(address2.trim() == "" &&enaddress2 == "" ){
		alert("住所（町）を入力してください！");
		return false;
	}
	
//	住所（巨細）を入力してください！
	if(address3.indexOf("(例)XXXX8番14号")==0){
		address3 = "";
	}
	if(enaddress3.indexOf("(例)No.317")==0){
		enaddress3 = "";
	}
	if(address3.trim() == "" && enaddress3 == "" ){
		alert("住所（巨細）を入力してください！");
		return false;
	}		
//	郵便番号を入力してください！	
	if(postcd.trim() == "" || postcd.indexOf("(例)")==0){
		alert("郵便番号を入力してください！");
		return false;
	}
//	電話番号を入力してください
	if(tel1.trim() == "" || tel1.indexOf("(例)")==0){
		alert("電話番号を入力してください！");
		return false;
	}
//	ファクスを入力してください
	if(fax.trim() == "" || fax.indexOf("(例)")==0){
		alert("ファクスを入力してください！");
		return false;
	}
	
	document.getElementById("customerid").value = "";
	$.ajax({
	    type: 'post',
	    url:"xinan/customerAdd.action?",
	    data: $("form").serialize(),
	    error:function(){
	    	alert("登録しませんでした");
	    },
	    success: function(data) {
	    	alert("登録しました");
	    	reset();
	    	findAll();
//	    	 location.href = "xinan/customer.action";
	    }
	});
}
//查询
function find(){
	var jpname = document.getElementById("jpname").value ;
	var cnname = document.getElementById("cnname").value ;
	var enname = document.getElementById("enname").value ;
	var enshortname = document.getElementById("enshortname").value ;
	var country = document.getElementById("country").value ;
	var address1 = document.getElementById("address1").value ;
	var address2 = document.getElementById("address2").value ;
	var address3 = document.getElementById("address3").value ;
	var enaddress1 = document.getElementById("enaddress1").value ;
	var enaddress2 = document.getElementById("enaddress2").value ;
	var enaddress3 = document.getElementById("enaddress3").value ;
	var postcd = document.getElementById("postcd").value ;
	var tel1 = document.getElementById("tel1").value ;
	var tel2 = document.getElementById("tel2").value ;
	var fax = document.getElementById("fax").value ;
	
	var urlstr = "";
	if(jpname != "" && jpname.indexOf("(例)")!=0){
		urlstr="&jpname="+jpname;
	}
	if(cnname != "" && cnname.indexOf("(例)")!=0){
		urlstr=urlstr+"&cnname="+cnname;
	}
	if(enname != "" && enname.indexOf("(例)")!=0){
		urlstr=urlstr+"&enname="+enname;
	}
	if(enshortname != "" && enshortname.indexOf("(例)")!=0){
		urlstr=urlstr+"&enshortname="+enshortname;
	}
	if(country != "" && country.indexOf("(例)")!=0){
		urlstr=urlstr+"&country="+country;
	}
	if(address1 != "" && address1.indexOf("(例)")!=0){
		urlstr=urlstr+"&address1="+address1;
	}
	if(address2 != "" && address2.indexOf("(例)")!=0){
		urlstr=urlstr+"&address2="+address2;
	}
	if(address3 != "" && address3.indexOf("(例)")!=0){
		urlstr=urlstr+"&address3="+address3;
	}
	if(enaddress1 != "" && enaddress1.indexOf("(例)")!=0){
		urlstr=urlstr+"&enaddress1="+enaddress1;
	}
	if(enaddress2 != "" && enaddress2.indexOf("(例)")!=0){
		urlstr=urlstr+"&enaddress2="+enaddress2;
	}
	if(enaddress3 != "" && enaddress3.indexOf("(例)")!=0){
		urlstr=urlstr+"&enaddress3="+enaddress3;
	}
	if(postcd != "" && postcd.indexOf("(例)")!=0){
		urlstr=urlstr+"&postcd="+postcd;
	}
	if(tel1 != "" && tel1.indexOf("(例)")!=0){
		urlstr=urlstr+"&tel1="+tel1;
	}
	if(tel2 != "" && tel2.indexOf("(例)")!=0){
		urlstr=urlstr+"&tel2="+tel2;
	}
	if(fax != "" && fax.indexOf("(例)")!=0){
		urlstr=urlstr+"&fax="+fax;
	}
	  $.ajax({
			type:"post",
			url:"xinan/customerSearch.action?",
			async:"false",
			data:urlstr,
			dataType:"json",
			error:function() {  
				
			}, 
			success: function(data){
				var table = document.getElementById('sample');
//				var len = data.length;
				var th = "<tbody><tr>"+
							"<th style='display: none'>得意先ID</th>"+
							"<th  style='width:150px;'>会社名（日本語）</th>"+
							"<th  style='width:150px;'>会社名（中国語）</th>"+
							"<th  style='width:150px;'>会社名（英語）</th>"+
							"<th  style='width:150px;'>会社名（英語略語）</th>"+
							"<th  style='width:150px;'>所在国</th>"+
							"<th  style='display: none'>住所（県、市、区）</th>"+
							"<th  style='display: none'>住所（町）</th>"+
							"<th  style='display: none'>住所（巨細）</th>"+
							"<th  style='display: none'>住所-英語（県、市、区）</th>"+
							"<th  style='display: none'>住所-英語（町）</th>"+
							"<th  style='display: none'>住所-英語（巨細）</th>"+
							"<th  style='display: none'>郵便番号</th>"+
							"<th  style='width:200px;'>電話番号１</th>"+
							"<th  style='width:200px;'>電話番号２</th>"+
							"<th  style='width:200px;'>ファクス</th>"+
							"<th  colspan='2' style='width:200px;'>操作</th>"+
						"</tr>";
				for(var i=0;i<data.length;i++){
					var jpName = "" ;
					var cnName = "" ;
					var enName = "" ;
					var enShortName = "" ;
					var country = "" ;
					var address1 = "" ;
					var address2 = "" ;
					var address3 = "" ;
					var enaddress1 = "" ;
					var enaddress2 = "" ;
					var enaddress3 = "" ;
					var partsCd = "" ;
					var tel1 = "" ;
					var tel2 = "";
					var fax = "" ;
					if(data[i].jpName){
						jpName = data[i].jpName ;
					}
					if(data[i].cnName){
						 cnName = data[i].cnName ;
					}
					if(data[i].enName){
						 enName = data[i].enName ;
					}
					if(data[i].enShortName){
						 enShortName = data[i].enShortName ;
					}
					if(data[i].country){
						 country = data[i].country ;
					}
					if(data[i].address1){
						 address1 = data[i].address1 ;
					}
					if(data[i].address2){
						 address2 = data[i].address2 ;
					}
					if(data[i].address3){
						 address3 = data[i].address3 ;
					}
					if(data[i].enAddress1){
						 enaddress1 = data[i].enAddress1 ;
					}
					if(data[i].enAddress2){
						 enaddress2 = data[i].enAddress2 ;
					}
					if(data[i].enAddress3){
						 enaddress3 = data[i].enAddress3 ;
					}
					if(data[i].postCd){
						 partsCd = data[i].postCd ;
					}
					
					if(data[i].tel1){
						 tel1 = data[i].tel1 ;
					}
					
					if(data[i].tel2){
						 tel2 = data[i].tel2;
					}
					
					if(data[i].fax){
						 fax = data[i].fax ;
					}
					
					
					
					 th = th + "<tr>"+
					 		"<td style='display: none'>"+data[i].customerId+"</td>"+
					 		"<td >"+jpName+"</td>"+
					 		"<td >"+cnName+"</td>"+
					 		"<td >"+enName+"</td>"+
					 		"<td >"+enShortName+"</td>"+
					 		"<td >"+country+"</td>"+
					 		"<td style='display: none'>"+address1+"</td>"+
					 		"<td style='display: none'>"+address2+"</td>"+
					 		"<td style='display: none'>"+address3+"</td>"+
					 		"<td style='display: none'>"+enaddress1+"</td>"+
					 		"<td style='display: none'>"+enaddress2+"</td>"+
					 		"<td style='display: none'>"+enaddress3+"</td>"+
					 		"<td style='display: none'>"+partsCd+"</td>"+
					 		"<td >"+tel1+"</td>"+
					 		"<td >"+tel2+"</td>"+
					 		"<td >"+fax+"</td>"+
					 		"<td> <input  class='listbt' type='button' value='編集' onclick='change(this);'/></td>"+
					 		"<td> <input class='listbt' type='button' value='削除' onclick='dele(this);'/></td>"+
							"</tr>";
					 }
					th = th + "</tbody>";
					table.innerHTML = th;
			}
		});
//	 location.href = "xinan/customerSearch.action?" + urlstr;
}
//
function dele(evt){
	if(confirm("得意先は削除しますか？")){
	var eparent = evt.parentNode;
    var tparent = eparent.parentNode;
    var customerid = tparent.getElementsByTagName('td')[0].innerHTML;
    $.ajax({
		type:"post",
		url:"xinan/customerDel.action?customerid="+customerid,
		async:"false",
		dataType:"text",
		error:function() {  
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
    
    document.getElementById("customerid").value = tparent.getElementsByTagName('td')[0].innerHTML;
    document.getElementById("jpname").value = tparent.getElementsByTagName('td')[1].innerHTML;
	document.getElementById("cnname").value =tparent.getElementsByTagName('td')[2].innerHTML;;
	document.getElementById("enname").value = tparent.getElementsByTagName('td')[3].innerHTML;;
	document.getElementById("enshortname").value = tparent.getElementsByTagName('td')[4].innerHTML;;
	document.getElementById("country").value = tparent.getElementsByTagName('td')[5].innerHTML;;
	document.getElementById("address1").value = tparent.getElementsByTagName('td')[6].innerHTML;;
	document.getElementById("address2").value = tparent.getElementsByTagName('td')[7].innerHTML;;
	document.getElementById("address3").value = tparent.getElementsByTagName('td')[8].innerHTML;;
	document.getElementById("enaddress1").value = tparent.getElementsByTagName('td')[9].innerHTML;;
	document.getElementById("enaddress2").value = tparent.getElementsByTagName('td')[10].innerHTML;;
	document.getElementById("enaddress3").value = tparent.getElementsByTagName('td')[11].innerHTML;;
	document.getElementById("postcd").value = tparent.getElementsByTagName('td')[12].innerHTML;;
	document.getElementById("tel1").value = tparent.getElementsByTagName('td')[13].innerHTML;;
	document.getElementById("tel2").value = tparent.getElementsByTagName('td')[14].innerHTML;;
	document.getElementById("fax").value = tparent.getElementsByTagName('td')[15].innerHTML;;

	hAdjuster(document.getElementById('address1'));
	hAdjuster(document.getElementById('address2'));
	hAdjuster(document.getElementById('address3'));
	
	hAdjuster(document.getElementById('enaddress1'));
	hAdjuster(document.getElementById('enaddress2'));
	hAdjuster(document.getElementById('enaddress3'));
}


function updata(){
	var jpname = document.getElementById("jpname").value ;
	var cnname = document.getElementById("cnname").value ;
	var enname = document.getElementById("enname").value ;
	var enshortname = document.getElementById("enshortname").value ;
	var country = document.getElementById("country").value ;
	var address1 = document.getElementById("address1").value ;
	var address2 = document.getElementById("address2").value ;
	var address3 = document.getElementById("address3").value ;
	var enaddress1 = document.getElementById("enaddress1").value ;
	var enaddress2 = document.getElementById("enaddress2").value ;
	var enaddress3 = document.getElementById("enaddress3").value ;
	var postcd = document.getElementById("postcd").value ;
	var tel1 = document.getElementById("tel1").value ;
	var tel2 = document.getElementById("tel2").value ;
	var fax = document.getElementById("fax").value ;
	
	var customerid = document.getElementById("customerid").value ;
	if(customerid == ""){
		alert("データ　選択してください！");
		return false;
	}
	
//	会社名を入力してください
	if(cnname.indexOf("(例)XXX机械制造厂")==0){
		cnname = "";
	}
	if(enname.indexOf("(例)XXX mechanism")==0){
		enname = "";
	}
	if(jpname.indexOf("(例)XXX機械製作所")==0){
		jpname = "";
	}	
	if(jpname.trim() == "" && cnname.trim() == "" && enname.trim() == ""){
		alert("会社名を入力してください！");
		return false;
	}
//	会社名（英語略語）を入力してください
	if(enshortname.trim() == "" || enshortname.indexOf("(例)")==0){
		alert("会社名（英語略語）を入力してください！");
		return false;
	}
// 	所在国を入力してください
	if(country.trim() == "" || country.indexOf("(例)")==0){
		alert("所在国を入力してください！");
		return false;
	}
	
//	住所（県、市、区）を入力してください！
	if(address1.indexOf("(例)愛知県")==0){
		address1 = "";
	}
	if(enaddress1.indexOf("(例) Anqing City,Anhui Province")==0){
		enaddress1 = "";
	}
	if(address1.trim() == "" &&enaddress1.trim() == ""){
		alert("住所（県、市、区）を入力してください！");
		return false;
	}	
//	住所（町）を入力してください！
	if(address2.indexOf("(例)岡崎市美合町")==0){
		address2 = "";
	}
	if(enaddress2.indexOf("(例)Yingbin Road of Development Area")==0){
		enaddress2 = "";
	}
	if(address2.trim() == "" &&enaddress2 == "" ){
		alert("住所（町）を入力してください！");
		return false;
	}
	
//	住所（巨細）を入力してください！
	if(address3.indexOf("(例)XXXX8番14号")==0){
		address3 = "";
	}
	if(enaddress3.indexOf("(例)No.317")==0){
		enaddress3 = "";
	}
	if(address3.trim() == "" && enaddress3 == "" ){
		alert("住所（巨細）を入力してください！");
		return false;
	}		
//	郵便番号を入力してください！	
	if(postcd.trim() == "" || postcd.indexOf("(例)")==0){
		alert("郵便番号を入力してください！");
		return false;
	}
//	電話番号を入力してください
	if(tel1.trim() == "" || tel1.indexOf("(例)")==0){
		alert("電話番号を入力してください！");
		return false;
	}
//	ファクスを入力してください
	if(fax.trim() == "" || fax.indexOf("(例)")==0){
		alert("ファクスを入力してください！");
		return false;
	}
	$.ajax({
	    type: 'post',
	    url:"xinan/customerAdd.action?",
	    data: $("form").serialize(),
	    error:function(){
	    	alert("更新しませんでした");
	    },
	    success: function(data) {
	    	reset();
	    	findAll();
	    	alert("更新しました");
//	    	 location.href = "xinan/customer.action";
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
			url:"xinan/customerSearch.action?",
			async:"false",
			dataType:"json",
			error:function(XML) {  
				
			}, 
			success: function(data){
				var table = document.getElementById('sample');
//				var len = data.length;
				var th = "<tbody><tr>"+
							"<th style='display: none'>得意先ID</th>"+
							"<th  style='width:150px;'>会社名（日本語）</th>"+
							"<th  style='width:150px;'>会社名（中国語）</th>"+
							"<th  style='width:150px;'>会社名（英語）</th>"+
							"<th  style='width:150px;'>会社名（英語略語）</th>"+
							"<th  style='width:150px;'>所在国</th>"+

							"<th  style='display: none'>住所（県、市、区）</th>"+
							"<th  style='display: none'>住所（町）</th>"+
							"<th  style='display: none'>住所（巨細）</th>"+
							"<th  style='display: none'>住所-英語（県、市、区）</th>"+
							"<th  style='display: none'>住所-英語（町）</th>"+
							"<th  style='display: none'>住所-英語（巨細）</th>"+
							"<th  style='display: none'>郵便番号</th>"+
							
							"<th  style='width:200px;'>電話番号１</th>"+
							"<th  style='width:200px;'>電話番号２</th>"+
							"<th  style='width:200px;'>ファクス</th>"+
							"<th  colspan='2' style='width:200px;'>操作</th>"+
						"</tr>";
				for(var i=0;i<data.length;i++){
					 th = th + "<tr>"+
					 		"<td style='display: none'>"+data[i].customerId+"</td>"+
					 		"<td >"+data[i].jpName+"</td>"+
					 		"<td >"+data[i].cnName+"</td>"+
					 		"<td >"+data[i].enName+"</td>"+
					 		"<td >"+data[i].enShortName+"</td>"+
					 		"<td >"+data[i].country+"</td>"+
					 		
					 		"<td style='display: none'>"+data[i].address1+"</td>"+
					 		"<td style='display: none'>"+data[i].address2+"</td>"+
					 		"<td style='display: none'>"+data[i].address3+"</td>"+
					 		"<td style='display: none'>"+data[i].enAddress1+"</td>"+
					 		"<td style='display: none'>"+data[i].enAddress2+"</td>"+
					 		"<td style='display: none'>"+data[i].enAddress3+"</td>"+
					 		"<td style='display: none'>"+data[i].postCd+"</td>"+
					 		
					 		"<td >"+data[i].tel1+"</td>"+
					 		"<td >"+data[i].tel2+"</td>"+
					 		"<td >"+data[i].fax+"</td>"+
					 		"<td> <input  class='listbt' type='button' value='編集' onclick='change(this);'/></td>"+
					 		"<td> <input class='listbt' type='button' value='削除' onclick='dele(this);'/></td>"+
							"</tr>";
					 }
					th = th + "</tbody>";
					table.innerHTML = th;
			}
		});
}