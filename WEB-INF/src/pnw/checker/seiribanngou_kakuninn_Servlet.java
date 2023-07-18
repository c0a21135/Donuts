package pnw.checker;

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

import pnw.common.PnwDB;

import javax.servlet.RequestDispatcher;

@WebServlet("/checker/seiribanngou_kakuninn_Servlet")
public class seiribanngou_kakuninn_Servlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public seiribanngou_kakuninn_Servlet() {
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
        String forwardURL = "/checker/seiribanngou_list.jsp";

        try {
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            // String sql = "SELECT docked_number FROM users WHERE completed = 1 ASC LIMIT
            // 0, 1";
            String sql = "SELECT docked_number FROM users WHERE completed = 1 ORDER BY docked_number DESC LIMIT 0, 1";
            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/

            // 実行結果取得
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            ArrayList<UserInfoBean_seribanngou> infoArray = new ArrayList<UserInfoBean_seribanngou>();

            while (rs.next()) {
                // カラムの値を取得する．
                int docked_number = rs.getInt("docked_number");

                // beanを生成
                UserInfoBean_seribanngou bean = new UserInfoBean_seribanngou(docked_number);
                // bean.setID(id);
                // Listへbeanを追加する．
                infoArray.add(bean);
                // 見つかった
                cnt++;
            }
            // requestへセットする．
            request.setAttribute("docked_number", infoArray);
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
