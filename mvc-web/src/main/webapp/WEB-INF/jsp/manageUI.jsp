<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String userName = (String)session.getAttribute("userName");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>管理界面</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <%--<div class="jumbotron">--%>
    <%--<h3>端口为 8080 的页面${sessionScope}</h3>--%>
    <%--<h3>用户名：${sessionScope.userName}（session 域数据）</h3>--%>
    <%--<p><a class="btn btn-lg btn-success" href="/logout" role="button">注销</a></p>--%>
    <%--</div>--%>
    <div class="jumbotron">
        <h3>端口为 8080 的页面</h3>
        <h3>用户名：<%=userName%>（session 域数据）</h3>
        <p><a class="btn btn-lg btn-success" href="/logout" role="button">注销</a></p>
    </div>


</div>
</body>
</html>
