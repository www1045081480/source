function Search(){
	 var NapaId = document.getElementById("NapaId").value;
	 var Cname = document.getElementById("Cname").value;
	 document.getElementById("Cname1").value = document.getElementById("Cname").value;
	 document.getElementById("NapaId1").value = document.getElementById("NapaId").value;
	 document.getElementById("SearchForm").submit();
}
function Clean(){
	 document.getElementById("Cname").value = "";
	 document.getElementById("NapaId").value= "";
	 document.getElementById("Cname1").value = "";
	 document.getElementById("NapaId1").value = "";
}