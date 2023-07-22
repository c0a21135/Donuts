<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, pnw.checker.UserInfoBean"%>
<html>   
<head>
    <meta charset="UTF-8">
    <title>管理者ホーム</title>
    <link rel="stylesheet" type="text/css" href="../css/all.css" media="all" id="cssMain">
</head>     
<body>
    <header>
    <div class="design-brown"></div><div class="design-white"></div>
    <div class="header-logo">
    <a>管理者ホーム</a>
    </div>
    <div class="icon">
        <img src="../img/TUROto.png">
    </div>
    </header>
    <div class="design-line"></div>
    <div class="design-btm"></div>
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <%-- <%
    UserInfoBean bean = (UserInfoBean)session.getAttribute("cuser");
    %>
    <%=bean.getUserID()%>：でログイン中 --%>
    
    <div class="form footer-logo">
    <div class="bottom-li"><a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/OperateHomeSurvlet">販売用ページ ＞</a></div>
    <div class="bottom-li"><a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/checker/StockShowServlet">在庫管理 ＞</a></div>

    </div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>
    <footer>
    <div class="footer-logo">
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/checker/checkerlogin.jsp">ログアウト ＞</a>
    </div>
    </footer>
    <div class="design-white"></div><div class="design-brown"></div>

</body>
</html>
