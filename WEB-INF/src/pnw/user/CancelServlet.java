package pnw.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.servlet.ServletContext;

@WebServlet("/CancelServlet")
public class CancelServlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelServlet() {
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
        UsersBean user = (UsersBean)session.getAttribute("user");
        
        try {
            // ここから
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．インスタンス化
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            String sql = "UPDATE users SET completed=? where docked_number=?";
            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/
            stmt.setInt(1, 9);
            stmt.setInt(2, user.getDockedNumber());

            // 実行結果取得
            // ここまで
            int ret = stmt.executeUpdate();
            forwardURL = "/Loginuser";
            session.invalidate();

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
