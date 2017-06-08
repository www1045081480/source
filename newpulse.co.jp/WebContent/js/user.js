		//添加用户
function save(){
			//alert($("#userID").val());
			var jname = $("#jname").val();
			var ename = $("#ename").val();
			var cname = $("#cname").val();
			var id = $("#id").val();
			var password = $("#password").val();
            var password2 = $("#password2").val();
            var role = $("#role").val();
            var sign = $("#sign").val();
            var seal = $("#seal").val();
			if(jname=="(例) 新安 一郎"){
            	alert("姓名を入力してください！");
            	return false;
            };
            if(ename == "(例)XINAN YITIRO" ){
            	alert("英語表記を入力してください！");
            	return false;
            };
            if(cname == "(例)张 博然" ){
            	alert("中国語表記を入力してください！");
            	return false;
            };
            if(id == "(例)TtestUserId"){
            	alert("ユーザーIDを入力してください！");
            	return false;
            };
            if(password == ""){
            	alert("パスワードを入力してください！");
            	return false;
            };
            if(password2 == ""){
            	alert("パスワード(確認)を入力してください！");
            	return false;
            };
            if(password != password2) {
            	alert("パスワードが一致しません");
            	return false;
            };
            if(role == "0") {
            	alert("権限を入力してください");
            	return false;
            };
            if(sign == ""){
            	alert("電子印鑑を入力してください！");
            	return false;
            };
            if(seal == ""){
            	alert("電子サインを入力してください！");
            	return false;
            };
            document.getElementById("focus").submit();
           
		}
//查询
function find(){
            /*var cname = $("#cname").val();*/
			var ename = $("#ename").val();
			var jname = $("#jname").val();
			var cname = $("#cname").val();
            var role = $("#role").val();
            var id = $("#id").val();
            if(jname=="(例) 新安 一郎"){
            	jname = "";
            };
            if(ename == "(例)XINAN YITIRO" ){
            	ename = "";
            };
            if(id == "(例)TtestUserId"){
            	id = "";
            };
            
            if(cname == "(例)张 博然"){
            	cname = "";
            };
//           	var userfind = "xinan/userfind.action?";
           	var userfind = "";
            if(cname != ""){
            	userfind = userfind + "&cnName="+cname;
            }
            if(ename != ""){
            	userfind = userfind + "&enName="+ename;
            }
            if(jname != ""){
            	userfind = userfind + "&jpName="+jname;
            }
            if(id != ""){
            	userfind = userfind + "&account="+id;
            }
            if(role != 0){
            	userfind = userfind + "&role="+role;
            }
            //alert(userfind);
//            location.href = userfind;
            $.ajax({
        		type:"post",
        		url:"xinan/userList.action?",
        		async:"true",
        		data:userfind,
        		dataType:"json",
        		error:function() {  
        		}, 
        		success: function(data){
        			var table = document.getElementById('sample');
        			var len = data.length;
        			var th = "<tbody><tr>"+
        						"<th style='display: none'>ID</th>"+
        						"<th  style='width:150px;'>姓名</th>"+
        						"<th  style='width:150px;'>英語表記</th>"+
        						"<th  style='width:150px;'>中国語表記</th>"+
        						"<th  style='width:150px;'>ユーザーID</th>"+
        						"<th  style='width:150px;'>権限</th>"+
        						"<th  style='width:150px;'>電子印鑑</th>"+
        						"<th  style='width:150px;'>電子サイン</th>"+
        						"<th  colspan='2' style='width:200px;'>操作</th>"+
        					"</tr>";
        			for(var i=0;i<data.length;i++){
        				var role = 0;
						
        				switch(data[i].role)
        				{
        				case 1:
        					role = "営業"
        				  break;
        				case 2:
        					role = "物流"
        				  break;
        				case 3:
        					role = "財務"
        				  break;
        				case 4:
        					role = "副社長"
        				  break;
        				case 5:
        					role = "社長"
        				  break;
        				case 6:
        					role = "管理者"
        				  break;
						case 7:
        			        role = "営業物流"
        				  break;
        				default:
        					role = ""
        				}
        				var id = data[i].userId;
        				
        				var jpName = "";
        				var enName = "";
        				var cnName = "";
        				var account = "";
        				//var role = "";
        				
        				if(data[i].jpName){
        					jpName = data[i].jpName;
        				}
        				if(data[i].enName){
        					enName = data[i].enName;
        				}
        				if(data[i].cnName){
        					cnName = data[i].cnName;
        				}
        				if(data[i].account){
        					account = data[i].account;
        				}
        			 th = th + "<tr>"+
        			 		"<td style='display: none'>"+data[i].userId+"</td>"+
        			 		"<td >"+jpName+"</td>"+
        			 		"<td >"+enName+"</td>"+
        			 		"<td >"+cnName+"</td>"+
        			 		"<td >"+account+"</td>"+
        			 		"<td >"+role+"</td>"+
        			 		"<td ><img onload='imgOnload(this);' style='width:50px;height:50px;' src='xinan/userseal.action?userId="+id+"'/></td>"+
        			 		"<td ><img onload='imgOnload(this);' style='width:50px;height:50px;' src='xinan/usersign.action?userId="+id+"'/></td>"+
        			 		"<td> <input  class='listbt' type='button' value='編集' onclick='change(this);'/></td>"+
        			 		"<td> <input class='listbt' type='button' value='削除' onclick='dele(this);'/></td>"+
        					"</tr>";
        			 }
        			th = th + "</tbody>";
        			table.innerHTML = th;
        			
        		}
        	});
		}
		//修改提交
//		function changesub(e){
//			var password = $("#password").val();
//            var password2 = $("#password2").val();
//            /*var cname = $("#cname").val();*/
//			var ename = $("#ename").val();
//			var jname = $("#jname").val();
//            var id = $("#id").val();
//            var role = $("#role").val();
//            var sign = $("#sign").val();
//            var seal = $("#seal").val();
//            /*var div2 = $("#div2");*/
//            /*if(ename == "" && cname=="" && jname==""){
//            	alert("中文名、英文名、日文名至少填一个！");
//            	return false;
//            };*/
//            if(ename == "" ){
//            	alert("英文名不能为空！");
//            	return false;
//            };
//            if(jname==""){
//            	alert("日文名不能为空！");
//            	return false;
//            };
//            
//            if(id == ""){
//            	alert("账号不能为空！");
//            	return false;
//            };
//            if(password != password2) {
//            	alert("密码不一致");
//            	return false;
//            };
//            if(role == 0){
//            	alert("请选择权限！");
//            	return false;
//            };
//            var urlstr="cname="+cname+"&ename="+ename+"&jname="+jname+"&role="+role+"&password="+password+"&sign="+sign+"&seal="+seal+"&id="+id;
//            $.ajax({
//				type:"post",
//				url:"xinan/changeSubUser.action",
//				async:"false",
//				data:urlstr,
//				success: function(data){
//					if(data){
//						alert("修改提交成功！");
//						$("#password").val("");
//			            $("#password2").val("");
//			            /*$("#cname").val("");*/
//						$("#ename").val("");
//						$("#jname").val("");
//			            $("#id").val("");
//			            $("#role").val(0);
//			            $("#sign").val("");
//			            $("#seal").val("");
//						find();
//					}
//				}
//			});
//		}
//删除操作
function dele(e){
			if(confirm("ユーザーは削除しますか？")){
				var eparent = e.parentNode;
				var tparent = eparent.parentNode;
				var tdid = tparent.getElementsByTagName('td')[0];
				var id = tdid.innerHTML;
				$.ajax({
					type:"POST",
					url:"xinan/deleuser.action?account="+id,
					async:"false",
					dataType:"json",
					error:function(){	
						alert("削除しませんでした");
					},
					success:function(data){
						tparent.parentNode.removeChild(tparent);
						alert("削除しました");
						//画面删除节点
					}
				});
			}else{
				return false;
			}
		}
//修改
function change(e){
			var eparent = e.parentNode;
			var tparent = eparent.parentNode;
			//查找账号
			var tdid = tparent.getElementsByTagName('td')[0];
			//对应隐藏userID
			var id = tdid.innerHTML;
			$("#userID").val(id);
			
			document.getElementById("id").setAttribute("readonly","true");
			//document.getElementById("id").backgroundColor="#EBEBE4"; 
			/*var tdcname = tparent.getElementsByTagName('td')[1];
			var tdename = tparent.getElementsByTagName('td')[2];
			var tdjname = tparent.getElementsByTagName('td')[3];
			var tdrole = tparent.getElementsByTagName('td')[4];
			var cname = tdcname.innerHTML;
			var ename = tdename.innerHTML;
			var jname = tdjname.innerHTML;*/
			$.ajax({
					type:"POST",
					url:"xinan/usertest1.action",
					async:"true",
					data:{"UserID":id},
					dataType:"json",
					contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					success:function(data){
						 $("#ename").val(data[0].enName);
						 $("#jname").val(data[0].jpName);
						 $("#cname").val(data[0].cnName);
						 $("#password").val(data[0].password);
						 $("#id").val(data[0].account);
						 $("#role").val(data[0].role);
				         //$("#sign").val(data[0].sign);
				         //$("#seal").val(data[0].seal);
						 $("#user-sign").attr("src", "xinan/usersign.action?userId="+id);
						 $("#user-seal").attr("src", "xinan/userseal.action?userId="+id);
//						 var dssSrc = document.getElementById("user-sign").img;
//						 var dskSrc = document.getElementById("user-seal").img;
//						 $("#dss").attr("value",  dssSrc);
//						 $("#dsk").attr("value",  dskSrc);
						}
				});
		}

function clearform(){
	document.getElementById("id").removeAttribute("readonly");
	document.getElementById("focus").reset();
	sampleText_init();
}
		
/*//电子印章
function sealchange(e){
	alert(123);
    document.getElementById("dsk").value = e.value;
}
//电子签名
function signchange(e){
    document.getElementById("dss").value = e.value;}*/
		  
function imgOnload(e){
		if(e.width > 50){
			e.width = 50;
		}
}

