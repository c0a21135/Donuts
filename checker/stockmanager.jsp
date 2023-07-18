<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, pnw.*, pnw.checker.*, pnw.common.DonutsBean"%>
<html>  
<head>
    <meta charset="UTF-8">
    <title>在庫管理</title>
    <link rel="stylesheet" type="text/css" href="../css/all.css" media="all" id="cssMain">
</head>      
<body>
    <header>
    <div class="design-brown"></div><div class="design-white"></div>
    <div class="header-logo">
    <a>在庫管理</a>
    </div>
    <div class="icon">
        <img src="../img/TUROto.png">
    </div>
    </header>
    <div class="design-line"></div>
    <div class="design-btm"></div>
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <%-- <%
    UserInfoBean bean = (UserInfoBean)session.getAttribute("user");
    %>
    <%=bean.getUserID()%>：でログイン中 --%>

    <div class="table">
        <table class="design01">
        <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Price</td>
            <td>Stock</td>
        </tr>
        <%
        //Servletで設定されたリストを取得する．
        ArrayList<DonutsBean> list = (ArrayList<DonutsBean>)request.getAttribute("stocklist");
        Iterator<DonutsBean> ite = list.iterator();

        //結果の表示
        while(ite.hasNext()){
            DonutsBean bean = ite.next();
        %>
        <%-- HTML内にJSPコードをスクリプト式として埋め込む--%>
            <tr>
            <td><%=bean.getDonutId()%></td>
            <td><%=bean.getDonutName()%></td>
            <td><%=bean.getDonutPrice()%></td>
            <td><%=bean.getQuantity()%></td>
            </tr>
        <%
        }
        %>
        </table>
    </div>
    
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <div class="form">
        <h3>商品の管理（ 追加 / 削除 / 在庫補充 ）</h3>

        <p>商品の追加： 各項目を指定</p>
        <p>商品の削除： IDを指定</p>
        <p>在庫補充： ID・数量を指定</p>

        <form action="./StockProcessServlet" method="post" accept-charset="UTF-8">
        ⅠⅮ　：<input type="text" name="id"><br>
        数量　：<input type="text" name="quantity" ><br>
        商品名：<input type="text" name="name" ><br>
        価格　：<input type="text" name="price" ><br>
        <input type="submit" name="btn" value="追加">
        <input type="submit" name="btn" value="更新">
        <input type="submit" name="btn" value="削除">
    </div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <footer>
    <div class="footer-logo">
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/checker/checkerhome.jsp">管理者ホームへ ＞</a>
    </div>
    </footer>

    <div class="design-white"></div><div class="design-brown"></div>
</form>
</body>
</html>
