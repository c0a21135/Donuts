<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, pnw.user.*" %>
        <html>

            <head>
                <meta charset="UTF-8">
                <title>一覧と更新処理</title>
            </head>

            <body>
                <h1>一覧と更新処理</h1>
                <% 
                //Servletで設定したセッションから取得する
                UserInfoBean_nickname bin=(UserInfoBean_nickname)session.getAttribute("nickname"); 
                %>
                <%=bin.getNickname()%>さんの整理番号は下記の通りです。


                <table border="1">
                    <%--Point: 学籍番号と名前を書いて下さい--%>

                        <tr>
                            <td>（整理番号）docked_number</td>

                        </tr>
                        <% ArrayList<UserInfoBean_nickname> list = (ArrayList<UserInfoBean_nickname>
                                )request.getAttribute("docked_number");
                                Iterator<UserInfoBean_nickname> ite = list.iterator();

                                    //結果の表示
                                    while(ite.hasNext()){
                                    //Point: Iteratorの次の要素をbeanへ格納させてください
                                    UserInfoBean_nickname bean = ite.next();
                                    %>
                                    <%-- HTML内にJSPコードをスクリプト式として埋め込む--%>
                                        <tr>
                                            <td>
                                                <%=bean.getNumber()%>
                                            </td>

                                        </tr>
                                        <% } %>
                                        
                </table>
                <% 
                
                
                
                %>


        <a href="wakarannLogoutServlet">セッション消すんご</a>

                <hr />


            </body>

        </html>