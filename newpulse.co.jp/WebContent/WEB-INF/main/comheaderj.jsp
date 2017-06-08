<%@ page   language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="X-UA-Compatible">
<title>新安商事業務管理システム</title>

<link rel="stylesheet" type="text/css" href="css/commonj.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/content.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/default.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/stylea.css?${initParam.version}">
<script type="text/javascript" src="js/DatePicker.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery.min.js?${initParam.version}"></script>
<link rel="stylesheet" href="css/jquery-ui.css?${initParam.version}" />
<link rel="stylesheet" type="text/css" href="css/print.css?${initParam.version}">
<script type="text/javascript" src="js/DatePicker4ordermake.js?${initParam.version}"></script>
<script type="text/javascript" src="js/zDialog.js?${initParam.version}"></script>
<script type="text/javascript" src="js/zDrag.js?${initParam.version}"></script>
<script src="js/jquery-ui.js?${initParam.version}"></script>
<script src="js/jQuery.print.js?${initParam.version}"></script>
<script src="js/hashtable.js?${initParam.version}"></script>
<script src="js/jquery.datef.js?${initParam.version}"></script>
<script src="js/jquery.numeric.js?${initParam.version}"></script>
<script src="js/jquery.numfm1.2.4.js?${initParam.version}"></script>

</head>