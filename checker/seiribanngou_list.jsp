<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, pnw.checker.*" %>
        <html>

            <head>
                <meta charset="UTF-8">
                <title>一覧と更新処理</title>
            </head>

            <body>
                <h1>次の人の整理番号は下記の通りです</h1>
                <table border="1">
                        
                        <tr>
                            <td>docked_number</td>
                           
                        </tr>
                        <%
                            ArrayList<UserInfoBean_seribanngou> list = (ArrayList<UserInfoBean_seribanngou>)request.getAttribute("docked_number");
                                Iterator<UserInfoBean_seribanngou> ite = list.iterator();

                                    //結果の表示
                                    while(ite.hasNext()){
                                    //Point: Iteratorの次の要素をbeanへ格納させてください
                                    UserInfoBean_seribanngou bean = ite.next();
                                    %>
                                    <%-- HTML内にJSPコードをスクリプト式として埋め込む--%>
                                        <tr>
                                            <td>
                                                <%=bean.getNumber()%>
                                            </td>
                                           
                                        </tr>
                                        <% } %>
                </table>

                <a href="operate_1.jsp">もどるんご</a>

                <hr />
                
                   
            </body>

        </html>