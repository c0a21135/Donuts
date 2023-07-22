<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, pnw.checker.*" %>
        <html>

            <head>
                <meta charset="UTF-8">
                <title>一覧と更新処理</title>
            </head>

            <body>
            <div class="center">

                <h1>注文表</h1>
                <table border="1">
                        <tr>
                            <td>数</td>
                            <td>一個の値段</td>
                            <td>ドーナツの名前</td>
                        </tr>
                        <%
                            ArrayList<UserInfoBean_tyuumonn> list = (ArrayList<UserInfoBean_tyuumonn>)request.getAttribute("userlist");
                                Iterator<UserInfoBean_tyuumonn> ite = list.iterator();
                                double testsum = 0;

                                    //結果の表示

                                    while(ite.hasNext()){
                                        UserInfoBean_tyuumonn bean = ite.next();

                                    %>
                                        <tr>
                                            <td>
                                                <%=bean.getCount()%>
                                                <%
                                                int count = bean.getCount();
                                                %>
                                            </td>
                                            <td>
                                                <%=bean.getPrice()%>
                                                <%
                                                testsum += (double)count * bean.getPrice();
                                                %>
                                            </td>
                                            <td>
                                                <%=bean.getName()%>
                                            </td>
                                           
                                        </tr>
                                       
                                        <% 
                                    }
                                        %>


                                    
                                        <table border="0" width="200" height="150" cellspacing="1" bgcolor="blue">
                                        <tr>
                                        <td bgcolor="white">合計金額は<strong><%=testsum%>円</strong>です</td>
                                        </tr>
                                        </table>


                </table>





                  </div>

            </body>

        </html>