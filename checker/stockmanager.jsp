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
    UserInfoBean bean = (UserInfoBean)session.getAttribute("cuser");
    %>
    <%=bean.getUserID()%>：でログイン中 --%>

    <div class="table">
        <table class="design01">
        <tr>
            <td>Id</td>
            <td>商品名</td>
            <td>価格</td>
            <td>在庫</td>
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

        <p>新規商品追加： 各項目を指定</p>
        <p>在庫更新： ID・数量を指定</p>
        <p>在庫追加： ID・数量を指定</p>
        <p>商品削除： IDを指定</p>

        <form action="./StockProcessServlet" method="post" accept-charset="UTF-8">
        ⅠⅮ　：<input type="text" name="id"><br>
        商品名：<input type="text" name="name" ><br>
        価格　：<input type="text" name="price" ><br>
        数量　：<input type="text" name="quantity" ><br>
        <input type="submit" name="btn" value="新規商品追加">
        <input type="submit" name="btn" value="在庫更新">
        <input type="submit" name="btn" value="在庫追加">
        <input type="submit" name="btn" value="商品削除">
        </form>
    </div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <footer>
    <div class="footer-logo">
        <a href="http://pnw.cloud.cs.priv.teu.ac.jp:8080/2023g03/checker/checkerhome.jsp">管理者ホームへ ＞</a>
    </div>
    </footer>

    <div class="design-white"></div><div class="design-brown"></div>

</body>
</html>
