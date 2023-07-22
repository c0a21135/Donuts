<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*"%>
<html>  
<head>
    <meta charset="UTF-8">
    <title>管理者ログイン</title>
    <link rel="stylesheet" type="text/css" href="../css/all.css" media="all" id="cssMain">
</head>      
<body>
    <header>
    <div class="design-brown"></div><div class="design-white"></div>
    <div class="header-logo">
    <a>管理者ログイン</a>
    </div>
    <div class="icon">
        <img src="../img/TUROto.png">
    </div>
    </header>
    <div class="design-line"></div>
    <div class="design-btm"></div>
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>


    <div class="form">
    <form action= "./LoginSessionServlet" method="post">
    <br>
    ユーザ名　：<input type="text" name="checker_name" required><br>
    パスワード：<input type="password" name="checker_pw"  required><br>
    <input type="submit" value="認証">
    </form>
    </div>


    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>
    <footer>
    <div class="footer-logo">
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/Loginuser">購入登録ページへ ＞ </a>
    </div>
    </footer>
    <div class="design-white"></div><div class="design-brown"></div>

    
    <%-- <form action= "./LoginSessionServlet" method="post">
    ユーザ名：<input type="text" name="checker_name" required><br>
    パスワード：<input type="password" name="checker_pw" value="<%=initPass%>" required><br>
    <input type="submit" value="認証">
    </form> --%>
</body>
</html>
