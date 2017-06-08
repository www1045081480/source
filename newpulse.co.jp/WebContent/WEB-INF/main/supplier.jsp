<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="comheader.jsp" flush="true" />
<html>
<script type="text/javascript" src="js/supplier.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery-migrate.min.js"></script>
<script type="text/javascript" src="js/jquery.autosize.min.js"></script>
<body>
	<table class="stable" id="wrapper"
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
								<h3 id="h3">&nbsp;仕入先登録・検索・更新</h3>
								<div id="regist_cont" class="clearfix">

									<form id="form">
										<input id="supplierid" name="supplierid" type="text" value=""
											style="display: none">
										<table class="regist_tbl" summary="仕入先ー登録">
												<tbody>
												<tr>
													<th style="width:150px;" class="req" scope="row">会社名（日本語）</th>
													<td><input  id="jpname" name="jpname" type="text"
														maxlength="64"
														value="" sampletext="(例)XXX機械製作所" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												
												<tr>
													<th class="req" scope="row">会社名（中国語）</th>
													<td><input id="cnname" name="cnname" type="text"
													maxlength="64"
														value="" sampletext="(例)XXX机械制造厂" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>

												<tr>
													<th class="req" scope="row">会社名（英語）</th>
													<td><input id="enname" name="enname" type="text"
													maxlength="64"
														value="" sampletext="(例)XXX mechanism" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												
												<tr>
													<th class="req" scope="row">会社名（英語略語）</th>
													<td><input id="enshortname" name="enshortname" type="text"
													maxlength="64"
														value="" sampletext="(例)AAA" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>

												<tr>
													<th class="" scope="row">所在国</th>
													<td><input id="country" name="country" type="text"
													maxlength="64"
														value="" sampletext="(例)日本" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>

												<tr>
													<th class="" scope="row">住所（県、市、区）</th>
													<td><textarea id="address1" name="address1" type="text"
													maxlength="64"
														value="" sampletext="(例)愛知県" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal;color: rgb(51, 51, 51);width:200px;"></textarea></td>
												</tr>
												
												<tr>
													<th class="" scope="row">住所（町）</th>
													<td><textarea id="address2" name="address2" type="text"
													maxlength="64"
														value="" sampletext="(例)岡崎市美合町" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal;color: rgb(51, 51, 51);width:200px;"></textarea></td>
												</tr>
												
												<tr>
													<th class="" scope="row">住所（巨細）</th>
													<td><textarea id="address3" name="address3" 
													maxlength="64"
														value="" sampletext="(例)XXXX8番14号" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal;color: rgb(51, 51, 51);width:200px;"></textarea></td>
												</tr>
												
												<tr>
													<th class="" scope="row" style="backgroud-align:center">住所-英語（県、市、区）</th>
													<td><textarea id="enaddress1" name="enaddress1" type="text"
													maxlength="64"
														value="" sampletext="(例) Anqing City,Anhui Province" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal;color: rgb(51, 51, 51);width:200px;"></textarea></td>
												</tr>
												
												<tr>
													<th class="" scope="row">住所-英語（町）</th>
													<td><textarea id="enaddress2" name="enaddress2" type="text"
													maxlength="64"
														value="" sampletext="(例)Yingbin Road of Development Area" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal;color: rgb(51, 51, 51);width:200px;"></textarea></td>
												</tr>
												
												<tr>
													<th class="" scope="row">住所-英語（巨細）</th>
													<td><textarea id="enaddress3" name="enaddress3"
													maxlength="64"
														value="" sampletext="(例)No.317" class="textform"
														style="min-width: 200px; max-width: 200px; overflow: hidden; word-wrap: break-word; resize: horizontal;color: rgb(51, 51, 51);width:200px;"></textarea></td>
												</tr>
												
												<tr>
													<th class="" scope="row">郵便番号</th>
													<td><input id="postcd" name="postcd" type="text"
													maxlength="64"
														value="" sampletext="(例)170-0014" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												<tr>
													<th class="" scope="row">電話番号１</th>
													<td><input id="tel1" name="tel1" type="text"
													maxlength="16"
														value="" sampletext="(例)03－6912－XXXX" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												<tr>
													<th class="" scope="row">電話番号２</th>
													<td><input id="tel2" name="tel2" type="text"
													maxlength="16"
														value="" sampletext="(例)03－6912－XXXX" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
												<tr>
													<th class="" scope="row">ファクス</th>
													<td><input id="fax" name="fax" type="text"
													maxlength="16"
														value="" sampletext="(例)03－6912－7996" class="textform"
														style="color: rgb(51, 51, 51);width:200px;"></td>
												</tr>
											</tbody>
										</table>
									</form>

									<div id="btcontents">
									<div class="box">
											<p onclick="reset();" class="button orange medium">クリア</p>
										</div>
										<div class="box" style="margin-left: 50px;">
											<a onclick="save();" id="btn_confirm"
												class="button blue medium">登録</a>
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
						<div id="inputPane">
							<div class="regist clearfix">
								<h3 id="h3">&nbsp;仕入先一覧</h3>
								<div style="width: 775px;height:400px; overflow: scroll;">
								<table id="sample" width="1800px">
									<tbody>
										<tr>
											<th style="display: none">仕入先ID</th>
											<th style="width:150px;">会社名（日本語）</th>
											<th style="width:150px;">会社名（中国語）</th>
											<th style="width:150px;">会社名（英語）</th>
											<th style="width:150px;">会社名（英語略語）</th>
											<th style="width:150px;">所在国</th>
											<th style="width:200px;display: none">住所（県、市、区）</th>
											<th style="width:200px;display: none">住所（町）</th>
											<th style="width:200px;display: none">住所（巨細）</th>
											<th style="width:200px;display: none">住所-英語（県、市、区）</th>
											<th style="width:200px;display: none">住所-英語（町）</th>
											<th style="width:200px;display: none">住所-英語（巨細）</th>
											<th style="width:200px;display: none">郵便番号</th>
											<th style="width:200px;">電話番号１</th>
											<th style="width:200px;">電話番号２</th>
											<th style="width:200px;">ファクス</th>
											<th colspan="2" style="width:200px;">操作</th>
										</tr>
										<div>
										<s:iterator value="suppliers">
											<tr align="center">
												<td style="display: none"><s:property value="SupplierId" /></td>
												<td><s:property value="JpName"/></td>
												<td><s:property value="CnName"/></td>
												<td><s:property value="EnName"/></td>
												<td><s:property value="EnShortName"/></td>
												<td><s:property value="country"/></td>
												<td style="display: none"><s:property value="Address1"/></td>
												<td style="display: none"><s:property value="Address2"/></td>
					 							<td style="display: none"><s:property value="Address3"/></td>
												<td style="display: none"><s:property value="EnAddress1"/></td>
												<td style="display: none"><s:property value="EnAddress2"/></td>
												<td style="display: none"><s:property value="EnAddress3"/></td>
												<td style="display: none"><s:property value="PostCd"/></td>
												<td><s:property value="Tel1"/></td>
												<td><s:property value="Tel2"/></td>
												<td><s:property value="Fax"/></td>
												<td> 
													<input  class="listbt" type="button" value="編集" onclick="change(this);"/></td>
												<td>
													<input class="listbt" type="button" value="削除" onclick="dele(this);"/></td>
																</tr>
										</div>
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
		$('#address1').autosize({
			append: "\n"
		});
		$('#address2').autosize({
			append: "\n"
		});
		$('#address3').autosize({
			append: "\n"
		});
		$('#enaddress1').autosize({
			append: "\n"
		});
		$('#enaddress2').autosize({
			append: "\n"
		});
		$('#enaddress3').autosize({
			append: "\n"
		});
	});
</script>

</html>