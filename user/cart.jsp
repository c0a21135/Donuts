<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*,pnw.common.UsersBean,pnw.common.DonutsBean,pnw.*"%>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>注文ページ</title>
            <link rel="stylesheet" type="text/css" href="css/all.css" media="all" id="cssMain">
        </head>

        <%
        //ログインしたユーザ情報を受け取る
        UsersBean user = (UsersBean)session.getAttribute("user"); 
        // ドーナツの一覧(名前・値段・在庫)を取得
        ArrayList<DonutsBean> list = (ArrayList<DonutsBean>)session.getAttribute("stock");
        Iterator<DonutsBean> ite = list.iterator();
        %>

        <body>
            <header>
            <div class="design-brown"></div><div class="design-white"></div>
            <div class="header-logo">
            <a><%=user.getNickName()%>さんの注文ページ</a>
            </div>
            <div class="icon">
                <img src="img/TUROto.png">
            </div>
            </header>
            <div class="design-line"></div>
            <div class="design-btm"></div>
            <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>

            <div class="form">
                <h1>あなたの整理番号は<%=user.getDockedNumber()%>です</h1>
            </div>

            <div class="form">
                <form action="./CartCreateServlet" method="post">
                
                <%//結果の表示
                while(ite.hasNext()){
                    DonutsBean bean = ite.next();
                %>

                <%-- HTML内にJSPコードをスクリプト式として埋め込む--%>
                    <font face="Impact"><span><%=bean.getDonutName()%>  税込み¥<%=bean.getDonutPrice()%></span></font>
                            <select name="<%=bean.getDonutId()%>">
                            <%
                            int loopcount = Math.min(bean.getQuantity(), 10);
                            for(int i=0; i<=loopcount; i++){
                            %>
                            <option value="<%=i%>"><%=i%></option>

                                <%
                                }
                                %>
                                
                                <!--oldというドロップダウンリストにoptionの中(1~10)の選択肢が入ってる-->  
                                </select><br>
                <%
                }
                %>

                <br>
                <p><b><font color="red"><%String message = (String)session.getAttribute("nonecart");%>
                <%=message%></font></b></p><br>

                <input type="submit" name="btn" value="注文">
                </form>
            </div>

            <div class="design-white"></div><div class="design-brown"></div><div class="design-white"></div>
            <footer>
            <div class="footer-logo">
                <p>注文をやめる場合は<a href="./CancelServlet" onclick="return confirm('本当に注文をやめますか？');" style="text-align:left"><font color="red">こちら</font></a>を押してください</p>
            </div>
            </footer>
            <div class="design-white"></div><div class="design-brown"></div>

        </body>

        </html>