<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,pnw.common.CartBean,pnw.common.*,pnw.*"%>
<html>  
<head>
    <meta charset="UTF-8">
    <title>最終確認</title>
    <link rel="stylesheet" type="text/css" href="css/all.css" media="all" id="cssMain">
</head>
<body>
    <header>
    <div class="design-brown"></div><div class="design-white"></div>
    <div class="header-logo">
    <a>最終確認</a>
    </div>
    <div class="icon">
        <img src="img/TUROto.png">
    </div>
    </header>
    <div class="design-line"></div>
    <div class="design-btm"></div>
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <%
    UsersBean user = (UsersBean)session.getAttribute("user");
    CartBean cart = (CartBean)session.getAttribute("shoppingcart");
    
    %>



    <div class="form">
        <form action="./UsersProcessServlet" method="post">
        <br>
        整理番号：<%=user.getDockedNumber()%><br>
        名前：<%=user.getNickName()%><br>
        ニックネーム：<input type="text" name="nickname" required><br>
        <input type="submit" name="btn" value="カートへ">
        </form>
    </div>
    <div class="form">
        <p>商品のラインナップを知りたい方は<a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/c0a2111648/misuta/stock.jsp">こちら</a></p>
    </div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>
    <footer>
    <div class="footer-logo">
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/checker/checkerlogin.jsp"> 管理者用ログイン ＞</a>
    </div>
    </footer>
    <div class="design-white"></div><div class="design-brown"></div>


</body>
</html>

