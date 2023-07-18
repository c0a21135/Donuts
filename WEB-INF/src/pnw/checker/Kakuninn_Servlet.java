package pnw.checker;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pnw.common.PnwDB;

import javax.servlet.RequestDispatcher;

@WebServlet("/checker/Kakuninn_Servlet")

public class Kakuninn_Servlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kakuninn_Servlet() {
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

        // ログイン画面で入力したパスワード
        String docked_number = request.getParameter("docked_number");
        int docked_number_num = Integer.parseInt(docked_number);

        // 転送先のURL
        String forwardURL = "";

        ResultSet rs;

        try {
            // ここから
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．インスタンス化
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)

            // 一旦全部表示 ショッピングを１にドーナツを２に
            // String sql = "SELECT tb1.donuts_count, tb2.donut_name, tb2.donut_price FROM
            // shopping_cart tb1 INNER JOIN donuts tb2 ON tb1.donut_id = tb2.donut_id WHERE
            // tb1.docked_number = ? AND ";
            String sql = "SELECT tb1.donuts_count, tb2.donut_name, tb2.donut_price FROM shopping_cart tb1 JOIN donuts tb2 ON tb1.donut_id = tb2.donut_id JOIN users tb3 ON tb1.docked_number = tb3.docked_number WHERE tb1.docked_number = ? AND tb3.completed = 1";
            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/
            // ?のところに値いれる
            stmt.setInt(1, docked_number_num);

            // 実行結果取得
            // ここまで
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            // ArrayList<String> nameArray = new ArrayList<String>();
            // 結果格納のLIst
            ArrayList<UserInfoBean_tyuumonn> list_s = new ArrayList<UserInfoBean_tyuumonn>();

            while (rs.next()) {
                // カラムの値を取得する．
                // tb1.donuts_count, tb2.donut_name, tb2_donut_price

                int donuts_count = rs.getInt("tb1.donuts_count");
                String donut_name = rs.getString("tb2.donut_name");
                double donut_price = rs.getDouble("tb2.donut_price");
                // beanを生成
                UserInfoBean_tyuumonn bean = new UserInfoBean_tyuumonn(donut_name, donut_price);
                bean.setCount(donuts_count);
                bean.setPrice(donut_price);

                // Listへbeanを追加する．
                list_s.add(bean);

                // 見つかった
                cnt++;
            }
            if (cnt == 0) {
                // 見つからない == 前の人が終わっている
                String no = "その番号はまだ選んでいるか(completed = 0)、そもそも登録されていないかのどちらかです";
                request.setAttribute("greeting", no);
                forwardURL = "/checker/tyuumonn_taiki.jsp";

            } else {
                // 見つかる＝＝前の人が終わっていないので戻る
                // String no = "時間を置いてから再度お願いいたします。";
                // request.setAttribute("greeting", no);

                forwardURL = "/checker/tyuumonn.jsp";

            }
            // requestへセットする．
            request.setAttribute("userlist", list_s);

            // stmt = db.getStmt(sql);

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
