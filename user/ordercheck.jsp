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
    LinkedList<DonutsCountBean> dcountBean = cart.getDcountBean();
    Iterator<DonutsCountBean> ite = dcountBean.iterator();
    session.setAttribute("nonecart", "");

    double sum = 0.0;
    %>



    <div class="form">
        
        <br>
        こちらの内容で注文を確定します。よろしいですか？<br>
        整理番号：<%=user.getDockedNumber()%><br>
        名前：<%=user.getNickName()%><br>
        <div class="table">
            <table class="design01">
            <tr>
                <td>種類</td>
                <td>個数</td>
                <td>金額</td>
            </tr>
            <% //購入内容の表示
            while(ite.hasNext()){
                DonutsCountBean bean = ite.next();
            %>
            <tr>
                <td><%=bean.getDonutName()%></td>
                <td><%=bean.getDonutCount()%></td>
                <td><%=bean.getDonutPrice()%></td>
                <%
                    sum += bean.getDonutPrice()*bean.getDonutCount();
                %>
            </tr>
            <%
            }
        %>
            </table>

        合計金額：<%=sum%>円
        </div>
        <form action="./CartCheckingServlet" method="post">
            <input type="submit" name="btn" value="購入確定">
        </form>
    </div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>
    <footer>
    <div class="footer-logo">
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/Menu"> 注文画面へ戻る ＞</a>
    </div>
    </footer>
    <div class="design-white"></div><div class="design-brown"></div>


</body>
</html>

