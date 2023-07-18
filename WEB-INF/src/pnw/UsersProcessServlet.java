package pnw;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pnw.common.DonutsBean;
import pnw.common.PnwDB;
import pnw.common.UsersBean;

import javax.servlet.RequestDispatcher;

@WebServlet("/UsersProcessServlet")
public class UsersProcessServlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // HTTP応答のエンコード設定
        response.setContentType("text/html; charset=UTF-8");

        ResultSet rs;
        String forwardURL = null;
        // ボタンの値を取得する．
        String btn_val = request.getParameter("btn");

        try {
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            String sql = null;
            int docked_number = 0;

            String nickname = request.getParameter("nickname");

            // String completedParam = request.getParameter("completed");
            // int completed = Integer.parseInt(completedParam);
            int completed = 0;

            PreparedStatement stmt = null;
            switch (btn_val) {
                case "カートへ":
                    HttpSession session = request.getSession();
                    int uid = 999;
                    UsersBean an_user;

                    // sessionでuserが作られていない場合はuserを作成する
                    if (session.getAttribute("user") == null) {
                        sql = "INSERT INTO users (nickname,completed) VALUES(?,?)";
                        stmt = db.getStmt(sql);
                        stmt.setString(1, nickname);
                        stmt.setInt(2, completed);
                        int ret4 = stmt.executeUpdate();
                        ResultSet id = stmt.getGeneratedKeys();
                        if (id.next()) {
                            uid = id.getInt(1);
                        }
                        // ログインしたユーザをUsersBeanオブジェクトに格納して購入ページへ送信
                        an_user = new UsersBean(nickname, completed);
                        an_user.setDockedNumber(uid);
                        session.setAttribute("user", an_user);

                    }
                    String nonecart = "";
                    request.setAttribute("nonecart", nonecart);
                    forwardURL = "/user/cart.jsp";
                    break;

            }
            // 個別の処理が終わったら，再度，DBから一覧を取得する．
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            // sql = "SELECT * FROM users";
            // stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/

            sql = "select s.donut_id, d.donut_name, d.donut_price, s.donut_stock from stock s join donuts d on (s.donut_id = d.donut_id)";
            stmt = db.getStmt(sql);

            // 実行結果取得
            rs = stmt.executeQuery();
            // // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            ArrayList<DonutsBean> infoArray = new ArrayList<DonutsBean>();
            while (rs.next()) {
                // // カラムの値を取得する．
                int id = rs.getInt("donut_id");
                String name = rs.getString("donut_name");
                double price = rs.getInt("donut_price");
                int quantity = rs.getInt("donut_stock");

                // int id = rs.getInt("docked_number");
                // String name = rs.getString("nickname");
                // int comp = rs.getInt("completed");
                // // beanを生成
                DonutsBean bean = new DonutsBean(name, price, quantity);
                bean.setDonutId(id);
                // Listへbeanを追加する．
                infoArray.add(bean);
                // bean.setDockedNumber(id);
                // // Listへbeanを追加する．
                // infoArray.add(bean);
                // // 見つかった
                cnt++;
            }

            HttpSession session = request.getSession();
            // セッションへセットする．
            session.setAttribute("stock", infoArray);
            // forwardURL = "/loginuser.jsp";

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 外部ファイルに転送する準備
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
        // 外部ファイルに表示処理を任せる
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
