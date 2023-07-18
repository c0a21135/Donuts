<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>



<html>


    <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>テスト</title>
        

    </head>

    <body>
        <h1>予約確定ページ</h1>
        <form action="./Yoyaku_taiki_Servlet" method="post">
            整理番号：<input type="int" name="docked_number" required><br>
            <input type="submit" value="確定チェック" style=" display: inline-block;
    vertical-align: middle;
    margin: 0 10px;
    padding: 6px 25px;
    color: #F15A24;
    font-weight: bold;
    letter-spacing: 0.5pt;
    text-decoration: none;
    background-color: #ffffff;
    border: 1px solid #F15A24;
    cursor: pointer;
    transition-duration: 0.3s;
    -webkit-transition-duration: 0.3s;
    -moz-transition-duration: 0.3s;
    -o-transition-duration: 0.3s;
    -ms-transition-duration: 0.3s">


        </form>
        <link rel="stylesheet" href="./css/btn.css" />

      




<br>
        <a href="">整理番号分からん方はこちらから</a>
        <%-- <% 
        UsersBean user = (UsersBean) session.getAttribute("user");
        %>
        <%=user.getNickName()%>さん<br>
        <%=user.getDockedNumber()%> --%>
    


    </body>

</html>