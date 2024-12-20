<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page language="java" pageEncoding="utf-8" isErrorPage="true"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>500-<PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="/view/web-simple/atext/include-web.jsp"></jsp:include>
</head>
<body>
	<div class="super_content">
		<br />
	</div>
	<div class="containerbox" style="background-color: #fff;">
		<div class="container ">
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default userbox"
						style="margin: auto; width: 500px; margin-top: 100px; margin-bottom: 100px; background-color: #fcfcfc;">
						<div class="panel-body">
							<div class="text-center">
								<img src="<PF:basePath/>webfile/PubHomelogo.do" alt="wcp"
									style="margin: 20px; max-width: 128px;" />
							</div>
							<div id="errorMessageId" class="text-center"
								style="margin: -4px; padding: 4px; color: #d13133; font-size: 64px;">
								500</div>
							<div id="errorMessageId" class="text-center"
								style="margin: -4px; padding: 4px; color: #666; font-size: 16px;">
								抱歉，我们的服务遇到一些问题</div>

							<div id="errorMessageId" class="text-center"
								style="margin: -4px; padding: 4px; color: #666; font-size: 12px;">


								错误信息为： ${MESSAGE}
								<c:if test="${USEROBJ!=null}">
									<c:if test="${USEROBJ==null}">
									请登陆后查看具体问题原因
								</c:if>
								</c:if>
							</div>
							<div class="text-center" style="margin-top: 26px;">
								<a type="button" href="<PF:basePath/>"
									class="btn btn-danger btn-xs">系统首页</a>
								<c:if test="${USEROBJ!=null}">&nbsp;&nbsp;
									<a type="button" href="<PF:basePath/>webuser/PubHome.do"
										class="btn btn-default btn-xs">我的信息</a>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
