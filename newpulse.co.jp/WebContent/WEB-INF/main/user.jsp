<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="comheader.jsp" flush="true" />
<html>
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
								<h3 id="h3">&nbsp;ユーザー登録・検索・更新</h3>
								<div id="regist_cont" class="clearfix">

									<form id="focus" action="xinan/adduser.action" method="post"
										enctype="multipart/form-data">
										<input id="userID" name="userID" type="text" value=""
											style="display: none">
										<table class="regist_tbl" summary="ユーザー登録">
											<tbody>
												<tr>
													<th class="req" scope="row">姓名</th>
													<td><input id="jname" name="jname" type="text"
													maxlength="64"
														value="" sampletext="(例) 新安 一郎" class="textform"
														style="color: rgb(51, 51, 51);"></td>
												</tr>


												<tr>
													<th class="noreq" scope="row">姓名(英語表記)</th>
													<td><input id="ename" name="ename" type="text"
													maxlength="64"
														value="" sampletext="(例)XINAN YITIRO" class="textform"
														style="color: rgb(51, 51, 51);"></td>
												</tr>
												
												<tr>
													<th class="noreq" scope="row">姓名(中国語表記)</th>
													<td><input id="cname" name="cname" type="text"
													maxlength="64"
														value="" sampletext="(例)张 博然" class="textform"
														style="color: rgb(51, 51, 51);"></td>
												</tr>


												<tr>
													<th class="req" scope="row">ユーザーID</th>
													<td><input id="id" name="id" type="text" 
													maxlength="64"
														sampletext="(例)TtestUserId" class="textform t_katakana"
														sampled="true" style="color: rgb(204, 204, 204);">
													</td>
												</tr>

												<tr>
													<th class="req" scope="row">パスワード</th>
													<td><input id="password" name="password"
													maxlength="64"
														type="password" maxlength="10"
														class="textform password_form"><span
														class="form_right_text">(半角英数字6～10文字)</span></td>
												</tr>
												<tr>
													<th class="req" scope="row">パスワード(確認)</th>
													<td><input id="password2" name="password2"
													maxlength="64"
														type="password" maxlength="10"
														class="textform password_form"><span
														class="form_right_text">(半角英数字6～10文字)</span></td>
												</tr>


												<tr>
													<th class="req" scope="row">権限</th>
													<td><select id="role" name="role">
															<option value="0">--</option>
															<option value="1">営業</option>
															<option value="2">物流</option>
															<option value="3">財務</option>
															<option value="4">副社長</option>
															<option value="5">社長</option>
															<option value="6">管理员</option>
															<option value="7">営業物流</option>
													</select></td>
												</tr>


												<tr>
													<th class="noreq" scope="row">電子印鑑</th>
													<td><input id="dsk" type="text" value=""
														class="textform t_katakana"
														style="color: rgb(204, 204, 204);">
														<p class="file-upload btn btn-primary">
															<span>選択</span> <input class="upload" id="seal"
																name="seal" type="file">
														</p> <img onload="imgOnload(this);" id="user-seal" src="" /></td>
												</tr>


												<tr>
													<th class="noreq" scope="row">電子サイン</th>
													<td><input id="dss" type="text" value=""
														class="textform t_katakana"
														style="color: rgb(204, 204, 204);">
														<p class="file-upload btn btn-primary">
															<span>選択</span> <input class="upload" id="sign"
																name="sign" type="file">
														</p> <img onload="imgOnload(this);"  id="user-sign" src="" /></td>
												</tr>
											</tbody>
										</table>
									</form>

									<div id="btcontents">
										<div class="box">
											<p onclick="clearform();" class="button orange medium">クリア</p>
										</div>
										
										<div class="box" style="margin-left: 50px;">
											<a onclick="save();" id="btn_confirm"
												class="button blue medium">登録</a>
										</div>
										<div class="box">
											<p onclick="find();" class="button green medium">検索</p>
										</div>
										<div class="box">
											<p onclick="save();" class="button rosy medium">更新</p>
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
								<h3 id="h3">&nbsp;ユーザー一覧</h3>
								<div style="width: 775px; overflow-x: scroll;">
									<table id="sample" width="1300px">
										<tbody>
											<tr>
												<th style="display: none">ID</th>
												<th style="width:200px;">姓名</th>
												<th style="width:200px;">英語表記</th>
												<th style="width:200px;">中国語表記</th>
												<th style="width:100px;">ユーザーID</th>
												<!-- <th>パスワード</th> -->
												<th style="width:100px;">権限</th>
												<th style="width:150px;">電子印鑑</th>
												<th style="width:150px;">電子サイン</th>
												<th colspan="2" style="width:100px;">操作</th>

											</tr>
											<s:iterator value="userList">
												<tr align="center">
													<td style="display: none"><s:property value="userId" /></td>
													<td ><s:property value="jpName" /></td>
													<td ><s:property value="enName" /></td>
													<td ><s:property value="cnName" /></td>
													<td ><s:property value="account" /></td>
													<td ><s:if test="role==1">営業</s:if> <s:if
															test="role==2">物流</s:if> <s:if test="role==3">財務</s:if> <s:if
															test="role==4">副社長</s:if> <s:if test="role==5">社長</s:if>
															<s:if test="role==6">管理者</s:if> <s:if test="role==7">営業物流</s:if>
													</td>
													<td ><img style="width:50px;height:50px;" 
														src="xinan/userseal.action?userId=<s:property value='userId'/>" /></td>
													<td ><img style="width:50px;height:50px;" 
														src="xinan/usersign.action?userId=<s:property value='userId'/>" /></td>
													<td><input class="listbt" type="button" value="編集"
														onclick="change(this);" /></td>
													<td><input class="listbt" type="button" value="削除"
														onclick="dele(this);" /></td>
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
</html>
<script type="text/javascript">
document.getElementById("seal").onchange = function () {
    document.getElementById("dsk").value = this.value;
};
document.getElementById("sign").onchange = function () {
    document.getElementById("dss").value = this.value;
};
</script>


