package pnw.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pnw.common.CartBean;
import pnw.common.DonutsBean;
import pnw.common.PnwDB;
import pnw.common.UsersBean;
import pnw.common.DonutsCountBean;

import javax.servlet.RequestDispatcher;

@WebServlet("/CartCheckingServlet")
public class CartChecking extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartChecking() {
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
        // フォワード先を待機画面に
        String forwardURL = null;
        HttpSession session = request.getSession();

        // ショッピングカート情報(ユーザID, ドーナツのID, count)を取得
        CartBean cart = (CartBean) session.getAttribute("shoppingcart");

        int preUid = cart.getDockedNumber();

        try {
            // ここから
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．インスタンス化
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            String sql = "SELECT * FROM users where docked_number=? and completed=0";
            // String sql = "SELECT COUNT(*) FROM users WHERE < docked_number=そいつのID"

            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/
            // ?のところに値いれる

            stmt.setInt(1, preUid);

            // stmt.setString(2, completed);//デバック用

            // 実行結果取得
            // ここまで
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            // ArrayList<String> nameArray = new ArrayList<String>();

            // completedを１にする
            // int completed = 1;

            while (rs.next()) {

                // 見つかった
                cnt++;
            }
            if (cnt == 0) {
                // 見つからない == 前の人が終わっている

                sql = "UPDATE users SET completed=1 WHERE docked_number=?";
                // sql = "UPDATE users SET completed=1 WHERE docked_number=そいつのID

                stmt = db.getStmt(sql);
                stmt.setInt(1, cart.getDockedNumber());
                int ret2 = stmt.executeUpdate();

                forwardURL = "/user/completed.jsp";
            } else {
                // 見つかる＝＝前の人が終わっていないので戻る
                forwardURL = "/user/yoyaku_taiki.jsp";
            }
            // sql = "SELECT * FROM users";
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
