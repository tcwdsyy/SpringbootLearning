<%--
  Created by IntelliJ IDEA.
  User: shiyuyang
  Date: 2024/1/2
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>hello!</h1>
<h1>欢迎回来</h1>
    <p>id: ${id}</p>
<form class="transferForm" action="transfer">
    <div>
        <label for="id">账户ID</label>
        <input type="text" class="input-text" id="id" name="id" placeholder="请输入用户ID" required/>
    </div>
    <div>
        <label for="amount">金额</label>
        <input type="text" class="input-text" id="amount" name="amount" placeholder="请输入金额" required/>
    </div>
    <div class="submit">
        <input type="submit" value="转账"/>
    </div>
</form>
</body>
</html>
