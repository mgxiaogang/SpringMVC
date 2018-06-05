<%@ page import="com.liupeng.spring.shiro.vo.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String userName = (String)session.getAttribute("userName");
    User user = (User)request.getAttribute("loginUser");
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
    <c:if test="<%=userName != null%>">
        <div class="jumbotron">
            <h3>spring session测试</h3>
            <h3>用户名：<%=userName%>（session 域数据）</h3>
            <p><a class="btn btn-lg btn-success" href="/logout" role="button">注销</a></p>
        </div>
    </c:if>
    <c:if test="<%=user != null%>">
        <div class="jumbotron">
            <h3>shiro测试</h3>
            <h3>用户名：<%=user.getUserName()%>（request 域数据）</h3>
            <p><a class="btn btn-lg btn-success" href="/logout" role="button">注销</a></p>
        </div>
    </c:if>
</div>
</body>
</html>
