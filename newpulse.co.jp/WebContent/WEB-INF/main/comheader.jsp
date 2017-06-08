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
<link rel="stylesheet" type="text/css" href="css/common.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/validate.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/regist.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/content.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/default.css?${initParam.version}">
<link rel="stylesheet" type="text/css" href="css/stylea.css?${initParam.version}">
<link href="css/table.css?${initParam.version}" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/user.js?${initParam.version}"></script>
<script type="text/javascript" src="js/jquery.min.js?${initParam.version}"></script>
<script type="text/javascript" src="js/util.js?${initParam.version}"></script>
<script type="text/javascript" src="js/validate.js?${initParam.version}"></script>
<script type="text/javascript" src="js/sampleText.js?${initParam.version}"></script>
</head>