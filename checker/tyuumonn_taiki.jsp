<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

        <head>
            <meta charset="UTF-8">
            <title>注文のやつ</title>
        </head>

        <body>
            <%-- JSPのコード部 --%>
                <% String val=(String)request.getAttribute("greeting"); %>

                    <%= val %>

                        <br>
                        <a href="toppage.jsp"><img src="../img/back.png"></a>
                        <h3>トップページに戻る</h3>


        </body>

    </html>