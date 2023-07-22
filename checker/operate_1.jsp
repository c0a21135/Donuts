<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>注文表取得ページ</title>
        <link rel="stylesheet" type="text/css" href="css/all.css" media="all" id="cssMain">
    </head>

    <body>
        <header>
        <div class="design-brown"></div><div class="design-white"></div>
        <div class="header-logo">
        <a>管理者ホーム</a>
        </div>
        <div class="icon">
            <img src="img/TUROto.png">
        </div>
        </header>
        <div class="design-line"></div>
        <div class="design-btm"></div>
        <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

        <div class="form">
            <form action="./Kakuninn_Servlet" method="post">
                次の注文者の整理番号：<input type="int" name="docked_number" required><br>
                <input type="submit" value="あれ" style="display: inline-block;">       
            </form>
        </div>
        <div class="form">
            <a href="seiribanngou_kakuninn_Servlet">次の整理番号はこちら ＞</a>
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