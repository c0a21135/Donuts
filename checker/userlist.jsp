<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, pnw.*, pnw.checker.*, pnw.common.*"%>
<html>  
<head>
    <meta charset="UTF-8">
    <title>ユーザ一覧</title>
    <link rel="stylesheet" type="text/css" href="./css/all.css" media="all" id="cssMain">
</head>      
<body>
    <header>
    <div class="design-brown"></div><div class="design-white"></div>
    <div class="header-logo">
    <a>ユーザ一覧</a>
    </div>
    <div class="icon">
        <img src="./img/TUROto.png">
    </div>
    </header>
    <div class="design-line"></div>
    <div class="design-btm"></div>
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <div class="table">
        <table class="design01">
        <tr>
            <td>整理番号</td>
            <td>名前</td>
            <td>取引状況</td>
        </tr>
        <%
        //Servletで設定されたリストを取得する．
        ArrayList<UsersBean> list = (ArrayList<UsersBean>)session.getAttribute("userslist");
        Iterator<UsersBean> ite = list.iterator();

        String sta = "";

        //結果の表示
        while(ite.hasNext()){
            UsersBean bean = ite.next();
        %>
        <%-- HTML内にJSPコードをスクリプト式として埋め込む--%>
            <tr>
            <td><%=bean.getDockedNumber()%></td>
            <td><%=bean.getNickName()%></td>
            <td>
            <%
            int status = bean.getCompleted();
            switch(status){
                case 0:
                    sta = "選択中";
                    break;
                case 1:
                    sta = "待機";
                    break;
                case 2:
                    sta = "完了";
                    break;
                case 9:
                    sta = "中止";
            }
            %>
            <%=sta%>
            </td>
            </tr>
        <%
        }
        %>
        </table>
    </div>
    
    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <div class="form">
        <h3>整理番号指定でユーザの詳細画面へ移動</h3>

        <form action="./StockProcessServlet" method="post" accept-charset="UTF-8">
        <%-- ⅠⅮ　：<input type="text" name="id"><br>
        商品名：<input type="text" name="name" ><br>
        価格　：<input type="text" name="price" ><br>
        数量　：<input type="text" name="quantity" ><br>
        <input type="submit" name="btn" value="新規商品追加">
        <input type="submit" name="btn" value="在庫更新">
        <input type="submit" name="btn" value="在庫追加">
        <input type="submit" name="btn" value="商品削除"> --%>
        <input type="submit" name="btn" value="hoge">
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

