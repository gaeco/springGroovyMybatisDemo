<%--
  Created by IntelliJ IDEA.
  User: GSYHS
  Date: 2018-01-29
  Time: 오후 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <style>
        body{
            display: table;
            vertical-align: middle;
            height: 100%;
            width: 100%;
        }
        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }

        .container-login{
            width: 500px;
            height: 400px;
            background-color: #fefefe;
        }

        .login-form{
            display: table;
            vertical-align: middle;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: auto;
            padding-top: 200px
        }
    </style>
</head>
<body>
<form class="login-form" action="/loginCheck" method="post">
    <div class="container-login">
        <H2>LG 수처리 ERP</H2>
        <label for="userid"><b>USER ID</b></label>
        <input type="text" placeholder="Enter User ID" name="userid" id="userid"/><br/><br/>
        <label for="userid"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="pwd" id="pwd"/><br/><br/>
        <button type="submit">Login</button>
    </div>
</form>
<%
    String msg = request.getParameter("msg");
    if(msg != null && !msg.equals("")){
%>
<script>
    alert("<%=msg%>");
</script>
<%
    }
%>
</body>
</html>
