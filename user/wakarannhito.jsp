<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>

<html>
    <link rel="stylesheet" type="text/css" href="css/wakarannhito.css" />



    <head>

        <meta charset="UTF-8">
        <title>テスト</title>
        


    </head>

    <body>
        <h1>ニックネームから整理番号確認ページ</h1>
        <form action="./wakarann_Servlet" method="post">

            ニックネームをいれてください：<input type="String" name="nickname" required>
            <input type="submit" value="送信" style=" display: inline-block;
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




        <a href="yoyaku_taiki.jsp">もどるんご</a>

    </body>

</html>