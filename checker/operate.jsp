<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, pnw.checker.*, pnw.common.*" %>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>販売用ページ</title>
            <link rel="stylesheet" type="text/css" href="css/operate.css" media="all" id="cssMain">
        </head>

        <body>
            <header>
            <div class="design-brown"></div><div class="design-white"></div>
            <div class="header-logo">
            <a>販売用ページ</a>
            </div>
            <div class="icon">
                <img src="img/TUROto.png">
            </div>
            </header>
            <div class="design-line"></div>
            <div class="design-btm"></div>
            <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>


                    <%
                    UsersBean focus_user = (UsersBean)session.getAttribute("focus_user");
                    String comment = (String)session.getAttribute("comm");   
                    String jump_comment = (String)session.getAttribute("jumpcomm");     
                    //Servletで設定されたリストを取得する．
                    ArrayList<UsersBean> ulist = (ArrayList<UsersBean>)session.getAttribute("userslist");
                    Iterator<UsersBean> uite = ulist.iterator();
                    String sta = "";
                    
                    %>
            
            <div class="total">

            <%-- 左側リスト表示 --%>
            <div class="left">
                <div class="table">
                    <div class="form">
                        <h3>整理番号で直接移動</h3>
                        <form action="./OperateSurvlet" method="post" accept-charset="UTF-8">
                        整理番号：<input type="text" name="jump_num" required><%=jump_comment%><br>
                        <input type="submit" name="btn" value="指定した番号へジャンプ">
                        </form>
                    </div>
                    <table class="design01">
                        <tr bgcolor="#141414">
                            <td class="label">整理番号</td>
                            <td class="label">名前</td>
                            <td class="label">取引状況</td>
                        </tr>


                        <%
                        //結果の表示
                        while(uite.hasNext()){
                            UsersBean ubean = uite.next();
                        %>
                        <%-- HTML内にJSPコードをスクリプト式として埋め込む--%>
                            <tr>
                            <td><%=ubean.getDockedNumber()%></td>
                            <td><%=ubean.getNickName()%></td>
                            <td>
                            <%
                            sta = CreateMessage.generate(ubean.getCompleted());
                            %>
                            <%=sta%>
                            </td>
                            </tr>
                        <%
                        }
                        %>
                    </table>
                </div>
            </div>

            <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

    <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>


            <div class="right">

                <div class="page">
                    <form action="./OperateSurvlet" method="post" accept-charset="UTF-8">
                    <div class="btn-l"> <input type="submit" name="btn" value="１つ前へ"></div>
                    <div class="btn-r"> <input type="submit" name="btn" value="１つ後へ"></div>
                </div>

                <div class="form2">

                    <%-- 整理番号とニックネームの表示 --%>
                    <p class="logo"><b><%=focus_user.getDockedNumber()%>：　<%=focus_user.getNickName()%></b></p><br>
                    取引状況：　<%=CreateMessage.generate(focus_user.getCompleted())%>

                    <%-- 注文内容の表示 --%>
                    <div class="table2">
                        <table class="design01">
                            <tr>
                                <td>商品名</td>
                                <td>数量</td>
                                <td>価格</td>
                            </tr>
                            <%
                            ArrayList<UserInfoBean_tyuumonn> list = (ArrayList<UserInfoBean_tyuumonn>)session.getAttribute("order_list");
                            Iterator<UserInfoBean_tyuumonn> ite = list.iterator();
                            int total = 0;
                            //結果の表示
                            while(ite.hasNext()){
                                UserInfoBean_tyuumonn bean = ite.next();
                            %>
                            <tr>
                                <td>
                                    <%=bean.getName()%>
                                </td>
                                <td>
                                    <%=bean.getCount()%>
                                    <%
                                    int count = bean.getCount();
                                    %>
                                </td>
                                <td>
                                    <%
                                    int price = (int)bean.getPrice();
                                    %>
                                    <%=price%>
                                    <%
                                    total += count*price;
                                    %>
                                </td>
                            </tr>          
                            <% 
                            }
                            %>

                            <%-- 合計金額の表示 --%>
                            <table border="0" cellspacing="1" bgcolor="red">
                            <tr><br><br>
                            <td bgcolor="white">合計金額は<strong><font color="red"><%=total%></font>円</strong>です</td>
                            </tr>
                            </table>
                        </table>
                    <%=comment%>
                    </div>

                    <br>
                    <form action="./OperateSurvlet" method="post" accept-charset="UTF-8">
                    <input type="submit" name="btn" value="完了">
                </div>

                <%-- 完了--%>               

                
            </div>
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