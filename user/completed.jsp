<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,pnw.common.CartBean,pnw.common.*,pnw.*"%>
<html>  
<head>
    <meta charset="UTF-8">
    <title>注文確定</title>
    <link rel="stylesheet" type="text/css" href="css/all.css" media="all" id="cssMain">
</head>
<body>
    <header>
    <div class="design-brown"></div><div class="design-white"></div>
    <div class="header-logo">
    <a>注文確定</a>
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

    double sum = 0.0;
    session.invalidate();
    %>

    <div class="form">
        
        <br>
        以下の内容で注文を確定しました！<br>
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
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/Loginuser">購入登録ページへ戻る</a>
    </div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>
    <footer>
    <div class="footer-logo">
        またのご利用をお待ちしております。
    </div>
    </footer>
    <div class="design-white"></div><div class="design-brown"></div>


</body>
</html>

