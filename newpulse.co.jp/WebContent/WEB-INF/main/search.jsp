<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>加盟店管理</title>
<meta name="description" content="这是一个 index 页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="../../assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed" href="../../assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="../../assets/css/amazeui.min.css"/>
<link rel="stylesheet" href="../../assets/css/admin.css">
<script src="../../assets/js/jquery.min.js"></script>
<script src="../../assets/js/app.js"></script>
<script src="../../assets/js/search.js"></script>
</head>
<body>

</head>

<body>
<header class="am-topbar admin-header" >
  <div class="am-topbar-brand"><img src="../../assets/i/logo.png"></div>
  <div class="am-collapse am-topbar-collapse" id="topbar-collapse" >
    <ul class="am-nav am-nav-pills am-topbar-nav admin-header-list">
      <!--  <li class="am-dropdown tognzhi" data-am-dropdown>
        <button class="am-btn am-btn-primary am-dropdown-toggle am-btn-xs am-radius am-icon-bell-o" data-am-dropdown-toggle> 消息管理<span class="am-badge am-badge-danger am-round">6</span></button>
        <ul class="am-dropdown-content">
          <li class="am-dropdown-header">所有消息都在这里</li>
          <li><a href="#">未激活会员 <span class="am-badge am-badge-danger am-round">556</span></a></li>
          <li><a href="#">未激活代理 <span class="am-badge am-badge-danger am-round">69</span></a></a></li>
          <li><a href="#">未处理汇款</a></li>
          <li><a href="#">未发放提现</a></li>
          <li><a href="#">未发货订单</a></li>
          <li><a href="#">低库存产品</a></li>
          <li><a href="#">信息反馈</a></li>
        </ul>
      </li>
      -->
      <li class="soso"> 
        <p>
          <button type="button" class="am-btn am-btn-danger am-round am-btn-xs am-icon-plus" data-am-modal="{target: '#my-popup'}">新規</button>
        </p>
       </li>
      <li class="kuanjie"><a>加盟店ID</a> </li>
      <li class="soso">
        
        <p class="ycfg">
          <input type="text" class="am-form-field am-input-sm" id= "NapaId" placeholder="一意検索" value="<s:property value='NapaId'/>" />
        </p>
        
      </li>
      <li class="kuanjie"><a href="#">加盟店名</a> </li>
      <li class="soso">
        <p class="ycfg">
          <input type="text" class="am-form-field am-input-sm" id= "Cname" placeholder="曖昧検索" value="<s:property value='Cname'/>"/>
        </p>
      </li>
      <li class="soso"> 
        <p>
          <button class="am-btn am-btn-xs am-btn-default am-xiao" onclick="Search();"><i class="am-icon-search">検索</i></button>
        </p>
       </li>
       <li class="soso"> 
        <p>
          <button class="am-btn am-btn-xs am-btn-default am-xiao" onclick="Clean();"><i>クリア</i></button>
        </p>
       </li>
       
        <li class="kuanjie"><a></a> <a></a><a></a><a></a><a></a><a></a><a></a><a></a></li>
      <li class="am-hide-sm-only" style="float: right;"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
    </ul>
  </div>
</header>
<div class="am-cf admin-main">
  <div class="nav-navicon admin-main admin-sidebar">
    <div class="sideMenu am-icon-dashboard" style="color:#aeb2b7; margin: 10px 0 0 0;"> ユーザー：admin</div>
    <div class="sideMenu">
      <h3 class="am-icon-flag"><em></em> <a href="#">商品管理</a></h3>
      <ul>
        <li><a href="">商品列表</a></li>
        <li class="func" dataType='html' dataLink='msn.htm' iconImg='images/msn.gif'>添加新商品</li>
        <li>商品分类</li>
        <li>用户评论</li>
        <li>商品回收站</li>
        <li>库存管理 </li>
      </ul>
      <h3 class="am-icon-cart-plus"><em></em> <a href="#"> 订单管理</a></h3>
      <ul>
        <li>订单列表</li>
        <li>合并订单</li>
        <li>订单打印</li>
        <li>添加订单</li>
        <li>发货单列表</li>
        <li>换货单列表</li>
      </ul>
      <h3 class="am-icon-users"><em></em> <a href="#">会员管理</a></h3>
      <ul>
        <li>会员列表 </li>
        <li>未激活会员</li>
        <li>团队系谱图</li>
        <li>会员推荐图</li>
        <li>推荐列表</li>
      </ul>
      <h3 class="am-icon-volume-up"><em></em> <a href="#">信息通知</a></h3>
      <ul>
        <li>站内消息 /留言 </li>
        <li>短信</li>
        <li>邮件</li>
        <li>微信</li>
        <li>客服</li>
      </ul>
      <h3 class="am-icon-gears"><em></em> <a href="#">系统设置</a></h3>
      <ul>
        <li>数据备份</li>
        <li>邮件/短信管理</li>
        <li>上传/下载</li>
        <li>权限</li>
        <li>网站设置</li>
        <li>第三方支付</li>
        <li>提现 /转账 出入账汇率</li>
        <li>平台设置</li>
        <li>声音文件</li>
      </ul>
    </div>
    <!-- sideMenu End --> 
    
    <script type="text/javascript">
			jQuery(".sideMenu").slide({
				titCell:"h3", //鼠标触发对象
				targetCell:"ul", //与titCell一一对应，第n个titCell控制第n个targetCell的显示隐藏
				effect:"slideDown", //targetCell下拉效果
				delayTime:300 , //效果时间
				triggerTime:150, //鼠标延迟触发时间（默认150）
				defaultPlay:true,//默认是否执行效果（默认true）
				returnDefault:true //鼠标从.sideMen移走后返回默认状态（默认false）
				});
		</script> 
  </div>
 
  <div class=" admin-content" >
    <div class="daohang">
    <!--  
      <ul>
        <li>
        <button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
        首页
        </li>
        <li>
        <button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
        帮助中心<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
        </button>
        </li>
        <li>
          <button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
          奖金管理<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
          </button>
        </li>
        <li>
          <button type="button" class="am-btn am-btn-default am-radius am-btn-xs">
          产品管理<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close="">×</a>
          </button>
        </li>
      </ul>
      -->
    </div>

    <div class="am-popup am-popup-inner" id="my-popup">
      <div class="am-popup-hd">
        <h4 class="am-popup-title">添加商品一级分类</h4>
        <span data-am-modal-close
            class="am-close">&times;</span> </div>
      <div class="am-popup-bd">
        <form class="am-form tjlanmu">
          <div class="am-form-group">
            <div class="zuo">栏目名称：</div>
            <div class="you">
              <input type="email" class="am-input-sm" id="doc-ipt-email-1" placeholder="请输入标题">
            </div>
          </div>
          <div class="am-form-group">
            <div class="zuo">栏目关键词：</div>
            <div class="you">
              <input type="password" class="am-input-sm" id="doc-ipt-pwd-1" placeholder="请输入关键词">
            </div>
          </div>
          <div class="am-form-group am-cf">
            <div class="zuo">栏目描述：</div>
            <div class="you">
              <textarea class="" rows="2" id="doc-ta-1"></textarea>
            </div>
          </div>
          <div class="am-form-group am-cf">
            <div class="zuo">栏目图片：</div>
            <div class="you" style="height: 45px;">
              <input type="file" id="doc-ipt-file-1">
              <p class="am-form-help">请选择要上传的文件...</p>
            </div>
          </div>
          <div class="am-form-group am-cf">
            <div class="zuo">简介：</div>
            <div class="you">
              <textarea class="" rows="2" id="doc-ta-1"></textarea>
            </div>
          </div>
          <div class="am-form-group am-cf">
            <div class="zuo">状态：</div>
            <div class="you" style="margin-top: 3px;">
              <label class="am-checkbox-inline">
                <input type="checkbox" value="option1">
                显示 </label>
              <label class="am-checkbox-inline">
                <input type="checkbox" value="option2">
                隐藏 </label>
            </div>
          </div>
          <div class="am-form-group am-cf">
            <div class="you">
              <p>
                <button type="submit" class="am-btn am-btn-success am-radius">提交</button>
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
    
          <div class="am-popup am-popup-inner" id="my-popups">
        <div class="am-popup-hd">
          <h4 class="am-popup-title">修改栏目名称</h4>
          <span data-am-modal-close class="am-close">&times;</span> </div>
        <div class="am-popup-bd">
          <form class="am-form tjlanmu">
            <div class="am-form-group">
              <div class="zuo">栏目名称：</div>
              <div class="you">
                <input type="email" class="am-input-sm" id="doc-ipt-email-1" placeholder="请输入标题">
              </div>
            </div>
            <div class="am-form-group">
              <div class="zuo">栏目关键词：</div>
              <div class="you">
                <input type="password" class="am-input-sm" id="doc-ipt-pwd-1" placeholder="请输入关键词">
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">栏目描述：</div>
              <div class="you">
                <textarea class="" rows="2" id="doc-ta-1"></textarea>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">栏目图片：</div>
              <div class="you" style="height: 45px;">
                <input type="file" id="doc-ipt-file-1">
                <p class="am-form-help">请选择要上传的文件...</p>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">简介：</div>
              <div class="you">
                <textarea class="" rows="2" id="doc-ta-1"></textarea>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">状态：</div>
              <div class="you" style="margin-top: 3px;">
                <label class="am-checkbox-inline">
                  <input type="checkbox" value="option1">
                  显示 </label>
                <label class="am-checkbox-inline">
                  <input type="checkbox" value="option2">
                  隐藏 </label>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="you">
                <p>
                  <button type="submit" class="am-btn am-btn-success am-radius">提交</button>
                </p>
              </div>
            </div>
          </form>
        </div>
      </div>
    
    
    
    
    
    
    <div class="am-popup am-popup-inner" id="my-popupss">
        <div class="am-popup-hd">
          <h4 class="am-popup-title">复制栏目</h4>
          <span data-am-modal-close class="am-close">&times;</span> </div>
        <div class="am-popup-bd">
          <form class="am-form tjlanmu">
          
          
          <div class="am-form-group">
              <div class="zuo">复制到：</div>
              <div class="you">
              
  <select data-am-selected="{btnWidth: 200, btnSize: 'sm', btnStyle: 'danger'}">
  <option value="a">Apple</option>
  <option value="b">Banana</option>
  <option value="o">Orange</option>
  <option value="m">Mango</option>
</select>
 顶级栏目选默认

              </div>
            </div>
          
          
            <div class="am-form-group">
              <div class="zuo">新栏目名称：</div>
              <div class="you">
                <input type="email" class="am-input-sm" id="doc-ipt-email-1" placeholder="请输入标题">
              </div>
            </div>
            <div class="am-form-group">
              <div class="zuo">栏目关键词：</div>
              <div class="you">
                <input type="password" class="am-input-sm" id="doc-ipt-pwd-1" placeholder="请输入关键词">
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">栏目描述：</div>
              <div class="you">
                <textarea class="" rows="2" id="doc-ta-1"></textarea>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">栏目图片：</div>
              <div class="you" style="height: 45px;">
                <input type="file" id="doc-ipt-file-1">
                <p class="am-form-help">请选择要上传的文件...</p>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">简介：</div>
              <div class="you">
                <textarea class="" rows="2" id="doc-ta-1"></textarea>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="zuo">状态：</div>
              <div class="you" style="margin-top: 3px;">
                <label class="am-checkbox-inline">
                  <input type="checkbox" value="option1">
                  显示 </label>
                <label class="am-checkbox-inline">
                  <input type="checkbox" value="option2">
                  隐藏 </label>
              </div>
            </div>
            <div class="am-form-group am-cf">
              <div class="you">
                <p>
                  <button type="submit" class="am-btn am-btn-success am-radius">提交</button>
                </p>
              </div>
            </div>
          </form>
        </div>
      </div>
    
    
    
    
    
    
    
    
    
    
    <div class="admin-biaogelist">
      <div class="listbiaoti am-cf">
        <ul class="am-icon-flag on">
          		加盟店情報
        </ul>
        <dl class="am-icon-home" style="float: right;">
          当前位置： 首页 > <a href="#">商品列表</a>
        </dl>        
      </div>
      <form class="am-form am-g">
        <table width="100%" class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover">
          <thead>
            <tr class="am-success">
              <th class="table-id">加盟店ID</th>
              <th class="table-title">加盟店名称</th>
              <th class="table-type">支店名</th>
              <th class="table-author am-hide-sm-only">セキュアキー</th>
              <th width="163px" class="table-set">操作</th>
            </tr>
          </thead>
          
          <tbody>
         
          <s:iterator id="li" value="list"   status="">
           <tr>
                  <td><s:property value='NapaId'/></td>
                  <td><s:property value='Cname'/></td>
                  <td><s:property value='SubCName'/></td>
                  <td><s:property value='Security'/></td>
                  <td><div class="am-btn-toolbar">
                  <div class="am-btn-group am-btn-group-xs">
                  <a class="am-btn am-btn-default am-btn-xs am-text-success am-round am-icon-file" data-am-modal="{target: '#my-popups'}" title="改修"></a>
                  </div>
                </div></td>
                  </tr>
          </s:iterator>
          </tbody>
        </table>
        <div class="am-btn-group am-btn-group-xs">
        </div>
        <hr />
      </form>

      <div class="foods">
        <ul>
          
          CopyRight　NewPulse Co., Ltd 2017
        </ul>
        <dl>
          <a href="" title="返回头部" class="am-icon-btn am-icon-arrow-up"></a>
        </dl>
      </div>
    </div>
  </div>
</div>
<form id="SearchForm" action="search.action"  method="post" style="display:none">
  <input type="text" name= "NapaId" style="display:none" id= "NapaId1"/>
  <input type="text" name= "Cname" style="display:none" id= "Cname1"/>
</form>
<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/polyfill/rem.min.js"></script>
<script src="assets/js/polyfill/respond.min.js"></script>
<script src="assets/js/amazeui.legacy.js"></script>
<![endif]--> 

<!--[if (gte IE 9)|!(IE)]><!--> 
<script src="../../assets/js/amazeui.min.js"></script> 
<!--<![endif]-->

</body>
</html>