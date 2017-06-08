<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="comheader.jsp" flush="true" />
<html>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>
<script type="text/javascript" src="js/item.js?${initParam.version}"></script>
<body>
	<table width="970" id="wrapper"
		style="background: linear-gradient(#05FBFF, #1E00FF);">
		<tbody>
			<tr>
				<jsp:include page="headerm.jsp" flush="true" />
			</tr>
			<tr>
				<td id="container">
					<div>
						<div id="inputPane">
							<div class="regist clearfix">
								<h3 id="h3">&nbsp;商品登録・検索・更新</h3>
								<div id="regist_cont" class="clearfix">

									<form id="form">
										<input id="itemid" name="itemid" type="text" value=""
											style="display: none">
										<table class="regist_tbl" summary="itemー登録" >
											<tbody>
												<tr>
													<th class="req" scope="row">大分類</th>
														<td>
															<select id="categorytype" name="categorytype">
															<option value="1" selected>材料</option>
										  					<option value="2">生产设备</option>
										  					<option value="3">检查设备</option>
										  					<option value="4">其他</option>
															</select>
													</td>
												</tr>
												
												<tr>
													<th class="req" scope="row">小分類</th>
													<td><input maxlength="64"  id="family" name="family" type="text"
														value="" sampletext="(例)XXX" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												<tr>
													<th class="req" scope="row">品名（日本語）</th>
													<td ><input maxlength="64" id="jpname" name="jpname" type="text"
														value="" sampletext="(例)エレベーター" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												
												<tr>
													<th  scope="row">規格（日本語）</th>
													<td><textarea maxlength="256" id="jpdesc" name="jpdesc" 
														value="" 
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal; height: 40px; color: rgb(51, 51, 51);width:200px;height:30px"></textarea></td>
												</tr>

												<tr>
													<th class="req" scope="row">品名（中国語）</th>
													<td><input maxlength="64"  id="cnname" name="cnname" type="text"
														value="" sampletext="(例)升降机" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												
												<tr>
													<th  scope="row">規格（中国語）</th>
													<td><textarea maxlength="256"  id="cndesc" name="cndesc" 
														value="" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal; height: 40px; color: rgb(51, 51, 51);width:200px;height:30px"></textarea></td>
												</tr>

												<tr>
													<th class="req" scope="row">品名（英語）</th>
													<td><input maxlength="64"  id="enname" name="enname" type="text"
														value="" sampletext="(例)elevator" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												
												<tr>
													<th  scope="row">規格（英語）</th>
													<td><textarea maxlength="256"  id="endesc" name="endesc"
														value=""  class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal; height: 40px; color: rgb(51, 51, 51);width:200px;height:30px"></textarea></td>
												</tr>
												
												
												<tr style="display: none">
													<th class="req" scope="row">品番</th>
													<td><input maxlength="64"  id="partscd" name="partscd" type="text"
														value="" sampletext="(例)URO" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												
												<tr style="display: none">
													<th class="req" scope="row">型番</th>
													<td><input maxlength="64"  id="modelcd" name="modelcd" type="text"
														value="" sampletext="(例)89-01" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
											</tbody>
										</table>
									</form>

									<div id="btcontents">
										<div class="box">
											<p onclick="reset();" class="button orange medium">クリア</p>
										</div>
										<div class="box">
											<a onclick="save();" id="btn_confirm"
												class="button blue medium" style="margin-left: 50px;">登録</a>
										</div>
										<div class="box" >
											<p onclick="find();" class="button green medium">検索</p>
										</div>
										<div class="box">
											<p onclick="updata();" class="button rosy medium">更新</p>
										</div>
									</div>
									<!-- end login_cont -->
								</div>
								<!-- end container -->
							</div>
							<!-- end #inputPane -->
						</div>
						<style type='text/css'>
tr.locktop{
  position:relative;
  top:expression((this.offsetParent.scrollTop>this.parentElement.parentElement.offsetTop?this.offsetParent.scrollTop-this.parentElement.parentElement.offsetTop-1:0)-1);
}
</style>
						<div id="inputPane">
							<div class="regist clearfix">
								<h3 id="h3">&nbsp;商品一覧</h3>
								<div style="width: 775px;height:400px; overflow: scroll;">
								<table id="sample" width="762px">
									<tbody>
										<tr>
											<th style="display: none">商品ID</th>
											<th vertical-align="center"  style="width:120px;">大分類</th>
											<th style="width:180px;">小分類</th>
											<th style="width:250px;">品名（日本語）</th>											
											<th style="display: none" style="width:150px;">品名（中国語）</th>
											<th style="display: none" style="width:150px;">品名（英語）</th>
											<th style="width:300px;">規格（日本語）</th>
											<th style="display: none" style="width:100px;">品番</th>
											<th style="display: none" style="width:100px;">型番</th>
											<th colspan="1" style="width:30px;">操作</th>
										</tr>
										<s:iterator value="items">
											<tr align="center">
												<td style="display: none"><s:property value="ItemId" /></td>
							 					<td style="text-align:left;">
							 							<s:if test="CategoryType==1">材料</s:if> 
							 							<s:if test="CategoryType==2">生产设备</s:if> 
							 							<s:if test="CategoryType==3">检查设备</s:if> 
							 							<s:if test="CategoryType==4">其他</s:if> 
							 					</td>
												<td style="text-align:left;"><s:property value="Family" /></td>
												<td style="text-align:left;"><s:property value="JpName" /></td>
												<td style="display: none" style="width:150px;text-align:left;"><s:property value="CnName" /></td>
												<td style="display: none" style="width:150px;text-align:left;"><s:property value="EnName" /></td>
											    <td ><s:property value="JpDesc" /></td>
												<td style="display: none"><s:property value="CnDesc" /></td>
												<td style="display: none"><s:property value="EnDesc" /></td>
												<td style="display: none" style="width:100px;text-align:left;"><s:property value="PartsCd" /></td>
												<td style="display: none" style="width:100px;text-align:left;"><s:property value="ModelCd" /></td>
												<td> 
													<input  class="listbt" type="button" value="編集" onclick="change(this);"/>
												</td>
												<!-- 
												<td>
													<input class="listbt" type="button" value="削除" onclick="dele(this);"/>
												</td>
												 -->
																</tr>
										</s:iterator>
									</tbody>
								</table>
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<!-- footerここから -->
					<jsp:include page="footer.jsp" flush="true" />
					<!-- footerここまで -->
				</td>
			</tr>
		</tbody>
	</table>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		$('#jpdesc').autosize({
			append: "\n"
		});
		$('#endesc').autosize({
			append: "\n"
		});
		$('#cndesc').autosize({
			append: "\n"
		});
	});
</script>

</html>

