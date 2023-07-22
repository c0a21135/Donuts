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
import pnw.common.UsersBean;

import javax.servlet.RequestDispatcher;

@WebServlet("/UserListServlet")

public class UserList extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserList() {
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

        // 転送先のURL
        String forwardURL = "";

        ResultSet rs;
        HttpSession session = request.getSession();

        try {
            // ここから
            /** DB接続に関する共通部 START **/
            // DB接続してStatementを取得する．インスタンス化
            PnwDB db = new PnwDB("2023g03");
            // SQL文の作成(プレースホルダーを使うのがわかりやすい)
            String sql = "SELECT * FROM users";
            PreparedStatement stmt = db.getStmt(sql);
            /** DB接続に関する共通部 END **/
            // 実行結果取得
            // ここまで
            rs = stmt.executeQuery();
            // データがなくなるまで(rs.next()がfalseになるまで)繰り返す
            int cnt = 0;
            // ArrayList<String> nameArray = new ArrayList<String>();
            // 結果格納のLIst
            ArrayList<UsersBean> usersBeans = new ArrayList<UsersBean>();

            while (rs.next()) {
                // カラムの値を取得する．
                int docked_number = rs.getInt("docked_number");
                String nickname = rs.getString("nickname");
                int completed = rs.getInt("completed");
                // beanを生成
                UsersBean bean = new UsersBean(nickname, completed);
                bean.setDockedNumber(docked_number);

                // Listへbeanを追加する．
                usersBeans.add(bean);

                // 見つかった
                cnt++;
            }
            forwardURL = "checker/operate.jsp";
            // requestへセットする．
            session.setAttribute("userslist", usersBeans);

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
